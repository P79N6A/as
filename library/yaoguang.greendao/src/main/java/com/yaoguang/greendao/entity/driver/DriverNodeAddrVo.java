package com.yaoguang.greendao.entity.driver;


import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * 节点地址
 * @author liyangbin
 * 
 * @date 2018年4月28日下午3:16:14
 */
public class DriverNodeAddrVo implements Parcelable {

    private String id;

    private String flag;

    private String address;

    private String contacts;

    private String mobile;

    private String tel;

    private String remarks;

    private String goodsUnit;

    private String unitFax;

    private String goodsPriority;

    private Site site;

    private List<Site> area;

    private String location;

    private String importAddrId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
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

    public Site getSite() {
        return site;
    }

    public void setSite(Site site) {
        this.site = site;
    }

    public List<Site> getArea() {
        return area;
    }

    public void setArea(List<Site> area) {
        this.area = area;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getImportAddrId() {
        return importAddrId;
    }

    public void setImportAddrId(String importAddrId) {
        this.importAddrId = importAddrId;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.flag);
        dest.writeString(this.address);
        dest.writeString(this.contacts);
        dest.writeString(this.mobile);
        dest.writeString(this.tel);
        dest.writeString(this.remarks);
        dest.writeString(this.goodsUnit);
        dest.writeString(this.unitFax);
        dest.writeString(this.goodsPriority);
        dest.writeParcelable(this.site, flags);
        dest.writeTypedList(this.area);
        dest.writeString(this.location);
        dest.writeString(this.importAddrId);
    }

    public DriverNodeAddrVo() {
    }

    protected DriverNodeAddrVo(Parcel in) {
        this.id = in.readString();
        this.flag = in.readString();
        this.address = in.readString();
        this.contacts = in.readString();
        this.mobile = in.readString();
        this.tel = in.readString();
        this.remarks = in.readString();
        this.goodsUnit = in.readString();
        this.unitFax = in.readString();
        this.goodsPriority = in.readString();
        this.site = in.readParcelable(Site.class.getClassLoader());
        this.area = in.createTypedArrayList(Site.CREATOR);
        this.location = in.readString();
        this.importAddrId = in.readString();
    }

    public static final Parcelable.Creator<DriverNodeAddrVo> CREATOR = new Parcelable.Creator<DriverNodeAddrVo>() {
        @Override
        public DriverNodeAddrVo createFromParcel(Parcel source) {
            return new DriverNodeAddrVo(source);
        }

        @Override
        public DriverNodeAddrVo[] newArray(int size) {
            return new DriverNodeAddrVo[size];
        }
    };
}