package com.joker.utils.redis;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.Protocol;

/**
 * 获得一个jedis实例
 * <p>
 * 使用方法: try(Jedis jedis = JedisClientFactory.getJedisClient()) {
 * ...
 * }
 * (或者在finally里显示地调用jedis.close())
 */
public class JedisClientFactory {

//    private static final Logger logger = LoggerFactory.getLogger(JedisClientFactory.class);

    static {
        new JedisConfig();
    }

    public static Jedis getJedisClient() {
        return JedisPoolHolder.getJedisPool().getResource();
    }

    public static Jedis getSmsJedisClient() {
        return SmsJedisPoolHolder.getSmsJedisPool().getResource();
    }

    private static class JedisPoolHolder {
        private static JedisPool jedisPool;
        private static String redisHost;
        private static int redisPort;
        private static String redisPassword;
        private static int redisDatabase;

        private static void setRedisHost(String redisHost) {
            JedisPoolHolder.redisHost = redisHost;
        }

        private static void setRedisPort(int redisPort) {
            JedisPoolHolder.redisPort = redisPort;
        }

        private static void setRedisPassword(String redisPassword) {
            JedisPoolHolder.redisPassword = redisPassword;
        }

        private static void setRedisDatabase(int redisDatabase) {
            JedisPoolHolder.redisDatabase = redisDatabase;
        }

        private static void setJedisPool(String redisHost, int redisPort, String redisPassword, int redisDatabase) {
            JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
            jedisPoolConfig.setMaxIdle(10);
            jedisPoolConfig.setMaxTotal(50);
            jedisPoolConfig.setMaxWaitMillis(2000);
            jedisPoolConfig.setTestOnBorrow(false);
            jedisPoolConfig.setTestOnCreate(false);
            jedisPoolConfig.setTestOnReturn(false);
            jedisPoolConfig.setTimeBetweenEvictionRunsMillis(2000);
//            logger.info("init jedis Pool,jedisPoolConfig max:{},min:{},maxWait:{}",jedisPoolConfig.getMaxIdle(),jedisPoolConfig.getMinIdle(),jedisPoolConfig.getMaxWaitMillis());
            jedisPool = new JedisPool(jedisPoolConfig, redisHost, redisPort, Protocol.DEFAULT_TIMEOUT, redisPassword, redisDatabase);
        }

        static {
            setRedisHost(JedisConfig.getRedisHost());
            setRedisPort(JedisConfig.getRedisPort());
            if (null != JedisConfig.getRedisPassword() && !JedisConfig.getRedisPassword().isEmpty()) {
                setRedisPassword(JedisConfig.getRedisPassword());
            }
            setRedisDatabase(JedisConfig.getRedisDatabase());
            setJedisPool(redisHost, redisPort, redisPassword, redisDatabase);
        }

        public static JedisPool getJedisPool() {
            return jedisPool;
        }
    }

    private static class SmsJedisPoolHolder {
        private static JedisPool jedisPool;
        private static String redisHost;
        private static int redisPort;
        private static String redisPassword;
        private static int redisDatabase;

        private static void setRedisHost(String redisHost) {
            SmsJedisPoolHolder.redisHost = redisHost;
        }

        private static void setRedisPort(int redisPort) {
            SmsJedisPoolHolder.redisPort = redisPort;
        }

        private static void setRedisPassword(String redisPassword) {
            SmsJedisPoolHolder.redisPassword = redisPassword;
        }

        private static void setRedisDatabase(int redisDatabase) {
            SmsJedisPoolHolder.redisDatabase = redisDatabase;
        }

        private static void setJedisPool(String redisHost, int redisPort, String redisPassword, int redisDatabase) {
            jedisPool = new JedisPool(new JedisPoolConfig(), redisHost, redisPort, Protocol.DEFAULT_TIMEOUT, redisPassword, redisDatabase);
        }

        static {
            setRedisHost(JedisConfig.getSmsRHost());
            setRedisPort(JedisConfig.getSmsRPort());
            if (null != JedisConfig.getSmsRPassword() && !JedisConfig.getSmsRPassword().isEmpty()) {
                setRedisPassword(JedisConfig.getSmsRPassword());
            }
            setRedisDatabase(JedisConfig.getSmsRDatabase());
            setJedisPool(redisHost, redisPort, redisPassword, redisDatabase);
        }

        public static JedisPool getSmsJedisPool() {
            return jedisPool;
        }
    }
}
