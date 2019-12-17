package com.joker.test;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by xiangrui on 2019/4/21.
 *
 * @author xiangrui
 * @date 2019/4/21
 */
public class Airplane {

    // 第一次的女性生理期
    public static LocalDate firstWomanDate = LocalDate.of(2019, 1, 14);
    // 女性生理周期
    public static int sync = 28;
    // 女性痛经时间
    public static List<LocalDate> womanDateList = new ArrayList<>();

    // 初始化女性痛经时间
    static {
        LocalDate endDate = LocalDate.of(2019, 12, 31);
        LocalDate nextWomanDate = firstWomanDate;
        while (true) {
            if (nextWomanDate.isAfter(endDate)) {
                break;
            }
            womanDateList.add(nextWomanDate);
            womanDateList.add(nextWomanDate.plusDays(1));
            nextWomanDate = nextWomanDate.plusDays(sync);
        }
    }

    public static int length = 4700;
    public static int lengthAll = length * 2;
    public static BigDecimal ten = BigDecimal.valueOf(10);
    public static BigDecimal thousand = BigDecimal.valueOf(1000);
    public static BigDecimal thousandCount = BigDecimal.valueOf(lengthAll).divide(thousand, 2, RoundingMode.HALF_DOWN);
    public static LocalDate Y_DATE = LocalDate.of(2019, 4, 19);
    // 年度安排时间表
    public static LinkedHashMap<LocalDate, DayPlan> yearScheduleMap = new LinkedHashMap<>();
    // 男人加班的时间
    public static List<DayPlan> overtimeWorkMan = new ArrayList<>();
    // 男人休息的时间
    public static List<DayPlan> manSleepDay = new ArrayList<>();
    // 男人是否停止工作
    public static boolean manStop = false;

    // 运载重量
    public static int[] weightArr = new int[]{60, 70, 80, 90};

    public static BigDecimal getWeight() {
        int i = weightArr[(int) Math.round(Math.random() * (3))];
        return BigDecimal.valueOf(i);
//        return BigDecimal.valueOf(Math.round(Math.random() * (100 - 60) + 60));
    }

    public static void main(String[] args) {
        System.out.println(printYearPlan());
    }

    private static String printYearPlan() {

        // 处理年度安排
        processYearPlan();

        // 统计行程安排
        List<String> dayList = new ArrayList<>();
        BigDecimal totalHour = BigDecimal.ZERO;
        BigDecimal totalOil = BigDecimal.ZERO;
        for (Map.Entry<LocalDate, DayPlan> entry : yearScheduleMap.entrySet()) {
            LocalDate date = entry.getKey();
            DayPlan value = entry.getValue();
            BigDecimal dayHour = value.getDayHour(date);
            BigDecimal dayOil = value.getDayOil(date);
            dayList.add(String.format("本次航班信息：日期:%s，驾驶员性别:%s，加班:%s，自动驾驶:%s，耗油:%s吨，用时:%s小时。",
                    date.toString(), value.getSex().getDesc(), value.overtime ? "是" : "否", value.auto ? "是" : "否", dayOil, dayHour));
            totalHour = totalHour.add(dayHour);
            totalOil = totalOil.add(dayOil);
        }
        // 打印输出
        String name = String.format("航空安排%s-%s.txt", LocalDate.now().toString(), LocalTime.now().toString());
        appendFileTextLine(name, String.format("年度航班驾驶信息耗油:%s吨，用时:%s小时。", totalOil, totalHour));
        for (String s : dayList) {
            appendFileTextLine(name, s);
        }
        return name;
    }

    /**
     * 处理年度安排
     */
    private static void processYearPlan() {
        LocalDate begin = LocalDate.of(2019, 1, 1);
        LocalDate end = LocalDate.of(2019, 12, 31);
        int count = 0;
        while (true) {
            LocalDate nextDay = begin.plusDays(count);
            if (nextDay.isAfter(end)) {
                break;
            }
            DayPlan processPlan = processDayPlan(nextDay);
            yearScheduleMap.put(begin.plusDays(count), processPlan);
            count++;
        }
    }

    /**
     * 处理每天的安排
     *
     * @param thiDate 时间
     * @return 行程安排
     */
    private static DayPlan processDayPlan(LocalDate thiDate) {
        // first day
        if (yearScheduleMap.isEmpty()) {
            return new DayPlanWoman(thiDate);
        }

        LocalDate beforeDay = thiDate.plusDays(-1);
        DayPlan beforeDayPlan = yearScheduleMap.get(beforeDay);
        SEX beforeSex = beforeDayPlan.getSex();

        DayPlan thisDay;
        // 女性不能驾驶的时间
        if (manStop) {
            thisDay = new DayPlanWoman(thiDate);
            if (womanDateList.contains(thiDate)) {
                // 女性生理期使用自动驾驶
                thisDay.auto = true;
            }
            manSleepDay.add(thisDay);
        } else {
            // 女性生理期，默认使用男性
            if (womanDateList.contains(thiDate)) {
                thisDay = new DayPlanMan(thiDate);
            } else {
                // 默认不加班
                if (beforeSex == SEX.MAN) {
                    thisDay = new DayPlanWoman(thiDate);
                } else {
                    thisDay = new DayPlanMan(thiDate);
                }
            }
        }

        // 如果昨天也是同性别
        if (beforeSex == thisDay.getSex()) {
            thisDay.overtime = true;
            if (beforeSex == SEX.MAN) {
                overtimeWorkMan.add(thisDay);
            }
        }
        clearManStopOrOvertime();
        return thisDay;
    }

    private static void clearManStopOrOvertime() {
        // 如果男性加班到达了十一天则男性罢工
        if (!manStop && overtimeWorkMan.size() == 11) {
            manStop = true;
            manSleepDay = new ArrayList<>();
        }
        // 如果男性罢工并且连续休息了十天，则停止罢工，并重新计算男性加班
        if (manStop && manSleepDay.size() == 10) {
            manStop = false;
            overtimeWorkMan = new ArrayList<>();
        }
    }

    public static void appendFileTextLine(String fileName, String content) {
        File file = new File(fileName);
        try {
            if (!file.exists()) {
                file.createNewFile();// 创建新文件,有同名的文件的话直接覆盖
            }
            try (FileWriter writer = new FileWriter(file, true);
                 BufferedWriter out = new BufferedWriter(writer)
            ) {
                out.append(content);
                out.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // region  内部类支持

    /**
     * 抽象类，每日的行程计划
     */
    public static abstract class DayPlan {

        /**
         * 当日重量
         */
        public BigDecimal weight = getWeight();
        /**
         * 当日时间
         */
        public LocalDate date;
        /**
         * 当日是否加班
         */
        public boolean overtime;
        /**
         * 当日是否使用自动驾驶
         */
        public boolean auto;

        public DayPlan() {
        }

        public DayPlan(LocalDate date) {
            this.date = date;
        }

        /**
         * 获取当日耗油
         */
        public abstract BigDecimal getDayOil(LocalDate localDate);

        /**
         * 获取当日的总耗时（单位小时）
         */
        public abstract BigDecimal getDayHour(LocalDate localDate);

        /**
         * 性别
         */
        public abstract SEX getSex();

        /**
         * 使用自动驾驶的千公里耗油
         */
        public BigDecimal thousandOilAuto(AirplaneType type) {
            BigDecimal unit = weight.divide(ten, 2, RoundingMode.HALF_DOWN);
            if (type == AirplaneType.X) {
                return unit.multiply(BigDecimal.valueOf(1.5));
            }
            if (type == AirplaneType.Y) {
                return unit.multiply(BigDecimal.valueOf(1.4));
            }
            return BigDecimal.ZERO;
        }

        /**
         * 使用自动驾驶的千公里耗时
         */
        public BigDecimal thousandHourAuto(AirplaneType type) {
            if (type == AirplaneType.X) {
                return BigDecimal.valueOf(1.7);
            }
            if (type == AirplaneType.Y) {
                BigDecimal divide = BigDecimal.valueOf(100).divide(BigDecimal.valueOf(115), 2, RoundingMode.HALF_DOWN);
                return BigDecimal.valueOf(1.7).multiply(divide);
            }
            return BigDecimal.ZERO;
        }
    }

    /**
     * 男人的行程安排
     */
    public static class DayPlanMan extends DayPlan {

        public DayPlanMan() {
        }

        public DayPlanMan(LocalDate date) {
            super(date);
        }

        public BigDecimal getDayOil(LocalDate localDate) {
            if (localDate.isBefore(Y_DATE)) {
                return dayOil(AirplaneType.X);
            } else {
                // 如果有新飞机则引入新飞机
                return dayOil(AirplaneType.Y);
            }
        }

        public BigDecimal getDayHour(LocalDate localDate) {
            if (localDate.isBefore(Y_DATE)) {
                return dayHour(AirplaneType.X);
            } else {
                return dayHour(AirplaneType.Y);
            }
        }

        @Override
        public SEX getSex() {
            return SEX.MAN;
        }

        private BigDecimal dayOil(AirplaneType type) {
            BigDecimal units = thousandOil(type);
            return thousandCount.multiply(units);
        }

        private BigDecimal dayHour(AirplaneType type) {
            return thousandCount.multiply(thousandHour(type));
        }

        /**
         * 千公里耗油
         */
        private BigDecimal thousandOil(AirplaneType type) {
            if (auto) {
                return thousandOilAuto(type);
            }
            BigDecimal unit = weight.divide(ten, 2, RoundingMode.HALF_DOWN);
            if (type == AirplaneType.X) {
                if (overtime) {
                    return unit.multiply(BigDecimal.valueOf(1.3));
                } else {
                    return unit.multiply(BigDecimal.valueOf(1));
                }
            }
            if (type == AirplaneType.Y) {
                if (overtime) {
                    return unit.multiply(BigDecimal.valueOf(1.2));
                } else {
                    return unit.multiply(BigDecimal.valueOf(0.9));
                }
            }
            return BigDecimal.ZERO;
        }

        /**
         * 千公里耗时
         */
        private BigDecimal thousandHour(AirplaneType type) {
            if (auto) {
                return thousandHourAuto(type);
            }
            if (type == AirplaneType.X) {
                return BigDecimal.valueOf(1.25);
            }
            if (type == AirplaneType.Y) {
                BigDecimal divide = BigDecimal.valueOf(100).divide(BigDecimal.valueOf(115), 2, RoundingMode.HALF_DOWN);
                return BigDecimal.valueOf(1.25).multiply(divide);
            }
            return BigDecimal.ZERO;
        }
    }

    /**
     * 女人的行程安排
     */
    public static class DayPlanWoman extends DayPlan {
        public DayPlanWoman() {
        }

        public DayPlanWoman(LocalDate date) {
            super(date);
        }

        public BigDecimal getDayOil(LocalDate localDate) {
            if (localDate.isBefore(Y_DATE)) {
                return dayOil(AirplaneType.X);
            } else {
                return dayOil(AirplaneType.Y);
            }
        }

        public BigDecimal getDayHour(LocalDate localDate) {
            if (localDate.isBefore(Y_DATE)) {
                return dayHour(AirplaneType.X);
            } else {
                return dayHour(AirplaneType.Y);
            }
        }

        @Override
        public SEX getSex() {
            return SEX.WOMAN;
        }

        private BigDecimal dayOil(AirplaneType type) {
            BigDecimal units = thousandOil(type);
            return thousandCount.multiply(units);
        }

        private BigDecimal dayHour(AirplaneType type) {
            return thousandCount.multiply(thousandHour(type));
        }

        /**
         * 千公里耗油
         */
        private BigDecimal thousandOil(AirplaneType type) {
            // 如果使用自动驾驶
            if (auto) {
                return thousandOilAuto(type);
            }
            BigDecimal unit = weight.divide(ten, 2, RoundingMode.HALF_DOWN);
            if (type == AirplaneType.X) {
                return unit.multiply(BigDecimal.valueOf(0.8));
            }
            if (type == AirplaneType.Y) {
                return unit.multiply(BigDecimal.valueOf(0.7));
            }
            return BigDecimal.ZERO;
        }

        /**
         * 千公里耗时
         */
        private BigDecimal thousandHour(AirplaneType type) {
            // 如果使用自动驾驶
            if (auto) {
                return thousandHourAuto(type);
            }
            if (type == AirplaneType.X) {
                return BigDecimal.valueOf(1.6);
            }
            if (type == AirplaneType.Y) {
                BigDecimal divide = BigDecimal.valueOf(100).divide(BigDecimal.valueOf(115), 2, RoundingMode.HALF_DOWN);
                return BigDecimal.valueOf(1.6).multiply(divide);
            }
            return BigDecimal.ZERO;
        }
    }

    // endregion

    // region 枚举

    /**
     * 飞机类型枚举
     */
    public enum AirplaneType {

        X("X飞机"),

        Y("Y飞机");

        private String desc;

        AirplaneType(String desc) {
            this.desc = desc;
        }

        public String getDesc() {
            return desc;
        }
    }

    /**
     * 性别枚举
     */
    public enum SEX {

        MAN("男人"),

        WOMAN("女人");

        private String desc;

        SEX(String desc) {
            this.desc = desc;
        }

        public String getDesc() {
            return desc;
        }
    }

    // endregion

}

