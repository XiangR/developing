package com.joker.test;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Created by xiangrui on 2017/11/26.
 *
 * @author xiangrui
 * @date 2017/11/26
 */
public class Function1 {


    public static void main(String[] args) {
        List<Integer> list = Arrays.asList(1, 2, 3, 4, 5);

        Map<Integer, String> apply = convert.apply(list);

        apply.forEach((k, v) -> System.out.format("k: %s, v: %s \n", k, v));
    }

    private static Function<List<Integer>, Map<Integer, String>> convert = (List<Integer> input) -> {
        Map<Integer, String> result;
        result = input.stream().collect(Collectors.toMap(k -> k, Object::toString, (k, v) -> k));
        return result;
    };
}
