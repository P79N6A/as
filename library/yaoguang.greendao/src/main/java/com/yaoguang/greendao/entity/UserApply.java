package com.yaoguang.greendao.entity;


import android.os.Parcel;
import android.os.Parcelable;

/**
 * 企业用户注册信息
 * 
 * @author ZhangDeQuan
 * @time 2017年6月12日 下午2:20:58
 */
public class UserApply implements Parcelable {


	private String id;

	private Integer resource;
	// 0 货代 1 拖车，2货代拖车
	private Integer applyType;
	
	//注册手机
	private String phone;
	
	//注册密码
	private String password;
	
	private String companyName;

	private String province;

	private String city;

	private String district;

	private String companyAddress;

	private String name;

	private String mobile;

	private String shopLogo;

	private String shopPhoto;

	private String shopDetail;

	private String companyCode;

	private String licensePhoto;

	private Integer applyStatus;

	private String refuseRemark;

	private String userId;

	// 申请通过后创建的登录名
	private String loginName;

	private String remark;

	private String createdBy;

	private String createdDeptId;

	private String updatedBy;

	private String created;

	private String updatedDeptId;

	private String updated;

	private Integer isValid;

	public String getDeviceToken() {
		return deviceToken;
	}

	public void setDeviceToken(String deviceToken) {
		this.deviceToken = deviceToken;
	}

	// 设备token
	private String deviceToken;

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id == null ? null : id.trim();
	}

	public Integer getResource() {
		return resource;
	}

	public void setResource(Integer resource) {
		this.resource = resource;
	}

	public Integer getApplyType() {
		return applyType;
	}

	public void setApplyType(Integer applyType) {
		this.applyType = applyType;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName == null ? null : companyName.trim();
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province == null ? null : province.trim();
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city == null ? null : city.trim();
	}

	public String getDistrict() {
		return district;
	}

	public void setDistrict(String district) {
		this.district = district == null ? null : district.trim();
	}

	public String getCompanyAddress() {
		return companyAddress;
	}

	public void setCompanyAddress(String companyAddress) {
		this.companyAddress = companyAddress == null ? null : companyAddress.trim();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name == null ? null : name.trim();
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile == null ? null : mobile.trim();
	}

	public String getShopLogo() {
		return shopLogo;
	}

	public void setShopLogo(String shopLogo) {
		this.shopLogo = shopLogo == null ? null : shopLogo.trim();
	}

	public String getShopPhoto() {
		return shopPhoto;
	}

	public void setShopPhoto(String shopPhoto) {
		this.shopPhoto = shopPhoto == null ? null : shopPhoto.trim();
	}

	public String getShopDetail() {
		return shopDetail;
	}

	public void setShopDetail(String shopDetail) {
		this.shopDetail = shopDetail == null ? null : shopDetail.trim();
	}

	public String getCompanyCode() {
		return companyCode;
	}

	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode == null ? null : companyCode.trim();
	}

	public String getLicensePhoto() {
		return licensePhoto;
	}

	public void setLicensePhoto(String licensePhoto) {
		this.licensePhoto = licensePhoto == null ? null : licensePhoto.trim();
	}

	public Integer getApplyStatus() {
		return applyStatus;
	}

	public void setApplyStatus(Integer applyStatus) {
		this.applyStatus = applyStatus;
	}

	public String getRefuseRemark() {
		return refuseRemark;
	}

	public void setRefuseRemark(String refuseRemark) {
		this.refuseRemark = refuseRemark == null ? null : refuseRemark.trim();
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId == null ? null : userId.trim();
	}

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark == null ? null : remark.trim();
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

	public String getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy == null ? null : updatedBy.trim();
	}

	public String getCreated() {
		return created;
	}

	public void setCreated(String created) {
		this.created = created;
	}

	public String getUpdatedDeptId() {
		return updatedDeptId;
	}

	public void setUpdatedDeptId(String updatedDeptId) {
		this.updatedDeptId = updatedDeptId == null ? null : updatedDeptId.trim();
	}

	public String getUpdated() {
		return updated;
	}

	public void setUpdated(String updated) {
		this.updated = updated;
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
		dest.writeValue(this.resource);
		dest.writeValue(this.applyType);
		dest.writeString(this.phone);
		dest.writeString(this.password);
		dest.writeString(this.companyName);
		dest.writeString(this.province);
		dest.writeString(this.city);
		dest.writeString(this.district);
		dest.writeString(this.companyAddress);
		dest.writeString(this.name);
		dest.writeString(this.mobile);
		dest.writeString(this.shopLogo);
		dest.writeString(this.shopPhoto);
		dest.writeString(this.shopDetail);
		dest.writeString(this.companyCode);
		dest.writeString(this.licensePhoto);
		dest.writeValue(this.applyStatus);
		dest.writeString(this.refuseRemark);
		dest.writeString(this.userId);
		dest.writeString(this.loginName);
		dest.writeString(this.remark);
		dest.writeString(this.createdBy);
		dest.writeString(this.createdDeptId);
		dest.writeString(this.updatedBy);
		dest.writeString(this.created);
		dest.writeString(this.updatedDeptId);
		dest.writeString(this.updated);
		dest.writeValue(this.isValid);
		dest.writeString(this.deviceToken);
	}

	public UserApply() {
	}

	protected UserApply(Parcel in) {
		this.id = in.readString();
		this.resource = (Integer) in.readValue(Integer.class.getClassLoader());
		this.applyType = (Integer) in.readValue(Integer.class.getClassLoader());
		this.phone = in.readString();
		this.password = in.readString();
		this.companyName = in.readString();
		this.province = in.readString();
		this.city = in.readString();
		this.district = in.readString();
		this.companyAddress = in.readString();
		this.name = in.readString();
		this.mobile = in.readString();
		this.shopLogo = in.readString();
		this.shopPhoto = in.readString();
		this.shopDetail = in.readString();
		this.companyCode = in.readString();
		this.licensePhoto = in.readString();
		this.applyStatus = (Integer) in.readValue(Integer.class.getClassLoader());
		this.refuseRemark = in.readString();
		this.userId = in.readString();
		this.loginName = in.readString();
		this.remark = in.readString();
		this.createdBy = in.readString();
		this.createdDeptId = in.readString();
		this.updatedBy = in.readString();
		this.created = in.readString();
		this.updatedDeptId = in.readString();
		this.updated = in.readString();
		this.isValid = (Integer) in.readValue(Integer.class.getClassLoader());
		this.deviceToken = in.readString();
	}

	public static final Parcelable.Creator<UserApply> CREATOR = new Parcelable.Creator<UserApply>() {
		@Override
		public UserApply createFromParcel(Parcel source) {
			return new UserApply(source);
		}

		@Override
		public UserApply[] newArray(int size) {
			return new UserApply[size];
		}
	};
}