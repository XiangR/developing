package com.joker.model;

import com.alibaba.fastjson.JSON;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public class SaleCardTool implements Serializable {
    private static final long serialVersionUID = -8431973540993777442L;

    private long subjectId;

    private long technicianId;

    private int subjectIdType;

    private Map<String, String> extendDims;

    private String dims;

    private long dpSales;

    private long mtSales;

    private long dpRefundCount;

    private long mtRefundCount;

    private long dpCheatCount;

    private long mtCheatCount;

    private long dpDisplayCount;

    private long mtDisplayCount;

    public long getDpSales() {
        return dpSales;
    }

    public void setDpSales(long dpSales) {
        this.dpSales = dpSales;
    }

    public long getMtSales() {
        return mtSales;
    }

    public void setMtSales(long mtSales) {
        this.mtSales = mtSales;
    }

    public long getDpRefundCount() {
        return dpRefundCount;
    }

    public void setDpRefundCount(long dpRefundCount) {
        this.dpRefundCount = dpRefundCount;
    }

    public long getMtRefundCount() {
        return mtRefundCount;
    }

    public void setMtRefundCount(long mtRefundCount) {
        this.mtRefundCount = mtRefundCount;
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

    public long getDpDisplayCount() {
        return dpDisplayCount - dpCheatCount > 0 ? dpDisplayCount - dpCheatCount : 0;
    }

    public void setDpDisplayCount(long dpDisplayCount) {
        this.dpDisplayCount = dpDisplayCount;
    }

    public long getMtDisplayCount() {
        return mtDisplayCount - mtCheatCount > 0 ? mtDisplayCount - mtCheatCount : 0;
    }

    public void setMtDisplayCount(long mtDisplayCount) {
        this.mtDisplayCount = mtDisplayCount;
    }

    public long getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(long subjectId) {
        this.subjectId = subjectId;
    }

    public int getSubjectIdType() {
        return subjectIdType;
    }

    public void setSubjectIdType(int subjectIdType) {
        this.subjectIdType = subjectIdType;
    }

    public Map<String, String> getExtendDims() {
        return extendDims;
    }

    public void setExtendDims(Map<String, String> extendDims) {
        this.extendDims = extendDims;
    }

    public String getDims() {
        return dims;
    }

    public void setDims(String dims) {
        this.dims = dims;
    }

    public long getTechnicianId() {
        return technicianId;
    }

    public void setTechnicianId(long technicianId) {
        this.technicianId = technicianId;
    }

    public static void main(String[] args) {

        String string = "[ { \"subjectId\" : 23093756, \"technicianId\" : 218278, \"subjectIdType\" : 3, \"extendDims\" : { " +
                "\"distributionId\" : \"SDS.1.29918;2\", \"saleType\" : \"200\", \"saleProductType\" : \"3\" }, \"dims\" : \"DI:SDS.1" +
                ".29918;2-SA:200-SPA:3\", \"dpSales\" : 31, \"mtSales\" : 9, \"dpRefundCount\" : 16, \"mtRefundCount\" : 5, " +
                "\"dpCheatCount\" : 0, \"mtCheatCount\" : 0, \"dpDisplayCount\" : 15, \"mtDisplayCount\" : 4}, { \"subjectId\" : " +
                "130509934, \"technicianId\" : 102870, \"subjectIdType\" : 3, \"extendDims\" : { \"distributionId\" : \"SDS.1.1881;2\", " +
                "\"saleType\" : \"200\", \"saleProductType\" : \"3\" }, \"dims\" : \"DI:SDS.1.1881;2-SA:200-SPA:3\", \"dpSales\" : 56, " +
                "\"mtSales\" : 3, \"dpRefundCount\" : 37, \"mtRefundCount\" : 1, \"dpCheatCount\" : 0, \"mtCheatCount\" : 0, " +
                "\"dpDisplayCount\" : 19, \"mtDisplayCount\" : 2}, { \"subjectId\" : 8478818, \"technicianId\" : 1084114, " +
                "\"subjectIdType\" : 3, \"extendDims\" : { \"distributionId\" : \"SDS.1.44270;2\", \"saleType\" : \"200\", " +
                "\"saleProductType\" : \"3\" }, \"dims\" : \"DI:SDS.1.44270;2-SA:200-SPA:3\", \"dpSales\" : 26, \"mtSales\" : 36, " +
                "\"dpRefundCount\" : 18, \"mtRefundCount\" : 20, \"dpCheatCount\" : 0, \"mtCheatCount\" : 0, \"dpDisplayCount\" : 8, " +
                "\"mtDisplayCount\" : 16}, { \"subjectId\" : 131060656, \"technicianId\" : 7004496, \"subjectIdType\" : 3, \"extendDims\"" +
                " : { \"distributionId\" : \"SDS.1.3465;2\", \"saleType\" : \"200\", \"saleProductType\" : \"3\" }, \"dims\" : \"DI:SDS.1" +
                ".3465;2-SA:200-SPA:3\", \"dpSales\" : 0, \"mtSales\" : 3, \"dpRefundCount\" : 0, \"mtRefundCount\" : 2, \"dpCheatCount\"" +
                " : 0, \"mtCheatCount\" : 0, \"dpDisplayCount\" : 0, \"mtDisplayCount\" : 1}, { \"subjectId\" : 93549343, " +
                "\"technicianId\" : 7581609, \"subjectIdType\" : 3, \"extendDims\" : { \"distributionId\" : \"SDS.1.17210;2\", " +
                "\"saleType\" : \"200\", \"saleProductType\" : \"3\" }, \"dims\" : \"DI:SDS.1.17210;2-SA:200-SPA:3\", \"dpSales\" : 171, " +
                "\"mtSales\" : 9, \"dpRefundCount\" : 97, \"mtRefundCount\" : 1, \"dpCheatCount\" : 0, \"mtCheatCount\" : 0, " +
                "\"dpDisplayCount\" : 74, \"mtDisplayCount\" : 8}, { \"subjectId\" : 21664850, \"technicianId\" : 328764, " +
                "\"subjectIdType\" : 3, \"extendDims\" : { \"distributionId\" : \"SDS.1.86949;2\", \"saleType\" : \"200\", " +
                "\"saleProductType\" : \"3\" }, \"dims\" : \"DI:SDS.1.86949;2-SA:200-SPA:3\", \"dpSales\" : 1, \"mtSales\" : 0, " +
                "\"dpRefundCount\" : 0, \"mtRefundCount\" : 0, \"dpCheatCount\" : 0, \"mtCheatCount\" : 0, \"dpDisplayCount\" : 1, " +
                "\"mtDisplayCount\" : 0} ]";

        List<SaleCardTool> saleCardTools = JSON.parseArray(string, SaleCardTool.class);

        String prefix = ", ";
        for (SaleCardTool saleCardTool : saleCardTools) {
            StringBuilder append = new StringBuilder()
                    .append("手艺人ID:")
                    .append(saleCardTool.getTechnicianId())
                    .append(prefix)
                    .append("shopId:")
                    .append(saleCardTool.getTechnicianId())
                    .append(prefix)
                    .append("点评销量:")
                    .append(saleCardTool.getDpSales())
                    .append(prefix)
                    .append("美团销量:")
                    .append(saleCardTool.getMtSales())
                    .append(prefix)
                    .append("点评退款:")
                    .append(saleCardTool.getDpRefundCount())
                    .append(prefix)
                    .append("美团退款:")
                    .append(saleCardTool.getMtRefundCount())
                    .append(prefix)
                    .append("点评刷单:")
                    .append(saleCardTool.getDpCheatCount())
                    .append(prefix)
                    .append("美团刷单:")
                    .append(saleCardTool.getMtCheatCount());
            System.out.println(append.toString());
        }
    }
}
