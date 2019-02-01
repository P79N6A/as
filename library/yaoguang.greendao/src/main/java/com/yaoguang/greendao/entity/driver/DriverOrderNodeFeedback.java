package com.yaoguang.greendao.entity.driver;

import java.io.Serializable;

/**
 * 故障反馈
 */
public class DriverOrderNodeFeedback implements Serializable {

    private static final long serialVersionUID = -3518006362284155336L;

    private String id;
    private String orderSn;
    private String nodeName;
    private String nodeId;
    private String remark;
    private String picture;
    private String videoUrl;
    private byte[] video;
    private String contNoFirst;
    private String sealNoFirst;
    private String contNoSecond;
    private String sealNoSecond;
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
    private String created;
    private String updated;
    private Integer isValid;

    public String getContNoFirst() {
        return contNoFirst;
    }

    public void setContNoFirst(String contNoFirst) {
        this.contNoFirst = contNoFirst;
    }

    public String getContNoSecond() {
        return contNoSecond;
    }

    public void setContNoSecond(String contNoSecond) {
        this.contNoSecond = contNoSecond;
    }

    public String getSealNoFirst() {
        return sealNoFirst;
    }

    public void setSealNoFirst(String sealNoFirst) {
        this.sealNoFirst = sealNoFirst;
    }

    public String getSealNoSecond() {
        return sealNoSecond;
    }

    public void setSealNoSecond(String sealNoSecond) {
        this.sealNoSecond = sealNoSecond;
    }

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

    public String getNodeName() {
        return nodeName;
    }

    public void setNodeName(String nodeName) {
        this.nodeName = nodeName == null ? null : nodeName.trim();
    }

    public String getNodeId() {
        return nodeId;
    }

    public void setNodeId(String nodeId) {
        this.nodeId = nodeId == null ? null : nodeId.trim();
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

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
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

    public byte[] getVideo() {
        return video;
    }

    public void setVideo(byte[] video) {
        this.video = video;
    }
}