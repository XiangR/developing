package com.joker.concurrent;

/**
 * Created by xiangrui on 2019-10-27.
 *
 * @author xiangrui
 * @date 2019-10-27
 */
public class JoinMethodTest {

    private static void printWithThread(String content) {
        System.out.println("[" + Thread.currentThread().getName() + "线程]: " + content);
    }

    public static void main(String[] args) {

        printWithThread("开始执行main方法");

        Thread myThread = new Thread(() -> {
            printWithThread("我在自定义的线程的run方法里");
            printWithThread("我马上要休息1秒钟, 并让出CPU给别的线程使用.");
            try {
                Thread.sleep(1000);
                printWithThread("已经休息了1秒, 又重新获得了CPU");
                printWithThread("我休息好了, 马上就退出了");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        });
        try {
            //myThread.start();
            printWithThread("我在main方法里面, 我要等下面这个线程执行完了才能继续往下执行.");
            myThread.join(5000);
            printWithThread("我在main方法里面, 马上就要退出了.");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
