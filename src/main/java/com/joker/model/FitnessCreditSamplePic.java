package com.joker.model;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;

import java.util.List;

public class FitnessCreditSamplePic {

    private int key;

    private String picUrl;

    private String desc;

    public int getKey() {
        return key;
    }

    public void setKey(int key) {
        this.key = key;
    }

    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
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
            fitnessCreditSamplePicList.add(fitnessCreditSamplePic);
        }
        {
            FitnessCreditSamplePic fitnessCreditSamplePic = new FitnessCreditSamplePic();
            fitnessCreditSamplePic.setKey(2);
            fitnessCreditSamplePic.setDesc("国家健身专业人员认证");
            fitnessCreditSamplePicList.add(fitnessCreditSamplePic);
        }
        {
            FitnessCreditSamplePic fitnessCreditSamplePic = new FitnessCreditSamplePic();
            fitnessCreditSamplePic.setKey(3);
            fitnessCreditSamplePic.setDesc("CBBA中国健美协会认证");
            fitnessCreditSamplePicList.add(fitnessCreditSamplePic);
        }
        {
            FitnessCreditSamplePic fitnessCreditSamplePic = new FitnessCreditSamplePic();
            fitnessCreditSamplePic.setKey(4);
            fitnessCreditSamplePic.setDesc("AASFP亚洲体适能教练认证");
            fitnessCreditSamplePicList.add(fitnessCreditSamplePic);
        }
        {
            FitnessCreditSamplePic fitnessCreditSamplePic = new FitnessCreditSamplePic();
            fitnessCreditSamplePic.setKey(5);
            fitnessCreditSamplePic.setDesc("ACE美国运动协会认证");
            fitnessCreditSamplePicList.add(fitnessCreditSamplePic);
        }
        {
            FitnessCreditSamplePic fitnessCreditSamplePic = new FitnessCreditSamplePic();
            fitnessCreditSamplePic.setKey(6);
            fitnessCreditSamplePic.setDesc("NSCA美国国家体适能协会认证");
            fitnessCreditSamplePic.setPicUrl("http://p0.meituan.net/scarlett/8ca4e11cb6d35a92633b81b58f513d4a1583359.png");
            fitnessCreditSamplePicList.add(fitnessCreditSamplePic);
        }
        {
            FitnessCreditSamplePic fitnessCreditSamplePic = new FitnessCreditSamplePic();
            fitnessCreditSamplePic.setKey(7);
            fitnessCreditSamplePic.setDesc("ACSM美国运动医学会认证");
            fitnessCreditSamplePic.setPicUrl("http://p0.meituan.net/scarlett/19d0549cd2150d51c15de415b6867388360881.png");
            fitnessCreditSamplePicList.add(fitnessCreditSamplePic);
        }
        {
            FitnessCreditSamplePic fitnessCreditSamplePic = new FitnessCreditSamplePic();
            fitnessCreditSamplePic.setKey(8);
            fitnessCreditSamplePic.setDesc("NASM美国国家运动医学会认证");
            fitnessCreditSamplePicList.add(fitnessCreditSamplePic);
        }
        {
            FitnessCreditSamplePic fitnessCreditSamplePic = new FitnessCreditSamplePic();
            fitnessCreditSamplePic.setKey(9);
            fitnessCreditSamplePic.setDesc("IFBB国际健美联合会认证");
            fitnessCreditSamplePic.setPicUrl("http://p0.meituan.net/scarlett/7d3dacc704d5f77be202ad530359678e45022.jpg");
            fitnessCreditSamplePicList.add(fitnessCreditSamplePic);
        }
        {
            FitnessCreditSamplePic fitnessCreditSamplePic = new FitnessCreditSamplePic();
            fitnessCreditSamplePic.setKey(10);
            fitnessCreditSamplePic.setDesc("IBFA国际身体阻力与体适能训练协会");
            fitnessCreditSamplePic.setPicUrl("http://p1.meituan.net/scarlett/50711fc3c210e10bd3065b8ce747418d200388.png");
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
            fitnessCreditSamplePic.setPicUrl("http://p0.meituan.net/scarlett/ab9a55ce99b335474e5f0c81abffb9f01609795.png");
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
            fitnessCreditSamplePic.setPicUrl("http://p1.meituan.net/scarlett/6af2ccdea21e8b1bd7b7e9a485ae14924581125.png");
            fitnessCreditSamplePicList.add(fitnessCreditSamplePic);
        }
        {
            FitnessCreditSamplePic fitnessCreditSamplePic = new FitnessCreditSamplePic();
            fitnessCreditSamplePic.setKey(34);
            fitnessCreditSamplePic.setDesc("CrossFit二级教员");
            fitnessCreditSamplePic.setPicUrl("http://p0.meituan.net/scarlett/1e21af59f03dd2a7b715f4e4f6fe5eea5846159.png");
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
            fitnessCreditSamplePic.setPicUrl("http://p1.meituan.net/scarlett/1e7e985096114049c044ed3c408aa07c217625.png");
            fitnessCreditSamplePicList.add(fitnessCreditSamplePic);
        }
        System.out.println(JSON.toJSON(fitnessCreditSamplePicList));
    }
}
