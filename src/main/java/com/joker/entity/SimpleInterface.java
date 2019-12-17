package com.joker.entity;

/**
 * Created by xiangrui on 2018/1/8.
 *
 * @author xiangrui
 * @date 2018/1/8
 */
public interface SimpleInterface {

    void doSomeWork();

    //A default method in the interface created using "default" keyword
    //使用default关键字创在interface中直接创建一个default方法，该方法包含了具体的实现代码
    default void doSomeOtherWork() {
        System.out.println("DoSomeOtherWork implementation in the interface");
    }
}
