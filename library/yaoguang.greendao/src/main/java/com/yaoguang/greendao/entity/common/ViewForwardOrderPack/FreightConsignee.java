package com.yaoguang.greendao.entity.common.ViewForwardOrderPack;

import com.yaoguang.greendao.entity.driver.LocationArea;

import java.io.Serializable;

public class FreightConsignee implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String id;

	private String billsId;

	private Long sort;

	private String consigneePlace;

	private String consigneeLinkman;

	private String consigneeTel;

	private String consigneeMp;

	private String consigneeUnit;

	private String consigneeUnitFax;

	private String remark9;

	private Integer lastReliveStatus;

	private String lastReliveDate;

	private String createdBy;

	private String createdDeptId;

	private String created;

	private String updatedBy;

	private String updatedDeptId;

	private String updated;

	private LocationArea locationArea;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id == null ? null : id.trim();
	}

	public String getBillsId() {
		return billsId;
	}

	public void setBillsId(String billsId) {
		this.billsId = billsId == null ? null : billsId.trim();
	}

	public Long getSort() {
		return sort;
	}

	public void setSort(Long sort) {
		this.sort = sort;
	}

	public String getConsigneePlace() {
		return consigneePlace;
	}

	public void setConsigneePlace(String consigneePlace) {
		this.consigneePlace = consigneePlace == null ? null : consigneePlace.trim();
	}

	public String getConsigneeLinkman() {
		return consigneeLinkman;
	}

	public void setConsigneeLinkman(String consigneeLinkman) {
		this.consigneeLinkman = consigneeLinkman == null ? null : consigneeLinkman.trim();
	}

	public String getConsigneeTel() {
		return consigneeTel;
	}

	public void setConsigneeTel(String consigneeTel) {
		this.consigneeTel = consigneeTel == null ? null : consigneeTel.trim();
	}

	public String getConsigneeMp() {
		return consigneeMp;
	}

	public void setConsigneeMp(String consigneeMp) {
		this.consigneeMp = consigneeMp == null ? null : consigneeMp.trim();
	}

	public String getConsigneeUnit() {
		return consigneeUnit;
	}

	public void setConsigneeUnit(String consigneeUnit) {
		this.consigneeUnit = consigneeUnit == null ? null : consigneeUnit.trim();
	}

	public String getConsigneeUnitFax() {
		return consigneeUnitFax;
	}

	public void setConsigneeUnitFax(String consigneeUnitFax) {
		this.consigneeUnitFax = consigneeUnitFax == null ? null : consigneeUnitFax.trim();
	}

	public String getRemark9() {
		return remark9;
	}

	public void setRemark9(String remark9) {
		this.remark9 = remark9 == null ? null : remark9.trim();
	}

	public Integer getLastReliveStatus() {
		return lastReliveStatus;
	}

	public void setLastReliveStatus(Integer lastReliveStatus) {
		this.lastReliveStatus = lastReliveStatus;
	}

	public String getLastReliveDate() {
		return lastReliveDate;
	}

	public void setLastReliveDate(String lastReliveDate) {
		this.lastReliveDate = lastReliveDate;
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

	public LocationArea getLocationArea() {
		return locationArea;
	}

	public void setLocationArea(LocationArea locationArea) {
		this.locationArea = locationArea;
	}
}