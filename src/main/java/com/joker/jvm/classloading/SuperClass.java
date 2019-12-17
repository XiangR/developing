package com.joker.jvm.classloading;

/**
 * Created by xiangrui on 2019-10-03.
 *
 * @author xiangrui
 * @date 2019-10-03
 */
public class SuperClass {

    static {
        System.out.println("SuperClass init!");
    }

    public static int value = 123;

}
