package com.yaoguang.greendao.entity;

import java.io.Serializable;
import java.util.List;

/**
 * 公共-查询条件实体
 * 
 * @author ZhangDeQuan
 * @time 2016年11月5日 下午1:38:53
 */
public class SysCondition implements Serializable {

	private static final long serialVersionUID = 1690541413049414365L;

	private Integer id;

	private String serviceType;

	private String conditionType;

	private String tableFieldName;

	private String displayName;

	private String inputValue;

	private String inputValue2;

	private String remark;

	private String created;

	// 用于货代拖车工作单列表的，如果这个有数据，那么下拉框可选的就不是：是/否 ，而是该列表
	private List<Option> options;

	// app自己这边做的自定义字段，分辨是什么条件
	private String appCustomType;

	// app自己这边做的自定义字段，分辨位置
	private int appPosition;

	private boolean isCheck;// 是否选择，app自己这边自己制作的一个字段

	public boolean isCheck() {
		return isCheck;
	}

	public void setCheck(boolean check) {
		isCheck = check;
	}

	public int getAppPosition() {
		return appPosition;
	}

	public void setAppPosition(int appPosition) {
		this.appPosition = appPosition;
	}

	public String getAppCustomType() {
		return appCustomType;
	}

	public void setAppCustomType(String appCustomType) {
		this.appCustomType = appCustomType;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getServiceType() {
		return serviceType;
	}

	public void setServiceType(String serviceType) {
		this.serviceType = serviceType == null ? null : serviceType.trim();
	}

	public String getConditionType() {
		return conditionType;
	}

	public void setConditionType(String conditionType) {
		this.conditionType = conditionType == null ? null : conditionType.trim();
	}

	public String getTableFieldName() {
		return tableFieldName;
	}

	public void setTableFieldName(String tableFieldName) {
		this.tableFieldName = tableFieldName == null ? null : tableFieldName.trim();
	}

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName == null ? null : displayName.trim();
	}

	public String getInputValue() {
		return inputValue;
	}

	public void setInputValue(String inputValue) {
		this.inputValue = inputValue == null ? null : inputValue.trim();
	}

	public String getInputValue2() {
		return inputValue2;
	}

	public void setInputValue2(String inputValue2) {
		this.inputValue2 = inputValue2;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark == null ? null : remark.trim();
	}

	public String getCreated() {
		return created;
	}

	public void setCreated(String created) {
		this.created = created;
	}

	public List<Option> getOptions() {
		return options;
	}

	public void setOptions(List<Option> options) {
		this.options = options;
	}

}