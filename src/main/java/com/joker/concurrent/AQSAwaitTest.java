package com.joker.concurrent;

import java.util.concurrent.CountDownLatch;

/**
 * Created by xiangrui on 2019-10-14.
 *
 * @author xiangrui
 * @date 2019-10-14
 */
public class AQSAwaitTest {


    private static CountDownLatch countDownLatch = new CountDownLatch(1);

    public static void main(String[] args) throws InterruptedException {


        Thread thread = new Thread(new Test());
        thread.start();


        System.out.println("开始执行 await:" + Thread.currentThread().getName());
        countDownLatch.await();
        System.out.println("程序退出 :" + Thread.currentThread().getName());

    }

    static class Test implements Runnable {

        @Override
        public void run() {

            try {
                Thread.sleep(10 * 1000);
                System.out.println("开始执行 countDown:" + Thread.currentThread().getName());
                countDownLatch.countDown();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                System.out.println("开始退出 countDown:" + Thread.currentThread().getName());
            }
        }
    }


}
