package com.joker.guava;

import com.google.common.base.Joiner;

import java.util.Arrays;
import java.util.List;

/**
 * Created by xiangrui on 2017/12/14.
 *
 * @author xiangrui
 * @date 2017/12/14
 */
public class JoinTest {


    public static void main(String[] args) {

        test1();
    }

    public static void test1() {
        List<String> strings = Arrays.asList("1", "2", null, "3");
        String join = Joiner.on(",").skipNulls().join(strings);

        System.out.format("join: %s \n", join);
    }
}
