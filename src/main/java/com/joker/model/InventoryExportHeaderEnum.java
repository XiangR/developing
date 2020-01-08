package com.joker.model;


/**
 * Created by xiangrui on 2018/8/13.
 *
 * @author xiangrui
 * @date 2018/8/13
 */
public enum InventoryExportHeaderEnum {

    INVENTORY_ORDER_NO("盘点任务编号", "inventoryOrderNo"),
    TYPE("盘点类型", "typeName"),
    FINANCE_INITIATE("是否财务发起", "isFinanceInitiateStr"),
    TAG("初盘/复盘", "tagName"),
    STATUS("盘点状态", "statusName"),
    WAREHOUSE_NAME("仓库", "warehouseName"),
    CREATE_TIME("创建时间", "createTimeStr"),
    CREATE_OPERATOR("创建人", "operatorName"),
    SPU_ID("Spuid", "spuId"),
    SPU_NAME("商品名称", "spuName"),
    SKU_ID("skuid", "skuId"),
    SPECIFICATION("规格名称", "options"),
    BARCODE("商品条码", "barcode"),
    SUPPLIER_NAME("货主", "supplierName"),
    DAILY_PRICE("售价", "salePriceStr"),
    COST_PRICE("成本价", "costPriceStr"),
    TOTAL_CURRENT_COUNT("系统Units", "totalCurrentCount"),
    TOTAL_INVENTORY_COUNT("盘点Units", "totalInventoryCount"),
    TOTAL_GAP_COUNT("总差异数量", "totalGapCount"),
    NORMAL_CURRENT_COUNT("系统普通商品Units", "normalCurrentCount"),
    NORMAL_INVENTORY_COUNT("盘点普通商品Units", "normalInventoryCount"),
    NORMAL_GAP_COUNT("普通商品差异数量", "normalGapCount"),
    SPECIMEN_CURRENT_COUNT("系统样品Units", "specimenCurrentCount"),
    SPECIMEN_INVENTORY_COUNT("盘点样品Units", "specimenInventoryCount"),
    SPECIMEN_GAP_COUNT("样品差异数量", "specimenGapCount"),
    ;

    private String title;
    /**
     * 单元格获取属性值的方法名称。通过此方法获取目标实体的属性值
     */
    private String field;

    InventoryExportHeaderEnum(String title, String field) {
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
