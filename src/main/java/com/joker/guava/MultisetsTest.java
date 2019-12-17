package com.joker.guava;


import com.google.common.collect.HashMultiset;
import com.google.common.collect.Multiset;
import com.google.common.collect.Multisets;

/**
 * Created by xiangrui on 2017/12/16.
 *
 * @author xiangrui
 * @date 2017/12/16
 */
public class MultisetsTest {

    public static void main(String[] args) {
        operator();
    }

    public static void operator() {
        Multiset<String> multiset1 = HashMultiset.create();
        multiset1.add("a", 2);

        Multiset<String> multiset2 = HashMultiset.create();
        multiset2.add("a", 5);

        boolean containsAll = multiset1.containsAll(multiset2);//返回true；因为包含了所有不重复元素，虽然multiset1实际上包含2个"a"，而multiset2包含5个"a"
        System.out.format("containsAll: %s \n", containsAll);

        boolean containsOccurrences = Multisets.containsOccurrences(multiset1, multiset2);// returns false
        System.out.format("containsOccurrences: %s \n", containsOccurrences);

        Multisets.removeOccurrences(multiset1, multiset2);// multiset2 现在包含3个"a"
        int set2Count = multiset2.count("a");
        int set1Count = multiset1.count("a");
        System.out.format("set1Count: %s, set2Count: %s \n", set1Count, set2Count);

        multiset2.removeAll(multiset1);//multiset2移除所有"a"，虽然multiset1只有2个"a"
        boolean empty = multiset2.isEmpty();// returns true
        System.out.format("empty: %s \n", empty);
        System.out.format("=========== %s end ===========\n", Thread.currentThread().getStackTrace()[1].getMethodName());
    }
}
