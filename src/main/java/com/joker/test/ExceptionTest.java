package com.joker.test;

import com.alibaba.fastjson.JSONArray;
import com.joker.model.ServiceRuntimeException;

import java.util.Arrays;
import java.util.List;

/**
 * Created by xiangrui on 2017/11/27.
 *
 * @author xiangrui
 * @date 2017/11/27
 */
public class ExceptionTest {


    public static void main(String[] args) {

        try {
            throwMethod(null);
        } catch (Exception e) {
            System.out.println("catch 到了");
        }


        List<String> strings = Arrays.asList("1", "2", "3");
        JSONArray.toJSONString(strings);
        System.out.println(strings);

        strings.stream().forEach(k -> k.toString());
    }

    public static void throwMethod(String s) {
        if (s == null) {
            throw new ServiceRuntimeException("错误了：", 1);
        }
    }

}
