package com.joker.price;

import com.google.common.collect.Lists;
import com.joker.model.pricecurve.CurvePriceNode;
import com.joker.model.pricecurve.PriceConfigDTO;
import com.joker.model.pricecurve.PriceSkuRealTimePriceDTO;
import org.apache.commons.collections4.CollectionUtils;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Created by xiangrui on 2017/9/29.
 *
 * @author xiangrui
 * @date 2017/09/29
 */
public class PriceGenerateBizServiceImpl {

    /**
     * 生成无交集的价格区间
     *
     * @param entities
     * @return 价格区间
     */
    public List<PriceSkuRealTimePriceDTO> generateRealTimePriceConfig(List<PriceConfigDTO> entities) {

        if (CollectionUtils.isEmpty(entities)) {
            return Lists.newArrayList();
        }

        CurveService curve = new CurveService();

        curve.generateRealTimePrice(entities.stream().sorted(comparator).collect(Collectors.toList()));

        List<PriceSkuRealTimePriceDTO> result = Lists.newArrayList();

        // 价格变化点生成区间，选取开始类型的点和下一个点组成一个价格区间
        int length = curve.priceNodes.size();
        for (int index = 0; index < length; ++index) {
            CurvePriceNode startNode = curve.priceNodes.get(index);
            PriceConfigDTO priceInfo = (PriceConfigDTO) startNode.getConfig();
            if (Objects.nonNull(priceInfo)) {
                int next = index + 1;
                if (next < length) {
                    CurvePriceNode nextNode = curve.priceNodes.get(next);
                    priceInfo.setEndTime(nextNode.getEffectiveTime());
                }
                result.add(convert(priceInfo));
            }
        }
        return result;
    }

    private PriceSkuRealTimePriceDTO convert(PriceConfigDTO priceInfo) {
        PriceSkuRealTimePriceDTO result = new PriceSkuRealTimePriceDTO();
        result.setPriceSkuConfigId(priceInfo.getId());
        result.setContractId(priceInfo.getContractId());
        result.setSkuId(priceInfo.getSkuId());
        result.setPrice(priceInfo.getPrice());
        result.setTagPrice(priceInfo.getTagPrice());
        result.setStartTime(priceInfo.getStartTime());
        result.setEndTime(priceInfo.getEndTime());
        return result;
    }

    private static Comparator<PriceConfigDTO> comparator = (o1, o2) -> {
        if (Objects.equals(o1.getWeight(), o2.getWeight())) {
            return o1.getCreateTime().compareTo(o2.getCreateTime());
        }
        return o1.getWeight() - o2.getWeight();
    };

}
