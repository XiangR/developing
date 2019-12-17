package com.yit.test;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.google.common.collect.Lists;
import com.joker.utils.SplitJson;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

/**
 * Created by xiangrui on 2019-08-29.
 *
 * @author xiangrui
 * @date 2019-08-29
 */
public class SplitJsonTest {


    @Test
    public void test_split_TC1() {

        String str = " " +
                "{\"warehouseId\":2056,\"skuId\":[35971,34950],\"class\":\"com.yit.stock.entity.PageSkuStockRequest\"}"
                + ","
                + "{\"class\":\"com.yit.common.entity.PageParameter\"}";

        List<String> split = SplitJson.split(str);

        Assert.assertEquals(2, split.size());

        Assert.assertEquals(2056, JSON.parseObject(split.get(0)).getInteger("warehouseId").intValue());
        Assert.assertEquals("[35971,34950]", JSON.parseObject(split.get(0)).getString("skuId"));
        Assert.assertEquals("com.yit.stock.entity.PageSkuStockRequest", JSON.parseObject(split.get(0)).getString("class"));

        Assert.assertEquals("com.yit.common.entity.PageParameter", JSON.parseObject(split.get(1)).getString("class"));
    }


    @Test
    public void test_split_TC2() {

        String str = " "
                + "{\"warehouseId\":2056,\"skuId\":[35971,34950],\"class\":\"com.yit.stock.entity.PageSkuStockRequest\"}"
                + ","
                + "24";

        List<String> split = SplitJson.split(str);

        Assert.assertEquals(2, split.size());

        Assert.assertEquals(2056, JSON.parseObject(split.get(0)).getInteger("warehouseId").intValue());
        Assert.assertEquals("[35971,34950]", JSON.parseObject(split.get(0)).getString("skuId"));
        Assert.assertEquals("com.yit.stock.entity.PageSkuStockRequest", JSON.parseObject(split.get(0)).getString("class"));

        Assert.assertEquals("24", split.get(1));
    }


    @Test
    public void test_split_TC3() {
        String args = " "
                + "[{\"warehouseId\":2056,\"skuId\":[35971,34950],\"class\":\"com.yit.stock.entity.PageSkuStockRequest\"}]"
                + ","
                + "24";

        List<Object> list = Lists.newArrayList();
        try {
            list = JSON.parseArray("[" + args + "]", Object.class);
        } catch (Throwable t) {
        }

        System.out.println(list);
    }

}
