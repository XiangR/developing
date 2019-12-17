package com.joker.model;/*
 * Create Author  : apple
 * Create  Time   : 14-8-28 下午2:45
 * Project        : BeautyWeb
 *
 * Copyright (c) 2010-2015 by Shanghai HanTao Information Co., Ltd.
 * All rights reserved.
 *
 */

import java.io.Serializable;
import java.util.List;

public class TechnicianModel implements Serializable {
    private int id;

    private int shopId;

    private String shopname;

    private String shopRegionName;

    private String photoUrl;

    private String name;

    private String title;

    private String link;

    private String summary;

    private int star;

    private int status;

    private int hasapplaud;

    private int authed;

    private int bookable;

    private String distance;

    /**
     * 技师星级
     */
    private int reviewStar;

    /**
     * 技师积分
     */
    private int point;

    /**
     * 积分等级
     */
    private String level;

    private List<TechnicianWorkModel> technicianWorkModelList;

    private int authenticationStatus;

    //XX人预约
    private String bookCount;

    private int workYears;

    private int categoryId;

    private String mainPrice;

    private String tagType;

    private int source;

    /**
     * 技师好评数量
     */
    private String gooodReviewCount;

    /**
     * 技师好评文案
     */
    private String gooodReviewTitle;

    /**
     * 技师是否认证（1：认证，0：未认证）
     */
    private int certificate;

    /**
     * 技师认证图片
     */
    private String certificateUrl;

    private String detailUrl;

    private String honorText;//标签文案
    private String honorColor;//文案色值
    private String backgroundColor;//背景色值

    private int sellNum;//次卡售卖量

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public int getAuthenticationStatus() {
        return authenticationStatus;
    }

    public void setAuthenticationStatus(int authenticationStatus) {
        this.authenticationStatus = authenticationStatus;
    }

    public String getGooodReviewCount() {
        return gooodReviewCount;
    }

    public void setGooodReviewCount(String gooodReviewCount) {
        this.gooodReviewCount = gooodReviewCount;
    }

    public String getGooodReviewTitle() {
        return gooodReviewTitle;
    }

    public void setGooodReviewTitle(String gooodReviewTitle) {
        this.gooodReviewTitle = gooodReviewTitle;
    }

    public int getCertificate() {
        return certificate;
    }

    public void setCertificate(int certificate) {
        this.certificate = certificate;
    }

    public String getCertificateUrl() {
        return certificateUrl;
    }

    public void setCertificateUrl(String certificateUrl) {
        this.certificateUrl = certificateUrl;
    }

    public int getWorkYears() {
        return workYears;
    }

    public void setWorkYears(int workYears) {
        this.workYears = workYears;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public int getPoint() {
        return point;
    }

    public void setPoint(int point) {
        this.point = point;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getShopId() {
        return shopId;
    }

    public void setShopId(int shopId) {
        this.shopId = shopId;
    }

    public String getShopname() {
        return shopname;
    }

    public void setShopname(String shopname) {
        this.shopname = shopname;
    }

    public String getShopRegionName() {
        return shopRegionName;
    }

    public void setShopRegionName(String shopRegionName) {
        this.shopRegionName = shopRegionName;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getStar() {
        return star;
    }

    public void setStar(int star) {
        this.star = star;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getHasapplaud() {
        return hasapplaud;
    }

    public void setHasapplaud(int hasapplaud) {
        this.hasapplaud = hasapplaud;
    }

    public int getAuthed() {
        return authed;
    }

    public void setAuthed(int authed) {
        this.authed = authed;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getBookable() {
        return bookable;
    }

    public void setBookable(int bookable) {
        this.bookable = bookable;
    }

    public int getReviewStar() {
        return reviewStar;
    }

    public void setReviewStar(int reviewStar) {
        this.reviewStar = reviewStar;
    }

    public List<TechnicianWorkModel> getTechnicianWorkModelList() {
        return technicianWorkModelList;
    }

    public void setTechnicianWorkModelList(List<TechnicianWorkModel> technicianWorkModelList) {
        this.technicianWorkModelList = technicianWorkModelList;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getBookCount() {
        return bookCount;
    }

    public void setBookCount(String bookCount) {
        this.bookCount = bookCount;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public String getMainPrice() {
        return mainPrice;
    }

    public void setMainPrice(String mainPrice) {
        this.mainPrice = mainPrice;
    }

    public String getTagType() {
        return tagType;
    }

    public void setTagType(String tagType) {
        this.tagType = tagType;
    }

    public int getSource() {
        return source;
    }

    public void setSource(int source) {
        this.source = source;
    }

    public String getDetailUrl() {
        return detailUrl;
    }

    public void setDetailUrl(String detailUrl) {
        this.detailUrl = detailUrl;
    }

    public String getHonorText() {
        return honorText;
    }

    public void setHonorText(String honorText) {
        this.honorText = honorText;
    }

    public String getHonorColor() {
        return honorColor;
    }

    public void setHonorColor(String honorColor) {
        this.honorColor = honorColor;
    }

    public String getBackgroundColor() {
        return backgroundColor;
    }

    public void setBackgroundColor(String backgroundColor) {
        this.backgroundColor = backgroundColor;
    }

    public int getSellNum() {
        return sellNum;
    }

    public void setSellNum(int sellNum) {
        this.sellNum = sellNum;
    }
}

