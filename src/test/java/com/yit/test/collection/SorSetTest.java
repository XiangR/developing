package com.yit.test.collection;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import org.junit.Assert;
import org.junit.Test;

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by xiangrui on 2019-09-25.
 *
 * @author xiangrui
 * @date 2019-09-25
 */
public class SorSetTest {

    private static List<Integer> integers = Lists.newArrayList(1, 4, 4, 7, 3, 3, 2, 5, 5, 7);
    private static List<Integer> integers_1 = Lists.newArrayList(1, 4, 7, 3, 2, 5);

    @Test
    public void test_1() {

        LinkedHashSet<Integer> integers1 = Sets.newLinkedHashSet(integers);

        Assert.assertEquals(JSON.toJSONString(integers1), JSON.toJSONString(integers_1));
    }

    @Test
    public void test_2() {

        List<Integer> collect = integers_1.stream().distinct().collect(Collectors.toList());

        Assert.assertEquals(JSON.toJSONString(collect), JSON.toJSONString(integers_1));
    }


    @Test
    public void test_3() {

        HashSet<Integer> integers = Sets.newHashSet(SorSetTest.integers);

        Assert.assertNotEquals(JSON.toJSONString(integers), JSON.toJSONString(integers_1));

    }

}
