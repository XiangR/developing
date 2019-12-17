package com.joker.utils.redis;

import com.alibaba.fastjson.JSON;

import java.util.function.Function;

/**
 * Created by xiangrui on 2019-07-15.
 *
 * @author xiangrui
 * @date 2019-07-15
 */
public class AbstractJedisLockFactory {
    protected String namespace;
    protected String keyPrefix;
    protected int expiryTimeMillis;
    public static final String LOCK_KEY_SPLITOR = "/";

    protected AbstractJedisLockFactory(String namespace, String keyPrefix) {
        this(namespace, keyPrefix, JedisLock.DEFAULT_EXPIRY_TIME_MILLIS);
    }

    protected AbstractJedisLockFactory(String namespace, String keyPrefix, int expiryTimeMillis) {
        this.namespace = namespace;
        this.keyPrefix = keyPrefix;
        this.expiryTimeMillis = expiryTimeMillis;
    }

    public JedisLock buildLock(Function<Object[], String> func, Object... params) {
        return new JedisLock(this.namespace, this.lockKeyPostProcess((String)func.apply(params)), this.expiryTimeMillis);
    }

    public JedisLock buildLock(Object... params) {
        String identity = params != null ? (params.length == 1 ? JSON.toJSONString(params[0]) : JSON.toJSONString(params)) : "";
        return new JedisLock(this.namespace, this.lockKeyPostProcess(identity), this.expiryTimeMillis);
    }

    public JedisLock buildLock(int param) {
        return new JedisLock(this.namespace, this.lockKeyPostProcess(String.valueOf(param)), this.expiryTimeMillis);
    }

    public JedisLock buildLock(boolean param) {
        return new JedisLock(this.namespace, this.lockKeyPostProcess(String.valueOf(param)), this.expiryTimeMillis);
    }

    public JedisLock buildLock(double param) {
        return new JedisLock(this.namespace, this.lockKeyPostProcess(String.valueOf(param)), this.expiryTimeMillis);
    }

    public JedisLock buildLock(float param) {
        return new JedisLock(this.namespace, this.lockKeyPostProcess(String.valueOf(param)), this.expiryTimeMillis);
    }

    public JedisLock buildLock(short param) {
        return new JedisLock(this.namespace, this.lockKeyPostProcess(String.valueOf(param)), this.expiryTimeMillis);
    }

    public JedisLock buildLock(long param) {
        return new JedisLock(this.namespace, this.lockKeyPostProcess(String.valueOf(param)), this.expiryTimeMillis);
    }

    public JedisLock buildLock(byte param) {
        return new JedisLock(this.namespace, this.lockKeyPostProcess(String.valueOf(param)), this.expiryTimeMillis);
    }

    public JedisLock buildLock(char param) {
        return new JedisLock(this.namespace, this.lockKeyPostProcess(String.valueOf(param)), this.expiryTimeMillis);
    }

    protected String lockKeyPostProcess(String identify) {
        return this.keyPrefix + "/" + identify;
    }
}
