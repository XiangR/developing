package com.joker.test;

import com.joker.utils.CollectionUtil;

import java.util.Arrays;
import java.util.List;

/**
 * Created by xiangrui on 2017/12/21.
 *
 * @author xiangrui
 * @date 2017/12/21
 */
public class FuncTest {

    public static void test() {
        List<String> strings = Arrays.asList("1", "2", "3", "1");
        List<String> distinct = CollectionUtil.distinct(strings, String::equals);
        System.out.println(distinct);
    }

    public static void main(String[] args) {
        test();
    }
}
