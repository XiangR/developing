package com.joker.concurrent;

import java.util.concurrent.atomic.AtomicReference;

/**
 * Created by xiangrui on 2019-09-24.
 *
 * @author xiangrui
 * @date 2019-09-24
 */
public class SpinLockV2 {

    private AtomicReference<Thread> cas = new AtomicReference<>();

    public int count = 0;

    public void lock() {
        Thread current = Thread.currentThread();

        // 当前线程拿到了锁，则模拟可重入
        if (current == cas.get()) {
            count += 1;
            return;
        }

        while (!cas.compareAndSet(null, current)) {
            // do nothing
            System.out.println("while for lock");
        }
    }

    public void unlock() {
        Thread current = Thread.currentThread();
        if (current == cas.get()) {
            // 不等于零的时候，模拟锁释放
            if (count > 0) {
                count -= 1;
            } else {
                // 等于零的时候释放锁
                cas.compareAndSet(current, null);
            }
        }
    }

}
