package com.joker.model;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import org.apache.commons.collections4.CollectionUtils;

import java.util.List;

public class FitnessCreditSamplePic {

    private int key;

    private List<String> picUrlList;

    private String desc;

    private String creditDetectionUrl;

    public int getKey() {
        return key;
    }

    public void setKey(int key) {
        this.key = key;
    }

    public List<String> getPicUrlList() {
        return picUrlList;
    }

    public void setPicUrlList(List<String> picUrlList) {
        this.picUrlList = picUrlList;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getCreditDetectionUrl() {
        return creditDetectionUrl;
    }

    public void setCreditDetectionUrl(String creditDetectionUrl) {
        this.creditDetectionUrl = creditDetectionUrl;
    }

    public static void print() {
        StringBuilder str = new StringBuilder("List<FitnessCreditSamplePic> joyPicList = Lists.newArrayList();\n");
        for (CreditTypeEnum value : CreditTypeEnum.values()) {
            str.append(String.format(" {\n" +
                    "FitnessCreditSamplePic joyPic = new FitnessCreditSamplePic();\n" +
                    "            joyPic.setKey(%s);\n" +
                    "            joyPic.setDesc(\"%s\");\n" +
                    "            joyPicList.add(joyPic);\n" +
                    "}\n", value.getValue(), value.getDesc()));
        }

        System.out.println(str);
    }

    public static void main(String[] args) {
        test2();
    }

    public static void test2() {
        List<FitnessCreditSamplePic> fitnessCreditSamplePicList = Lists.newArrayList();
        {
            FitnessCreditSamplePic fitnessCreditSamplePic = new FitnessCreditSamplePic();
            fitnessCreditSamplePic.setKey(1);
            fitnessCreditSamplePic.setDesc("国家健身教练职业资格认证");
            fitnessCreditSamplePic.setPicUrlList(Lists.newArrayList("https://p1.meituan.net/scarlett/1176ff9949945c214da374b7c5188d41618095.png"));
            fitnessCreditSamplePic.setCreditDetectionUrl("http://www.sportosta.org.cn/ost/infoSearchAction.do?method=toCertificateSearch");
            fitnessCreditSamplePicList.add(fitnessCreditSamplePic);
        }
        {
            FitnessCreditSamplePic fitnessCreditSamplePic = new FitnessCreditSamplePic();
            fitnessCreditSamplePic.setKey(2);
            fitnessCreditSamplePic.setDesc("国家健身专业人员认证");
            fitnessCreditSamplePic.setPicUrlList(Lists.newArrayList("https://p0.meituan.net/scarlett/f6352dfc4ff877bfaa49175ef9f6ad0d448997.png"));
            fitnessCreditSamplePicList.add(fitnessCreditSamplePic);
        }
        {
            FitnessCreditSamplePic fitnessCreditSamplePic = new FitnessCreditSamplePic();
            fitnessCreditSamplePic.setKey(3);
            fitnessCreditSamplePic.setDesc("CBBA中国健美协会认证");
            fitnessCreditSamplePic.setPicUrlList(Lists.newArrayList("https://p0.meituan.net/scarlett/822bcfed6249e95d88d30053cdd8ae25763751.png", "https://p0.meituan.net/scarlett/fba2565c825dfe589d8948112e2bfb18734820.png"));
            fitnessCreditSamplePic.setCreditDetectionUrl("http://www.chinabba.cn/search.php?cid=54&nid=3");
            fitnessCreditSamplePicList.add(fitnessCreditSamplePic);
        }
        {
            FitnessCreditSamplePic fitnessCreditSamplePic = new FitnessCreditSamplePic();
            fitnessCreditSamplePic.setKey(4);
            fitnessCreditSamplePic.setDesc("AASFP亚洲体适能教练认证");
            fitnessCreditSamplePic.setPicUrlList(Lists.newArrayList("https://p0.meituan.net/scarlett/af42f25524f56a33f166e6ad7e02791c936990.png"));
            fitnessCreditSamplePicList.add(fitnessCreditSamplePic);
        }
        {
            FitnessCreditSamplePic fitnessCreditSamplePic = new FitnessCreditSamplePic();
            fitnessCreditSamplePic.setKey(5);
            fitnessCreditSamplePic.setDesc("ACE美国运动协会认证");
            fitnessCreditSamplePic.setPicUrlList(Lists.newArrayList("https://p0.meituan.net/scarlett/0ecb49a8bf4c5ca7969ff06a5414a2ba225587.png", "https://p0.meituan.net/scarlett/8e3425e0a989fb535a5d28d4f9552b075779617.png"));
            fitnessCreditSamplePicList.add(fitnessCreditSamplePic);
        }
        {
            FitnessCreditSamplePic fitnessCreditSamplePic = new FitnessCreditSamplePic();
            fitnessCreditSamplePic.setKey(6);
            fitnessCreditSamplePic.setDesc("NSCA美国国家体适能协会认证");
            fitnessCreditSamplePic.setPicUrlList(Lists.newArrayList("https://p0.meituan.net/scarlett/8ca4e11cb6d35a92633b81b58f513d4a1583359.png"));
            fitnessCreditSamplePic.setCreditDetectionUrl("http://www.nsca-shanghai.com.cn/net/foreground/zscx2.jsp");
            fitnessCreditSamplePicList.add(fitnessCreditSamplePic);
        }
        {
            FitnessCreditSamplePic fitnessCreditSamplePic = new FitnessCreditSamplePic();
            fitnessCreditSamplePic.setKey(7);
            fitnessCreditSamplePic.setDesc("ACSM美国运动医学会认证");
            fitnessCreditSamplePic.setPicUrlList(Lists.newArrayList("https://p0.meituan.net/scarlett/19d0549cd2150d51c15de415b6867388360881.png"));
            fitnessCreditSamplePicList.add(fitnessCreditSamplePic);
        }
        {
            FitnessCreditSamplePic fitnessCreditSamplePic = new FitnessCreditSamplePic();
            fitnessCreditSamplePic.setKey(8);
            fitnessCreditSamplePic.setDesc("NASM美国国家运动医学会认证");
            fitnessCreditSamplePic.setPicUrlList(Lists.newArrayList("https://p0.meituan.net/scarlett/4f1baa1af2adca5003b2ad27c7ee04d0235804.png"));
            fitnessCreditSamplePicList.add(fitnessCreditSamplePic);
        }
        {
            FitnessCreditSamplePic fitnessCreditSamplePic = new FitnessCreditSamplePic();
            fitnessCreditSamplePic.setKey(9);
            fitnessCreditSamplePic.setDesc("IFBB国际健美联合会认证");
            fitnessCreditSamplePic.setPicUrlList(Lists.newArrayList("https://p0.meituan.net/scarlett/7d3dacc704d5f77be202ad530359678e45022.jpg"));
            fitnessCreditSamplePicList.add(fitnessCreditSamplePic);
        }
        {
            FitnessCreditSamplePic fitnessCreditSamplePic = new FitnessCreditSamplePic();
            fitnessCreditSamplePic.setKey(10);
            fitnessCreditSamplePic.setDesc("IBFA国际身体阻力与体适能训练协会");
            fitnessCreditSamplePic.setPicUrlList(Lists.newArrayList("https://p1.meituan.net/scarlett/50711fc3c210e10bd3065b8ce747418d200388.png"));
            fitnessCreditSamplePicList.add(fitnessCreditSamplePic);
        }
        {
            FitnessCreditSamplePic fitnessCreditSamplePic = new FitnessCreditSamplePic();
            fitnessCreditSamplePic.setKey(12);
            fitnessCreditSamplePic.setDesc("ISSA国际体育科学协会认证");
            fitnessCreditSamplePicList.add(fitnessCreditSamplePic);
        }
        {
            FitnessCreditSamplePic fitnessCreditSamplePic = new FitnessCreditSamplePic();
            fitnessCreditSamplePic.setKey(14);
            fitnessCreditSamplePic.setDesc("AFAA美国有氧体适能协会");
            fitnessCreditSamplePic.setPicUrlList(Lists.newArrayList("https://p0.meituan.net/scarlett/ab9a55ce99b335474e5f0c81abffb9f01609795.png"));
            fitnessCreditSamplePicList.add(fitnessCreditSamplePic);
        }
        {
            FitnessCreditSamplePic fitnessCreditSamplePic = new FitnessCreditSamplePic();
            fitnessCreditSamplePic.setKey(15);
            fitnessCreditSamplePic.setDesc("IFA国际健身协会有氧训练师");
            fitnessCreditSamplePicList.add(fitnessCreditSamplePic);
        }
        {
            FitnessCreditSamplePic fitnessCreditSamplePic = new FitnessCreditSamplePic();
            fitnessCreditSamplePic.setKey(33);
            fitnessCreditSamplePic.setDesc("CrossFit一级教员");
            fitnessCreditSamplePic.setPicUrlList(Lists.newArrayList("https://p1.meituan.net/scarlett/6af2ccdea21e8b1bd7b7e9a485ae14924581125.png"));
            fitnessCreditSamplePic.setCreditDetectionUrl("https://www.crossfit.com/");
            fitnessCreditSamplePicList.add(fitnessCreditSamplePic);
        }
        {
            FitnessCreditSamplePic fitnessCreditSamplePic = new FitnessCreditSamplePic();
            fitnessCreditSamplePic.setKey(34);
            fitnessCreditSamplePic.setDesc("CrossFit二级教员");
            fitnessCreditSamplePic.setPicUrlList(Lists.newArrayList("https://p0.meituan.net/scarlett/1e21af59f03dd2a7b715f4e4f6fe5eea5846159.png"));
            fitnessCreditSamplePic.setCreditDetectionUrl("https://www.crossfit.com/");
            fitnessCreditSamplePicList.add(fitnessCreditSamplePic);
        }
        {
            FitnessCreditSamplePic fitnessCreditSamplePic = new FitnessCreditSamplePic();
            fitnessCreditSamplePic.setKey(35);
            fitnessCreditSamplePic.setDesc("CrossFit三级教员");
            fitnessCreditSamplePicList.add(fitnessCreditSamplePic);
        }
        {
            FitnessCreditSamplePic fitnessCreditSamplePic = new FitnessCreditSamplePic();
            fitnessCreditSamplePic.setKey(36);
            fitnessCreditSamplePic.setDesc("CrossFit四级教员");
            fitnessCreditSamplePicList.add(fitnessCreditSamplePic);
        }
        {
            FitnessCreditSamplePic fitnessCreditSamplePic = new FitnessCreditSamplePic();
            fitnessCreditSamplePic.setKey(37);
            fitnessCreditSamplePic.setDesc("MFT格斗认证");
            fitnessCreditSamplePic.setPicUrlList(Lists.newArrayList("https://p1.meituan.net/scarlett/1e7e985096114049c044ed3c408aa07c217625.png"));
            fitnessCreditSamplePicList.add(fitnessCreditSamplePic);
        }
        System.out.println(fitnessCreditSamplePicList.stream().filter(k -> CollectionUtils.isNotEmpty(k.getPicUrlList())).count());
        System.out.println(JSON.toJSON(fitnessCreditSamplePicList));
    }
}
