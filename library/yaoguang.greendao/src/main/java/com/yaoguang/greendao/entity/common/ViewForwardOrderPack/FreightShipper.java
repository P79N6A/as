package com.yaoguang.greendao.entity.common.ViewForwardOrderPack;

import com.yaoguang.greendao.entity.driver.LocationArea;

import java.io.Serializable;

public class FreightShipper implements Serializable {
	private static final long serialVersionUID = 1L;

	private String id;

	private String billsId;
	
	private Long sort;

	private String jhr;

	private String companyLxr;

	private String shipperTel;

	private String shipperMp;

	private String consignerUnit;

	private String consignerUnitFax;

	private String remark3;

	private Integer loadStatus;

	private String lastLoadDate;

	private String createdBy;

	private String createdDeptId;

	private String created;

	private String updatedBy;

	private String updatedDeptId;

	private String updated;

	private String location;

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

	public String getJhr() {
		return jhr;
	}

	public void setJhr(String jhr) {
		this.jhr = jhr == null ? null : jhr.trim();
	}

	public String getCompanyLxr() {
		return companyLxr;
	}

	public void setCompanyLxr(String companyLxr) {
		this.companyLxr = companyLxr == null ? null : companyLxr.trim();
	}

	public String getShipperTel() {
		return shipperTel;
	}

	public void setShipperTel(String shipperTel) {
		this.shipperTel = shipperTel == null ? null : shipperTel.trim();
	}

	public String getShipperMp() {
		return shipperMp;
	}

	public void setShipperMp(String shipperMp) {
		this.shipperMp = shipperMp == null ? null : shipperMp.trim();
	}

	public String getConsignerUnit() {
		return consignerUnit;
	}

	public void setConsignerUnit(String consignerUnit) {
		this.consignerUnit = consignerUnit == null ? null : consignerUnit.trim();
	}

	public String getConsignerUnitFax() {
		return consignerUnitFax;
	}

	public void setConsignerUnitFax(String consignerUnitFax) {
		this.consignerUnitFax = consignerUnitFax == null ? null : consignerUnitFax.trim();
	}

	public String getRemark3() {
		return remark3;
	}

	public void setRemark3(String remark3) {
		this.remark3 = remark3 == null ? null : remark3.trim();
	}

	public Integer getLoadStatus() {
		return loadStatus;
	}

	public void setLoadStatus(Integer loadStatus) {
		this.loadStatus = loadStatus;
	}

	public String getLastLoadDate() {
		return lastLoadDate;
	}

	public void setLastLoadDate(String lastLoadDate) {
		this.lastLoadDate = lastLoadDate;
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

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public LocationArea getLocationArea() {
		return locationArea;
	}

	public void setLocationArea(LocationArea locationArea) {
		this.locationArea = locationArea;
	}
}