package com.joker.test;

/**
 * 体会可变参数
 * <p>
 * Created by xiangrui on 2017/12/26.
 *
 * @author xiangrui
 * @date 2017/12/26
 */
public class VariableTest {

    public static void main(String[] args) {
        System.out.println(sum(2, 3));
        System.out.println(sum(2, 3, 5));
    }

    public static int sum(int x, int... args) {
        int sum = x;

        for (int y : args) {
            sum += y;
        }
        return sum;
    }
}