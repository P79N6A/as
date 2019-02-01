package com.yaoguang.greendao.entity.company.user;


/**
 * 用户信息展示实体
 *
 * @author ZhangDeQuan
 * @time 2016年9月30日 上午9:33:39
 */
public class ViewUser {
	private String id;
	// 总公司id
	private String companyId;

	private String companyRemark;

	private String officeId;

	private String loginName; // 登录名

	private String password;

	private Integer passwordStrength;

	private String workNo;

	private String name; // 姓名

	private String email;

	private String phone;

	private String mobile;

	private Integer userType; // 用户类型(0:总公司用户 1:普通用户 2:管理员)

	private String photo;

	private Integer loginFlag;

	private Integer appLoginFlag;

	private String loginTimeId;

	private String remarks;

	private String userRemark;

	private String createdBy;

	private String createdDeptId;

	private String created;

	private String updatedBy;

	private String updatedDeptId;

	private String updated;

	private Integer delFlag;

	private Integer bossLoginFlag;

	private Integer isPhoneValid;

	private String roleName;  // 角色名称（英文逗号分隔）

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

	// 归属公司名称
	private String companyName;

	// 归属公司父级编号
	private String companyParentId;

	// 归属公司所有父级编号
	private String companyAllParentIds;

	// 归属公司区域编号
	private String companyAreaId;

	// 归属公司区域名称
	private String companyAreaName;

	// 归属公司区域父级编号
	private String companyAreaParentId;

	// 归属公司区域所有父级编号
	private String companyAreaAllParentIds;

	// 归属部门名称
	private String officeName;

	// 归属部门父级编号
	private String officeParentId;

	// 归属部门所有父级编号
	private String officeAllParentIds;

	// 归属部门区域编号
	private String officeAreaId;

	// 归属部门区域名称
	private String officeAreaName;

	// 归属部门区域父级编号
	private String officeAreaParentId;

	// 归属部门所有父级编号
	private String officeAreaAllParentIds;

	//登录方案名称
	private String loginTimeName;

	//企业机构ID
	private String enterpriseId;

	//企业机构简称
	private String enterpriseShortName;

	//企业机构抬头
	private String enterpriseTitle;

	//企业机构负责人
	private String enterpriseMaster;

	//企业机构邮箱
	private String enterpriseEmail;

	//企业机构联系地址
	private String enterpriseAddress;

	//企业机构电话
	private String enterprisePhone;

	//企业机构传真
	private String enterpriseFax;

	//企业机构备注
	private String enterpriseRemarks;

	//企业已开通短信账号
	private String smsUsername;

	//企业已开通短信密码
	private String smsPassword;

	private Integer usable; // 是否可用(0:否 1:是)

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

	public String getLoginTimeId() {
		return loginTimeId;
	}

	public void setLoginTimeId(String loginTimeId) {
		this.loginTimeId = loginTimeId;
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

	public Integer getBossLoginFlag() {
		return bossLoginFlag;
	}

	public void setBossLoginFlag(Integer bossLoginFlag) {
		this.bossLoginFlag = bossLoginFlag;
	}

	public Integer getIsPhoneValid() {
		return isPhoneValid;
	}

	public void setIsPhoneValid(Integer isPhoneValid) {
		this.isPhoneValid = isPhoneValid;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
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

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getCompanyParentId() {
		return companyParentId;
	}

	public void setCompanyParentId(String companyParentId) {
		this.companyParentId = companyParentId;
	}

	public String getCompanyAllParentIds() {
		return companyAllParentIds;
	}

	public void setCompanyAllParentIds(String companyAllParentIds) {
		this.companyAllParentIds = companyAllParentIds;
	}

	public String getCompanyAreaId() {
		return companyAreaId;
	}

	public void setCompanyAreaId(String companyAreaId) {
		this.companyAreaId = companyAreaId;
	}

	public String getCompanyAreaName() {
		return companyAreaName;
	}

	public void setCompanyAreaName(String companyAreaName) {
		this.companyAreaName = companyAreaName;
	}

	public String getCompanyAreaParentId() {
		return companyAreaParentId;
	}

	public void setCompanyAreaParentId(String companyAreaParentId) {
		this.companyAreaParentId = companyAreaParentId;
	}

	public String getCompanyAreaAllParentIds() {
		return companyAreaAllParentIds;
	}

	public void setCompanyAreaAllParentIds(String companyAreaAllParentIds) {
		this.companyAreaAllParentIds = companyAreaAllParentIds;
	}

	public String getOfficeName() {
		return officeName;
	}

	public void setOfficeName(String officeName) {
		this.officeName = officeName;
	}

	public String getOfficeParentId() {
		return officeParentId;
	}

	public void setOfficeParentId(String officeParentId) {
		this.officeParentId = officeParentId;
	}

	public String getOfficeAllParentIds() {
		return officeAllParentIds;
	}

	public void setOfficeAllParentIds(String officeAllParentIds) {
		this.officeAllParentIds = officeAllParentIds;
	}

	public String getOfficeAreaId() {
		return officeAreaId;
	}

	public void setOfficeAreaId(String officeAreaId) {
		this.officeAreaId = officeAreaId;
	}

	public String getOfficeAreaName() {
		return officeAreaName;
	}

	public void setOfficeAreaName(String officeAreaName) {
		this.officeAreaName = officeAreaName;
	}

	public String getOfficeAreaParentId() {
		return officeAreaParentId;
	}

	public void setOfficeAreaParentId(String officeAreaParentId) {
		this.officeAreaParentId = officeAreaParentId;
	}

	public String getOfficeAreaAllParentIds() {
		return officeAreaAllParentIds;
	}

	public void setOfficeAreaAllParentIds(String officeAreaAllParentIds) {
		this.officeAreaAllParentIds = officeAreaAllParentIds;
	}

	public String getLoginTimeName() {
		return loginTimeName;
	}

	public void setLoginTimeName(String loginTimeName) {
		this.loginTimeName = loginTimeName;
	}

	public String getEnterpriseId() {
		return enterpriseId;
	}

	public void setEnterpriseId(String enterpriseId) {
		this.enterpriseId = enterpriseId;
	}

	public String getEnterpriseShortName() {
		return enterpriseShortName;
	}

	public void setEnterpriseShortName(String enterpriseShortName) {
		this.enterpriseShortName = enterpriseShortName;
	}

	public String getEnterpriseTitle() {
		return enterpriseTitle;
	}

	public void setEnterpriseTitle(String enterpriseTitle) {
		this.enterpriseTitle = enterpriseTitle;
	}

	public String getEnterpriseMaster() {
		return enterpriseMaster;
	}

	public void setEnterpriseMaster(String enterpriseMaster) {
		this.enterpriseMaster = enterpriseMaster;
	}

	public String getEnterpriseEmail() {
		return enterpriseEmail;
	}

	public void setEnterpriseEmail(String enterpriseEmail) {
		this.enterpriseEmail = enterpriseEmail;
	}

	public String getEnterpriseAddress() {
		return enterpriseAddress;
	}

	public void setEnterpriseAddress(String enterpriseAddress) {
		this.enterpriseAddress = enterpriseAddress;
	}

	public String getEnterprisePhone() {
		return enterprisePhone;
	}

	public void setEnterprisePhone(String enterprisePhone) {
		this.enterprisePhone = enterprisePhone;
	}

	public String getEnterpriseFax() {
		return enterpriseFax;
	}

	public void setEnterpriseFax(String enterpriseFax) {
		this.enterpriseFax = enterpriseFax;
	}

	public String getEnterpriseRemarks() {
		return enterpriseRemarks;
	}

	public void setEnterpriseRemarks(String enterpriseRemarks) {
		this.enterpriseRemarks = enterpriseRemarks;
	}

	public String getSmsUsername() {
		return smsUsername;
	}

	public void setSmsUsername(String smsUsername) {
		this.smsUsername = smsUsername;
	}

	public String getSmsPassword() {
		return smsPassword;
	}

	public void setSmsPassword(String smsPassword) {
		this.smsPassword = smsPassword;
	}

	public Integer getUsable() {
		return usable;
	}

	public void setUsable(Integer usable) {
		this.usable = usable;
	}
}
