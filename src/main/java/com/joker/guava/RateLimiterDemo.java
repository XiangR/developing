package com.joker.guava;

import com.google.common.util.concurrent.RateLimiter;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

/**
 * 理解根据 RateLimiter 实现限流
 * <p>
 * Created by xiangrui on 2019-10-07.
 *
 * @author xiangrui
 * @date 2019-10-07
 */
public class RateLimiterDemo {

    private static RateLimiter limiter = RateLimiter.create(5);

    public static void exec() {

        // 可以达到限流但是等待的目的
        limiter.acquire();

        // 可以使用 tryAcquire 达到限流消封的目的
        // limiter.tryAcquire()

        System.out.println("--" + System.currentTimeMillis() / 1000);
    }

    public static void main(String[] args) throws InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(1);
        List<Callable<String>> list = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            list.add(() -> {
                exec();
                return "SUCCESS";
            });
        }
        long l = System.currentTimeMillis();
        List<Future<String>> futures = executorService.invokeAll(list);
        long l2 = System.currentTimeMillis();
        System.out.println(l2 - l);

        executorService.shutdown();
    }
}
