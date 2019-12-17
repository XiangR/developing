package com.joker.model.pricecurve;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.annotation.JSONField;
import com.joker.model.pricecurve.AbstractPriceEntity;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by xiangrui on 2017/10/9.
 *
 * @author xiangrui
 * @date 2017/10/09
 */
public class CurvePriceNode {

    private BigDecimal price;

    @JSONField(format = "yyyy-MM-dd")
    private Date effectiveTime;

    private AbstractPriceEntity config;

    public CurvePriceNode(BigDecimal price, Date effectiveTime, AbstractPriceEntity config) {
        this.price = price;
        this.effectiveTime = effectiveTime;
        this.config = config;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Date getEffectiveTime() {
        return effectiveTime;
    }

    public void setEffectiveTime(Date effectiveTime) {
        this.effectiveTime = effectiveTime;
    }

    public AbstractPriceEntity getConfig() {
        return config;
    }

    public void setConfig(AbstractPriceEntity config) {
        this.config = config;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}
