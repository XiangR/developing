package com.yit.test.cache;

import com.alibaba.dubbo.common.utils.CollectionUtils;
import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.joker.cache.KeyValueService;
import com.yit.test.BaseTest;
import com.yit.test.entity.Person;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class KeyValueServiceTest extends BaseTest {

    private static String category = "technician_customize_cache";

    private static String folder = "folder";

    @Autowired
    private KeyValueService keyValueService;

    @Test
    public void test_cache_TC1() {

        Map<Integer, Person> map = Maps.newHashMap();

        map.put(10001, new Person(10001, 11, "name-1"));
        map.put(20001, new Person(20001, 21, "name-2"));
        map.put(30001, new Person(30001, 31, "name-3"));

        keyValueService.mset(category, folder, map, 60 * 10);

        List<Integer> keyList = Lists.newArrayList(map.keySet());
        List<Person> mget = keyValueService.mget(category, folder, keyList, Person.class);
        Assert.assertEquals(3, mget.size());
        for (Person person : mget) {
            Person person1 = map.get(person.key);
            Assert.assertEquals(person1, person);
        }

        keyValueService.del(category, folder, keyList);
    }

    @Test
    public void test_del_TC1() {

        Map<Integer, Person> map = Maps.newHashMap();

        map.put(1000001, new Person(1000001, 11, "name-1"));
        map.put(2000001, new Person(2000001, 21, "name-2"));
        map.put(3000001, new Person(3000001, 31, "name-3"));

        keyValueService.mset(category, folder, map, 60 * 10);

        List<Integer> keyList = Lists.newArrayList(map.keySet());
        List<Person> mget = keyValueService.mget(category, folder, keyList, Person.class);
        Assert.assertEquals(3, mget.size());
        for (Person person : mget) {
            Person person1 = map.get(person.key);
            Assert.assertEquals(person1, person);
        }

        keyValueService.del(category, folder, keyList);

        sleep(2);

        List<Person> mgetAfterDel = keyValueService.mget(category, folder, keyList, Person.class);
        Assert.assertEquals(0, mgetAfterDel.size());
    }

    @Test
    public void test_mget_fun_TC1() {

        Map<Integer, Person> map = Maps.newHashMap();

        map.put(10000001, new Person(10000001, 11, "name-1"));
        map.put(20000001, new Person(20000001, 21, "name-2"));
        map.put(30000001, new Person(30000001, 31, "name-3"));
        keyValueService.mset(category, folder, map, 60 * 10);

        List<Integer> keyList = Lists.newArrayList();
        keyList.addAll(map.keySet());
        keyList.add(40000001);
        keyList.add(50000001);
        keyList.add(60000001);

        List<Person> mget = keyValueService.mget(category, folder, keyList, Person.class);
        System.out.println(JSON.toJSON(mget));
        Assert.assertEquals(3, mget.size());
        for (Person person : mget) {
            Person person1 = map.get(person.key);
            Assert.assertEquals(person1, person);
        }

        Map<Integer, Person> mgetAfterFuc = keyValueService.mget(category, folder, keyList,
                Person.class, (missKeys) -> {
                    Map<Integer, Person> innerMap = Maps.newHashMap();
                    for (Integer missKey : missKeys) {
                        innerMap.put(missKey, new Person(missKey, missKey, String.format("name-%s", missKey)));
                    }
                    map.putAll(innerMap);
                    return innerMap;
                }, 60 * 10);
        Assert.assertEquals(6, mgetAfterFuc.size());
        for (Map.Entry<Integer, Person> entry : mgetAfterFuc.entrySet()) {
            Person person1 = map.get(entry.getValue().key);
            Assert.assertEquals(entry.getValue(), person1);

            Person person = map.get(entry.getKey());
            Assert.assertEquals(entry.getValue(), person);
        }

        // 上面的 mget 会有一个异步 mset 的过程
        sleep(3);

        List<Person> mgetLast = keyValueService.mget(category, folder, keyList, Person.class);
        Assert.assertEquals(6, mgetLast.size());
        for (Person person : mgetLast) {
            Person person1 = map.get(person.key);
            Assert.assertEquals(person, person1);
        }

        keyValueService.del(category, folder, keyList);
    }

    @Test
    public void test_mget_fun_TC2() {

        List<Integer> firstList = Lists.newArrayList();
        firstList.add(11000001);
        firstList.add(21000001);
        firstList.add(31000001);

        List<Integer> keyList = Lists.newArrayList();
        keyList.addAll(firstList);
        keyList.add(41000001);
        keyList.add(51000001);
        keyList.add(61000001);

        // 预处理
        keyValueService.del(category, folder, keyList);

        Map<Integer, Person> baseMap = Maps.newHashMap();

        Map<Integer, Person> mgetFirst = keyValueService.mget(category, folder, keyList, 10, 10 * 60, (missKeys) -> {

            Map<Integer, Person> innerMap = Maps.newHashMap();
            for (Integer missKey : missKeys) {
                if (firstList.contains(missKey)) {
                    innerMap.put(missKey, new Person(missKey, missKey, String.format("name-%s", missKey)));
                }
            }
            baseMap.putAll(innerMap);
            return innerMap;

        });

        Assert.assertEquals(3, mgetFirst.size());
        for (Map.Entry<Integer, Person> entry : mgetFirst.entrySet()) {
            Person person1 = baseMap.get(entry.getKey());
            Assert.assertEquals(person1, entry.getValue());
        }

        Map<Integer, Person> mgetAfterFuc = keyValueService.mget(category, folder, keyList, 10, 10 * 60, (missKeys) -> {

            Map<Integer, Person> innerMap = Maps.newHashMap();
            for (Integer missKey : missKeys) {
                innerMap.put(missKey, new Person(missKey, missKey, String.format("name-%s", missKey)));
            }
            baseMap.putAll(innerMap);
            return innerMap;

        });

        Assert.assertEquals(6, mgetAfterFuc.size());
        for (Map.Entry<Integer, Person> entry : mgetAfterFuc.entrySet()) {
            Person person1 = baseMap.get(entry.getValue().key);
            Assert.assertEquals(entry.getValue(), person1);

            Person person = baseMap.get(entry.getKey());
            Assert.assertEquals(entry.getValue(), person);
        }

        // 上面的 mget 会有一个异步 mset 的过程
        sleep(4);

        Map<Integer, Person> mgetLast = keyValueService.mget(category, folder, keyList, 10, 10 * 60, (missKeys) -> null);

        Assert.assertEquals(6, mgetLast.size());

        for (Map.Entry<Integer, Person> entry : mgetLast.entrySet()) {
            Person person1 = baseMap.get(entry.getValue().key);
            Assert.assertEquals(entry.getValue(), person1);

            Person person = baseMap.get(entry.getKey());
            Assert.assertEquals(entry.getValue(), person);
        }

        keyValueService.del(category, folder, keyList);
    }

    @Test
    public void test_mget_cacheNullable_TC4() {

        List<Integer> firstList = Lists.newArrayList();

        firstList.add(11000001);
        firstList.add(21000001);
        firstList.add(31000001);

        List<Integer> secondList = Lists.newArrayList();
        secondList.add(41000001);
        secondList.add(51000001);
        secondList.add(61000001);

        List<Integer> keyList = Lists.newArrayList();
        keyList.addAll(firstList);
        keyList.addAll(secondList);
        // 预处理
        keyValueService.del(category, folder, keyList);

        Map<Integer, Person> baseMap = Maps.newHashMap();

        Map<Integer, Person> mgetFirst = keyValueService.mget(category, folder, keyList, 10, 10 * 60, true, (missKeys) -> {

            Map<Integer, Person> innerMap = Maps.newHashMap();
            for (Integer missKey : missKeys) {
                if (firstList.contains(missKey)) {
                    innerMap.put(missKey, new Person(missKey, missKey, String.format("name-%s", missKey)));
                }
            }
            baseMap.putAll(innerMap);
            return innerMap;

        });

        Assert.assertEquals(6, mgetFirst.size());
        for (Map.Entry<Integer, Person> entry : mgetFirst.entrySet()) {

            if (firstList.contains(entry.getKey())) {
                Person person1 = baseMap.get(entry.getKey());
                Assert.assertEquals(person1, entry.getValue());
            }
            if (secondList.contains(entry.getKey())) {
                Assert.assertNull(entry.getValue());
            }
        }

        Assert.assertTrue(mgetFirst.keySet().containsAll(firstList));
        Assert.assertTrue(mgetFirst.keySet().containsAll(secondList));

        // 上面的 mget 会有一个异步 mset 的过程

        sleep(4);

        Map<Integer, Person> mgetLast = keyValueService.mget(category, folder, keyList, 10, 10 * 60, true, (missKeys) -> {
            if (CollectionUtils.isNotEmpty(missKeys)) {
                throw new RuntimeException("异常");
            }
            return null;
        });


        Assert.assertEquals(6, mgetLast.size());
        for (Map.Entry<Integer, Person> entry : mgetLast.entrySet()) {

            if (firstList.contains(entry.getKey())) {
                Person person1 = baseMap.get(entry.getKey());
                Assert.assertEquals(person1, entry.getValue());
            }
            if (secondList.contains(entry.getKey())) {
                Assert.assertNull(entry.getValue());
            }
        }

        Assert.assertTrue(mgetLast.keySet().containsAll(firstList));
        Assert.assertTrue(mgetLast.keySet().containsAll(secondList));

        keyValueService.del(category, folder, keyList);
    }

    private void sleep(int seconds) {
        try {
            TimeUnit.SECONDS.sleep(seconds);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
