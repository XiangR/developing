package com.joker.jvm.classloading;

/**
 * Created by xiangrui on 2019-10-03.
 *
 * @author xiangrui
 * @date 2019-10-03
 */
public class NotInitialization {

    public static void main(String[] args) {

        // System.out.println(SubClass.value);

        // SuperClass[] classes = new SuperClass[0];

        System.out.println(ConstClass.HELLO);
    }
}
