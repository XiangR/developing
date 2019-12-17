package com.joker.test;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.joker.utils.DateStyle;
import com.joker.utils.DateUtil;

import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by xiangrui on 2018/1/4.
 *
 * @author xiangrui
 * @date 2018/1/4
 */
public class ListTest {


    static class PriceCurvePicResponse {
        public Date time;
        public String price;
        public String schemeType;

        @Override
        public String toString() {
            StringBuilder builder = new StringBuilder();
            return builder.append("[")
                    .append("time: ").append(DateUtil.DateToString(this.time, DateStyle.YYYY_MM_DD_HH_MM_SS)).append(", ")
                    .append("price: ").append(this.price).append(", ")
                    .append("schemeType: ").append(this.schemeType).append("]").toString();
        }
    }

    private static List<PriceCurvePicResponse> priceList;

    public static void main(String[] args) {
        generate1();
        test1();
    }

    /**
     *
     */
    private static void generate3() {
        PriceCurvePicResponse response1 = new PriceCurvePicResponse();
        response1.time = DateUtil.StringToDate("2017-05-02 00:00:00", DateStyle.YYYY_MM_DD_HH_MM_SS);
        response1.price = "322";
        response1.schemeType = "type";
        PriceCurvePicResponse response2 = new PriceCurvePicResponse();
        response2.time = DateUtil.StringToDate("2017-05-03 10:00:00", DateStyle.YYYY_MM_DD_HH_MM_SS);
        response2.price = "1322";
        response2.schemeType = "type2";
        PriceCurvePicResponse response3 = new PriceCurvePicResponse();
        response3.time = DateUtil.StringToDate("2017-05-03 17:00:00", DateStyle.YYYY_MM_DD_HH_MM_SS);
        response3.price = "2322";
        response3.schemeType = "type2";
        PriceCurvePicResponse response4 = new PriceCurvePicResponse();
        response4.time = DateUtil.StringToDate("2017-05-06 10:00:00", DateStyle.YYYY_MM_DD_HH_MM_SS);
        response4.price = "3322";
        response4.schemeType = "type";
        PriceCurvePicResponse response5 = new PriceCurvePicResponse();
        response5.time = DateUtil.StringToDate("2017-05-10 00:00:00", DateStyle.YYYY_MM_DD_HH_MM_SS);
        response5.price = "1322";
        response5.schemeType = "type";
        priceList = Lists.newArrayList(response1, response2, response3, response4, response5);
    }

    /**
     * 跨月份测试
     */
    private static void generate() {
        PriceCurvePicResponse response1 = new PriceCurvePicResponse();
        response1.time = DateUtil.StringToDate("2018-08-01 10:00:00", DateStyle.YYYY_MM_DD_HH_MM_SS);
        response1.price = "90";
        response1.schemeType = "type";
        PriceCurvePicResponse response2 = new PriceCurvePicResponse();
        response2.time = DateUtil.StringToDate("2018-08-06 14:00:00", DateStyle.YYYY_MM_DD_HH_MM_SS);
        response2.price = "80";
        response2.schemeType = "type2";
        PriceCurvePicResponse response3 = new PriceCurvePicResponse();
        response3.time = DateUtil.StringToDate("2018-09-09 15:00:00", DateStyle.YYYY_MM_DD_HH_MM_SS);
//        测试 此凌晨时间是否会重合
//        response3.time = DateUtil.StringToDate("2018-09-09 00:00:00", DateStyle.YYYY_MM_DD_HH_MM_SS);
        response3.price = "90";
        response3.schemeType = "type";
        PriceCurvePicResponse response4 = new PriceCurvePicResponse();
        response4.time = DateUtil.StringToDate("2018-10-15 10:00:00", DateStyle.YYYY_MM_DD_HH_MM_SS);
        response4.price = "90";
        response4.schemeType = "type";
        priceList = Lists.newArrayList(response1, response2, response3, response4);
    }

    /**
     * 最简单的案例测试
     */
    private static void generate1() {
        PriceCurvePicResponse response1 = new PriceCurvePicResponse();
        response1.time = DateUtil.StringToDate("2018-08-01 10:00:00", DateStyle.YYYY_MM_DD_HH_MM_SS);
        response1.price = "90";
        response1.schemeType = "type";

        PriceCurvePicResponse response2 = new PriceCurvePicResponse();
        response2.time = DateUtil.StringToDate("2019-09-15 10:00:00", DateStyle.YYYY_MM_DD_HH_MM_SS);
        response2.price = "90";
        response2.schemeType = "type";
        priceList = Lists.newArrayList(response1, response2);
    }

    /**
     * 同一天的多节点测试
     */
    private static void generate2() {
        PriceCurvePicResponse response1 = new PriceCurvePicResponse();
        response1.time = DateUtil.StringToDate("2018-08-01 10:00:00", DateStyle.YYYY_MM_DD_HH_MM_SS);
        response1.price = "90";
        response1.schemeType = "type";

        PriceCurvePicResponse response2 = new PriceCurvePicResponse();
        response2.time = DateUtil.StringToDate("2018-08-06 14:00:00", DateStyle.YYYY_MM_DD_HH_MM_SS);
        response2.price = "80";
        response2.schemeType = "type2";

        PriceCurvePicResponse response3 = new PriceCurvePicResponse();
        response3.time = DateUtil.StringToDate("2018-08-06 17:00:00", DateStyle.YYYY_MM_DD_HH_MM_SS);
        response3.price = "50";
        response3.schemeType = "type3";

        PriceCurvePicResponse response4 = new PriceCurvePicResponse();
        response4.time = DateUtil.StringToDate("2018-08-06 19:00:00", DateStyle.YYYY_MM_DD_HH_MM_SS);
        response4.price = "70";
        response4.schemeType = "type4";

        PriceCurvePicResponse response5 = new PriceCurvePicResponse();
        response5.time = DateUtil.StringToDate("2018-09-09 15:00:00", DateStyle.YYYY_MM_DD_HH_MM_SS);
        response5.price = "90";
        response5.schemeType = "type";

        PriceCurvePicResponse response6 = new PriceCurvePicResponse();
        response6.time = DateUtil.StringToDate("2018-09-10 00:00:00", DateStyle.YYYY_MM_DD_HH_MM_SS);
        response6.price = "80";
        response6.schemeType = "type2";

        PriceCurvePicResponse response7 = new PriceCurvePicResponse();
        response7.time = DateUtil.StringToDate("2019-01-16 10:00:00", DateStyle.YYYY_MM_DD_HH_MM_SS);
        response7.price = "90";
        response7.schemeType = "type";
        priceList = Lists.newArrayList(response1, response2, response3, response4, response5, response6, response7);
    }

    /**
     * 1：从第一个时间节点遍历到倒数第二个，取 index[i] -> leftTime 与 index[i+1] -> rightTime
     * 2：默认添加 leftTime
     * 2：若是 leftTime 与 rightTime 相差超过一天，则遍历间隔天数依次 leftTime.plusDays(i)，价格取 leftTime 对应 leftPrice
     * 3：若是 leftTime 与 rightTime 为同一天，则 continue
     * 4：默认补充上结尾时间
     */
    private static void test1() {

        Map<Date, PriceCurvePicResponse> priceMap = Maps.newHashMap();
        for (PriceCurvePicResponse priceCurvePicResponse : priceList) {
            priceMap.put(priceCurvePicResponse.time, priceCurvePicResponse);
        }

        priceList.sort(Comparator.comparing(k -> k.time));
        List<PriceCurvePicResponse> resultList = Lists.newArrayList();

        int cycle = priceList.size() - 1;
        for (int i = 0; i < cycle; i++) {
            Date leftTime = priceList.get(i).time;
            Date rightTime = priceList.get(i + 1).time;

            PriceCurvePicResponse leftCurvePrice = priceMap.get(leftTime);
            // 默认添加左边时间节点
            resultList.add(leftCurvePrice);
            int daysBetween = DateUtil.daysBetween(leftTime, rightTime);
            if (daysBetween == 0) {
                continue;
            }
            for (int j = 1; j <= daysBetween; j++) {
                Date date = DateUtil.getDayBegin(DateUtil.addDay(leftTime, j));
                if (date.equals(rightTime)) {
                    continue;
                }
                resultList.add(convertCurvePic(date, leftCurvePrice.price, leftCurvePrice.schemeType));
            }
            if (i == cycle - 1) {
                // 添加结尾时间节点
                resultList.add(convertCurvePic(rightTime, leftCurvePrice.price, leftCurvePrice.schemeType));
            }
        }
        // resultList.sort(Comparator.comparing(p -> p.time));
        resultList.forEach(System.out::println);
    }

    private static PriceCurvePicResponse convertCurvePic(Date time, String price, String schemeType) {
        PriceCurvePicResponse nextDayPrice = new PriceCurvePicResponse();
        nextDayPrice.time = time;
        nextDayPrice.price = price;
        nextDayPrice.schemeType = schemeType;
        return nextDayPrice;
    }
}
