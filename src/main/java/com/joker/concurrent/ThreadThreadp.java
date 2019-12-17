package com.joker.concurrent;

/**
 * Created by xiangrui on 2019-10-06.
 *
 * @author xiangrui
 * @date 2019-10-06
 */
public class ThreadThreadp {

    private int flag = 1;

    public synchronized void printA() throws InterruptedException {
        while (true) {
            if (flag == 1 || flag % 3 == 1) {
                System.out.print("A" + Thread.currentThread());
                flag++;
                notifyAll();
            }
            wait();
        }
    }

    public synchronized void printB() throws InterruptedException {
        while (true) {
            if (flag % 2 == 0) {
                System.out.print("B" + Thread.currentThread());
                flag++;
                notifyAll();
            }
            wait();
        }
    }

    public synchronized void printC() throws InterruptedException {
        while (true) {
            if (flag % 3 == 0) {
                System.out.print("C" + Thread.currentThread());
                flag++;
                notifyAll();
            }
            wait();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        ThreadThreadp t = new ThreadThreadp();
        PrintA printA = new PrintA(t);
        PrintB printB = new PrintB(t);
        PrintC printC = new PrintC(t);
        Thread t1 = new Thread(printA);
        Thread t2 = new Thread(printB);
        Thread t3 = new Thread(printC);
        t1.start();
        t2.start();
        t3.start();

    }

    static class PrintA implements Runnable {
        private ThreadThreadp t;

        PrintA(ThreadThreadp t) {
            this.t = t;
        }

        @Override
        public void run() {
            try {
                t.printA();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    static class PrintB implements Runnable {

        private ThreadThreadp t;

        PrintB(ThreadThreadp t) {
            this.t = t;
        }

        @Override
        public void run() {
            try {
                t.printB();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    static class PrintC implements Runnable {
        private ThreadThreadp t;

        PrintC(ThreadThreadp t) {
            this.t = t;
        }

        @Override
        public void run() {
            try {
                t.printC();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
