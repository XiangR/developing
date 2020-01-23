package com.yit.test;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.joker.model.TechSaleSkuAdjust;
import org.junit.Test;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

public class ProcessAllSDOrderRes {

    @Test
    public void getCount() {

        int techId = 7498940;

        int shopId = 69262684;

        String str = "[ { \"techId\" : 7596867, \"fenYongId\" : 94239, \"fenYongIdStr\" : \"SDS.1.94239;2\", \"platform\" : 1, \"unifiedOrderId\" : \"157751731262312060980536\", \"sdOrderId\" : \"1262733\", \"skuId\" : \"39745922\", \"spugId\" : \"13274699\", \"adjustQty\" : 1, \"orderShopId\" : 95984330, \"exactShopId\" : 132098468}, { \"techId\" : 7596867, \"fenYongId\" : 94239, \"fenYongIdStr\" : \"SDS.1.94239;2\", \"platform\" : 1, \"unifiedOrderId\" : \"157743369874592060459049\", \"sdOrderId\" : \"1256444\", \"skuId\" : \"39745922\", \"spugId\" : \"13274699\", \"adjustQty\" : 1, \"orderShopId\" : 101246074, \"exactShopId\" : 132098468} ]";

        process(str);

    }

    private void processCurrentShop(String str, int shopId) {

        List<TechSaleSkuAdjust> list = JSON.parseArray(str, TechSaleSkuAdjust.class);

        List<TechSaleSkuAdjust> subtract = list.stream().filter(k -> Objects.equals(k.getOrderShopId(), shopId)).collect(Collectors.toList());

        List<TechSaleSkuAdjust> plus = list.stream().filter(k -> Objects.equals(k.getExactShopId(), shopId)).collect(Collectors.toList());

        System.out.println(String.format("shopId:%s,减:%s,加:%s,共调整:%s", shopId, subtract.size(), plus.size(), (plus.size() - subtract.size())));
    }

    private void process(String str) {

        List<TechSaleSkuAdjust> list = JSON.parseArray(str, TechSaleSkuAdjust.class);

        Map<Integer, List<TechSaleSkuAdjust>> subtractMap = list.stream().collect(Collectors.groupingBy(TechSaleSkuAdjust::getOrderShopId));
        Map<Integer, List<TechSaleSkuAdjust>> plusMap = list.stream().collect(Collectors.groupingBy(TechSaleSkuAdjust::getExactShopId));

        Set<Integer> shopIdList = Sets.newTreeSet();
        shopIdList.addAll(subtractMap.keySet());
        shopIdList.addAll(plusMap.keySet());

        for (Integer shopId : shopIdList) {

            List<TechSaleSkuAdjust> subtract = subtractMap.getOrDefault(shopId, Lists.newArrayList());
            List<TechSaleSkuAdjust> plus = plusMap.getOrDefault(shopId, Lists.newArrayList());

            System.out.println(String.format("shopId:%s,减:%s,加:%s,共调整:%s", shopId, subtract.size(), plus.size(), (plus.size() - subtract.size())));
        }

        Map<Integer, List<TechSaleSkuAdjust>> collect = list.stream().collect(Collectors.groupingBy(TechSaleSkuAdjust::getPlatform));
        for (Map.Entry<Integer, List<TechSaleSkuAdjust>> integerListEntry : collect.entrySet()) {
            System.out.println(String.format("platform:%s,size:%s", integerListEntry.getKey(), integerListEntry.getValue().size()));
        }


    }
}
