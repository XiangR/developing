package com.joker.test;

import com.joker.utils.RegexUtil;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

/**
 * Created by xiangrui on 2018/1/4.
 *
 * @author xiangrui
 * @date 2018/1/4
 */
public class RegexTest {

    public static void main(String[] args) {
        password2();
    }

    private static void password2() {

        List<String> stringList = Arrays.asList(
                "short12",
                "short12",
                "short12}",
                "12",
                "sho1",
                "shortshort",
                "231dnsja-p=+",
                "][][abc23n42jkn]"
        );
        //stringList.forEach(RegexTest::passwordCheck);
        stringList.forEach((String k) -> {
            boolean isMatcher = RegexUtil.passwordCheck(k);
            System.out.format("matcher: %s\t str: %s\n", isMatcher, k);
        });
    }

    private static void passwordCheck(String str) {
        if (!RegexUtil.letterAndNumber(str)) {
            return;
        }

        String pattern = "[A-Za-z]";
        String patternNumber = "[0-9]";

        System.out.format("original: %s\n", str);
        str = str.trim();
        System.out.format("trim: %s\n", str);

        String s = str.replaceAll(pattern, "");
        System.out.format("pattern: %s\n", s);

        String s1 = s.replaceAll(patternNumber, "");
        System.out.format("patternNumber: %s\n", s1);

        System.out.format("hasOther: %s \n", s1.length() > 1);
        System.out.format("==========================\n");
    }
}
