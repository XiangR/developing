package com.joker.time;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;

/**
 * Created by xiangrui on 2018/3/29.
 *
 * @author xiangrui
 * @date 2018/3/29
 */
public class YearMonth1 {


    public static void main(String[] args) {
        test2();
    }


    private static void test2() {

        String str = "2017.08";


        YearMonth parse1 = YearMonth.parse(str, DateTimeFormatter.ofPattern("yyyy.MM"));
        System.out.println(parse1);

        LocalDate localDate = parse1.atDay(1);

        System.out.println(localDate);

        LocalDateTime localDateTime = localDate.atStartOfDay();

        System.out.println(localDateTime);

    }
}
