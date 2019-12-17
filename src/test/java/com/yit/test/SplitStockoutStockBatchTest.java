package com.yit.test;

import com.google.common.collect.Lists;
import com.joker.model.ServiceRuntimeException;
import com.joker.utils.SplitStockoutStockBatch;
import org.hamcrest.Matchers;
import org.hamcrest.core.AllOf;
import org.junit.Assert;
import org.junit.Test;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.yit.test.extend.MyHasPropertiesMatcher.hasPropertyExt;
import static com.yit.test.extend.MyMatchers.allOf;
import static com.yit.test.extend.MyMatchers.contains;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.fail;

/**
 * Created by xiangrui on 2019-07-15.
 *
 * @author xiangrui
 * @date 2019-07-15
 */
public class SplitStockoutStockBatchTest {

    private int supplierId = 1;
    private int warehouseId = 1;
    private String externalKey = "externalKey";

    /**
     * 切分多个批次来使用
     */
    @Test
    public void test_SplitStockoutStockBatchTest_TC1() {

        int skuId = 111;
        List<SplitStockoutStockBatch.SkuStockBatch> skuStockBatchList = Lists.newArrayList();

        skuStockBatchList.add(new SplitStockoutStockBatch.SkuStockBatch(1, supplierId, skuId, 2, String.format("batch-%s-%s", skuId, 1)));
        skuStockBatchList.add(new SplitStockoutStockBatch.SkuStockBatch(2, supplierId, skuId, 5, String.format("batch-%s-%s", skuId, 2)));
        skuStockBatchList.add(new SplitStockoutStockBatch.SkuStockBatch(3, supplierId, skuId, 3, String.format("batch-%s-%s", skuId, 3)));

        List<SplitStockoutStockBatch.StockoutSku> stockoutSkuList = Lists.newArrayList();
        stockoutSkuList.add(new SplitStockoutStockBatch.StockoutSku(externalKey, supplierId, skuId, 6));
        SplitStockoutStockBatch stockBatch = new SplitStockoutStockBatch(warehouseId, stockoutSkuList, skuStockBatchList);
        stockBatch.addSplitSortEnum(SplitStockoutStockBatch.SortEnum.WEIGHT)
                // 先找到第一个足够到批次
                .setEnoughFirst(true)
                // 是否加锁
                .setWarnQtyTryLock(false)
                .splitBatchSkuStock();

        List<SplitStockoutStockBatch.StockoutSkuBatch> stockoutSkuBatchList = stockBatch.getStockoutSkuBatchList();

        Assert.assertEquals(2, stockoutSkuBatchList.size());
        Assert.assertEquals(2, stockoutSkuBatchList.get(0).goodQty);
        Assert.assertEquals(4, stockoutSkuBatchList.get(1).goodQty);
    }

    /**
     * 第一个批次足够使用
     */
    @Test
    public void test_SplitStockoutStockBatchTest_TC2() {

        int skuId = 111;
        List<SplitStockoutStockBatch.SkuStockBatch> skuStockBatchList = Lists.newArrayList();

        skuStockBatchList.add(new SplitStockoutStockBatch.SkuStockBatch(1, supplierId, skuId, 8, String.format("batch-%s-%s", skuId, 1)));
        skuStockBatchList.add(new SplitStockoutStockBatch.SkuStockBatch(2, supplierId, skuId, 5, String.format("batch-%s-%s", skuId, 2)));
        skuStockBatchList.add(new SplitStockoutStockBatch.SkuStockBatch(3, supplierId, skuId, 3, String.format("batch-%s-%s", skuId, 3)));

        List<SplitStockoutStockBatch.StockoutSku> stockoutSkuList = Lists.newArrayList();
        stockoutSkuList.add(new SplitStockoutStockBatch.StockoutSku(externalKey, supplierId, skuId, 6));
        SplitStockoutStockBatch stockBatch = new SplitStockoutStockBatch(warehouseId, stockoutSkuList, skuStockBatchList);
        stockBatch.addSplitSortEnum(SplitStockoutStockBatch.SortEnum.WEIGHT)
                // 先找到第一个足够到批次
                .setEnoughFirst(true)
                // 是否加锁
                .setWarnQtyTryLock(true)
                .splitBatchSkuStock();
        List<SplitStockoutStockBatch.StockoutSkuBatch> stockoutSkuBatchList = stockBatch.getStockoutSkuBatchList();

        Assert.assertEquals(1, stockoutSkuBatchList.size());
        Assert.assertEquals(6, stockoutSkuBatchList.get(0).goodQty);
    }

    /**
     * 找到第一个足够扣减的库存批次，根据条件判断是否加锁
     */
    @Test
    public void test_SplitStockoutStockBatchTest_TC3() {

        int skuId = 111;
        List<SplitStockoutStockBatch.SkuStockBatch> skuStockBatchList = Lists.newArrayList();

        skuStockBatchList.add(new SplitStockoutStockBatch.SkuStockBatch(1, supplierId, skuId, 5, String.format("batch-%s-%s", skuId, 1)));
        skuStockBatchList.add(new SplitStockoutStockBatch.SkuStockBatch(2, supplierId, skuId, 6, String.format("batch-%s-%s", skuId, 2)));
        skuStockBatchList.add(new SplitStockoutStockBatch.SkuStockBatch(3, supplierId, skuId, 3, String.format("batch-%s-%s", skuId, 3)));

        List<SplitStockoutStockBatch.StockoutSku> stockoutSkuList = Lists.newArrayList();
        stockoutSkuList.add(new SplitStockoutStockBatch.StockoutSku(externalKey, supplierId, skuId, 6));
        SplitStockoutStockBatch stockBatch = new SplitStockoutStockBatch(warehouseId, stockoutSkuList, skuStockBatchList)
                // 先找到第一个足够到批次
                .setEnoughFirst(true)
                // 是否加锁
                .setWarnQtyTryLock(false)
                .addSplitSortEnum(SplitStockoutStockBatch.SortEnum.WEIGHT);
        stockBatch.splitBatchSkuStock();

        List<SplitStockoutStockBatch.StockoutSkuBatch> stockoutSkuBatchList = stockBatch.getStockoutSkuBatchList();

        Assert.assertEquals(1, stockoutSkuBatchList.size());
        Assert.assertEquals(6, stockoutSkuBatchList.get(0).goodQty);

//
//        List<SplitStockoutStockBatch.JedisLock> jedisLockList = stockBatch.getJedisLockList();
//
//        Assert.assertEquals(1, jedisLockList.size());
//        Assert.assertEquals(String.format("%s-%s-%s", supplierId, skuId, stockoutSkuBatchList.get(0).batchNo), jedisLockList.get(0).getKey());
    }

    /**
     * 找到第一个足够扣减的库存批次，同时设置不允许加锁，则切分后无锁
     */
    @Test
    public void test_SplitStockoutStockBatchTest_TC4() {

        int skuId = 111;
        List<SplitStockoutStockBatch.SkuStockBatch> skuStockBatchList = Lists.newArrayList();

        skuStockBatchList.add(new SplitStockoutStockBatch.SkuStockBatch(1, supplierId, skuId, 5, String.format("batch-%s-%s", skuId, 1)));
        skuStockBatchList.add(new SplitStockoutStockBatch.SkuStockBatch(2, supplierId, skuId, 6, String.format("batch-%s-%s", skuId, 2)));
        skuStockBatchList.add(new SplitStockoutStockBatch.SkuStockBatch(3, supplierId, skuId, 3, String.format("batch-%s-%s", skuId, 3)));

        List<SplitStockoutStockBatch.StockoutSku> stockoutSkuList = Lists.newArrayList();
        stockoutSkuList.add(new SplitStockoutStockBatch.StockoutSku(externalKey, supplierId, skuId, 6));
        SplitStockoutStockBatch stockBatch = new SplitStockoutStockBatch(warehouseId, stockoutSkuList, skuStockBatchList)
                // 切分顺序
                .addSplitSortEnum(SplitStockoutStockBatch.SortEnum.WEIGHT)
                // 先找到第一个足够到批次
                .setEnoughFirst(true)
                // 是否加锁
                .setWarnQtyTryLock(false);

        stockBatch.splitBatchSkuStock();

        List<SplitStockoutStockBatch.StockoutSkuBatch> stockoutSkuBatchList = stockBatch.getStockoutSkuBatchList();

        Assert.assertEquals(1, stockoutSkuBatchList.size());
        Assert.assertEquals(6, stockoutSkuBatchList.get(0).goodQty);

//
//        List<SplitStockoutStockBatch.JedisLock> jedisLockList = stockBatch.getJedisLockList();
//
//        Assert.assertEquals(0, jedisLockList.size());
    }

    /**
     * 构造第一个出库单 两个SKU
     * 构造第个出库单 一个SKU
     */
    @Test
    public void test_SplitStockoutStockBatchTest_TC5() {

        String stockoutNo1 = "stockout-no-1";
        String stockoutNo2 = "stockout-no-2";

        int skuId1 = 1;
        int skuId2 = 2;

        List<SplitStockoutStockBatch.StockoutSku> stockoutSkuList = Lists.newArrayList();
        {
            SplitStockoutStockBatch.StockoutSku sku = new SplitStockoutStockBatch.StockoutSku();
            sku.externalKey = stockoutNo1;
            sku.skuId = skuId1;
            sku.supplierId = supplierId;
            sku.goodQty = 9;
            sku.badQty = 9;
            sku.specimenQty = 9;
            stockoutSkuList.add(sku);
        }
        {
            SplitStockoutStockBatch.StockoutSku sku = new SplitStockoutStockBatch.StockoutSku();
            sku.externalKey = stockoutNo1;
            sku.skuId = skuId2;
            sku.supplierId = supplierId;
            sku.goodQty = 9;
            sku.badQty = 9;
            sku.specimenQty = 9;
            stockoutSkuList.add(sku);
        }
        {
            SplitStockoutStockBatch.StockoutSku sku = new SplitStockoutStockBatch.StockoutSku();
            sku.externalKey = stockoutNo2;
            sku.skuId = skuId1;
            sku.supplierId = supplierId;
            sku.goodQty = 5;
            sku.badQty = 5;
            stockoutSkuList.add(sku);
        }

        List<SplitStockoutStockBatch.SkuStockBatch> stockBatchList = Lists.newArrayList();
        {
            SplitStockoutStockBatch.SkuStockBatch sku = new SplitStockoutStockBatch.SkuStockBatch();
            sku.weight = 1;
            sku.skuId = skuId1;
            sku.supplierId = supplierId;
            sku.goodQty = 4;
            sku.badQty = 5;
            sku.specimenQty = 9;
            sku.batchNo = "batch-1";
            stockBatchList.add(sku);
        }
        {
            SplitStockoutStockBatch.SkuStockBatch sku = new SplitStockoutStockBatch.SkuStockBatch();
            sku.weight = 2;
            sku.skuId = skuId1;
            sku.supplierId = supplierId;
            sku.goodQty = 2;
            sku.badQty = 10;
            sku.specimenQty = 9;
            sku.batchNo = "batch-2";
            stockBatchList.add(sku);
        }
        {
            SplitStockoutStockBatch.SkuStockBatch sku = new SplitStockoutStockBatch.SkuStockBatch();
            sku.weight = 3;
            sku.skuId = skuId1;
            sku.supplierId = supplierId;
            sku.goodQty = 20;
            sku.badQty = 10;
            sku.specimenQty = 10;
            sku.batchNo = "batch-3";
            stockBatchList.add(sku);
        }
        {
            SplitStockoutStockBatch.SkuStockBatch sku = new SplitStockoutStockBatch.SkuStockBatch();
            sku.weight = 4;
            sku.skuId = skuId2;
            sku.supplierId = supplierId;
            sku.goodQty = 9;
            sku.badQty = 9;
            sku.specimenQty = 5;
            sku.batchNo = "batch-4";
            stockBatchList.add(sku);
        }
        {
            SplitStockoutStockBatch.SkuStockBatch sku = new SplitStockoutStockBatch.SkuStockBatch();
            sku.weight = 5;
            sku.skuId = skuId2;
            sku.supplierId = supplierId;
            sku.goodQty = 20;
            sku.badQty = 20;
            sku.specimenQty = 20;
            sku.batchNo = "batch-5";
            stockBatchList.add(sku);
        }

        SplitStockoutStockBatch split = new SplitStockoutStockBatch(warehouseId, stockoutSkuList, stockBatchList);
        split.splitBatchSkuStock();
        List<SplitStockoutStockBatch.StockoutSkuBatch> stockoutSkuBatchList = split.getStockoutSkuBatchList();

        {
            List<SplitStockoutStockBatch.StockoutSkuBatch> skuBatchList = stockoutSkuBatchList.stream().filter(k -> Objects.equals(k.skuId, skuId1)).collect(Collectors.toList());
            Assert.assertEquals(5, skuBatchList.size());

            assertThat(skuBatchList, contains(
                    allOf(
                            hasPropertyExt("externalKey", Matchers.equalTo(stockoutNo1)),
                            hasPropertyExt("supplierId", Matchers.equalTo(supplierId)),
                            hasPropertyExt("skuId", Matchers.equalTo(skuId1)),
                            hasPropertyExt("goodQty", Matchers.equalTo(4)),
                            hasPropertyExt("badQty", Matchers.equalTo(5)),
                            hasPropertyExt("specimenQty", Matchers.equalTo(9)),
                            hasPropertyExt("batchNo", Matchers.equalTo("batch-1"))
                    ),
                    allOf(
                            hasPropertyExt("externalKey", Matchers.equalTo(stockoutNo1)),
                            hasPropertyExt("supplierId", Matchers.equalTo(supplierId)),
                            hasPropertyExt("skuId", Matchers.equalTo(skuId1)),
                            hasPropertyExt("goodQty", Matchers.equalTo(2)),
                            hasPropertyExt("badQty", Matchers.equalTo(4)),
                            hasPropertyExt("specimenQty", Matchers.equalTo(0)),
                            hasPropertyExt("batchNo", Matchers.equalTo("batch-2"))
                    ),
                    allOf(
                            hasPropertyExt("externalKey", Matchers.equalTo(stockoutNo1)),
                            hasPropertyExt("supplierId", Matchers.equalTo(supplierId)),
                            hasPropertyExt("skuId", Matchers.equalTo(skuId1)),
                            hasPropertyExt("goodQty", Matchers.equalTo(3)),
                            hasPropertyExt("badQty", Matchers.equalTo(0)),
                            hasPropertyExt("specimenQty", Matchers.equalTo(0)),
                            hasPropertyExt("batchNo", Matchers.equalTo("batch-3"))
                    ),
                    allOf(
                            hasPropertyExt("externalKey", Matchers.equalTo(stockoutNo2)),
                            hasPropertyExt("supplierId", Matchers.equalTo(supplierId)),
                            hasPropertyExt("skuId", Matchers.equalTo(skuId1)),
                            hasPropertyExt("goodQty", Matchers.equalTo(0)),
                            hasPropertyExt("badQty", Matchers.equalTo(5)),
                            hasPropertyExt("specimenQty", Matchers.equalTo(0)),
                            hasPropertyExt("batchNo", Matchers.equalTo("batch-2"))
                    ),
                    allOf(
                            hasPropertyExt("externalKey", Matchers.equalTo(stockoutNo2)),
                            hasPropertyExt("supplierId", Matchers.equalTo(supplierId)),
                            hasPropertyExt("skuId", Matchers.equalTo(skuId1)),
                            hasPropertyExt("goodQty", Matchers.equalTo(5)),
                            hasPropertyExt("badQty", Matchers.equalTo(0)),
                            hasPropertyExt("specimenQty", Matchers.equalTo(0)),
                            hasPropertyExt("batchNo", Matchers.equalTo("batch-3"))
                    )
            ));

        }
        {
            List<SplitStockoutStockBatch.StockoutSkuBatch> skuBatchList = stockoutSkuBatchList.stream().filter(k -> Objects.equals(k.skuId, skuId2)).collect(Collectors.toList());
            Assert.assertEquals(2, skuBatchList.size());

            assertThat(skuBatchList, contains(
                    allOf(
                            hasPropertyExt("externalKey", Matchers.equalTo(stockoutNo1)),
                            hasPropertyExt("supplierId", Matchers.equalTo(supplierId)),
                            hasPropertyExt("skuId", Matchers.equalTo(skuId2)),
                            hasPropertyExt("goodQty", Matchers.equalTo(9)),
                            hasPropertyExt("badQty", Matchers.equalTo(9)),
                            hasPropertyExt("specimenQty", Matchers.equalTo(5)),
                            hasPropertyExt("batchNo", Matchers.equalTo("batch-4"))
                    ),
                    allOf(
                            hasPropertyExt("externalKey", Matchers.equalTo(stockoutNo1)),
                            hasPropertyExt("supplierId", Matchers.equalTo(supplierId)),
                            hasPropertyExt("skuId", Matchers.equalTo(skuId2)),
                            hasPropertyExt("goodQty", Matchers.equalTo(0)),
                            hasPropertyExt("badQty", Matchers.equalTo(0)),
                            hasPropertyExt("specimenQty", Matchers.equalTo(4)),
                            hasPropertyExt("batchNo", Matchers.equalTo("batch-5"))
                    )
            ));

        }
    }

    /**
     * 单SKU 多出库单
     */
    @Test
    public void test_SplitStockoutStockBatchTest_TC6() {

        int skuId = 1;
        List<SplitStockoutStockBatch.SkuStockBatch> stockoutSkuBatchList = Lists.newArrayList();

        stockoutSkuBatchList.add(new SplitStockoutStockBatch.SkuStockBatch(1, supplierId, skuId, 3, String.format("%s-%s", skuId, 1)));
        stockoutSkuBatchList.add(new SplitStockoutStockBatch.SkuStockBatch(2, supplierId, skuId, 2, String.format("%s-%s", skuId, 2)));
        stockoutSkuBatchList.add(new SplitStockoutStockBatch.SkuStockBatch(3, supplierId, skuId, 6, String.format("%s-%s", skuId, 3)));
        stockoutSkuBatchList.add(new SplitStockoutStockBatch.SkuStockBatch(4, supplierId, skuId, 5, String.format("%s-%s", skuId, 4)));

        List<SplitStockoutStockBatch.StockoutSku> boxList = Lists.newArrayList();
        boxList.add(new SplitStockoutStockBatch.StockoutSku("box-1", supplierId, skuId, 7));
        boxList.add(new SplitStockoutStockBatch.StockoutSku("box-2", supplierId, skuId, 3));
        boxList.add(new SplitStockoutStockBatch.StockoutSku("box-3", supplierId, skuId, 6));
        SplitStockoutStockBatch boxBatch = new SplitStockoutStockBatch(1, boxList, stockoutSkuBatchList);
        boxBatch.splitBatchSkuStock();
        List<SplitStockoutStockBatch.StockoutSkuBatch> stockoutSkuBatchList1 = boxBatch.getStockoutSkuBatchList();

        Map<String, List<SplitStockoutStockBatch.StockoutSkuBatch>> map = stockoutSkuBatchList1.stream().collect(Collectors.groupingBy(k -> k.externalKey));
        Assert.assertEquals(3, map.size());

        {
            List<SplitStockoutStockBatch.StockoutSkuBatch> stockoutSkuBatches = map.get("box-1");
            Assert.assertEquals(3, stockoutSkuBatches.size());

            assertThat(stockoutSkuBatches, contains(
                    AllOf.allOf(
                            hasPropertyExt("supplierId", Matchers.equalTo(supplierId)),
                            hasPropertyExt("skuId", Matchers.equalTo(skuId)),
                            hasPropertyExt("goodQty", Matchers.equalTo(3)),
                            hasPropertyExt("batchNo", Matchers.equalTo(String.format("%s-%s", skuId, 1)))

                    ),
                    AllOf.allOf(
                            hasPropertyExt("supplierId", Matchers.equalTo(supplierId)),
                            hasPropertyExt("skuId", Matchers.equalTo(skuId)),
                            hasPropertyExt("goodQty", Matchers.equalTo(2)),
                            hasPropertyExt("batchNo", Matchers.equalTo(String.format("%s-%s", skuId, 2)))

                    ),
                    AllOf.allOf(
                            hasPropertyExt("supplierId", Matchers.equalTo(supplierId)),
                            hasPropertyExt("skuId", Matchers.equalTo(skuId)),
                            hasPropertyExt("goodQty", Matchers.equalTo(2)),
                            hasPropertyExt("batchNo", Matchers.equalTo(String.format("%s-%s", skuId, 3)))

                    )
            ));
        }
        {
            List<SplitStockoutStockBatch.StockoutSkuBatch> stockoutSkuBatches = map.get("box-2");
            Assert.assertEquals(1, stockoutSkuBatches.size());
            assertThat(stockoutSkuBatches, Matchers.contains(
                    AllOf.allOf(
                            hasPropertyExt("supplierId", Matchers.equalTo(supplierId)),
                            hasPropertyExt("skuId", Matchers.equalTo(skuId)),
                            hasPropertyExt("goodQty", Matchers.equalTo(3)),
                            hasPropertyExt("batchNo", Matchers.equalTo(String.format("%s-%s", skuId, 3)))

                    )
            ));
        }
        {
            List<SplitStockoutStockBatch.StockoutSkuBatch> stockoutSkuBatches = map.get("box-3");
            Assert.assertEquals(2, stockoutSkuBatches.size());

            assertThat(stockoutSkuBatches, contains(
                    AllOf.allOf(
                            hasPropertyExt("supplierId", Matchers.equalTo(supplierId)),
                            hasPropertyExt("skuId", Matchers.equalTo(skuId)),
                            hasPropertyExt("goodQty", Matchers.equalTo(1)),
                            hasPropertyExt("batchNo", Matchers.equalTo(String.format("%s-%s", skuId, 3)))

                    ),
                    AllOf.allOf(
                            hasPropertyExt("supplierId", Matchers.equalTo(supplierId)),
                            hasPropertyExt("skuId", Matchers.equalTo(skuId)),
                            hasPropertyExt("goodQty", Matchers.equalTo(5)),
                            hasPropertyExt("batchNo", Matchers.equalTo(String.format("%s-%s", skuId, 4)))

                    )
            ));
        }
    }

    /**
     * 多SKU 多出库单
     */
    @Test
    public void test_SplitStockoutStockBatchTest_TC7() {
        int skuId = 1;
        int skuId2 = 2;
        List<SplitStockoutStockBatch.SkuStockBatch> stockoutSkuBatchList = Lists.newArrayList();
        {
            stockoutSkuBatchList.add(new SplitStockoutStockBatch.SkuStockBatch(1, supplierId, skuId, 3, String.format("%s-%s", skuId, 1)));
            stockoutSkuBatchList.add(new SplitStockoutStockBatch.SkuStockBatch(2, supplierId, skuId, 2, String.format("%s-%s", skuId, 2)));
            stockoutSkuBatchList.add(new SplitStockoutStockBatch.SkuStockBatch(3, supplierId, skuId, 6, String.format("%s-%s", skuId, 3)));
        }
        {
            stockoutSkuBatchList.add(new SplitStockoutStockBatch.SkuStockBatch(1, supplierId, skuId2, 4, String.format("%s-%s", skuId, 1)));
            stockoutSkuBatchList.add(new SplitStockoutStockBatch.SkuStockBatch(2, supplierId, skuId2, 1, String.format("%s-%s", skuId, 2)));
            stockoutSkuBatchList.add(new SplitStockoutStockBatch.SkuStockBatch(3, supplierId, skuId2, 7, String.format("%s-%s", skuId, 3)));
        }

        List<SplitStockoutStockBatch.StockoutSku> boxList = Lists.newArrayList();

        boxList.add(new SplitStockoutStockBatch.StockoutSku("box-1", supplierId, skuId, 2));
        boxList.add(new SplitStockoutStockBatch.StockoutSku("box-1", supplierId, skuId2, 3));

        boxList.add(new SplitStockoutStockBatch.StockoutSku("box-2", supplierId, skuId, 9));
        boxList.add(new SplitStockoutStockBatch.StockoutSku("box-2", supplierId, skuId2, 4));


        boxList.add(new SplitStockoutStockBatch.StockoutSku("box-3", supplierId, skuId2, 5));
        SplitStockoutStockBatch boxBatch = new SplitStockoutStockBatch(1, boxList, stockoutSkuBatchList);
        boxBatch.splitBatchSkuStock();
        List<SplitStockoutStockBatch.StockoutSkuBatch> stockoutSkuBatchList1 = boxBatch.getStockoutSkuBatchList();


        Map<String, List<SplitStockoutStockBatch.StockoutSkuBatch>> map = stockoutSkuBatchList1.stream().collect(Collectors.groupingBy(k -> k.externalKey));
        Assert.assertEquals(3, map.size());
        {
            List<SplitStockoutStockBatch.StockoutSkuBatch> boxSkuBatchList = map.get("box-1");
            Assert.assertEquals(2, boxSkuBatchList.size());

            boxSkuBatchList.sort(Comparator.comparing(k -> k.skuId));

            assertThat(boxSkuBatchList, contains(
                    AllOf.allOf(
                            hasPropertyExt("supplierId", Matchers.equalTo(supplierId)),
                            hasPropertyExt("skuId", Matchers.equalTo(skuId)),
                            hasPropertyExt("goodQty", Matchers.equalTo(2)),
                            hasPropertyExt("batchNo", Matchers.equalTo(String.format("%s-%s", skuId, 1)))

                    ),
                    AllOf.allOf(
                            hasPropertyExt("supplierId", Matchers.equalTo(supplierId)),
                            hasPropertyExt("skuId", Matchers.equalTo(skuId2)),
                            hasPropertyExt("goodQty", Matchers.equalTo(3)),
                            hasPropertyExt("batchNo", Matchers.equalTo(String.format("%s-%s", skuId, 1)))

                    )
            ));
        }
        {
            List<SplitStockoutStockBatch.StockoutSkuBatch> boxSkuBatchList = map.get("box-2");
            Assert.assertEquals(6, boxSkuBatchList.size());
            boxSkuBatchList.sort(Comparator.comparing(k -> k.skuId));
            assertThat(boxSkuBatchList, contains(
                    AllOf.allOf(
                            hasPropertyExt("supplierId", Matchers.equalTo(supplierId)),
                            hasPropertyExt("skuId", Matchers.equalTo(skuId)),
                            hasPropertyExt("goodQty", Matchers.equalTo(1)),
                            hasPropertyExt("batchNo", Matchers.equalTo(String.format("%s-%s", skuId, 1)))

                    ),
                    AllOf.allOf(
                            hasPropertyExt("supplierId", Matchers.equalTo(supplierId)),
                            hasPropertyExt("skuId", Matchers.equalTo(skuId)),
                            hasPropertyExt("goodQty", Matchers.equalTo(2)),
                            hasPropertyExt("batchNo", Matchers.equalTo(String.format("%s-%s", skuId, 2)))

                    ),
                    AllOf.allOf(
                            hasPropertyExt("supplierId", Matchers.equalTo(supplierId)),
                            hasPropertyExt("skuId", Matchers.equalTo(skuId)),
                            hasPropertyExt("goodQty", Matchers.equalTo(6)),
                            hasPropertyExt("batchNo", Matchers.equalTo(String.format("%s-%s", skuId, 3)))

                    ),
                    AllOf.allOf(
                            hasPropertyExt("supplierId", Matchers.equalTo(supplierId)),
                            hasPropertyExt("supplierId", Matchers.equalTo(supplierId)),
                            hasPropertyExt("skuId", Matchers.equalTo(skuId2)),
                            hasPropertyExt("goodQty", Matchers.equalTo(1)),
                            hasPropertyExt("batchNo", Matchers.equalTo(String.format("%s-%s", skuId, 1)))

                    ),
                    AllOf.allOf(
                            hasPropertyExt("supplierId", Matchers.equalTo(supplierId)),
                            hasPropertyExt("skuId", Matchers.equalTo(skuId2)),
                            hasPropertyExt("goodQty", Matchers.equalTo(1)),
                            hasPropertyExt("batchNo", Matchers.equalTo(String.format("%s-%s", skuId, 2)))

                    ),
                    AllOf.allOf(
                            hasPropertyExt("supplierId", Matchers.equalTo(supplierId)),
                            hasPropertyExt("skuId", Matchers.equalTo(skuId2)),
                            hasPropertyExt("goodQty", Matchers.equalTo(2)),
                            hasPropertyExt("batchNo", Matchers.equalTo(String.format("%s-%s", skuId, 3)))

                    )
            ));
        }
        {
            List<SplitStockoutStockBatch.StockoutSkuBatch> boxSkuBatchList = map.get("box-3");
            Assert.assertEquals(1, boxSkuBatchList.size());

            assertThat(boxSkuBatchList, Matchers.contains(
                    AllOf.allOf(
                            hasPropertyExt("supplierId", Matchers.equalTo(supplierId)),
                            hasPropertyExt("skuId", Matchers.equalTo(skuId2)),
                            hasPropertyExt("goodQty", Matchers.equalTo(5)),
                            hasPropertyExt("batchNo", Matchers.equalTo(String.format("%s-%s", skuId, 3)))
                    )
            ));
        }
    }

    /**
     * 异常场景-出库单内SKU重复
     */
    @Test
    public void test_SplitStockoutStockBatchTest_TC8() {

        int skuId = 1;
        int skuId2 = 2;
        List<SplitStockoutStockBatch.SkuStockBatch> stockoutSkuBatchList = Lists.newArrayList();
        {
            stockoutSkuBatchList.add(new SplitStockoutStockBatch.SkuStockBatch(1, supplierId, skuId, 3, String.format("%s-%s", skuId, 1)));
            stockoutSkuBatchList.add(new SplitStockoutStockBatch.SkuStockBatch(2, supplierId, skuId, 2, String.format("%s-%s", skuId, 2)));
            stockoutSkuBatchList.add(new SplitStockoutStockBatch.SkuStockBatch(3, supplierId, skuId, 6, String.format("%s-%s", skuId, 3)));
        }
        {
            stockoutSkuBatchList.add(new SplitStockoutStockBatch.SkuStockBatch(1, supplierId, skuId2, 4, String.format("%s-%s", skuId, 1)));
            stockoutSkuBatchList.add(new SplitStockoutStockBatch.SkuStockBatch(2, supplierId, skuId2, 1, String.format("%s-%s", skuId, 2)));
            stockoutSkuBatchList.add(new SplitStockoutStockBatch.SkuStockBatch(3, supplierId, skuId2, 7, String.format("%s-%s", skuId, 3)));
        }

        List<SplitStockoutStockBatch.StockoutSku> boxList = Lists.newArrayList();

        boxList.add(new SplitStockoutStockBatch.StockoutSku("box-1", supplierId, skuId, 2));
        boxList.add(new SplitStockoutStockBatch.StockoutSku("box-1", supplierId, skuId2, 3));
        boxList.add(new SplitStockoutStockBatch.StockoutSku("box-1", supplierId, skuId2, 3));


        try {
            SplitStockoutStockBatch boxBatch = new SplitStockoutStockBatch(1, boxList, stockoutSkuBatchList);
            boxBatch.splitBatchSkuStock();
            boxBatch.getStockoutSkuBatchList();
            fail();
        } catch (ServiceRuntimeException e) {

            Assert.assertTrue(e.getMsg().contains("重复"));
        }
    }

    /**
     * 异常场景-出库单内SKU重复
     */
    @Test
    public void test_SplitStockoutStockBatchTest_TC9() {

        int skuId = 1;
        int skuId2 = 2;
        List<SplitStockoutStockBatch.SkuStockBatch> stockoutSkuBatchList = Lists.newArrayList();
        {
            stockoutSkuBatchList.add(new SplitStockoutStockBatch.SkuStockBatch(1, supplierId, skuId, 4, String.format("%s-%s", skuId, 1)));
            stockoutSkuBatchList.add(new SplitStockoutStockBatch.SkuStockBatch(2, supplierId, skuId, 2, String.format("%s-%s", skuId, 2)));
            stockoutSkuBatchList.add(new SplitStockoutStockBatch.SkuStockBatch(3, supplierId, skuId, 6, String.format("%s-%s", skuId, 3)));
        }
        {
            stockoutSkuBatchList.add(new SplitStockoutStockBatch.SkuStockBatch(1, supplierId, skuId2, 4, String.format("%s-%s", skuId, 1)));
            stockoutSkuBatchList.add(new SplitStockoutStockBatch.SkuStockBatch(2, supplierId, skuId2, 1, String.format("%s-%s", skuId, 2)));
            stockoutSkuBatchList.add(new SplitStockoutStockBatch.SkuStockBatch(3, supplierId, skuId2, 7, String.format("%s-%s", skuId, 3)));
        }

        List<SplitStockoutStockBatch.StockoutSku> boxList = Lists.newArrayList();

        boxList.add(new SplitStockoutStockBatch.StockoutSku("box-1", supplierId, skuId, 8));
        boxList.add(new SplitStockoutStockBatch.StockoutSku("box-1", supplierId, skuId2, 3));
        boxList.add(new SplitStockoutStockBatch.StockoutSku("box-2", supplierId, skuId, 5));

        try {
            SplitStockoutStockBatch boxBatch = new SplitStockoutStockBatch(7, boxList, stockoutSkuBatchList);
            boxBatch.splitBatchSkuStock();
            boxBatch.getStockoutSkuBatchList();
            fail();
        } catch (ServiceRuntimeException e) {

            Assert.assertTrue(e.getMsg().contains("库存扣减不足"));
        }
    }

}
