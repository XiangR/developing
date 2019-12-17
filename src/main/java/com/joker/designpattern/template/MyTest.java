package com.joker.designpattern.template;

/**
 * Created by xiangrui on 2019-06-04.
 *
 * @author xiangrui
 * @date 2019-06-04
 */
public class MyTest {

    public static void main(String[] args) {

        test();
    }

    public static void test() {
//        DriveTemplate template = new SuzukiStrom1000();
        DriveTemplate template = new SuzukiScross();
        template.drive();
    }
}
