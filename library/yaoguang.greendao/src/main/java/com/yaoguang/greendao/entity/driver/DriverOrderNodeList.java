package com.yaoguang.greendao.entity.driver;

import android.os.Parcel;
import android.os.Parcelable;

public class DriverOrderNodeList implements Parcelable {

	private String id;

	private String driverOrderId;

	private String orderNodeId;

	private String orderSn;

	private int number;

	private String nodeName;

	private Double lon;

	private Double lat;

	private String address;

	private String remark;

	private int isFinished;

	private int detailFlag;

	private int detailSuccess;
	
	private boolean latestFinish;

	private String createdBy;

	private String created;

	private String updated;


	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getDriverOrderId() {
		return driverOrderId;
	}

	public void setDriverOrderId(String driverOrderId) {
		this.driverOrderId = driverOrderId;
	}

	public String getOrderNodeId() {
		return orderNodeId;
	}

	public void setOrderNodeId(String orderNodeId) {
		this.orderNodeId = orderNodeId;
	}

	public String getOrderSn() {
		return orderSn;
	}

	public void setOrderSn(String orderSn) {
		this.orderSn = orderSn;
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public String getNodeName() {
		return nodeName;
	}

	public void setNodeName(String nodeName) {
		this.nodeName = nodeName;
	}

	public Double getLon() {
		return lon;
	}

	public void setLon(Double lon) {
		this.lon = lon;
	}

	public Double getLat() {
		return lat;
	}

	public void setLat(Double lat) {
		this.lat = lat;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public int getIsFinished() {
		return isFinished;
	}

	public void setIsFinished(int isFinished) {
		this.isFinished = isFinished;
	}

	public int getDetailFlag() {
		return detailFlag;
	}

	public void setDetailFlag(int detailFlag) {
		this.detailFlag = detailFlag;
	}

	public int getDetailSuccess() {
		return detailSuccess;
	}

	public void setDetailSuccess(int detailSuccess) {
		this.detailSuccess = detailSuccess;
	}

	public boolean isLatestFinish() {
		return latestFinish;
	}

	public void setLatestFinish(boolean latestFinish) {
		this.latestFinish = latestFinish;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
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

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(this.id);
		dest.writeString(this.driverOrderId);
		dest.writeString(this.orderNodeId);
		dest.writeString(this.orderSn);
		dest.writeValue(this.number);
		dest.writeString(this.nodeName);
		dest.writeValue(this.lon);
		dest.writeValue(this.lat);
		dest.writeString(this.address);
		dest.writeString(this.remark);
		dest.writeValue(this.isFinished);
		dest.writeValue(this.detailFlag);
		dest.writeValue(this.detailSuccess);
		dest.writeByte(this.latestFinish ? (byte) 1 : (byte) 0);
		dest.writeString(this.createdBy);
		dest.writeString(this.created);
		dest.writeString(this.updated);
	}

	public DriverOrderNodeList() {
	}

	protected DriverOrderNodeList(Parcel in) {
		this.id = in.readString();
		this.driverOrderId = in.readString();
		this.orderNodeId = in.readString();
		this.orderSn = in.readString();
		this.number = (int) in.readValue(int.class.getClassLoader());
		this.nodeName = in.readString();
		this.lon = (Double) in.readValue(Double.class.getClassLoader());
		this.lat = (Double) in.readValue(Double.class.getClassLoader());
		this.address = in.readString();
		this.remark = in.readString();
		this.isFinished = (int) in.readValue(int.class.getClassLoader());
		this.detailFlag = (int) in.readValue(int.class.getClassLoader());
		this.detailSuccess = (int) in.readValue(int.class.getClassLoader());
		this.latestFinish = in.readByte() != 0;
		this.createdBy = in.readString();
		this.created = in.readString();
		this.updated = in.readString();
	}

	public static final Parcelable.Creator<DriverOrderNodeList> CREATOR = new Parcelable.Creator<DriverOrderNodeList>() {
		@Override
		public DriverOrderNodeList createFromParcel(Parcel source) {
			return new DriverOrderNodeList(source);
		}

		@Override
		public DriverOrderNodeList[] newArray(int size) {
			return new DriverOrderNodeList[size];
		}
	};
}