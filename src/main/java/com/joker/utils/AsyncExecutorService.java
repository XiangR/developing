package com.joker.utils;

import com.google.common.util.concurrent.ThreadFactoryBuilder;

import com.joker.entity.FuncWithException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by zhaolulun on 2018/3/16.
 *
 * @author zhaolulun
 * @date 2018/03/16
 */
public class AsyncExecutorService {
    // region 配置
    /**
     * 日志操作
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(AsyncExecutorService.class);

    /**
     * 任务队列
     */
    private static final LinkedBlockingQueue<Runnable> REFRESH_WORKING_QUEUE = new LinkedBlockingQueue<>(1024);

    /**
     * 异步线程池
     */
    private static final ThreadPoolExecutor EXECUTOR =
        new ThreadPoolExecutor(20, 20, 30, TimeUnit.SECONDS, REFRESH_WORKING_QUEUE, new ThreadFactoryBuilder().build(), (r, executor) ->{
            try {
                executor.getQueue().put(r);
            } catch (InterruptedException e) {
                LOGGER.error("ThreadPoolExecutor has InterruptedException");
            }
        } );

    // endregion 

    // region 依赖
    // endregion 

    // region 公有方法

    public AsyncExecutorService() {
        // 优雅停机
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            LOGGER.info("停机信号收到, 优雅停机中");
            EXECUTOR.shutdown();
            try {
                EXECUTOR.awaitTermination(30, TimeUnit.SECONDS);
                LOGGER.info("优雅停机完毕");
            } catch (InterruptedException ex) {
            }
        }));

    }

    /**
     * 开始一个异步任务
     *
     * @return 任务ID
     */
    public void start(FuncWithException<Object> task) {
        EXECUTOR.execute(() -> {
            try {
                task.invoke();
            } catch (Exception e) {
                LOGGER.error("异步任务处理错误", e);
            }
        });
    }

    // endregion

    // region 私有方法
    // endregion
}
