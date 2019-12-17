package com.yit.test;

import com.google.common.collect.Lists;
import com.joker.thread.HungrySingleton;
import com.joker.thread.LazySingleton;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class SingletonTest {

    @Test
    public void lazy() {
        int times = 10;
        List<Integer> list = new ArrayList<>();
        while ((times--) > 0) {
            new Thread(() -> {
                LazySingleton instance;
                while ((instance = LazySingleton.getInstance()) != null) {
                    list.add(instance.getId());
                    break;
                }
            }).start();
        }
        list.forEach(System.out::println);
    }

    @Test
    public void hungry() throws InterruptedException {
        int times = 1000;
        List<Thread> allThread = Lists.newArrayList();
        List<Integer> list = new ArrayList<>();
        while ((times--) > 0) {
            Thread thread = new Thread(() -> {
                int id = HungrySingleton.getInstance().getId();
                list.add(id);
            });
            thread.start();
            thread.join();
            allThread.add(thread);
        }
        System.out.println(String.format("子线程数 %s", allThread.size()));
        System.out.println(String.format("构造实例数 %s", list.size()));
        // list.forEach(System.out::println);
    }
}
