package com.joker.jvm.classloading;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by xiangrui on 2019-10-08.
 *
 * @author xiangrui
 * @date 2019-10-08
 */
public class SingletonTest {

    public static void main(String[] args) {

        ExecutorService executorService = Executors.newFixedThreadPool(20);
        for (int i = 0; i < 20; i++) {
            executorService.execute(() -> {
                System.out.println("Thread running_" + Thread.currentThread());
                Object load = SingletonHolder.load();
            });
        }
        executorService.shutdown();

    }
}
