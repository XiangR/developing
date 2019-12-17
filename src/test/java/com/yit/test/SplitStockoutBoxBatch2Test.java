package com.yit.test;

import com.google.common.collect.Lists;
import com.joker.utils.SplitStockoutBoxBatch2;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Test;

import java.util.Comparator;
import java.util.List;

import static com.yit.test.extend.MyHasPropertiesMatcher.hasPropertyExt;
import static com.yit.test.extend.MyMatchers.contains;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.AllOf.allOf;

/**
 * Created by xiangrui on 2019-07-15.
 *
 * @author xiangrui
 * @date 2019-07-15
 */
public class SplitStockoutBoxBatch2Test {

    private int supplierId = 1;

    @Test
    public void test_SplitStockoutBoxBatch2Test_TC1() {

        int skuId = 1;
        List<SplitStockoutBoxBatch2.StockoutSkuBatch> stockoutSkuBatchList = Lists.newArrayList();

        stockoutSkuBatchList.add(new SplitStockoutBoxBatch2.StockoutSkuBatch(supplierId, skuId, 3, String.format("%s-%s", skuId, 1), 1));
        stockoutSkuBatchList.add(new SplitStockoutBoxBatch2.StockoutSkuBatch(supplierId, skuId, 2, String.format("%s-%s", skuId, 2), 2));
        stockoutSkuBatchList.add(new SplitStockoutBoxBatch2.StockoutSkuBatch(supplierId, skuId, 6, String.format("%s-%s", skuId, 3), 3));
        stockoutSkuBatchList.add(new SplitStockoutBoxBatch2.StockoutSkuBatch(supplierId, skuId, 5, String.format("%s-%s", skuId, 4), 4));

        List<SplitStockoutBoxBatch2.Box> boxList = Lists.newArrayList();
        {
            List<SplitStockoutBoxBatch2.BoxSku> list = Lists.newArrayList();
            list.add(new SplitStockoutBoxBatch2.BoxSku(supplierId, skuId, 7));
            boxList.add(new SplitStockoutBoxBatch2.Box("box-1", list));
        }
        {
            List<SplitStockoutBoxBatch2.BoxSku> list = Lists.newArrayList();
            list.add(new SplitStockoutBoxBatch2.BoxSku(supplierId, skuId, 3));
            boxList.add(new SplitStockoutBoxBatch2.Box("box-2", list));
        }
        {
            List<SplitStockoutBoxBatch2.BoxSku> list = Lists.newArrayList();
            list.add(new SplitStockoutBoxBatch2.BoxSku(supplierId, skuId, 6));
            boxList.add(new SplitStockoutBoxBatch2.Box("box-3", list));
        }
        SplitStockoutBoxBatch2 boxBatch = new SplitStockoutBoxBatch2(stockoutSkuBatchList, boxList);
        boxBatch.splitAllBoxBatch();
        List<SplitStockoutBoxBatch2.BoxBatch> boxBatchList = boxBatch.getBoxBatchList();

        Assert.assertEquals(3, boxBatchList.size());

        {
            List<SplitStockoutBoxBatch2.BoxSkuBatch> boxSkuBatchList = boxBatchList.get(0).boxSkuBatchList;
            Assert.assertEquals(3, boxSkuBatchList.size());

            assertThat(boxSkuBatchList, contains(
                    allOf(
                            hasPropertyExt("supplierId", Matchers.equalTo(supplierId)),
                            hasPropertyExt("skuId", Matchers.equalTo(skuId)),
                            hasPropertyExt("goodQty", Matchers.equalTo(3)),
                            hasPropertyExt("batchNo", Matchers.equalTo(String.format("%s-%s", skuId, 1)))

                    ),
                    allOf(
                            hasPropertyExt("supplierId", Matchers.equalTo(supplierId)),
                            hasPropertyExt("skuId", Matchers.equalTo(skuId)),
                            hasPropertyExt("goodQty", Matchers.equalTo(2)),
                            hasPropertyExt("batchNo", Matchers.equalTo(String.format("%s-%s", skuId, 2)))

                    ),
                    allOf(
                            hasPropertyExt("supplierId", Matchers.equalTo(supplierId)),
                            hasPropertyExt("skuId", Matchers.equalTo(skuId)),
                            hasPropertyExt("goodQty", Matchers.equalTo(2)),
                            hasPropertyExt("batchNo", Matchers.equalTo(String.format("%s-%s", skuId, 3)))

                    )
            ));
        }
        {
            List<SplitStockoutBoxBatch2.BoxSkuBatch> boxSkuBatchList = boxBatchList.get(1).boxSkuBatchList;
            Assert.assertEquals(1, boxSkuBatchList.size());

            assertThat(boxSkuBatchList, contains(
                    allOf(
                            hasPropertyExt("supplierId", Matchers.equalTo(supplierId)),
                            hasPropertyExt("skuId", Matchers.equalTo(skuId)),
                            hasPropertyExt("goodQty", Matchers.equalTo(3)),
                            hasPropertyExt("batchNo", Matchers.equalTo(String.format("%s-%s", skuId, 3)))

                    )
            ));
        }
        {
            List<SplitStockoutBoxBatch2.BoxSkuBatch> boxSkuBatchList = boxBatchList.get(2).boxSkuBatchList;
            Assert.assertEquals(2, boxSkuBatchList.size());

            assertThat(boxSkuBatchList, contains(
                    allOf(
                            hasPropertyExt("supplierId", Matchers.equalTo(supplierId)),
                            hasPropertyExt("skuId", Matchers.equalTo(skuId)),
                            hasPropertyExt("goodQty", Matchers.equalTo(1)),
                            hasPropertyExt("batchNo", Matchers.equalTo(String.format("%s-%s", skuId, 3)))

                    ),
                    allOf(
                            hasPropertyExt("supplierId", Matchers.equalTo(supplierId)),
                            hasPropertyExt("skuId", Matchers.equalTo(skuId)),
                            hasPropertyExt("goodQty", Matchers.equalTo(5)),
                            hasPropertyExt("batchNo", Matchers.equalTo(String.format("%s-%s", skuId, 4)))

                    )
            ));
        }
    }

    @Test
    public void test_SplitStockoutBoxBatch2Test_TC2() {
        int skuId = 1;
        int skuId2 = 2;
        List<SplitStockoutBoxBatch2.StockoutSkuBatch> stockoutSkuBatchList = Lists.newArrayList();
        {
            stockoutSkuBatchList.add(new SplitStockoutBoxBatch2.StockoutSkuBatch(supplierId, skuId, 3, String.format("%s-%s", skuId, 1), 1));
            stockoutSkuBatchList.add(new SplitStockoutBoxBatch2.StockoutSkuBatch(supplierId, skuId, 2, String.format("%s-%s", skuId, 2), 2));
            stockoutSkuBatchList.add(new SplitStockoutBoxBatch2.StockoutSkuBatch(supplierId, skuId, 6, String.format("%s-%s", skuId, 3), 3));
        }
        {
            stockoutSkuBatchList.add(new SplitStockoutBoxBatch2.StockoutSkuBatch(supplierId, skuId2, 4, String.format("%s-%s", skuId, 1), 1));
            stockoutSkuBatchList.add(new SplitStockoutBoxBatch2.StockoutSkuBatch(supplierId, skuId2, 1, String.format("%s-%s", skuId, 2), 2));
            stockoutSkuBatchList.add(new SplitStockoutBoxBatch2.StockoutSkuBatch(supplierId, skuId2, 7, String.format("%s-%s", skuId, 3), 3));
        }

        List<SplitStockoutBoxBatch2.Box> boxList = Lists.newArrayList();

        {
            List<SplitStockoutBoxBatch2.BoxSku> list = Lists.newArrayList();
            list.add(new SplitStockoutBoxBatch2.BoxSku(supplierId, skuId, 2));
            list.add(new SplitStockoutBoxBatch2.BoxSku(supplierId, skuId2, 3));
            boxList.add(new SplitStockoutBoxBatch2.Box("box-1", list));

        }
        {
            List<SplitStockoutBoxBatch2.BoxSku> list = Lists.newArrayList();
            list.add(new SplitStockoutBoxBatch2.BoxSku(supplierId, skuId, 9));
            list.add(new SplitStockoutBoxBatch2.BoxSku(supplierId, skuId2, 4));
            boxList.add(new SplitStockoutBoxBatch2.Box("box-2", list));

        }
        {
            List<SplitStockoutBoxBatch2.BoxSku> list = Lists.newArrayList();
            list.add(new SplitStockoutBoxBatch2.BoxSku(supplierId, skuId2, 5));
            boxList.add(new SplitStockoutBoxBatch2.Box("box-3", list));

        }
        SplitStockoutBoxBatch2 boxBatch = new SplitStockoutBoxBatch2(stockoutSkuBatchList, boxList);
        boxBatch.splitAllBoxBatch();
        List<SplitStockoutBoxBatch2.BoxBatch> boxBatchList = boxBatch.getBoxBatchList();

        Assert.assertEquals(3, boxBatchList.size());
        {
            List<SplitStockoutBoxBatch2.BoxSkuBatch> boxSkuBatchList = boxBatchList.get(0).boxSkuBatchList;
            Assert.assertEquals(2, boxSkuBatchList.size());

            boxSkuBatchList.sort(Comparator.comparing(k -> k.skuId));

            assertThat(boxSkuBatchList, contains(
                    allOf(
                            hasPropertyExt("supplierId", Matchers.equalTo(supplierId)),
                            hasPropertyExt("skuId", Matchers.equalTo(skuId)),
                            hasPropertyExt("goodQty", Matchers.equalTo(2)),
                            hasPropertyExt("batchNo", Matchers.equalTo(String.format("%s-%s", skuId, 1)))

                    ),
                    allOf(
                            hasPropertyExt("supplierId", Matchers.equalTo(supplierId)),
                            hasPropertyExt("skuId", Matchers.equalTo(skuId2)),
                            hasPropertyExt("goodQty", Matchers.equalTo(3)),
                            hasPropertyExt("batchNo", Matchers.equalTo(String.format("%s-%s", skuId, 1)))

                    )
            ));
        }
        {
            List<SplitStockoutBoxBatch2.BoxSkuBatch> boxSkuBatchList = boxBatchList.get(1).boxSkuBatchList;
            Assert.assertEquals(6, boxSkuBatchList.size());

            boxSkuBatchList.sort(Comparator.comparing(k -> k.skuId));

            assertThat(boxSkuBatchList, contains(
                    allOf(
                            hasPropertyExt("supplierId", Matchers.equalTo(supplierId)),
                            hasPropertyExt("skuId", Matchers.equalTo(skuId)),
                            hasPropertyExt("goodQty", Matchers.equalTo(1)),
                            hasPropertyExt("batchNo", Matchers.equalTo(String.format("%s-%s", skuId, 1)))

                    ),
                    allOf(
                            hasPropertyExt("supplierId", Matchers.equalTo(supplierId)),
                            hasPropertyExt("skuId", Matchers.equalTo(skuId)),
                            hasPropertyExt("goodQty", Matchers.equalTo(2)),
                            hasPropertyExt("batchNo", Matchers.equalTo(String.format("%s-%s", skuId, 2)))

                    ),
                    allOf(
                            hasPropertyExt("supplierId", Matchers.equalTo(supplierId)),
                            hasPropertyExt("skuId", Matchers.equalTo(skuId)),
                            hasPropertyExt("goodQty", Matchers.equalTo(6)),
                            hasPropertyExt("batchNo", Matchers.equalTo(String.format("%s-%s", skuId, 3)))

                    ),
                    allOf(
                            hasPropertyExt("supplierId", Matchers.equalTo(supplierId)),
                            hasPropertyExt("supplierId", Matchers.equalTo(supplierId)),
                            hasPropertyExt("skuId", Matchers.equalTo(skuId2)),
                            hasPropertyExt("goodQty", Matchers.equalTo(1)),
                            hasPropertyExt("batchNo", Matchers.equalTo(String.format("%s-%s", skuId, 1)))

                    ),
                    allOf(
                            hasPropertyExt("supplierId", Matchers.equalTo(supplierId)),
                            hasPropertyExt("skuId", Matchers.equalTo(skuId2)),
                            hasPropertyExt("goodQty", Matchers.equalTo(1)),
                            hasPropertyExt("batchNo", Matchers.equalTo(String.format("%s-%s", skuId, 2)))

                    ),
                    allOf(
                            hasPropertyExt("supplierId", Matchers.equalTo(supplierId)),
                            hasPropertyExt("skuId", Matchers.equalTo(skuId2)),
                            hasPropertyExt("goodQty", Matchers.equalTo(2)),
                            hasPropertyExt("batchNo", Matchers.equalTo(String.format("%s-%s", skuId, 3)))

                    )
            ));
        }
        {
            List<SplitStockoutBoxBatch2.BoxSkuBatch> boxSkuBatchList = boxBatchList.get(2).boxSkuBatchList;
            Assert.assertEquals(1, boxSkuBatchList.size());

            assertThat(boxSkuBatchList, contains(
                    allOf(
                            hasPropertyExt("supplierId", Matchers.equalTo(supplierId)),
                            hasPropertyExt("skuId", Matchers.equalTo(skuId2)),
                            hasPropertyExt("goodQty", Matchers.equalTo(5)),
                            hasPropertyExt("batchNo", Matchers.equalTo(String.format("%s-%s", skuId, 3)))
                    )
            ));
        }
    }
}
