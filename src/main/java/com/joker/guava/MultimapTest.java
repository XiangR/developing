package com.joker.guava;

import com.google.common.collect.*;
import com.google.common.primitives.Ints;

import java.util.*;

/**
 * Created by xiangrui on 2017/12/18.
 *
 * @author xiangrui
 * @date 2017/12/18
 */
public class MultimapTest {

    public static void main(String[] args) {

        operator();
    }

    /**
     * 了解 Multimap 的基本用法
     */
    public static void operator() {
        ArrayListMultimap<String, Integer> multimap = ArrayListMultimap.create();
        multimap.putAll("b", Ints.asList(2, 4, 6));
        multimap.putAll("a", Ints.asList(4, 2, 1));
        multimap.putAll("c", Ints.asList(2, 5, 3));

        // 表示 Multimap 中所有不同的键
        Set<String> strings = multimap.keySet();
        System.out.format("keySet: %s \n", strings);

        // 表示 Multimap 中的所有键，每个键重复出现的次数等于它映射的值的个数
        Multiset<String> keys = multimap.keys();
        System.out.format("keys: %s \n", keys);

        // 返回 Multimap 中所有 ”键 - 单个值映射”——包括重复键。
        Collection<Map.Entry<String, Integer>> entries = multimap.entries();
        System.out.format("entries: %s \n", entries);

        // 得到所有 ”键 - 值集合映射”
        Map<String, List<Integer>> stringListMap = Multimaps.asMap(multimap);
        Set<Map.Entry<String, List<Integer>>> entries2 = stringListMap.entrySet();
        System.out.format("asMap -> entrySet: %s \n", entries2);

        System.out.format("multimap: %s \n", multimap);
        System.out.format("=========== %s end ===========\n", Thread.currentThread().getStackTrace()[1].getMethodName());
    }

    /**
     * 了解 Multimap 的基本用法
     */
    public static void test1() {
        ArrayListMultimap<String, Integer> multimap = ArrayListMultimap.create();
        multimap.putAll("b", Ints.asList(2, 4, 6, 6));
        multimap.putAll("a", Ints.asList(4, 2, 1));
        multimap.putAll("c", Ints.asList(2, 5, 3));

        Map<String, List<Integer>> stringListMap = Multimaps.asMap(multimap);
        System.out.println(stringListMap);


        TreeMultimap<String, Integer> multimap2 = TreeMultimap.create();
        multimap2.putAll("b", Ints.asList(2, 4, 6, 6));
        multimap2.putAll("a", Ints.asList(4, 2, 1));
        multimap2.putAll("c", Ints.asList(2, 5, 3));

        Map<String, SortedSet<Integer>> stringSortedSetMap = Multimaps.asMap(multimap2);
        System.out.println(stringSortedSetMap);
        System.out.format("=========== %s end ===========\n", Thread.currentThread().getStackTrace()[1].getMethodName());
    }
}
