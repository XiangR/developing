package com.joker.jvm.classloading;

/**
 * Created by xiangrui on 2019-10-03.
 *
 * @author xiangrui
 * @date 2019-10-03
 */
public class ConstClass {

    static {

        System.out.println("ConstClass init");

    }

    public static final String HELLO = "hello";
}
