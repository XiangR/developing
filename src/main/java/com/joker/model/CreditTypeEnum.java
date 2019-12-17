package com.joker.model;

public enum CreditTypeEnum {

    FNVQ(1, "国家健身教练职业资格认证"),

    NFP(2, "国家健身专业人员认证"),

    CBBA(3, "CBBA中国健美协会认证"),

    AASFP(4, "AASFP亚洲体适能教练认证"),

    ACE(5, "ACE美国运动协会认证"),

    NSCA(6, "NSCA美国国家体适能协会认证"),

    ACSM(7, "ACSM美国运动医学会认证"),

    NASM(8, "NASM美国国家运动医学会认证"),

    IFBB(9, "IFBB国际健美联合会认证"),

    IBFA(10, "IBFA国际身体阻力与体适能训练协会"),

    //CHEA(11,"CHEA：美国高等教育认证委员会"), //历史数据，该类型删除
    ISSA(12, "ISSA国际体育科学协会认证"),

    //NSCF(13, "NSCF：美国体能健身协会"),  //历史数据，该类型删除
    AFAA(14, "AFAA美国有氧体适能协会"),

    IFA(15, "IFA国际健身协会有氧训练师"),

    CROSSFIT_FIRST(33, "CrossFit一级教员"),

    CROSSFIT_SECONT(34, "CrossFit二级教员"),

    CROSSFIT_THIRD(35, "CrossFit三级教员"),

    CROSSFIT_FOURTH(36, "CrossFit四级教员"),

    MFT(37, "MFT格斗认证"),

    HONOR(100, "荣誉");//value值注意

    private int value;

    private String desc;

    CreditTypeEnum(int value, String desc) {
        this.desc = desc;
        this.value = value;
    }

    public int getValue() {

        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public static CreditTypeEnum getEnumByType(int type) {
        CreditTypeEnum[] values = CreditTypeEnum.values();
        for (CreditTypeEnum typeEnum : values) {
            if (typeEnum.getValue() == type) {
                return typeEnum;
            }
        }
        return null;
    }
}
