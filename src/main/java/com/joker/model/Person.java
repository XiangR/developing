package com.joker.model;

import java.io.Serializable;

/**
 * Created by xiangrui on 2019-06-27.
 *
 * @author xiangrui
 * @date 2019-06-27
 */
public class Person implements Serializable {

    private int age;
    private String name;

    public Person(String name, int age) {
        this.age = age;
        this.name = name;
        System.out.println("构造函数Person(有参数)执行");
    }

    public Person() {
        System.out.println("构造函数Person(无参数)执行");
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

    @Override
    public String toString() {
        return "Person{" +
                "age=" + age +
                ", name='" + name + '\'' +
                '}';
    }


    public static void main(String[] args) {



    }
}
