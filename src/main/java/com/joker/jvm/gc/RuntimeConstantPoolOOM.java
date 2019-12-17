package com.joker.jvm.gc;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xiangrui on 2019-10-01.
 * <p>
 * VM args: -XX:PermSize=10M -XX:MaxPermSize=10M
 */
public class RuntimeConstantPoolOOM {

    public static void main(String[] args) {
        List<String> list = new ArrayList<>();
        int i = 0;
        while (true) {
            list.add(String.valueOf(i).intern());
        }
    }
}
