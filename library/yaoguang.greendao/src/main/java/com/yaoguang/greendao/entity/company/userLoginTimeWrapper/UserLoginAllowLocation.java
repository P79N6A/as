package com.yaoguang.greendao.entity.company.userLoginTimeWrapper;

import android.os.Parcel;
import android.os.Parcelable;

public class UserLoginAllowLocation implements Parcelable {

    public boolean isCheck() {
        return isCheck;
    }

    public void setCheck(boolean check) {
        isCheck = check;
    }

    private boolean isCheck;

    private String id;

    private String address; // 地址

    private Double lon; // 经度

    private Double lat; // 纬度

    private Integer radius; // 半径

    private String clientId; // 归属总公司ID

    private Integer delFlag; // 是否删除

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
        this.id = id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
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

    public Integer getRadius() {
        return radius;
    }

    public void setRadius(Integer radius) {
        this.radius = radius;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public Integer getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(Integer delFlag) {
        this.delFlag = delFlag;
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


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeByte(this.isCheck ? (byte) 1 : (byte) 0);
        dest.writeString(this.id);
        dest.writeString(this.address);
        dest.writeValue(this.lon);
        dest.writeValue(this.lat);
        dest.writeValue(this.radius);
        dest.writeString(this.clientId);
        dest.writeValue(this.delFlag);
        dest.writeString(this.createdBy);
        dest.writeString(this.createdDeptId);
        dest.writeString(this.created);
        dest.writeString(this.updatedBy);
        dest.writeString(this.updatedDeptId);
        dest.writeString(this.updated);
    }

    public UserLoginAllowLocation() {
    }

    protected UserLoginAllowLocation(Parcel in) {
        this.isCheck = in.readByte() != 0;
        this.id = in.readString();
        this.address = in.readString();
        this.lon = (Double) in.readValue(Double.class.getClassLoader());
        this.lat = (Double) in.readValue(Double.class.getClassLoader());
        this.radius = (Integer) in.readValue(Integer.class.getClassLoader());
        this.clientId = in.readString();
        this.delFlag = (Integer) in.readValue(Integer.class.getClassLoader());
        this.createdBy = in.readString();
        this.createdDeptId = in.readString();
        this.created = in.readString();
        this.updatedBy = in.readString();
        this.updatedDeptId = in.readString();
        this.updated = in.readString();
    }

    public static final Parcelable.Creator<UserLoginAllowLocation> CREATOR = new Parcelable.Creator<UserLoginAllowLocation>() {
        @Override
        public UserLoginAllowLocation createFromParcel(Parcel source) {
            return new UserLoginAllowLocation(source);
        }

        @Override
        public UserLoginAllowLocation[] newArray(int size) {
            return new UserLoginAllowLocation[size];
        }
    };
}