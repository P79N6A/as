package com.yaoguang.greendao.entity.common.ViewForwardOrderPack;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

public class FreightTruck implements Parcelable {

	private String id;

	private String billsId;

	private String preTruck;

	private String preShipCompany;

	private String loadDate;

	private String goodsNameE;

	private String destTruck;

	private String desShipCompany;

	private String releasePlanDate;

	private String goodsPriority;

	public String getGoodsPriorityZn() {
		return goodsPriorityZn;
	}

	public void setGoodsPriorityZn(String goodsPriorityZn) {
		this.goodsPriorityZn = goodsPriorityZn;
	}

	private String goodsPriorityZn;

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

	public String getPreTruck() {
		return preTruck;
	}

	public void setPreTruck(String preTruck) {
		this.preTruck = preTruck == null ? null : preTruck.trim();
	}

	public String getPreShipCompany() {
		return preShipCompany;
	}

	public void setPreShipCompany(String preShipCompany) {
		this.preShipCompany = preShipCompany == null ? null : preShipCompany.trim();
	}

	public String getLoadDate() {
		return loadDate;
	}

	public void setLoadDate(String loadDate) {
		this.loadDate = loadDate;
	}

	public String getGoodsNameE() {
		return goodsNameE;
	}

	public void setGoodsNameE(String goodsNameE) {
		this.goodsNameE = goodsNameE == null ? null : goodsNameE.trim();
	}

	public String getDestTruck() {
		return destTruck;
	}

	public void setDestTruck(String destTruck) {
		this.destTruck = destTruck == null ? null : destTruck.trim();
	}

	public String getDesShipCompany() {
		return desShipCompany;
	}

	public void setDesShipCompany(String desShipCompany) {
		this.desShipCompany = desShipCompany == null ? null : desShipCompany.trim();
	}

	public String getReleasePlanDate() {
		return releasePlanDate;
	}

	public void setReleasePlanDate(String releasePlanDate) {
		this.releasePlanDate = releasePlanDate;
	}

	public String getGoodsPriority() {
		return goodsPriority;
	}

	public void setGoodsPriority(String goodsPriority) {
		this.goodsPriority = goodsPriority == null ? null : goodsPriority.trim();
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
		dest.writeString(this.preTruck);
		dest.writeString(this.preShipCompany);
		dest.writeString(this.loadDate);
		dest.writeString(this.goodsNameE);
		dest.writeString(this.destTruck);
		dest.writeString(this.desShipCompany);
		dest.writeString(this.releasePlanDate);
		dest.writeString(this.goodsPriority);
		dest.writeString(this.goodsPriorityZn);
		dest.writeString(this.createdBy);
		dest.writeString(this.createdDeptId);
		dest.writeString(this.created);
		dest.writeString(this.updatedBy);
		dest.writeString(this.updatedDeptId);
		dest.writeString(this.updated);
	}

	public FreightTruck() {
	}

	protected FreightTruck(Parcel in) {
		this.id = in.readString();
		this.billsId = in.readString();
		this.preTruck = in.readString();
		this.preShipCompany = in.readString();
		this.loadDate = in.readString();
		this.goodsNameE = in.readString();
		this.destTruck = in.readString();
		this.desShipCompany = in.readString();
		this.releasePlanDate = in.readString();
		this.goodsPriority = in.readString();
		this.goodsPriorityZn = in.readString();
		this.createdBy = in.readString();
		this.createdDeptId = in.readString();
		this.created = in.readString();
		this.updatedBy = in.readString();
		this.updatedDeptId = in.readString();
		this.updated = in.readString();
	}

	public static final Parcelable.Creator<FreightTruck> CREATOR = new Parcelable.Creator<FreightTruck>() {
		@Override
		public FreightTruck createFromParcel(Parcel source) {
			return new FreightTruck(source);
		}

		@Override
		public FreightTruck[] newArray(int size) {
			return new FreightTruck[size];
		}
	};
}