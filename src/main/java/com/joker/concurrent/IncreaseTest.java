package com.joker.concurrent;

import com.google.common.collect.Lists;

import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by xiangrui on 2019-10-11.
 *
 * @author xiangrui
 * @date 2019-10-11
 */
public class IncreaseTest {
    static int cnt = 0;

    public static void main(String[] args) {

        lock();
    }

    public static void unLock() {
        Callable<String> r = () -> {
            int n = 10000;
            while (n > 0) {
                cnt++;
                n--;
            }
            return "success";
        };

        List<Callable<String>> runnableList = Lists.newArrayList();
        for (int i = 0; i < 10; i++) {
            runnableList.add(r);
        }
        ExecutorService executorService = Executors.newFixedThreadPool(10);

        try {
            executorService.invokeAll(runnableList);
            executorService.shutdown();
            System.out.println(cnt);
        } catch (InterruptedException e) {

        }
    }

    public static void lock() {

        //初始化ReentrantLock
        ReentrantLock reentrantLock = new ReentrantLock();

        Callable<String> r = () -> {
            reentrantLock.lock();
            int n = 10000;
            while (n > 0) {
                cnt++;
                n--;
            }
            reentrantLock.unlock();
            return "success";
        };

        List<Callable<String>> runnableList = Lists.newArrayList();
        for (int i = 0; i < 10; i++) {
            runnableList.add(r);
        }
        ExecutorService executorService = Executors.newFixedThreadPool(10);

        try {
            List<Future<String>> futures = executorService.invokeAll(runnableList);
            executorService.shutdown();
            System.out.println(cnt);
        } catch (InterruptedException e) {

        }
    }
}
