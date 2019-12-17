package com.joker.test;

import java.util.Arrays;

/**
 * Created by xiangrui on 2017/11/29.
 *
 * @author xiangrui
 * @date 2017/11/29
 */
public class DistinctTest {


    public static void main(String[] args) {

        int[] a = new int[]{1, 2, 3, 4, 1};

        Arrays.stream(a).distinct().forEach(System.out::println);
    }
}
