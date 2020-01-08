package com.joker.model;


/**
 * Created by xiangrui on 2018/8/13.
 *
 * @author xiangrui
 * @date 2018/8/13
 */
public enum AdjustEnum {

    INVENTORY_ORDER_NO("手艺人id", "fenYongIdStr"),
    TYPE("门店id", "shopId"),
    FINANCE_INITIATE("平台（1--点评  2--美团）", "platform"),
    TAG("商品id", "spugId"),
    STATUS("skuid", "skuId"),
    WAREHOUSE_NAME("数量", "adjustQtyAbs"),
    CREATE_TIME("操作（1--减少   2--增加）", "operate"),
    CREATE_OPERATOR("商品类型（100--团购  200--泛商品）", "skuType"),
    SPU_ID("业务类型（1--次卡   2--团购）", "businessType"),
    SPU_NAME("销量类型（100--验券  200--售卖）", "saleType"),
    ;

    private String title;
    /**
     * 单元格获取属性值的方法名称。通过此方法获取目标实体的属性值
     */
    private String field;

    AdjustEnum(String title, String field) {
        this.title = title;
        this.field = field;
    }

    public String getTitle() {
        return title;
    }

    public String getField() {
        return field;
    }
}
