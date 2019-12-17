package com.joker.guava;

import com.google.common.base.Function;
import com.google.common.collect.*;
import com.joker.model.LevelOneCategoryInfo;
import org.javatuples.Pair;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Created by xiangrui on 2017/12/15.
 *
 * @author xiangrui
 * @date 2017/12/15
 */
public class MapsTest {

    public static void main(String[] args) {
//        uniqueIndex();
//        difference();
        difference1();
    }

    // lambda 的使用
    //  Function function = (Function<LevelOneCategoryInfo, Integer>) LevelOneCategoryInfo::getId;
    static Function function = new Function<LevelOneCategoryInfo, Integer>() {
        @Override
        public Integer apply(LevelOneCategoryInfo o) {
            return o.getId();
        }
    };

    /**
     * uniqueIndex -> 有一组对象，它们在某个属性上分别有独一无二的值，而我们希望能够按照这个属性值查找对象
     * List<Object> -> Map<Integer, Object> ==> ImmutableMap<Integer, Object>
     * ImmutableMap 直接实现 Map，其主要特点是不可变性，对其进行 put remove 都直接报 UnsupportedOperationException
     */
    private static void uniqueIndex() {
        LevelOneCategoryInfo levelOneCategoryInfo1 = new LevelOneCategoryInfo();
        levelOneCategoryInfo1.setId(1);
        levelOneCategoryInfo1.setName("类目一");
        LevelOneCategoryInfo levelOneCategoryInfo2 = new LevelOneCategoryInfo();
        levelOneCategoryInfo2.setId(2);
        levelOneCategoryInfo2.setName("类目二");
        LevelOneCategoryInfo levelOneCategoryInfo5 = new LevelOneCategoryInfo();
        levelOneCategoryInfo5.setId(5);
        levelOneCategoryInfo5.setName("类目五");

        List<LevelOneCategoryInfo> categoryInfoList = Lists.newArrayList(levelOneCategoryInfo1, levelOneCategoryInfo2, levelOneCategoryInfo5);

        // 得到的是一个不可变 Map
        ImmutableMap<Integer, LevelOneCategoryInfo> immutableMap1 = Maps.uniqueIndex(categoryInfoList, function);
        LevelOneCategoryInfo levelOneCategoryInfo = immutableMap1.get(1);
        System.out.println(levelOneCategoryInfo);
        System.out.format("=========== %s end ===========\n", Thread.currentThread().getStackTrace()[1].getMethodName());
    }

    /**
     * 查询两个map 中不同的内容
     * <p>
     * MapDifference 存在四个类型
     * inCommon：键与值全部匹配的部分
     * differing：键相同但是值不同值映射项
     * onlyOnLeft：键只存在于左边 Map
     * onlyOnRight：键只存在于右边 Map
     */
    private static void difference() {
        Map<String, Integer> left = ImmutableMap.of("a", 1, "b", 2, "c", 3, "f", 8);
        Map<String, Integer> right = ImmutableMap.of("b", 2, "c", 3, "d", 5, "f", 9);
        MapDifference<String, Integer> diff = Maps.difference(left, right);

        Map<String, Integer> inCommon = diff.entriesInCommon();// {"b" => 2,"c" => 3}
        System.out.format("inCommon: %s \n", inCommon);

        Map<String, MapDifference.ValueDifference<Integer>> differenceMap = diff.entriesDiffering();// {"f" => (8,9)}
        MapDifference.ValueDifference<Integer> f = differenceMap.get("f");
        System.out.format("differenceMap: %s \n", differenceMap);
        System.out.format("differenceMap -> leftValue: %s, rightValue: %s \n", f.leftValue(), f.rightValue());

        Map<String, Integer> leftMap = diff.entriesOnlyOnLeft();// {"a" => 1}
        System.out.format("leftMap: %s \n", leftMap);

        Map<String, Integer> rightMap = diff.entriesOnlyOnRight();// {"d" => 5}
        System.out.format("rightMap: %s \n", rightMap);
        System.out.format("=========== %s end ===========\n", Thread.currentThread().getStackTrace()[1].getMethodName());
    }

    private static void difference1() {

        List<String> list1 = Arrays.asList("A", "B", "C");
        List<String> list2 = Arrays.asList("B", "C", "D");

        System.out.println(difference2(list1, list2));
    }

    private static void difference2() {

        long l = System.currentTimeMillis();

        System.out.println();

        List<String> list1 = IntStream.range(1, 1000000).mapToObj(k -> UUID.randomUUID().toString()).collect(Collectors.toList());
        List<String> list2 = IntStream.range(1, 1000000).mapToObj(k -> UUID.randomUUID().toString()).collect(Collectors.toList());

        long l2 = System.currentTimeMillis();

        difference(list1, list2);

        long l3 = System.currentTimeMillis();

        System.out.println(l2 - l);
        System.out.println(l3 - l2);
    }

    private static <O> Pair<Set<O>, Set<O>> difference(
            final Iterable<? extends O> a,
            final Iterable<? extends O> b
    ) {
        Objects.requireNonNull(a);
        Objects.requireNonNull(b);

        Map<O, Object> map_a = Maps.newHashMap();
        for (O o : a) {
            map_a.put(o, null);
        }

        Map<O, Object> map_b = Maps.newHashMap();
        for (O o : b) {
            map_b.put(o, null);
        }
        MapDifference<O, Object> difference = Maps.difference(map_a, map_b);
        return Pair.with(difference.entriesOnlyOnLeft().keySet(), difference.entriesOnlyOnRight().keySet());
    }

    private static final Object PRESENT = new Object();

    private static <O> Pair<Set<O>, Set<O>> difference2(
            final Iterable<? extends O> a_list,
            final Iterable<? extends O> b_list
    ) {
        Objects.requireNonNull(a_list);
        Objects.requireNonNull(b_list);

        Map<O, Object> a_map = new HashMap<>();
        for (O o : a_list) {
            a_map.put(o, PRESENT);
        }

        Map<O, Object> b_map = new HashMap<>();
        for (O o : b_list) {
            b_map.put(o, PRESENT);
        }

        Set<O> left_set = new HashSet<>();
        for (O o : a_list) {
            if (!b_map.containsKey(o)) {
                left_set.add(o);
            }
        }

        Set<O> right_set = new HashSet<>();
        for (O o : b_list) {
            if (!a_map.containsKey(o)) {
                right_set.add(o);
            }
        }
        return Pair.with(left_set, right_set);
    }
}


