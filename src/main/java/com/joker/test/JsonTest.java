package com.joker.test;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.google.common.collect.Maps;

import java.util.Map;

public class JsonTest {

    public static void main(String[] args) {

        Model model = new Model();
        model.name = "xiangrui";
        System.out.println(JSON.toJSONString(model, SerializerFeature.WriteMapNullValue));
//        test1();
//        test3();
    }


    public static void test1() {

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("b", 2);
        jsonObject.put("d", 4);
        jsonObject.put("a", 1);
        jsonObject.put("c", 3);

        System.out.println(jsonObject.toJSONString());
        System.out.println(JSON.toJSONString(jsonObject));
    }

    public static void test3() {

        Map<String, Integer> jsonObject = Maps.newLinkedHashMap();
        jsonObject.put("b", 2);
        jsonObject.put("d", 4);
        jsonObject.put("a", 1);
        jsonObject.put("c", 3);

        System.out.println(jsonObject.toString());
        System.out.println(JSON.toJSONString(jsonObject));
    }


    public static class Model {

        public String name;
        public String age;
    }
}
