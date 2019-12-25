package com.yit.test.cache;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.alibaba.fastjson.parser.ParserConfig;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.yit.test.entity.KeyValueObject;
import com.yit.test.entity.KeyValueTest;
import com.yit.test.entity.Person;
import org.junit.Assert;
import org.junit.Test;

public class ParseTest {

    static {
        ParserConfig.getGlobalInstance().setAutoTypeSupport(true);
    }

    @Test
    public void test_parse_1() {

        Person target = new Person(10086, 18, "xiangrui");
        KeyValueObject<Person> keyValueTest = new KeyValueObject<>();
        keyValueTest.setKey(String.valueOf(10086));
        keyValueTest.setObject(target);
        keyValueTest.setLogicalExpireSeconds(1000);

        String string = JSON.toJSONString(keyValueTest, SerializerFeature.WriteClassName, SerializerFeature.DisableCircularReferenceDetect);

        KeyValueObject<Person> parse = parse2(string);

        Assert.assertEquals(parse.getKey(), keyValueTest.getKey());
        Assert.assertEquals(parse.getObject(), keyValueTest.getObject());
    }

    @Test
    public void test_parse_2() {

        KeyValueTest<Integer, Person> keyValueTest = new KeyValueTest<>();
        keyValueTest.setKey(10086);
        keyValueTest.setObject(new Person(10086, 18, "xiangrui"));
        keyValueTest.setLogicalExpireSeconds(1000);

        String string = JSON.toJSONString(keyValueTest, SerializerFeature.WriteClassName, SerializerFeature.DisableCircularReferenceDetect);

        KeyValueTest<Integer, Person> parse = parse(string);

        Assert.assertEquals(parse.getKey(), keyValueTest.getKey());
        Assert.assertEquals(parse.getObject(), keyValueTest.getObject());
    }

    @Test
    public void test_parse_3() {

        KeyValueTest<Long, Person> keyValueTest = new KeyValueTest<>();
        keyValueTest.setKey(10086L);
        keyValueTest.setObject(new Person(10086, 18, "xiangrui"));
        keyValueTest.setLogicalExpireSeconds(1000);

        String string = JSON.toJSONString(keyValueTest, SerializerFeature.WriteClassName, SerializerFeature.DisableCircularReferenceDetect);

        KeyValueTest<Long, Person> parse = parse(string);

        Assert.assertEquals(parse.getKey(), keyValueTest.getKey());
        Assert.assertEquals(parse.getObject(), keyValueTest.getObject());
    }

    @Test
    public void test_parse_4() {

        KeyValueTest<String, Person> keyValueTest = new KeyValueTest<>();
        keyValueTest.setKey(String.valueOf(10086));
        keyValueTest.setObject(new Person(10086, 18, "xiangrui"));
        keyValueTest.setLogicalExpireSeconds(1000);

        String string = JSON.toJSONString(keyValueTest, SerializerFeature.WriteClassName, SerializerFeature.DisableCircularReferenceDetect);

        KeyValueTest<String, Person> parse = parse(string);

        Assert.assertEquals(parse.getKey(), keyValueTest.getKey());
        Assert.assertEquals(parse.getObject(), keyValueTest.getObject());
    }

    private <T, R> KeyValueTest<T, R> parse(String str) {
        KeyValueTest<T, R> t = JSON.parseObject(str, new TypeReference<KeyValueTest<T, R>>() {
        });
        System.out.println(JSON.toJSON(t));
        return t;
    }

    private <R> KeyValueObject<R> parse2(String str) {
        KeyValueObject<R> t = JSON.parseObject(str, new TypeReference<KeyValueObject<R>>() {
        });
        System.out.println(JSON.toJSON(t));
        return t;
    }
}
