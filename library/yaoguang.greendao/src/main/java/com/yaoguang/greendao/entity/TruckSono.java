package com.yaoguang.greendao.entity;


import android.os.Parcel;
import android.os.Parcelable;

/**
 * 拖车-货柜信息
 * 
 * @author heyonggang
 * @date 2016年9月5日下午2:29:51
 */
public class TruckSono implements Parcelable {

	private String goodsSize;

	private String id;

    private String billsId;

    private String soId;

    private Integer sortno;

    private String contId;

    private String contNo;

    private String sealNo;

    private String carryPort;

    private String getPort;

    private Integer customStatus;

    private String customDate;

    private String pickupDate;

    private Integer isRepair;

    private String inRecord;

    private String outRecord;

    private Double shipperW;

    private Double netWeight;

    private Integer piecs;

    private String pack;

    private Double w1;

    private String remark1;

    private String consigneePlace;

    private String deliveryDate;

    private String damage;

    private String appointno;

    private String contposition;

    private String trucktype;

    private String truckno;

    private Integer tx;

    private String referenceNo;

    private Integer truckGoodsOr;

    private Integer delFlag;

    private String createdBy;

    private String createdDeptId;

    private String created;

    private String updatedBy;

    private String updatedDeptId;

    private String updated;

	public String getPcSonoId() {
		return pcSonoId;
	}

	public void setPcSonoId(String pcSonoId) {
		this.pcSonoId = pcSonoId;
	}

	private String pcSonoId;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getBillsId() {
		return billsId;
	}

	public void setBillsId(String billsId) {
		this.billsId = billsId;
	}

	public String getSoId() {
		return soId;
	}

	public void setSoId(String soId) {
		this.soId = soId;
	}

	public Integer getSortno() {
		return sortno;
	}

	public void setSortno(Integer sortno) {
		this.sortno = sortno;
	}

	public String getContId() {
		return contId;
	}

	public void setContId(String contId) {
		this.contId = contId;
	}

	public String getContNo() {
		return contNo;
	}

	public void setContNo(String contNo) {
		this.contNo = contNo;
	}

	public String getSealNo() {
		return sealNo;
	}

	public void setSealNo(String sealNo) {
		this.sealNo = sealNo;
	}

	public String getCarryPort() {
		return carryPort;
	}

	public void setCarryPort(String carryPort) {
		this.carryPort = carryPort;
	}

	public String getGetPort() {
		return getPort;
	}

	public void setGetPort(String getPort) {
		this.getPort = getPort;
	}

	public Integer getCustomStatus() {
		return customStatus;
	}

	public void setCustomStatus(Integer customStatus) {
		this.customStatus = customStatus;
	}

	public String getCustomDate() {
		return customDate;
	}

	public void setCustomDate(String customDate) {
		this.customDate = customDate;
	}

	public String getPickupDate() {
		return pickupDate;
	}

	public void setPickupDate(String pickupDate) {
		this.pickupDate = pickupDate;
	}

	public Integer getIsRepair() {
		return isRepair;
	}

	public void setIsRepair(Integer isRepair) {
		this.isRepair = isRepair;
	}

	public String getInRecord() {
		return inRecord;
	}

	public void setInRecord(String inRecord) {
		this.inRecord = inRecord;
	}

	public String getOutRecord() {
		return outRecord;
	}

	public void setOutRecord(String outRecord) {
		this.outRecord = outRecord;
	}

	public Double getShipperW() {
		return shipperW;
	}

	public void setShipperW(Double shipperW) {
		this.shipperW = shipperW;
	}

	public Double getNetWeight() {
		return netWeight;
	}

	public void setNetWeight(Double netWeight) {
		this.netWeight = netWeight;
	}

	public Integer getPiecs() {
		return piecs;
	}

	public void setPiecs(Integer piecs) {
		this.piecs = piecs;
	}

	public String getPack() {
		return pack;
	}

	public void setPack(String pack) {
		this.pack = pack;
	}

	public Double getW1() {
		return w1;
	}

	public void setW1(Double w1) {
		this.w1 = w1;
	}

	public String getRemark1() {
		return remark1;
	}

	public void setRemark1(String remark1) {
		this.remark1 = remark1;
	}

	public String getConsigneePlace() {
		return consigneePlace;
	}

	public void setConsigneePlace(String consigneePlace) {
		this.consigneePlace = consigneePlace;
	}

	public String getDeliveryDate() {
		return deliveryDate;
	}

	public void setDeliveryDate(String deliveryDate) {
		this.deliveryDate = deliveryDate;
	}

	public String getDamage() {
		return damage;
	}

	public void setDamage(String damage) {
		this.damage = damage;
	}

	public String getAppointno() {
		return appointno;
	}

	public void setAppointno(String appointno) {
		this.appointno = appointno;
	}

	public String getContposition() {
		return contposition;
	}

	public void setContposition(String contposition) {
		this.contposition = contposition;
	}

	public String getTrucktype() {
		return trucktype;
	}

	public void setTrucktype(String trucktype) {
		this.trucktype = trucktype;
	}

	public String getTruckno() {
		return truckno;
	}

	public void setTruckno(String truckno) {
		this.truckno = truckno;
	}

	public Integer getTx() {
		return tx;
	}

	public void setTx(Integer tx) {
		this.tx = tx;
	}

	public String getReferenceNo() {
		return referenceNo;
	}

	public void setReferenceNo(String referenceNo) {
		this.referenceNo = referenceNo;
	}

	public Integer getTruckGoodsOr() {
		return truckGoodsOr;
	}

	public void setTruckGoodsOr(Integer truckGoodsOr) {
		this.truckGoodsOr = truckGoodsOr;
	}

	public Integer getDelFlag() {
		return delFlag;
	}

	public void setDelFlag(Integer delFlag) {
		this.delFlag = delFlag;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public String getCreatedDeptId() {
		return createdDeptId;
	}

	public void setCreatedDeptId(String createdDeptId) {
		this.createdDeptId = createdDeptId;
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
		this.updatedBy = updatedBy;
	}

	public String getUpdatedDeptId() {
		return updatedDeptId;
	}

	public void setUpdatedDeptId(String updatedDeptId) {
		this.updatedDeptId = updatedDeptId;
	}

	public String getUpdated() {
		return updated;
	}

	public void setUpdated(String updated) {
		this.updated = updated;
	}


	public String getGoodsSize() {
		return goodsSize;
	}

	public void setGoodsSize(String goodsSize) {
		this.goodsSize = goodsSize;
	}


	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(this.goodsSize);
		dest.writeString(this.id);
		dest.writeString(this.billsId);
		dest.writeString(this.soId);
		dest.writeValue(this.sortno);
		dest.writeString(this.contId);
		dest.writeString(this.contNo);
		dest.writeString(this.sealNo);
		dest.writeString(this.carryPort);
		dest.writeString(this.getPort);
		dest.writeValue(this.customStatus);
		dest.writeString(this.customDate);
		dest.writeString(this.pickupDate);
		dest.writeValue(this.isRepair);
		dest.writeString(this.inRecord);
		dest.writeString(this.outRecord);
		dest.writeValue(this.shipperW);
		dest.writeValue(this.netWeight);
		dest.writeValue(this.piecs);
		dest.writeString(this.pack);
		dest.writeValue(this.w1);
		dest.writeString(this.remark1);
		dest.writeString(this.consigneePlace);
		dest.writeString(this.deliveryDate);
		dest.writeString(this.damage);
		dest.writeString(this.appointno);
		dest.writeString(this.contposition);
		dest.writeString(this.trucktype);
		dest.writeString(this.truckno);
		dest.writeValue(this.tx);
		dest.writeString(this.referenceNo);
		dest.writeValue(this.truckGoodsOr);
		dest.writeValue(this.delFlag);
		dest.writeString(this.createdBy);
		dest.writeString(this.createdDeptId);
		dest.writeString(this.created);
		dest.writeString(this.updatedBy);
		dest.writeString(this.updatedDeptId);
		dest.writeString(this.updated);
		dest.writeString(this.pcSonoId);
	}

	public TruckSono() {
	}

	protected TruckSono(Parcel in) {
		this.goodsSize = in.readString();
		this.id = in.readString();
		this.billsId = in.readString();
		this.soId = in.readString();
		this.sortno = (Integer) in.readValue(Integer.class.getClassLoader());
		this.contId = in.readString();
		this.contNo = in.readString();
		this.sealNo = in.readString();
		this.carryPort = in.readString();
		this.getPort = in.readString();
		this.customStatus = (Integer) in.readValue(Integer.class.getClassLoader());
		this.customDate = in.readString();
		this.pickupDate = in.readString();
		this.isRepair = (Integer) in.readValue(Integer.class.getClassLoader());
		this.inRecord = in.readString();
		this.outRecord = in.readString();
		this.shipperW = (Double) in.readValue(Double.class.getClassLoader());
		this.netWeight = (Double) in.readValue(Double.class.getClassLoader());
		this.piecs = (Integer) in.readValue(Integer.class.getClassLoader());
		this.pack = in.readString();
		this.w1 = (Double) in.readValue(Double.class.getClassLoader());
		this.remark1 = in.readString();
		this.consigneePlace = in.readString();
		this.deliveryDate = in.readString();
		this.damage = in.readString();
		this.appointno = in.readString();
		this.contposition = in.readString();
		this.trucktype = in.readString();
		this.truckno = in.readString();
		this.tx = (Integer) in.readValue(Integer.class.getClassLoader());
		this.referenceNo = in.readString();
		this.truckGoodsOr = (Integer) in.readValue(Integer.class.getClassLoader());
		this.delFlag = (Integer) in.readValue(Integer.class.getClassLoader());
		this.createdBy = in.readString();
		this.createdDeptId = in.readString();
		this.created = in.readString();
		this.updatedBy = in.readString();
		this.updatedDeptId = in.readString();
		this.updated = in.readString();
		this.pcSonoId = in.readString();
	}

	public static final Parcelable.Creator<TruckSono> CREATOR = new Parcelable.Creator<TruckSono>() {
		@Override
		public TruckSono createFromParcel(Parcel source) {
			return new TruckSono(source);
		}

		@Override
		public TruckSono[] newArray(int size) {
			return new TruckSono[size];
		}
	};
}