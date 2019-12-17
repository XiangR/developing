package com.joker.model;


import java.util.List;

/**
 * Created by xiangrui on 2018/1/16.
 *
 * @author xiangrui
 * @date 2018/1/16
 */
public class BossSupplierBrand {
    public Integer id;

    public Integer brandId;

    public String chineseName;

    public String englishName;

    public String logoUrl;

    public List<SupplierDocument> documentList;




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
