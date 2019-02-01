package com.yaoguang.greendao.entity.company;


import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * App企业端业务下单实体
 *
 */
public class AppCompanyOrder implements Parcelable {

	public List<AppInfoClientPlace> getLoadClientPlaces() {
		return loadClientPlaces;
	}

	public void setLoadClientPlaces(List<AppInfoClientPlace> loadClientPlaces) {
		this.loadClientPlaces = loadClientPlaces;
	}

	public List<AppInfoClientPlace> getConsigneeClientPlaces() {
		return consigneeClientPlaces;
	}

	public void setConsigneeClientPlaces(List<AppInfoClientPlace> consigneeClientPlaces) {
		this.consigneeClientPlaces = consigneeClientPlaces;
	}

	private List<AppInfoClientPlace> loadClientPlaces;

	private List<AppInfoClientPlace> consigneeClientPlaces;

	private String id;

	private String userId;
	
	private String orderSn;

	private String shipperId;

	private String shipper;

	private String dockOfLoading;

	private String finalDestination;

	private String goodsName;

	private String carriageItem;

	private String owner;

	private String loadDate;

	private String portLoading;

	private String portDelivery;

	private String carriageWay;

	private String loadingId;

	private String consigneeId;

	private String isInsurance;
	private String insurValue;
	private String insurRate;
	private String insurMoney;

	private String sonos;

	private String fee;

	private String reamrk;

	private String reamrk1;
	private String reamrk2;
	private String reamrk3;

	private String createdBy;
	
	private String clientId;

	private String created;

	private String updated;

	private String isValid;

	private String status;

	public String getImportTime() {
		return importTime;
	}

	public void setImportTime(String importTime) {
		this.importTime = importTime;
	}

	private String importTime;

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}



	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getClientId() {
		return clientId;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

	public String getLoadDate() {
		return loadDate;
	}

	public void setLoadDate(String loadDate) {
		this.loadDate = loadDate;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id == null ? null : id.trim();
	}

	public String getOrderSn() {
		return orderSn;
	}

	public void setOrderSn(String orderSn) {
		this.orderSn = orderSn == null ? null : orderSn.trim();
	}

	public String getShipperId() {
		return shipperId;
	}

	public void setShipperId(String shipperId) {
		this.shipperId = shipperId == null ? null : shipperId.trim();
	}

	public String getShipper() {
		return shipper;
	}

	public void setShipper(String shipper) {
		this.shipper = shipper == null ? null : shipper.trim();
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

	public String getGoodsName() {
		return goodsName;
	}

	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName == null ? null : goodsName.trim();
	}

	public String getCarriageItem() {
		return carriageItem;
	}

	public void setCarriageItem(String carriageItem) {
		this.carriageItem = carriageItem == null ? null : carriageItem.trim();
	}

	public String getOwner() {
		return owner;
	}

	public void setOwner(String owner) {
		this.owner = owner == null ? null : owner.trim();
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

	public String getLoadingId() {
		return loadingId;
	}

	public void setLoadingId(String loadingId) {
		this.loadingId = loadingId == null ? null : loadingId.trim();
	}

	public String getConsigneeId() {
		return consigneeId;
	}

	public void setConsigneeId(String consigneeId) {
		this.consigneeId = consigneeId == null ? null : consigneeId.trim();
	}

	public String getIsInsurance() {
		return isInsurance;
	}

	public void setIsInsurance(String isInsurance) {
		this.isInsurance = isInsurance;
	}

	public String getSonos() {
		return sonos;
	}

	public void setSonos(String sonos) {
		this.sonos = sonos == null ? null : sonos.trim();
	}

	public String getFee() {
		return fee;
	}

	public void setFee(String fee) {
		this.fee = fee;
	}

	public String getReamrk() {
		return reamrk;
	}

	public void setReamrk(String reamrk) {
		this.reamrk = reamrk == null ? null : reamrk.trim();
	}

	public String getReamrk1() {
		return reamrk1;
	}

	public void setReamrk1(String reamrk1) {
		this.reamrk1 = reamrk1;
	}

	public String getReamrk2() {
		return reamrk2;
	}

	public void setReamrk2(String reamrk2) {
		this.reamrk2 = reamrk2;
	}

	public String getReamrk3() {
		return reamrk3;
	}

	public void setReamrk3(String reamrk3) {
		this.reamrk3 = reamrk3;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy == null ? null : createdBy.trim();
	}

	public String getCreated() {
		return created;
	}

	public void setCreated(String created) {
		this.created = created;
	}

	public String getUpdated() {
		return updated;
	}

	public void setUpdated(String updated) {
		this.updated = updated;
	}

	public String getIsValid() {
		return isValid;
	}

	public void setIsValid(String isValid) {
		this.isValid = isValid;
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

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeTypedList(this.loadClientPlaces);
		dest.writeTypedList(this.consigneeClientPlaces);
		dest.writeString(this.id);
		dest.writeString(this.userId);
		dest.writeString(this.orderSn);
		dest.writeString(this.shipperId);
		dest.writeString(this.shipper);
		dest.writeString(this.dockOfLoading);
		dest.writeString(this.finalDestination);
		dest.writeString(this.goodsName);
		dest.writeString(this.carriageItem);
		dest.writeString(this.owner);
		dest.writeString(this.loadDate);
		dest.writeString(this.portLoading);
		dest.writeString(this.portDelivery);
		dest.writeString(this.carriageWay);
		dest.writeString(this.loadingId);
		dest.writeString(this.consigneeId);
		dest.writeString(this.isInsurance);
		dest.writeString(this.insurValue);
		dest.writeString(this.insurRate);
		dest.writeString(this.insurMoney);
		dest.writeString(this.sonos);
		dest.writeString(this.fee);
		dest.writeString(this.reamrk);
		dest.writeString(this.reamrk1);
		dest.writeString(this.reamrk2);
		dest.writeString(this.reamrk3);
		dest.writeString(this.createdBy);
		dest.writeString(this.clientId);
		dest.writeString(this.created);
		dest.writeString(this.updated);
		dest.writeString(this.isValid);
		dest.writeString(this.status);
		dest.writeString(this.importTime);
	}

	public AppCompanyOrder() {
	}

	protected AppCompanyOrder(Parcel in) {
		this.loadClientPlaces = in.createTypedArrayList(AppInfoClientPlace.CREATOR);
		this.consigneeClientPlaces = in.createTypedArrayList(AppInfoClientPlace.CREATOR);
		this.id = in.readString();
		this.userId = in.readString();
		this.orderSn = in.readString();
		this.shipperId = in.readString();
		this.shipper = in.readString();
		this.dockOfLoading = in.readString();
		this.finalDestination = in.readString();
		this.goodsName = in.readString();
		this.carriageItem = in.readString();
		this.owner = in.readString();
		this.loadDate = in.readString();
		this.portLoading = in.readString();
		this.portDelivery = in.readString();
		this.carriageWay = in.readString();
		this.loadingId = in.readString();
		this.consigneeId = in.readString();
		this.isInsurance = in.readString();
		this.insurValue = in.readString();
		this.insurRate = in.readString();
		this.insurMoney = in.readString();
		this.sonos = in.readString();
		this.fee = in.readString();
		this.reamrk = in.readString();
		this.reamrk1 = in.readString();
		this.reamrk2 = in.readString();
		this.reamrk3 = in.readString();
		this.createdBy = in.readString();
		this.clientId = in.readString();
		this.created = in.readString();
		this.updated = in.readString();
		this.isValid = in.readString();
		this.status = in.readString();
		this.importTime = in.readString();
	}

	public static final Creator<AppCompanyOrder> CREATOR = new Creator<AppCompanyOrder>() {
		@Override
		public AppCompanyOrder createFromParcel(Parcel source) {
			return new AppCompanyOrder(source);
		}

		@Override
		public AppCompanyOrder[] newArray(int size) {
			return new AppCompanyOrder[size];
		}
	};
}