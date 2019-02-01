package com.yaoguang.greendao.entity.driver;

import java.io.Serializable;
import java.util.Date;

public class UserDriverCar implements Serializable{

    private static final long serialVersionUID = 8983637955259784235L;

    private String id;

    private String userId;

    private String licenceType;

    private String licenceProvince;

    private String licenceNumber;

    private String brandModel;

    private String owner;

    private String licencePhoto1;

    private String licencePhoto2;

    private String registerDate;

    private String loadOrTraction;

    private String curbWeight;

    private String length;

    private String width;

    private String height;

    private String applyTime;

    private String auditStatus;

    private String auditStatusStr;

    // 拒绝原因
    private String remark;

    private String auditTime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId == null ? null : userId.trim();
    }

    public String getLicenceType() {
        return licenceType;
    }

    public void setLicenceType(String licenceType) {
        this.licenceType = licenceType;
    }

    public String getLicenceProvince() {
        return licenceProvince;
    }

    public void setLicenceProvince(String licenceProvince) {
        this.licenceProvince = licenceProvince == null ? null : licenceProvince.trim();
    }

    public String getLicenceNumber() {
        return licenceNumber;
    }

    public void setLicenceNumber(String licenceNumber) {
        this.licenceNumber = licenceNumber == null ? null : licenceNumber.trim();
    }

    public String getBrandModel() {
        return brandModel;
    }

    public void setBrandModel(String brandModel) {
        this.brandModel = brandModel == null ? null : brandModel.trim();
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner == null ? null : owner.trim();
    }

    public String getLicencePhoto1() {
        return licencePhoto1;
    }

    public void setLicencePhoto1(String licencePhoto1) {
        this.licencePhoto1 = licencePhoto1 == null ? null : licencePhoto1.trim();
    }

    public String getLicencePhoto2() {
        return licencePhoto2;
    }

    public void setLicencePhoto2(String licencePhoto2) {
        this.licencePhoto2 = licencePhoto2 == null ? null : licencePhoto2.trim();
    }

    public String getRegisterDate() {
        return registerDate;
    }

    public void setRegisterDate(String registerDate) {
        this.registerDate = registerDate;
    }

    public String getLoadOrTraction() {
        return loadOrTraction;
    }

    public void setLoadOrTraction(String loadOrTraction) {
        this.loadOrTraction = loadOrTraction;
    }

    public String getCurbWeight() {
        return curbWeight;
    }

    public void setCurbWeight(String curbWeight) {
        this.curbWeight = curbWeight;
    }

    public String getLength() {
        return length;
    }

    public void setLength(String length) {
        this.length = length;
    }

    public String getWidth() {
        return width;
    }

    public void setWidth(String width) {
        this.width = width;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getApplyTime() {
        return applyTime;
    }

    public void setApplyTime(String applyTime) {
        this.applyTime = applyTime;
    }

    public String getAuditStatus() {
        return auditStatus;
    }

    public void setAuditStatus(String auditStatus) {
        this.auditStatus = auditStatus;
    }

    public String getAuditTime() {
        return auditTime;
    }

    public void setAuditTime(String auditTime) {
        this.auditTime = auditTime;
    }

    /**
     * 认证状态(0:未认证 1:待认证 2:通过 3:不通过)
     */
    public String getAuditStatusStr() {
        switch (auditStatus) {
            case "0":
                return "未认证";
            case "1":
                return "待认证";
            case "2":
                return "通过";
            case "3":
                return "不通过";
        }
        return "";
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public void setAuditStatusStr(String auditStatusStr) {
        this.auditStatusStr = auditStatusStr;
    }
}