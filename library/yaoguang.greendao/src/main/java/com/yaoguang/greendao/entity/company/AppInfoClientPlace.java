package com.yaoguang.greendao.entity.company;


import android.os.Parcel;
import android.os.Parcelable;

/**
 * App装卸货地址包装类
 * 
 * @author wangxiaohui
 * @date 2017年9月25日 下午2:32:20
 */
public class AppInfoClientPlace implements Parcelable {

	private String id;

	//联系人
	private String linkman;

	//联系人手机
	private String linkmanMp;

	//联系人电话
	private String linkmanTel;

	//行政区划
	private String regionid;

	//装卸地址
	private String address;

	// 备注
	private String remark;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getLinkman() {
		return linkman;
	}

	public void setLinkman(String linkman) {
		this.linkman = linkman;
	}

	public String getLinkmanMp() {
		return linkmanMp;
	}

	public void setLinkmanMp(String linkmanMp) {
		this.linkmanMp = linkmanMp;
	}

	public String getLinkmanTel() {
		return linkmanTel;
	}

	public void setLinkmanTel(String linkmanTel) {
		this.linkmanTel = linkmanTel;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getRegionid() {
		return regionid;
	}

	public void setRegionid(String regionid) {
		this.regionid = regionid;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(this.id);
		dest.writeString(this.linkman);
		dest.writeString(this.linkmanMp);
		dest.writeString(this.linkmanTel);
		dest.writeString(this.regionid);
		dest.writeString(this.address);
		dest.writeString(this.remark);
	}

	public AppInfoClientPlace() {
	}

	protected AppInfoClientPlace(Parcel in) {
		this.id = in.readString();
		this.linkman = in.readString();
		this.linkmanMp = in.readString();
		this.linkmanTel = in.readString();
		this.regionid = in.readString();
		this.address = in.readString();
		this.remark = in.readString();
	}

	public static final Creator<AppInfoClientPlace> CREATOR = new Creator<AppInfoClientPlace>() {
		@Override
		public AppInfoClientPlace createFromParcel(Parcel source) {
			return new AppInfoClientPlace(source);
		}

		@Override
		public AppInfoClientPlace[] newArray(int size) {
			return new AppInfoClientPlace[size];
		}
	};
}
