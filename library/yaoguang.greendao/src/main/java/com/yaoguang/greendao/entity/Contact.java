package com.yaoguang.greendao.entity;

import com.yaoguang.greendao.entity.common.UserOffice;

/**
 * 关联表
 * Created by zhongjh on 2017/4/18.
 */

public class Contact {

    private Long lid;
    private String id;
    private String created;
    private String userDriverId;
    private String drivingLicence;
    private String userOfficeName;
    private String userOfficeId;
    private String firstletter;
    private long firstletterid;
    private String refuseRemark;
    private String address;
    private String name;
    private String companyRemark;
    private String email;
    private String phone;
    private String photo;
    private String companyCode;
    private String zipCode;
    private String master;
    private String fax;
    private String shortName;
    private String companyId;

    // 机构信息
    private UserOffice userOffice;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCompanyCode() {
        return companyCode;
    }

    public void setCompanyCode(String companyCode) {
        this.companyCode = companyCode;
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public String getCompanyRemark() {
        return companyRemark;
    }

    public void setCompanyRemark(String companyRemark) {
        this.companyRemark = companyRemark;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public String getDrivingLicence() {
        return drivingLicence;
    }

    public void setDrivingLicence(String drivingLicence) {
        this.drivingLicence = drivingLicence;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    public String getFirstletter() {
        return firstletter;
    }

    public void setFirstletter(String firstletter) {
        this.firstletter = firstletter;
    }

    public long getFirstletterid() {
        return firstletterid;
    }

    public void setFirstletterid(long firstletterid) {
        this.firstletterid = firstletterid;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Long getLid() {
        return lid;
    }

    public void setLid(Long lid) {
        this.lid = lid;
    }

    public String getMaster() {
        return master;
    }

    public void setMaster(String master) {
        this.master = master;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getRefuseRemark() {
        return refuseRemark;
    }

    public void setRefuseRemark(String refuseRemark) {
        this.refuseRemark = refuseRemark;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public String getUserDriverId() {
        return userDriverId;
    }

    public void setUserDriverId(String userDriverId) {
        this.userDriverId = userDriverId;
    }

    public UserOffice getUserOffice() {
        return userOffice;
    }

    public void setUserOffice(UserOffice userOffice) {
        this.userOffice = userOffice;
    }

    public String getUserOfficeId() {
        return userOfficeId;
    }

    public void setUserOfficeId(String userOfficeId) {
        this.userOfficeId = userOfficeId;
    }

    public String getUserOfficeName() {
        return userOfficeName;
    }

    public void setUserOfficeName(String userOfficeName) {
        this.userOfficeName = userOfficeName;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }
}
