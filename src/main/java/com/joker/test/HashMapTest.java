package com.joker.test;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.joker.model.TestObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by xiangrui on 2018/7/24.
 *
 * @author xiangrui
 * @date 2018/7/24
 */
public class HashMapTest {

    public static void main(String[] args) {
        test1();
    }


    private static void test1() {

        List<TestObject> list = Lists.newArrayList();

        // 构造 hashCode 碰撞
        {
            TestObject testObject = new TestObject();
            testObject.setAge(12);
            testObject.setName("xiangrui");
            list.add(testObject);
        }
        {
            TestObject testObject = new TestObject();
            testObject.setAge(12);
            testObject.setName("xiangrui2");
            list.add(testObject);
        }
        {

            TestObject testObject = new TestObject();
            testObject.setAge(22);
            testObject.setName("xiangrui3");
            list.add(testObject);
        }

        Map<TestObject, TestObject> collect = new HashMap<>();
        for (TestObject k : list) {
            collect.put(k, k);
        }

        TestObject testObject = new TestObject();
        testObject.setAge(12);
        testObject.setName("xiangrui");

        TestObject testObject1 = collect.get(testObject);

        System.out.println(JSON.toJSONString(testObject1));
    }
}
