package com.joker.utils;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.IntStream;

/**
 * Created by xiangrui on 2017/12/28.
 *
 * @author xiangrui
 * @date 2017/12/28
 */
public class RegexUtil {


    public static void main(String[] args) {
//        phone();
//        number();
//        email();
        System.out.println(Pattern.matches(".*[A-Za-z].*+", " 中国 "));
    }

    private static void number() {
        StringBuilder str = new StringBuilder();
        IntStream.rangeClosed(1, 15).forEach((int k) -> str.append(1));

        boolean matches = Pattern.matches("^\\d{15}$", str.toString());
        System.out.println(matches);
    }

    private static void email() {
        // 电子邮件
        String input = "dffdfdf@qq.com";
        Pattern regex = Pattern.compile("^[a-z0-9]+([._\\\\-]*[a-z0-9])*@([a-z0-9]+[-a-z0-9]*[a-z0-9]+.){1,63}[a-z0-9]+$");
//        Pattern regex = Pattern.compile("^([a-z0-9A-Z]+[\\S]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$");
        Matcher matcher = regex.matcher(input);
        boolean isMatched = matcher.matches();
        System.out.println(isMatched);
    }

    private static void phone() {
        String cellphone = "15755054577";
        Pattern regex = Pattern.compile("^((13[0-9])|(14[5|7])|(15([0-3]|[5-9]))|(18[0,5-9]))\\d{8}$");
        Matcher matcher = regex.matcher(cellphone);
        boolean isMatched = matcher.matches();
        System.out.println(isMatched);
    }

    /**
     * 测试存在中文或数字或字符
     */
    private static void password() {
        Pattern pattern = Pattern.compile("^[A-Za-z0-9\\S]{6,20}$");
        List<String> stringList = Arrays.asList("short",
                "231dnsja-p=+",
                "][][abc23n42jkn]",
                "dbsajknd",
                "@#$%^&*()____{})",
                "qwertyuiopasdfghjkl234567892345678][];."
        );

        stringList.forEach(str -> {
            Matcher matcher = pattern.matcher(str);
            System.out.format("matcher: %s\t str: %s\n", matcher.matches(), str);
        });
    }

    /**
     * 测试失败
     *
     * @param str
     */
    private static void password(String str) {
        String pattern = ".*[A-Za-z].*+";
        String patternNumber = ".*[0-9].*+";
        String patternSymbol = ".*[\\S].*+";
        boolean isMatcher;
        isMatcher = Pattern.matches(pattern, str) && Pattern.matches(patternNumber, str) && Pattern.matches(patternSymbol, str);
        System.out.format("matcher: %s\t str: %s\n", isMatcher, str);
    }


    /**
     * 即存在数字也存在字母
     *
     * @param str 检测字符
     * @return boolean
     */
    public static boolean letterAndNumber(String str) {
        String pattern = ".*[A-Za-z].*+";
        String patternNumber = ".*[0-9].*+";
        boolean isMatcher;
        isMatcher = Pattern.matches(pattern, str) && Pattern.matches(patternNumber, str);
//        System.out.format("matcher: %s\t str: %s\n", isMatcher, str);
//        if (!isMatcher) {
//            System.out.format("==========================\n");
//        }
        return isMatcher;
    }


    /**
     * 是否即存在英文，数字，字符
     *
     * @param str 待检测字符
     * @return boolean
     */
    public static boolean passwordCheck(String str) {
        if (!RegexUtil.letterAndNumber(str)) {
            // 不含字母和数字
            return false;
        }
        str = str.trim();
        // 去除字母
        String s1 = str.replaceAll("[A-Za-z]", "");
        // 去除数字
        String s2 = s1.replaceAll("[0-9]", "");
        // 是否存在其他
        return s2.length() > 0;
    }
}
