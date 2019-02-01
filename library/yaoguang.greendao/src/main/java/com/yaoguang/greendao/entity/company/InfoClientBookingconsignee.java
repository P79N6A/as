package com.yaoguang.greendao.entity.company;




/**
 * 基本资料-往来单位-订舱收货人信息
 * 
 * @author ZhangDeQuan
 * @time 2017年2月22日 上午11:17:55
 */

public class InfoClientBookingconsignee  {

	private String id;

	private String codeId;

	private String companyname;

	private String linkman;

	private String linkmanTel;

	private String linkmanPhone;

	private String consigneeCompanyFax;

	private String linkmanAddress;

	private String cardNumber;

	private String remark;

	private String shipCompany;

	private String destTruck;

	private Integer isDefault;

	private String clientId;

	private String createdBy;

	private String createdDeptId;

	private String created;

	private String updatedBy;

	private String updatedDeptId;

	private String updated;

	private Integer isValid;
	
	public Integer getIsValid() {
		return isValid;
	}

	public void setIsValid(Integer isValid) {
		this.isValid = isValid;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id == null ? null : id.trim();
	}

	public String getCodeId() {
		return codeId;
	}

	public void setCodeId(String codeId) {
		this.codeId = codeId == null ? null : codeId.trim();
	}

	public String getCompanyname() {
		return companyname;
	}

	public void setCompanyname(String companyname) {
		this.companyname = companyname == null ? null : companyname.trim();
	}

	public String getLinkman() {
		return linkman;
	}

	public void setLinkman(String linkman) {
		this.linkman = linkman == null ? null : linkman.trim();
	}

	public String getLinkmanTel() {
		return linkmanTel;
	}

	public void setLinkmanTel(String linkmanTel) {
		this.linkmanTel = linkmanTel == null ? null : linkmanTel.trim();
	}

	public String getLinkmanPhone() {
		return linkmanPhone;
	}

	public void setLinkmanPhone(String linkmanPhone) {
		this.linkmanPhone = linkmanPhone;
	}

	public String getConsigneeCompanyFax() {
		return consigneeCompanyFax;
	}

	public void setConsigneeCompanyFax(String consigneeCompanyFax) {
		this.consigneeCompanyFax = consigneeCompanyFax == null ? null : consigneeCompanyFax.trim();
	}

	public String getLinkmanAddress() {
		return linkmanAddress;
	}

	public void setLinkmanAddress(String linkmanAddress) {
		this.linkmanAddress = linkmanAddress == null ? null : linkmanAddress.trim();
	}

	public String getCardNumber() {
		return cardNumber;
	}

	public void setCardNumber(String cardNumber) {
		this.cardNumber = cardNumber;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark == null ? null : remark.trim();
	}

	public String getShipCompany() {
		return shipCompany;
	}

	public void setShipCompany(String shipCompany) {
		this.shipCompany = shipCompany == null ? null : shipCompany.trim();
	}

	public String getDestTruck() {
		return destTruck;
	}

	public void setDestTruck(String destTruck) {
		this.destTruck = destTruck == null ? null : destTruck.trim();
	}

	public Integer getIsDefault() {
		return isDefault;
	}

	public void setIsDefault(Integer isDefault) {
		this.isDefault = isDefault;
	}

	public String getClientId() {
		return clientId;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
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