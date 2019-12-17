package com.joker.test;

import com.google.common.collect.Lists;

import java.util.Comparator;
import java.util.List;

/**
 * Created by xiangrui on 2018/4/23.
 *
 * @author xiangrui
 * @date 2018/4/23
 */
public class SortTest {


    public static void main(String[] args) {
        List<Integer> integers = Lists.newArrayList(3, 5, 1, 8, 2);

        integers.sort(Comparator.naturalOrder());
        System.out.println(integers);

        integers.sort(Comparator.reverseOrder());
        System.out.println(integers);
    }
}
