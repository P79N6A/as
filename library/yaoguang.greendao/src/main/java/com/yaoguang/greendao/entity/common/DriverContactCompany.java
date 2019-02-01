package com.yaoguang.greendao.entity.common;


import android.os.Parcel;
import android.os.Parcelable;

import com.yaoguang.greendao.entity.UserOwner;

/**
 * 用户与企业成功关联的信息表
 *
 * @author heyonggang
 * @date 2017年4月1日下午2:04:23
 */
public class DriverContactCompany implements Parcelable {


    private String id;

    private String type;

    private String userDriverId;

    private String userCompanyId;

    private String userHackmanId;

    private String companyId;

    private String infoHackmanId;

    private String resourceId;

    private String remark;

    private String createdDeptId;

    private String created;

    private String updatedBy;

    private String updatedDeptId;

    private String updated;

    private String isValid;

    //状态，0-双方均未取消关注，1-关注方取消，2-被关注方取消
    private String status;

    //关注方公司信息
    private UserOfficeWrapper userOffice;

    //关注方货主信息
    private UserOwner userOwner;

    public UserOwner getUserOwner() {
        return userOwner;
    }

    public void setUserOwner(UserOwner userOwner) {
        this.userOwner = userOwner;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUserDriverId() {
        return userDriverId;
    }

    public void setUserDriverId(String userDriverId) {
        this.userDriverId = userDriverId;
    }

    public String getUserCompanyId() {
        return userCompanyId;
    }

    public void setUserCompanyId(String userCompanyId) {
        this.userCompanyId = userCompanyId;
    }

    public String getUserHackmanId() {
        return userHackmanId;
    }

    public void setUserHackmanId(String userHackmanId) {
        this.userHackmanId = userHackmanId;
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public String getInfoHackmanId() {
        return infoHackmanId;
    }

    public void setInfoHackmanId(String infoHackmanId) {
        this.infoHackmanId = infoHackmanId;
    }

    public String getResourceId() {
        return resourceId;
    }

    public void setResourceId(String resourceId) {
        this.resourceId = resourceId;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getCreatedDeptId() {
        return createdDeptId;
    }

    public void setCreatedDeptId(String createdDeptId) {
        this.createdDeptId = createdDeptId;
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
        this.updatedBy = updatedBy;
    }

    public String getUpdatedDeptId() {
        return updatedDeptId;
    }

    public void setUpdatedDeptId(String updatedDeptId) {
        this.updatedDeptId = updatedDeptId;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public UserOfficeWrapper getUserOffice() {
        return userOffice;
    }

    public void setUserOffice(UserOfficeWrapper userOffice) {
        this.userOffice = userOffice;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.type);
        dest.writeString(this.userDriverId);
        dest.writeString(this.userCompanyId);
        dest.writeString(this.userHackmanId);
        dest.writeString(this.companyId);
        dest.writeString(this.infoHackmanId);
        dest.writeString(this.resourceId);
        dest.writeString(this.remark);
        dest.writeString(this.createdDeptId);
        dest.writeString(this.created);
        dest.writeString(this.updatedBy);
        dest.writeString(this.updatedDeptId);
        dest.writeString(this.updated);
        dest.writeString(this.isValid);
        dest.writeString(this.status);
        dest.writeParcelable(this.userOffice, flags);
        dest.writeParcelable(this.userOwner, flags);
    }

    public DriverContactCompany() {
    }

    protected DriverContactCompany(Parcel in) {
        this.id = in.readString();
        this.type = in.readString();
        this.userDriverId = in.readString();
        this.userCompanyId = in.readString();
        this.userHackmanId = in.readString();
        this.companyId = in.readString();
        this.infoHackmanId = in.readString();
        this.resourceId = in.readString();
        this.remark = in.readString();
        this.createdDeptId = in.readString();
        this.created = in.readString();
        this.updatedBy = in.readString();
        this.updatedDeptId = in.readString();
        this.updated = in.readString();
        this.isValid = in.readString();
        this.status = in.readString();
        this.userOffice = in.readParcelable(UserOffice.class.getClassLoader());
        this.userOwner = in.readParcelable(UserOwner.class.getClassLoader());
    }

    public static final Parcelable.Creator<DriverContactCompany> CREATOR = new Parcelable.Creator<DriverContactCompany>() {
        @Override
        public DriverContactCompany createFromParcel(Parcel source) {
            return new DriverContactCompany(source);
        }

        @Override
        public DriverContactCompany[] newArray(int size) {
            return new DriverContactCompany[size];
        }
    };
}