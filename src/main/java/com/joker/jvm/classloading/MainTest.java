package com.joker.jvm.classloading;

/**
 * Created by xiangrui on 2019-10-05.
 *
 * @author xiangrui
 * @date 2019-10-05
 */
public class MainTest {

    public static void main(String[] args) {

        try {
            Class<?> aClass = Class.forName("com.joker.jvm.classloading.ClassForName");
            System.out.println("#########分割符(上面是Class.forName的加载过程，下面是ClassLoader的加载过程)##########");
            Class<?> aClass1 = ClassLoader.getSystemClassLoader().loadClass("com.joker.jvm.classloading.ClassForName");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

}
