package com.joker.test;

import com.alibaba.fastjson.JSONArray;

import java.util.Arrays;
import java.util.List;

public enum EnumTest {


    CREATE((byte) 0, "未提交"),

    SUBMIT((byte) 1, "已生效");

    private byte code;

    private String desc;

    EnumTest(byte code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public static String getDesc(byte code) {
        for (EnumTest statusEnum : EnumTest.values()) {
            if (statusEnum.code == code) {
                return statusEnum.desc;
            }
        }
        return null;
    }


    public byte getCode() {
        return code;
    }

    public void setCode(byte code) {
        this.code = code;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }


    public static void main(String[] args) {
        System.out.println(EnumTest.CREATE.name());
        Arrays.asList(EnumTest.values()).forEach(k -> System.out.println("k -> " + k));

        List<String> strings = Arrays.asList("1", "2", "3");
        JSONArray.toJSONString(strings);
        System.out.println(strings);

        strings.stream().forEach(k -> k.toString());
    }
}
