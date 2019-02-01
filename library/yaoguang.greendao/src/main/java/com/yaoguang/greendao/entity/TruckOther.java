package com.yaoguang.greendao.entity;


import android.os.Parcel;
import android.os.Parcelable;

/**
 * 拖车-其它信息
 *
 * @author heyonggang
 * @date 2016年9月5日下午2:25:24
 */
public class TruckOther implements Parcelable {

    private String id;

    private String billsId;

    private String bBlNo;

    private String contQty;

    private String orderSn;

    private String ladingIdGroup;

    private String ladingIdSub;

    private String makeDate;

    private Integer bgType;

    private String firstOpen;

    private String firstBy;

    private String operator;

    private String operator2;

    private String deptid;

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

    public String getBillsId() {
        return billsId;
    }

    public void setBillsId(String billsId) {
        this.billsId = billsId == null ? null : billsId.trim();
    }

    public String getbBlNo() {
        return bBlNo;
    }

    public void setbBlNo(String bBlNo) {
        this.bBlNo = bBlNo == null ? null : bBlNo.trim();
    }

    public String getContQty() {
        return contQty;
    }

    public void setContQty(String contQty) {
        this.contQty = contQty == null ? null : contQty.trim();
    }

    public String getOrderSn() {
        return orderSn;
    }

    public void setOrderSn(String orderSn) {
        this.orderSn = orderSn == null ? null : orderSn.trim();
    }

    public String getLadingIdGroup() {
        return ladingIdGroup;
    }

    public void setLadingIdGroup(String ladingIdGroup) {
        this.ladingIdGroup = ladingIdGroup == null ? null : ladingIdGroup.trim();
    }

    public String getLadingIdSub() {
        return ladingIdSub;
    }

    public void setLadingIdSub(String ladingIdSub) {
        this.ladingIdSub = ladingIdSub == null ? null : ladingIdSub.trim();
    }

    public String getMakeDate() {
        return makeDate;
    }

    public void setMakeDate(String makeDate) {
        this.makeDate = makeDate;
    }

    public Integer getBgType() {
        return bgType;
    }

    public void setBgType(Integer bgType) {
        this.bgType = bgType;
    }

    public String getFirstOpen() {
        return firstOpen;
    }

    public void setFirstOpen(String firstOpen) {
        this.firstOpen = firstOpen;
    }

    public String getFirstBy() {
        return firstBy;
    }

    public void setFirstBy(String firstBy) {
        this.firstBy = firstBy;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator == null ? null : operator.trim();
    }

    public String getOperator2() {
        return operator2;
    }

    public void setOperator2(String operator2) {
        this.operator2 = operator2 == null ? null : operator2.trim();
    }

    public String getDeptid() {
        return deptid;
    }

    public void setDeptid(String deptid) {
        this.deptid = deptid == null ? null : deptid.trim();
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


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.billsId);
        dest.writeString(this.bBlNo);
        dest.writeString(this.contQty);
        dest.writeString(this.orderSn);
        dest.writeString(this.ladingIdGroup);
        dest.writeString(this.ladingIdSub);
        dest.writeString(this.makeDate);
        dest.writeValue(this.bgType);
        dest.writeString(this.firstOpen);
        dest.writeString(this.firstBy);
        dest.writeString(this.operator);
        dest.writeString(this.operator2);
        dest.writeString(this.deptid);
        dest.writeString(this.createdBy);
        dest.writeString(this.createdDeptId);
        dest.writeString(this.created);
        dest.writeString(this.updatedBy);
        dest.writeString(this.updatedDeptId);
        dest.writeString(this.updated);
    }

    public TruckOther() {
    }

    protected TruckOther(Parcel in) {
        this.id = in.readString();
        this.billsId = in.readString();
        this.bBlNo = in.readString();
        this.contQty = in.readString();
        this.orderSn = in.readString();
        this.ladingIdGroup = in.readString();
        this.ladingIdSub = in.readString();
        this.makeDate = in.readString();
        this.bgType = (Integer) in.readValue(Integer.class.getClassLoader());
        this.firstOpen = in.readString();
        this.firstBy = in.readString();
        this.operator = in.readString();
        this.operator2 = in.readString();
        this.deptid = in.readString();
        this.createdBy = in.readString();
        this.createdDeptId = in.readString();
        this.created = in.readString();
        this.updatedBy = in.readString();
        this.updatedDeptId = in.readString();
        this.updated = in.readString();
    }

    public static final Parcelable.Creator<TruckOther> CREATOR = new Parcelable.Creator<TruckOther>() {
        @Override
        public TruckOther createFromParcel(Parcel source) {
            return new TruckOther(source);
        }

        @Override
        public TruckOther[] newArray(int size) {
            return new TruckOther[size];
        }
    };
}