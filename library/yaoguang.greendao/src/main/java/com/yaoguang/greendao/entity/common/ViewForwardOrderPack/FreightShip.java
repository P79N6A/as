package com.yaoguang.greendao.entity.common.ViewForwardOrderPack;


import android.os.Parcel;
import android.os.Parcelable;

public class FreightShip implements Parcelable {

	private String id;

	private String billsId;

	private String mBlNo;

	private String shipCompany;

	private String vessel;

	private String voyage;

	private String portLoading;

	private String portDelivery;

	private String carriageWay;

	private String inVesselPlanTime;

	private String etaPlan2;

	private String secondVessel;

	private String secondVoyage;

	private String secondEtd;

	private String secondEta;

	private Integer trangeOr;

	private String thirdVessel;

	private String thirdVoyage;

	private String thirdEtd;

	private String feederCompany;

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

	public String getmBlNo() {
		return mBlNo;
	}

	public void setmBlNo(String mBlNo) {
		this.mBlNo = mBlNo == null ? null : mBlNo.trim();
	}

	public String getShipCompany() {
		return shipCompany;
	}

	public void setShipCompany(String shipCompany) {
		this.shipCompany = shipCompany == null ? null : shipCompany.trim();
	}

	public String getVessel() {
		return vessel;
	}

	public void setVessel(String vessel) {
		this.vessel = vessel == null ? null : vessel.trim();
	}

	public String getVoyage() {
		return voyage;
	}

	public void setVoyage(String voyage) {
		this.voyage = voyage == null ? null : voyage.trim();
	}

	public String getPortLoading() {
		return portLoading;
	}

	public void setPortLoading(String portLoading) {
		this.portLoading = portLoading == null ? null : portLoading.trim();
	}

	public String getPortDelivery() {
		return portDelivery;
	}

	public void setPortDelivery(String portDelivery) {
		this.portDelivery = portDelivery == null ? null : portDelivery.trim();
	}

	public String getCarriageWay() {
		return carriageWay;
	}

	public void setCarriageWay(String carriageWay) {
		this.carriageWay = carriageWay == null ? null : carriageWay.trim();
	}

	public String getInVesselPlanTime() {
		return inVesselPlanTime;
	}

	public void setInVesselPlanTime(String inVesselPlanTime) {
		this.inVesselPlanTime = inVesselPlanTime;
	}

	public String getEtaPlan2() {
		return etaPlan2;
	}

	public void setEtaPlan2(String etaPlan2) {
		this.etaPlan2 = etaPlan2;
	}

	public String getSecondVessel() {
		return secondVessel;
	}

	public void setSecondVessel(String secondVessel) {
		this.secondVessel = secondVessel == null ? null : secondVessel.trim();
	}

	public String getSecondVoyage() {
		return secondVoyage;
	}

	public void setSecondVoyage(String secondVoyage) {
		this.secondVoyage = secondVoyage == null ? null : secondVoyage.trim();
	}

	public String getSecondEtd() {
		return secondEtd;
	}

	public void setSecondEtd(String secondEtd) {
		this.secondEtd = secondEtd;
	}

	public String getSecondEta() {
		return secondEta;
	}

	public void setSecondEta(String secondEta) {
		this.secondEta = secondEta;
	}

	public Integer getTrangeOr() {
		return trangeOr;
	}

	public void setTrangeOr(Integer trangeOr) {
		this.trangeOr = trangeOr;
	}

	public String getThirdVessel() {
		return thirdVessel;
	}

	public void setThirdVessel(String thirdVessel) {
		this.thirdVessel = thirdVessel == null ? null : thirdVessel.trim();
	}

	public String getThirdVoyage() {
		return thirdVoyage;
	}

	public void setThirdVoyage(String thirdVoyage) {
		this.thirdVoyage = thirdVoyage == null ? null : thirdVoyage.trim();
	}

	public String getThirdEtd() {
		return thirdEtd;
	}

	public void setThirdEtd(String thirdEtd) {
		this.thirdEtd = thirdEtd;
	}

	public String getFeederCompany() {
		return feederCompany;
	}

	public void setFeederCompany(String feederCompany) {
		this.feederCompany = feederCompany == null ? null : feederCompany.trim();
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
		dest.writeString(this.mBlNo);
		dest.writeString(this.shipCompany);
		dest.writeString(this.vessel);
		dest.writeString(this.voyage);
		dest.writeString(this.portLoading);
		dest.writeString(this.portDelivery);
		dest.writeString(this.carriageWay);
		dest.writeString(this.inVesselPlanTime);
		dest.writeString(this.etaPlan2);
		dest.writeString(this.secondVessel);
		dest.writeString(this.secondVoyage);
		dest.writeString(this.secondEtd);
		dest.writeString(this.secondEta);
		dest.writeValue(this.trangeOr);
		dest.writeString(this.thirdVessel);
		dest.writeString(this.thirdVoyage);
		dest.writeString(this.thirdEtd);
		dest.writeString(this.feederCompany);
		dest.writeString(this.createdBy);
		dest.writeString(this.createdDeptId);
		dest.writeString(this.created);
		dest.writeString(this.updatedBy);
		dest.writeString(this.updatedDeptId);
		dest.writeString(this.updated);
	}

	public FreightShip() {
	}

	protected FreightShip(Parcel in) {
		this.id = in.readString();
		this.billsId = in.readString();
		this.mBlNo = in.readString();
		this.shipCompany = in.readString();
		this.vessel = in.readString();
		this.voyage = in.readString();
		this.portLoading = in.readString();
		this.portDelivery = in.readString();
		this.carriageWay = in.readString();
		this.inVesselPlanTime = in.readString();
		this.etaPlan2 = in.readString();
		this.secondVessel = in.readString();
		this.secondVoyage = in.readString();
		this.secondEtd = in.readString();
		this.secondEta = in.readString();
		this.trangeOr = (Integer) in.readValue(Integer.class.getClassLoader());
		this.thirdVessel = in.readString();
		this.thirdVoyage = in.readString();
		this.thirdEtd = in.readString();
		this.feederCompany = in.readString();
		this.createdBy = in.readString();
		this.createdDeptId = in.readString();
		this.created = in.readString();
		this.updatedBy = in.readString();
		this.updatedDeptId = in.readString();
		this.updated = in.readString();
	}

	public static final Parcelable.Creator<FreightShip> CREATOR = new Parcelable.Creator<FreightShip>() {
		@Override
		public FreightShip createFromParcel(Parcel source) {
			return new FreightShip(source);
		}

		@Override
		public FreightShip[] newArray(int size) {
			return new FreightShip[size];
		}
	};
}