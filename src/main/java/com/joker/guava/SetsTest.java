package com.joker.guava;

import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Multiset;
import com.google.common.collect.Sets;
import com.google.common.primitives.Ints;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by xiangrui on 2017/12/15.
 *
 * @author xiangrui
 * @date 2017/12/15
 */
public class SetsTest {

    private static Set<String> wordsWithPrimeLength = ImmutableSet.of("one", "two", "three", "six", "seven", "eight");
    private static Set<String> primes = ImmutableSet.of("two", "three", "five", "seven");


    public static void main(String[] args) {
//        union();
//        intersection();
//        difference();
//        symmetricDifference();
//        cartesianProduct();
//        testDifference();
    }

    private static void union() {
        Sets.SetView<String> union = Sets.union(wordsWithPrimeLength, primes);
        Set<String> strings = Sets.newTreeSet(union.immutableCopy());
        // [eight, five, one, seven, six, three, two]
        System.out.println(strings);

    }

    private static void intersection() {
        Sets.SetView<String> intersection = Sets.intersection(wordsWithPrimeLength, primes);
        ImmutableSet<String> strings = intersection.immutableCopy();
        // [two, three, seven]
        System.out.println(strings);
    }

    private static void difference() {
        // 第一个集合中第二个没有的元素
        Sets.SetView<String> difference = Sets.difference(wordsWithPrimeLength, primes);
        ImmutableSet<String> strings = difference.immutableCopy();
        // [one, six, eight]
        System.out.println(strings);

        difference = Sets.difference(primes, wordsWithPrimeLength);
        strings = difference.immutableCopy();
        // [five]
        System.out.println(strings);
    }

    private static void testDifference() {
        Set<Integer> set1 = Sets.newHashSet(1, 2, 3, 4);
        Set<Integer> set2 = Sets.newHashSet(1, 4, 3, 2);

        if (set1.containsAll(set2)) {
            System.out.println("containsAll");
        }
        Sets.SetView<Integer> difference = Sets.difference(set1, set2);
        if (difference.isEmpty()) {
            System.out.println("set1, set1 完全相同");
        } else {
            System.out.println("set1 存在而 set2 中不存在：" + difference.toString());
        }
        final Predicate<Integer> notInSet2 = Predicates.not(Predicates.in(set2));
        set1.stream().filter(notInSet2);

        List<Integer> collect = Arrays.asList(1, 2, 4);
        int supplierIdArr = 1;
        final Predicate<Integer> notInSet3 = Predicates.not(Predicates.in(Ints.asList(supplierIdArr)));
        collect.stream().filter(notInSet3);

    }

    private static void symmetricDifference() {
        // 两个集合中互相不存在的集合的合并
        Sets.SetView<String> difference = Sets.symmetricDifference(wordsWithPrimeLength, primes);
        ImmutableSet<String> strings = difference.immutableCopy();
        // [one, six, eight, five]
        System.out.println(strings);
    }

    public static void cartesianProduct() {
        Set<String> animals = ImmutableSet.of("gerbil", "hamster");
        Set<String> fruits = ImmutableSet.of("apple", "orange", "banana");

        // 笛卡尔集
        Set<List<String>> product = Sets.cartesianProduct(animals, fruits);
        // {{"gerbil", "apple"}, {"gerbil", "orange"}, {"gerbil", "banana"},
        //  {"hamster", "apple"}, {"hamster", "orange"}, {"hamster", "banana"}}
        System.out.println(product);
    }


}
