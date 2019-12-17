package com.joker.concurrent;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by xiangrui on 2019-09-24.
 *
 * @author xiangrui
 * @date 2019-09-24
 */
public class Hosee2 {
    private static Integer count = 0;
    private final Integer FULL = 10;
    final Lock lock = new ReentrantLock();
    final Condition NotFull = lock.newCondition();
    final Condition NotEmpty = lock.newCondition();

    class Producer implements Runnable {
        @Override
        public void run() {
            for (int i = 0; i < 10; i++) {
                try {
                    Thread.sleep(3000);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                lock.lock();
                try {
                    while (count == FULL) {
                        try {
                            NotFull.await();
                        } catch (InterruptedException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                    }
                    count++;
                    System.out.println(Thread.currentThread().getName() + "生产者生产，目前总共有" + count);
                    NotEmpty.signal();
                } finally {
                    lock.unlock();
                }

            }
        }
    }

    class Consumer implements Runnable {

        @Override
        public void run() {
            for (int i = 0; i < 10; i++) {
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e1) {
                    e1.printStackTrace();
                }
                lock.lock();
                try {
                    while (count == 0) {
                        try {
                            NotEmpty.await();
                        } catch (Exception e) {
                            // TODO: handle exception
                            e.printStackTrace();
                        }
                    }
                    count--;
                    System.out.println(Thread.currentThread().getName() + "消费者消费，目前总共有" + count);
                    NotFull.signal();
                } finally {
                    lock.unlock();
                }

            }

        }

    }

    public static void main(String[] args) throws Exception {
//        Hosee2 hosee = new Hosee2();
//        new Thread(hosee.new Producer()).start();
//        new Thread(hosee.new Consumer()).start();
//        new Thread(hosee.new Producer()).start();
//        new Thread(hosee.new Consumer()).start();
//
//        new Thread(hosee.new Producer()).start();
//        new Thread(hosee.new Consumer()).start();
//        new Thread(hosee.new Producer()).start();
//        new Thread(hosee.new Consumer()).start();

    }
}