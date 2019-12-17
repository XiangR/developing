package com.joker.utils;

import com.google.common.collect.Lists;

import java.util.List;

public class SplitJson {

    public static List<String> split(String str) {
        List<String> stringList = Lists.newArrayList();
        split(stringList, str);
        return stringList;
    }

    private static void split(List<String> stringList, String str) {
        if (str == null || str.length() == 0) {
            return;
        }
        str = replaceEmpty(str);

        if (str.startsWith("{")) {
            int index = str.indexOf("}") + 1;
            stringList.add(str.substring(0, index));
            str = str.substring(index);
            split(stringList, str);
        } else if (str.startsWith("[")) {
            int index = str.indexOf("]") + 1;
            stringList.add(str.substring(0, index));
            str = str.substring(index);
            split(stringList, str);
        } else if (str.startsWith(",")) {
            str = remove(str, ",");
            split(stringList, str);
        } else {
            int i = str.indexOf(",");
            if (i < 0) {
                stringList.add(str);
            } else {
                int index = str.indexOf(",") + 1;
                stringList.add(str.substring(0, index));
                str = str.substring(index);
                split(stringList, str);
            }
        }
    }

    private static String remove(String str, String prefix) {
        int index = str.indexOf(prefix) + 1;
        return str.substring(index);
    }

    private static String replaceEmpty(String str) {
        return str.replace(" ", "");
    }
}
