package com.joker.designpattern.singleton;

/**
 * Created by xiangrui on 2019-10-31.
 *
 * @author xiangrui
 * @date 2019-10-31
 */
public class Singleton1 {

    private static class InnerHolder {
        private static Object instance;

        static {

            System.out.println("Init InnerHolder thread " + Thread.currentThread());

            instance = new Object();
        }
    }

    public static Object load() {
        return InnerHolder.instance;
    }

}
