package com.joker.utils.redis;

//@DisconfFile(filename = "com.yit.common.utils.redis.JedisConfig.properties")
public class JedisConfig {
    // Redis配置
    private static String redisHost;
    private static int redisPort;
    private static String redisPassword;
    private static int redisDatabase;

    //    @DisconfFileItem(name = "redis.host", associateField = "redisHost")
    public static String getRedisHost() {
        return redisHost;
    }

    //    @DisconfFileItem(name = "redis.port", associateField = "redisPort")
    public static int getRedisPort() {
        return redisPort;
    }

    //    @DisconfFileItem(name = "redis.password", associateField = "redisPassword")
    public static String getRedisPassword() {
        return redisPassword;
    }

    //    @DisconfFileItem(name = "redis.database", associateField = "redisDatabase")
    public static int getRedisDatabase() {
        return redisDatabase;
    }

    // 短消息服务Redis配置
    private static String smsRHost;
    private static int smsRPort;
    private static String smsRPassword;
    private static int smsRDatabase;

    //    @DisconfFileItem(name = "redis.smsRHost", associateField = "smsRHost")
    public static String getSmsRHost() {
        return smsRHost;
    }

    //    @DisconfFileItem(name = "redis.smsRPort", associateField = "smsRPort")
    public static int getSmsRPort() {
        return smsRPort;
    }

    //    @DisconfFileItem(name = "redis.smsRPassword", associateField = "smsRPassword")
    public static String getSmsRPassword() {
        return smsRPassword;
    }

    //    @DisconfFileItem(name = "redis.smsRDatabase", associateField = "smsRDatabase")
    public static int getSmsRDatabase() {
        return smsRDatabase;
    }

    public JedisConfig() {
//        DisconfClient.register(this);
    }
}
