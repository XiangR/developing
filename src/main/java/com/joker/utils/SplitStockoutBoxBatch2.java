package com.joker.utils;

import com.alibaba.fastjson.JSON;
import com.google.common.base.Joiner;
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
public class SplitStockoutBoxBatch2 {

    private static final Joiner HYPHEN_JOIN = Joiner.on(CommonConfig.HYPHEN);

    private List<InnerBox> innerBoxList = new ArrayList<>();
    private List<InnerBoxBatch> innerBoxBatchList = new ArrayList<>();
    private List<InnerStockoutSkuBatch> innerStockoutSkuBatchList = new ArrayList<>();
    private Map<String, List<InnerStockoutSkuBatch>> existStockoutSkuBatchMap = new HashMap<>();

    /**
     * 排序
     */
    private List<SortEnum> sortEnumList = new ArrayList<>();

    /**
     * 参数-出库批次
     */
    private List<StockoutSkuBatch> stockoutSkuBatchList = new ArrayList<>();

    /**
     * 参数-箱子
     */
    private List<Box> boxList = new ArrayList<>();

    /**
     * 解析结果-每个箱子对应的批次信息
     */
    private List<BoxBatch> boxBatchList = new ArrayList<>();

    public SplitStockoutBoxBatch2() {
    }

    public SplitStockoutBoxBatch2(List<StockoutSkuBatch> stockoutSkuBatchList, List<Box> innerBoxList) {
        this.boxList = innerBoxList;
        this.stockoutSkuBatchList = stockoutSkuBatchList;
    }

    public SplitStockoutBoxBatch2 addSplitSortEnum(SortEnum sortEnum) {
        sortEnumList.add(sortEnum);
        return this;
    }


    public List<BoxBatch> getBoxBatchList() {
        setUpBoxBatch();
        return boxBatchList;
    }

    /**
     * 处理全部箱子
     */
    public void splitAllBoxBatch() {

        // 设置 inner 类
        setUpInnerObj();

        // 默认设置排列
        setDefaultSortEnum();

        // 切分装箱批次
        for (InnerBox innerBox : innerBoxList) {
            splitBoxBatch(innerBox);
        }
    }

    private void setUpInnerObj() {

        setUpInnerStockoutSku();
        setUpInnerBox();
    }

    private void setUpInnerBox() {
        for (Box box : boxList) {
            List<InnerBoxSku> thisInnerBoxList = Lists.newArrayList();
            InnerBox innerBox = new InnerBox(box.boxNo, thisInnerBoxList);
            for (BoxSku boxSku : box.boxSkuList) {
                if (boxSku.goodQty > 0) {
                    thisInnerBoxList.add(new InnerBoxSku(boxSku.supplierId, boxSku.skuId, boxSku.goodQty, SkuType.GOOD));
                }
                if (boxSku.badQty > 0) {
                    thisInnerBoxList.add(new InnerBoxSku(boxSku.supplierId, boxSku.skuId, boxSku.badQty, SkuType.BAD));
                }
                if (boxSku.specimenQty > 0) {
                    thisInnerBoxList.add(new InnerBoxSku(boxSku.supplierId, boxSku.skuId, boxSku.specimenQty, SkuType.SPECIMEN));
                }
            }
            innerBoxList.add(innerBox);
        }
    }

    private void setUpInnerStockoutSku() {

        for (StockoutSkuBatch stockoutSku : stockoutSkuBatchList) {
            if (stockoutSku.goodQty > 0) {
                innerStockoutSkuBatchList.add(new InnerStockoutSkuBatch(stockoutSku.supplierId, stockoutSku.skuId, stockoutSku.goodQty, SkuType.GOOD, stockoutSku.batchNo, stockoutSku.sequence));
            }
            if (stockoutSku.badQty > 0) {
                innerStockoutSkuBatchList.add(new InnerStockoutSkuBatch(stockoutSku.supplierId, stockoutSku.skuId, stockoutSku.badQty, SkuType.BAD, stockoutSku.batchNo, stockoutSku.sequence));
            }
            if (stockoutSku.specimenQty > 0) {
                innerStockoutSkuBatchList.add(new InnerStockoutSkuBatch(stockoutSku.supplierId, stockoutSku.skuId, stockoutSku.specimenQty, SkuType.SPECIMEN, stockoutSku.batchNo, stockoutSku.sequence));
            }
        }
        // 初始化 map
        existStockoutSkuBatchMap = innerStockoutSkuBatchList.stream().collect(Collectors.groupingBy(InnerStockoutSkuBatch::getKey));
    }

    private void setUpBoxBatch() {

        // 已经处理过了，直接返回
        if (CollectionUtils.isNotEmpty(boxBatchList)) {
            return;
        }
        // 如果不存在可以处理的数据，直接返回
        if (CollectionUtils.isEmpty(innerBoxBatchList)) {
            return;
        }

        for (InnerBoxBatch innerBoxBatch : innerBoxBatchList) {
            BoxBatch boxBatch = new BoxBatch(innerBoxBatch.boxNo, Lists.newArrayList());
            boxBatchList.add(boxBatch);

            // group 数据
            Map<String, List<InnerBoxSkuBatch>> batchMap = innerBoxBatch.innerBoxSkuBatchList.stream()
                    .collect(Collectors.groupingBy(k -> join(k.batchNo, k.supplierId, k.skuId), LinkedHashMap::new, Collectors.toList()));

            for (Map.Entry<String, List<InnerBoxSkuBatch>> entry : batchMap.entrySet()) {
                List<InnerBoxSkuBatch> value = entry.getValue();
                if (CollectionUtils.isEmpty(value)) {
                    continue;
                }
                BoxSkuBatch batch = new BoxSkuBatch();
                batch.supplierId = value.get(0).supplierId;
                batch.skuId = value.get(0).skuId;
                batch.batchNo = value.get(0).batchNo;
                batch.badQty = value.stream().filter(k -> Objects.equals(k.skuType, SkuType.BAD)).mapToInt(k -> k.quantity).sum();
                batch.goodQty = value.stream().filter(k -> Objects.equals(k.skuType, SkuType.GOOD)).mapToInt(k -> k.quantity).sum();
                batch.specimenQty = value.stream().filter(k -> Objects.equals(k.skuType, SkuType.SPECIMEN)).mapToInt(k -> k.quantity).sum();
                boxBatch.boxSkuBatchList.add(batch);
            }

        }
    }

    /**
     * 处理单个箱子
     *
     * @param innerBox 箱子
     */
    private void splitBoxBatch(InnerBox innerBox) {

        InnerBoxBatch innerBoxBatch = new InnerBoxBatch(innerBox.boxNo, Lists.newArrayList());
        innerBoxBatchList.add(innerBoxBatch);

        List<InnerBoxSku> innerBoxSkuList = innerBox.innerBoxSkuList;
        for (InnerBoxSku innerBoxSku : innerBoxSkuList) {
            List<InnerStockoutSkuBatch> existInnerStockoutSkuBatchList = existStockoutSkuBatchMap.getOrDefault(innerBoxSku.getKey(), Lists.newArrayList());
            processDeductivelyBatch(innerBoxSku, existInnerStockoutSkuBatchList, innerBoxBatch.innerBoxSkuBatchList);
        }
    }

    /**
     * 依次扣减的批次
     */
    private void processDeductivelyBatch(InnerBoxSku innerBoxSku, List<InnerStockoutSkuBatch> existInnerStockoutSkuBatchList, List<InnerBoxSkuBatch> innerBoxSkuBatchList) {
        rebaseExistStockoutSkuBatch(existInnerStockoutSkuBatchList);

        int quantity = innerBoxSku.quantity;
        if (existInnerStockoutSkuBatchList.stream().mapToInt(k -> k.quantity).sum() < quantity) {
            throw new ServiceRuntimeException("SKU 库存扣减不足");
        }

        for (InnerStockoutSkuBatch innerStockoutSkuBatch : existInnerStockoutSkuBatchList) {
            if (quantity <= 0) {
                return;
            }
            int thisQty;
            int batchQty = innerStockoutSkuBatch.quantity;
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
            innerStockoutSkuBatch.quantity -= thisQty;
            // 记录当次装箱批次
            innerBoxSkuBatchList.add(new InnerBoxSkuBatch(innerBoxSku.supplierId, innerBoxSku.skuId, thisQty, innerBoxSku.skuType, innerStockoutSkuBatch.batchNo));
        }

    }

    /**
     * 重新整理
     */
    private void rebaseExistStockoutSkuBatch(List<InnerStockoutSkuBatch> existInnerStockoutSkuBatchList) {
        // 去除等于零的
        existInnerStockoutSkuBatchList.removeIf(k -> Objects.equals(k.quantity, 0));
        // 重新排序
        sortStockoutSkuBatch(existInnerStockoutSkuBatchList);
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
    private void sortStockoutSkuBatch(List<InnerStockoutSkuBatch> skuStockBatchList) {
        if (CollectionUtils.isEmpty(skuStockBatchList)) {
            return;
        }
        // 排序
        List<Comparator<InnerStockoutSkuBatch>> listComparator = Lists.newArrayList();
        for (SortEnum sortEnum : sortEnumList) {
            switch (sortEnum) {
                case SEQUENCE:
                    Comparator<InnerStockoutSkuBatch> sequence = Comparator.comparing(k -> k.sequence);
                    if (sortEnum.reverse) {
                        sequence.reversed();
                    }
                    listComparator.add(sequence);
                    break;
                case PRODUCTION_DATE:
                    Comparator<InnerStockoutSkuBatch> productionDate = Comparator.nullsLast(Comparator.comparing(k -> k.productionDate));
                    if (sortEnum.reverse) {
                        productionDate.reversed();
                    }
                    listComparator.add(productionDate);
                    break;
                case VALID_DATE:
                    Comparator<InnerStockoutSkuBatch> validDate = Comparator.nullsLast(Comparator.comparing(k -> k.validDate));
                    if (sortEnum.reverse) {
                        validDate.reversed();
                    }
                    listComparator.add(validDate);
                    break;
                default:
                    break;
            }
        }
        Comparator<InnerStockoutSkuBatch> finalComparator = ComparatorUtils.chainedComparator(listComparator);
        skuStockBatchList.sort(finalComparator);
    }

    @SafeVarargs
    public static <T> String join(T... a) {
        if (a == null) {
            return "";
        }
        return HYPHEN_JOIN.join(a);
    }

    // region inner class

    static class InnerBox {
        public String boxNo;
        public List<InnerBoxSku> innerBoxSkuList;

        public InnerBox(String boxNo, List<InnerBoxSku> innerBoxSkuList) {
            this.boxNo = boxNo;
            this.innerBoxSkuList = innerBoxSkuList;
        }

        @Override
        public String toString() {
            return JSON.toJSONString(this);
        }
    }

    public static class Box {
        public String boxNo;
        public List<BoxSku> boxSkuList;

        public Box(String boxNo, List<BoxSku> boxSkuList) {
            this.boxNo = boxNo;
            this.boxSkuList = boxSkuList;
        }

        @Override
        public String toString() {
            return JSON.toJSONString(this);
        }
    }

    public static class InnerBoxSku {

        public int supplierId;
        public int skuId;
        public int quantity;
        public SkuType skuType;

        public InnerBoxSku(int supplierId, int skuId, int quantity, SkuType skuType) {
            this.supplierId = supplierId;
            this.skuId = skuId;
            this.quantity = quantity;
            this.skuType = skuType;
        }

        private String getKey() {
            return join(supplierId, skuId, skuType);
        }

        @Override
        public String toString() {
            return JSON.toJSONString(this);
        }
    }

    public static class BoxSku {

        public int supplierId;
        public int skuId;
        public int goodQty;
        public int badQty;
        public int specimenQty;

        public BoxSku(int supplierId, int skuId, int goodQty) {
            this.supplierId = supplierId;
            this.skuId = skuId;
            this.goodQty = goodQty;
        }

        @Override
        public String toString() {
            return JSON.toJSONString(this);
        }
    }

    static class InnerBoxBatch {

        public String boxNo;
        public List<InnerBoxSkuBatch> innerBoxSkuBatchList;

        public InnerBoxBatch(String boxNo, List<InnerBoxSkuBatch> innerBoxSkuBatchList) {
            this.boxNo = boxNo;
            this.innerBoxSkuBatchList = innerBoxSkuBatchList;
        }

        @Override
        public String toString() {
            return JSON.toJSONString(this);
        }
    }

    public static class BoxBatch {

        public String boxNo;
        public List<BoxSkuBatch> boxSkuBatchList;

        public BoxBatch(String boxNo, List<BoxSkuBatch> boxSkuBatchList) {
            this.boxNo = boxNo;
            this.boxSkuBatchList = boxSkuBatchList;
        }

        @Override
        public String toString() {
            return JSON.toJSONString(this);
        }
    }

    public static class InnerBoxSkuBatch {

        public int supplierId;
        public int skuId;
        public int quantity;
        public SkuType skuType;
        public String batchNo;

        public InnerBoxSkuBatch(int supplierId, int skuId, int quantity, SkuType skuType, String batchNo) {
            this.supplierId = supplierId;
            this.skuId = skuId;
            this.quantity = quantity;
            this.skuType = skuType;
            this.batchNo = batchNo;
        }

        @Override
        public String toString() {
            return JSON.toJSONString(this);
        }
    }

    public static class BoxSkuBatch {

        public int supplierId;
        public int skuId;
        public int goodQty;
        public int badQty;
        public int specimenQty;
        public String batchNo;

        @Override
        public String toString() {
            return JSON.toJSONString(this);
        }
    }

    static class InnerStockoutSkuBatch {
        public Date productionDate;
        public Date validDate;
        public int supplierId;
        public int skuId;
        public int quantity;
        public String batchNo;
        public int sequence;
        public SkuType skuType;

        public InnerStockoutSkuBatch(int supplierId, int skuId, int quantity, SkuType skuType, String batchNo, int sequence) {
            this.supplierId = supplierId;
            this.skuId = skuId;
            this.quantity = quantity;
            this.skuType = skuType;
            this.batchNo = batchNo;
            this.sequence = sequence;
        }

        private String getKey() {
            return join(supplierId, skuId, skuType);
        }

        @Override
        public String toString() {
            return JSON.toJSONString(this);
        }
    }

    public static class StockoutSkuBatch {
        public int supplierId;
        public int skuId;
        public int goodQty;
        public int badQty;
        public int specimenQty;
        public String batchNo;
        private Date productionDate;
        private Date validDate;
        private int sequence;

        public StockoutSkuBatch(int supplierId, int skuId, int quantity, String batchNo, int sequence) {
            this.supplierId = supplierId;
            this.skuId = skuId;
            this.goodQty = quantity;
            this.batchNo = batchNo;
            this.sequence = sequence;
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

    public enum SkuType {
        /**
         * 好品
         */
        GOOD,
        /**
         * 坏品
         */
        BAD,
        /**
         * 样品
         */
        SPECIMEN,
        ;
    }

    // endregion
}
