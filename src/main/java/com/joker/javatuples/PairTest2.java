package com.joker.javatuples;

import com.google.common.collect.Lists;
import org.javatuples.Pair;

import java.util.List;

/**
 * Created by xiangrui on 2018/9/5.
 *
 * @author xiangrui
 * @date 2018/9/5
 */
public class PairTest2 {


    private static Integer value1 = 1;
    private static Integer value2 = 2;

    public static void main(String[] args) {

        test_equals();

    }

    private static void test_form_collection() {


        List<Integer> integers = Lists.newArrayList(value1, value2);
        Pair<Integer, Integer> objects = Pair.fromCollection(integers);

        System.out.println(objects);
    }

    private static void test_with() {

        Pair<Integer, Integer> with = Pair.with(value1, value2);
        System.out.println(with);
    }

    private static void test_equals() {

        List<Integer> integers = Lists.newArrayList(value1, value2);
        Pair<Integer, Integer> objects = Pair.fromCollection(integers);

        Pair<Integer, Integer> with = Pair.with(value1, value2);

        System.out.println(objects.equals(with));
    }
}
