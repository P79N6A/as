package com.yaoguang.greendao.entity;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;

import java.io.Serializable;


/**
 * 基础资料-放单地点
 * 
 * @author ZhangDeQuan
 * @time 2017年12月28日 上午9:40:06
 */
public class InfoPutorderPlace implements Parcelable {

	private String id;

	private String name;

	private String address;

	private String phone;

	private String longitude;

	private String latitude;

	private String clientId;

	private String flag;

	private String isdelete;

	private String createdBy;

	private String createdDeptId;

	private String created;

	private String upStringdBy;

	private String upStringdDeptId;

	private String upStringd;

	public boolean isSelect;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id == null ? null : id.trim();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name == null ? null : name.trim();
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address == null ? null : address.trim();
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone == null ? null : phone.trim();
	}

	public double getLongitude() {
		if (TextUtils.isEmpty(longitude)) {
			return 0;
		}
		return Double.parseDouble(longitude);
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	public double getLatitude() {
		if (TextUtils.isEmpty(latitude)) {
			return 0;
		}
		return Double.parseDouble(latitude);
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	public String getClientId() {
		return clientId;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId == null ? null : clientId.trim();
	}

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	public String getIsdelete() {
		return isdelete;
	}

	public void setIsdelete(String isdelete) {
		this.isdelete = isdelete;
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

	public String getUpStringdBy() {
		return upStringdBy;
	}

	public void setUpStringdBy(String upStringdBy) {
		this.upStringdBy = upStringdBy == null ? null : upStringdBy.trim();
	}

	public String getUpStringdDeptId() {
		return upStringdDeptId;
	}

	public void setUpStringdDeptId(String upStringdDeptId) {
		this.upStringdDeptId = upStringdDeptId == null ? null : upStringdDeptId.trim();
	}

	public String getUpStringd() {
		return upStringd;
	}

	public void setUpStringd(String upStringd) {
		this.upStringd = upStringd;
	}

	public boolean isSelect() {
		return isSelect;
	}

	public void setSelect(boolean select) {
		isSelect = select;
	}


	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(this.id);
		dest.writeString(this.name);
		dest.writeString(this.address);
		dest.writeString(this.phone);
		dest.writeString(this.longitude);
		dest.writeString(this.latitude);
		dest.writeString(this.clientId);
		dest.writeString(this.flag);
		dest.writeString(this.isdelete);
		dest.writeString(this.createdBy);
		dest.writeString(this.createdDeptId);
		dest.writeString(this.created);
		dest.writeString(this.upStringdBy);
		dest.writeString(this.upStringdDeptId);
		dest.writeString(this.upStringd);
		dest.writeByte(this.isSelect ? (byte) 1 : (byte) 0);
	}

	public InfoPutorderPlace() {
	}

	protected InfoPutorderPlace(Parcel in) {
		this.id = in.readString();
		this.name = in.readString();
		this.address = in.readString();
		this.phone = in.readString();
		this.longitude = in.readString();
		this.latitude = in.readString();
		this.clientId = in.readString();
		this.flag = in.readString();
		this.isdelete = in.readString();
		this.createdBy = in.readString();
		this.createdDeptId = in.readString();
		this.created = in.readString();
		this.upStringdBy = in.readString();
		this.upStringdDeptId = in.readString();
		this.upStringd = in.readString();
		this.isSelect = in.readByte() != 0;
	}

	public static final Creator<InfoPutorderPlace> CREATOR = new Creator<InfoPutorderPlace>() {
		@Override
		public InfoPutorderPlace createFromParcel(Parcel source) {
			return new InfoPutorderPlace(source);
		}

		@Override
		public InfoPutorderPlace[] newArray(int size) {
			return new InfoPutorderPlace[size];
		}
	};
}