package com.joker.jvm.classloading;

/**
 * Created by xiangrui on 2019-10-05.
 *
 * @author xiangrui
 * @date 2019-10-05
 */
public class ClassForName {

    //静态代码块
    static {
        System.out.println("执行了静态代码块");
    }

    //静态变量
    private static String staticFiled = staticMethod();

    //赋值静态变量的静态方法
    public static String staticMethod() {
        System.out.println("执行了静态方法");
        return "给静态字段赋值了";
    }
}
