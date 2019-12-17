package com.joker.test;

import org.apache.commons.lang3.StringUtils;

import java.util.regex.Pattern;

/**
 * Created by xiangrui on 2017/12/29.
 *
 * @author xiangrui
 * @date 2017/12/29
 */
public class StringTest {

    final static String HTTP = "http";
    final static String HTTPS = "https";


    public static void main(String[] args) {
//        String string = "HTTP://www.baidu.com/";
//        test(string);
        Integer val1 = 1;
        Integer val2 = null;
        System.out.println(val1.equals(val2));
        System.out.println(val2.equals(val1));

        String str = null;
        System.out.println(HTTP.equals(null));
        System.out.println(str.equals(HTTP));

    }


    private static void test(String url) {

        boolean invalid = StringUtils.isNotBlank(url) && (url.toLowerCase().startsWith(HTTP) || url.toLowerCase().startsWith(HTTPS));
        System.out.println(url);
        System.out.println(invalid);
    }


}
