package com.joker.time;

import com.google.common.collect.Lists;
import com.joker.utils.DateStyle;
import com.joker.utils.DateUtil;
import org.apache.commons.lang3.StringUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by xiangrui on 2018/4/11.
 *
 * @author xiangrui
 * @date 2018/4/11
 */
public class TimeFormat {

    // 格式兼容 yyyy-MM-dd
    private static final DateTimeFormatter FORMAT_YYYY_M_D = DateTimeFormatter.ofPattern("yyyy-M-d");
    private static final DateTimeFormatter FORMAT_YYYY_M_D_EN = DateTimeFormatter.ofPattern("yyyy/M/d");
    private static final DateTimeFormatter FORMAT_YYYY_M_D_POINT = DateTimeFormatter.ofPattern("yyyy.M.d");

    public static void main(String[] args) throws ParseException {
//        test1();
//        test2();
//        test3();
        System.out.println(getLocalDate2("2017/8/1").toString());
    }

    public static void test1() throws ParseException {
        {
            String time = "2017-08-01";
            Date date = DateUtil.StringToDate(time, DateStyle.YYYY_MM_DD);
            System.out.println(time + " -> " + DateUtil.DateToString(date, DateStyle.YYYY_MM_DD));

            LocalDate parse = LocalDate.parse(time, DateTimeFormatter.ofPattern("yyyy-M-d"));
            System.out.println("localDate1 -> " + parse.toString());
        }
        {
            String time2 = "2017--08-07";
            Date date2 = new SimpleDateFormat(DateStyle.YYYY_MM_DD.getValue()).parse(time2);
            System.out.println(time2 + " -> " + DateUtil.DateToString(date2, DateStyle.YYYY_MM_DD));
            LocalDate parse = LocalDate.parse(time2);
            System.out.println("localDate2:" + parse.toString());
        }
    }

    public static void test2() {
        {
            String time = "2017/10/27";
            if (time.length() > 10) {
                time = time.substring(0, 10);
            }
            Date date = DateUtil.StringToDate(time, DateStyle.YYYY_MM_DD_EN);
            System.out.println(time + " -> " + DateUtil.DateToString(date, DateStyle.YYYY_MM_DD_EN));

            LocalDate parse = LocalDate.parse(time, DateTimeFormatter.ofPattern("yyyy/M/d"));
            System.out.println("localDate1 -> " + parse.toString());
        }
//        {
//            String time2 = "2017//08/07";
//            Date date2 = DateUtil.StringToDate(time2, DateStyle.YYYY_MM_DD_EN);
//            System.out.println(time2 + " -> " + DateUtil.DateToString(date2, DateStyle.YYYY_MM_DD_EN));
//
//            LocalDate parse = LocalDate.parse(time2);
//            System.out.println("localDate2:" + parse.toString());
//        }
    }

    public static LocalDate getLocalDate(String date) {
        try {
            if (StringUtils.isEmpty(date)) {
                return null;
            }
            // 强制取年月日
            if (date.length() > 10) {
                date = date.substring(0, 10);
            }
            LocalDate localDate;
            try {
                localDate = LocalDate.parse(date, FORMAT_YYYY_M_D);
            } catch (Exception e) {
                try {
                    localDate = LocalDate.parse(date, FORMAT_YYYY_M_D_EN);
                } catch (Exception ignore) {
                    localDate = LocalDate.parse(date, FORMAT_YYYY_M_D_POINT);
                }
            }
            return localDate;
        } catch (Exception e) {
            return null;
        }
    }

    public static LocalDate getLocalDate2(String date) {
        if (StringUtils.isEmpty(date)) {
            return null;
        }
        // 强制取年月日
        if (date.length() > 10) {
            date = date.substring(0, 10);
        }
        List<DateTimeFormatter> formatterList = Lists.newArrayList(FORMAT_YYYY_M_D, FORMAT_YYYY_M_D_EN, FORMAT_YYYY_M_D_POINT);
        for (DateTimeFormatter k : formatterList) {
            try {
                return LocalDate.parse(date, k);
            } catch (Exception ignore) {
            }
        }
        return null;
    }
}
