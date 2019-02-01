package com.yaoguang.greendao.entity.company.userLoginTimeWrapper;

import java.util.List;

/**
 * 用户成员-用户权限信息包装类
 * 
 * @author ZhangDeQuan
 * @time 2016年10月21日 上午11:54:32
 */
public class UserLoginTimeWrapper {

	private List<UserLoginTime> userLoginTimes;

	public List<UserLoginTime> getUserLoginTimes() {
		return userLoginTimes;
	}

	public void setUserLoginTimes(List<UserLoginTime> userLoginTimes) {
		this.userLoginTimes = userLoginTimes;
	}

	public UserLoginTimeWrapper(List<UserLoginTime> userLoginTimes) {
		super();
		this.userLoginTimes = userLoginTimes;
	}

	public UserLoginTimeWrapper() {
		super();
	}

}
