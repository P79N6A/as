package com.yaoguang.greendao.entity.company;

import android.os.Parcel;
import android.os.Parcelable;

import com.yaoguang.greendao.entity.AppTruckSono;

import java.util.ArrayList;

/**
 * App企业端拖车业务下单实体
 * 
 * @author wangxiaohui
 * @date 2017年9月25日 下午2:32:20
 */
public class AppTruckOrder implements Parcelable {

	private String id;

	private String userId;

	private String orderSn;

	private String billsId;

	// 0-装，1-卸
	private String otherService;

	// 托运人id
	private String shipperId;

	// 托运人
	private String shipper;

	// 货物名称
	private String goodsName;

	// 货主
	private String owner;

	// 港口
	private String port;

	// 地区
	private String address;

	// 船公司
	private String shipCompany;

	// 运单号
	private String mBlNo;

	// 预到船期
	private String etaPlan;

	// 装卸货信息
	private String dockInfos;

	// 货柜信息
	private String sonos;

	// 卸货时间
	private String loadDate;

	// 是否代收 0-否，1-是
	private Integer isFeeCollect;

	// 预定费用
	private Double fee;

	private String remark;
	private String remark1;
	private String remark2;
	private String remark3;

	private String created;

	private String updated;

	private String createdBy;

	private String clientId;

	private Integer isValid;

	// 是否导入 0-否，1-是
	private Integer status;

	// 导入时间
	private String importTime;

	// 装卸货地址
	private ArrayList<AppInfoClientPlace> appInfoClientPlaces;

	// 货柜信息
	private ArrayList<AppTruckSono> appTruckSonos;

	public ArrayList<AppInfoClientPlace> getAppInfoClientPlaces() {
		return appInfoClientPlaces;
	}

	public void setAppInfoClientPlaces(ArrayList<AppInfoClientPlace> appInfoClientPlaces) {
		this.appInfoClientPlaces = appInfoClientPlaces;
	}

	public ArrayList<AppTruckSono> getAppTruckSonos() {
		return appTruckSonos;
	}

	public void setAppTruckSonos(ArrayList<AppTruckSono> appTruckSonos) {
		this.appTruckSonos = appTruckSonos;
	}

	public String getUpdated() {
		return updated;
	}

	public void setUpdated(String updated) {
		this.updated = updated;
	}

	public String getCreated() {
		return created;
	}

	public void setCreated(String created) {
		this.created = created;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
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

	public String getOtherService() {
		return otherService;
	}

	public void setOtherService(String otherService) {
		this.otherService = otherService;
	}

	public String getShipperId() {
		return shipperId;
	}

	public void setShipperId(String shipperId) {
		this.shipperId = shipperId;
	}

	public String getShipper() {
		return shipper;
	}

	public void setShipper(String shipper) {
		this.shipper = shipper;
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

	public Integer getIsFeeCollect() {
		return isFeeCollect;
	}

	public void setIsFeeCollect(Integer isFeeCollect) {
		this.isFeeCollect = isFeeCollect;
	}

	public Double getFee() {
		return fee;
	}

	public void setFee(Double fee) {
		this.fee = fee;
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

	public String getClientId() {
		return clientId;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

	public Integer getIsValid() {
		return isValid;
	}

	public void setIsValid(Integer isValid) {
		this.isValid = isValid;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getImportTime() {
		return importTime;
	}

	public void setImportTime(String importTime) {
		this.importTime = importTime;
	}

	public String getRemark1() {
		return remark1;
	}

	public void setRemark1(String remark1) {
		this.remark1 = remark1;
	}

	public String getRemark2() {
		return remark2;
	}

	public void setRemark2(String remark2) {
		this.remark2 = remark2;
	}

	public String getRemark3() {
		return remark3;
	}

	public void setRemark3(String remark3) {
		this.remark3 = remark3;
	}


	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(this.id);
		dest.writeString(this.userId);
		dest.writeString(this.orderSn);
		dest.writeString(this.billsId);
		dest.writeString(this.otherService);
		dest.writeString(this.shipperId);
		dest.writeString(this.shipper);
		dest.writeString(this.goodsName);
		dest.writeString(this.owner);
		dest.writeString(this.port);
		dest.writeString(this.address);
		dest.writeString(this.shipCompany);
		dest.writeString(this.mBlNo);
		dest.writeString(this.etaPlan);
		dest.writeString(this.dockInfos);
		dest.writeString(this.sonos);
		dest.writeString(this.loadDate);
		dest.writeValue(this.isFeeCollect);
		dest.writeValue(this.fee);
		dest.writeString(this.remark);
		dest.writeString(this.remark1);
		dest.writeString(this.remark2);
		dest.writeString(this.remark3);
		dest.writeString(this.created);
		dest.writeString(this.updated);
		dest.writeString(this.createdBy);
		dest.writeString(this.clientId);
		dest.writeValue(this.isValid);
		dest.writeValue(this.status);
		dest.writeString(this.importTime);
		dest.writeTypedList(this.appInfoClientPlaces);
		dest.writeTypedList(this.appTruckSonos);
	}

	public AppTruckOrder() {
	}

	protected AppTruckOrder(Parcel in) {
		this.id = in.readString();
		this.userId = in.readString();
		this.orderSn = in.readString();
		this.billsId = in.readString();
		this.otherService = in.readString();
		this.shipperId = in.readString();
		this.shipper = in.readString();
		this.goodsName = in.readString();
		this.owner = in.readString();
		this.port = in.readString();
		this.address = in.readString();
		this.shipCompany = in.readString();
		this.mBlNo = in.readString();
		this.etaPlan = in.readString();
		this.dockInfos = in.readString();
		this.sonos = in.readString();
		this.loadDate = in.readString();
		this.isFeeCollect = (Integer) in.readValue(Integer.class.getClassLoader());
		this.fee = (Double) in.readValue(Double.class.getClassLoader());
		this.remark = in.readString();
		this.remark1 = in.readString();
		this.remark2 = in.readString();
		this.remark3 = in.readString();
		this.created = in.readString();
		this.updated = in.readString();
		this.createdBy = in.readString();
		this.clientId = in.readString();
		this.isValid = (Integer) in.readValue(Integer.class.getClassLoader());
		this.status = (Integer) in.readValue(Integer.class.getClassLoader());
		this.importTime = in.readString();
		this.appInfoClientPlaces = in.createTypedArrayList(AppInfoClientPlace.CREATOR);
		this.appTruckSonos = in.createTypedArrayList(AppTruckSono.CREATOR);
	}

	public static final Parcelable.Creator<AppTruckOrder> CREATOR = new Parcelable.Creator<AppTruckOrder>() {
		@Override
		public AppTruckOrder createFromParcel(Parcel source) {
			return new AppTruckOrder(source);
		}

		@Override
		public AppTruckOrder[] newArray(int size) {
			return new AppTruckOrder[size];
		}
	};
}