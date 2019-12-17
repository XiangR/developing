package com.joker.utils.redis;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.joker.entity.Func;
import com.joker.model.ServiceRuntimeException;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * 分布式锁util
 */
@Component
public class JedisLockUtil {

    private final Logger LOGGER = LoggerFactory.getLogger(JedisLockUtil.class);

    @Resource
    private AbstractJedisLockFactory supplyChainJedisLockFactory;


    public <T> T tryLock(String key, Func func) {
        return tryLock(key, func, 0);
    }

    public <T> T tryLockOrWait(String key, Func func) {
        // lock 占不到锁到时候，等待 500 毫秒
        return tryLock(key, func, 500);
    }

    public <T> T tryLock(String key, Func func, int timeout) {
        if (StringUtils.isEmpty(key)) {
            throw new RuntimeException("创建分布式锁时未传入key");
        }
        JedisLock jedisLock = supplyChainJedisLockFactory.buildLock(key);
        if (jedisLock.tryLock(timeout, TimeUnit.MILLISECONDS)) {
            try {
                Object invoke = func.invoke();
                return (T) invoke;
            } finally {
                jedisLock.release();
            }
        } else {
            LOGGER.warn("存在并发操作!lockKey:{}", key);
            throw new ServiceRuntimeException("");
        }
    }

    public JedisLock tryLock(String key, int timeout) {
        if (StringUtils.isEmpty(key)) {
            throw new RuntimeException("创建分布式锁时未传入key");
        }
        JedisLock jedisLock = supplyChainJedisLockFactory.buildLock(key);
        if (jedisLock.tryLock(timeout, TimeUnit.MILLISECONDS)) {

            return jedisLock;
        } else {
            LOGGER.warn("存在并发操作!lockKey:{}", key);
            throw new ServiceRuntimeException("");
        }
    }

    public JedisLock tryLock(String key) {
        if (StringUtils.isEmpty(key)) {
            throw new RuntimeException("创建分布式锁时未传入key");
        }
        return tryLock(key, 0);
    }

    public <T> T batchTryLock(List<String> keyList, Func<T> func) {
        List<JedisLock> jedisLockList = new ArrayList<>();
        for (String key : keyList) {
            JedisLock jedisLock = supplyChainJedisLockFactory.buildLock(key);
            jedisLockList.add(jedisLock);
        }
        if (batchLock(jedisLockList)) {
            try {
                return func.invoke();
            } finally {
                batchRelease(jedisLockList);
            }
        } else {
            LOGGER.warn("存在并发操作!lockKey:{}", JSON.toJSON(keyList));
            throw new ServiceRuntimeException("");
        }
    }

    public boolean batchLock(List<JedisLock> jedisLockList) {
        boolean tryLock = true;
        List<JedisLock> existJedisLockList = Lists.newArrayList();
        for (JedisLock jedisLock : jedisLockList) {
            boolean flag = jedisLock.tryLock();
            tryLock = tryLock && flag;
            if (flag) {
                existJedisLockList.add(jedisLock);
            }
        }
        if (!tryLock) {
            batchRelease(existJedisLockList);
        }
        return tryLock;
    }

    public void batchRelease(List<JedisLock> jedisLockList) {
        if (CollectionUtils.isEmpty(jedisLockList)) {
            return;
        }
        for (JedisLock jedisLock : jedisLockList) {
            jedisLock.release();
        }
    }
}
