package com.joker.model;

import java.io.Serializable;

public class TechSaleSkuAdjust implements Serializable {

    /**
     * 技师ID
     */
    private int techId;

    /**
     * 分拥ID
     */
    private long fenYongId;

    /**
     * 分拥标示
     */
    private String fenYongIdStr;

    /**
     * 平台
     */
    private int platform;

    /**
     * 订单ID
     */
    private String unifiedOrderId;

    /**
     * 分销订单ID
     */
    private String sdOrderId;

    /**
     * skuId
     */
    private String skuId;

    /**
     * 商品ID
     */
    private String spugId;

    /**
     * 调整数量
     */
    private int adjustQty;

    /**
     * 订单shopId
     */
    private int orderShopId;

    /**
     * 真实shopId
     */
    private int exactShopId;

    public int getTechId() {
        return techId;
    }

    public void setTechId(int techId) {
        this.techId = techId;
    }

    public long getFenYongId() {
        return fenYongId;
    }

    public void setFenYongId(long fenYongId) {
        this.fenYongId = fenYongId;
    }

    public String getFenYongIdStr() {
        return fenYongIdStr;
    }

    public void setFenYongIdStr(String fenYongIdStr) {
        this.fenYongIdStr = fenYongIdStr;
    }

    public int getPlatform() {
        return platform;
    }

    public void setPlatform(int platform) {
        this.platform = platform;
    }

    public String getUnifiedOrderId() {
        return unifiedOrderId;
    }

    public void setUnifiedOrderId(String unifiedOrderId) {
        this.unifiedOrderId = unifiedOrderId;
    }

    public String getSdOrderId() {
        return sdOrderId;
    }

    public void setSdOrderId(String sdOrderId) {
        this.sdOrderId = sdOrderId;
    }

    public String getSkuId() {
        return skuId;
    }

    public void setSkuId(String skuId) {
        this.skuId = skuId;
    }

    public String getSpugId() {
        return spugId;
    }

    public void setSpugId(String spugId) {
        this.spugId = spugId;
    }

    public int getAdjustQty() {
        return adjustQty;
    }

    public void setAdjustQty(int adjustQty) {
        this.adjustQty = adjustQty;
    }

    public int getOrderShopId() {
        return orderShopId;
    }

    public void setOrderShopId(int orderShopId) {
        this.orderShopId = orderShopId;
    }

    public int getExactShopId() {
        return exactShopId;
    }

    public void setExactShopId(int exactShopId) {
        this.exactShopId = exactShopId;
    }
}
