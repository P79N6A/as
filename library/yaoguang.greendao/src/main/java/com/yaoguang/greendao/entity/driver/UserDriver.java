package com.yaoguang.greendao.entity.driver;

import java.io.Serializable;
import java.util.Date;

/**
 * 司机用户信息
 *
 * @author liyangbin
 *
 * @date 2017年6月13日上午11:22:25
 */
public class UserDriver implements Serializable {

	private static final long serialVersionUID = 40778384302132013L;

	private String id;

	// 昵称
	private String nickName;

	// 手机号
	private String mobile;

	// 密码
	private String password;

	// 密码强度(0：弱 1：中 2：强)
	private Integer passwordStrength;

	// 用户头像
	private String photo;

	// 家乡省
	private String nativePlaceProvince;

	// 家乡市
	private String nativePlaceCity;

	// 家乡区
	private String nativePlaceDistrict;

	// 所在城市(省)
	private String placeProvince;

	// 所在城市(市)
	private String placeCity;

	// 所在城市(区)
	private String placeDistrict;

	// QQ
	private String qq;

	// 邮箱
	private String email;

	// 签名
	private String sign;

	// 服务地区
	private String serviceArea;

	// 车头牌号（省）
	private String carProvince;

	// 车头牌号
	private String carNumber;

	// 车头所有人
	private String carOwner;

	// 车头注册日期
	private Date carRegisterDate;

	// 车头行驶证正副页
	private String carLicencePhoto;

	// 挂车牌号（省）
	private String trailerProvince;

	// 挂车牌号
	private String trailerNumber;

	// 挂车所有人
	private String trailerOwner;

	// 挂车注册日期
	private Date trailerRegisterDate;

	// 挂车行驶证正副页
	private String trailerPhoto;

	// 司机姓名
	private String driverName;

	// 身份证号
	private String idNumber;

	// 身份证正反面
	private String idPhoto;

	// 身份证签发机关
	private String idAuthority;

	// 身份证有效期开始时间
	private Date idStartDate;

	// 身份证有效期结束时间
	private Date idEndDate;

	// 身份证人像面
	private String idPhoto1;

	// 身份证国徽面
	private String idPhoto2;

	// 身份证认证申请时间
	private Date idApplyTime;

	// 身份证认证审核时间
	private Date idAuditTime;

	// 身份证审核状态
	private Integer idAuditStatus;

	// 驾驶证有效期
	private Date licenceValidDate;

	// 司机驾驶证正副页
	private String driverLicencePhoto;

	// 司机工作状态（0:暂停接单 1：接单 ）
	private Integer status;

	// 司机工作状态说明id
	private String statusDetailId;

	// 司机资料状态（0：待审核 1：拒绝 2：通过）
	private Integer loginFlag;

	// 审核备注
	private String auditRemark;

	// 备注信息
	private String remark;

	private String uuid;

	//限制单设备登录
	private String singleToken;

	// 设备token
	private String deviceToken;

	// 是否允许推送消息(0：不可以 1：可以)
	private Integer messageFlag;

	// 用户背景图片
	private String backgroundPicture;

	// 记录创建人
	private String createdBy;

	// 记录创建时间
	private Date created;

	// 记录修改时间
	private Date updated;

	// 是否有效(0:无效 1：有效)
	private Integer isValid;
	
	// 融云即时通讯token
	private String rongyunToken;

	public String getRongyunToken() {
		return rongyunToken;
	}

	public void setRongyunToken(String rongyunToken) {
		this.rongyunToken = rongyunToken;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Integer getPasswordStrength() {
		return passwordStrength;
	}

	public void setPasswordStrength(Integer passwordStrength) {
		this.passwordStrength = passwordStrength;
	}

	public String getPhoto() {
		return photo;
	}

	public void setPhoto(String photo) {
		this.photo = photo;
	}

	public String getNativePlaceProvince() {
		return nativePlaceProvince;
	}

	public void setNativePlaceProvince(String nativePlaceProvince) {
		this.nativePlaceProvince = nativePlaceProvince;
	}

	public String getNativePlaceCity() {
		return nativePlaceCity;
	}

	public void setNativePlaceCity(String nativePlaceCity) {
		this.nativePlaceCity = nativePlaceCity;
	}

	public String getNativePlaceDistrict() {
		return nativePlaceDistrict;
	}

	public void setNativePlaceDistrict(String nativePlaceDistrict) {
		this.nativePlaceDistrict = nativePlaceDistrict;
	}

	public String getPlaceProvince() {
		return placeProvince;
	}

	public void setPlaceProvince(String placeProvince) {
		this.placeProvince = placeProvince;
	}

	public String getPlaceCity() {
		return placeCity;
	}

	public void setPlaceCity(String placeCity) {
		this.placeCity = placeCity;
	}

	public String getPlaceDistrict() {
		return placeDistrict;
	}

	public void setPlaceDistrict(String placeDistrict) {
		this.placeDistrict = placeDistrict;
	}

	public String getQq() {
		return qq;
	}

	public void setQq(String qq) {
		this.qq = qq;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}

	public String getServiceArea() {
		return serviceArea;
	}

	public void setServiceArea(String serviceArea) {
		this.serviceArea = serviceArea;
	}

	public String getCarProvince() {
		return carProvince;
	}

	public void setCarProvince(String carProvince) {
		this.carProvince = carProvince;
	}

	public String getCarNumber() {
		return carNumber;
	}

	public void setCarNumber(String carNumber) {
		this.carNumber = carNumber;
	}

	public String getCarOwner() {
		return carOwner;
	}

	public void setCarOwner(String carOwner) {
		this.carOwner = carOwner;
	}

	public Date getCarRegisterDate() {
		return carRegisterDate;
	}

	public void setCarRegisterDate(Date carRegisterDate) {
		this.carRegisterDate = carRegisterDate;
	}

	public String getCarLicencePhoto() {
		return carLicencePhoto;
	}

	public void setCarLicencePhoto(String carLicencePhoto) {
		this.carLicencePhoto = carLicencePhoto;
	}

	public String getTrailerProvince() {
		return trailerProvince;
	}

	public void setTrailerProvince(String trailerProvince) {
		this.trailerProvince = trailerProvince;
	}

	public String getTrailerNumber() {
		return trailerNumber;
	}

	public void setTrailerNumber(String trailerNumber) {
		this.trailerNumber = trailerNumber;
	}

	public String getTrailerOwner() {
		return trailerOwner;
	}

	public void setTrailerOwner(String trailerOwner) {
		this.trailerOwner = trailerOwner;
	}

	public Date getTrailerRegisterDate() {
		return trailerRegisterDate;
	}

	public void setTrailerRegisterDate(Date trailerRegisterDate) {
		this.trailerRegisterDate = trailerRegisterDate;
	}

	public String getTrailerPhoto() {
		return trailerPhoto;
	}

	public void setTrailerPhoto(String trailerPhoto) {
		this.trailerPhoto = trailerPhoto;
	}

	public String getDriverName() {
		return driverName;
	}

	public void setDriverName(String driverName) {
		this.driverName = driverName;
	}

	public String getIdNumber() {
		return idNumber;
	}

	public void setIdNumber(String idNumber) {
		this.idNumber = idNumber;
	}

	public String getIdPhoto() {
		return idPhoto;
	}

	public void setIdPhoto(String idPhoto) {
		this.idPhoto = idPhoto;
	}

	public String getIdAuthority() {
		return idAuthority;
	}

	public void setIdAuthority(String idAuthority) {
		this.idAuthority = idAuthority;
	}

	public Date getIdStartDate() {
		return idStartDate;
	}

	public void setIdStartDate(Date idStartDate) {
		this.idStartDate = idStartDate;
	}

	public Date getIdEndDate() {
		return idEndDate;
	}

	public void setIdEndDate(Date idEndDate) {
		this.idEndDate = idEndDate;
	}

	public String getIdPhoto1() {
		return idPhoto1;
	}

	public void setIdPhoto1(String idPhoto1) {
		this.idPhoto1 = idPhoto1;
	}

	public String getIdPhoto2() {
		return idPhoto2;
	}

	public void setIdPhoto2(String idPhoto2) {
		this.idPhoto2 = idPhoto2;
	}

	public Date getIdApplyTime() {
		return idApplyTime;
	}

	public void setIdApplyTime(Date idApplyTime) {
		this.idApplyTime = idApplyTime;
	}

	public Date getIdAuditTime() {
		return idAuditTime;
	}

	public void setIdAuditTime(Date idAuditTime) {
		this.idAuditTime = idAuditTime;
	}

	public Integer getIdAuditStatus() {
		return idAuditStatus;
	}

	public void setIdAuditStatus(Integer idAuditStatus) {
		this.idAuditStatus = idAuditStatus;
	}

	public Date getLicenceValidDate() {
		return licenceValidDate;
	}

	public void setLicenceValidDate(Date licenceValidDate) {
		this.licenceValidDate = licenceValidDate;
	}

	public String getDriverLicencePhoto() {
		return driverLicencePhoto;
	}

	public void setDriverLicencePhoto(String driverLicencePhoto) {
		this.driverLicencePhoto = driverLicencePhoto;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getStatusDetailId() {
		return statusDetailId;
	}

	public void setStatusDetailId(String statusDetailId) {
		this.statusDetailId = statusDetailId;
	}

	public Integer getLoginFlag() {
		return loginFlag;
	}

	public void setLoginFlag(Integer loginFlag) {
		this.loginFlag = loginFlag;
	}

	public String getAuditRemark() {
		return auditRemark;
	}

	public void setAuditRemark(String auditRemark) {
		this.auditRemark = auditRemark;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getSingleToken() {
		return singleToken;
	}

	public void setSingleToken(String singleToken) {
		this.singleToken = singleToken;
	}

	public String getDeviceToken() {
		return deviceToken;
	}

	public void setDeviceToken(String deviceToken) {
		this.deviceToken = deviceToken;
	}

	public Integer getMessageFlag() {
		return messageFlag;
	}

	public void setMessageFlag(Integer messageFlag) {
		this.messageFlag = messageFlag;
	}

	public String getBackgroundPicture() {
		return backgroundPicture;
	}

	public void setBackgroundPicture(String backgroundPicture) {
		this.backgroundPicture = backgroundPicture;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	public Date getUpdated() {
		return updated;
	}

	public void setUpdated(Date updated) {
		this.updated = updated;
	}

	public Integer getIsValid() {
		return isValid;
	}

	public void setIsValid(Integer isValid) {
		this.isValid = isValid;
	}
}