package com.joker.model.pricecurve;

import com.joker.model.pricecurve.AbstractPriceEntity;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by xiangrui on 2017/10/9.
 *
 * @author xiangrui
 * @date 2017/10/09
 */
public class PriceConfigDTO extends AbstractPriceEntity {
    private Integer id;

    private Integer contractId;

    private Integer skuId;

    private BigDecimal tagPrice;

    private String vendorSkuCode;

    private String spuOptionText;

    private String referenceType;

    private String referenceNo;

    private Integer operatorId;

    private String operator;

    private Byte isDeleted;

    private Date updateTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getContractId() {
        return contractId;
    }

    public void setContractId(Integer contractId) {
        this.contractId = contractId;
    }

    public Integer getSkuId() {
        return skuId;
    }

    public void setSkuId(Integer skuId) {
        this.skuId = skuId;
    }

    public BigDecimal getTagPrice() {
        return tagPrice;
    }

    public void setTagPrice(BigDecimal tagPrice) {
        this.tagPrice = tagPrice;
    }

    public String getVendorSkuCode() {
        return vendorSkuCode;
    }

    public void setVendorSkuCode(String vendorSkuCode) {
        this.vendorSkuCode = vendorSkuCode;
    }

    public String getSpuOptionText() {
        return spuOptionText;
    }

    public void setSpuOptionText(String spuOptionText) {
        this.spuOptionText = spuOptionText;
    }

    public String getReferenceType() {
        return referenceType;
    }

    public void setReferenceType(String referenceType) {
        this.referenceType = referenceType;
    }

    public String getReferenceNo() {
        return referenceNo;
    }

    public void setReferenceNo(String referenceNo) {
        this.referenceNo = referenceNo;
    }

    public Integer getOperatorId() {
        return operatorId;
    }

    public void setOperatorId(Integer operatorId) {
        this.operatorId = operatorId;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public Byte getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Byte isDeleted) {
        this.isDeleted = isDeleted;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}
