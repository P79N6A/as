package com.yaoguang.greendao.entity.common;

import android.os.Parcel;
import android.os.Parcelable;

import com.yaoguang.greendao.entity.UserApply;

import java.io.Serializable;


/**
 * 机构管理表
 *
 * @author heyonggang
 * @date 2016年9月23日下午1:51:50
 */
public class UserOffice implements Parcelable {

    private String id;

    private String parentId;

    private String allParentIds;

    private String name;

    private String companyRemark;

    private String shortName;

    private String companyCode;

    private Long sort;

    private String areaId;

    private String code;

    private Integer type;

    private Integer grade;

    private String address;

    private String zipCode;

    private String master;

    private String phone;

    private String fax;

    private String email;

    private Integer useable;

    private String primaryPerson;

    private String deputyPerson;

    private String remarks;

    private String createdBy;

    private String createdDeptId;

    private String created;

    private String updatedBy;

    private String updatedDeptId;

    private String updated;

    private Integer delFlag;

    /** 是否选择公司，用于 我的订单 选择公司菜单*/
    private boolean isSelect;

    //企业logo
    private String photo;

    private UserApply userApply;

    //关注表主键id
    private String contactId;

    //关注类型 0-待审核，1-已关注成功，2-未关注
    private Integer status;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAllParentIds() {
        return allParentIds;
    }

    public void setAllParentIds(String allParentIds) {
        this.allParentIds = allParentIds;
    }

    public String getAreaId() {
        return areaId;
    }

    public void setAreaId(String areaId) {
        this.areaId = areaId;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getCompanyCode() {
        return companyCode;
    }

    public void setCompanyCode(String companyCode) {
        this.companyCode = companyCode;
    }

    public String getCompanyRemark() {
        return companyRemark;
    }

    public void setCompanyRemark(String companyRemark) {
        this.companyRemark = companyRemark;
    }

    public String getContactId() {
        return contactId;
    }

    public void setContactId(String contactId) {
        this.contactId = contactId;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getCreatedDeptId() {
        return createdDeptId;
    }

    public void setCreatedDeptId(String createdDeptId) {
        this.createdDeptId = createdDeptId;
    }

    public Integer getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(Integer delFlag) {
        this.delFlag = delFlag;
    }

    public String getDeputyPerson() {
        return deputyPerson;
    }

    public void setDeputyPerson(String deputyPerson) {
        this.deputyPerson = deputyPerson;
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

    public Integer getGrade() {
        return grade;
    }

    public void setGrade(Integer grade) {
        this.grade = grade;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
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

    public String getPrimaryPerson() {
        return primaryPerson;
    }

    public void setPrimaryPerson(String primaryPerson) {
        this.primaryPerson = primaryPerson;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public Long getSort() {
        return sort;
    }

    public void setSort(Long sort) {
        this.sort = sort;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getUpdated() {
        return updated;
    }

    public void setUpdated(String updated) {
        this.updated = updated;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public String getUpdatedDeptId() {
        return updatedDeptId;
    }

    public void setUpdatedDeptId(String updatedDeptId) {
        this.updatedDeptId = updatedDeptId;
    }

    public Integer getUseable() {
        return useable;
    }

    public void setUseable(Integer useable) {
        this.useable = useable;
    }

    public UserApply getUserApply() {
        return userApply;
    }

    public void setUserApply(UserApply userApply) {
        this.userApply = userApply;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public boolean isSelect() {
        return isSelect;
    }

    public void setSelect(boolean select) {
        isSelect = select;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.parentId);
        dest.writeString(this.allParentIds);
        dest.writeString(this.name);
        dest.writeString(this.companyRemark);
        dest.writeString(this.shortName);
        dest.writeString(this.companyCode);
        dest.writeValue(this.sort);
        dest.writeString(this.areaId);
        dest.writeString(this.code);
        dest.writeValue(this.type);
        dest.writeValue(this.grade);
        dest.writeString(this.address);
        dest.writeString(this.zipCode);
        dest.writeString(this.master);
        dest.writeString(this.phone);
        dest.writeString(this.fax);
        dest.writeString(this.email);
        dest.writeValue(this.useable);
        dest.writeString(this.primaryPerson);
        dest.writeString(this.deputyPerson);
        dest.writeString(this.remarks);
        dest.writeString(this.createdBy);
        dest.writeString(this.createdDeptId);
        dest.writeString(this.created);
        dest.writeString(this.updatedBy);
        dest.writeString(this.updatedDeptId);
        dest.writeString(this.updated);
        dest.writeValue(this.delFlag);
        dest.writeByte(this.isSelect ? (byte) 1 : (byte) 0);
        dest.writeString(this.photo);
        dest.writeParcelable(this.userApply, flags);
        dest.writeString(this.contactId);
        dest.writeValue(this.status);
    }

    public UserOffice() {
    }

    protected UserOffice(Parcel in) {
        this.id = in.readString();
        this.parentId = in.readString();
        this.allParentIds = in.readString();
        this.name = in.readString();
        this.companyRemark = in.readString();
        this.shortName = in.readString();
        this.companyCode = in.readString();
        this.sort = (Long) in.readValue(Long.class.getClassLoader());
        this.areaId = in.readString();
        this.code = in.readString();
        this.type = (Integer) in.readValue(Integer.class.getClassLoader());
        this.grade = (Integer) in.readValue(Integer.class.getClassLoader());
        this.address = in.readString();
        this.zipCode = in.readString();
        this.master = in.readString();
        this.phone = in.readString();
        this.fax = in.readString();
        this.email = in.readString();
        this.useable = (Integer) in.readValue(Integer.class.getClassLoader());
        this.primaryPerson = in.readString();
        this.deputyPerson = in.readString();
        this.remarks = in.readString();
        this.createdBy = in.readString();
        this.createdDeptId = in.readString();
        this.created = in.readString();
        this.updatedBy = in.readString();
        this.updatedDeptId = in.readString();
        this.updated = in.readString();
        this.delFlag = (Integer) in.readValue(Integer.class.getClassLoader());
        this.isSelect = in.readByte() != 0;
        this.photo = in.readString();
        this.userApply = in.readParcelable(UserApply.class.getClassLoader());
        this.contactId = in.readString();
        this.status = (Integer) in.readValue(Integer.class.getClassLoader());
    }

    public static final Parcelable.Creator<UserOffice> CREATOR = new Parcelable.Creator<UserOffice>() {
        @Override
        public UserOffice createFromParcel(Parcel source) {
            return new UserOffice(source);
        }

        @Override
        public UserOffice[] newArray(int size) {
            return new UserOffice[size];
        }
    };
}