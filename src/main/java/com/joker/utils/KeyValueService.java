package com.joker.utils;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.joker.utils.redis.JedisClientFactory;
import redis.clients.jedis.Jedis;

import java.lang.reflect.Type;
import java.util.List;
import java.util.function.Function;

/**
 * 基于Redis的键值对存储服务
 */
public class KeyValueService {


    // region 公有方法

    /**
     * 存储一个对象
     *
     * @param folder 文件夹名称
     * @param key    主键
     * @param value  对象
     * @param <T>
     */
    public <T> void set(String folder, String key, T value) {
        set(folder, key, JSON.toJSONString(value));
    }

    /**
     * 储存一个字符串内容
     *
     * @param folder 文件夹名称
     * @param key    主键
     * @param value  内容
     */
    public void set(String folder, String key, String value) {
        String realKey = getRealKey(folder, key);
        execute(x -> x.set(realKey, value));
//        LOGGER.info("set key: {}", realKey);
    }

    /**
     * 获取一个对象
     *
     * @param folder 文件夹名称
     * @param key    主键
     * @param clazz  对象类型
     * @param <T>
     * @return
     */
    public <T> T get(String folder, String key, Class<T> clazz) {
        String body = get(folder, key);
        return JSON.parseObject(body, clazz);
    }

    /**
     * 获取一个对象
     *
     * @param folder 文件夹名称
     * @param key    主键
     * @param type   对象类型
     * @param <T>
     * @return
     */
    public <T> T get(String folder, String key, Type type) {
        String body = get(folder, key);
        return JSON.parseObject(body, type);
    }

    /**
     * 获取一个字符串内容
     *
     * @param folder 文件夹名称
     * @param key    主键
     * @return
     */
    public String get(String folder, String key) {
        String realKey = getRealKey(folder, key);
        return execute(x -> x.get(realKey));
    }

    /**
     * 增加值
     *
     * @param folder 文件夹名称
     * @param key    主键
     * @param value  增加的值
     */
    public void increase(String folder, String key, int value) {
        String realKey = getRealKey(folder, key);
        execute(x -> x.incrBy(realKey, value));
//        LOGGER.info("increase key: {}, value: {}", realKey, value);
    }

    /**
     * 删除一个对象
     *
     * @param folder 文件夹名称
     * @param key    主键
     */
    public void delete(String folder, String key) {
        String realKey = getRealKey(folder, key);
        execute(x -> x.del(realKey));
    }

    /**
     * 设置过期超时时间
     *
     * @param folder  文件夹名称
     * @param key     主键
     * @param seconds 相对超时时间(秒)
     */
    public void expire(String folder, String key, int seconds) {
        String realKey = getRealKey(folder, key);
        execute(x -> x.expire(realKey, seconds));
//        LOGGER.info("set expire key: {}, seconds: {}", realKey, seconds);
    }

    /**
     * 单次获取多个缓存对象
     *
     * @param folder 文件夹名称
     * @param keys   主键集合
     * @param clazz  对象类型
     * @return
     */
    public <T> List<T> mget(String folder, List<String> keys, Class<T> clazz) {
        List<String> bodys = mget(folder, keys);
        List<T> result = Lists.newArrayList();
        for (String body : bodys) {
            T t = JSON.parseObject(body, clazz);
            result.add(t);
        }
        return result;
    }


    /**
     * 获取多个字符串内容
     *
     * @param folder 文件夹名称
     * @param keys   主键集合
     */
    public List<String> mget(String folder, List<String> keys) {
        List<String> realKeys = Lists.newArrayList();
        for (String key : keys) {
            String realKey = getRealKey(folder, key);
            realKeys.add(realKey);
        }
        String[] keyArrays = realKeys.toArray(new String[realKeys.size()]);
        List<String> result = execute(x -> x.mget(keyArrays));
        return result;
    }

    // endregion

    // region 私有方法

    private <T> T execute(Function<Jedis, T> func) {
        try (Jedis jedis = JedisClientFactory.getJedisClient()) {
            return func.apply(jedis);
        }
    }

    private String getRealKey(String folder, String key) {
        return folder != null ? folder + "/" + key : key;
    }

    // endregion
}