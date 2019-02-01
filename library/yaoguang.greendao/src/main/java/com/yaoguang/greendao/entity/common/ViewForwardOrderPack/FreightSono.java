package com.yaoguang.greendao.entity.common.ViewForwardOrderPack;


import android.os.Parcel;
import android.os.Parcelable;

public class FreightSono implements Parcelable {

	// 卸货派车单单号
	private String consigneeDriverId;

	// 卸货拖车单单号
	private String consigneeTruckId;

	// 装货派车单单号
	private String shipperDriverId;

	// 装货拖车单单号
	private String shipperTruckId;

	private String id;

	private String billsId;

	private String soId;

	private Integer sortno;

	private String soN;

	private String sonoDate;

	private Integer sonoStatus;

	private String contId;

	private String contNo;

	private String sealNo;

	private Integer ladingStatus;

	private String ladingDate;

	private Integer truckStatus;

	private Integer customStatus;

	private Integer truckGoodsOr;

	private Integer goodsReliveStatus;

	private String customDate;

	private String truckId;

	private String hackman;

	private String hackmanTel;

	private String remark5;

	private Double shipperW;

	private Double netWeight;

	private String remark1;

	private Integer isRepair;

	private String inRecord;

	private String outRecord;

	private Integer piecs;

	private String pack;

	private Double goodsValue;

	private Double w1;

	private String destTruckId;

	private String destHackman;

	private String destHackmanTel;

	private String destRemark5;

	private String consigneePlace;

	private String deliveryDate;

	private String loadingId;

	private String sealNoEncrypt;

	private String factory;

	private String goods;

	private String contractNo;

	private String referenceNo;

	private String carryPort;

	private String getPort;

	private String pickupDate;

	private String goodsSize;

	private String markNo;

	private Double quantity;

	private String goodsPosition;

	private String appointno;

	private String contposition;

	private String trucktype;

	private String truckno;

	private Integer tx;

	private String createdBy;

	private String createdDeptId;

	private String created;

	private String updatedBy;

	private String updatedDeptId;

	private String updated;

	public Double getWeight() {
		return weight;
	}

	public void setWeight(Double weight) {
		this.weight = weight;
	}

	private Double weight;

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
		this.id = id == null ? null : id.trim();
	}

	public String getBillsId() {
		return billsId;
	}

	public void setBillsId(String billsId) {
		this.billsId = billsId == null ? null : billsId.trim();
	}

	public String getSoId() {
		return soId;
	}

	public void setSoId(String soId) {
		this.soId = soId == null ? null : soId.trim();
	}

	public Integer getSortno() {
		return sortno;
	}

	public void setSortno(Integer sortno) {
		this.sortno = sortno;
	}

	public String getSoN() {
		return soN;
	}

	public void setSoN(String soN) {
		this.soN = soN == null ? null : soN.trim();
	}

	public String getSonoDate() {
		return sonoDate;
	}

	public void setSonoDate(String sonoDate) {
		this.sonoDate = sonoDate;
	}

	public Integer getSonoStatus() {
		return sonoStatus;
	}

	public void setSonoStatus(Integer sonoStatus) {
		this.sonoStatus = sonoStatus;
	}

	public String getContId() {
		return contId;
	}

	public void setContId(String contId) {
		this.contId = contId == null ? null : contId.trim();
	}

	public String getContNo() {
		return contNo;
	}

	public void setContNo(String contNo) {
		this.contNo = contNo == null ? null : contNo.trim();
	}

	public String getSealNo() {
		return sealNo;
	}

	public void setSealNo(String sealNo) {
		this.sealNo = sealNo == null ? null : sealNo.trim();
	}

	public Integer getLadingStatus() {
		return ladingStatus;
	}

	public void setLadingStatus(Integer ladingStatus) {
		this.ladingStatus = ladingStatus;
	}

	public String getLadingDate() {
		return ladingDate;
	}

	public void setLadingDate(String ladingDate) {
		this.ladingDate = ladingDate;
	}

	public Integer getTruckStatus() {
		return truckStatus;
	}

	public void setTruckStatus(Integer truckStatus) {
		this.truckStatus = truckStatus;
	}

	public Integer getCustomStatus() {
		return customStatus;
	}

	public void setCustomStatus(Integer customStatus) {
		this.customStatus = customStatus;
	}

	public Integer getTruckGoodsOr() {
		return truckGoodsOr;
	}

	public void setTruckGoodsOr(Integer truckGoodsOr) {
		this.truckGoodsOr = truckGoodsOr;
	}

	public Integer getGoodsReliveStatus() {
		return goodsReliveStatus;
	}

	public void setGoodsReliveStatus(Integer goodsReliveStatus) {
		this.goodsReliveStatus = goodsReliveStatus;
	}

	public String getCustomDate() {
		return customDate;
	}

	public void setCustomDate(String customDate) {
		this.customDate = customDate;
	}

	public String getTruckId() {
		return truckId;
	}

	public void setTruckId(String truckId) {
		this.truckId = truckId == null ? null : truckId.trim();
	}

	public String getHackman() {
		return hackman;
	}

	public void setHackman(String hackman) {
		this.hackman = hackman == null ? null : hackman.trim();
	}

	public String getHackmanTel() {
		return hackmanTel;
	}

	public void setHackmanTel(String hackmanTel) {
		this.hackmanTel = hackmanTel == null ? null : hackmanTel.trim();
	}

	public String getRemark5() {
		return remark5;
	}

	public void setRemark5(String remark5) {
		this.remark5 = remark5 == null ? null : remark5.trim();
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

	public String getRemark1() {
		return remark1;
	}

	public void setRemark1(String remark1) {
		this.remark1 = remark1 == null ? null : remark1.trim();
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
		this.inRecord = inRecord == null ? null : inRecord.trim();
	}

	public String getOutRecord() {
		return outRecord;
	}

	public void setOutRecord(String outRecord) {
		this.outRecord = outRecord == null ? null : outRecord.trim();
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
		this.pack = pack == null ? null : pack.trim();
	}

	public Double getGoodsValue() {
		return goodsValue;
	}

	public void setGoodsValue(Double goodsValue) {
		this.goodsValue = goodsValue;
	}

	public Double getW1() {
		return w1;
	}

	public void setW1(Double w1) {
		this.w1 = w1;
	}

	public String getDestTruckId() {
		return destTruckId;
	}

	public void setDestTruckId(String destTruckId) {
		this.destTruckId = destTruckId == null ? null : destTruckId.trim();
	}

	public String getDestHackman() {
		return destHackman;
	}

	public void setDestHackman(String destHackman) {
		this.destHackman = destHackman == null ? null : destHackman.trim();
	}

	public String getDestHackmanTel() {
		return destHackmanTel;
	}

	public void setDestHackmanTel(String destHackmanTel) {
		this.destHackmanTel = destHackmanTel == null ? null : destHackmanTel.trim();
	}

	public String getDestRemark5() {
		return destRemark5;
	}

	public void setDestRemark5(String destRemark5) {
		this.destRemark5 = destRemark5 == null ? null : destRemark5.trim();
	}

	public String getConsigneePlace() {
		return consigneePlace;
	}

	public void setConsigneePlace(String consigneePlace) {
		this.consigneePlace = consigneePlace == null ? null : consigneePlace.trim();
	}

	public String getDeliveryDate() {
		return deliveryDate;
	}

	public void setDeliveryDate(String deliveryDate) {
		this.deliveryDate = deliveryDate;
	}

	public String getLoadingId() {
		return loadingId;
	}

	public void setLoadingId(String loadingId) {
		this.loadingId = loadingId == null ? null : loadingId.trim();
	}

	public String getSealNoEncrypt() {
		return sealNoEncrypt;
	}

	public void setSealNoEncrypt(String sealNoEncrypt) {
		this.sealNoEncrypt = sealNoEncrypt == null ? null : sealNoEncrypt.trim();
	}

	public String getFactory() {
		return factory;
	}

	public void setFactory(String factory) {
		this.factory = factory == null ? null : factory.trim();
	}

	public String getGoods() {
		return goods;
	}

	public void setGoods(String goods) {
		this.goods = goods == null ? null : goods.trim();
	}

	public String getContractNo() {
		return contractNo;
	}

	public void setContractNo(String contractNo) {
		this.contractNo = contractNo == null ? null : contractNo.trim();
	}

	public String getReferenceNo() {
		return referenceNo;
	}

	public void setReferenceNo(String referenceNo) {
		this.referenceNo = referenceNo == null ? null : referenceNo.trim();
	}

	public String getCarryPort() {
		return carryPort;
	}

	public void setCarryPort(String carryPort) {
		this.carryPort = carryPort == null ? null : carryPort.trim();
	}

	public String getGetPort() {
		return getPort;
	}

	public void setGetPort(String getPort) {
		this.getPort = getPort == null ? null : getPort.trim();
	}

	public String getPickupDate() {
		return pickupDate;
	}

	public void setPickupDate(String pickupDate) {
		this.pickupDate = pickupDate;
	}

	public String getGoodsSize() {
		return goodsSize;
	}

	public void setGoodsSize(String goodsSize) {
		this.goodsSize = goodsSize == null ? null : goodsSize.trim();
	}

	public String getMarkNo() {
		return markNo;
	}

	public void setMarkNo(String markNo) {
		this.markNo = markNo == null ? null : markNo.trim();
	}

	public Double getQuantity() {
		return quantity;
	}

	public void setQuantity(Double quantity) {
		this.quantity = quantity;
	}

	public String getGoodsPosition() {
		return goodsPosition;
	}

	public void setGoodsPosition(String goodsPosition) {
		this.goodsPosition = goodsPosition == null ? null : goodsPosition.trim();
	}

	public String getAppointno() {
		return appointno;
	}

	public void setAppointno(String appointno) {
		this.appointno = appointno == null ? null : appointno.trim();
	}

	public String getContposition() {
		return contposition;
	}

	public void setContposition(String contposition) {
		this.contposition = contposition == null ? null : contposition.trim();
	}

	public String getTrucktype() {
		return trucktype;
	}

	public void setTrucktype(String trucktype) {
		this.trucktype = trucktype == null ? null : trucktype.trim();
	}

	public String getTruckno() {
		return truckno;
	}

	public void setTruckno(String truckno) {
		this.truckno = truckno == null ? null : truckno.trim();
	}

	public Integer getTx() {
		return tx;
	}

	public void setTx(Integer tx) {
		this.tx = tx;
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

	public String getConsigneeDriverId() {
		return consigneeDriverId;
	}

	public void setConsigneeDriverId(String consigneeDriverId) {
		this.consigneeDriverId = consigneeDriverId;
	}

	public String getConsigneeTruckId() {
		return consigneeTruckId;
	}

	public void setConsigneeTruckId(String consigneeTruckId) {
		this.consigneeTruckId = consigneeTruckId;
	}

	public String getShipperDriverId() {
		return shipperDriverId;
	}

	public void setShipperDriverId(String shipperDriverId) {
		this.shipperDriverId = shipperDriverId;
	}

	public String getShipperTruckId() {
		return shipperTruckId;
	}

	public void setShipperTruckId(String shipperTruckId) {
		this.shipperTruckId = shipperTruckId;
	}


	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(this.consigneeDriverId);
		dest.writeString(this.consigneeTruckId);
		dest.writeString(this.shipperDriverId);
		dest.writeString(this.shipperTruckId);
		dest.writeString(this.id);
		dest.writeString(this.billsId);
		dest.writeString(this.soId);
		dest.writeValue(this.sortno);
		dest.writeString(this.soN);
		dest.writeString(this.sonoDate);
		dest.writeValue(this.sonoStatus);
		dest.writeString(this.contId);
		dest.writeString(this.contNo);
		dest.writeString(this.sealNo);
		dest.writeValue(this.ladingStatus);
		dest.writeString(this.ladingDate);
		dest.writeValue(this.truckStatus);
		dest.writeValue(this.customStatus);
		dest.writeValue(this.truckGoodsOr);
		dest.writeValue(this.goodsReliveStatus);
		dest.writeString(this.customDate);
		dest.writeString(this.truckId);
		dest.writeString(this.hackman);
		dest.writeString(this.hackmanTel);
		dest.writeString(this.remark5);
		dest.writeValue(this.shipperW);
		dest.writeValue(this.netWeight);
		dest.writeString(this.remark1);
		dest.writeValue(this.isRepair);
		dest.writeString(this.inRecord);
		dest.writeString(this.outRecord);
		dest.writeValue(this.piecs);
		dest.writeString(this.pack);
		dest.writeValue(this.goodsValue);
		dest.writeValue(this.w1);
		dest.writeString(this.destTruckId);
		dest.writeString(this.destHackman);
		dest.writeString(this.destHackmanTel);
		dest.writeString(this.destRemark5);
		dest.writeString(this.consigneePlace);
		dest.writeString(this.deliveryDate);
		dest.writeString(this.loadingId);
		dest.writeString(this.sealNoEncrypt);
		dest.writeString(this.factory);
		dest.writeString(this.goods);
		dest.writeString(this.contractNo);
		dest.writeString(this.referenceNo);
		dest.writeString(this.carryPort);
		dest.writeString(this.getPort);
		dest.writeString(this.pickupDate);
		dest.writeString(this.goodsSize);
		dest.writeString(this.markNo);
		dest.writeValue(this.quantity);
		dest.writeString(this.goodsPosition);
		dest.writeString(this.appointno);
		dest.writeString(this.contposition);
		dest.writeString(this.trucktype);
		dest.writeString(this.truckno);
		dest.writeValue(this.tx);
		dest.writeString(this.createdBy);
		dest.writeString(this.createdDeptId);
		dest.writeString(this.created);
		dest.writeString(this.updatedBy);
		dest.writeString(this.updatedDeptId);
		dest.writeString(this.updated);
		dest.writeString(this.pcSonoId);
	}

	public FreightSono() {
	}

	protected FreightSono(Parcel in) {
		this.consigneeDriverId = in.readString();
		this.consigneeTruckId = in.readString();
		this.shipperDriverId = in.readString();
		this.shipperTruckId = in.readString();
		this.id = in.readString();
		this.billsId = in.readString();
		this.soId = in.readString();
		this.sortno = (Integer) in.readValue(Integer.class.getClassLoader());
		this.soN = in.readString();
		this.sonoDate = in.readString();
		this.sonoStatus = (Integer) in.readValue(Integer.class.getClassLoader());
		this.contId = in.readString();
		this.contNo = in.readString();
		this.sealNo = in.readString();
		this.ladingStatus = (Integer) in.readValue(Integer.class.getClassLoader());
		this.ladingDate = in.readString();
		this.truckStatus = (Integer) in.readValue(Integer.class.getClassLoader());
		this.customStatus = (Integer) in.readValue(Integer.class.getClassLoader());
		this.truckGoodsOr = (Integer) in.readValue(Integer.class.getClassLoader());
		this.goodsReliveStatus = (Integer) in.readValue(Integer.class.getClassLoader());
		this.customDate = in.readString();
		this.truckId = in.readString();
		this.hackman = in.readString();
		this.hackmanTel = in.readString();
		this.remark5 = in.readString();
		this.shipperW = (Double) in.readValue(Double.class.getClassLoader());
		this.netWeight = (Double) in.readValue(Double.class.getClassLoader());
		this.remark1 = in.readString();
		this.isRepair = (Integer) in.readValue(Integer.class.getClassLoader());
		this.inRecord = in.readString();
		this.outRecord = in.readString();
		this.piecs = (Integer) in.readValue(Integer.class.getClassLoader());
		this.pack = in.readString();
		this.goodsValue = (Double) in.readValue(Double.class.getClassLoader());
		this.w1 = (Double) in.readValue(Double.class.getClassLoader());
		this.destTruckId = in.readString();
		this.destHackman = in.readString();
		this.destHackmanTel = in.readString();
		this.destRemark5 = in.readString();
		this.consigneePlace = in.readString();
		this.deliveryDate = in.readString();
		this.loadingId = in.readString();
		this.sealNoEncrypt = in.readString();
		this.factory = in.readString();
		this.goods = in.readString();
		this.contractNo = in.readString();
		this.referenceNo = in.readString();
		this.carryPort = in.readString();
		this.getPort = in.readString();
		this.pickupDate = in.readString();
		this.goodsSize = in.readString();
		this.markNo = in.readString();
		this.quantity = (Double) in.readValue(Double.class.getClassLoader());
		this.goodsPosition = in.readString();
		this.appointno = in.readString();
		this.contposition = in.readString();
		this.trucktype = in.readString();
		this.truckno = in.readString();
		this.tx = (Integer) in.readValue(Integer.class.getClassLoader());
		this.createdBy = in.readString();
		this.createdDeptId = in.readString();
		this.created = in.readString();
		this.updatedBy = in.readString();
		this.updatedDeptId = in.readString();
		this.updated = in.readString();
		this.pcSonoId = in.readString();
	}

	public static final Parcelable.Creator<FreightSono> CREATOR = new Parcelable.Creator<FreightSono>() {
		@Override
		public FreightSono createFromParcel(Parcel source) {
			return new FreightSono(source);
		}

		@Override
		public FreightSono[] newArray(int size) {
			return new FreightSono[size];
		}
	};
}