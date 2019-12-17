package com.joker.test;

/**
 * Created by xiangrui on 2019/4/21.
 *
 * @author xiangrui
 * @date 2019/4/21
 */
public class ConTest {

    static class A {
        static {
            System.out.println("1");
        }

        public A() {
            System.out.println("2");
        }
    }

    static class B extends A {
        static {
            System.out.println("a");
        }

        public B() {
            System.out.println("b");
        }
    }

    public static void main(String[] args) {
        A a = new B();
        a = new B();
    }
}

