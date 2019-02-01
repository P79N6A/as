package com.yaoguang.greendao.entity;

import java.util.List;

/**
 * 公共-查询条件信息包装类
 * 
 * @author ZhangDeQuan
 * @time 2016年11月8日 上午11:54:32
 */
public class SysConditionWrapper {

	private List<SysCondition> sysConditions;

	public List<SysCondition> getsysConditions() {
		return sysConditions;
	}

	public void setsysConditions(List<SysCondition> sysConditions) {
		this.sysConditions = sysConditions;
	}

	public SysConditionWrapper(List<SysCondition> sysConditions) {
		super();
		this.sysConditions = sysConditions;
	}

	public SysConditionWrapper() {
		super();
	}

}
