package com.yit.test.cache;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yit.test.entity.KeyValueObject;
import com.yit.test.entity.Person;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;

public class JacksonParseTest {

    private static ObjectMapper mapper = new ObjectMapper();

    static {
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    @Test
    public void test_parse_1() throws Exception {


        Person target = new Person(10086, 18, "xiangrui");
        KeyValueObject<Person> keyValueTest = new KeyValueObject<>();
        keyValueTest.setKey(String.valueOf(10086));
        keyValueTest.setObject(target);
        keyValueTest.setLogicalExpireSeconds(1000);

        String jsonString = "null";
        jsonString = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(keyValueTest);
        System.out.println(jsonString);


        KeyValueObject<Person> parse = parse(jsonString);

        Assert.assertEquals(parse.getKey(), keyValueTest.getKey());
        Assert.assertEquals(parse.getObject(), keyValueTest.getObject());
    }

    private KeyValueObject<Person> parse(String str) throws IOException {
        KeyValueObject<Person> o = mapper.readValue(str, new TypeReference<KeyValueObject<Person>>() {
        });
        return o;
    }

    private <R> KeyValueObject<R> parse2(String str) throws IOException {
        KeyValueObject<R> o = mapper.readValue(str, new TypeReference<KeyValueObject<R>>() {
        });
        return o;
    }
}
