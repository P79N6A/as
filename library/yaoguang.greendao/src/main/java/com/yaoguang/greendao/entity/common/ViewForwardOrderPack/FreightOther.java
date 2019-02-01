package com.yaoguang.greendao.entity.common.ViewForwardOrderPack;

import java.io.Serializable;
public class FreightOther implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String id;

	private String billsId;

	private Integer isInsurClaim;

	private Integer isOverweight;

	private Integer goodsType;

	private String accountRemark;

	private String remark5;

	private String isNote;

	private Integer haveSpecial;

	private Integer debitNoteStatus;

	private String debitNoteDate;

	private Integer isAgentback;

	private String agentbackDate;

	private String tjId;

	private Integer isForesee;

	private String shipOrder;

	private String shipLine;

	private String mainOrder;

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

	public String getBillsId() {
		return billsId;
	}

	public void setBillsId(String billsId) {
		this.billsId = billsId == null ? null : billsId.trim();
	}

	public Integer getIsInsurClaim() {
		return isInsurClaim;
	}

	public void setIsInsurClaim(Integer isInsurClaim) {
		this.isInsurClaim = isInsurClaim;
	}

	public Integer getIsOverweight() {
		return isOverweight;
	}

	public void setIsOverweight(Integer isOverweight) {
		this.isOverweight = isOverweight;
	}

	public Integer getGoodsType() {
		return goodsType;
	}

	public void setGoodsType(Integer goodsType) {
		this.goodsType = goodsType;
	}

	public String getAccountRemark() {
		return accountRemark;
	}

	public void setAccountRemark(String accountRemark) {
		this.accountRemark = accountRemark == null ? null : accountRemark.trim();
	}

	public String getRemark5() {
		return remark5;
	}

	public void setRemark5(String remark5) {
		this.remark5 = remark5 == null ? null : remark5.trim();
	}

	public String getIsNote() {
		return isNote;
	}

	public void setIsNote(String isNote) {
		this.isNote = isNote == null ? null : isNote.trim();
	}

	public Integer getHaveSpecial() {
		return haveSpecial;
	}

	public void setHaveSpecial(Integer haveSpecial) {
		this.haveSpecial = haveSpecial;
	}

	public Integer getDebitNoteStatus() {
		return debitNoteStatus;
	}

	public void setDebitNoteStatus(Integer debitNoteStatus) {
		this.debitNoteStatus = debitNoteStatus;
	}

	public String getDebitNoteDate() {
		return debitNoteDate;
	}

	public void setDebitNoteDate(String debitNoteDate) {
		this.debitNoteDate = debitNoteDate;
	}

	public Integer getIsAgentback() {
		return isAgentback;
	}

	public void setIsAgentback(Integer isAgentback) {
		this.isAgentback = isAgentback;
	}

	public String getAgentbackDate() {
		return agentbackDate;
	}

	public void setAgentbackDate(String agentbackDate) {
		this.agentbackDate = agentbackDate;
	}

	public String getTjId() {
		return tjId;
	}

	public void setTjId(String tjId) {
		this.tjId = tjId == null ? null : tjId.trim();
	}

	public Integer getIsForesee() {
		return isForesee;
	}

	public void setIsForesee(Integer isForesee) {
		this.isForesee = isForesee;
	}

	public String getShipOrder() {
		return shipOrder;
	}

	public void setShipOrder(String shipOrder) {
		this.shipOrder = shipOrder == null ? null : shipOrder.trim();
	}

	public String getShipLine() {
		return shipLine;
	}

	public void setShipLine(String shipLine) {
		this.shipLine = shipLine == null ? null : shipLine.trim();
	}

	public String getMainOrder() {
		return mainOrder;
	}

	public void setMainOrder(String mainOrder) {
		this.mainOrder = mainOrder;
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
}