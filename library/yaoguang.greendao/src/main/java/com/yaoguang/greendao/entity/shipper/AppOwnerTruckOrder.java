package com.yaoguang.greendao.entity.shipper;


import android.os.Parcel;
import android.os.Parcelable;

/**
 * APP货主端拖车下单
 * @author wangxiaohui
 * 2018年4月12日
 */
public class AppOwnerTruckOrder implements Parcelable {

	private String id;

    private String otherService;

    private String orderSn;

    private String billsId;

    private String shipper;

    private String shipperId;

    private String goodsName;

    private String owner;

    private String port;

    private String address;

    private String shipCompany;

    private String mBlNo;

    private String etaPlan;

    private String userId;

    private String dockInfos;

    private String sonos;

    private String loadDate;

    private String isFeeCollect;

    private String fee;

    private String status;

    private String importTime;

    private String operator;

    private String clientId;

    private String createdDeptId;

    private String reamrk;
    private String reamrk1;
    private String reamrk2;

    private String created;

    private String updated;

    private String isValid;
    
    private String companyName;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOtherService() {
        return otherService;
    }

    public void setOtherService(String otherService) {
        this.otherService = otherService;
    }

    public String getOrderSn() {
        return orderSn;
    }

    public void setOrderSn(String orderSn) {
        this.orderSn = orderSn;
    }

    public String getBillsId() {
        return billsId;
    }

    public void setBillsId(String billsId) {
        this.billsId = billsId;
    }

    public String getShipper() {
        return shipper;
    }

    public void setShipper(String shipper) {
        this.shipper = shipper;
    }

    public String getShipperId() {
        return shipperId;
    }

    public void setShipperId(String shipperId) {
        this.shipperId = shipperId;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getShipCompany() {
        return shipCompany;
    }

    public void setShipCompany(String shipCompany) {
        this.shipCompany = shipCompany;
    }

    public String getmBlNo() {
        return mBlNo;
    }

    public void setmBlNo(String mBlNo) {
        this.mBlNo = mBlNo;
    }

    public String getEtaPlan() {
        return etaPlan;
    }

    public void setEtaPlan(String etaPlan) {
        this.etaPlan = etaPlan;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getDockInfos() {
        return dockInfos;
    }

    public void setDockInfos(String dockInfos) {
        this.dockInfos = dockInfos;
    }

    public String getSonos() {
        return sonos;
    }

    public void setSonos(String sonos) {
        this.sonos = sonos;
    }

    public String getLoadDate() {
        return loadDate;
    }

    public void setLoadDate(String loadDate) {
        this.loadDate = loadDate;
    }

    public String getIsFeeCollect() {
        return isFeeCollect;
    }

    public void setIsFeeCollect(String isFeeCollect) {
        this.isFeeCollect = isFeeCollect;
    }

    public String getFee() {
        return fee;
    }

    public void setFee(String fee) {
        this.fee = fee;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getImportTime() {
        return importTime;
    }

    public void setImportTime(String importTime) {
        this.importTime = importTime;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getCreatedDeptId() {
        return createdDeptId;
    }

    public void setCreatedDeptId(String createdDeptId) {
        this.createdDeptId = createdDeptId;
    }

    public String getReamrk() {
        return reamrk;
    }

    public void setReamrk(String reamrk) {
        this.reamrk = reamrk;
    }

    public String getReamrk1() {
        return reamrk1;
    }

    public void setReamrk1(String reamrk1) {
        this.reamrk1 = reamrk1;
    }

    public String getReamrk2() {
        return reamrk2;
    }

    public void setReamrk2(String reamrk2) {
        this.reamrk2 = reamrk2;
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

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.otherService);
        dest.writeString(this.orderSn);
        dest.writeString(this.billsId);
        dest.writeString(this.shipper);
        dest.writeString(this.shipperId);
        dest.writeString(this.goodsName);
        dest.writeString(this.owner);
        dest.writeString(this.port);
        dest.writeString(this.address);
        dest.writeString(this.shipCompany);
        dest.writeString(this.mBlNo);
        dest.writeString(this.etaPlan);
        dest.writeString(this.userId);
        dest.writeString(this.dockInfos);
        dest.writeString(this.sonos);
        dest.writeString(this.loadDate);
        dest.writeString(this.isFeeCollect);
        dest.writeString(this.fee);
        dest.writeString(this.status);
        dest.writeString(this.importTime);
        dest.writeString(this.operator);
        dest.writeString(this.clientId);
        dest.writeString(this.createdDeptId);
        dest.writeString(this.reamrk);
        dest.writeString(this.reamrk1);
        dest.writeString(this.reamrk2);
        dest.writeString(this.created);
        dest.writeString(this.updated);
        dest.writeString(this.isValid);
        dest.writeString(this.companyName);
    }

    public AppOwnerTruckOrder() {
    }

    protected AppOwnerTruckOrder(Parcel in) {
        this.id = in.readString();
        this.otherService = in.readString();
        this.orderSn = in.readString();
        this.billsId = in.readString();
        this.shipper = in.readString();
        this.shipperId = in.readString();
        this.goodsName = in.readString();
        this.owner = in.readString();
        this.port = in.readString();
        this.address = in.readString();
        this.shipCompany = in.readString();
        this.mBlNo = in.readString();
        this.etaPlan = in.readString();
        this.userId = in.readString();
        this.dockInfos = in.readString();
        this.sonos = in.readString();
        this.loadDate = in.readString();
        this.isFeeCollect = in.readString();
        this.fee = in.readString();
        this.status = in.readString();
        this.importTime = in.readString();
        this.operator = in.readString();
        this.clientId = in.readString();
        this.createdDeptId = in.readString();
        this.reamrk = in.readString();
        this.reamrk1 = in.readString();
        this.reamrk2 = in.readString();
        this.created = in.readString();
        this.updated = in.readString();
        this.isValid = in.readString();
        this.companyName = in.readString();
    }

    public static final Parcelable.Creator<AppOwnerTruckOrder> CREATOR = new Parcelable.Creator<AppOwnerTruckOrder>() {
        @Override
        public AppOwnerTruckOrder createFromParcel(Parcel source) {
            return new AppOwnerTruckOrder(source);
        }

        @Override
        public AppOwnerTruckOrder[] newArray(int size) {
            return new AppOwnerTruckOrder[size];
        }
    };
}