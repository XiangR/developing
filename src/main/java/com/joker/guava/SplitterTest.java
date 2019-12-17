package com.joker.guava;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.common.base.CharMatcher;
import com.google.common.base.Joiner;
import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import com.joker.utils.CommonConfig;
import com.sun.tools.javac.util.Assert;
import org.apache.commons.collections4.CollectionUtils;

import java.util.*;

/**
 * Created by xiangrui on 2017/12/14.
 *
 * @author xiangrui
 * @date 2017/12/14
 */
public class SplitterTest {

    /*
     * trimResults()   去除sequence 中的空格
     *
     */

    public static void main(String[] args) {
//        test1();
//        test2();
//        test3();

        List<String> markList = Lists.newArrayList();
        markList.add("去除seq中wwwwww");
        markList.add("trimResults()   去除sequence 中的空格");
        System.out.println(substring(markList, 10));
    }

    private static String substring(List<String> markList, int max) {
        if (CollectionUtils.isEmpty(markList)) {
            return null;
        }
        String join = Joiner.on(CommonConfig.COMMA).skipNulls().join(markList);
        if (join.length() > max) {
            String substring = join.substring(0, max);
            // Splitter 返回的List 默认是实现的 AbstractList 在 remove 的时候会报错
            // 需要转化为一个有实现的List
            List<String> strings = Lists.newArrayList(Splitter.on(CommonConfig.COMMA).splitToList(substring));
            if (strings.size() > 1) {
                strings.remove(strings.size() - 1);
            }
            return Joiner.on(CommonConfig.COMMA).skipNulls().join(strings);
        }
        return join;
    }


    private static void test1() {
        String sequence = "wrong/wrong/wrong/wrong/wrong";

        List<String> strings = Splitter.on('/').splitToList(sequence);
        // ["wrong","wrong","wrong","wrong","wrong"]
        System.out.println(JSON.toJSONString(strings));
        Assert.check(strings.size() == 5);

        List<String> strings1 = Splitter.on('/').splitToList(sequence).subList(0, 3);
        Assert.check(strings1.size() == 3);
        // ["wrong","wrong","wrong"]
        System.out.println(JSON.toJSONString(strings1));

        List<String> strings2 = Splitter.on('/').limit(3).splitToList(sequence);
        // ["wrong","wrong","wrong/wrong/wrong"]
        System.out.println(JSON.toJSONString(strings2));
        System.out.format("=========== %s end ===========\n", Thread.currentThread().getStackTrace()[1].getMethodName());
    }

    private static void test2() {
        String sequence = "a=2; c=4;b=3";
        Map<String, String> split = Splitter.on(';').trimResults().withKeyValueSeparator("=").split(sequence);

        String toJSONString = JSON.toJSONString(split);

        System.out.println(toJSONString);

        HashMap<String, Integer> hashMap = JSONObject.parseObject(toJSONString, new HashMap<String, Integer>().getClass());
        TreeMap treeMap = JSONObject.parseObject(toJSONString, TreeMap.class);
        System.out.format("=========== %s end ===========\n", Thread.currentThread().getStackTrace()[1].getMethodName());
    }

    private static void test3() {
        List<String> split = Splitter.on(',')
                .trimResults()
                .omitEmptyStrings() // 排除空字符串
                .splitToList("foo,bar,,   qux");
        System.out.format("split: %s \n", split);
        System.out.format("=========== %s end ===========\n", Thread.currentThread().getStackTrace()[1].getMethodName());
    }

    private static void charMatcher() {
        String string = "";
        String noControl = CharMatcher.javaIsoControl().removeFrom(string); //移除control字符
        String theDigits = CharMatcher.digit().retainFrom(string); //只保留数字字符
        String spaced = CharMatcher.whitespace().trimAndCollapseFrom(string, ' ');//去除两端的空格，并把中间的连续空格替换成单个空格
        String noDigits = CharMatcher.javaDigit().replaceFrom(string, "*"); //用*号替换所有数字
        String lowerAndDigit = CharMatcher.javaDigit().or(CharMatcher.javaLowerCase()).retainFrom(string);// 只保留数字和小写字母
    }
}
