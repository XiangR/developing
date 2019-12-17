package com.joker.concurrent;

import java.util.Date;

/**
 * Created by xiangrui on 2019-10-28.
 *
 * @author xiangrui
 * @date 2019-10-28
 */
public class DaemonThread {
    public static void main(String[] args) throws Exception {
        Runnable r = new Runnable() {
            @Override
            public void run() {
                String daemon = (Thread.currentThread().isDaemon() ? "daemon"
                        : "not daemon");
                while (true) {
                    System.out.println("I'm running at " + new Date() + ", I am " + daemon);
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        System.out.println("I was interrupt, I am " + daemon);
                    }
                }
            }

        };

        Thread t = new Thread(r);
        t.setDaemon(true);
        t.start();
        Thread.sleep(3000);
        System.out.println("main thread exits");
    }
}
