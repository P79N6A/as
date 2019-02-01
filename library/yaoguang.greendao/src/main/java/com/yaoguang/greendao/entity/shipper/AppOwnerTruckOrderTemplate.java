package com.yaoguang.greendao.entity.shipper;

import android.os.Parcel;
import android.os.Parcelable;

import com.yaoguang.greendao.entity.company.AppInfoClientPlace;

import java.util.List;

/**
 * app货主端下单模板
 * 
 * @author wangxiaohui 2018年4月12日
 */
public class AppOwnerTruckOrderTemplate implements Parcelable {

	private String id;

	private String userId;

	private String goodsName;

	private String owner;

	private String port;

	private String address;

	private String shipCompany;

	private String dockInfos;

	private String clientId;

	private String remark;

	private String created;

	private String updated;

	private String isValid;

	// 装卸货地址
	private List<AppInfoClientPlace> appInfoClientPlaces;

	// 关联公司名称
	private String officeName;

	public String getOfficeName() {
		return officeName;
	}

	public void setOfficeName(String officeName) {
		this.officeName = officeName;
	}


	public List<AppInfoClientPlace> getAppInfoClientPlaces() {
		return appInfoClientPlaces;
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

	public String getClientId() {
		return clientId;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
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

	public void setAppInfoClientPlaces(List<AppInfoClientPlace> appInfoClientPlaces) {
		this.appInfoClientPlaces = appInfoClientPlaces;
	}


	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(this.id);
		dest.writeString(this.userId);
		dest.writeString(this.goodsName);
		dest.writeString(this.owner);
		dest.writeString(this.port);
		dest.writeString(this.address);
		dest.writeString(this.shipCompany);
		dest.writeString(this.dockInfos);
		dest.writeString(this.clientId);
		dest.writeString(this.remark);
		dest.writeString(this.created);
		dest.writeString(this.updated);
		dest.writeString(this.isValid);
		dest.writeTypedList(this.appInfoClientPlaces);
		dest.writeString(this.officeName);
	}

	public AppOwnerTruckOrderTemplate() {
	}

	protected AppOwnerTruckOrderTemplate(Parcel in) {
		this.id = in.readString();
		this.userId = in.readString();
		this.goodsName = in.readString();
		this.owner = in.readString();
		this.port = in.readString();
		this.address = in.readString();
		this.shipCompany = in.readString();
		this.dockInfos = in.readString();
		this.clientId = in.readString();
		this.remark = in.readString();
		this.created = in.readString();
		this.updated = in.readString();
		this.isValid = in.readString();
		this.appInfoClientPlaces = in.createTypedArrayList(AppInfoClientPlace.CREATOR);
		this.officeName = in.readString();
	}

	public static final Creator<AppOwnerTruckOrderTemplate> CREATOR = new Creator<AppOwnerTruckOrderTemplate>() {
		@Override
		public AppOwnerTruckOrderTemplate createFromParcel(Parcel source) {
			return new AppOwnerTruckOrderTemplate(source);
		}

		@Override
		public AppOwnerTruckOrderTemplate[] newArray(int size) {
			return new AppOwnerTruckOrderTemplate[size];
		}
	};
}