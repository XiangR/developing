package com.joker.concurrent;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by xiangrui on 2019-10-13.
 *
 * @author xiangrui
 * @date 2019-10-13
 */
public class ConditionTest {

    static ReentrantLock lock = new ReentrantLock();
    static Condition condition = lock.newCondition();

    public static void main(String[] args) throws InterruptedException {

        lock.lock();
        new Thread(new SignalThread()).start();

        System.out.println("主线程等待通知");
        try {
            condition.await();
        } finally {
            lock.unlock();
        }
        System.out.println("主线程恢复运行");
    }

    static class SignalThread implements Runnable {

        @Override
        public void run() {
            lock.lock();
            try {
                Thread.sleep(2000);
                condition.signal();
                System.out.println("子线程通知");
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        }
    }
}
