package com.yaoguang.greendao.entity.common.ViewForwardOrderPack;


import android.os.Parcel;
import android.os.Parcelable;

public class FreightInsurance implements Parcelable {

	private String id;

	private String billsId;

	private String insurType;

	private String insurValue;

	private String insurRate;

	private String insurMoney;

	private String hBlNo;

	private String agency;

	private Integer isInsurance;

	private Integer instoreStatus;

	private String instoreStatusDate;

	private String remark;

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

	public String getInsurType() {
		return insurType;
	}

	public void setInsurType(String insurType) {
		this.insurType = insurType == null ? null : insurType.trim();
	}

	public String getInsurValue() {
		return insurValue;
	}

	public void setInsurValue(String insurValue) {
		this.insurValue = insurValue;
	}

	public String getInsurRate() {
		return insurRate;
	}

	public void setInsurRate(String insurRate) {
		this.insurRate = insurRate;
	}

	public String getInsurMoney() {
		return insurMoney;
	}

	public void setInsurMoney(String insurMoney) {
		this.insurMoney = insurMoney;
	}

	public String gethBlNo() {
		return hBlNo;
	}

	public void sethBlNo(String hBlNo) {
		this.hBlNo = hBlNo == null ? null : hBlNo.trim();
	}

	public String getAgency() {
		return agency;
	}

	public void setAgency(String agency) {
		this.agency = agency == null ? null : agency.trim();
	}

	public Integer getIsInsurance() {
		return isInsurance;
	}

	public void setIsInsurance(Integer isInsurance) {
		this.isInsurance = isInsurance;
	}

	public Integer getInstoreStatus() {
		return instoreStatus;
	}

	public void setInstoreStatus(Integer instoreStatus) {
		this.instoreStatus = instoreStatus;
	}

	public String getInstoreStatusDate() {
		return instoreStatusDate;
	}

	public void setInstoreStatusDate(String instoreStatusDate) {
		this.instoreStatusDate = instoreStatusDate;
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


	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(this.id);
		dest.writeString(this.billsId);
		dest.writeString(this.insurType);
		dest.writeString(this.insurValue);
		dest.writeString(this.insurRate);
		dest.writeString(this.insurMoney);
		dest.writeString(this.hBlNo);
		dest.writeString(this.agency);
		dest.writeValue(this.isInsurance);
		dest.writeValue(this.instoreStatus);
		dest.writeString(this.instoreStatusDate);
		dest.writeString(this.remark);
		dest.writeString(this.createdBy);
		dest.writeString(this.createdDeptId);
		dest.writeString(this.created);
		dest.writeString(this.updatedBy);
		dest.writeString(this.updatedDeptId);
		dest.writeString(this.updated);
	}

	public FreightInsurance() {
	}

	protected FreightInsurance(Parcel in) {
		this.id = in.readString();
		this.billsId = in.readString();
		this.insurType = in.readString();
		this.insurValue = in.readString();
		this.insurRate = in.readString();
		this.insurMoney = in.readString();
		this.hBlNo = in.readString();
		this.agency = in.readString();
		this.isInsurance = (Integer) in.readValue(Integer.class.getClassLoader());
		this.instoreStatus = (Integer) in.readValue(Integer.class.getClassLoader());
		this.instoreStatusDate = in.readString();
		this.remark = in.readString();
		this.createdBy = in.readString();
		this.createdDeptId = in.readString();
		this.created = in.readString();
		this.updatedBy = in.readString();
		this.updatedDeptId = in.readString();
		this.updated = in.readString();
	}

	public static final Parcelable.Creator<FreightInsurance> CREATOR = new Parcelable.Creator<FreightInsurance>() {
		@Override
		public FreightInsurance createFromParcel(Parcel source) {
			return new FreightInsurance(source);
		}

		@Override
		public FreightInsurance[] newArray(int size) {
			return new FreightInsurance[size];
		}
	};
}