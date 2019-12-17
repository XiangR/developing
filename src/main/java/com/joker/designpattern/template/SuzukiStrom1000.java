package com.joker.designpattern.template;

/**
 * Created by xiangrui on 2019-06-04.
 *
 * @author xiangrui
 * @date 2019-06-04
 */
public class SuzukiStrom1000 extends DriveTemplate {

    @Override
    protected void openDoor() {
        System.out.println("no door actually");
    }

    @Override
    protected void gear() {
        System.out.println("gear with foot");
    }

    @Override
    protected void brake() {
        System.out.println("brake with hand");
    }
}