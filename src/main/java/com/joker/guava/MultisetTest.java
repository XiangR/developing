package com.joker.guava;

import com.google.common.collect.*;
import com.joker.utils.CollectionUtil;
import com.joker.utils.CommonConfig;
import com.sun.tools.javac.util.Assert;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 给定一个集合来查找集合中每个元素重复的数量
 * 并且正序排列
 * <p>
 * Created by xiangrui on 2017/12/14.
 *
 * @author xiangrui
 * @date 2017/12/14
 */
public class MultisetTest {

    static List<String> words = new ArrayList<String>() {
        {
            add("1");
            add("2");
            add("3");
            add("4");
            add("5");
            add("5");
            add("3");
            add(null);
        }
    };

    /**
     * 自定义比较器
     */
    // private static Comparator<String> comparator = (s1, s2) -> s1 == null || s2 == null ? -1 : s2.compareTo(s1);
    private static Comparator<String> comparator = new Comparator<String>() {
        @Override
        public int compare(String s1, String s2) {
            return s1 == null || s2 == null ? -1 : s2.compareTo(s1);
        }
    };

    private static Comparator<Integer> comparator1 = (s1, s2) -> s1 == null || s2 == null ? -1 : s2.compareTo(s1);

    public static void main(String[] args) {
//        test();
//        test1();
//        test2();
//        test3();
//        test4();
//        test5();
        test6();
    }

    /**
     * 查询存在重复的部分
     */
    public static void test6() {

        List<String> strings = Lists.newArrayList("A", "B", "C", "A", "B", "D");

        Multiset<String> multiset = HashMultiset.create(strings);

        List<Multiset.Entry<String>> collect = multiset.entrySet().stream().filter(k -> k.getCount() > 1).collect(Collectors.toList());

        if (!collect.isEmpty()) {
            String collect1 = collect.stream().map(Multiset.Entry::getElement).collect(Collectors.joining(CommonConfig.COMMA));
            System.out.println(collect1);
        }

    }

    /**
     * 了解 Multiset
     */
    public static void test() {
        Multiset<String> multiset = HashMultiset.create(words);

        System.out.format("multiset size: %s \n", multiset.size()); // 8

        Set<Multiset.Entry<String>> entries = multiset.entrySet();
        System.out.format("multiset entrySet size : %s \n", entries.size());// 6

        Set<String> strings = multiset.elementSet();
        System.out.format("multiset elementSet size: %s \n", strings.size());// 6

        // operator
        int count1 = multiset.count("3"); // 2
        multiset.remove("3");
        int count2 = multiset.count("3");// 1

        multiset.add("3");
        int count3 = multiset.count("3");// 2

        int add = multiset.add("3", 4); // (add -> oldCount)
        int count4 = multiset.count("3");// 6

        multiset.remove("3", Integer.MAX_VALUE);// remove 的数量超出时不会报错，可以使用 MAX_VALUE 表示清空
        int count5 = multiset.count("3"); // 0
        boolean contains3 = multiset.contains("3"); // false 清空后此元素消失

        multiset.setCount("3", 0);//移除所有 elem

        System.out.format("count1: %s, count2: %s, count3: %s, add: %s, count4: %s, count5: %s ,contains3: %s \n", count1, count2, count3, add, count4, count5, contains3);
    }


    /**
     * 使用 TreeMultiset 实现
     */
    public static void test1() {
        Multiset<String> multiset = TreeMultiset.create(comparator);
        multiset.addAll(words);
        Map<String, Integer> collect = Maps.newLinkedHashMap();
        for (Multiset.Entry<String> entry : multiset.entrySet()) {
            collect.put(entry.getElement(), entry.getCount());
        }
        System.out.format("HashMultiset: %s \n", collect);
    }

    /**
     * 使用 Multiset 实现
     */
    public static void test2() {
        Multiset<String> multiset = HashMultiset.create(words);

        Map<String, Integer> collect = Maps.newTreeMap(comparator);
        for (Multiset.Entry<String> entry : multiset.entrySet()) {
            collect.put(entry.getElement(), entry.getCount());
        }
        System.out.format("HashMultiset: %s \n", collect);
    }

    /**
     * 最基本的循环实现
     */
    public static void test3() {
        Map<String, Integer> counts = new TreeMap<>(comparator);
        for (String word : words) {
            Integer count = counts.get(word);
            counts.put(word, count == null ? 1 : count + 1);
        }
        System.out.format("HashMultiset: %s \n", counts);
    }

    /**
     * 使用 Collections 工具类实现
     */
    public static void test4() {
        Set<String> strings = Sets.newHashSet(words);
        Map<String, Integer> stringObjectTreeMap = Maps.newTreeMap(comparator);
        for (String string : strings) {
            stringObjectTreeMap.put(string, Collections.frequency(words, string));
        }
        System.out.format("HashMultiset: %s \n", stringObjectTreeMap);
    }

    /**
     * stream的弊端，不能只能treeMap 的正逆序
     */
    public static void test5() {
        Set<String> strings = Sets.newHashSet(words);
        TreeMap<String, Integer> collect = strings.stream().filter(Objects::nonNull).collect(Collectors.toMap(k -> k, k -> Collections.frequency(words, k), (k, v) -> k, TreeMap::new));
        System.out.format("HashMultiset: %s \n", collect);
    }


}
