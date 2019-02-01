package com.yaoguang.greendao.entity.driver;

import java.io.Serializable;
import java.util.Date;

public class UserDriverLicence implements Serializable{

    private static final long serialVersionUID = -4142347581077974632L;

    private String id;

    private String userId;

    private String name;

    private String licenceNumber;

    private String licenseDate;

    private String carType;

    private String licencePhoto1;

    private String licencePhoto2;

    private String startDate;

    private String endDate;

    private String applyTime;

    private Integer auditStatus;

    private String auditStatusStr;

    private String auditTime;

    // 拒绝原因
    private String remark;

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getLicenceNumber() {
        return licenceNumber;
    }

    public void setLicenceNumber(String licenceNumber) {
        this.licenceNumber = licenceNumber == null ? null : licenceNumber.trim();
    }

    public String getLicenseDate() {
        return licenseDate;
    }

    public void setLicenseDate(String licenseDate) {
        this.licenseDate = licenseDate;
    }

    public String getCarType() {
        return carType;
    }

    public void setCarType(String carType) {
        this.carType = carType == null ? null : carType.trim();
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

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getApplyTime() {
        return applyTime;
    }

    public void setApplyTime(String applyTime) {
        this.applyTime = applyTime;
    }

    public Integer getAuditStatus() {
        return auditStatus;
    }

    public void setAuditStatus(Integer auditStatus) {
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
     * @return 认证状态
     */
    public String getAuditStatusStr() {
        switch (auditStatus) {
            case 0:
                return "未认证";
            case 1:
                return "待认证";
            case 2:
                return "通过";
            case 3:
                return "不通过";
        }
        return "";
    }

    public void setAuditStatusStr(String auditStatusStr) {
        this.auditStatusStr = auditStatusStr;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}