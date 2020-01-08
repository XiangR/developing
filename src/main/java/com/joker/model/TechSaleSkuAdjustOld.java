package com.joker.model;


import java.io.Serializable;

public class TechSaleSkuAdjustOld implements Serializable {
    private int techId;
    private long fenYongId;
    private String fenYongIdStr;
    private int shopId;
    private String skuId;
    private String spugId;
    private String orderId;
    private int adjustQty;

    public TechSaleSkuAdjustOld() {
    }

    public int getTechId() {
        return this.techId;
    }

    public void setTechId(int techId) {
        this.techId = techId;
    }

    public long getFenYongId() {
        return this.fenYongId;
    }

    public void setFenYongId(long fenYongId) {
        this.fenYongId = fenYongId;
    }

    public String getFenYongIdStr() {
        return this.fenYongIdStr;
    }

    public void setFenYongIdStr(String fenYongIdStr) {
        this.fenYongIdStr = fenYongIdStr;
    }

    public int getShopId() {
        return this.shopId;
    }

    public void setShopId(int shopId) {
        this.shopId = shopId;
    }

    public String getSkuId() {
        return this.skuId;
    }

    public void setSkuId(String skuId) {
        this.skuId = skuId;
    }

    public String getSpugId() {
        return this.spugId;
    }

    public void setSpugId(String spugId) {
        this.spugId = spugId;
    }

    public String getOrderId() {
        return this.orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public int getAdjustQty() {
        return this.adjustQty;
    }

    public void setAdjustQty(int adjustQty) {
        this.adjustQty = adjustQty;
    }
}
