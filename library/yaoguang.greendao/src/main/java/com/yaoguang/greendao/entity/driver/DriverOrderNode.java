package com.yaoguang.greendao.entity.driver;

import android.os.Parcel;
import android.os.Parcelable;

public class DriverOrderNode implements Parcelable {

	private String id;

    private String driverOrderId;

    private String orderSn;

    private String orderSnSecond;

    private String parentNumber;

    private String parentName;

    private String childNumber;

    private String childName;

    private String sono1Id;

    private String sono2Id;

    private String address;
    
    private String addressId;

    private String detailFlag;

    private String detailSuccess;

    private String remark;

    private String createdBy;

    private String created;

    private String updated;

    private String isValid;

    public static final Creator<DriverOrderNode> CREATOR = new Creator<DriverOrderNode>() {
        @Override
        public DriverOrderNode createFromParcel(Parcel in) {
            return new DriverOrderNode(in);
        }

        @Override
        public DriverOrderNode[] newArray(int size) {
            return new DriverOrderNode[size];
        }
    };

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDriverOrderId() {
        return driverOrderId;
    }

    public void setDriverOrderId(String driverOrderId) {
        this.driverOrderId = driverOrderId;
    }

    public String getOrderSn() {
        return orderSn;
    }

    public void setOrderSn(String orderSn) {
        this.orderSn = orderSn;
    }

    public String getOrderSnSecond() {
        return orderSnSecond;
    }

    public void setOrderSnSecond(String orderSnSecond) {
        this.orderSnSecond = orderSnSecond;
    }

    public String getParentNumber() {
        return parentNumber;
    }

    public void setParentNumber(String parentNumber) {
        this.parentNumber = parentNumber;
    }

    public String getParentName() {
        return parentName;
    }

    public void setParentName(String parentName) {
        this.parentName = parentName;
    }

    public String getChildNumber() {
        return childNumber;
    }

    public void setChildNumber(String childNumber) {
        this.childNumber = childNumber;
    }

    public String getChildName() {
        return childName;
    }

    public void setChildName(String childName) {
        this.childName = childName;
    }

    public String getSono1Id() {
        return sono1Id;
    }

    public void setSono1Id(String sono1Id) {
        this.sono1Id = sono1Id;
    }

    public String getSono2Id() {
        return sono2Id;
    }

    public void setSono2Id(String sono2Id) {
        this.sono2Id = sono2Id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAddressId() {
        return addressId;
    }

    public void setAddressId(String addressId) {
        this.addressId = addressId;
    }

    public String getDetailFlag() {
        return detailFlag;
    }

    public void setDetailFlag(String detailFlag) {
        this.detailFlag = detailFlag;
    }

    public String getDetailSuccess() {
        return detailSuccess;
    }

    public void setDetailSuccess(String detailSuccess) {
        this.detailSuccess = detailSuccess;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
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

    public String getIsValid() {
        return isValid;
    }

    public void setIsValid(String isValid) {
        this.isValid = isValid;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.driverOrderId);
        dest.writeString(this.orderSn);
        dest.writeString(this.orderSnSecond);
        dest.writeString(this.parentNumber);
        dest.writeString(this.parentName);
        dest.writeString(this.childNumber);
        dest.writeString(this.childName);
        dest.writeString(this.sono1Id);
        dest.writeString(this.sono2Id);
        dest.writeString(this.address);
        dest.writeString(this.addressId);
        dest.writeString(this.detailFlag);
        dest.writeString(this.detailSuccess);
        dest.writeString(this.remark);
        dest.writeString(this.createdBy);
        dest.writeString(this.created);
        dest.writeString(this.updated);
        dest.writeString(this.isValid);
    }

    public DriverOrderNode() {
    }

    protected DriverOrderNode(Parcel in) {
        this.id = in.readString();
        this.driverOrderId = in.readString();
        this.orderSn = in.readString();
        this.orderSnSecond = in.readString();
        this.parentNumber = in.readString();
        this.parentName = in.readString();
        this.childNumber = in.readString();
        this.childName = in.readString();
        this.sono1Id = in.readString();
        this.sono2Id = in.readString();
        this.address = in.readString();
        this.addressId = in.readString();
        this.detailFlag = in.readString();
        this.detailSuccess = in.readString();
        this.remark = in.readString();
        this.createdBy = in.readString();
        this.created = in.readString();
        this.updated = in.readString();
        this.isValid = in.readString();
    }

}