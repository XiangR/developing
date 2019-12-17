package com.joker.test;

import com.joker.entity.SimpleInterface;

/**
 * Created by xiangrui on 2018/1/8.
 *
 * @author xiangrui
 * @date 2018/1/8
 */
public class SimpleInterfaceImpl implements SimpleInterface {

    @Override
    public void doSomeWork() {
        System.out.println("Do Some Work implementation in the class");
    }

    public static void main(String[] args) {
        SimpleInterfaceImpl simpObj = new SimpleInterfaceImpl();
        simpObj.doSomeWork();
        simpObj.doSomeOtherWork();
    }
}
