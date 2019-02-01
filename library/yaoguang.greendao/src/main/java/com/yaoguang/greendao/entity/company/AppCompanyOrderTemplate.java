package com.yaoguang.greendao.entity.company;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * App企业端货代业务下单模板实体
 * 
 * @author wangxiaohui
 * @date 2017年11月7日 下午3:28:37
 */
public class AppCompanyOrderTemplate implements Parcelable {

	private String id;

	// 用户id
	private String userId;

	// 托运人
	private String shipper;

	// 托运人id
	private String shipperId;

	// 货名
	private String goodsName;

	// 货主
	private String owner;

	// 起运地
	private String dockOfLoading;

	// 目的地
	private String finalDestination;

	// 操作条款
	private String carriageItem;

	// 运输条款
	private String carriageWay;

	// 起运港
	private String portLoading;

	// 目的港
	private String portDelivery;

	// 装货地址
	private String loadInfos;

	// 卸货地址
	private String consigneeInfos;
	
	//是否保险（0-否，1-是）
	private String isInsurance;

	private String clientId;

	private String remark;

	private String createdDeptId;

	private String createdBy;

	private String created;

	private String updated;

	private Integer isValid;

	// 装卸货地址
	private List<AppInfoClientPlace> loadClientPlaces;

	// 卸货地址
	private List<AppInfoClientPlace> consigneeClientPlaces;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getShipper() {
		return shipper;
	}

	public void setShipper(String shipper) {
		this.shipper = shipper;
	}

	public String getShipperId() {
		return shipperId;
	}

	public void setShipperId(String shipperId) {
		this.shipperId = shipperId;
	}

	public String getGoodsName() {
		return goodsName;
	}

	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}

	public String getOwner() {
		return owner;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}

	public String getDockOfLoading() {
		return dockOfLoading;
	}

	public void setDockOfLoading(String dockOfLoading) {
		this.dockOfLoading = dockOfLoading;
	}

	public String getFinalDestination() {
		return finalDestination;
	}

	public void setFinalDestination(String finalDestination) {
		this.finalDestination = finalDestination;
	}

	public String getCarriageItem() {
		return carriageItem;
	}

	public void setCarriageItem(String carriageItem) {
		this.carriageItem = carriageItem;
	}

	public String getCarriageWay() {
		return carriageWay;
	}

	public void setCarriageWay(String carriageWay) {
		this.carriageWay = carriageWay;
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

	public String getLoadInfos() {
		return loadInfos;
	}

	public void setLoadInfos(String loadInfos) {
		this.loadInfos = loadInfos;
	}

	public String getConsigneeInfos() {
		return consigneeInfos;
	}

	public void setConsigneeInfos(String consigneeInfos) {
		this.consigneeInfos = consigneeInfos;
	}

	public String getIsInsurance() {
		return isInsurance;
	}

	public void setIsInsurance(String isInsurance) {
		this.isInsurance = isInsurance;
	}

	public String getClientId() {
		return clientId;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getCreatedDeptId() {
		return createdDeptId;
	}

	public void setCreatedDeptId(String createdDeptId) {
		this.createdDeptId = createdDeptId;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
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

	public Integer getIsValid() {
		return isValid;
	}

	public void setIsValid(Integer isValid) {
		this.isValid = isValid;
	}

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


	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(this.id);
		dest.writeString(this.userId);
		dest.writeString(this.shipper);
		dest.writeString(this.shipperId);
		dest.writeString(this.goodsName);
		dest.writeString(this.owner);
		dest.writeString(this.dockOfLoading);
		dest.writeString(this.finalDestination);
		dest.writeString(this.carriageItem);
		dest.writeString(this.carriageWay);
		dest.writeString(this.portLoading);
		dest.writeString(this.portDelivery);
		dest.writeString(this.loadInfos);
		dest.writeString(this.consigneeInfos);
		dest.writeString(this.isInsurance);
		dest.writeString(this.clientId);
		dest.writeString(this.remark);
		dest.writeString(this.createdDeptId);
		dest.writeString(this.createdBy);
		dest.writeString(this.created);
		dest.writeString(this.updated);
		dest.writeValue(this.isValid);
		dest.writeTypedList(this.loadClientPlaces);
		dest.writeTypedList(this.consigneeClientPlaces);
	}

	public AppCompanyOrderTemplate() {
	}

	protected AppCompanyOrderTemplate(Parcel in) {
		this.id = in.readString();
		this.userId = in.readString();
		this.shipper = in.readString();
		this.shipperId = in.readString();
		this.goodsName = in.readString();
		this.owner = in.readString();
		this.dockOfLoading = in.readString();
		this.finalDestination = in.readString();
		this.carriageItem = in.readString();
		this.carriageWay = in.readString();
		this.portLoading = in.readString();
		this.portDelivery = in.readString();
		this.loadInfos = in.readString();
		this.consigneeInfos = in.readString();
		this.isInsurance = in.readString();
		this.clientId = in.readString();
		this.remark = in.readString();
		this.createdDeptId = in.readString();
		this.createdBy = in.readString();
		this.created = in.readString();
		this.updated = in.readString();
		this.isValid = (Integer) in.readValue(Integer.class.getClassLoader());
		this.loadClientPlaces = in.createTypedArrayList(AppInfoClientPlace.CREATOR);
		this.consigneeClientPlaces = in.createTypedArrayList(AppInfoClientPlace.CREATOR);
	}

	public static final Creator<AppCompanyOrderTemplate> CREATOR = new Creator<AppCompanyOrderTemplate>() {
		@Override
		public AppCompanyOrderTemplate createFromParcel(Parcel source) {
			return new AppCompanyOrderTemplate(source);
		}

		@Override
		public AppCompanyOrderTemplate[] newArray(int size) {
			return new AppCompanyOrderTemplate[size];
		}
	};
}