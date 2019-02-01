package com.yaoguang.greendao.entity;

import java.io.Serializable;


/**
 * 基本资料-起运地/目的地
 *
 * @author ZhangDeQuan
 * @time 2016年10月18日 上午10:05:01
 */
public class InfoFreightCity implements Serializable {
    /**
     *
     */
    private static final long serialVersionUID = -4016419582804985555L;

    private String id;

    private String cityId;

    private String cityChina;

    private String cityEnglish;

    private String telCode;

    private String zipCode;

    private String province;

    private String country;

    private String remark;

    private String createdBy;

    private String createdDeptId;

    private String created;

    private String updatedBy;

    private String updatedDeptId;

    private String updated;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getCityId() {
        return cityId;
    }

    public void setCityId(String cityId) {
        this.cityId = cityId == null ? null : cityId.trim();
    }

    public String getCityChina() {
        return cityChina;
    }

    public void setCityChina(String cityChina) {
        this.cityChina = cityChina == null ? null : cityChina.trim();
    }

    public String getCityEnglish() {
        return cityEnglish;
    }

    public void setCityEnglish(String cityEnglish) {
        this.cityEnglish = cityEnglish == null ? null : cityEnglish.trim();
    }

    public String getTelCode() {
        return telCode;
    }

    public void setTelCode(String telCode) {
        this.telCode = telCode == null ? null : telCode.trim();
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode == null ? null : zipCode.trim();
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province == null ? null : province.trim();
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country == null ? null : country.trim();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
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
}