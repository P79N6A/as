package com.yaoguang.greendao.entity;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * 司机工作单详情
 * @author liyangbin
 *
 * @date 2017年11月1日下午6:03:59
 */
public class DriverBillsVo implements Parcelable {

    // 主键
    private String id;

    //工作单号
    private String truckBillsId;

    //装卸时间
    private String loadDate;

    //货名
    private String goodsName;

    //货物类型
    private String goodsType;

    //船公司
    private String shipCompany;

    //装拆箱类型(0:装箱 1：拆箱)
    private Integer otherService;

    //装卸地址信息
    List<DriverGoodsAddr> goodsAddrs;

    //货柜
    private List<DriverSonoVo> sonos;

    public String getServiceType(){
        if(this.otherService == 0){
            return "装箱";
        }else {
            return"拆箱";
        }
    }

    public String getShipCompany() {
        return shipCompany;
    }

    public void setShipCompany(String shipCompany) {
        this.shipCompany = shipCompany;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getOtherService() {
        return otherService;
    }

    public void setOtherService(Integer otherService) {
        this.otherService = otherService;
    }

    public String getTruckBillsId() {
        return truckBillsId;
    }

    public void setTruckBillsId(String truckBillsId) {
        this.truckBillsId = truckBillsId;
    }

    public String getLoadDate() {
        return loadDate;
    }

    public void setLoadDate(String loadDate) {
        this.loadDate = loadDate;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public String getGoodsType() {
        return goodsType;
    }

    public void setGoodsType(String goodsType) {
        this.goodsType = goodsType;
    }

    public List<DriverGoodsAddr> getGoodsAddrs() {
        return goodsAddrs;
    }

    public void setGoodsAddrs(List<DriverGoodsAddr> goodsAddrs) {
        this.goodsAddrs = goodsAddrs;
    }

    public List<DriverSonoVo> getSonos() {
        return sonos;
    }

    public void setSonos(List<DriverSonoVo> sonos) {
        this.sonos = sonos;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.truckBillsId);
        dest.writeString(this.loadDate);
        dest.writeString(this.goodsName);
        dest.writeString(this.goodsType);
        dest.writeString(this.shipCompany);
        dest.writeValue(this.otherService);
        dest.writeList(this.goodsAddrs);
        dest.writeList(this.sonos);
    }

    public DriverBillsVo() {
    }

    protected DriverBillsVo(Parcel in) {
        this.id = in.readString();
        this.truckBillsId = in.readString();
        this.loadDate = in.readString();
        this.goodsName = in.readString();
        this.goodsType = in.readString();
        this.shipCompany = in.readString();
        this.otherService = (Integer) in.readValue(Integer.class.getClassLoader());
        this.goodsAddrs = new ArrayList<DriverGoodsAddr>();
        in.readList(this.goodsAddrs, DriverGoodsAddr.class.getClassLoader());
        this.sonos = new ArrayList<DriverSonoVo>();
        in.readList(this.sonos, DriverSonoVo.class.getClassLoader());
    }

    public static final Creator<DriverBillsVo> CREATOR = new Creator<DriverBillsVo>() {
        @Override
        public DriverBillsVo createFromParcel(Parcel source) {
            return new DriverBillsVo(source);
        }

        @Override
        public DriverBillsVo[] newArray(int size) {
            return new DriverBillsVo[size];
        }
    };
}
