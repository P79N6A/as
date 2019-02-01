package com.yaoguang.greendao.entity;

/**
 * Created by zhongjh on 2017/7/11.
 */
public class BannerPic {

    /**
     * id : 2886d586d4de4b308ccda3e42947dea1
     * platformType : 2
     * title : 3
     * userId : 1
     * picAddr : p/201706/9848b18572c3486eadff9ecb511334c153529.jpg
     * picAttrId : d26f04b81b5d4e44918822ceacefd073
     * sort : 18
     * urlHref :
     * remark :
     * created : 2017-07-07 11:25:04
     * isValid : 1
     * createdBy : admin
     */

    private String id;
    private int platformType;
    private String title;
    private String userId;
    private String picAddr;
    private String picAttrId;
    private int sort;
    private String urlHref;
    private String remark;
    private String created;
    private int isValid;
    private String createdBy;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getPlatformType() {
        return platformType;
    }

    public void setPlatformType(int platformType) {
        this.platformType = platformType;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPicAddr() {
        return picAddr;
    }

    public void setPicAddr(String picAddr) {
        this.picAddr = picAddr;
    }

    public String getPicAttrId() {
        return picAttrId;
    }

    public void setPicAttrId(String picAttrId) {
        this.picAttrId = picAttrId;
    }

    public int getSort() {
        return sort;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }

    public String getUrlHref() {
        return urlHref;
    }

    public void setUrlHref(String urlHref) {
        this.urlHref = urlHref;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public int getIsValid() {
        return isValid;
    }

    public void setIsValid(int isValid) {
        this.isValid = isValid;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

}