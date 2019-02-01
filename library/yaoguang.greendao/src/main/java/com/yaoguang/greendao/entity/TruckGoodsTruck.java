package com.yaoguang.greendao.entity;


import android.os.Parcel;
import android.os.Parcelable;

/**
 * 拖车-装/卸货的派车
 * 
 * @author heyonggang
 * @date 2016年9月5日下午2:27:35
 */
public class TruckGoodsTruck implements Parcelable {

	private String id;

    private String billsId;

    private String truckId;

    private String preCarriage;

    private String driverId;

    private String driverTel;

    private String preTruck;

    private String truckNo;

    private String driverOther;

    private String supercargo;

    private Integer haveSpecial;

    private Integer debitNoteStatus;

    private String debitNoteDate;

    private Integer isAgentback;

    private String agentbackDate;

    private String tjId;

    private String overTime;

    private Integer isSafe;

    private Integer isOverload;

    private String accountRemark;

    private String specialRemark;

    private String isNote;

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

    public String getTruckId() {
        return truckId;
    }

    public void setTruckId(String truckId) {
        this.truckId = truckId == null ? null : truckId.trim();
    }

    public String getPreCarriage() {
        return preCarriage;
    }

    public void setPreCarriage(String preCarriage) {
        this.preCarriage = preCarriage == null ? null : preCarriage.trim();
    }

    public String getDriverId() {
        return driverId;
    }

    public void setDriverId(String driverId) {
        this.driverId = driverId == null ? null : driverId.trim();
    }

    public String getDriverTel() {
        return driverTel;
    }

    public void setDriverTel(String driverTel) {
        this.driverTel = driverTel == null ? null : driverTel.trim();
    }

    public String getPreTruck() {
        return preTruck;
    }

    public void setPreTruck(String preTruck) {
        this.preTruck = preTruck == null ? null : preTruck.trim();
    }

    public String getTruckNo() {
        return truckNo;
    }

    public void setTruckNo(String truckNo) {
        this.truckNo = truckNo == null ? null : truckNo.trim();
    }

    public String getDriverOther() {
        return driverOther;
    }

    public void setDriverOther(String driverOther) {
        this.driverOther = driverOther == null ? null : driverOther.trim();
    }

    public String getSupercargo() {
        return supercargo;
    }

    public void setSupercargo(String supercargo) {
        this.supercargo = supercargo == null ? null : supercargo.trim();
    }

    public Integer getHaveSpecial() {
        return haveSpecial;
    }

    public void setHaveSpecial(Integer haveSpecial) {
        this.haveSpecial = haveSpecial;
    }

    public Integer getDebitNoteStatus() {
        return debitNoteStatus;
    }

    public void setDebitNoteStatus(Integer debitNoteStatus) {
        this.debitNoteStatus = debitNoteStatus;
    }

    public String getDebitNoteDate() {
        return debitNoteDate;
    }

    public void setDebitNoteDate(String debitNoteDate) {
        this.debitNoteDate = debitNoteDate;
    }

    public Integer getIsAgentback() {
        return isAgentback;
    }

    public void setIsAgentback(Integer isAgentback) {
        this.isAgentback = isAgentback;
    }

    public String getAgentbackDate() {
        return agentbackDate;
    }

    public void setAgentbackDate(String agentbackDate) {
        this.agentbackDate = agentbackDate;
    }

    public String getTjId() {
        return tjId;
    }

    public void setTjId(String tjId) {
        this.tjId = tjId == null ? null : tjId.trim();
    }

    public String getOverTime() {
        return overTime;
    }

    public void setOverTime(String overTime) {
        this.overTime = overTime == null ? null : overTime.trim();
    }

    public Integer getIsSafe() {
        return isSafe;
    }

    public void setIsSafe(Integer isSafe) {
        this.isSafe = isSafe;
    }

    public Integer getIsOverload() {
        return isOverload;
    }

    public void setIsOverload(Integer isOverload) {
        this.isOverload = isOverload;
    }

    public String getAccountRemark() {
        return accountRemark;
    }

    public void setAccountRemark(String accountRemark) {
        this.accountRemark = accountRemark == null ? null : accountRemark.trim();
    }

    public String getSpecialRemark() {
        return specialRemark;
    }

    public void setSpecialRemark(String specialRemark) {
        this.specialRemark = specialRemark == null ? null : specialRemark.trim();
    }

    public String getIsNote() {
        return isNote;
    }

    public void setIsNote(String isNote) {
        this.isNote = isNote == null ? null : isNote.trim();
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
        dest.writeString(this.truckId);
        dest.writeString(this.preCarriage);
        dest.writeString(this.driverId);
        dest.writeString(this.driverTel);
        dest.writeString(this.preTruck);
        dest.writeString(this.truckNo);
        dest.writeString(this.driverOther);
        dest.writeString(this.supercargo);
        dest.writeValue(this.haveSpecial);
        dest.writeValue(this.debitNoteStatus);
        dest.writeString(this.debitNoteDate);
        dest.writeValue(this.isAgentback);
        dest.writeString(this.agentbackDate);
        dest.writeString(this.tjId);
        dest.writeString(this.overTime);
        dest.writeValue(this.isSafe);
        dest.writeValue(this.isOverload);
        dest.writeString(this.accountRemark);
        dest.writeString(this.specialRemark);
        dest.writeString(this.isNote);
        dest.writeString(this.createdBy);
        dest.writeString(this.createdDeptId);
        dest.writeString(this.created);
        dest.writeString(this.updatedBy);
        dest.writeString(this.updatedDeptId);
        dest.writeString(this.updated);
    }

    public TruckGoodsTruck() {
    }

    protected TruckGoodsTruck(Parcel in) {
        this.id = in.readString();
        this.billsId = in.readString();
        this.truckId = in.readString();
        this.preCarriage = in.readString();
        this.driverId = in.readString();
        this.driverTel = in.readString();
        this.preTruck = in.readString();
        this.truckNo = in.readString();
        this.driverOther = in.readString();
        this.supercargo = in.readString();
        this.haveSpecial = (Integer) in.readValue(Integer.class.getClassLoader());
        this.debitNoteStatus = (Integer) in.readValue(Integer.class.getClassLoader());
        this.debitNoteDate = in.readString();
        this.isAgentback = (Integer) in.readValue(Integer.class.getClassLoader());
        this.agentbackDate = in.readString();
        this.tjId = in.readString();
        this.overTime = in.readString();
        this.isSafe = (Integer) in.readValue(Integer.class.getClassLoader());
        this.isOverload = (Integer) in.readValue(Integer.class.getClassLoader());
        this.accountRemark = in.readString();
        this.specialRemark = in.readString();
        this.isNote = in.readString();
        this.createdBy = in.readString();
        this.createdDeptId = in.readString();
        this.created = in.readString();
        this.updatedBy = in.readString();
        this.updatedDeptId = in.readString();
        this.updated = in.readString();
    }

    public static final Parcelable.Creator<TruckGoodsTruck> CREATOR = new Parcelable.Creator<TruckGoodsTruck>() {
        @Override
        public TruckGoodsTruck createFromParcel(Parcel source) {
            return new TruckGoodsTruck(source);
        }

        @Override
        public TruckGoodsTruck[] newArray(int size) {
            return new TruckGoodsTruck[size];
        }
    };
}