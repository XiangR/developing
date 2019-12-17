package com.joker.model;

import java.util.List;

/**
 * Created by xiangrui on 2018/1/17.
 *
 * @author xiangrui
 * @date 2018/1/17
 */
public class SupplierBrandDetail {

    /**
     * id
     */
    private Integer id;
    /**
     * 品牌库id
     */
    private Integer brandId;
    /**
     * 中文名称
     */
    private String chineseName;
    /**
     * 英文名称
     */
    private String englishName;
    /**
     * 品牌logo
     */
    private String logoUrl;
    /**
     * 供应商品牌文档资料
     */
    private List<SupplierDocument> documentList;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getBrandId() {
        return brandId;
    }

    public void setBrandId(Integer brandId) {
        this.brandId = brandId;
    }

    public String getChineseName() {
        return chineseName;
    }

    public void setChineseName(String chineseName) {
        this.chineseName = chineseName;
    }

    public String getEnglishName() {
        return englishName;
    }

    public void setEnglishName(String englishName) {
        this.englishName = englishName;
    }

    public String getLogoUrl() {
        return logoUrl;
    }

    public void setLogoUrl(String logoUrl) {
        this.logoUrl = logoUrl;
    }

    public List<SupplierDocument> getDocumentList() {
        return documentList;
    }

    public void setDocumentList(List<SupplierDocument> documentList) {
        this.documentList = documentList;
    }
}
