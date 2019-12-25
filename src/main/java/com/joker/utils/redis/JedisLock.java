package com.joker.utils.redis;

import com.joker.entity.Func1;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;

import java.util.concurrent.TimeUnit;

/**
 * 使用redis作为分布式锁实现，分布式，不可重入
 * 不要多线程共享同一个分布式锁，多线程共享同一个分布式锁反而会失去锁"分布式"的特性
 * 使用示例:
 * <pre> {@code
 *     DefaultJedisLockFactory jedisLockFactory = new DefaultJedisLockFactory("common", "default");
 *     JedisLock jedisLock = jedisLockFactory.buildLock(1);
 *     if (jedisLock.tryLock()) {
 *         try {
 *              System.out.println("acquire lock success, do something...");
 *          } finally {
 *              //锁释放
 *              jedisLock.release();
 *          }
 *      } else {
 *          System.out.println("acquire lock failed");
 *      }
 *  }</pre>
 *
 * @author Alois Belaska <alois.belaska@gmail.com>
 * @author guankaiqiang 修改了锁释放逻辑 修改原acquire函数 支持0等
 * @author shixinxin 增加lockThenInvoke方法，可直接传入执行函数
 * @link https://github.com/abelaska/jedis-lock
 * Redis distributed lock implementation
 * (fork by  Bruno Bossola <bbossola@gmail.com>)
 */
public class JedisLock {
    /**
     * 默认过期时间
     */
    public static final int DEFAULT_EXPIRY_TIME_MILLIS = Integer.getInteger("com.yit.common.jedis.lock.expiry.millis",
            60 * 1000);
    /**
     * 默认重新申请锁的重试等待时间
     */
    public static final int DEFAULT_ACQUIRY_RESOLUTION_MILLIS = Integer.getInteger(
            "com.yit.common.jedis.lock.acquiry.resolution.millis", 100);
    /**
     * 分布式锁使用的redis锁命名空间
     */
    private static final String LOCK_NAMESAPACE = "_LOCK";
    private static final Logger logger = LoggerFactory.getLogger(JedisLock.class);
    private final String lockKeyPath;
    private final int lockExpiryInMillis;
    private boolean locked = false;

    public int getLockExpiryInMillis() {
        return lockExpiryInMillis;
    }

    /**
     * Detailed constructor with default lock expiration of 60000 msecs.
     *
     * @param namespace 命名空间
     * @param lockKey   lock key (ex. account:1, ...)
     */
    public JedisLock(String namespace, String lockKey) {
        this(namespace, lockKey, DEFAULT_EXPIRY_TIME_MILLIS);
    }

    /**
     * Detailed constructor.
     *
     * @param namespace        命名空间
     * @param lockKey          lock key (ex. account:1, ...)
     * @param expiryTimeMillis lock expiration in miliseconds (default: 60000 msecs)
     */
    public JedisLock(String namespace, String lockKey, int expiryTimeMillis) {
        this.lockKeyPath = namespace + "/" + LOCK_NAMESAPACE + "/" + lockKey;
        this.lockExpiryInMillis = expiryTimeMillis;
    }

    /**
     * @return lock key path
     */
    public String getLockKeyPath() {
        return lockKeyPath;
    }

    /**
     * 0等，上锁失败立即返回false，不可重入
     * 分布式:依赖redis setnx原子操作
     * Acquire lock.
     * 注意: 如果争用到锁却不调用release函数（分布式锁自动失效），需要注意调用closeJedis，释放jedis实例
     *
     * @return true if lock is acquired, false acquire timeouted
     */
    public boolean tryLock() {
        return tryLock(0, TimeUnit.MILLISECONDS);
    }

    /**
     * 超时后返回false，不可重入
     * 分布式:依赖redis setnx原子操作
     * Acquire lock.
     * 注意: 如果争用到锁却不调用release函数（分布式锁自动失效），需要注意调用closeJedis，释放jedis实例
     *
     * @param timeout 锁超时时间
     * @param unit    时间单位
     * @return true if lock is acquired, false acquire timeouted
     */
    public synchronized boolean tryLock(int timeout, TimeUnit unit) {
        Jedis jedis = null;
        try {
            jedis = JedisClientFactory.getJedisClient();
            long timeoutMillis = unit.toMillis(timeout);
            while (timeoutMillis >= 0) {
                //NX -- Only set the key if it does not already exist.
                //XX -- Only set the key if it already exist.
                //EX seconds -- Set the specified expire time, in seconds.
                //PX milliseconds -- Set the specified expire time, in milliseconds.
                String result = jedis.set(lockKeyPath, "1", "NX", "PX",
                        lockExpiryInMillis);
                if ("OK".equalsIgnoreCase(result)) {
                    locked = true;
                    break;
                }
                //超时时间递减
                timeoutMillis -= DEFAULT_ACQUIRY_RESOLUTION_MILLIS;
                if (timeoutMillis > 0) {
                    Thread.sleep(DEFAULT_ACQUIRY_RESOLUTION_MILLIS);
                }
            }
        } catch (Exception e) {
            logger.error("{} trylock failed!", lockKeyPath, e);
        } finally {
            //及时释放jedis链接
            JedisLock.forceClose(jedis);
        }
        return locked;
    }

    /**
     * 带执行函数的redis分布式锁
     *
     * @param namespace        命名空间
     * @param key              lock key (ex. account:1, ...)
     * @param timeoutMillis    阻塞等待拿到锁超时时间(单位：miliseconds)
     * @param expiryTimeMillis 缓存key失效时间(单位：miliseconds)
     * @param function         锁需要执行的函数
     * @return T
     * @author sxx
     * @date 2018-08-21 10:34
     * @note sxx@2018-08-21 10:34创建
     */
    public static <T> T lockThenInvoke(String namespace, String key, int timeoutMillis, int expiryTimeMillis, Func1<T> function) throws Exception {
        JedisLock lock = new JedisLock(namespace, key, expiryTimeMillis);
        T result = null;
        if (lock.tryLock(timeoutMillis, TimeUnit.MILLISECONDS)) {
            try {
                result = function.invoke();
            } catch (Exception e) {
                logger.error("jedislock function invoke error", e);
                // 业务异常由业务自行捕获处理
                throw e;
            } finally {
                lock.release();
            }
        }
        return result;
    }

    /**
     * 带执行函数的redis分布式锁
     * 阻塞等待拿到锁，timeout 0，拿不到锁立即返回
     * 锁key失效时间默认(60000 msecs)
     *
     * @param namespace 命名空间
     * @param key       lock key (ex. account:1, ...)
     * @param function  锁需要执行的函数
     * @return T
     * @author sxx
     * @date 2018-08-21 10:34
     * @note sxx@2018-08-21 10:34创建
     */
    public static <T> T lockThenInvoke(String namespace, String key, Func1<T> function) throws Exception {
        return JedisLock.lockThenInvoke(namespace, key, 0, DEFAULT_EXPIRY_TIME_MILLIS, function);
    }

    /**
     * 带执行函数的redis分布式锁
     * 阻塞等待拿到锁，timeoutMillis为阻塞等待时间
     * 锁key失效时间默认(60000 msecs)
     *
     * @param namespace     命名空间
     * @param timeoutMillis 获取锁等待的超时时间
     * @param key           lock key (ex. account:1, ...)
     * @param function      锁需要执行的函数
     * @return T
     * @author sxx
     * @date 2018-08-21 10:34
     * @note sxx@2018-08-21 10:34创建
     */
    public static <T> T lockThenInvoke(String namespace, String key, int timeoutMillis, Func1<T> function) throws Exception {
        return JedisLock.lockThenInvoke(namespace, key, timeoutMillis, DEFAULT_EXPIRY_TIME_MILLIS, function);
    }

    /**
     * 释放锁
     * 注意：release已经隐含了forceClose动作，请勿在调用release之前进行jedis实例释放
     * Acquired lock release.
     */
    public synchronized void release() {
        Jedis jedis = null;
        try {
            jedis = JedisClientFactory.getJedisClient();
            jedis.del(lockKeyPath);
        } finally {
            locked = false;
            JedisLock.forceClose(jedis);
        }
    }

    /**
     * 释放jedis链接,内部创建的jedis实例需要触发释放
     * 注意：release已经隐含了forceClose动作，请勿在调用release之前进行jedis实例释放
     *
     * @note 不再开放forceClose的方法，如需要释放资源请调用
     * @since 2.38.2
     */
    private static void forceClose(Jedis jedis) {
        if (jedis != null) {
            try {
                jedis.close();
            } catch (Exception e) {
                logger.error("insure jedis is closed!", e);
            }
        } else {
            logger.info("jedis is closed!");
        }
    }

    /**
     * 是否已被锁
     * Check if owns the lock
     *
     * @return true if lock owned
     */
    public boolean isLocked() {
        return locked;
    }
}