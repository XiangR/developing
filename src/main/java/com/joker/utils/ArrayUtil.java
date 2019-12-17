package com.joker.utils;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import org.apache.commons.collections4.CollectionUtils;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 * Created by xiangrui on 2017/12/11.
 *
 * @author xiangrui
 * @date 2017/12/11
 */
public class ArrayUtil {


    /**
     * List转int数组
     */
    public static int[] toIntArray(List<Integer> list) {
        if (CollectionUtils.isEmpty(list)) {
            return new int[0];
        }
        int[] ret = new int[list.size()];
        for (int i = 0; i < list.size(); i++) {
            ret[i] = list.get(i);
        }
        return ret;
    }

    /**
     * 数组转集合
     *
     * @param arr
     * @return
     */
    public static List<Integer> toIntegerList(int[] arr) {
        if (arr == null || arr.length == 0) {
            return Lists.newArrayList();
        }
        List list = Lists.newArrayList();
        for (int i = 0; i < arr.length; i++) {
            list.add(arr[i]);
        }
        return list;
    }

    /**
     * 数组的去重复
     *
     * @param arr
     * @return
     */
    public static int[] deduplication(int[] arr) {
        Set<Integer> set = new HashSet<>(toIntegerList(arr));
        Iterator<Integer> iterator = set.iterator();
        int[] ret = new int[set.size()];
        int index = 0;
        while (iterator.hasNext()) {
            ret[index] = iterator.next();
            index++;
        }
        return ret;
    }

    public static void main(String[] args) {
        int[] arr = new int[]{};
        int[] deduplication = deduplication(arr);
        System.out.println(JSON.toJSONString(deduplication));

    }
}
