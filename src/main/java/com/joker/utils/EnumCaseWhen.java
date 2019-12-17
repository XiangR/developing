package com.joker.utils;

import com.google.common.collect.Lists;
import com.sun.org.glassfish.gmbal.Description;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;

/**
 * 打印出数据库某个字段的  CASE WHEN
 * <p>
 * <p>
 * result：
 * CASE status
 * WHEN ('INIT') THEN '初始化'
 * WHEN ('CANCEL') THEN '已取消'
 * WHEN ('WAIT_APPOINT') THEN '待预约'
 * WHEN ('WAIT_STOCK_OUT') THEN '待推单'
 * WHEN ('STOCKOUTING') THEN '推单中'
 * WHEN ('WAIT_PICK') THEN '待拣货'
 * WHEN ('UNDER_PICK') THEN '拣货中'
 * WHEN ('WAIT_BOX') THEN '待装箱'
 * WHEN ('UNDER_BOX') THEN '装箱中'
 * WHEN ('WAIT_DELIVERY') THEN '待发货'
 * WHEN ('STOCK_OUT') THEN '已发货'
 * end AS '状态'
 * <p>
 * Created by xiangrui on 2019-05-08.
 *
 * @author xiangrui
 * @date 2019-05-08
 */
public class EnumCaseWhen {

    public static void main(String[] args) {
        printCaseWhen(StockinOrderType.values(), "status", "desc");

    }

    /**
     * 打印出状态的  CASE WHEN
     *
     * @param enums  枚举
     * @param column 数据库字段
     * @param field  中文属性
     */
    public static void printCaseWhen(Enum[] enums, String column, String field) {
        StringBuilder builder = new StringBuilder();
        builder.append(String.format(" CASE %s ", column));
        builder.append("\n");
        for (Enum en : enums) {
            try {
                String name = en.name();
                Field methodField = en.getDeclaringClass().getDeclaredField(field);
                methodField.setAccessible(true);
                String entityFieldName = (String) methodField.get(en);
                builder.append(String.format("    WHEN ('%s') THEN '%s' ", name, entityFieldName));
                builder.append("\n");
            } catch (NoSuchFieldException | IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        builder.append(" end AS '状态'");
        builder.append("\n");

        System.out.println(builder.toString());
    }

    @Description("入库单类型枚举")
    public enum StockinOrderType {

        @Description("寄售")
        CONSIGNMENT("CONSIGNMENT", "寄售"),

        @Description("采购")
        PURCHASE("PURCHASE", "采购"),

        @Description("调拨")
        TRANSFER("TRANSFER", "调拨"),

        @Description("退货")
        RETURN("RETURN", "退货");

        private String value;
        private String label;

        StockinOrderType(String value, String label) {
            this.value = value;
            this.label = label;
        }

        public String getValue() {
            return value;
        }

        public String getLabel() {
            return label;
        }

        /**
         * 得到所有包含的枚举类型的集合
         *
         * @return value 的集合
         */
        public static List<String> getValues() {
            List<String> values = Lists.newArrayList();
            for (StockinOrderType status : StockinOrderType.values()) {
                values.add(status.getValue());
            }
            return values;
        }

        /**
         * 得到所有包含的枚举类型的集合
         *
         * @return value 的集合
         */
        public static List<String> getLabels() {
            List<String> values = Lists.newArrayList();
            for (StockinOrderType status : StockinOrderType.values()) {
                values.add(status.getLabel());
            }
            return values;
        }

        /**
         * 由value 拿到 label
         *
         * @param value value
         * @return type
         */
        public static String getLabelByValue(String value) {
            for (StockinOrderType status : StockinOrderType.values()) {
                if (status.getValue().equals(value)) {
                    return status.getLabel();
                }
            }
            return null;
        }

        /**
         * 由label 拿到 value
         *
         * @param label value
         * @return type
         */
        public static String getValueByLabel(String label) {
            for (StockinOrderType status : StockinOrderType.values()) {
                if (status.getLabel().equals(label)) {
                    return status.getValue();
                }
            }
            return null;
        }

        /**
         * value 拿到枚举类型
         *
         * @param value value
         * @return type
         */
        public static StockinOrderType getTypeByValue(String value) {
            return Arrays.stream(StockinOrderType.values())
                    .filter(k -> k.getValue().equals(value)).findAny().orElse(null);
        }
    }

}
