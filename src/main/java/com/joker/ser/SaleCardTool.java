
package com.joker.ser;

import com.alibaba.fastjson.JSON;

import java.io.Serializable;
import java.lang.reflect.Field;

public class SaleCardTool implements Serializable {

    public long technicianId;

    public long dpCheatCount;

    public long mtCheatCount;

    public long dpDisplayCount;

    public long mtDisplayCount;

    public long getTechnicianId() {
        return technicianId;
    }

    public void setTechnicianId(long technicianId) {
        this.technicianId = technicianId;
    }

    public long getDpDisplayCount() {
        return dpDisplayCount - dpCheatCount > 0 ? dpDisplayCount - dpCheatCount : 0;
    }

    public void setDpDisplayCount(long dpDisplayCount) {
        this.dpDisplayCount = dpDisplayCount;
    }

    public long getMtDisplayCount() {
        return mtDisplayCount - mtCheatCount > 0 ? mtDisplayCount - mtCheatCount : 0;
    }

    public long getDpCheatCount() {
        return dpCheatCount;
    }

    public void setDpCheatCount(long dpCheatCount) {
        this.dpCheatCount = dpCheatCount;
    }

    public long getMtCheatCount() {
        return mtCheatCount;
    }

    public void setMtCheatCount(long mtCheatCount) {
        this.mtCheatCount = mtCheatCount;
    }

    public void setMtDisplayCount(long mtDisplayCount) {
        this.mtDisplayCount = mtDisplayCount;
    }

    public static void main(String[] args) {

        String str = "{\n" +
                "\"subjectId\": 130364417,\n" +
                "\"subjectIdType\": 3,\n" +
                "\"extendDims\": {\n" +
                "\"distributionId\": \"SDS.1.4456;2\",\n" +
                "\"saleType\": \"200\",\n" +
                "\"saleProductType\": \"3\"\n" +
                "},\n" +
                "\"dims\": \"DI:SDS.1.4456;2-SA:200-SPA:3\",\n" +
                "\"dpSales\": 198,\n" +
                "\"mtSales\": 5,\n" +
                "\"dpRefundCount\": 19,\n" +
                "\"mtRefundCount\": 0,\n" +
                "\"dpCheatCount\": 4,\n" +
                "\"mtCheatCount\": 0,\n" +
                "\"dpDisplayCount\": 179,\n" +
                "\"mtDisplayCount\": 5\n" +
                "}";
        test(str);
        test2(str);
    }

    private static void test2(String str) {

        SaleCardTool subjectSalesDTO = JSON.parseObject(str, SaleCardTool.class);
        String string = JSON.toJSONString(subjectSalesDTO);
        System.out.println(string);
        SaleCardTool saleCardTool = JSON.parseObject(string, SaleCardTool.class);
        System.out.println(JSON.toJSON(saleCardTool));
    }

    private static void test(String str) {

        try {
            SaleCardTool subjectSalesDTO = JSON.parseObject(str, SaleCardTool.class);
            Field fieldMethod = subjectSalesDTO.getClass().getDeclaredField("dpDisplayCount");
            fieldMethod.setAccessible(true);
            Object fieldValue = fieldMethod.get(subjectSalesDTO);
            System.out.println(fieldValue);

        }
        catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public <T> T deepCopy(T r) {
        Object o = JSON.parseObject(JSON.toJSONString(r), r.getClass());
        return (T) o;
    }

}
