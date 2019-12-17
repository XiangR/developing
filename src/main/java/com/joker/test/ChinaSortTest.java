package com.joker.test;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.joker.entity.Func2;
import org.apache.commons.collections4.ComparatorUtils;

import java.text.Collator;
import java.util.*;

/**
 * 中文名称的排序
 * <p>
 * Created by xiangrui on 2018/10/9.
 *
 * @author xiangrui
 * @date 2018/10/9
 */
public class ChinaSortTest {


    public static void main(String[] args) {
        List<NameSort> list = getList();

        List<NameSort> nameSorts1 = sortWithChina(list, k -> k.name);
        System.out.println(JSON.toJSONString(nameSorts1));
    }

    private static List<NameSort> sort1(List<NameSort> list) {
        Comparator cmp = Collator.getInstance(java.util.Locale.CHINA);
        Comparator<NameSort> boolean_c = (v1, v2) -> cmp.compare(v1.name, v2.name);
        list.sort(boolean_c);
        return list;
    }

    private static List<NameSort> getList() {
        List<NameSort> list = Lists.newArrayList();
        {
            NameSort sku = new NameSort();
            sku.name = "A";
            list.add(sku);
        }
        {
            NameSort sku = new NameSort();
            sku.name = null;
            list.add(sku);
        }
        {
            NameSort sku = new NameSort();
            sku.name = "相瑞";
            list.add(sku);
        }
        {
            NameSort sku = new NameSort();
            sku.name = "代婉瑞";
            list.add(sku);
        }
        {
            NameSort sku = new NameSort();
            sku.name = "B";
            list.add(sku);
        }
        return list;
    }

    private static <T> List<T> sortWithChina(List<T> entityList, Func2<String, T> funcWithString) {

        List<T> newList = Lists.newArrayList(entityList);
        // 中文的排序
        Comparator cmp = Collator.getInstance(java.util.Locale.CHINA);

        Comparator<T> china_sort = (v1, v2) -> cmp.compare(funcWithString.invoke(v1), funcWithString.invoke(v2));
        newList.sort(china_sort);

        return newList;
    }

    private static <T> List<T> sortWithChina(List<T> entityList, Func2<String, T> fun, boolean nullsAreHigh) {

        List<T> newList = Lists.newArrayList(entityList);
        // 中文的排序
        Comparator collator = Collator.getInstance(java.util.Locale.CHINA);
        Comparator comparator;
        if (nullsAreHigh) {
            comparator = ComparatorUtils.nullHighComparator(collator);
        } else {
            comparator = ComparatorUtils.nullLowComparator(collator);
        }

        Comparator<T> china_sort = (v1, v2) -> comparator.compare(fun.invoke(v1), fun.invoke(v2));
        newList.sort(china_sort);

        return newList;
    }

    public static class NameSort {

        public String name;

        @Override
        public String toString() {
            return JSON.toJSONString(this);
        }
    }
}
