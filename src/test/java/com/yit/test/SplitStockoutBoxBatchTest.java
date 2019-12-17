package com.yit.test;

import com.google.common.collect.Lists;
import com.joker.utils.SplitStockoutBoxBatch;
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
public class SplitStockoutBoxBatchTest {

    @Test
    public void test_SplitStockoutBoxBatchTest_TC1() {

        int skuId = 1;
        List<SplitStockoutBoxBatch.StockoutSkuBatch> stockoutSkuBatchList = Lists.newArrayList();

        stockoutSkuBatchList.add(new SplitStockoutBoxBatch.StockoutSkuBatch(skuId, 3, String.format("%s-%s", skuId, 1), 1));
        stockoutSkuBatchList.add(new SplitStockoutBoxBatch.StockoutSkuBatch(skuId, 2, String.format("%s-%s", skuId, 2), 2));
        stockoutSkuBatchList.add(new SplitStockoutBoxBatch.StockoutSkuBatch(skuId, 6, String.format("%s-%s", skuId, 3), 3));
        stockoutSkuBatchList.add(new SplitStockoutBoxBatch.StockoutSkuBatch(skuId, 5, String.format("%s-%s", skuId, 4), 4));

        List<SplitStockoutBoxBatch.Box> boxList = Lists.newArrayList();
        {
            List<SplitStockoutBoxBatch.BoxSku> list = Lists.newArrayList();
            list.add(new SplitStockoutBoxBatch.BoxSku(skuId, 7));
            boxList.add(new SplitStockoutBoxBatch.Box("box-1", list));
        }
        {
            List<SplitStockoutBoxBatch.BoxSku> list = Lists.newArrayList();
            list.add(new SplitStockoutBoxBatch.BoxSku(skuId, 3));
            boxList.add(new SplitStockoutBoxBatch.Box("box-2", list));
        }
        {
            List<SplitStockoutBoxBatch.BoxSku> list = Lists.newArrayList();
            list.add(new SplitStockoutBoxBatch.BoxSku(skuId, 6));
            boxList.add(new SplitStockoutBoxBatch.Box("box-3", list));
        }
        SplitStockoutBoxBatch boxBatch = new SplitStockoutBoxBatch(stockoutSkuBatchList, boxList);
        boxBatch.splitAllBoxBatch();
        List<SplitStockoutBoxBatch.BoxBatch> boxBatchList = boxBatch.getBoxBatchList();

        Assert.assertEquals(3, boxBatchList.size());

        {
            List<SplitStockoutBoxBatch.BoxSkuBatch> boxSkuBatchList = boxBatchList.get(0).getBoxSkuBatchList();
            Assert.assertEquals(3, boxSkuBatchList.size());

            assertThat(boxSkuBatchList, contains(
                    allOf(
                            hasPropertyExt("skuId", Matchers.equalTo(skuId)),
                            hasPropertyExt("quantity", Matchers.equalTo(3)),
                            hasPropertyExt("batchNo", Matchers.equalTo(String.format("%s-%s", skuId, 1)))

                    ),
                    allOf(
                            hasPropertyExt("skuId", Matchers.equalTo(skuId)),
                            hasPropertyExt("quantity", Matchers.equalTo(2)),
                            hasPropertyExt("batchNo", Matchers.equalTo(String.format("%s-%s", skuId, 2)))

                    ),
                    allOf(
                            hasPropertyExt("skuId", Matchers.equalTo(skuId)),
                            hasPropertyExt("quantity", Matchers.equalTo(2)),
                            hasPropertyExt("batchNo", Matchers.equalTo(String.format("%s-%s", skuId, 3)))

                    )
            ));
        }
        {
            List<SplitStockoutBoxBatch.BoxSkuBatch> boxSkuBatchList = boxBatchList.get(1).getBoxSkuBatchList();
            Assert.assertEquals(1, boxSkuBatchList.size());

            assertThat(boxSkuBatchList, contains(
                    allOf(
                            hasPropertyExt("skuId", Matchers.equalTo(skuId)),
                            hasPropertyExt("quantity", Matchers.equalTo(3)),
                            hasPropertyExt("batchNo", Matchers.equalTo(String.format("%s-%s", skuId, 3)))

                    )
            ));
        }
        {
            List<SplitStockoutBoxBatch.BoxSkuBatch> boxSkuBatchList = boxBatchList.get(2).getBoxSkuBatchList();
            Assert.assertEquals(2, boxSkuBatchList.size());

            assertThat(boxSkuBatchList, contains(
                    allOf(
                            hasPropertyExt("skuId", Matchers.equalTo(skuId)),
                            hasPropertyExt("quantity", Matchers.equalTo(1)),
                            hasPropertyExt("batchNo", Matchers.equalTo(String.format("%s-%s", skuId, 3)))

                    ),
                    allOf(
                            hasPropertyExt("skuId", Matchers.equalTo(skuId)),
                            hasPropertyExt("quantity", Matchers.equalTo(5)),
                            hasPropertyExt("batchNo", Matchers.equalTo(String.format("%s-%s", skuId, 4)))

                    )
            ));
        }
    }

    @Test
    public void test_SplitStockoutBoxBatchTest_TC2() {
        int skuId = 1;
        int skuId2 = 2;
        List<SplitStockoutBoxBatch.StockoutSkuBatch> stockoutSkuBatchList = Lists.newArrayList();
        {
            stockoutSkuBatchList.add(new SplitStockoutBoxBatch.StockoutSkuBatch(skuId, 3, String.format("%s-%s", skuId, 1), 1));
            stockoutSkuBatchList.add(new SplitStockoutBoxBatch.StockoutSkuBatch(skuId, 2, String.format("%s-%s", skuId, 2), 2));
            stockoutSkuBatchList.add(new SplitStockoutBoxBatch.StockoutSkuBatch(skuId, 6, String.format("%s-%s", skuId, 3), 3));
        }
        {
            stockoutSkuBatchList.add(new SplitStockoutBoxBatch.StockoutSkuBatch(skuId2, 4, String.format("%s-%s", skuId, 1), 1));
            stockoutSkuBatchList.add(new SplitStockoutBoxBatch.StockoutSkuBatch(skuId2, 1, String.format("%s-%s", skuId, 2), 2));
            stockoutSkuBatchList.add(new SplitStockoutBoxBatch.StockoutSkuBatch(skuId2, 7, String.format("%s-%s", skuId, 3), 3));
        }

        List<SplitStockoutBoxBatch.Box> boxList = Lists.newArrayList();

        {
            List<SplitStockoutBoxBatch.BoxSku> list = Lists.newArrayList();
            list.add(new SplitStockoutBoxBatch.BoxSku(skuId, 2));
            list.add(new SplitStockoutBoxBatch.BoxSku(skuId2, 3));
            boxList.add(new SplitStockoutBoxBatch.Box("box-1", list));

        }
        {
            List<SplitStockoutBoxBatch.BoxSku> list = Lists.newArrayList();
            list.add(new SplitStockoutBoxBatch.BoxSku(skuId, 9));
            list.add(new SplitStockoutBoxBatch.BoxSku(skuId2, 4));
            boxList.add(new SplitStockoutBoxBatch.Box("box-2", list));

        }
        {
            List<SplitStockoutBoxBatch.BoxSku> list = Lists.newArrayList();
            list.add(new SplitStockoutBoxBatch.BoxSku(skuId2, 5));
            boxList.add(new SplitStockoutBoxBatch.Box("box-3", list));

        }
        SplitStockoutBoxBatch boxBatch = new SplitStockoutBoxBatch(stockoutSkuBatchList, boxList);
        boxBatch.splitAllBoxBatch();
        List<SplitStockoutBoxBatch.BoxBatch> boxBatchList = boxBatch.getBoxBatchList();

        Assert.assertEquals(3, boxBatchList.size());
        {
            List<SplitStockoutBoxBatch.BoxSkuBatch> boxSkuBatchList = boxBatchList.get(0).getBoxSkuBatchList();
            Assert.assertEquals(2, boxSkuBatchList.size());

            boxSkuBatchList.sort(Comparator.comparing(SplitStockoutBoxBatch.BoxSkuBatch::getSkuId));

            assertThat(boxSkuBatchList, contains(
                    allOf(
                            hasPropertyExt("skuId", Matchers.equalTo(skuId)),
                            hasPropertyExt("quantity", Matchers.equalTo(2)),
                            hasPropertyExt("batchNo", Matchers.equalTo(String.format("%s-%s", skuId, 1)))

                    ),
                    allOf(
                            hasPropertyExt("skuId", Matchers.equalTo(skuId2)),
                            hasPropertyExt("quantity", Matchers.equalTo(3)),
                            hasPropertyExt("batchNo", Matchers.equalTo(String.format("%s-%s", skuId, 1)))

                    )
            ));
        }
        {
            List<SplitStockoutBoxBatch.BoxSkuBatch> boxSkuBatchList = boxBatchList.get(1).getBoxSkuBatchList();
            Assert.assertEquals(6, boxSkuBatchList.size());

            boxSkuBatchList.sort(Comparator.comparing(SplitStockoutBoxBatch.BoxSkuBatch::getSkuId));

            assertThat(boxSkuBatchList, contains(
                    allOf(
                            hasPropertyExt("skuId", Matchers.equalTo(skuId)),
                            hasPropertyExt("quantity", Matchers.equalTo(1)),
                            hasPropertyExt("batchNo", Matchers.equalTo(String.format("%s-%s", skuId, 1)))

                    ),
                    allOf(
                            hasPropertyExt("skuId", Matchers.equalTo(skuId)),
                            hasPropertyExt("quantity", Matchers.equalTo(2)),
                            hasPropertyExt("batchNo", Matchers.equalTo(String.format("%s-%s", skuId, 2)))

                    ),
                    allOf(
                            hasPropertyExt("skuId", Matchers.equalTo(skuId)),
                            hasPropertyExt("quantity", Matchers.equalTo(6)),
                            hasPropertyExt("batchNo", Matchers.equalTo(String.format("%s-%s", skuId, 3)))

                    ),
                    allOf(
                            hasPropertyExt("skuId", Matchers.equalTo(skuId2)),
                            hasPropertyExt("quantity", Matchers.equalTo(1)),
                            hasPropertyExt("batchNo", Matchers.equalTo(String.format("%s-%s", skuId, 1)))

                    ),
                    allOf(
                            hasPropertyExt("skuId", Matchers.equalTo(skuId2)),
                            hasPropertyExt("quantity", Matchers.equalTo(1)),
                            hasPropertyExt("batchNo", Matchers.equalTo(String.format("%s-%s", skuId, 2)))

                    ),
                    allOf(
                            hasPropertyExt("skuId", Matchers.equalTo(skuId2)),
                            hasPropertyExt("quantity", Matchers.equalTo(2)),
                            hasPropertyExt("batchNo", Matchers.equalTo(String.format("%s-%s", skuId, 3)))

                    )
            ));
        }
        {
            List<SplitStockoutBoxBatch.BoxSkuBatch> boxSkuBatchList = boxBatchList.get(2).getBoxSkuBatchList();
            Assert.assertEquals(1, boxSkuBatchList.size());

            boxSkuBatchList.sort(Comparator.comparing(SplitStockoutBoxBatch.BoxSkuBatch::getSkuId));

            assertThat(boxSkuBatchList, contains(
                    allOf(
                            hasPropertyExt("skuId", Matchers.equalTo(skuId2)),
                            hasPropertyExt("quantity", Matchers.equalTo(5)),
                            hasPropertyExt("batchNo", Matchers.equalTo(String.format("%s-%s", skuId, 3)))
                    )
            ));
        }
    }
}
