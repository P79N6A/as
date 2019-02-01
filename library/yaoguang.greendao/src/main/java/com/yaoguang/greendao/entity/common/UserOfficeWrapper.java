package com.yaoguang.greendao.entity.common;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 移动端查询结构信息包装类
 * 
 * @author heyonggang
 * @date 2017年4月25日下午4:28:54
 */
public class UserOfficeWrapper implements Parcelable {


	private String id;



	private String userId;

	// 名称
	private String name;
	// 企业表示
	private String companyRemark;
	// 简称
	private String shortName;
	// 公司抬头
	private String companyCode;

	private String zipCode;

	private String master;

	private String fax;

	private String email;
	
	private String address;
	
	private String phone;

	// 以下为userapply中的属性
	// 简介
	private String shopDetail;
	// 服务范围
	private Integer applyType;
	// 店铺照片
	private String photo;
	
	// 店铺地址
	private String shopAddress;
	
	// 联系人
	private String linkMan;
	
	// 联系电话
	private String linkPhone;

	// logo
	private String shopLogo;

	// 关注表主键id
	private String contactId;

	// 关注类型 2可发起申请信息 1显示取消关注 3显示同意或者拒绝
	private Integer status;

	public String getShopAddress() {
		return shopAddress;
	}

	public void setShopAddress(String shopAddress) {
		this.shopAddress = shopAddress;
	}

	public String getLinkMan() {
		return linkMan;
	}

	public void setLinkMan(String linkMan) {
		this.linkMan = linkMan;
	}

	public String getLinkPhone() {
		return linkPhone;
	}

	public void setLinkPhone(String linkPhone) {
		this.linkPhone = linkPhone;
	}

	public String getShopDetail() {
		return shopDetail;
	}

	public void setShopDetail(String shopDetail) {
		this.shopDetail = shopDetail;
	}

	public Integer getApplyType() {
		return applyType;
	}

	public void setApplyType(Integer applyType) {
		this.applyType = applyType;
	}

	public String getContactId() {
		return contactId;
	}

	public void setContactId(String contactId) {
		this.contactId = contactId;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getShopLogo() {
		return shopLogo;
	}

	public void setShopLogo(String shopLogo) {
		this.shopLogo = shopLogo;
	}

	public String getPhoto() {
		return photo;
	}

	public void setPhoto(String photo) {
		this.photo = photo;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getZipCode() {
		return zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	public String getMaster() {
		return master;
	}

	public void setMaster(String master) {
		this.master = master;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getFax() {
		return fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCompanyRemark() {
		return companyRemark;
	}

	public void setCompanyRemark(String companyRemark) {
		this.companyRemark = companyRemark;
	}

	public String getShortName() {
		return shortName;
	}

	public void setShortName(String shortName) {
		this.shortName = shortName;
	}

	public String getCompanyCode() {
		return companyCode;
	}

	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(this.id);
		dest.writeString(this.userId);
		dest.writeString(this.name);
		dest.writeString(this.companyRemark);
		dest.writeString(this.shortName);
		dest.writeString(this.companyCode);
		dest.writeString(this.zipCode);
		dest.writeString(this.master);
		dest.writeString(this.fax);
		dest.writeString(this.email);
		dest.writeString(this.address);
		dest.writeString(this.phone);
		dest.writeString(this.shopDetail);
		dest.writeValue(this.applyType);
		dest.writeString(this.photo);
		dest.writeString(this.shopAddress);
		dest.writeString(this.linkMan);
		dest.writeString(this.linkPhone);
		dest.writeString(this.shopLogo);
		dest.writeString(this.contactId);
		dest.writeValue(this.status);
	}

	public UserOfficeWrapper() {
	}

	protected UserOfficeWrapper(Parcel in) {
		this.id = in.readString();
		this.userId = in.readString();
		this.name = in.readString();
		this.companyRemark = in.readString();
		this.shortName = in.readString();
		this.companyCode = in.readString();
		this.zipCode = in.readString();
		this.master = in.readString();
		this.fax = in.readString();
		this.email = in.readString();
		this.address = in.readString();
		this.phone = in.readString();
		this.shopDetail = in.readString();
		this.applyType = (Integer) in.readValue(Integer.class.getClassLoader());
		this.photo = in.readString();
		this.shopAddress = in.readString();
		this.linkMan = in.readString();
		this.linkPhone = in.readString();
		this.shopLogo = in.readString();
		this.contactId = in.readString();
		this.status = (Integer) in.readValue(Integer.class.getClassLoader());
	}

	public static final Creator<UserOfficeWrapper> CREATOR = new Creator<UserOfficeWrapper>() {
		@Override
		public UserOfficeWrapper createFromParcel(Parcel source) {
			return new UserOfficeWrapper(source);
		}

		@Override
		public UserOfficeWrapper[] newArray(int size) {
			return new UserOfficeWrapper[size];
		}
	};
}
