package com.joker.test;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import org.apache.commons.collections4.ComparatorUtils;

import java.util.Comparator;
import java.util.List;

/**
 * 未盘点的放在最上面，其次按照差异值的绝对值 逆序
 * <p>
 * Created by xiangrui on 2018/10/9.
 *
 * @author xiangrui
 * @date 2018/10/9
 */
public class MoreSortTest {


    public static void main(String[] args) {
        List<InventorySku> list = getList();

        List<InventorySku> sort1 = sort(list);
        JSON.toJSONString(sort1);

        list = getList();

        List<InventorySku> sort2 = sort2(list);
        JSON.toJSONString(sort1);
    }

    private static List<InventorySku> sort(List<InventorySku> list) {
        list.sort((o1, o2) -> {
            if (!o1.isInventory || !o2.isInventory) {
                return -1;
            }
            return Integer.compare(Math.abs(o2.gapCount), Math.abs(o1.gapCount));
        });
        return list;
    }

    private static List<InventorySku> sort2(List<InventorySku> list) {
        Comparator<InventorySku> boolean_c = (v1, v2) -> Boolean.compare(v1.isInventory, v2.isInventory);
        Comparator<InventorySku> int_c = (v1, v2) -> Integer.compare(Math.abs(v2.gapCount), Math.abs(v1.gapCount));
        Comparator<InventorySku> comparator = ComparatorUtils.chainedComparator(Lists.newArrayList(boolean_c, int_c));
        list.sort(comparator);
        return list;
    }

    private static List<InventorySku> getList() {
        List<InventorySku> list = Lists.newArrayList();
        {
            InventorySku sku = new InventorySku();
            sku.skuId = 1;
            sku.gapCount = -1;
            sku.isInventory = true;
            list.add(sku);
        }
        {
            InventorySku sku = new InventorySku();
            sku.skuId = 2;
            sku.gapCount = 8;
            sku.isInventory = false;
            list.add(sku);
        }
        {
            InventorySku sku = new InventorySku();
            sku.skuId = 3;
            sku.gapCount = 3;
            sku.isInventory = true;
            list.add(sku);
        }
        {
            InventorySku sku = new InventorySku();
            sku.skuId = 4;
            sku.gapCount = 9;
            sku.isInventory = false;
            list.add(sku);
        }
        return list;
    }

    public static class InventorySku {

        public int skuId;

        public int gapCount;

        public boolean isInventory = false;

        public String name;

        @Override
        public String toString() {
            return JSON.toJSONString(this);
        }
    }
}
