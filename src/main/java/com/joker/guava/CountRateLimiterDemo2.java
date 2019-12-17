package com.joker.guava;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

/**
 * Created by xiangrui on 2019-10-07.
 *
 * @author xiangrui
 * @date 2019-10-07
 */
public class CountRateLimiterDemo2 {

    private static Semaphore semphore = new Semaphore(5);

    public static void exec() {
        if (semphore.getQueueLength() > 100) {
            System.out.println("当前等待排队的任务数大于100，请稍候再试...");
        }
        try {
            semphore.acquire();
            System.out.println("--" + System.currentTimeMillis() / 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            semphore.release();
        }
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
