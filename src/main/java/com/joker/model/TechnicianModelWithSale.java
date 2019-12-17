package com.joker.model;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

/**
 * 手艺人带次卡信息 :
 * <p/>
 * author：xiaye create：2019-05-16
 */

public class TechnicianModelWithSale implements Serializable {
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

    private String bookTag;

    private int oldCustomers;

    private long weekSale;

    private long totalSale;

    private String cardDiscount;

    private String groupDiscount;

    //已服务X人
    private String cardSaleCount;

    private int mallAd; //1有广告；0无广告

    //已生效资源位
    private List<String> resourceState;

    private List<ProductTag> cardList;

    private List<ProductTag> groupList;

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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
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

    public int getBookable() {
        return bookable;
    }

    public void setBookable(int bookable) {
        this.bookable = bookable;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public int getReviewStar() {
        return reviewStar;
    }

    public void setReviewStar(int reviewStar) {
        this.reviewStar = reviewStar;
    }

    public int getPoint() {
        return point;
    }

    public void setPoint(int point) {
        this.point = point;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public List<TechnicianWorkModel> getTechnicianWorkModelList() {
        return technicianWorkModelList;
    }

    public void setTechnicianWorkModelList(List<TechnicianWorkModel> technicianWorkModelList) {
        this.technicianWorkModelList = technicianWorkModelList;
    }

    public int getAuthenticationStatus() {
        return authenticationStatus;
    }

    public void setAuthenticationStatus(int authenticationStatus) {
        this.authenticationStatus = authenticationStatus;
    }

    public String getBookCount() {
        return bookCount;
    }

    public void setBookCount(String bookCount) {
        this.bookCount = bookCount;
    }

    public int getWorkYears() {
        return workYears;
    }

    public void setWorkYears(int workYears) {
        this.workYears = workYears;
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

    public String getBookTag() {
        return bookTag;
    }

    public void setBookTag(String bookTag) {
        this.bookTag = bookTag;
    }

    public int getOldCustomers() {
        return oldCustomers;
    }

    public void setOldCustomers(int oldCustomers) {
        this.oldCustomers = oldCustomers;
    }

    public long getWeekSale() {
        return weekSale;
    }

    public void setWeekSale(long weekSale) {
        this.weekSale = weekSale;
    }

    public long getTotalSale() {
        return totalSale;
    }

    public void setTotalSale(long totalSale) {
        this.totalSale = totalSale;
    }

    public String getCardDiscount() {
        return cardDiscount;
    }

    public void setCardDiscount(String cardDiscount) {
        this.cardDiscount = cardDiscount;
    }

    public String getGroupDiscount() {
        return groupDiscount;
    }

    public void setGroupDiscount(String groupDiscount) {
        this.groupDiscount = groupDiscount;
    }

    public String getCardSaleCount() {
        return cardSaleCount;
    }

    public void setCardSaleCount(String cardSaleCount) {
        this.cardSaleCount = cardSaleCount;
    }

    public int getMallAd() {
        return mallAd;
    }

    public void setMallAd(int mallAd) {
        this.mallAd = mallAd;
    }

    public List<String> getResourceState() {
        return resourceState;
    }

    public void setResourceState(List<String> resourceState) {
        this.resourceState = resourceState;
    }

    public List<ProductTag> getCardList() {
        return cardList;
    }

    public void setCardList(List<ProductTag> cardList) {
        this.cardList = cardList;
    }

    public List<ProductTag> getGroupList() {
        return groupList;
    }

    public void setGroupList(List<ProductTag> groupList) {
        this.groupList = groupList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TechnicianModelWithSale)) {
            return false;
        }
        TechnicianModelWithSale that = (TechnicianModelWithSale) o;
        return id == that.id &&
                shopId == that.shopId &&
                star == that.star &&
                status == that.status &&
                hasapplaud == that.hasapplaud &&
                authed == that.authed &&
                bookable == that.bookable &&
                reviewStar == that.reviewStar &&
                point == that.point &&
                authenticationStatus == that.authenticationStatus &&
                workYears == that.workYears &&
                categoryId == that.categoryId &&
                source == that.source &&
                certificate == that.certificate &&
                sellNum == that.sellNum &&
                oldCustomers == that.oldCustomers &&
                weekSale == that.weekSale &&
                totalSale == that.totalSale &&
                mallAd == that.mallAd &&
                Objects.equals(shopname, that.shopname) &&
                Objects.equals(shopRegionName, that.shopRegionName) &&
                Objects.equals(photoUrl, that.photoUrl) &&
                Objects.equals(name, that.name) &&
                Objects.equals(title, that.title) &&
                Objects.equals(link, that.link) &&
                Objects.equals(summary, that.summary) &&
                Objects.equals(distance, that.distance) &&
                Objects.equals(level, that.level) &&
                Objects.equals(technicianWorkModelList, that.technicianWorkModelList) &&
                Objects.equals(bookCount, that.bookCount) &&
                Objects.equals(mainPrice, that.mainPrice) &&
                Objects.equals(tagType, that.tagType) &&
                Objects.equals(gooodReviewCount, that.gooodReviewCount) &&
                Objects.equals(gooodReviewTitle, that.gooodReviewTitle) &&
                Objects.equals(certificateUrl, that.certificateUrl) &&
                Objects.equals(detailUrl, that.detailUrl) &&
                Objects.equals(honorText, that.honorText) &&
                Objects.equals(honorColor, that.honorColor) &&
                Objects.equals(backgroundColor, that.backgroundColor) &&
                Objects.equals(bookTag, that.bookTag) &&
                Objects.equals(cardDiscount, that.cardDiscount) &&
                Objects.equals(groupDiscount, that.groupDiscount) &&
                Objects.equals(cardSaleCount, that.cardSaleCount) &&
                Objects.equals(resourceState, that.resourceState) &&
                Objects.equals(cardList, that.cardList) &&
                Objects.equals(groupList, that.groupList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, shopId, shopname, shopRegionName, photoUrl, name, title, link, summary, star, status, hasapplaud, authed,
                            bookable, distance, reviewStar, point, level, technicianWorkModelList, authenticationStatus, bookCount,
                            workYears, categoryId, mainPrice, tagType, source, gooodReviewCount, gooodReviewTitle, certificate, certificateUrl, detailUrl, honorText, honorColor, backgroundColor, sellNum, bookTag, oldCustomers, weekSale, totalSale, cardDiscount, groupDiscount, cardSaleCount, mallAd, resourceState, cardList, groupList);
    }
}
