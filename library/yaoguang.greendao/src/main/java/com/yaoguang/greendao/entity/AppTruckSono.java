package com.yaoguang.greendao.entity;


import android.os.Parcel;
import android.os.Parcelable;

/**
 * App拖车业货柜包装对象
 * 
 * @author wangxiaohui
 * @date 2017年9月25日 下午2:32:20
 */
public class AppTruckSono implements Parcelable {
	// 柜型
	private String contId;
	// 柜号
	private String contNo;
	// 封号
	private String sealNo;
	// 提柜码头
	private String carryPort;
	// 还柜码头
	private String getPort;
	public String getContId() {
		return contId;
	}
	public void setContId(String contId) {
		this.contId = contId;
	}
	public String getContNo() {
		return contNo;
	}
	public void setContNo(String contNo) {
		this.contNo = contNo;
	}
	public String getSealNo() {
		return sealNo;
	}
	public void setSealNo(String sealNo) {
		this.sealNo = sealNo;
	}
	public String getCarryPort() {
		return carryPort;
	}
	public void setCarryPort(String carryPort) {
		this.carryPort = carryPort;
	}
	public String getGetPort() {
		return getPort;
	}
	public void setGetPort(String getPort) {
		this.getPort = getPort;
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(this.contId);
		dest.writeString(this.contNo);
		dest.writeString(this.sealNo);
		dest.writeString(this.carryPort);
		dest.writeString(this.getPort);
	}

	public AppTruckSono() {
	}

	protected AppTruckSono(Parcel in) {
		this.contId = in.readString();
		this.contNo = in.readString();
		this.sealNo = in.readString();
		this.carryPort = in.readString();
		this.getPort = in.readString();
	}

	public static final Parcelable.Creator<AppTruckSono> CREATOR = new Parcelable.Creator<AppTruckSono>() {
		@Override
		public AppTruckSono createFromParcel(Parcel source) {
			return new AppTruckSono(source);
		}

		@Override
		public AppTruckSono[] newArray(int size) {
			return new AppTruckSono[size];
		}
	};
}