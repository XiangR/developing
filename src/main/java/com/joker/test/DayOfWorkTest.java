package com.joker.test;

import com.google.common.collect.Lists;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;

/**
 * Created by xiangrui on 2018/8/19.
 *
 * @author xiangrui
 * @date 2018/8/19
 */
public class DayOfWorkTest {

    private final static List<DayOfWeek> releaseDays = Lists.newArrayList(DayOfWeek.SATURDAY, DayOfWeek.SUNDAY);

    public static void main(String[] args) {

        LocalDate localDate = plusWordDay(LocalDate.now(), -1);

        System.out.println(localDate);
    }

    private static LocalDate plusWordDay(LocalDate now, int plusDay) {
        if (plusDay > 0) {
            return plusWordDayPos(now, plusDay);
        } else if (plusDay < 0) {
            return plusWordDayNeg(now, plusDay);
        } else {
            return now;
        }
    }

    private static LocalDate plusWordDayPos(LocalDate now, int plusDay) {
        while (plusDay > 0) {
            now = now.plusDays(1);
            if (!releaseDays.contains(now.getDayOfWeek())) {
                plusDay--;
            }
        }
        return now;
    }

    private static LocalDate plusWordDayNeg(LocalDate now, int plusDay) {
        while (plusDay < 0) {
            now = now.plusDays(-1);
            if (!releaseDays.contains(now.getDayOfWeek())) {
                plusDay++;
            }
        }
        return now;
    }

}
