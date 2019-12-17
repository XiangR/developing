package com.joker.cache;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import com.joker.utils.DataUtil;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 缓存公共组件
 * <p>
 * Create Author : xiangrui / 2019-12-5
 */
@Component
public class CacheComponent {

    // region 属性

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

    @Autowired
    private RedisStoreClient storeClient;

    // endregion

    // region 公共方法

    public <T> void del(String category, String folder, List<T> keyList) {
        if (CollectionUtils.isEmpty(keyList)) {
            return;
        }
        for (T key : keyList) {
            storeClient.delete(new StoreKey(category, getRealKey(folder, key)));
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
            storeClient.multiSet(innerCache, expireInSeconds);
            return null;
        }, 1000);
    }

    public <T, R> List<R> mget(String category, String folder, List<T> keys, Class<R> type) {
        if (CollectionUtils.isEmpty(keys)) {
            return Lists.newArrayList();
        }
        List<StoreKey> keyList = Lists.newArrayList();
        for (T key : keys) {
            keyList.add(new StoreKey(category, getRealKey(folder, key)));
        }
        return mget(keyList, type);
    }

    /**
     * mget
     * 支持查询缓存，支持缓存未命中自定义查询策略，并将数据刷新到缓存中
     * 刷新缓存的过程是异步的
     *
     * @param category        缓存类别
     * @param folder          缓存前缀
     * @param keys            缓存 key
     * @param type            T
     * @param func            key 未命中时的 refresh 策略
     * @param expireInSeconds 过期时间
     */
    public <T, R> List<R> mget(String category, String folder, List<T> keys, Class<R> type, Function<List<T>, Map<T, R>> func, int expireInSeconds) {
        if (CollectionUtils.isEmpty(keys)) {
            return Lists.newArrayList();
        }
        List<StoreKey> keyList = Lists.newArrayList();
        Map<StoreKey, T> keyTMap = Maps.newHashMap();
        for (T key : keys) {
            StoreKey storeKey = new StoreKey(category, getRealKey(folder, key));
            keyList.add(storeKey);
            keyTMap.put(storeKey, key);
        }
        // 第一次查询
        Map<StoreKey, String> resultMap = storeClient.multiGet(keyList);
        List<R> finalRes = Lists.newArrayList();
        finalRes.addAll(parseRes(resultMap, type));
        Collection<StoreKey> noCacheKeyList = CollectionUtils.subtract(keyList, resultMap.keySet());
        // 第二段查询
        if (CollectionUtils.isNotEmpty(noCacheKeyList)) {
            List<T> noCacheRealKeys = noCacheKeyList.stream()
                    .map(keyTMap::get)
                    .filter(Objects::nonNull)
                    .collect(Collectors.toList());
            Map<T, R> applyRes = func.apply(noCacheRealKeys);
            Map<T, R> needCache = Maps.newHashMap();

            if (MapUtils.isNotEmpty(applyRes)) {
                for (Map.Entry<T, R> trEntry : applyRes.entrySet()) {
                    T key = trEntry.getKey();
                    R value = trEntry.getValue();
                    finalRes.add(value);
                    needCache.put(key, value);
                }
            }

            // 重新刷新缓存
            EXECUTOR.execute(() -> mset(category, folder, needCache, expireInSeconds));
        }
        return finalRes;
    }

    // endregion

    // region 私有方法

    private <R> List<R> mget(List<StoreKey> keyList, Class<R> type) {
        if (CollectionUtils.isEmpty(keyList)) {
            return Lists.newArrayList();
        }
        Map<StoreKey, String> resultMap = storeClient.multiGet(keyList);
        return parseRes(resultMap, type);
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

    private <T> String getRealKey(String folder, T key) {
        String string = key.toString();
        return folder != null ? folder + "/" + string : string;
    }

    // endregion

}
