package com.joker.test;

import sun.misc.Unsafe;

import java.lang.reflect.Field;

/**
 * Created by xiangrui on 2018/7/16.
 *
 * @author xiangrui
 * @date 2018/7/16
 */
public class ObjectLocation {

    private static int apple = 10;
    private int orange = 10;

    public static void main(String[] args) throws Exception {
        Unsafe unsafe = getUnsafeInstance();

        Field appleField = ObjectLocation.class.getDeclaredField("apple");
        System.out.println("Location of Apple: "
                + unsafe.staticFieldOffset(appleField));

        Field orangeField = ObjectLocation.class.getDeclaredField("orange");
        System.out.println("Location of Orange: "
                + unsafe.objectFieldOffset(orangeField));


        Object o = appleField.get(appleField);
        System.out.println(o.equals(10));

    }

    private static Unsafe getUnsafeInstance() throws SecurityException,
            NoSuchFieldException, IllegalArgumentException,
            IllegalAccessException {
        Field theUnsafeInstance = Unsafe.class.getDeclaredField("theUnsafe");
        theUnsafeInstance.setAccessible(true);
        return (Unsafe) theUnsafeInstance.get(Unsafe.class);
    }
}
