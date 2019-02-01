package com.yaoguang.greendao.entity.shipper;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 价格管理-应收托运人费率
 carriageItem = "DR-DR"
 clientId = "eb019c0cbd31481c832e9e9b891fd6e2"
 contId = "20GP"
 createBy = "admin@SZWL"
 created = "2018-08-29 14:44:27"
 createdDeptId = "eb019c0cbd31481c832e9e9b891fd6e2"
 departure = "黄埔东江仓"
 destination = "广州车陂"
 endDate = "2018-09-30 00:00:00"
 feeDetail = {ArrayList@7934}  size = 1
 feeId = "达驳费"
 fowardCompany = ""
 goodsName = "布匹"
 id = "7a95ed6984984e5ab2f3e440b406ad94"
 isBox = "0"
 isLcl = "0"
 isValid = "1"
 loadingAddress = "黄埔东江仓5号"
 money = "800"
 moneyStr = "800.00"
 offerId = "BJDH0002"
 officeName = ""
 paymentMethod = "D现金"
 portDelivery = "广州本港"
 portLoading = "东江仓"
 priceKey = ""
 remark = ""
 serviceType = "1"
 serviceTypeZn = ""
 shipCompany = "太阳井"
 shipper = "双子物流有限公司"
 startDate = "2018-08-29 00:00:00"
 typeZn = "货代"
 unloadingAddress = "业顺商务大厦"
 updated = ""
 updatedBy = ""
 updatedDeptId = ""
 versionId = "SZWL1808281725488931"
 * @author ZhangDeQuan
 * @time 2017年4月25日 下午2:14:34
 */
public class PriceShipperReceivableRate implements Parcelable {

	private String id;

	private String versionId;

	private String offerId;

	private String shipper;

	private String departure;

	private String destination;

	private String contId;

	private String feeId;

	private String money;

	private String carriageItem;

	private String shipCompany;

	private String fowardCompany;

	private String portLoading;

	private String portDelivery;

	private String serviceType;

	private String startDate;

	private String endDate;

	private String paymentMethod;

	private String goodsName;

	private String loadingAddress;

	private String unloadingAddress;

	private String remark;

	private String isLcl;

	private String isBox;

	private String priceKey;

	private String clientId;

	private String createBy;

	private String createdDeptId;

	private String created;

	private String updatedBy;

	private String updatedDeptId;

	private String updated;

	private String isValid;

	// 机构名称(分公司名称)
	private String officeName;
	
	private String serviceTypeZn;
	
	private String moneyStr;
	
	List<Map<String,Double>> feeDetail;

	private String typeZn;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getVersionId() {
		return versionId;
	}

	public void setVersionId(String versionId) {
		this.versionId = versionId;
	}

	public String getOfferId() {
		return offerId;
	}

	public void setOfferId(String offerId) {
		this.offerId = offerId;
	}

	public String getShipper() {
		return shipper;
	}

	public void setShipper(String shipper) {
		this.shipper = shipper;
	}

	public String getDeparture() {
		return departure;
	}

	public void setDeparture(String departure) {
		this.departure = departure;
	}

	public String getDestination() {
		return destination;
	}

	public void setDestination(String destination) {
		this.destination = destination;
	}

	public String getContId() {
		return contId;
	}

	public void setContId(String contId) {
		this.contId = contId;
	}

	public String getFeeId() {
		return feeId;
	}

	public void setFeeId(String feeId) {
		this.feeId = feeId;
	}

	public String getMoney() {
		return money;
	}

	public void setMoney(String money) {
		this.money = money;
	}

	public String getCarriageItem() {
		return carriageItem;
	}

	public void setCarriageItem(String carriageItem) {
		this.carriageItem = carriageItem;
	}

	public String getShipCompany() {
		return shipCompany;
	}

	public void setShipCompany(String shipCompany) {
		this.shipCompany = shipCompany;
	}

	public String getFowardCompany() {
		return fowardCompany;
	}

	public void setFowardCompany(String fowardCompany) {
		this.fowardCompany = fowardCompany;
	}

	public String getPortLoading() {
		return portLoading;
	}

	public void setPortLoading(String portLoading) {
		this.portLoading = portLoading;
	}

	public String getPortDelivery() {
		return portDelivery;
	}

	public void setPortDelivery(String portDelivery) {
		this.portDelivery = portDelivery;
	}

	public String getServiceType() {
		return serviceType;
	}

	public void setServiceType(String serviceType) {
		this.serviceType = serviceType;
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
		this.paymentMethod = paymentMethod;
	}

	public String getGoodsName() {
		return goodsName;
	}

	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}

	public String getLoadingAddress() {
		return loadingAddress;
	}

	public void setLoadingAddress(String loadingAddress) {
		this.loadingAddress = loadingAddress;
	}

	public String getUnloadingAddress() {
		return unloadingAddress;
	}

	public void setUnloadingAddress(String unloadingAddress) {
		this.unloadingAddress = unloadingAddress;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getIsLcl() {
		return isLcl;
	}

	public void setIsLcl(String isLcl) {
		this.isLcl = isLcl;
	}

	public String getIsBox() {
		return isBox;
	}

	public void setIsBox(String isBox) {
		this.isBox = isBox;
	}

	public String getPriceKey() {
		return priceKey;
	}

	public void setPriceKey(String priceKey) {
		this.priceKey = priceKey;
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
		this.createBy = createBy;
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

	public String getIsValid() {
		return isValid;
	}

	public void setIsValid(String isValid) {
		this.isValid = isValid;
	}

	public String getOfficeName() {
		return officeName;
	}

	public void setOfficeName(String officeName) {
		this.officeName = officeName;
	}

	public String getServiceTypeZn() {
		return serviceTypeZn;
	}

	public void setServiceTypeZn(String serviceTypeZn) {
		this.serviceTypeZn = serviceTypeZn;
	}

	public String getMoneyStr() {
		return moneyStr;
	}

	public void setMoneyStr(String moneyStr) {
		this.moneyStr = moneyStr;
	}

	public List<Map<String, Double>> getFeeDetail() {
		return feeDetail;
	}

	public void setFeeDetail(List<Map<String, Double>> feeDetail) {
		this.feeDetail = feeDetail;
	}

	public String getTypeZn() {
		return typeZn;
	}

	public void setTypeZn(String typeZn) {
		this.typeZn = typeZn;
	}


	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(this.id);
		dest.writeString(this.versionId);
		dest.writeString(this.offerId);
		dest.writeString(this.shipper);
		dest.writeString(this.departure);
		dest.writeString(this.destination);
		dest.writeString(this.contId);
		dest.writeString(this.feeId);
		dest.writeString(this.money);
		dest.writeString(this.carriageItem);
		dest.writeString(this.shipCompany);
		dest.writeString(this.fowardCompany);
		dest.writeString(this.portLoading);
		dest.writeString(this.portDelivery);
		dest.writeString(this.serviceType);
		dest.writeString(this.startDate);
		dest.writeString(this.endDate);
		dest.writeString(this.paymentMethod);
		dest.writeString(this.goodsName);
		dest.writeString(this.loadingAddress);
		dest.writeString(this.unloadingAddress);
		dest.writeString(this.remark);
		dest.writeString(this.isLcl);
		dest.writeString(this.isBox);
		dest.writeString(this.priceKey);
		dest.writeString(this.clientId);
		dest.writeString(this.createBy);
		dest.writeString(this.createdDeptId);
		dest.writeString(this.created);
		dest.writeString(this.updatedBy);
		dest.writeString(this.updatedDeptId);
		dest.writeString(this.updated);
		dest.writeString(this.isValid);
		dest.writeString(this.officeName);
		dest.writeString(this.serviceTypeZn);
		dest.writeString(this.moneyStr);
		dest.writeList(this.feeDetail);
		dest.writeString(this.typeZn);
	}

	public PriceShipperReceivableRate() {
	}

	protected PriceShipperReceivableRate(Parcel in) {
		this.id = in.readString();
		this.versionId = in.readString();
		this.offerId = in.readString();
		this.shipper = in.readString();
		this.departure = in.readString();
		this.destination = in.readString();
		this.contId = in.readString();
		this.feeId = in.readString();
		this.money = in.readString();
		this.carriageItem = in.readString();
		this.shipCompany = in.readString();
		this.fowardCompany = in.readString();
		this.portLoading = in.readString();
		this.portDelivery = in.readString();
		this.serviceType = in.readString();
		this.startDate = in.readString();
		this.endDate = in.readString();
		this.paymentMethod = in.readString();
		this.goodsName = in.readString();
		this.loadingAddress = in.readString();
		this.unloadingAddress = in.readString();
		this.remark = in.readString();
		this.isLcl = in.readString();
		this.isBox = in.readString();
		this.priceKey = in.readString();
		this.clientId = in.readString();
		this.createBy = in.readString();
		this.createdDeptId = in.readString();
		this.created = in.readString();
		this.updatedBy = in.readString();
		this.updatedDeptId = in.readString();
		this.updated = in.readString();
		this.isValid = in.readString();
		this.officeName = in.readString();
		this.serviceTypeZn = in.readString();
		this.moneyStr = in.readString();
		this.feeDetail = new ArrayList<Map<String, Double>>();
		in.readList(this.feeDetail, Map.class.getClassLoader());
		this.typeZn = in.readString();
	}

	public static final Parcelable.Creator<PriceShipperReceivableRate> CREATOR = new Parcelable.Creator<PriceShipperReceivableRate>() {
		@Override
		public PriceShipperReceivableRate createFromParcel(Parcel source) {
			return new PriceShipperReceivableRate(source);
		}

		@Override
		public PriceShipperReceivableRate[] newArray(int size) {
			return new PriceShipperReceivableRate[size];
		}
	};
}