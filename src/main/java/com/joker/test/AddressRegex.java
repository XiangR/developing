package com.joker.test;

import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by xiangrui on 2017/12/29.
 *
 * @author xiangrui
 * @date 2017/12/29
 */
public class AddressRegex {

    public static void main(String[] args) {
        test();
    }

    public static void replace() {

        // 改进的逻辑匹配完成后使用分组的形式进行获取
        String content = "长乐西路99号双鱼大厦一层和(西京医院)";
        String regex = "^([\\u4e00-\\u9fa5]+)([0-9]*)(号)([\\u4e00-\\u9fa5]+)(和)\\(([\\u4e00-\\u9fa5]+)\\)";
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(content);
        print(m);
    }

    private static Map<String, String> regexList = new LinkedHashMap<String, String>() {
        {
            put("^([\\u4e00-\\u9fa5]+省)([\\u4e00-\\u9fa5]+市)([\\u4e00-\\u9fa5]+[区县])([\\u4e00-\\u9fa5_()a-zA-Z0-9]+)", "province");
            put("^([\\u4e00-\\u9fa5]+市)([\\u4e00-\\u9fa5]+[区县])([\\u4e00-\\u9fa5_()a-zA-Z0-9]+)", "city");
        }
    };

    private static void test() {

        List<String> strings = Arrays.asList("安徽省滁州市南谯县丰乐大道1528号", "上海市普陀区真南路150号C233");
        strings.forEach(AddressRegex::regexAddress);
    }

    private static void regexAddress(String input) {
        if (StringUtils.isEmpty(input)) {
            return;
        }
        for (Map.Entry<String, String> entry : regexList.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            if (Pattern.matches(key, input)) {
                Matcher matcher = Pattern.compile(key).matcher(input);
                List<String> value1 = getValue(matcher);
                System.out.println(value1);
                if (value.equals("province")) {
                    // province;
                    return;
                }
                if (value.equals("city")) {
                    // city
                    return;
                }
            }
        }

    }

    private static void regexProvince() {
        String content = "安徽省滁州市南谯县丰乐大道1528号";
        Pattern pattern = Pattern.compile("^([\\u4e00-\\u9fa5]+省)([\\u4e00-\\u9fa5]+市)([\\u4e00-\\u9fa5]+[区县])([\\u4e00-\\u9fa5_()a-zA-Z0-9]+)");
        Matcher matcher = pattern.matcher(content);
        print(matcher);
    }

    private static void replaceCity() {
        String content = "上海市普陀区真南路150号C233";
        Pattern p = Pattern.compile("^([\\u4e00-\\u9fa5]+市)([\\u4e00-\\u9fa5]+[区县])([\\u4e00-\\u9fa5_()a-zA-Z0-9]+)");
        Matcher m = p.matcher(content);
        print(m);
    }


    private static List<String> getValue(Matcher m) {
        List<String> values = Lists.newArrayList();
        if (m.find()) {
            int count = m.groupCount();
            for (int i = 1; i <= count; i++) {
                values.add(m.group(i));
            }
        }
        return values;
    }

    private static void print(Matcher m) {
        if (!m.find()) {
            System.out.println("未成功匹配");
        }

        do {
            System.out.println(" 总过匹配的个数： " + m.groupCount());
            System.out.println(" 总过匹配的结果： " + m.group());
            int count = m.groupCount();
            for (int i = 1; i <= count; i++) {
                System.out.println(" 分组 " + i + ": " + m.group(i));
            }
        } while (m.find());
    }

}
