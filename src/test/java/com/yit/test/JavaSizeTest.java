package com.yit.test;

import com.google.common.collect.Lists;
import com.joker.test.ObjectSizeCalculator;
import org.junit.Test;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * 使用BeanUtils 进行对象copy时，其 source 与 target 对应的类都必须要有 get set 方法
 * Created by xiangrui on 2018/1/18.
 *
 * @author xiangrui
 * @date 2018/1/18
 */
public class JavaSizeTest {


    private static final int _1MB = 1024 * 1024;

    /**
     * 转换供应商基础信息到boss端
     */
    @Test
    public void test() {


        List<Integer> integers = Lists.newArrayList(1, 2, 3, 4);


        System.out.println(ObjectSizeCalculator.getObjectSize(integers));

        System.out.println(ObjectSizeCalculator.getObjectSize(1));


        List<List<Integer>> collect = IntStream.rangeClosed(0, 1000).mapToObj(k -> integers).collect(Collectors.toList());

        long objectSize = ObjectSizeCalculator.getObjectSize(collect);
        System.out.println(objectSize);

        if (objectSize > _1MB) {
            System.out.println("too lager");
        }

    }


}
