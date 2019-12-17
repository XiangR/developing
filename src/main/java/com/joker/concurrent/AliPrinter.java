package com.joker.concurrent;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by xiangrui on 2019-10-15.
 *
 * @author xiangrui
 * @date 2019-10-15
 */
public class AliPrinter {

    /**
     * 线程安全的计数用来控制线程顺序打印
     */
    private AtomicInteger count;

    /**
     * 控制线程是否运行
     */
    private volatile boolean flag;

    private Runnable runnableA;

    private Runnable runnableB;

    private Runnable runnableC;

    public Runnable getRunnableA() {
        return runnableA;
    }

    public Runnable getRunnableB() {
        return runnableB;
    }

    public Runnable getRunnableC() {
        return runnableC;
    }

    public AliPrinter() {
        count = new AtomicInteger();
        flag = true;
        runnableA = () -> {
            while (flag) {
                if (count.get() == 0) {
                    System.out.print("a");
                    count.getAndIncrement();
                }
            }
        };

        runnableB = () -> {
            while (flag) {
                if (count.get() == 1) {
                    System.out.print("l");
                    count.getAndIncrement();
                }
            }
        };

        runnableC = () -> {
            while (flag) {
                if (count.get() == 2) {
                    System.out.print("i");
                    count.set(0);
                }
            }
        };

    }

    public void stop() {
        flag = false;
    }

    public static void main(String[] args) throws InterruptedException {
        ThreadPoolExecutor executor = new ThreadPoolExecutor(3, 3, 0L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<>());

        AliPrinter printer = new AliPrinter();

        executor.execute(printer.getRunnableA());
        executor.execute(printer.getRunnableB());
        executor.execute(printer.getRunnableC());

        Thread.sleep(1000);

        printer.stop();

        executor.shutdown();
    }
}
