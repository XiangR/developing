package com.joker.test;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;

public class SerializationTest {


    public static void main(String[] args) {

        {
            Integer parse = parse("12");
            System.out.println(parse);
        }
        {
            Long parse = parse("12L");
            System.out.println(parse);
        }
        {
            String parse = parse("12");
            System.out.println(parse);
        }

    }

    public static <T> T parse(String t) {

        return JSON.parseObject(t, new TypeReference<T>() {
        });
    }
}
