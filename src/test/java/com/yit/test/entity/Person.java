package com.yit.test.entity;

import java.util.Objects;

public class Person {
    public int key;
    public int age;
    public String name;

    public Person() {
    }

    public Person(int key, int age, String name) {
        this.key = key;
        this.age = age;
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Person)) return false;
        Person person = (Person) o;
        return key == person.key &&
                age == person.age &&
                Objects.equals(name, person.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(key, age, name);
    }

    @Override
    public String toString() {
        return "Person{" +
                "key=" + key +
                ", age=" + age +
                ", name='" + name + '\'' +
                '}';
    }
}
