package com.joker.utils;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.joker.model.ServiceRuntimeException;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.ComparatorUtils;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by xiangrui on 2019-07-15.
 *
 * @author xiangrui
 * @date 2019-07-15
 */
public class SplitStockoutBoxBatch {

    private List<SortEnum> sortEnumList = new ArrayList<>();

    private List<StockoutSkuBatch> originalStockoutSkuBatchList = new ArrayList<>();

    private Map<Integer, List<StockoutSkuBatch>> existStockoutSkuBatchMap = new HashMap<>();

    private List<Box> boxList = new ArrayList<>();

    private List<BoxBatch> boxBatchList = new ArrayList<>();

    public List<BoxBatch> getBoxBatchList() {
        return boxBatchList;
    }

    public SplitStockoutBoxBatch() {
    }

    public SplitStockoutBoxBatch(List<StockoutSkuBatch> stockoutSkuBatchList, List<Box> boxList) {
        this.originalStockoutSkuBatchList = stockoutSkuBatchList;
        this.boxList = boxList;
        this.existStockoutSkuBatchMap = stockoutSkuBatchList.stream().collect(Collectors.groupingBy(StockoutSkuBatch::getSkuId));
    }

    public SplitStockoutBoxBatch addSplitSortEnum(SortEnum sortEnum) {
        sortEnumList.add(sortEnum);
        return this;
    }

    /**
     * 处理全部箱子
     */
    public void splitAllBoxBatch() {

        // 设置默认扣减批次顺序
        setDefaultSortEnum();

        // 切分装箱批次
        for (Box box : boxList) {
            splitBoxBatch(box);
        }
    }

    /**
     * 处理单个箱子
     *
     * @param box 箱子
     */
    private void splitBoxBatch(Box box) {

        BoxBatch boxBatch = new BoxBatch(box.getBoxNo(), box.getBoxSkuList(), Lists.newArrayList());
        boxBatchList.add(boxBatch);

        List<BoxSku> boxSkuList = box.boxSkuList;
        for (BoxSku boxSku : boxSkuList) {
            List<StockoutSkuBatch> existStockoutSkuBatchList = existStockoutSkuBatchMap.getOrDefault(boxSku.skuId, Lists.newArrayList());
            processDeductivelyBatch(boxSku, existStockoutSkuBatchList, boxBatch.boxSkuBatchList);
        }
    }

    /**
     * 依次扣减的批次
     */
    private void processDeductivelyBatch(BoxSku boxSku, List<StockoutSkuBatch> existStockoutSkuBatchList, List<BoxSkuBatch> boxSkuBatchList) {
        rebaseExistStockoutSkuBatch(existStockoutSkuBatchList);

        int quantity = boxSku.getQuantity();
        if (existStockoutSkuBatchList.stream().mapToInt(StockoutSkuBatch::getQuantity).sum() < quantity) {
            throw new ServiceRuntimeException("SKU 库存扣减不足");
        }

        for (StockoutSkuBatch stockoutSkuBatch : existStockoutSkuBatchList) {
            if (quantity <= 0) {
                return;
            }
            int thisQty;
            int batchQty = stockoutSkuBatch.getQuantity();
            int i = quantity - batchQty;
            // 当前批次不够用
            if (i >= 0) {
                thisQty = batchQty;
            } else {
                // 当前批次够用
                thisQty = quantity;
            }
            quantity -= thisQty;

            // 计算当次批次消耗
            stockoutSkuBatch.setQuantity(stockoutSkuBatch.getQuantity() - thisQty);
            // 记录当次装箱批次
            boxSkuBatchList.add(new BoxSkuBatch(boxSku.getSkuId(), thisQty, stockoutSkuBatch.getBatchNo()));
        }

    }

    /**
     * 重新整理
     */
    private void rebaseExistStockoutSkuBatch(List<StockoutSkuBatch> existStockoutSkuBatchList) {
        // 去除等于零的
        existStockoutSkuBatchList.removeIf(k -> Objects.equals(k.getQuantity(), 0));
        // 重新排序
        sortStockoutSkuBatch(existStockoutSkuBatchList);
    }

    /**
     * 设置默认排序
     */
    private void setDefaultSortEnum() {
        if (sortEnumList == null) {
            sortEnumList = Lists.newArrayList();
        }
        if (sortEnumList.isEmpty()) {
            SortEnum sequence = SortEnum.SEQUENCE;
            sortEnumList.add(sequence);
        }
    }

    /**
     * 重新整理
     */
    private void sortStockoutSkuBatch(List<StockoutSkuBatch> skuStockBatchList) {
        if (CollectionUtils.isEmpty(skuStockBatchList)) {
            return;
        }
        // 排序
        List<Comparator<StockoutSkuBatch>> list_comparator = Lists.newArrayList();
        for (SortEnum sortEnum : sortEnumList) {
            switch (sortEnum) {
                case SEQUENCE:
                    Comparator<StockoutSkuBatch> sequence = Comparator.comparing(StockoutSkuBatch::getSequence);
                    if (sortEnum.reverse) {
                        sequence.reversed();
                    }
                    list_comparator.add(sequence);
                    break;
                case PRODUCTION_DATE:
                    Comparator<StockoutSkuBatch> productionDate = Comparator.nullsLast(Comparator.comparing(StockoutSkuBatch::getProductionDate));
                    if (sortEnum.reverse) {
                        productionDate.reversed();
                    }
                    list_comparator.add(productionDate);
                    break;
                case VALID_DATE:
                    Comparator<StockoutSkuBatch> validDate = Comparator.nullsLast(Comparator.comparing(StockoutSkuBatch::getValidDate));
                    if (sortEnum.reverse) {
                        validDate.reversed();
                    }
                    list_comparator.add(validDate);
                    break;
                default:
                    break;
            }
        }
        Comparator<StockoutSkuBatch> final_comparator = ComparatorUtils.chainedComparator(list_comparator);
        skuStockBatchList.sort(final_comparator);
    }

    // region inner class

    public static class Box {
        private String boxNo;
        private List<BoxSku> boxSkuList;

        public Box(String boxNo, List<BoxSku> boxSkuList) {
            this.boxNo = boxNo;
            this.boxSkuList = boxSkuList;
        }

        public String getBoxNo() {
            return boxNo;
        }

        public void setBoxNo(String boxNo) {
            this.boxNo = boxNo;
        }

        public List<BoxSku> getBoxSkuList() {
            return boxSkuList;
        }

        public void setBoxSkuList(List<BoxSku> boxSkuList) {
            this.boxSkuList = boxSkuList;
        }

        @Override
        public String toString() {
            return JSON.toJSONString(this);
        }
    }

    public static class BoxSku {

        private int skuId;
        private int supplierId;
        private int quantity;

        public BoxSku(int skuId, int quantity) {
            this.skuId = skuId;
            this.quantity = quantity;
        }

        public int getSkuId() {
            return skuId;
        }

        public void setSkuId(int skuId) {
            this.skuId = skuId;
        }

        public int getQuantity() {
            return quantity;
        }

        public void setQuantity(int quantity) {
            this.quantity = quantity;
        }

        @Override
        public String toString() {
            return JSON.toJSONString(this);
        }
    }

    public static class BoxBatch {

        private String boxNo;
        private List<BoxSku> boxSkuList;
        private List<BoxSkuBatch> boxSkuBatchList;

        public BoxBatch(String boxNo, List<BoxSku> boxSkuList, List<BoxSkuBatch> boxSkuBatchList) {
            this.boxNo = boxNo;
            this.boxSkuList = boxSkuList;
            this.boxSkuBatchList = boxSkuBatchList;
        }

        public String getBoxNo() {
            return boxNo;
        }

        public void setBoxNo(String boxNo) {
            this.boxNo = boxNo;
        }

        public List<BoxSku> getBoxSkuList() {
            return boxSkuList;
        }

        public void setBoxSkuList(List<BoxSku> boxSkuList) {
            this.boxSkuList = boxSkuList;
        }

        public List<BoxSkuBatch> getBoxSkuBatchList() {
            return boxSkuBatchList;
        }

        public void setBoxSkuBatchList(List<BoxSkuBatch> boxSkuBatchList) {
            this.boxSkuBatchList = boxSkuBatchList;
        }

        @Override
        public String toString() {
            return JSON.toJSONString(this);
        }
    }

    public static class BoxSkuBatch {

        private int skuId;
        private int quantity;
        private String batchNo;

        public BoxSkuBatch(int skuId, int quantity, String batchNo) {
            this.skuId = skuId;
            this.quantity = quantity;
            this.batchNo = batchNo;
        }

        public int getSkuId() {
            return skuId;
        }

        public void setSkuId(int skuId) {
            this.skuId = skuId;
        }

        public int getQuantity() {
            return quantity;
        }

        public void setQuantity(int quantity) {
            this.quantity = quantity;
        }

        public String getBatchNo() {
            return batchNo;
        }

        public void setBatchNo(String batchNo) {
            this.batchNo = batchNo;
        }

        @Override
        public String toString() {
            return JSON.toJSONString(this);
        }
    }

    public static class StockoutSkuBatch {
        private Date productionDate;
        private Date validDate;
        private int skuId;
        private int quantity;
        private String batchNo;
        private int sequence;

        public StockoutSkuBatch(int skuId, int quantity, String batchNo, int sequence) {
            this.skuId = skuId;
            this.quantity = quantity;
            this.batchNo = batchNo;
            this.sequence = sequence;
        }

        public int getSkuId() {
            return skuId;
        }

        public void setSkuId(int skuId) {
            this.skuId = skuId;
        }

        public int getQuantity() {
            return quantity;
        }

        public void setQuantity(int quantity) {
            this.quantity = quantity;
        }

        public String getBatchNo() {
            return batchNo;
        }

        public void setBatchNo(String batchNo) {
            this.batchNo = batchNo;
        }

        public int getSequence() {
            return sequence;
        }

        public void setSequence(int sequence) {
            this.sequence = sequence;
        }

        public Date getProductionDate() {
            return productionDate;
        }

        public void setProductionDate(Date productionDate) {
            this.productionDate = productionDate;
        }

        public Date getValidDate() {
            return validDate;
        }

        public void setValidDate(Date validDate) {
            this.validDate = validDate;
        }

        @Override
        public String toString() {
            return JSON.toJSONString(this);
        }
    }

    public enum SortEnum {

        /**
         * 序号
         */
        SEQUENCE(false),
        /**
         * 生产日期
         */
        PRODUCTION_DATE(false),
        /**
         * 有效期
         */
        VALID_DATE(false);

        private boolean reverse;

        SortEnum(boolean reverse) {
            this.reverse = reverse;
        }

        public void setReverse(boolean reverse) {
            this.reverse = reverse;
        }
    }

    // endregion
}
