package com.joker.test;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.HashMultiset;
import com.google.common.collect.Lists;
import com.google.common.collect.Multiset;
import com.joker.entity.Func2;
import org.apache.commons.collections4.ComparatorUtils;

import java.text.Collator;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 中文名称的排序
 * <p>
 * Created by xiangrui on 2018/10/9.
 *
 * @author xiangrui
 * @date 2018/10/9
 */
public class RepeatTest {


    public static void main(String[] args) {

        List<NameSort> list = Lists.newArrayList(new NameSort("Joker"), new NameSort("Joker"), new NameSort("Joker-xiangR"));

        List<Multiset.Entry<String>> repeatEntry = getRepeatEntry(list, k -> k.name);
        // [{"count":2,"element":"Joker"}]
        System.out.println(JSON.toJSONString(repeatEntry));

        List<String> repeatElement = getRepeatElement(list, k -> k.name);
        //["Joker"]
        System.out.println(JSON.toJSONString(repeatElement));
    }


    public static <T, R> List<Multiset.Entry<R>> getRepeatEntry(List<T> list, Function<T, R> map) {
        List<R> checkList = new ArrayList<>();
        for (T t : list) {
            R apply = map.apply(t);
            checkList.add(apply);
        }
        HashMultiset<R> rs = HashMultiset.create(checkList);
        return rs.entrySet().stream().filter(k -> k.getCount() > 1).collect(Collectors.toList());
    }

    public static <T, R> List<R> getRepeatElement(List<T> list, Function<T, R> map) {
        List<Multiset.Entry<R>> repeatEntryList = getRepeatEntry(list, map);
        return repeatEntryList.stream().map(Multiset.Entry::getElement).collect(Collectors.toList());
    }

    public static class NameSort {

        public String name;
        public int age;

        public NameSort() {
        }

        public NameSort(String name) {
            this.name = name;
        }

        public NameSort(String name, int age) {
            this.name = name;
            this.age = age;
        }

        @Override
        public String toString() {
            return JSON.toJSONString(this);
        }
    }
}
