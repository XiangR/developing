package com.joker.cache;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.alibaba.fastjson.parser.ParserConfig;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import com.joker.entity.Func;
import com.joker.utils.DataUtil;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 基于Redis的键值对存储服务
 *
 * @author xiangrui
 */
public class KeyValueService {

    // region 属性

    /**
     * 开启 enable_autotype
     * {@link <a https://github.com/alibaba/fastjson/wiki/enable_autotype />}
     */
    static {
        ParserConfig.getGlobalInstance().setAutoTypeSupport(true);
    }

    private static final Logger LOGGER = LoggerFactory.getLogger(KeyValueService.class);

    private static final String NX = "NX";

    private static final String KEY_VALUE_MULTI_REFRESH = "KeyValueMultiRefresh";

    /**
     * 任务队列
     */
    private static final LinkedBlockingQueue<Runnable> REFRESH_WORKING_QUEUE = new LinkedBlockingQueue<>(1024);

    /**
     * 异步线程池
     */
    private static final ThreadPoolExecutor EXECUTOR = new ThreadPoolExecutor(20, 20, 30, TimeUnit.SECONDS,
            REFRESH_WORKING_QUEUE,
            new ThreadFactoryBuilder().build(),
            (r, executor) -> {
                try {
                    executor.getQueue().put(r);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
    );

    private RedisStoreClient redis = null;

    //集群名称
    private String clusterName = "";

    //路由策略,默认只从主节点读取
    private String routerType = "master-only";

    //读取超时时间,默认1000ms
    private int readTimeout = 1000;

    //连接redis节点的连接池配置
    private int poolMaxIdle = 16;

    private int poolMaxTotal = 32;

    private int poolWaitMillis = 500;

    private int poolMinIdle = 3;

    public String getClusterName() {
        return clusterName;
    }

    public void setClusterName(String clusterName) {
        this.clusterName = clusterName;
    }

    public String getRouterType() {
        return routerType;
    }

    public void setRouterType(String routerType) {
        this.routerType = routerType;
    }

    public int getReadTimeout() {
        return readTimeout;
    }

    public void setReadTimeout(int readTimeout) {
        this.readTimeout = readTimeout;
    }

    public int getPoolMaxIdle() {
        return poolMaxIdle;
    }

    public void setPoolMaxIdle(int poolMaxIdle) {
        this.poolMaxIdle = poolMaxIdle;
    }

    public int getPoolMaxTotal() {
        return poolMaxTotal;
    }

    public void setPoolMaxTotal(int poolMaxTotal) {
        this.poolMaxTotal = poolMaxTotal;
    }

    public int getPoolWaitMillis() {
        return poolWaitMillis;
    }

    public void setPoolWaitMillis(int poolWaitMillis) {
        this.poolWaitMillis = poolWaitMillis;
    }

    public int getPoolMinIdle() {
        return poolMinIdle;
    }

    public void setPoolMinIdle(int poolMinIdle) {
        this.poolMinIdle = poolMinIdle;
    }

    public void init() {
        RedisStoreClient build = new RedisClientBuilder(clusterName)
                .readTimeout(readTimeout)
                .routerType(routerType)
                .poolMaxIdle(poolMaxIdle)
                .poolMaxTotal(poolMaxTotal)
                .poolWaitMillis(poolWaitMillis)
                .poolMinIdle(poolMinIdle)
                .build();
        this.redis = build;
    }

    // endregion

    // region 共有方法

    public <T> void del(String category, String folder, List<T> keyList) {
        if (CollectionUtils.isEmpty(keyList)) {
            return;
        }
        List<StoreKey> storeKeyList = getStoreKeyList(category, folder, keyList);
        for (StoreKey storeKey : storeKeyList) {
            redis.delete(storeKey);
        }
    }

    public <T, R> void mset(String category, String folder, Map<T, R> keyValues, int expireInSeconds) {
        if (MapUtils.isEmpty(keyValues)) {
            return;
        }
        Map<StoreKey, String> cacheMap = Maps.newHashMap();
        for (Map.Entry<T, R> entry : keyValues.entrySet()) {
            StoreKey key = new StoreKey(category, getRealKey(folder, entry.getKey()));
            cacheMap.put(key, JSON.toJSONString(entry.getValue()));
        }
        Set<StoreKey> keyList = cacheMap.keySet();
        DataUtil.partitionInvoke(keyList, (thisList) -> {
            Map<StoreKey, String> innerCache = Maps.newHashMap();
            for (StoreKey storeKey : thisList) {
                innerCache.put(storeKey, cacheMap.get(storeKey));
            }
            redis.multiSet(innerCache, expireInSeconds);
            return null;
        }, 1000);
    }

    public <T, R> List<R> mget(String category, String folder, List<T> keys, Class<R> type) {
        if (CollectionUtils.isEmpty(keys)) {
            return Lists.newArrayList();
        }
        List<StoreKey> storeKeyList = getStoreKeyList(category, folder, keys);
        Map<StoreKey, String> resultMap = redis.multiGet(storeKeyList);
        return parseRes(resultMap, type);
    }

    /**
     * mget 支持查询缓存，支持缓存未命中自定义查询策略，并将数据刷新到缓存中 刷新缓存的过程是异步的
     *
     * @param category        缓存类别
     * @param folder          缓存前缀
     * @param keyList         缓存 key
     * @param type            T
     * @param func            key 未命中时的 refresh 策略
     * @param expireInSeconds 过期时间
     */
    public <T, R> Map<T, R> mget(String category, String folder, List<T> keyList, Class<R> type, Func<List<T>, Map<T, R>> func, int expireInSeconds) {
        if (CollectionUtils.isEmpty(keyList)) {
            return Maps.newHashMap();
        }
        List<StoreKey> storeKeyList = Lists.newArrayList();
        Map<StoreKey, T> storeKey2keyMap = Maps.newHashMap();
        for (T key : keyList) {
            StoreKey storeKey = new StoreKey(category, getRealKey(folder, key));
            storeKeyList.add(storeKey);
            storeKey2keyMap.put(storeKey, key);
        }
        // 第一段查询
        Map<StoreKey, String> resultMap = redis.multiGet(storeKeyList);
        Map<T, R> finalRes = Maps.newHashMap();
        finalRes.putAll(parseResMap(resultMap, storeKey2keyMap, type));
        Collection<StoreKey> noCacheKeyList = CollectionUtils.subtract(storeKeyList, resultMap.keySet());
        // 第二段查询
        if (CollectionUtils.isNotEmpty(noCacheKeyList)) {
            List<T> noCacheRealKeys = Lists.newArrayList();
            for (StoreKey storeKey : noCacheKeyList) {
                T t = storeKey2keyMap.get(storeKey);
                if (t != null) {
                    noCacheRealKeys.add(t);
                }
            }
            Map<T, R> applyRes = func.apply(noCacheRealKeys);
            Map<T, R> needCache = Maps.newHashMap();

            if (MapUtils.isNotEmpty(applyRes)) {
                needCache.putAll(applyRes);
                finalRes.putAll(applyRes);
            }

            // 重新刷新缓存
            EXECUTOR.execute(() -> mset(category, folder, needCache, expireInSeconds));
        }
        return finalRes;
    }

    /**
     * mget
     * <p>
     * 1：logicalExpire 时的查询会触发缓存的刷新，这个过程会加上分布式锁，默认锁10s，会在 finally 中 del key
     * 2：physicalExpireSeconds 时间为 redis.expire，理论上 logicalExpireSeconds < physicalExpireSeconds
     * 3：当 key 从 cache 与 function 中都未命中时，默认不填充 null 值
     *
     * @param category              类别
     * @param folder                前缀
     * @param paramList             keyList
     * @param logicalExpireSeconds  逻辑过期时间，开始刷新缓存的时间
     * @param physicalExpireSeconds 物理过期时间，缓存真实失效的时间
     * @param func                  未命中缓存 key 的刷新策略
     * @return key -> cache obj
     */
    public <T, R> Map<T, R> mget(String category,
                                 String folder,
                                 List<T> paramList,
                                 int logicalExpireSeconds,
                                 int physicalExpireSeconds,
                                 Func<List<T>, Map<T, R>> func
    ) {
        return mget(category, folder, paramList, logicalExpireSeconds, physicalExpireSeconds, false, func);
    }

    /**
     * mget
     * <p>
     * 1：logicalExpire 时的查询会触发缓存的刷新，这个过程会加上分布式锁，默认锁10s，会在 finally 中 del key
     * 2：physicalExpireSeconds 时间为 redis.expire，理论上 logicalExpireSeconds < physicalExpireSeconds
     *
     * @param category              类别
     * @param folder                前缀
     * @param paramList             keyList
     * @param logicalExpireSeconds  逻辑过期时间，开始刷新缓存的时间
     * @param physicalExpireSeconds 物理过期时间，缓存真实失效的时间
     * @param cacheNullable         cache 与 function 都未命中的 key 是否刷新为 null
     * @param func                  未命中缓存 key 的刷新策略
     * @return key -> cache obj
     */
    public <T, R> Map<T, R> mget(String category,
                                 String folder,
                                 List<T> paramList,
                                 int logicalExpireSeconds,
                                 int physicalExpireSeconds,
                                 boolean cacheNullable,
                                 Func<List<T>, Map<T, R>> func
    ) {
        // check param
        if (CollectionUtils.isEmpty(paramList)) {
            return Maps.newLinkedHashMap();
        }
        if (logicalExpireSeconds < 0) {
            throw new IllegalArgumentException("logicalExpireSeconds cannot be less than zero");
        }
        if (physicalExpireSeconds < 0) {
            throw new IllegalArgumentException("physicalExpireSeconds cannot be less than zero");
        }
        if (physicalExpireSeconds < logicalExpireSeconds) {
            throw new IllegalArgumentException("logicalExpireSeconds should be less than physicalExpireSeconds");
        }
        if (func == null) {
            throw new NullPointerException("function cannot be null");
        }

        List<T> keyList = DataUtil.distinct(paramList);
        if (CollectionUtils.isEmpty(keyList)) {
            return Maps.newLinkedHashMap();
        }

        // mget redis
        List<KeyValueObject<T, R>> cachedRes = mgetInner(category, folder, keyList);

        // check logic expire or reload
        reloadCachedLogicExpire(category, folder, cachedRes, logicalExpireSeconds, physicalExpireSeconds, func);

        Map<T, R> finalRes = Maps.newLinkedHashMap();
        List<T> cachedKeyList = Lists.newArrayList();
        for (KeyValueObject<T, R> firstRe : cachedRes) {
            if (firstRe != null) {
                cachedKeyList.add(firstRe.getKey());
                finalRes.put(firstRe.getKey(), firstRe.getValue());
            }
        }

        // invoke and mset
        List<T> noCacheKeyList = Lists.newArrayList(CollectionUtils.subtract(keyList, cachedKeyList));
        if (CollectionUtils.isNotEmpty(noCacheKeyList)) {
            Map<T, R> invokeRes = invokeAndRefresh(category, folder, noCacheKeyList, logicalExpireSeconds, physicalExpireSeconds, cacheNullable, func);
            finalRes.putAll(invokeRes);
        }
        return finalRes;
    }

    // endregion

    // region 私有方法

    private <T, R> Map<T, R> invokeAndRefresh(String category,
                                              String folder,
                                              List<T> keyList,
                                              int logicalExpireSeconds,
                                              int physicalExpireSeconds,
                                              boolean cacheNullable,
                                              Func<List<T>, Map<T, R>> func
    ) {
        if (CollectionUtils.isEmpty(keyList)) {
            return Maps.newLinkedHashMap();
        }
        List<T> invokeResKeyList = Lists.newArrayList();
        Map<T, R> invokeRes = func.apply(keyList);
        Map<T, R> finalRes = Maps.newLinkedHashMap();
        if (MapUtils.isNotEmpty(invokeRes)) {
            finalRes.putAll(invokeRes);
            invokeResKeyList.addAll(invokeRes.keySet());
        }
        // 顽固穿透的 key 可以 cache null value
        if (cacheNullable) {
            Collection<T> unCache = CollectionUtils.subtract(keyList, invokeResKeyList);
            for (T key : unCache) {
                finalRes.put(key, null);
            }
        }
        EXECUTOR.execute(
                () -> refreshList(category, folder, finalRes, logicalExpireSeconds, physicalExpireSeconds)
        );
        return finalRes;
    }

    private <T, R> void reloadCachedLogicExpire(String category,
                                                String folder,
                                                List<KeyValueObject<T, R>> cachedList,
                                                int logicalExpireSeconds,
                                                int physicalExpireSeconds,
                                                Func<List<T>, Map<T, R>> func
    ) {
        if (CollectionUtils.isEmpty(cachedList)) {
            return;
        }
        // 筛选出逻辑失效的key
        List<T> logicExpireKeyList = Lists.newLinkedList();
        for (KeyValueObject<T, R> keyValueObject : cachedList) {
            if (keyValueObject != null && keyValueObject.isLogicExpire()) {
                logicExpireKeyList.add(keyValueObject.getKey());
            }
        }
        if (CollectionUtils.isNotEmpty(logicExpireKeyList)) {
            reloadCacheAsync(category, folder, logicExpireKeyList, logicalExpireSeconds, physicalExpireSeconds, func);
        }
    }

    private <T, R> void reloadCacheAsync(String category,
                                         String folder,
                                         List<T> keyList,
                                         int logicalExpireSeconds,
                                         int physicalExpireSeconds,
                                         Func<List<T>, Map<T, R>> func
    ) {
        if (CollectionUtils.isEmpty(keyList)) {
            return;
        }
        EXECUTOR.execute(() -> {
            List<T> newKeyList = Lists.newArrayList();
            List<StoreKey> activeKeyList = Lists.newLinkedList();
            try {
                processSetNX(category, folder, keyList, newKeyList, activeKeyList);
                if (CollectionUtils.isEmpty(newKeyList)) {
                    return;
                }
                Map<T, R> invokeRes = func.apply(newKeyList);
                refreshList(category, folder, invokeRes, logicalExpireSeconds, physicalExpireSeconds);
            } catch (Exception e) {
                LOGGER.error("异步刷新多个redis缓存出现异常", e);
            } finally {
                if (CollectionUtils.isNotEmpty(activeKeyList)) {
                    redis.multiDelete(activeKeyList);
                }
            }
        });
    }

    private <T> void processSetNX(String category,
                                  String folder,
                                  List<T> keyList,
                                  List<T> newKeyList,
                                  List<StoreKey> activeKeyList
    ) {
        if (CollectionUtils.isEmpty(keyList)) {
            return;
        }
        for (T key : keyList) {
            StoreKey storeKey = new StoreKey(category, getNXKey(folder, key));
            Boolean setnx = redis.setnx(storeKey, NX, 10);
            if (Objects.equals(Boolean.TRUE, setnx)) {
                activeKeyList.add(storeKey);
                newKeyList.add(key);
            }
        }
    }

    private <T, R> List<KeyValueObject<T, R>> mgetInner(String category, String folder, List<T> keyList) {
        if (CollectionUtils.isEmpty(keyList)) {
            return Lists.newLinkedList();
        }
        try {
            List<StoreKey> storeKeyList = getStoreKeyList(category, folder, keyList);
            Map<StoreKey, String> resultMap = redis.multiGet(storeKeyList);
            Collection<String> bodyList = resultMap.values();
            List<KeyValueObject<T, R>> result = Lists.newLinkedList();
            for (String body : bodyList) {
                if (StringUtils.isNotBlank(body)) {
                    KeyValueObject<T, R> t = JSON.parseObject(body, new TypeReference<KeyValueObject<T, R>>() {
                    });
                    result.add(t);
                }
            }
            return result;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private <T, R> void refreshList(String category, String folder, Map<T, R> keyValues, int logicalExpireSeconds, int physicalExpireSeconds) {
        if (MapUtils.isEmpty(keyValues)) {
            return;
        }
        Map<T, KeyValueObject<T, R>> keyValueMap = new LinkedHashMap<>();
        for (Map.Entry<T, R> entry : keyValues.entrySet()) {
            KeyValueObject<T, R> keyValueObject = new KeyValueObject<>();
            keyValueObject.setKey(entry.getKey());
            keyValueObject.setLogicalExpireSeconds(logicalExpireSeconds);
            keyValueObject.setValue(entry.getValue());
            keyValueObject.setTag(System.currentTimeMillis());
            if (entry.getKey() != null) {
                keyValueObject.setObjClassName(entry.getKey().getClass().getName());
            }
            if (entry.getValue() != null) {
                keyValueObject.setObjClassName(entry.getValue().getClass().getName());
            }
            keyValueMap.put(entry.getKey(), keyValueObject);
        }
        if (MapUtils.isNotEmpty(keyValueMap)) {
            msetInner(category, folder, keyValueMap, physicalExpireSeconds);
        }
    }

    private <T, R> void msetInner(String category, String folder, Map<T, R> keyValue, int expireInSeconds) {
        Map<StoreKey, String> cacheMap = Maps.newLinkedHashMap();
        for (Map.Entry<T, R> entry : keyValue.entrySet()) {
            StoreKey key = new StoreKey(category, getRealKey(folder, entry.getKey()));
            cacheMap.put(key, JSON.toJSONString(entry.getValue(), SerializerFeature.WriteClassName, SerializerFeature.DisableCircularReferenceDetect));
        }
        Set<StoreKey> keyList = cacheMap.keySet();
        DataUtil.partitionInvoke(keyList, (thisList) -> {
            Map<StoreKey, String> innerCache = Maps.newLinkedHashMap();
            for (StoreKey storeKey : thisList) {
                innerCache.put(storeKey, cacheMap.get(storeKey));
            }
            redis.multiSet(innerCache, expireInSeconds);
            return null;
        }, 1000);
    }

    private <R> List<R> parseRes(Map<StoreKey, String> resultMap, Class<R> type) {
        if (MapUtils.isEmpty(resultMap)) {
            return Lists.newArrayList();
        }

        List<R> res = Lists.newArrayList();
        Collection<String> values = resultMap.values();
        for (String value : values) {
            R t = JSON.parseObject(value, type);
            if (t != null) {
                res.add(t);
            }
        }
        return res;
    }

    private <T, R> Map<T, R> parseResMap(Map<StoreKey, String> resultMap, Map<StoreKey, T> keyTMap, Class<R> type) {
        if (MapUtils.isEmpty(resultMap)) {
            return Maps.newHashMap();
        }

        Map<T, R> res = Maps.newHashMap();
        for (Map.Entry<StoreKey, String> entry : resultMap.entrySet()) {
            String value = entry.getValue();
            if (StringUtils.isNotEmpty(value)) {
                T key = keyTMap.get(entry.getKey());
                R t = JSON.parseObject(value, type);
                if (t != null) {
                    res.put(key, t);
                }
            }
        }
        return res;
    }

    private <T> List<StoreKey> getStoreKeyList(String category, String folder, List<T> keys) {
        if (CollectionUtils.isEmpty(keys)) {
            return Lists.newLinkedList();
        }
        List<StoreKey> keyList = Lists.newLinkedList();
        for (T key : keys) {
            keyList.add(new StoreKey(category, getRealKey(folder, key)));
        }
        return keyList;
    }

    private <T> String getRealKey(String folder, T key) {
        String s = key.toString();
        return folder != null
                ? String.format("%s/%s", folder, s)
                : s;
    }

    private <T> String getNXKey(String folder, T key2) {
        String s2 = key2.toString();
        return folder != null
                ? String.format("%s/%s/%s", folder, KEY_VALUE_MULTI_REFRESH, s2)
                : String.format("%s/%s", KEY_VALUE_MULTI_REFRESH, s2);
    }

    // endregion

}
