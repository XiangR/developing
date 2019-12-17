package com.joker.guava;

import com.google.common.base.Function;
import com.google.common.collect.*;
import com.google.common.primitives.Ints;

import java.util.HashSet;
import java.util.Map;

/**
 * Created by xiangrui on 2017/12/16.
 *
 * @author xiangrui
 * @date 2017/12/16
 */
public class MultimapsTest {


    public static void main(String[] args) {

        index();

        invertFrom();

        mapUp();
    }

    /**
     * 如果索引值不是独一无二的，可以考虑使用 Multimaps.index 其值可以以 List 的形式存在
     */
    private static void index() {

        ImmutableSet digits = ImmutableSet.of("zero", "one", "two", "three", "four", "five", "six", "seven", "eight", "nine");

        HashSet hashSet = Sets.newHashSet(digits);
        Function<String, Integer> lengthFunction = String::length;
        ImmutableListMultimap<Integer, String> digitsByLength = Multimaps.index(hashSet, lengthFunction);

        ImmutableList<String> strings = digitsByLength.get(4);
        System.out.format("length4 -> strings: %s\n", strings);

        System.out.format("digitsByLength: %s\n", digitsByLength);
        /*
        *  digitsByLength maps:
        *  3 => {"one", "two", "six"}
        *  4 => {"zero", "four", "five", "nine"}
        *  5 => {"three", "seven", "eight"}
        */
        System.out.format("=========== %s end ===========\n", Thread.currentThread().getStackTrace()[1].getMethodName());
    }

    private static void invertFrom() {
        ArrayListMultimap<String, Integer> multimap = ArrayListMultimap.create();
        multimap.putAll("b", Ints.asList(2, 4, 6));
        multimap.putAll("a", Ints.asList(4, 2, 1));
        multimap.putAll("c", Ints.asList(2, 5, 3));

        TreeMultimap<Integer, String> inverse = Multimaps.invertFrom(multimap, TreeMultimap.create());

        System.out.format("inverse: %s\n", inverse);
        //注意我们选择的实现，因为选了TreeMultimap，得到的反转结果是有序的

        /*
        * inverse maps:
        *  1 => {"a"}
        *  2 => {"a", "b", "c"}
        *  3 => {"c"}
        *  4 => {"a", "b"}
        *  5 => {"c"}
        *  6 => {"b"}
        */
        System.out.format("=========== %s end ===========\n", Thread.currentThread().getStackTrace()[1].getMethodName());
    }

    /**
     * 让 map 使用Multimaps 的方法，搜索将 map -> Multimap
     */
    private static void mapUp() {

        Map<String, Integer> map = ImmutableMap.of("a", 1, "b", 1, "c", 2);
        SetMultimap<String, Integer> multimap = Multimaps.forMap(map);
        System.out.format("multimap: %s \n", multimap);
        // multimap：["a" => {1}, "b" => {1}, "c" => {2}]

        Multimap<Integer, String> inverse = Multimaps.invertFrom(multimap, HashMultimap.create());
        System.out.format("inverse: %s \n", inverse);
        // inverse：[1 => {"a","b"}, 2 => {"c"}]
        System.out.format("=========== %s end ===========\n", Thread.currentThread().getStackTrace()[1].getMethodName());
    }
}
