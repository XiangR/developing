package com.joker.model.pricecurve;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.annotation.JSONField;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by xiangrui on 2017/10/9.
 *
 * @author xiangrui
 * @date 2017/10/09
 */
public class AbstractPriceEntity {

    private Integer weight;

    private BigDecimal price;

    @JSONField(format = "yyyy-MM-dd")
    private Date startTime;

    @JSONField(format = "yyyy-MM-dd")
    private Date endTime;

    @JSONField(format = "yyyy-MM-dd")
    private Date createTime;

    public Integer getWeight() {
        return weight;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}
