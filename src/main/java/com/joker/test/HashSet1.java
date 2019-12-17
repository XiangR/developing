package com.joker.test;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

import java.util.*;

/**
 * Created by xiangrui on 2017/12/12.
 *
 * @author xiangrui
 * @date 2017/12/12
 */
public class HashSet1 {

    public static void main(String args[]) {

        SpuCountLimitation spuCountLimitation1 = new SpuCountLimitation();
        spuCountLimitation1.setCategoryLevel1Id(1);
        spuCountLimitation1.setPromotionId(2);

        SpuCountLimitation spuCountLimitation2 = new SpuCountLimitation();
        spuCountLimitation2.setCategoryLevel1Id(1);
        spuCountLimitation2.setPromotionId(2);

        SpuCountLimitation spuCountLimitation3 = new SpuCountLimitation();
        spuCountLimitation3.setCategoryLevel1Id(3);
        spuCountLimitation3.setPromotionId(2);


        ArrayList<SpuCountLimitation> spuCountLimitations = Lists.newArrayList(null, spuCountLimitation1, spuCountLimitation2, spuCountLimitation3);

        System.out.println(spuCountLimitations);
        HashSet<SpuCountLimitation> spuCountLimitations1 = Sets.newHashSet(spuCountLimitations);
        System.out.println(spuCountLimitations1);
    }

    /**
     * 通过定义方法来实现构造器
     */
    private static Comparator<String> comparator = Comparator.reverseOrder();

}

class SpuCountLimitation {
    private Integer categoryLevel1Id;

    private Integer promotionId;

    private Integer spuCount;

    public Integer getCategoryLevel1Id() {
        return categoryLevel1Id;
    }

    public void setCategoryLevel1Id(Integer categoryLevel1Id) {
        this.categoryLevel1Id = categoryLevel1Id;
    }

    public Integer getPromotionId() {
        return promotionId;
    }

    public void setPromotionId(Integer promotionId) {
        this.promotionId = promotionId;
    }

    public Integer getSpuCount() {
        return spuCount;
    }

    public void setSpuCount(Integer spuCount) {
        this.spuCount = spuCount;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof SpuCountLimitation) {
            SpuCountLimitation another = (SpuCountLimitation) obj;
            return another.categoryLevel1Id.equals(this.categoryLevel1Id) && another.promotionId.equals(this.promotionId);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return this.promotionId << 3 + this.categoryLevel1Id << 3;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}

