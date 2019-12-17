package com.joker.cache;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import com.joker.utils.DataUtil;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 基于Redis的键值对存储服务
 */
@Component
public class KeyValueService {

    // region 属性

    @Autowired
    private RedisStoreClient storeClient;

    private static final Logger LOGGER = LoggerFactory.getLogger(KeyValueService.class);

    private static final Object PRESENT = new Object();

    private static final String KEY_VALUE_MULTI_REFRESH = "KeyValueMultiRefresh";

    /**
     * 任务队列
     */
    private static final LinkedBlockingQueue<Runnable> REFRESH_WORKING_QUEUE = new LinkedBlockingQueue<>(1024);

    /**
     * 异步线程池
     */
    private static final ThreadPoolExecutor EXECUTOR =
            new ThreadPoolExecutor(20, 20, 30, TimeUnit.SECONDS, REFRESH_WORKING_QUEUE, new ThreadFactoryBuilder().build(),
                    (r, executor) -> {
                        try {
                            executor.getQueue().put(r);
                        } catch (InterruptedException e) {
                            Thread.currentThread().interrupt();
                        }
                    }
            );

    // endregion

    // region 共有方法

    /**
     * mget
     * <p>
     * 1：logicalExpireSeconds 后的查询会触发缓存的刷新，这个过程会加上分布式锁，默认锁10s，会在 finally 中 del key
     * 2：physicalExpireSeconds 时间为 redis.expire，理论上 logicalExpireSeconds < physicalExpireSeconds
     *
     * @param category              类别
     * @param folder                前缀
     * @param paramList             keyList
     * @param logicalExpireSeconds  逻辑过期时间，开始刷新缓存的时间
     * @param physicalExpireSeconds 物理过期时间，缓存真实失效的时间
     * @param cacheNullable         redis 与 function 都未命中的 key 是否刷新为 null
     * @param func                  未命中缓存 key 的刷新策略
     * @return key -> cache obj
     */
    public <T> Map<String, T> mget(String category,
                                   String folder,
                                   List<String> paramList,
                                   int logicalExpireSeconds,
                                   int physicalExpireSeconds,
                                   boolean cacheNullable,
                                   Function<List<String>, Map<String, T>> func
    ) {
        if (CollectionUtils.isEmpty(paramList)) {
            return Maps.newLinkedHashMap();
        }
        List<String> keyList = paramList.stream().distinct().filter(Objects::nonNull).collect(Collectors.toList());
        if (CollectionUtils.isNotEmpty(keyList)) {
            return Maps.newLinkedHashMap();
        }
        // mget redis
        List<KeyValueObject<T>> firstRes = mgetInner(category, folder, keyList);
        // check logic expire or reload
        reloadCachedLogicExpire(category, folder, firstRes, logicalExpireSeconds, physicalExpireSeconds, func);

        Map<String, T> finalRes = Maps.newLinkedHashMap();
        finalRes.putAll(firstRes.stream().collect(Collectors.toMap(KeyValueObject::getKey, KeyValueObject::getObject, (v1, v2) -> v1)));

        // invoke and mset
        List<String> secondQueryKeyList = Lists.newArrayList(CollectionUtils.subtract(keyList,
                firstRes.stream().filter(Objects::nonNull).map(KeyValueObject::getKey).collect(Collectors.toList())
        ));
        if (CollectionUtils.isNotEmpty(secondQueryKeyList)) {
            Map<String, T> secondRes = invokeAndReload(category, folder, secondQueryKeyList, logicalExpireSeconds, physicalExpireSeconds, cacheNullable, func);
            finalRes.putAll(secondRes);
        }
        return finalRes;
    }

    // endregion

    // region 私有方法

    private <T> Map<String, T> invokeAndReload(String category,
                                               String folder,
                                               List<String> keyList,
                                               int logicalExpireSeconds,
                                               int physicalExpireSeconds,
                                               boolean cacheNullable,
                                               Function<List<String>, Map<String, T>> func
    ) {
        if (CollectionUtils.isEmpty(keyList)) {
            return Maps.newLinkedHashMap();
        }
        Map<String, T> invokeRes = func.apply(keyList);
        invokeRes = invokeRes == null ? Maps.newLinkedHashMap() : invokeRes;
        if (cacheNullable) {
            Collection<String> unCache = CollectionUtils.subtract(keyList, invokeRes.keySet());
            for (String key : unCache) {
                invokeRes.put(key, null);
            }
        }
        refreshList(category, folder, invokeRes, logicalExpireSeconds, physicalExpireSeconds);
        return invokeRes;
    }

    private <T> void reloadCachedLogicExpire(String category,
                                             String folder,
                                             List<KeyValueObject<T>> cachedList,
                                             int logicalExpireSeconds,
                                             int physicalExpireSeconds,
                                             Function<List<String>, Map<String, T>> func
    ) {
        if (CollectionUtils.isEmpty(cachedList)) {
            return;
        }
        // 筛选出逻辑失效的key
        List<String> logicExpireKeyList = Lists.newLinkedList();
        for (KeyValueObject<T> keyValueObject : cachedList) {
            if (keyValueObject.isLogicExpire()) {
                logicExpireKeyList.add(keyValueObject.getKey());
            }
        }
        if (CollectionUtils.isNotEmpty(logicExpireKeyList)) {
            reloadCacheAsync(category, folder, logicExpireKeyList, logicalExpireSeconds, physicalExpireSeconds, func);
        }
    }

    private <T> void reloadCacheAsync(String category,
                                      String folder,
                                      List<String> keyList,
                                      int logicalExpireSeconds,
                                      int physicalExpireSeconds,
                                      Function<List<String>, Map<String, T>> func
    ) {
        if (CollectionUtils.isEmpty(keyList)) {
            return;
        }
        EXECUTOR.execute(() -> {
            List<String> newKeyList = Lists.newArrayList();
            List<StoreKey> activeKeyList = Lists.newLinkedList();
            try {
                processSetNX(category, folder, keyList, newKeyList, activeKeyList);
                if (CollectionUtils.isEmpty(newKeyList)) {
                    return;
                }
                Map<String, T> invokeRes = func.apply(newKeyList);
                refreshList(category, folder, invokeRes, logicalExpireSeconds, physicalExpireSeconds);
            } catch (Exception e) {
                LOGGER.error("异步刷新多个redis缓存出现异常", e);
            } finally {
                if (CollectionUtils.isNotEmpty(activeKeyList)) {
                    storeClient.multiDelete(activeKeyList);
                }
            }
        });
    }

    private <T> void msetInner(String category, String folder, Map<String, T> keyValue, int expireInSeconds) {
        Map<StoreKey, String> cacheMap = Maps.newLinkedHashMap();
        for (Map.Entry<String, T> entry : keyValue.entrySet()) {
            StoreKey key = new StoreKey(category, getRealKey(folder, entry.getKey()));
            cacheMap.put(key, JSON.toJSONString(entry.getValue(), SerializerFeature.WriteClassName, SerializerFeature.DisableCircularReferenceDetect));
        }
        Set<StoreKey> keyList = cacheMap.keySet();
        DataUtil.partitionInvoke(keyList, (thisList) -> {
            Map<StoreKey, String> innerCache = Maps.newLinkedHashMap();
            for (StoreKey storeKey : thisList) {
                innerCache.put(storeKey, cacheMap.get(storeKey));
            }
            storeClient.multiSet(innerCache, expireInSeconds);
            return null;
        }, 1000);
    }

    private <T> List<KeyValueObject<T>> mgetInner(String category, String folder, List<String> keyList) {
        if (CollectionUtils.isEmpty(keyList)) {
            return Lists.newLinkedList();
        }
        try {
            List<String> bodyList = mget(category, folder, keyList);
            List<KeyValueObject<T>> result = Lists.newLinkedList();
            for (String body : bodyList) {
                if (StringUtils.isBlank(body)) {
                    result.add(null);
                } else {
                    KeyValueObject<T> t = JSON.parseObject(body, new TypeReference<KeyValueObject<T>>() {
                    });
                    result.add(t);
                }
            }
            return result;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 获取多个字符串内容
     *
     * @param folder 文件夹名称
     * @param keys   主键集合
     */
    private List<String> mget(String category, String folder, List<String> keys) {
        if (CollectionUtils.isEmpty(keys)) {
            return Lists.newLinkedList();
        }
        List<StoreKey> keyList = getStoreKeyList(category, folder, keys);

        return DataUtil.partitionInvoke(keyList, (thisList) -> {
            Map<StoreKey, String> resultMap = storeClient.multiGet(keyList);
            return Lists.newLinkedList(resultMap.values());
        }, 1000);
    }

    private <T> void refreshList(String category, String folder, Map<String, T> keyValues, int logicalExpireSeconds, int physicalExpireSeconds) {
        if (MapUtils.isEmpty(keyValues)) {
            return;
        }
        Map<String, KeyValueObject<T>> keyValueObjectMap = new LinkedHashMap<>();
        for (Map.Entry<String, T> entry : keyValues.entrySet()) {
            KeyValueObject<T> keyValueObject = new KeyValueObject<>();
            keyValueObject.setKey(entry.getKey());
            keyValueObject.setLogicalExpireSeconds(logicalExpireSeconds);
            keyValueObject.setObject(entry.getValue());
            keyValueObject.setTag(System.currentTimeMillis());
            if (entry.getValue() != null) {
                keyValueObject.setClassName(entry.getValue().getClass().getName());
            }
            keyValueObjectMap.put(entry.getKey(), keyValueObject);
        }
        if (MapUtils.isNotEmpty(keyValueObjectMap)) {
            msetInner(category, folder, keyValueObjectMap, physicalExpireSeconds);
        }
    }

    private void processSetNX(String category,
                              String folder,
                              List<String> keyList,
                              List<String> newKeyList,
                              List<StoreKey> activeKeyList
    ) {
        if (CollectionUtils.isEmpty(keyList)) {
            return;
        }
        for (String key : keyList) {
            StoreKey storeKey = new StoreKey(category, getNXKey(folder, key));
            Boolean setnx = storeClient.setnx(storeKey, PRESENT, 10);
            if (Objects.equals(Boolean.TRUE, setnx)) {
                activeKeyList.add(storeKey);
                newKeyList.add(key);
            }
        }
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
                ? String.format("%s/%s/%s", folder, KeyValueService.KEY_VALUE_MULTI_REFRESH, s2)
                : String.format("%s/%s", KeyValueService.KEY_VALUE_MULTI_REFRESH, s2);
    }

    // endregion
}
