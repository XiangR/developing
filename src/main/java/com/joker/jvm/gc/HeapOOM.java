package com.joker.jvm.gc;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xiangrui on 2019-10-01.
 * <p>
 * VM args: -Xms20m -Xmx20m
 * GCRoots 的可达路径使得当前new 出的对象都未被回收，
 * 随后：Exception in thread "main" java.lang.OutOfMemoryError: Java heap space
 */
public class HeapOOM {

    static class OOMObject {

    }

    public static void main(String[] args) {

        List<OOMObject> lists = new ArrayList<>();
        while (true) {
            lists.add(new OOMObject());
        }

    }
}
