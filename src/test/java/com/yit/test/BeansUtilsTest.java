package com.yit.test;

import com.joker.model.BossSupplierBrand;
import com.joker.model.SupplierBrandDetail;
import com.joker.model.SupplierDocument;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

/**
 * 使用BeanUtils 进行对象copy时，其 source 与 target 对应的类都必须要有 get set 方法
 * Created by xiangrui on 2018/1/18.
 *
 * @author xiangrui
 * @date 2018/1/18
 */
public class BeansUtilsTest {


    /**
     * 转换供应商基础信息到boss端
     */
    @Test
    public void test() {

        SupplierBrandDetail brandDetail = new SupplierBrandDetail();
        brandDetail.setBrandId(1);
        brandDetail.setChineseName("相瑞");
        brandDetail.setEnglishName("joker");
        List<SupplierDocument> documentList = new ArrayList<SupplierDocument>() {
            {
                SupplierDocument document = new SupplierDocument();
                document.fileName = "name1";
                document.fileUrl = "url1";
                SupplierDocument document2 = new SupplierDocument();
                document2.fileName = "name1";
                document2.fileUrl = "url1";

                add(document);
                add(document2);
            }
        };
        brandDetail.setDocumentList(documentList);
        BossSupplierBrand brand = convertBossSupplierInfo.apply(brandDetail);

        Assert.assertEquals(brand.brandId, brandDetail.getBrandId());
        Assert.assertEquals(brand.chineseName, brandDetail.getChineseName());
        Assert.assertEquals(brand.englishName, brandDetail.getEnglishName());
        Assert.assertEquals(brand.documentList, brandDetail.getDocumentList());
        Assert.assertEquals(brand.id, brandDetail.getId());
        Assert.assertEquals(brand.logoUrl, brandDetail.getLogoUrl());
    }

    private Function<SupplierBrandDetail, BossSupplierBrand> convertBossSupplierInfo = convert -> {

        BossSupplierBrand supplierBrand = new BossSupplierBrand();

        BeanUtils.copyProperties(convert, supplierBrand);

        return supplierBrand;
    };

}
