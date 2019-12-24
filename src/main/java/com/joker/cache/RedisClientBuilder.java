package com.joker.cache;

import java.util.Set;

public class RedisClientBuilder {

    private String clusterName;

    private String database;

    protected String routerType;

    // default use prototype
    protected String serializeType = null;

    protected String groupStrategy;

    protected int readTimeout;

    protected int connTimeout;

    protected int poolMaxTotal;

    protected int poolMaxIdle;

    protected int poolMinIdle;

    protected int poolWaitMillis;

    protected int poolIdleEvictTime;

    protected Boolean poolAsyncCreate;

    protected int asyncCoreSize;

    protected int asyncMaxSize;

    protected int asyncQueueSize;

    int multiCoreSize;

    int multiMaxSize;

    int multiQueueSize;

    int multiKeyTimeout;

    Boolean idcSensitive;

    Boolean regionSensitive;

    private Boolean dsMonitorable;

    private String autoAdjustThreshold;

    private Boolean autoAdjustable;

    private Set<String> backupCluster;

    protected Set<String> backupCategory;

    protected boolean categoryFullBackup;

    protected boolean asyncBackup;

    protected int asyncBackupPoolCoreMaxSize;

    protected int asyncBackupQueueSize;

    private boolean useSetModel = true;

    protected boolean useMtrace = true;

    public RedisClientBuilder() {
    }

    public RedisClientBuilder(String clusterName) {
        this.clusterName = clusterName;
    }

    /**
     * RedisStoreClient
     */
    public RedisStoreClient build() {
        if (clusterName != null) {
            database = clusterName;
        }

        return null;
    }

    /**
     * 设置路由策略
     * {@code master-only}(default) , {@code master-slave}, {@code slave-only}
     *
     * @param routerType 路由策略
     */
    public RedisClientBuilder routerType(String routerType) {
        this.routerType = routerType;
        return this;
    }

    /**
     * 设置序列化类型
     * 一旦设置,必须保证其他的客户端保持一致.
     *
     * @param serializeType
     */
    public RedisClientBuilder serializeType(String serializeType) {
        this.serializeType = serializeType;
        return this;
    }

    /**
     * 设置超时时间
     *
     * @param readTimeout 超时时间, 单位 ms {@code default : 1000 }
     */
    public RedisClientBuilder readTimeout(int readTimeout) {
        this.readTimeout = readTimeout;
        return this;
    }

    /**
     * 设置建立连接超时的时间
     *
     * @param connTimeout 建立连接时间 单位 ms {@code default : 2000 }
     */
    public RedisClientBuilder connTimeout(int connTimeout) {
        this.connTimeout = connTimeout;
        return this;
    }

    /**
     * 设置连接池最大连接数
     *
     * @param poolMaxTotal 最大连接数 {@code default : 50 }
     */
    public RedisClientBuilder poolMaxTotal(int poolMaxTotal) {
        this.poolMaxTotal = poolMaxTotal;
        return this;
    }

    /**
     * 设置连接池最大空闲连接数
     *
     * @param poolMaxIdle 最大空闲连接数 {@code default : 30 }
     */
    public RedisClientBuilder poolMaxIdle(int poolMaxIdle) {
        this.poolMaxIdle = poolMaxIdle;
        return this;
    }

    /**
     * 设置连接池最小空闲连接数,即连接池中会一直持有的最小连接数
     *
     * @param poolMinIdle 最小空闲连接数 {@code default : 3 }
     */
    public RedisClientBuilder poolMinIdle(int poolMinIdle) {
        this.poolMinIdle = poolMinIdle;
        return this;
    }

    /**
     * 设置获取连接的最长等待时间
     *
     * @param poolWaitMillis 获取连接的最长等待时间 {@code default : 500 }
     */
    public RedisClientBuilder poolWaitMillis(int poolWaitMillis) {
        this.poolWaitMillis = poolWaitMillis;
        return this;
    }

    public RedisClientBuilder poolAsyncCreate(boolean poolAsyncCreate) {
        this.poolAsyncCreate = poolAsyncCreate;
        return this;
    }

    /**
     * 一根空闲链接最大的空闲时间， 超过这个空闲时间，会被销毁重建。 且如果池子中的链接都是空闲链接，那么minIdle会被缩小
     *
     * @param poolIdleEvictTime 一根空闲链接最大的空闲时间，
     */
    public RedisClientBuilder poolIdleEvictTime(int poolIdleEvictTime) {
        this.poolIdleEvictTime = poolIdleEvictTime;
        return this;
    }

    /**
     * 异步操作线程池 coreSize
     *
     * @param asyncCoreSize {@code default : 16 }
     */
    public RedisClientBuilder asyncCoreSize(int asyncCoreSize) {
        this.asyncCoreSize = asyncCoreSize;
        return this;
    }

    /**
     * 异步操作线程池 maxSize
     *
     * @param asyncMaxSize {@code default : 64 }
     */
    public RedisClientBuilder asyncMaxSize(int asyncMaxSize) {
        this.asyncMaxSize = asyncMaxSize;
        return this;
    }

    /**
     * 异步操作线程池 queueSize
     *
     * @param asyncQueueSize {@code default : 1000 }
     */
    public RedisClientBuilder asyncQueueSize(int asyncQueueSize) {
        this.asyncQueueSize = asyncQueueSize;
        return this;
    }

    /**
     * multi异步操作线程池 coreSize
     *
     * @param multiCoreSize {@code default : 16 }
     */
    public RedisClientBuilder multiCoreSize(int multiCoreSize) {
        this.multiCoreSize = multiCoreSize;
        return this;
    }

    /**
     * multi异步操作线程池 maxSize
     *
     * @param multiMaxSize {@code default : 64 }
     */
    public RedisClientBuilder multiMaxSize(int multiMaxSize) {
        this.multiMaxSize = multiMaxSize;
        return this;
    }

    /**
     * multi异步操作线程池 queueSize
     *
     * @param multiQueueSize {@code default : 1000 }
     */
    public RedisClientBuilder multiQueueSize(int multiQueueSize) {
        this.multiQueueSize = multiQueueSize;
        return this;
    }

    /**
     * multi操作的超时时间
     *
     * @param multiKeyTimeout {@code default : 100 }
     */
    public RedisClientBuilder multiKeyTimeout(int multiKeyTimeout) {
        this.multiKeyTimeout = multiKeyTimeout;
        return this;
    }

    /**
     * 是否同机房敏感
     *
     * @param idcSensitive {@code default : true}
     */
    public RedisClientBuilder idcSensitive(boolean idcSensitive) {
        this.idcSensitive = idcSensitive;
        return this;
    }

    /**
     * 是否Region敏感, 一个Region包含多个机房。  注意： 如果机房敏感，默认Region敏感
     *
     * @param regionSensitive {@code default : true}
     */
    public RedisClientBuilder regionSensitive(boolean regionSensitive) {
        this.regionSensitive = regionSensitive;
        return this;
    }

    /**
     * 是否开启客户端到服务端的Cat监控，开启监控会有一定的性能损失（客户端QPS不大的可忽略）
     *
     * @param dsMonitorable {@code default : false}
     */
    public RedisClientBuilder dsMonitorable(boolean dsMonitorable) {
        this.dsMonitorable = dsMonitorable;
        return this;
    }

    /**
     * 是否开启超时节点自动降级功能
     *
     * @param timeoutRejectable 开关
     */
    @Deprecated
    public RedisClientBuilder timeoutRejectable(boolean timeoutRejectable) {
        this.autoAdjustable = timeoutRejectable;
        return this;
    }

    /**
     * 是否开启超时节点自动降级功能
     *
     * @param autoAdjustable 开关
     */
    public RedisClientBuilder autoAdjustable(boolean autoAdjustable) {
        this.autoAdjustable = autoAdjustable;
        return this;
    }

    /**
     * 单节点超时超过一定阈值后，客户端对该节点自动降级（一分钟后恢复）。可以应对服务端突然的抖动，减少报错
     * 该值的设置需要综合考虑日常的节点QPS等因素，建议有需求的联系DBA设置
     *
     * @param rejectThreshold 报错超过的阈值
     */
    @Deprecated
    public RedisClientBuilder rejectThreshold(int rejectThreshold) {
        return this;
    }

    @Deprecated
    public RedisClientBuilder multiClusterStrategy(String multiClusterStrategy) {
        this.groupStrategy = multiClusterStrategy;
        return this;
    }

    public RedisClientBuilder asyncBackup() {
        this.asyncBackup = true;
        return this;
    }

    public RedisClientBuilder asyncBackupPoolMaxSize(int asyncBackupPoolCoreMaxSize) {
        this.asyncBackupPoolCoreMaxSize = asyncBackupPoolCoreMaxSize;
        return this;
    }

    public RedisClientBuilder asyncBackupQueueSize(int asyncBackupQueueSize) {
        this.asyncBackupQueueSize = asyncBackupQueueSize;
        return this;
    }

    public RedisClientBuilder useSetModel(boolean useSetModel) {
        this.useSetModel = useSetModel;
        return this;
    }

    public RedisClientBuilder groupStrategy(String groupStrategy) {
        this.groupStrategy = groupStrategy;
        return this;
    }

    /**
     * 是否接入Mtrace上报参数，开启后会有一定的性能损失
     *
     * @param useMtrace {@code default : false}
     */
    public RedisClientBuilder useMtrace(boolean useMtrace) {
        this.useMtrace = useMtrace;
        return this;
    }
}
