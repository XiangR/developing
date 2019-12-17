package com.joker.proxy;

import java.io.Serializable;

/**
 * Created by xiangrui on 2019-06-27.
 *
 * @author xiangrui
 * @date 2019-06-27
 */
public class User implements Serializable {

    private int age;
    private String name;

    public User(String name, int age) {
        this.age = age;
        this.name = name;
    }

    public User() {
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
