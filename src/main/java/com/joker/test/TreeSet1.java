package com.joker.test;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.TreeSet;

public class TreeSet1 {
    public static void main(String args[]) {

        List<String> list = Arrays.asList("C", "A", "B", "E", "F", "D", "A");

        TreeSet<String> t1 = new TreeSet<>(comparator);
        TreeSet<String> t2 = new TreeSet<>(new MyComparator());

        t1.addAll(list);
        t2.addAll(list);

        t1.forEach(k -> System.out.format(" %s ", k));
        System.out.println("");
        t2.forEach(k -> System.out.format(" %s ", k));
    }

    /**
     * 通过定义方法来实现构造器
     */
    private static Comparator<String> comparator = Comparator.reverseOrder();

}

/**
 * 自定义class 实现构造器
 * 如果o1小于o2,返回一个负数;如果o1大于o2，返回一个正数;如果他们相等，则返回0;
 */
class MyComparator implements Comparator<String> {

    @Override
    public int compare(String a, String b) {
        return b.compareTo(a);
    }
    // No need to override equals.
}
