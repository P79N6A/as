package com.yaoguang.greendao.entity.common.ViewForwardOrderPack;


import android.os.Parcel;
import android.os.Parcelable;

public class FreightBills implements Parcelable {

	private String id;

	private String ladingId;

	private String billSource;

	private String shipper;

	private String shipperId;

	private String consigneeId;

	private String modifyDate;

	private String etdPlan;

	private String etaPlan;

	private String dockOfLoading;

	private String finalDestination;

	private String carriageItem;

	private String servicetype;

	private Integer fclLclAir;

	private String prepaidCollect;

	private String employeeId;

	private String consolCode;

	private String goodsName;

	private Double goodsGrossWeight;

	private String freightCompany;

	private Integer auditStatus;

	private Integer billStatus;

	private Integer unboxingStatus;

	private Integer packingStatus;

	private String clientId;

	private String createdBy;

	private String createdDeptId;

	private String created;

	private String updatedBy;

	private String updatedDeptId;

	private String updated;

	// 分公司名称
	private String officeName;

	// 柜量
	private String conts;

	public String getmBlNo() {
		return mBlNo;
	}

	public void setmBlNo(String mBlNo) {
		this.mBlNo = mBlNo;
	}

	private String mBlNo;

	public String getConts() {
		return conts;
	}

	public void setConts(String conts) {
		this.conts = conts;
	}


	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id == null ? null : id.trim();
	}

	public String getLadingId() {
		return ladingId;
	}

	public void setLadingId(String ladingId) {
		this.ladingId = ladingId == null ? null : ladingId.trim();
	}

	public String getBillSource() {
		return billSource;
	}

	public void setBillSource(String billSource) {
		this.billSource = billSource == null ? null : billSource.trim();
	}

	public String getShipper() {
		return shipper;
	}

	public void setShipper(String shipper) {
		this.shipper = shipper == null ? null : shipper.trim();
	}

	public String getShipperId() {
		return shipperId;
	}

	public void setShipperId(String shipperId) {
		this.shipperId = shipperId;
	}

	public String getConsigneeId() {
		return consigneeId;
	}

	public void setConsigneeId(String consigneeId) {
		this.consigneeId = consigneeId == null ? null : consigneeId.trim();
	}

	public String getModifyDate() {
		return modifyDate;
	}

	public void setModifyDate(String modifyDate) {
		this.modifyDate = modifyDate;
	}

	public String getEtdPlan() {
		return etdPlan;
	}

	public void setEtdPlan(String etdPlan) {
		this.etdPlan = etdPlan;
	}

	public String getEtaPlan() {
		return etaPlan;
	}

	public void setEtaPlan(String etaPlan) {
		this.etaPlan = etaPlan;
	}

	public String getDockOfLoading() {
		return dockOfLoading;
	}

	public void setDockOfLoading(String dockOfLoading) {
		this.dockOfLoading = dockOfLoading == null ? null : dockOfLoading.trim();
	}

	public String getFinalDestination() {
		return finalDestination;
	}

	public void setFinalDestination(String finalDestination) {
		this.finalDestination = finalDestination == null ? null : finalDestination.trim();
	}

	public String getCarriageItem() {
		return carriageItem;
	}

	public void setCarriageItem(String carriageItem) {
		this.carriageItem = carriageItem == null ? null : carriageItem.trim();
	}

	public String getServicetype() {
		return servicetype;
	}

	public void setServicetype(String servicetype) {
		this.servicetype = servicetype == null ? null : servicetype.trim();
	}

	public Integer getFclLclAir() {
		return fclLclAir;
	}

	public void setFclLclAir(Integer fclLclAir) {
		this.fclLclAir = fclLclAir;
	}

	public String getPrepaidCollect() {
		return prepaidCollect;
	}

	public void setPrepaidCollect(String prepaidCollect) {
		this.prepaidCollect = prepaidCollect == null ? null : prepaidCollect.trim();
	}

	public String getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(String employeeId) {
		this.employeeId = employeeId == null ? null : employeeId.trim();
	}

	public String getConsolCode() {
		return consolCode;
	}

	public void setConsolCode(String consolCode) {
		this.consolCode = consolCode == null ? null : consolCode.trim();
	}

	public String getGoodsName() {
		return goodsName;
	}

	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName == null ? null : goodsName.trim();
	}

	public Double getGoodsGrossWeight() {
		return goodsGrossWeight;
	}

	public void setGoodsGrossWeight(Double goodsGrossWeight) {
		this.goodsGrossWeight = goodsGrossWeight;
	}

	public String getFreightCompany() {
		return freightCompany;
	}

	public void setFreightCompany(String freightCompany) {
		this.freightCompany = freightCompany == null ? null : freightCompany.trim();
	}

	public Integer getAuditStatus() {
		return auditStatus;
	}

	public void setAuditStatus(Integer auditStatus) {
		this.auditStatus = auditStatus;
	}

	public Integer getBillStatus() {
		return billStatus;
	}

	public void setBillStatus(Integer billStatus) {
		this.billStatus = billStatus;
	}

	public Integer getUnboxingStatus() {
		return unboxingStatus;
	}

	public Integer getPackingStatus() {
		return packingStatus;
	}

	public void setUnboxingStatus(Integer unboxingStatus) {
		this.unboxingStatus = unboxingStatus;
	}

	public void setPackingStatus(Integer packingStatus) {
		this.packingStatus = packingStatus;
	}

	public String getClientId() {
		return clientId;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId == null ? null : clientId.trim();
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

	public String getOfficeName() {
		return officeName;
	}

	public void setOfficeName(String officeName) {
		this.officeName = officeName;
	}


	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(this.id);
		dest.writeString(this.ladingId);
		dest.writeString(this.billSource);
		dest.writeString(this.shipper);
		dest.writeString(this.shipperId);
		dest.writeString(this.consigneeId);
		dest.writeString(this.modifyDate);
		dest.writeString(this.etdPlan);
		dest.writeString(this.etaPlan);
		dest.writeString(this.dockOfLoading);
		dest.writeString(this.finalDestination);
		dest.writeString(this.carriageItem);
		dest.writeString(this.servicetype);
		dest.writeValue(this.fclLclAir);
		dest.writeString(this.prepaidCollect);
		dest.writeString(this.employeeId);
		dest.writeString(this.consolCode);
		dest.writeString(this.goodsName);
		dest.writeValue(this.goodsGrossWeight);
		dest.writeString(this.freightCompany);
		dest.writeValue(this.auditStatus);
		dest.writeValue(this.billStatus);
		dest.writeValue(this.unboxingStatus);
		dest.writeValue(this.packingStatus);
		dest.writeString(this.clientId);
		dest.writeString(this.createdBy);
		dest.writeString(this.createdDeptId);
		dest.writeString(this.created);
		dest.writeString(this.updatedBy);
		dest.writeString(this.updatedDeptId);
		dest.writeString(this.updated);
		dest.writeString(this.officeName);
		dest.writeString(this.conts);
		dest.writeString(this.mBlNo);
	}

	public FreightBills() {
	}

	protected FreightBills(Parcel in) {
		this.id = in.readString();
		this.ladingId = in.readString();
		this.billSource = in.readString();
		this.shipper = in.readString();
		this.shipperId = in.readString();
		this.consigneeId = in.readString();
		this.modifyDate = in.readString();
		this.etdPlan = in.readString();
		this.etaPlan = in.readString();
		this.dockOfLoading = in.readString();
		this.finalDestination = in.readString();
		this.carriageItem = in.readString();
		this.servicetype = in.readString();
		this.fclLclAir = (Integer) in.readValue(Integer.class.getClassLoader());
		this.prepaidCollect = in.readString();
		this.employeeId = in.readString();
		this.consolCode = in.readString();
		this.goodsName = in.readString();
		this.goodsGrossWeight = (Double) in.readValue(Double.class.getClassLoader());
		this.freightCompany = in.readString();
		this.auditStatus = (Integer) in.readValue(Integer.class.getClassLoader());
		this.billStatus = (Integer) in.readValue(Integer.class.getClassLoader());
		this.unboxingStatus = (Integer) in.readValue(Integer.class.getClassLoader());
		this.packingStatus = (Integer) in.readValue(Integer.class.getClassLoader());
		this.clientId = in.readString();
		this.createdBy = in.readString();
		this.createdDeptId = in.readString();
		this.created = in.readString();
		this.updatedBy = in.readString();
		this.updatedDeptId = in.readString();
		this.updated = in.readString();
		this.officeName = in.readString();
		this.conts = in.readString();
		this.mBlNo = in.readString();
	}

	public static final Parcelable.Creator<FreightBills> CREATOR = new Parcelable.Creator<FreightBills>() {
		@Override
		public FreightBills createFromParcel(Parcel source) {
			return new FreightBills(source);
		}

		@Override
		public FreightBills[] newArray(int size) {
			return new FreightBills[size];
		}
	};
}