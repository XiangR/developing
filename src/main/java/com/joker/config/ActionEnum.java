package com.joker.config;

/**
 * Created by xiangrui on 2019/2/18.
 *
 * @author xiangrui
 * @date 2019/2/18
 */
public enum ActionEnum {

    STOCKIN_UPDATE("入库单更新"),

    STOCKIN_CLEAN("入库单清空"),

    STOCKIN_FINISH("入库单完成"),

    INVENTORY_ADD("创建盘点单"),

    INVENTORY_EDIT("盘点单盘货"),

    INVENTORY_FINISH("完成盘点单"),

    INVENTORY_CANCEL("取消盘点单");

    private String desc;

    ActionEnum(String desc) {
        this.desc = desc;
    }

    public String getDesc() {
        return desc;
    }
}
