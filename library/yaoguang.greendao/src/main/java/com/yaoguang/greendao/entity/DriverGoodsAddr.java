package com.yaoguang.greendao.entity;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.Date;

/**
 * 司机工作单地址
 * @author liyangbin
 * 
 * @date 2017年10月30日下午6:16:14
 */
public class DriverGoodsAddr implements Parcelable {
	
	private static final long serialVersionUID = 9208877135789677057L;

	private String id;

    private String importAddrId;

    private String billsId;

    private String sort;

    private Integer flag;

    private String address;

    private String contacts;

    private String mobile;

    private String tel;

    private String remarks;

    private String goodsUnit;

    private String unitFax;

    private String goodsPriority;

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

    public String getImportAddrId() {
        return importAddrId;
    }

    public void setImportAddrId(String importAddrId) {
        this.importAddrId = importAddrId;
    }

    public String getBillsId() {
        return billsId;
    }

    public void setBillsId(String billsId) {
        this.billsId = billsId;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public Integer getFlag() {
        return flag;
    }

    public void setFlag(Integer flag) {
        this.flag = flag;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getContacts() {
        return contacts;
    }

    public void setContacts(String contacts) {
        this.contacts = contacts;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getGoodsUnit() {
        return goodsUnit;
    }

    public void setGoodsUnit(String goodsUnit) {
        this.goodsUnit = goodsUnit;
    }

    public String getUnitFax() {
        return unitFax;
    }

    public void setUnitFax(String unitFax) {
        this.unitFax = unitFax;
    }

    public String getGoodsPriority() {
        return goodsPriority;
    }

    public void setGoodsPriority(String goodsPriority) {
        this.goodsPriority = goodsPriority;
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
        dest.writeString(this.id);
        dest.writeString(this.importAddrId);
        dest.writeString(this.billsId);
        dest.writeString(this.sort);
        dest.writeValue(this.flag);
        dest.writeString(this.address);
        dest.writeString(this.contacts);
        dest.writeString(this.mobile);
        dest.writeString(this.tel);
        dest.writeString(this.remarks);
        dest.writeString(this.goodsUnit);
        dest.writeString(this.unitFax);
        dest.writeString(this.goodsPriority);
        dest.writeString(this.createdBy);
        dest.writeString(this.createdDeptId);
        dest.writeString(this.created);
        dest.writeString(this.updatedBy);
        dest.writeString(this.updatedDeptId);
        dest.writeString(this.updated);
    }

    public DriverGoodsAddr() {
    }

    protected DriverGoodsAddr(Parcel in) {
        this.id = in.readString();
        this.importAddrId = in.readString();
        this.billsId = in.readString();
        this.sort = in.readString();
        this.flag = (Integer) in.readValue(Integer.class.getClassLoader());
        this.address = in.readString();
        this.contacts = in.readString();
        this.mobile = in.readString();
        this.tel = in.readString();
        this.remarks = in.readString();
        this.goodsUnit = in.readString();
        this.unitFax = in.readString();
        this.goodsPriority = in.readString();
        this.createdBy = in.readString();
        this.createdDeptId = in.readString();
        this.created = in.readString();
        this.updatedBy = in.readString();
        this.updatedDeptId = in.readString();
        this.updated = in.readString();
    }

    public static final Creator<DriverGoodsAddr> CREATOR = new Creator<DriverGoodsAddr>() {
        @Override
        public DriverGoodsAddr createFromParcel(Parcel source) {
            return new DriverGoodsAddr(source);
        }

        @Override
        public DriverGoodsAddr[] newArray(int size) {
            return new DriverGoodsAddr[size];
        }
    };
}