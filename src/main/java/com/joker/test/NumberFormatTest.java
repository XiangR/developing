package com.joker.test;

import com.google.common.collect.Lists;

import java.util.List;

/**
 * Created by xiangrui on 2018/4/19.
 *
 * @author xiangrui
 * @date 2018/4/19
 */
public class NumberFormatTest {

    public static void main(String[] args) {

        List<String> list = Lists.newArrayList();
        list.add("ASN00000033");
        list.add("ASN00000032");
        list.add("ASN00000031");
        list.add("ASN00000030");
        list.add("ASN00000029");
        list.add("ASN00000028");
        list.add("ASN00000027");
        list.add("ASN00000026");
        list.add("ASN00000025");
        list.add("ASN00000024");
        list.add("ASN00000023");
        list.add("ASN00000022");
        list.add("ASN00000021");
        list.add("ASN00000020");
        list.add("ASN00000019");
        list.add("ASN00000018");
        list.add("ASN00000017");
        list.add("ASN00000016");
        list.add("ASN00000015");
        list.add("ASN00000014");
        list.add("ASN00000013");
        list.add("ASN00000012");
        list.add("ASN00000011");
        list.add("ASN00000010");
        list.add("ASN00000009");
        list.add("ASN00000008");
        list.add("ASN00000007");
        list.add("ASN00000006");
        list.add("ASN00000005");
        list.add("ASN00000004");
        list.add("ASN00000003");
        list.add("ASN00000002");
        list.add("ASN00000001");

        list.stream().map(k -> {
            k = k.replace("ASN", "");
            return Long.valueOf(k);
        }).map(NumberFormatTest::generateStockinOrderNo).forEach(System.out::println);
    }

    private static String generateStockinOrderNo(Long var) {
        return String.format("%012d", var);
    }

}
