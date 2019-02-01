package com.yaoguang.greendao.entity;


import java.io.Serializable;


/**
 * Created by zhongjh on 2017/4/21.
 */
public class NodesBean implements Serializable {

    private static final long serialVersionUID = -8003021310975257817L;

    //类型（0代表界面1 1代表界面2）
    private int type;
    //类型（0代表只需要提交界面1的数据,1代表需要提交界面2的数据）
    private int submitType;
    //第几步
    private int step;
    //提交的文本提示
    private String tips;
    //柜子数量
    private int countQty;
    // 黄点
    private boolean huangDian;
    // 是否有提交记录
    private Integer detailFlag;

    private Integer detailSuccess;

    public Integer getDetailSuccess() {
        return detailSuccess;
    }

    public void setDetailSuccess(Integer detailSuccess) {
        this.detailSuccess = detailSuccess;
    }

    private String contactsName;
    private String contactsMobile;
    private String controlSubmits;
    //不可编辑的 0代表柜号1,柜号2 1代表封号1,封号2 什么都没代表都可以编辑
    private String controlEdits;


    public Integer getDetailFlag() {
        return detailFlag;
    }

    public void setDetailFlag(Integer detailFlag) {
        this.detailFlag = detailFlag;
    }

    private String id;

    private String orderSn;

    private String nodeId;

    private String nodeName;

    private String contNoFirst;

    private String sealNoFirst;

    private String contNoSecond;

    private String sealNoSecond;

    private String remark;

    private String picture;

    private String videoUrl;

    private String audioUrl;

    private Integer mapType;

    private Double lon;

    private Double lat;

    private String address;

    private String province;

    private String city;

    private String district;

    private String street;

    private String createdBy;

    private String createdDeptId;

    private String created;

    private String updatedBy;

    private String updatedDeptId;

    private String updated;

    private Integer isValid;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getOrderSn() {
        return orderSn;
    }

    public void setOrderSn(String orderSn) {
        this.orderSn = orderSn == null ? null : orderSn.trim();
    }

    public boolean isHuangDian() {
        return huangDian;
    }

    public void setHuangDian(boolean huangDian) {
        this.huangDian = huangDian;
    }

    public String getNodeId() {
        return nodeId;
    }

    public void setNodeId(String nodeId) {
        this.nodeId = nodeId == null ? null : nodeId.trim();
    }

    public String getNodeName() {
        return nodeName;
    }

    public void setNodeName(String nodeName) {
        this.nodeName = nodeName == null ? null : nodeName.trim();
    }

    public String getContNoFirst() {
        return contNoFirst;
    }

    public void setContNoFirst(String contNoFirst) {
        this.contNoFirst = contNoFirst == null ? null : contNoFirst.trim();
    }

    public String getSealNoFirst() {
        return sealNoFirst;
    }

    public void setSealNoFirst(String sealNoFirst) {
        this.sealNoFirst = sealNoFirst == null ? null : sealNoFirst.trim();
    }

    public String getContNoSecond() {
        return contNoSecond;
    }

    public void setContNoSecond(String contNoSecond) {
        this.contNoSecond = contNoSecond == null ? null : contNoSecond.trim();
    }

    public String getSealNoSecond() {
        return sealNoSecond;
    }

    public void setSealNoSecond(String sealNoSecond) {
        this.sealNoSecond = sealNoSecond == null ? null : sealNoSecond.trim();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture == null ? null : picture.trim();
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl == null ? null : videoUrl.trim();
    }

    public String getAudioUrl() {
        return audioUrl;
    }

    public void setAudioUrl(String audioUrl) {
        this.audioUrl = audioUrl == null ? null : audioUrl.trim();
    }

    public Integer getMapType() {
        return mapType;
    }

    public void setMapType(Integer mapType) {
        this.mapType = mapType;
    }

    public Double getLon() {
        return lon;
    }

    public void setLon(Double lon) {
        this.lon = lon;
    }

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address == null ? null : address.trim();
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province == null ? null : province.trim();
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city == null ? null : city.trim();
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district == null ? null : district.trim();
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street == null ? null : street.trim();
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy == null ? null : createdBy.trim();
    }

    public String getCreatedDeptId() {
        return createdDeptId;
    }

    public void setCreatedDeptId(String createdDeptId) {
        this.createdDeptId = createdDeptId == null ? null : createdDeptId.trim();
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy == null ? null : updatedBy.trim();
    }

    public String getUpdatedDeptId() {
        return updatedDeptId;
    }

    public void setUpdatedDeptId(String updatedDeptId) {
        this.updatedDeptId = updatedDeptId == null ? null : updatedDeptId.trim();
    }

    public String getUpdated() {
        return updated;
    }

    public void setUpdated(String updated) {
        this.updated = updated;
    }

    public Integer getIsValid() {
        return isValid;
    }

    public void setIsValid(Integer isValid) {
        this.isValid = isValid;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getSubmitType() {
        return submitType;
    }

    public void setSubmitType(int submitType) {
        this.submitType = submitType;
    }

    public int getStep() {
        return step;
    }

    public void setStep(int step) {
        this.step = step;
    }

    public String getTips() {
        return tips;
    }

    public void setTips(String tips) {
        this.tips = tips;
    }

    public String getContactsName() {
        return contactsName;
    }

    public void setContactsName(String contactsName) {
        this.contactsName = contactsName;
    }

    public String getContactsMobile() {
        return contactsMobile;
    }

    public void setContactsMobile(String contactsMobile) {
        this.contactsMobile = contactsMobile;
    }

    public String getControlSubmits() {
        return controlSubmits;
    }

    public void setControlSubmits(String controlSubmits) {
        this.controlSubmits = controlSubmits;
    }

    public String getControlEdits() {
        return controlEdits;
    }

    public void setControlEdits(String controlEdits) {
        this.controlEdits = controlEdits;
    }

    public int getCountQty() {
        return countQty;
    }

    public void setCountQty(int countQty) {
        this.countQty = countQty;
    }

}
