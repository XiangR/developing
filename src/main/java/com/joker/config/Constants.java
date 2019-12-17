package com.joker.config;

import com.joker.utils.DateUtil;

import java.util.Date;

/**
 * Created by xiangrui on 2019/2/18.
 *
 * @author xiangrui
 * @date 2019/2/18
 */
public class Constants {

    public static final int LOGICAL_EXPIRE_SECONDS = 15;

    public static final int PHYSICAL_EXPIRE_SECONDS = 30;

    public static final int COMMON_EXPIRE_SECONDS = 3600;

    public static final int MILLION_PER_SECOND = 1000;

    public static final int CHECK_PRICE_PERIOD = 4;

    public static final String MAIL_RECEIVER_SPLITTER = ";";

    public final static String XLS = "xls";

    public final static String XLSX = "xlsx";

    //保留两位小数
    public static final int TWO_DECIMAL = 2;

    /**
     * 无穷大时间
     */
    public final static Date INFINITY_DATE = DateUtil.StringToDate("2099-12-31 23:59:59");

    /**
     * 容器初始化大小必须时2的指数
     */
    public static final int COLLECTION_DEFAULT_CAPACITY = 256;

    /**
     * 容器初始化大小必须时2的指数
     */
    public static final int SPLIT_PAGE_SIZE = 100;

    /**
     * 默认的页查询数
     */
    public static final int QUERY_PAGE_SIZE = 20;

    public static final int _1MB = 1024 * 1024;

}
