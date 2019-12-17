package com.yit.test;

import com.alibaba.dubbo.common.URL;
import com.alibaba.dubbo.common.serialize.ObjectInput;
import com.alibaba.dubbo.common.serialize.ObjectOutput;
import com.alibaba.dubbo.common.serialize.Serialization;
import com.alibaba.dubbo.common.serialize.hessian2.Hessian2Serialization;
import com.alibaba.fastjson.JSON;
import com.joker.model.Person;
import org.junit.Assert;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by xiangrui on 2019-08-14.
 *
 * @author xiangrui
 * @date 2019-08-14
 */
public class Hessian2Test {

    private Serialization serialization = new Hessian2Serialization();

    private URL url = new URL("protocl", "1.1.1.1", 1234);

    private ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

    private static List<Person> beforeSerialization = new ArrayList<>();

    static {
        Person person = new Person("xiangrui", 23);
        beforeSerialization.add(person);
        beforeSerialization.add(person);
    }


    /**
     * Hessian 序列化如果原list中存在相同对象，则序列化后的list中两个对象还是相同的对象
     */
    @Test
    public void test_Hessian() throws Exception {

        ObjectOutput objectOutput = serialization.serialize(url, byteArrayOutputStream);
        objectOutput.writeObject(beforeSerialization);
        objectOutput.flushBuffer();

        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(
                byteArrayOutputStream.toByteArray());
        ObjectInput deserialize = serialization.deserialize(url, byteArrayInputStream);

        List afterSerialization = deserialize.readObject(List.class);

        Assert.assertEquals(beforeSerialization.get(0), beforeSerialization.get(1));
        Assert.assertEquals(afterSerialization.get(0), afterSerialization.get(1));
    }

    /**
     * 大多数的http请求都是 JSON 序列化成可以读懂的string，而JSON序列化是没有这个特点的。在序列化后，就变成了两个对象
     */
    @Test
    public void test_JSON() {

        Object o = JSON.toJSON(beforeSerialization);
        String s = JSON.toJSONString(o);

        List<Person> afterSerialization = JSON.parseArray(s, Person.class);

        Assert.assertEquals(beforeSerialization.get(0), beforeSerialization.get(1));
        Assert.assertNotEquals(afterSerialization.get(0), afterSerialization.get(1));

    }

    /**
     * 如果直接使用 {@link JSON#toJSONString(java.lang.Object)} 则会保留原特性 [{"age":23,"name":"xiangrui"},{"$ref":"$[0]"}]
     */
    @Test
    public void test_JSON_2() {

        String s = JSON.toJSONString(beforeSerialization);

        System.out.println(s);

        List<Person> afterSerialization = JSON.parseArray(s, Person.class);

        Assert.assertEquals(beforeSerialization.get(0), beforeSerialization.get(1));
        Assert.assertEquals(afterSerialization.get(0), afterSerialization.get(1));

    }

}
