package com.yaoguang.greendao.entity.common;

import java.io.Serializable;


/**
 * 基本资料-码头/堆场
 * 
 * @author ZhangDeQuan
 * @time 2016年10月18日 上午11:32:33
 */
public class InfoDock implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 9097489407667399714L;

	private String id;

	private String dockId;

	private String dockCode;

	private String dockChina;

	private String dockEnglish;

	private String city;

	private Integer flag;

	private String remark;

	private String address;

	private String location;

	private String createdBy;

	private String createdDeptId;

	private String created;

	private String updatedBy;

	private String updatedDeptId;

	private String updated;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id == null ? null : id.trim();
	}

	public String getDockId() {
		return dockId;
	}

	public void setDockId(String dockId) {
		this.dockId = dockId == null ? null : dockId.trim();
	}

	public String getDockCode() {
		return dockCode;
	}

	public void setDockCode(String dockCode) {
		this.dockCode = dockCode == null ? null : dockCode.trim();
	}

	public String getDockChina() {
		return dockChina;
	}

	public void setDockChina(String dockChina) {
		this.dockChina = dockChina == null ? null : dockChina.trim();
	}

	public String getDockEnglish() {
		return dockEnglish;
	}

	public void setDockEnglish(String dockEnglish) {
		this.dockEnglish = dockEnglish == null ? null : dockEnglish.trim();
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city == null ? null : city.trim();
	}

	public Integer getFlag() {
		return flag;
	}

	public void setFlag(Integer flag) {
		this.flag = flag;
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
		this.updatedBy = updatedBy == null ? null : updatedBy.trim();
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

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}
}