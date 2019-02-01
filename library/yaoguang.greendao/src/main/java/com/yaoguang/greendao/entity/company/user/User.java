package com.yaoguang.greendao.entity.company.user;




/**
 * 用户信息表
 *
 * @author heyonggang
 * @date 2016年9月23日下午1:49:12
 */
public class User {

	private String id;

	private String companyId;

	private String companyRemark;

	private String officeId;

	private String loginName;

	private String password;

	private Integer passwordStrength;

	private String workNo;

	private String name;

	private String email;

	private String phone;

	private String mobile;

	private Integer userType;

	private String photo;

	private Integer loginFlag;// Web端是否可登录

	private Integer appLoginFlag; // app端是否可登录

	private Integer bossLoginFlag;// boss端是否可登录

	private String remarks;

	private String userRemark;

	private String imToken;

	private String uuid;

	// 限制单设备登录
	private String singleToken;

	// 限制PC单设备登录
	private String userMac;

	// 限制BOSS端单设备登录
	private String bossSingleToken;

	// 是否启用MAC地址限制登录(0:否 1：是)
	private Integer isMacValid;

	// 是否需要在第一次登录修改密码
	private Integer needResetPassword;

	// 是否修改过密码
	private Integer isPasswordReset;

	//是否开通黄页（0-否，1-是）
	private Integer isopenWebsite;

	//黄页有效期
	private String websiteValidtime;

	// 是否黄页官网显示(0-否，1-是)
	private Integer isWebsiteShow;

	private String deviceToken;

	private Integer messageFlag;

	private Integer isPhoneValid;

	private String backgroundPicture;

	private String createdBy;

	private String createdDeptId;

	private String created;

	private String updatedBy;

	private String updatedDeptId;

	private String updated;

	private Integer delFlag;

	private Integer sex;

	private String qq;

	private String sign;

	// 公司名称
	private String companyName;

	// 部门名称
	private String officeName;

	//登录日志id
	private String loginId;

	private String loginTimeId;  // 登录时间限制方案ID

	private Integer isOnlyQrCodeLogin; // 只允许扫码登录

	private Integer isLoginAuthRequire; // 登录是否需要管理员授权(0:否 1:是)

	private String appLoginTimeId; // APP限制登录方案id

	private Integer isAppLoginAuthRequire; // APP登录是否需要管理员授权(0:否 1:是)

	private Integer isAppVerifyWlan; // APP是否需要验证WiFi(0:否 1:是)

	private Integer isAppVerifyLocation; // APP是否需要验证位置(0:否 1:是)

	private String appVerifyPhoneNumber; // APP指定手机卡号

	private String bossLoginTimeId; // BOSS登陆时间方案id

	private Integer bossLoginAuthRequire; // BOSS登录是否需要授权(0:否 1:是)

	private Integer isBossVerifyWlan; // BOSS是否需要验证WiFi(0:否 1:是)

	private Integer isBossVerifyLocation; // BOSS是否需要验证位置(0:否 1:是)

	private String bossVerifyPhoneNumber; // BOSS指定手机卡号

	private Integer usable; // 账户是否可用(0:否 1:是)

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCompanyId() {
		return companyId;
	}

	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}

	public String getCompanyRemark() {
		return companyRemark;
	}

	public void setCompanyRemark(String companyRemark) {
		this.companyRemark = companyRemark;
	}

	public String getOfficeId() {
		return officeId;
	}

	public void setOfficeId(String officeId) {
		this.officeId = officeId;
	}

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
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

	public String getWorkNo() {
		return workNo;
	}

	public void setWorkNo(String workNo) {
		this.workNo = workNo;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public Integer getUserType() {
		return userType;
	}

	public void setUserType(Integer userType) {
		this.userType = userType;
	}

	public String getPhoto() {
		return photo;
	}

	public void setPhoto(String photo) {
		this.photo = photo;
	}

	public Integer getLoginFlag() {
		return loginFlag;
	}

	public void setLoginFlag(Integer loginFlag) {
		this.loginFlag = loginFlag;
	}

	public Integer getAppLoginFlag() {
		return appLoginFlag;
	}

	public void setAppLoginFlag(Integer appLoginFlag) {
		this.appLoginFlag = appLoginFlag;
	}

	public Integer getBossLoginFlag() {
		return bossLoginFlag;
	}

	public void setBossLoginFlag(Integer bossLoginFlag) {
		this.bossLoginFlag = bossLoginFlag;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getUserRemark() {
		return userRemark;
	}

	public void setUserRemark(String userRemark) {
		this.userRemark = userRemark;
	}

	public String getImToken() {
		return imToken;
	}

	public void setImToken(String imToken) {
		this.imToken = imToken;
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

	public String getUserMac() {
		return userMac;
	}

	public void setUserMac(String userMac) {
		this.userMac = userMac;
	}

	public String getBossSingleToken() {
		return bossSingleToken;
	}

	public void setBossSingleToken(String bossSingleToken) {
		this.bossSingleToken = bossSingleToken;
	}

	public Integer getIsMacValid() {
		return isMacValid;
	}

	public void setIsMacValid(Integer isMacValid) {
		this.isMacValid = isMacValid;
	}

	public Integer getNeedResetPassword() {
		return needResetPassword;
	}

	public void setNeedResetPassword(Integer needResetPassword) {
		this.needResetPassword = needResetPassword;
	}

	public Integer getIsPasswordReset() {
		return isPasswordReset;
	}

	public void setIsPasswordReset(Integer isPasswordReset) {
		this.isPasswordReset = isPasswordReset;
	}

	public Integer getIsopenWebsite() {
		return isopenWebsite;
	}

	public void setIsopenWebsite(Integer isopenWebsite) {
		this.isopenWebsite = isopenWebsite;
	}

	public String getWebsiteValidtime() {
		return websiteValidtime;
	}

	public void setWebsiteValidtime(String websiteValidtime) {
		this.websiteValidtime = websiteValidtime;
	}

	public Integer getIsWebsiteShow() {
		return isWebsiteShow;
	}

	public void setIsWebsiteShow(Integer isWebsiteShow) {
		this.isWebsiteShow = isWebsiteShow;
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

	public Integer getIsPhoneValid() {
		return isPhoneValid;
	}

	public void setIsPhoneValid(Integer isPhoneValid) {
		this.isPhoneValid = isPhoneValid;
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

	public String getCreatedDeptId() {
		return createdDeptId;
	}

	public void setCreatedDeptId(String createdDeptId) {
		this.createdDeptId = createdDeptId;
	}

	public String getCreated() {
		return created;
	}

	public void setCreated(String created) {
		this.created = created;
	}

	public String getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}

	public String getUpdatedDeptId() {
		return updatedDeptId;
	}

	public void setUpdatedDeptId(String updatedDeptId) {
		this.updatedDeptId = updatedDeptId;
	}

	public String getUpdated() {
		return updated;
	}

	public void setUpdated(String updated) {
		this.updated = updated;
	}

	public Integer getDelFlag() {
		return delFlag;
	}

	public void setDelFlag(Integer delFlag) {
		this.delFlag = delFlag;
	}

	public Integer getSex() {
		return sex;
	}

	public void setSex(Integer sex) {
		this.sex = sex;
	}

	public String getQq() {
		return qq;
	}

	public void setQq(String qq) {
		this.qq = qq;
	}

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getOfficeName() {
		return officeName;
	}

	public void setOfficeName(String officeName) {
		this.officeName = officeName;
	}

	public String getLoginId() {
		return loginId;
	}

	public void setLoginId(String loginId) {
		this.loginId = loginId;
	}

	public String getLoginTimeId() {
		return loginTimeId;
	}

	public void setLoginTimeId(String loginTimeId) {
		this.loginTimeId = loginTimeId;
	}

	public Integer getIsOnlyQrCodeLogin() {
		return isOnlyQrCodeLogin;
	}

	public void setIsOnlyQrCodeLogin(Integer isOnlyQrCodeLogin) {
		this.isOnlyQrCodeLogin = isOnlyQrCodeLogin;
	}

	public Integer getIsLoginAuthRequire() {
		return isLoginAuthRequire;
	}

	public void setIsLoginAuthRequire(Integer isLoginAuthRequire) {
		this.isLoginAuthRequire = isLoginAuthRequire;
	}

	public String getAppLoginTimeId() {
		return appLoginTimeId;
	}

	public void setAppLoginTimeId(String appLoginTimeId) {
		this.appLoginTimeId = appLoginTimeId;
	}

	public Integer getIsAppLoginAuthRequire() {
		return isAppLoginAuthRequire;
	}

	public void setIsAppLoginAuthRequire(Integer isAppLoginAuthRequire) {
		this.isAppLoginAuthRequire = isAppLoginAuthRequire;
	}

	public Integer getIsAppVerifyWlan() {
		return isAppVerifyWlan;
	}

	public void setIsAppVerifyWlan(Integer isAppVerifyWlan) {
		this.isAppVerifyWlan = isAppVerifyWlan;
	}

	public Integer getIsAppVerifyLocation() {
		return isAppVerifyLocation;
	}

	public void setIsAppVerifyLocation(Integer isAppVerifyLocation) {
		this.isAppVerifyLocation = isAppVerifyLocation;
	}

	public String getAppVerifyPhoneNumber() {
		return appVerifyPhoneNumber;
	}

	public void setAppVerifyPhoneNumber(String appVerifyPhoneNumber) {
		this.appVerifyPhoneNumber = appVerifyPhoneNumber;
	}

	public String getBossLoginTimeId() {
		return bossLoginTimeId;
	}

	public void setBossLoginTimeId(String bossLoginTimeId) {
		this.bossLoginTimeId = bossLoginTimeId;
	}

	public Integer getBossLoginAuthRequire() {
		return bossLoginAuthRequire;
	}

	public void setBossLoginAuthRequire(Integer bossLoginAuthRequire) {
		this.bossLoginAuthRequire = bossLoginAuthRequire;
	}

	public Integer getIsBossVerifyWlan() {
		return isBossVerifyWlan;
	}

	public void setIsBossVerifyWlan(Integer isBossVerifyWlan) {
		this.isBossVerifyWlan = isBossVerifyWlan;
	}

	public Integer getIsBossVerifyLocation() {
		return isBossVerifyLocation;
	}

	public void setIsBossVerifyLocation(Integer isBossVerifyLocation) {
		this.isBossVerifyLocation = isBossVerifyLocation;
	}

	public String getBossVerifyPhoneNumber() {
		return bossVerifyPhoneNumber;
	}

	public void setBossVerifyPhoneNumber(String bossVerifyPhoneNumber) {
		this.bossVerifyPhoneNumber = bossVerifyPhoneNumber;
	}

	public Integer getUsable() {
		return usable;
	}

	public void setUsable(Integer usable) {
		this.usable = usable;
	}
}