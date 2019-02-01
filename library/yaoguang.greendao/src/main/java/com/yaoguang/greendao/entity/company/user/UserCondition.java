package com.yaoguang.greendao.entity.company.user;

/**
 * 用户查询条件类
 *
 * @author ZhangDeQuan
 * @time 2016年9月30日 上午11:07:49
 */
public class UserCondition  {

	// 当前登录用户ID
	private String userId;

	// 当前登录用户归属部门ID
	private String userOfficeId;

	// 归属公司ID
	private String companyId;

	// 归属部门ID
	private String officeId;

	// 登录名
	private String loginName;

	// 姓名
	private String name;

	// 公司名称
	private String companyName;

	// 用户类型(0:系统管理 1：普通用户)
	private String userType;

	// 是否web端登录
	private Integer loginFlag = -1;

	// (0：离职 1：可用 -1：全部)
	private Integer usable = -1;

	// 是否黄页官网显示(0-否，1-是)
	private Integer isWebsiteShow = -1;

	// 黄页功能是否过期(0-否，1-是,-1-全部)
	private Integer isWebsiteOverdue = -1;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserOfficeId() {
		return userOfficeId;
	}

	public void setUserOfficeId(String userOfficeId) {
		this.userOfficeId = userOfficeId;
	}

	public String getCompanyId() {
		return companyId;
	}

	public void setCompanyId(String companyId) {
		this.companyId = companyId;
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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

	public Integer getLoginFlag() {
		return loginFlag;
	}

	public void setLoginFlag(Integer loginFlag) {
		this.loginFlag = loginFlag;
	}

	public Integer getUsable() {
		return usable;
	}

	public void setUsable(Integer usable) {
		this.usable = usable;
	}

	public Integer getIsWebsiteShow() {
		return isWebsiteShow;
	}

	public void setIsWebsiteShow(Integer isWebsiteShow) {
		this.isWebsiteShow = isWebsiteShow;
	}

	public Integer getIsWebsiteOverdue() {
		return isWebsiteOverdue;
	}

	public void setIsWebsiteOverdue(Integer isWebsiteOverdue) {
		this.isWebsiteOverdue = isWebsiteOverdue;
	}
}
