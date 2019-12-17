package com.joker.utils;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.joker.model.Person;

import java.util.List;
import java.util.stream.Collectors;

public class SortTest {

    public static void main(String[] args) {


        List<Person> personList = Lists.newArrayList();

        Person person = new Person("xiangrui", 12);
        Person person1 = new Person("xiangrui", 12);
        // personList.add(person);
        personList.add(person1);


        Person person2 = personList.stream().sorted().collect(Collectors.toList()).get(0);

        System.out.println(JSON.toJSON(person2));

    }

}