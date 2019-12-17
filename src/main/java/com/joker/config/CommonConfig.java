package com.joker.config;

import com.google.common.base.Joiner;

/**
 * Created by xiangrui on 2019/2/18.
 *
 * @author xiangrui
 * @date 2019/2/18
 */
public class CommonConfig {
    public static final String COMMA = ",";
    public static final String SEMICOLON = ";";
    public static final String POUND = "#";
    public static final String COLON = ":";
    public static final String SLASH = "/";
    public static final String HYPHEN = "-";
    public static final String VERTICAL_LINE = "|";
    public static final String DOT = ".";
    public static final String EMPTY = "";

    public static final String ALL = "ALL";
    public static final String ASN = "ASN";
    public static final String ST = "ST";
    public static final String IA = "IA";
    public static final String IV = "IV";
    public static final String PO = "PO";
    public static final String RTV = "RTV";
    public static final String CSO = "CSO";
    public static final String TEST = "test";
    public static final String PROD = "prod";

    public static final int SYSTEM_OPERATOR_ID = -1;
    public static final Joiner HYPHEN_JOINER = Joiner.on(HYPHEN);

    public static final String STOCKIN_ORDER_JEDIS_LOCK = "STOCKIN_ORDER_JEDIS";
    public static final String INVENTORY_ORDER_JEDIS_LOCK = "INVENTORY_ORDER_JEDIS_LOCK";
    public static final String STORE_INVENTORY_ORDER_JEDIS_LOCK = "STORE_INVENTORY_ORDER_JEDIS_LOCK";
    public static final String STOCK_ADJUST_ORDER_JEDIS_LOCK = "STOCK_ADJUST_ORDER_JEDIS_LOCK";
    public static final String RETURN_ORDER_JEDIS_LOCK = "RETURN_ORDER_JEDIS_LOCK";
    public static final String CUSTOM_ORDER_JEDIS_LOCK = "CUSTOM_ORDER_JEDIS_LOCK";

    public static final String PROFIT_LOSS_ORDER_JEDIS_LOCK = "PROFIT_LOSS_ORDER_JEDIS_LOCK";
    public static final int PROFIT_LOSS_ORDER_NO_NUM_LEN = 7;
}
