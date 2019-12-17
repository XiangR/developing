package com.joker.model;


/**
 * Created by xiangrui on 2018/1/17.
 *
 * @author xiangrui
 * @date 2018/1/17
 */
public class TestObject {


    private String name;

    private Integer age;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null)
            return false;
        if (obj instanceof TestObject) {
            TestObject out = (TestObject) obj;
            return out.name.equals(this.name) && out.age.equals(this.age);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return age;
    }
}
