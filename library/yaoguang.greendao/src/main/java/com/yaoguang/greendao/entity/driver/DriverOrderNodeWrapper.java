package com.yaoguang.greendao.entity.driver;


import android.os.Parcel;

import java.util.List;

public class DriverOrderNodeWrapper extends DriverOrderNode {
    private List<DriverOrderNodeList> driverOrderNodeList;

    private DriverNodeAddrVo truckGoodsAddr;

    private String nodeTitle;

    /**
     * 是否可以编辑
     * 1代表可以编辑
     * 0代表不可编辑
     */
    private String isEditable;

    public List<DriverOrderNodeList> getDriverOrderNodeList() {
        return driverOrderNodeList;
    }

    public void setDriverOrderNodeList(List<DriverOrderNodeList> driverOrderNodeList) {
        this.driverOrderNodeList = driverOrderNodeList;
    }

    public DriverNodeAddrVo getTruckGoodsAddr() {
        return truckGoodsAddr;
    }

    public void setTruckGoodsAddr(DriverNodeAddrVo truckGoodsAddr) {
        this.truckGoodsAddr = truckGoodsAddr;
    }

    public String getNodeTitle() {
        return nodeTitle;
    }

    public void setNodeTitle(String nodeTitle) {
        this.nodeTitle = nodeTitle;
    }

    public String getIsEditable() {
        return isEditable;
    }

    public void setIsEditable(String isEditable) {
        this.isEditable = isEditable;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeTypedList(this.driverOrderNodeList);
        dest.writeParcelable(this.truckGoodsAddr, flags);
        dest.writeString(this.nodeTitle);
        dest.writeString(this.isEditable);
    }

    public DriverOrderNodeWrapper() {
    }

    protected DriverOrderNodeWrapper(Parcel in) {
        super(in);
        this.driverOrderNodeList = in.createTypedArrayList(DriverOrderNodeList.CREATOR);
        this.truckGoodsAddr = in.readParcelable(DriverNodeAddrVo.class.getClassLoader());
        this.nodeTitle = in.readString();
        this.isEditable = in.readString();
    }

    public static final Creator<DriverOrderNodeWrapper> CREATOR = new Creator<DriverOrderNodeWrapper>() {
        @Override
        public DriverOrderNodeWrapper createFromParcel(Parcel source) {
            return new DriverOrderNodeWrapper(source);
        }

        @Override
        public DriverOrderNodeWrapper[] newArray(int size) {
            return new DriverOrderNodeWrapper[size];
        }
    };
}
