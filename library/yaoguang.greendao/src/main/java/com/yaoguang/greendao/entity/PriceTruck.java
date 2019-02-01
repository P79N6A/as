package com.yaoguang.greendao.entity;

import java.io.Serializable;

/**
 * 价格管理-拖车费
 * 
 * @author ZhangDeQuan
 * @time 2017年4月25日 下午2:14:34
 */
public class PriceTruck implements Serializable {

	private static final long serialVersionUID = 6125118879159792713L;

	private String id;

	private String versionId;

	private Integer accoAttr;

	private String truckCompany;

	private String shipper;

	private String port;

	private String shipCompany;

	private String address;

	private Integer otherservice;

	private Double one20gpPrice;

	private Double two20gpPrice;

	private Double one40rfPrice;

	private Double one40rhPrice;

	private Double one40gpPrice;

	private String standardScale;

	private String lclScale;

	private String boxScale;

	private Double one20gpYuan;

	private Double two20gpYuan;

	private Double one40rfYuan;

	private Double one40rhYuan;

	private Double one40gpYuan;

	private String startDate;

	private String endDate;

	private String paymentMethod;

	private String carriageWay;

	private String goodsType;

	private String remark;

	private String priceRemark;

	private Double kilometers;

	private String clientId;

	private String createBy;

	private String createdDeptId;

	private String created;

	private String updatedBy;

	private String updatedDeptId;

	private String updated;

	private Integer isValid;

	// 机构名称(分公司名称)
	private String officeName;

	public String getFee() {
		return fee;
	}

	public void setFee(String fee) {
		this.fee = fee;
	}

	private String fee;

	public String getCont() {
		return cont;
	}

	public void setCont(String cont) {
		this.cont = cont;
	}

	private String cont;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id == null ? null : id.trim();
	}

	public String getVersionId() {
		return versionId;
	}

	public void setVersionId(String versionId) {
		this.versionId = versionId == null ? null : versionId.trim();
	}

	public Integer getAccoAttr() {
		return accoAttr;
	}

	public void setAccoAttr(Integer accoAttr) {
		this.accoAttr = accoAttr;
	}

	public String getTruckCompany() {
		return truckCompany;
	}

	public void setTruckCompany(String truckCompany) {
		this.truckCompany = truckCompany == null ? null : truckCompany.trim();
	}

	public String getShipper() {
		return shipper;
	}

	public void setShipper(String shipper) {
		this.shipper = shipper == null ? null : shipper.trim();
	}

	public String getPort() {
		return port;
	}

	public void setPort(String port) {
		this.port = port == null ? null : port.trim();
	}

	public String getShipCompany() {
		return shipCompany;
	}

	public void setShipCompany(String shipCompany) {
		this.shipCompany = shipCompany == null ? null : shipCompany.trim();
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address == null ? null : address.trim();
	}

	public Integer getOtherservice() {
		return otherservice;
	}

	public void setOtherservice(Integer otherservice) {
		this.otherservice = otherservice;
	}

	public Double getOne20gpPrice() {
		return one20gpPrice;
	}

	public void setOne20gpPrice(Double one20gpPrice) {
		this.one20gpPrice = one20gpPrice;
	}

	public Double getTwo20gpPrice() {
		return two20gpPrice;
	}

	public void setTwo20gpPrice(Double two20gpPrice) {
		this.two20gpPrice = two20gpPrice;
	}

	public Double getOne40rfPrice() {
		return one40rfPrice;
	}

	public void setOne40rfPrice(Double one40rfPrice) {
		this.one40rfPrice = one40rfPrice;
	}

	public Double getOne40rhPrice() {
		return one40rhPrice;
	}

	public void setOne40rhPrice(Double one40rhPrice) {
		this.one40rhPrice = one40rhPrice;
	}

	public Double getOne40gpPrice() {
		return one40gpPrice;
	}

	public void setOne40gpPrice(Double one40gpPrice) {
		this.one40gpPrice = one40gpPrice;
	}

	public String getStandardScale() {
		return standardScale;
	}

	public void setStandardScale(String standardScale) {
		this.standardScale = standardScale == null ? null : standardScale.trim();
	}

	public String getLclScale() {
		return lclScale;
	}

	public void setLclScale(String lclScale) {
		this.lclScale = lclScale == null ? null : lclScale.trim();
	}

	public String getBoxScale() {
		return boxScale;
	}

	public void setBoxScale(String boxScale) {
		this.boxScale = boxScale == null ? null : boxScale.trim();
	}

	public Double getOne20gpYuan() {
		return one20gpYuan;
	}

	public void setOne20gpYuan(Double one20gpYuan) {
		this.one20gpYuan = one20gpYuan;
	}

	public Double getTwo20gpYuan() {
		return two20gpYuan;
	}

	public void setTwo20gpYuan(Double two20gpYuan) {
		this.two20gpYuan = two20gpYuan;
	}

	public Double getOne40rfYuan() {
		return one40rfYuan;
	}

	public void setOne40rfYuan(Double one40rfYuan) {
		this.one40rfYuan = one40rfYuan;
	}

	public Double getOne40rhYuan() {
		return one40rhYuan;
	}

	public void setOne40rhYuan(Double one40rhYuan) {
		this.one40rhYuan = one40rhYuan;
	}

	public Double getOne40gpYuan() {
		return one40gpYuan;
	}

	public void setOne40gpYuan(Double one40gpYuan) {
		this.one40gpYuan = one40gpYuan;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public String getPaymentMethod() {
		return paymentMethod;
	}

	public void setPaymentMethod(String paymentMethod) {
		this.paymentMethod = paymentMethod == null ? null : paymentMethod.trim();
	}

	public String getCarriageWay() {
		return carriageWay;
	}

	public void setCarriageWay(String carriageWay) {
		this.carriageWay = carriageWay == null ? null : carriageWay.trim();
	}

	public String getGoodsType() {
		return goodsType;
	}

	public void setGoodsType(String goodsType) {
		this.goodsType = goodsType == null ? null : goodsType.trim();
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark == null ? null : remark.trim();
	}

	public String getPriceRemark() {
		return priceRemark;
	}

	public void setPriceRemark(String priceRemark) {
		this.priceRemark = priceRemark == null ? null : priceRemark.trim();
	}

	public Double getKilometers() {
		return kilometers;
	}

	public void setKilometers(Double kilometers) {
		this.kilometers = kilometers;
	}

	public String getClientId() {
		return clientId;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

	public String getCreateBy() {
		return createBy;
	}

	public void setCreateBy(String createBy) {
		this.createBy = createBy == null ? null : createBy.trim();
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

	public Integer getIsValid() {
		return isValid;
	}

	public void setIsValid(Integer isValid) {
		this.isValid = isValid;
	}

	public String getOfficeName() {
		return officeName;
	}

	public void setOfficeName(String officeName) {
		this.officeName = officeName;
	}
}