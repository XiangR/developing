package com.joker.guava;

import com.google.common.base.Joiner;
import com.google.common.collect.Lists;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by xiangrui on 2017/11/26.
 *
 * @author xiangrui
 * @date 2017/11/26
 */
public class Test {

    public static void main(String[] args) {


        List<String> strings = Arrays.asList("1", "2", "3");

        ArrayList<String> strings1 = Lists.newArrayList(strings);
        boolean remove = strings1.remove("1");
        System.out.println(remove);

        strings1.forEach(k -> System.out.println(k));

        // System.out.println(JSON.toJSONString(strings1));

        String join = Joiner.on(",").join(Arrays.asList(1));
        System.out.println("join: " + join);
    }
}
