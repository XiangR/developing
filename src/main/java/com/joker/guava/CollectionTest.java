package com.joker.guava;


import com.google.common.collect.Lists;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.util.Assert;

import java.util.Arrays;
import java.util.List;

/**
 * Created by xiangrui on 2018/9/18.
 *
 * @author xiangrui
 * @date 2018/9/18
 */
public class CollectionTest {


    public static void main(String[] args) {

        subtract();
    }

    private static void subtract() {

        List<String> list1 = Arrays.asList("A", "B", "C");
        List<String> list2 = Arrays.asList("B", "C", "D");

        List<String> subtractList1 = Lists.newArrayList(CollectionUtils.subtract(list1, list2));

        List<String> subtractList2 = Lists.newArrayList(CollectionUtils.subtract(list2, list1));

        Assert.isTrue(subtractList1.equals(Lists.newArrayList("A")));
        Assert.isTrue(subtractList2.equals(Lists.newArrayList("D")));
    }
}
