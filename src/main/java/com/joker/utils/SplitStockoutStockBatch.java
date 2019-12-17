package com.joker.utils;

import com.google.common.collect.Lists;
import com.joker.config.CommonConfig;
import com.joker.model.ServiceRuntimeException;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.ComparatorUtils;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 根据出库单明细切分出所需要的库存批次
 * <p>
 * Created by xiangrui on 2019-07-15.
 *
 * @author xiangrui
 * @date 2019-07-15
 */
public class SplitStockoutStockBatch {

    // region var

    private static int warnQty = 5;

    private static String PREFIX = "SplitStockoutStockBatch-Jedis-Lock";

    /**
     * 仓库ID
     */
    private int warehouseId = 0;
    /**
     * 是否加锁
     */
    private boolean warnQtyTryLock = false;

    /**
     * 批次数量足够优先
     */
    private boolean enoughFirst = false;

    /**
     * jedis lock
     */
    private List<JedisLock> jedisLockList = new ArrayList<>();

    /**
     * jedis lock
     */
    private Set<String> jedisKeySet = new HashSet<>();

    /**
     * 库存扣减批次顺序
     */
    private List<SortEnum> sortEnumList = new ArrayList<>();

    /**
     * 最后解析的出库批次结果
     */
    private List<InnerStockoutSkuBatch> innerStockoutSkuBatchList = new ArrayList<>();

    /**
     * 预计出库的出库单SKU
     */
    private List<InnerStockoutSku> innerStockoutSkuList = new ArrayList<>();

    /**
     * SKU 对应的库存批次
     */
    private List<InnerSkuStockBatch> innerSkuStockBatchList = new ArrayList<>();

    /**
     * 最后解析的出库批次结果
     */
    private List<StockoutSkuBatch> stockoutSkuBatchList = new ArrayList<>();

    /**
     * 预计出库的出库单SKU
     */
    private List<StockoutSku> stockoutSkuList = new ArrayList<>();

    /**
     * SKU 对应的库存批次
     */
    private List<SkuStockBatch> skuStockBatchList = new ArrayList<>();

    // endregion

    // region constructor

    public SplitStockoutStockBatch() {
    }

    public SplitStockoutStockBatch(int warehouseId, List<StockoutSku> stockoutSkuList) {
        this.warehouseId = warehouseId;
        this.stockoutSkuList = stockoutSkuList;
        setUpSkuStockBatch();
    }

    public SplitStockoutStockBatch(int warehouseId, List<StockoutSku> stockoutSkuList, List<SkuStockBatch> stockBatchListList) {
        this.warehouseId = warehouseId;
        this.stockoutSkuList = stockoutSkuList;
        this.skuStockBatchList = stockBatchListList;
    }

    // endregion

    // region get and set

    public SplitStockoutStockBatch addSplitSortEnum(SortEnum sortEnum) {
        sortEnumList.add(sortEnum);
        return this;
    }

    public SplitStockoutStockBatch setWarnQtyTryLock(boolean warnQtyTryLock) {
        this.warnQtyTryLock = warnQtyTryLock;
        return this;
    }

    public SplitStockoutStockBatch setEnoughFirst(boolean enoughFirst) {
        this.enoughFirst = enoughFirst;
        return this;
    }

    /**
     * 拿到最终的jedis锁
     */
    public List<JedisLock> getJedisLockList() {
        return jedisLockList;
    }

    public List<StockoutSkuBatch> getStockoutSkuBatchList() {

        // 解析返回值
        setUpStockoutSkuBatch();

        // 返回结果
        return stockoutSkuBatchList;
    }

    // endregion

    // region 核心实现

    /**
     * 切分库存批次
     */
    public void splitBatchSkuStock() {

        try {

            // 检查参数
            checkParam();

            // 设置 inner 类
            setUpInnerObj();

            // 默认设置排列
            setDefaultSortEnum();

            // 开始执行切分
            splitBatchSkuStockAll();

        } catch (Exception t) {

            // 释放所有的锁
            releaseJedisLock();

            // 抛出异常
            throw t;
        }
    }

    /**
     * 按照 externalKey group by，可以同时支持多个外部单据
     */
    private void splitBatchSkuStockAll() {

        // externalKey-> List<InnerStockoutSku>
        Map<String, List<InnerStockoutSku>> externalKey2Map = innerStockoutSkuList.stream()
                .collect(Collectors.groupingBy(k -> k.externalKey, LinkedHashMap::new, Collectors.toList()));

        for (Map.Entry<String, List<InnerStockoutSku>> entry : externalKey2Map.entrySet()) {
            splitBatchSkuStock(entry.getValue());
        }
    }

    /**
     * 切分一个单据的明细
     */
    private void splitBatchSkuStock(List<InnerStockoutSku> innerStockoutSkuList) {

        // supplier-sku-skuType -> List<StockBatch>
        Map<String, List<InnerSkuStockBatch>> batchStockMap = innerSkuStockBatchList.stream()
                .collect(Collectors.groupingBy(InnerSkuStockBatch::getKey));

        // 按照 supplier-sku-skuType 开始切分
        for (InnerStockoutSku innerStockoutSku : innerStockoutSkuList) {
            splitBatchSkuStock(innerStockoutSku, batchStockMap.getOrDefault(innerStockoutSku.getKey(), Lists.newArrayList()));
        }
    }

    /**
     * 切分一个出库单SKU的批次
     *
     * @param innerStockoutSku       出库单SKU
     * @param innerSkuStockBatchList SKU批次库存
     */
    private void splitBatchSkuStock(InnerStockoutSku innerStockoutSku, List<InnerSkuStockBatch> innerSkuStockBatchList) {

        int quantity = innerStockoutSku.quantity;

        // 重新整理
        rebaseExistStockSkuBatch(innerSkuStockBatchList);

        // 检测当前SKU
        if (innerSkuStockBatchList.stream().mapToInt(k -> k.quantity).sum() < quantity) {
            throw new ServiceRuntimeException("SKU 库存扣减不足");
        }

        // 找到第一个足够扣减的批次
        InnerSkuStockBatch enoughBatch = innerSkuStockBatchList.stream()
                .filter(k -> k.quantity >= quantity)
                .findFirst()
                .orElse(null);

        if (enoughFirst && enoughBatch != null) {
            // 一次性扣减的批次
            processEnoughBatch(innerStockoutSku, enoughBatch);
        } else {
            // 依次扣减的批次
            processDeductivelyBatch(innerStockoutSku, innerSkuStockBatchList);
        }
    }

    /**
     * 依次扣减的批次
     */
    private void processDeductivelyBatch(InnerStockoutSku innerStockoutSku, List<InnerSkuStockBatch> innerSkuStockBatchList) {

        int quantity = innerStockoutSku.quantity;
        for (InnerSkuStockBatch stockBatch : innerSkuStockBatchList) {
            if (quantity <= 0) {
                return;
            }
            int thisQty;
            int batchQty = stockBatch.quantity;
            int i = quantity - batchQty;
            // 当前批次不够用
            if (i >= 0) {
                thisQty = batchQty;
            } else {
                // 当前批次够用
                thisQty = quantity;
            }

            // 小于警戒值加锁
            if (batchQty < warnQty) {
                tryLock(join(PREFIX, innerStockoutSku.supplierId, innerStockoutSku.skuId, stockBatch.batchNo));
            }

            // 记录当次切分批次
            innerStockoutSkuBatchList.add(
                    new InnerStockoutSkuBatch(
                            innerStockoutSku.externalKey, stockBatch.supplierId, stockBatch.skuId, min(quantity, batchQty), stockBatch.batchNo, stockBatch.skuType, stockBatch.costPrice
                    )
            );

            // 计算当前待切分数量消耗
            quantity -= thisQty;

            // 计算当次批次消耗
            stockBatch.quantity -= thisQty;
        }
    }

    /**
     * 一次性扣减的批次
     */
    private void processEnoughBatch(InnerStockoutSku innerStockoutSku, InnerSkuStockBatch enoughBatch) {
        // 相等时直接加锁
        if (Objects.equals(enoughBatch.quantity, innerStockoutSku.quantity)) {
            // try lock
            tryLock(join(PREFIX, innerStockoutSku.supplierId, innerStockoutSku.skuId, enoughBatch.batchNo));
        }
        innerStockoutSkuBatchList.add(new InnerStockoutSkuBatch(
                innerStockoutSku.externalKey, innerStockoutSku.supplierId, innerStockoutSku.skuId, innerStockoutSku.quantity, enoughBatch.batchNo, enoughBatch.skuType, enoughBatch.costPrice
        ));

        // 计算当次批次消耗
        enoughBatch.quantity -= innerStockoutSku.quantity;
    }

    /**
     * 设置默认排序
     */
    private void setDefaultSortEnum() {
        if (sortEnumList == null) {
            sortEnumList = Lists.newArrayList();
        }
        if (sortEnumList.isEmpty()) {
            SortEnum weight = SortEnum.WEIGHT;
            sortEnumList.add(weight);
        }
    }

    /**
     * 重新排序
     */
    private void sortSkuStockBatch(List<InnerSkuStockBatch> innerSkuStockBatchList) {
        // 排序
        List<Comparator<InnerSkuStockBatch>> listComparator = Lists.newArrayList();
        for (SortEnum sortEnum : sortEnumList) {
            switch (sortEnum) {
                case WEIGHT:
                    Comparator<InnerSkuStockBatch> weight = Comparator.comparing(k -> k.weight);
                    if (sortEnum.reverse) {
                        weight.reversed();
                    }
                    listComparator.add(weight);
                    break;
                case PRODUCTION_DATE:
                    Comparator<InnerSkuStockBatch> productionDate = Comparator.nullsLast(Comparator.comparing(k -> k.productionDate));
                    if (sortEnum.reverse) {
                        productionDate.reversed();
                    }
                    listComparator.add(productionDate);
                    break;
                case VALID_DATE:
                    Comparator<InnerSkuStockBatch> validDate = Comparator.nullsLast(Comparator.comparing(k -> k.validDate));
                    if (sortEnum.reverse) {
                        validDate.reversed();
                    }
                    listComparator.add(validDate);
                    break;
                default:
                    break;
            }
        }
        Comparator<InnerSkuStockBatch> finalComparator = ComparatorUtils.chainedComparator(listComparator);
        innerSkuStockBatchList.sort(finalComparator);
    }

    /**
     * 释放所有的锁
     */
    public void releaseJedisLock() {
        if (CollectionUtils.isEmpty(jedisLockList)) {
            return;
        }
        // 默认释放所有的锁
        for (JedisLock jedisLock : jedisLockList) {
            jedisLock.release();
        }
    }

    // endregion

    // region set obj

    private void setUpInnerObj() {

        // 调整出库单商品明细
        setUpInnerStockoutSku();

        // 调整库存批次
        setUpInnerSkuStockBatch();
    }

    private void setUpInnerStockoutSku() {

        for (StockoutSku stockoutSku : stockoutSkuList) {
            if (stockoutSku.badQty > 0) {
                innerStockoutSkuList.add(new InnerStockoutSku(
                        stockoutSku.externalKey, stockoutSku.supplierId, stockoutSku.skuId, stockoutSku.badQty, SkuType.BAD
                ));
            }
            if (stockoutSku.goodQty > 0) {
                innerStockoutSkuList.add(new InnerStockoutSku(
                        stockoutSku.externalKey, stockoutSku.supplierId, stockoutSku.skuId, stockoutSku.goodQty, SkuType.GOOD
                ));
            }
            if (stockoutSku.specimenQty > 0) {
                innerStockoutSkuList.add(new InnerStockoutSku(
                        stockoutSku.externalKey, stockoutSku.supplierId, stockoutSku.skuId, stockoutSku.specimenQty, SkuType.SPECIMEN
                ));
            }
        }

    }

    private void setUpInnerSkuStockBatch() {

        for (SkuStockBatch batch : skuStockBatchList) {
            if (batch.badQty > 0) {
                innerSkuStockBatchList.add(new InnerSkuStockBatch(
                        batch.weight, batch.supplierId, batch.skuId, SkuType.BAD, batch.badQty, batch.batchNo, batch.costPrice
                ));
            }
            if (batch.goodQty > 0) {
                innerSkuStockBatchList.add(new InnerSkuStockBatch(
                        batch.weight, batch.supplierId, batch.skuId, SkuType.GOOD, batch.goodQty, batch.batchNo, batch.costPrice
                ));
            }
            if (batch.specimenQty > 0) {
                innerSkuStockBatchList.add(new InnerSkuStockBatch(
                        batch.weight, batch.supplierId, batch.skuId, SkuType.SPECIMEN, batch.specimenQty, batch.batchNo, batch.costPrice
                ));
            }
        }

    }

    private void setUpStockoutSkuBatch() {

        // 已经处理过了，直接返回
        if (CollectionUtils.isNotEmpty(stockoutSkuBatchList)) {
            return;
        }

        // 如果不存在可以处理的数据，直接返回
        if (CollectionUtils.isEmpty(innerStockoutSkuBatchList)) {
            return;
        }

        // group 数据
        Map<String, List<InnerStockoutSkuBatch>> batchMap = innerStockoutSkuBatchList.stream()
                .collect(Collectors.groupingBy(k -> join(k.externalKey, k.supplierId, k.skuId, k.batchNo), LinkedHashMap::new, Collectors.toList()));

        for (Map.Entry<String, List<InnerStockoutSkuBatch>> entry : batchMap.entrySet()) {
            List<InnerStockoutSkuBatch> value = entry.getValue();
            if (CollectionUtils.isEmpty(value)) {
                continue;
            }
            StockoutSkuBatch batch = new StockoutSkuBatch();
            batch.externalKey = value.get(0).externalKey;
            batch.skuId = value.get(0).skuId;
            batch.supplierId = value.get(0).supplierId;
            batch.batchNo = value.get(0).batchNo;
            batch.costPrice = value.get(0).costPrice;
            batch.goodQty = value.stream().filter(k -> Objects.equals(k.skuType, SkuType.GOOD)).mapToInt(k -> k.quantity).sum();
            batch.badQty = value.stream().filter(k -> Objects.equals(k.skuType, SkuType.BAD)).mapToInt(k -> k.quantity).sum();
            batch.specimenQty = value.stream().filter(k -> Objects.equals(k.skuType, SkuType.SPECIMEN)).mapToInt(k -> k.quantity).sum();
            stockoutSkuBatchList.add(batch);
        }

    }

    private void setUpSkuStockBatch() {

        if (CollectionUtils.isEmpty(stockoutSkuList)) {
            this.skuStockBatchList = Lists.newArrayList();
            return;
        }

        skuStockBatchList.addAll(Lists.newArrayList());
    }

    // endregion

    // region private

    private void tryLock(String key) {

        // 警戒值是否加锁
        if (!warnQtyTryLock) {
            return;
        }

        // 单次解析一个SKU只加一次锁
        if (!jedisKeySet.contains(key)) {
            JedisLock jedisLock = JedisLock.tryLock(key);
            jedisKeySet.add(key);
            jedisLockList.add(jedisLock);
        }
    }

    private int min(int v1, int v2) {
        return v1 > v2 ? v2 : v1;
    }

    @SafeVarargs
    private static <T> String join(T... a) {
        if (a == null) {
            return "";
        }
        List<String> collect = Arrays.stream(a).map(Object::toString).collect(Collectors.toList());
        return String.join(CommonConfig.HYPHEN, collect);
    }

    /**
     * 重新整理
     */
    private void rebaseExistStockSkuBatch(List<InnerSkuStockBatch> innerSkuStockBatchList) {
        // 去除等于零的
        innerSkuStockBatchList.removeIf(k -> Objects.equals(k.quantity, 0));
        // 重新排序
        sortSkuStockBatch(innerSkuStockBatchList);
    }

    private void checkParam() {

        Map<String, List<StockoutSku>> externalKey2Map = stockoutSkuList.stream()
                .collect(Collectors.groupingBy(k -> k.externalKey));

        for (Map.Entry<String, List<StockoutSku>> entry : externalKey2Map.entrySet()) {
            List<StockoutSku> value = entry.getValue();
            List<String> repeatElement = DataUtil.getRepeatElement(value, k -> DataUtil.join(k.supplierId, k.skuId));
            if (CollectionUtils.isNotEmpty(repeatElement)) {
                throw new ServiceRuntimeException(
                        String.format("参数异常,同一个单据内货权SKU不允许重复,supplier-sku:%s", DataUtil.join(CommonConfig.COMMA, repeatElement)));
            }
        }
    }

    // endregion

    // region inner class

    static class InnerSkuStockBatch extends BatchInfo {

        public int supplierId;
        public int skuId;
        public SkuType skuType;
        public int quantity;
        public String batchNo;

        public InnerSkuStockBatch() {
        }

        public InnerSkuStockBatch(int weight, int supplierId, int skuId, SkuType skuType, int quantity, String batchNo, BigDecimal costPrice) {
            this.weight = weight;
            this.supplierId = supplierId;
            this.skuId = skuId;
            this.quantity = quantity;
            this.skuType = skuType;
            this.batchNo = batchNo;
            this.costPrice = costPrice;
        }

        public String getKey() {
            return join(supplierId, skuId, skuType);
        }
    }

    public static class SkuStockBatch extends BatchInfo {

        public int supplierId;
        public int skuId;
        public int goodQty;
        public int badQty;
        public int specimenQty;
        public String batchNo;

        public SkuStockBatch() {
        }

        public SkuStockBatch(int weight, int supplierId, int skuId, int goodQty, String batchNo) {
            this.weight = weight;
            this.supplierId = supplierId;
            this.skuId = skuId;
            this.goodQty = goodQty;
            this.batchNo = batchNo;
        }

        public SkuStockBatch(int weight, int skuId, int goodQty, int badQty, int specimenQty, String batchNo) {
            this.weight = weight;
            this.skuId = skuId;
            this.goodQty = goodQty;
            this.badQty = badQty;
            this.specimenQty = specimenQty;
            this.batchNo = batchNo;
        }
    }

    static class InnerStockoutSkuBatch extends BatchInfo {

        public String externalKey;
        public int supplierId;
        public int skuId;
        public int quantity;
        public String batchNo;
        public SkuType skuType;

        public InnerStockoutSkuBatch() {
        }

        public InnerStockoutSkuBatch(String externalKey, int supplierId, int skuId, int quantity, String batchNo, SkuType skuType, BigDecimal costPrice) {
            this.externalKey = externalKey;
            this.supplierId = supplierId;
            this.skuId = skuId;
            this.quantity = quantity;
            this.batchNo = batchNo;
            this.skuType = skuType;
            this.costPrice = costPrice;
        }

        public String getKey() {
            return join(supplierId, skuId, skuType);
        }
    }

    public static class StockoutSkuBatch extends BatchInfo {

        public String externalKey;
        public int supplierId;
        public int skuId;
        public int goodQty;
        public int badQty;
        public int specimenQty;
        public String batchNo;
    }

    static class InnerStockoutSku {

        public String externalKey;
        public int supplierId;
        public int skuId;
        public int quantity;
        public SkuType skuType;

        public InnerStockoutSku() {
        }

        public InnerStockoutSku(String externalKey, int supplierId, int skuId, int quantity, SkuType skuType) {
            this.externalKey = externalKey;
            this.supplierId = supplierId;
            this.skuId = skuId;
            this.quantity = quantity;
            this.skuType = skuType;
        }

        public String getKey() {
            return join(supplierId, skuId, skuType);
        }
    }

    public static class StockoutSku {

        public String externalKey;
        public int supplierId;
        public int skuId;
        public int goodQty;
        public int badQty;
        public int specimenQty;

        public StockoutSku() {
        }

        public StockoutSku(String externalKey, int supplierId, int skuId, int goodQty) {
            this.externalKey = externalKey;
            this.supplierId = supplierId;
            this.skuId = skuId;
            this.goodQty = goodQty;
        }

    }

    public static class BatchInfo {

        public int weight;
        public Date productionDate;
        public Date validDate;
        public BigDecimal costPrice;
    }

    public static class JedisLock {
        public String key;

        public JedisLock(String key) {
            this.key = key;
        }

        public static JedisLock tryLock(String key) {
            return new JedisLock(key);
        }

        public void release() {
            System.out.format("release-%s\n", key);
        }

        public String getKey() {
            return key;
        }

        public void setKey(String key) {
            this.key = key;
        }

    }

    public enum SortEnum {

        /**
         * 序号
         */
        WEIGHT(false),
        /**
         * 生产日期
         */
        PRODUCTION_DATE(false),
        /**
         * 有效期
         */
        VALID_DATE(false);

        public boolean reverse;

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
