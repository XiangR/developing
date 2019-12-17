package com.joker.designpattern.template;

/**
 * Created by xiangrui on 2019-06-04.
 *
 * @author xiangrui
 * @date 2019-06-04
 */
public class SuzukiScross extends DriveTemplate {

    @Override
    protected void openDoor() {
        System.out.println("keyless entry");
    }

    @Override
    protected void gear() {
        System.out.println("gear with hand");
    }

    @Override
    protected void brake() {
        System.out.println("brake with foot");
    }
}