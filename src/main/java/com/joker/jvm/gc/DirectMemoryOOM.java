package com.joker.jvm.gc;

import sun.misc.Unsafe;

import java.lang.reflect.Field;

/**
 * Created by xiangrui on 2019-10-01.
 * <p>
 * VM args: -Xmx20M -XX:MaxDirectMemorySize=10M
 */
public class DirectMemoryOOM {

    private static final int _1M = 1024 * 1024;

    public static void main(String[] args) throws IllegalAccessException {

        Field declaredField = Unsafe.class.getDeclaredFields()[0];
        declaredField.setAccessible(true);
        Unsafe unsafe = (Unsafe) declaredField.get(null);
        while (true) {
            unsafe.allocateMemory(_1M);
        }
    }
}
