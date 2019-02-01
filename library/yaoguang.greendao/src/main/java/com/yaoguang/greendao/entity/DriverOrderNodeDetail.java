package com.yaoguang.greendao.entity;

import java.io.Serializable;
import java.util.Date;

public class DriverOrderNodeDetail implements Serializable{

	private static final long serialVersionUID = -714033884752424494L;

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

    private String mapType;

	private String lon;

    private String lat;

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

    private String isValid;

    private String sonoFirstFinish; // 柜一 装/送货完成(0:未完成 1:已完成)

    private String sonoSecondFinish; // 柜二 装/送货完成(0:未完成 1:已完成)

    public String getId() {
        return id;
    }

    public String getSonoFirstFinish() {
        return sonoFirstFinish;
    }

    public void setSonoFirstFinish(String sonoFirstFinish) {
        this.sonoFirstFinish = sonoFirstFinish;
    }

    public String getSonoSecondFinish() {
        return sonoSecondFinish;
    }

    public void setSonoSecondFinish(String sonoSecondFinish) {
        this.sonoSecondFinish = sonoSecondFinish;
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

    public String getMapType() {
        return mapType;
    }

    public void setMapType(String mapType) {
        this.mapType = mapType;
    }

    public String getLon() {
		return lon;
	}

	public void setLon(String lon) {
		this.lon = lon;
	}

	public String getLat() {
		return lat;
	}

	public void setLat(String lat) {
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

    public String getIsValid() {
        return isValid;
    }

    public void setIsValid(String isValid) {
        this.isValid = isValid;
    }
}