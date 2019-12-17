package com.joker.designpattern.singleton;

/**
 * Created by xiangrui on 2019-10-31.
 *
 * @author xiangrui
 * @date 2019-10-31
 */
public class Singleton2 {

    public volatile static Object object;

    public static Object getInstance() {
        if (object == null) {
            synchronized (Singleton2.class) {
                if (object == null) {
                    object = new Object();
                }
            }
        }
        return object;
    }

}
