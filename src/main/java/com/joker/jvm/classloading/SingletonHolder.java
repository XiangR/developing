package com.joker.jvm.classloading;

/**
 * 使用内部静态类的形式来实现单例
 * <p>
 * Created by xiangrui on 2019-10-08.
 *
 * @author xiangrui
 * @date 2019-10-08
 */
public class SingletonHolder {

    /**
     * jvm 保证类加载过程中的 <clinit> 方法是线程安全的
     */
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
