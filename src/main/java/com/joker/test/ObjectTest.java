package com.joker.test;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.joker.model.InventoryShelfNoDetail;
import sun.misc.Unsafe;

import java.lang.reflect.Field;
import java.util.List;

/**
 * Created by xiangrui on 2018/7/16.
 *
 * @author xiangrui
 * @date 2018/7/16
 */
public class ObjectTest {


    private static InventoryShelfNoDetail shelfNoDetail = new InventoryShelfNoDetail();

    private static Unsafe unsafe;
    private static Field shelfNoDetailField;

    public static void main(String[] args) throws Exception {
        test0();

        System.gc();

        Thread.sleep(10000);

        System.out.println("Location of shelfNoDetailField: " + unsafe.staticFieldOffset(shelfNoDetailField));

    }


    private static void test0() throws NoSuchFieldException, IllegalAccessException {

        shelfNoDetail.shelfNo = "shelfNo-1";
        shelfNoDetail.quantity = 3;
        unsafe = getUnsafeInstance();

        shelfNoDetailField = ObjectTest.class.getDeclaredField("shelfNoDetail");
        System.out.println("Location of shelfNoDetailField: " + unsafe.staticFieldOffset(shelfNoDetailField));


        shelfNoDetail = null;


        System.out.println(JSON.toJSONString(shelfNoDetail));

        System.out.println("Location of shelfNoDetailField: " + unsafe.staticFieldOffset(shelfNoDetailField));

    }

    private static void test1() {
        List<InventoryShelfNoDetail> shelfNoDetailList = Lists.newArrayList();

        shelfNoDetailList.clear();
        {
            InventoryShelfNoDetail shelfNoDetail = new InventoryShelfNoDetail();
            shelfNoDetail.shelfNo = "shelfNo-1";
            shelfNoDetail.quantity = 3;
            shelfNoDetailList.add(shelfNoDetail);
        }
        {
            InventoryShelfNoDetail shelfNoDetail = new InventoryShelfNoDetail();
            shelfNoDetail.shelfNo = "shelfNo-2";
            shelfNoDetail.quantity = 4;
            shelfNoDetailList.add(shelfNoDetail);
        }
        {
            InventoryShelfNoDetail shelfNoDetail = new InventoryShelfNoDetail();
            shelfNoDetail.shelfNo = "shelfNo-3";
            shelfNoDetail.quantity = 2;
            shelfNoDetailList.add(shelfNoDetail);
        }
    }


    private static Unsafe getUnsafeInstance() throws SecurityException,
            NoSuchFieldException, IllegalArgumentException,
            IllegalAccessException {
        Field theUnsafeInstance = Unsafe.class.getDeclaredField("theUnsafe");
        theUnsafeInstance.setAccessible(true);
        return (Unsafe) theUnsafeInstance.get(Unsafe.class);
    }
}
