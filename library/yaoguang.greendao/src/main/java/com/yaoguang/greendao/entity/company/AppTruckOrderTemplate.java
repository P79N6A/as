package com.yaoguang.greendao.entity.company;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;


/**
 * App企业端拖车业务下单模板实体
 * 
 * @author wangxiaohui
 * @date 2017年9月25日 下午2:32:20
 */
public class AppTruckOrderTemplate implements Parcelable {

	private String id;

	private String userId;

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

	// 装卸货信息
	private String dockInfos;

	private String remark;

	private String createdBy;

	private String clientId;

	private String created;

	private String updated;

	private Integer isValid;

	// 装卸货地址
	private ArrayList<AppInfoClientPlace> appInfoClientPlaces;

	public ArrayList<AppInfoClientPlace> getAppInfoClientPlaces() {
		return appInfoClientPlaces;
	}

	public void setAppInfoClientPlaces(ArrayList<AppInfoClientPlace> appInfoClientPlaces) {
		this.appInfoClientPlaces = appInfoClientPlaces;
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

	public String getDockInfos() {
		return dockInfos;
	}

	public void setDockInfos(String dockInfos) {
		this.dockInfos = dockInfos;
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

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(this.id);
		dest.writeString(this.userId);
		dest.writeString(this.shipperId);
		dest.writeString(this.shipper);
		dest.writeString(this.goodsName);
		dest.writeString(this.owner);
		dest.writeString(this.port);
		dest.writeString(this.address);
		dest.writeString(this.shipCompany);
		dest.writeString(this.dockInfos);
		dest.writeString(this.remark);
		dest.writeString(this.createdBy);
		dest.writeString(this.clientId);
		dest.writeString(this.created);
		dest.writeString(this.updated);
		dest.writeValue(this.isValid);
		dest.writeTypedList(this.appInfoClientPlaces);
	}

	public AppTruckOrderTemplate() {
	}

	protected AppTruckOrderTemplate(Parcel in) {
		this.id = in.readString();
		this.userId = in.readString();
		this.shipperId = in.readString();
		this.shipper = in.readString();
		this.goodsName = in.readString();
		this.owner = in.readString();
		this.port = in.readString();
		this.address = in.readString();
		this.shipCompany = in.readString();
		this.dockInfos = in.readString();
		this.remark = in.readString();
		this.createdBy = in.readString();
		this.clientId = in.readString();
		this.created = in.readString();
		this.updated = in.readString();
		this.isValid = (Integer) in.readValue(Integer.class.getClassLoader());
		this.appInfoClientPlaces = in.createTypedArrayList(AppInfoClientPlace.CREATOR);
	}

	public static final Parcelable.Creator<AppTruckOrderTemplate> CREATOR = new Parcelable.Creator<AppTruckOrderTemplate>() {
		@Override
		public AppTruckOrderTemplate createFromParcel(Parcel source) {
			return new AppTruckOrderTemplate(source);
		}

		@Override
		public AppTruckOrderTemplate[] newArray(int size) {
			return new AppTruckOrderTemplate[size];
		}
	};
}