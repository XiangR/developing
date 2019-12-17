package com.joker.concurrent;

import java.util.concurrent.atomic.AtomicReference;

/**
 * Created by xiangrui on 2019-09-24.
 *
 * @author xiangrui
 * @date 2019-09-24
 */
public class SpinLock {

    private AtomicReference<Thread> cas = new AtomicReference<>();

    public void lock() {

        Thread current = Thread.currentThread();
        while (!cas.compareAndSet(null, current)) {
            // do nothing
            System.out.println("while for lock");
        }

    }

    public void unlock() {
        Thread current = Thread.currentThread();
        cas.compareAndSet(current, null);
    }

}
