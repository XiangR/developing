package com.joker.guava;

import com.google.common.collect.Lists;
import com.google.common.primitives.Ints;

import java.util.List;

/**
 * Created by xiangrui on 2017/12/18.
 *
 * @author xiangrui
 * @date 2017/12/18
 */
public class ListsTest {

    private static List lists = Ints.asList(1, 2, 3, 4, 5);

    public static void main(String[] args) {
//        reverse();
        partition2();
    }

    /**
     * 反转集合
     */
    private static void reverse() {
        List<Integer> reverse = Lists.reverse(lists); // {5, 4, 3, 2, 1}
        System.out.format("reverse: %s \n", reverse);
        System.out.format("=========== %s end ===========\n", Thread.currentThread().getStackTrace()[1].getMethodName());
    }

    /**
     * 切割集合
     */
    private static void partition() {
        List<List<Integer>> partitions = Lists.partition(lists, 2);//{{1,2}, {3,4}, {5}}
        System.out.format("partitions: %s \n", partitions);
        System.out.format("=========== %s end ===========\n", Thread.currentThread().getStackTrace()[1].getMethodName());
    }

    /**
     * 切割集合
     */
    private static void partition2() {

        List lists2 = Ints.asList(1, 2, 3);
        List<List<Integer>> partitions = Lists.partition(lists2, 2);//{{1,2}, {3,4}, {5}}
        System.out.format("partitions size : %s \n", partitions.size());
        System.out.format("partitions: %s \n", partitions);
        System.out.format("=========== %s end ===========\n", Thread.currentThread().getStackTrace()[1].getMethodName());
    }
}
