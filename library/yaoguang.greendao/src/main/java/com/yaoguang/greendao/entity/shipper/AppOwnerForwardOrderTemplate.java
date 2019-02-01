package com.yaoguang.greendao.entity.shipper;


import android.os.Parcel;
import android.os.Parcelable;

import com.yaoguang.greendao.entity.company.AppInfoClientPlace;

import java.util.List;

/**
 * app货主端货代下单模板
 * @author wangxiaohui
 * 2018年4月12日
 */
public class AppOwnerForwardOrderTemplate implements Parcelable {

	private String id;

    private String userId;

    private String goodsName;

    private String owner;

    private String dockOfLoading;

    private String finalDestination;

    private String carriageItem;

    private String carriageWay;

    private String portLoading;

    private String portDelivery;

    private String loadInfos;

    private String consigneeInfos;

    private String isInsurance;

    private String remark;

    private String createdBy;

    private String created;

    private String updated;

    private Integer isValid;
	// 装卸货地址
	private List<AppInfoClientPlace> loadClientPlaces;

	// 卸货地址
	private List<AppInfoClientPlace> consigneeClientPlaces;

	// 关联公司名称
	private String officeName;

	// 关联公司id
	private String clientId;

    public String getOfficeName() {
        return officeName;
    }

    public void setOfficeName(String officeName) {
        this.officeName = officeName;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

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

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
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
        dest.writeString(this.remark);
        dest.writeString(this.createdBy);
        dest.writeString(this.created);
        dest.writeString(this.updated);
        dest.writeValue(this.isValid);
        dest.writeTypedList(this.loadClientPlaces);
        dest.writeTypedList(this.consigneeClientPlaces);
        dest.writeString(this.officeName);
        dest.writeString(this.clientId);
    }

    public AppOwnerForwardOrderTemplate() {
    }

    protected AppOwnerForwardOrderTemplate(Parcel in) {
        this.id = in.readString();
        this.userId = in.readString();
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
        this.remark = in.readString();
        this.createdBy = in.readString();
        this.created = in.readString();
        this.updated = in.readString();
        this.isValid = (Integer) in.readValue(Integer.class.getClassLoader());
        this.loadClientPlaces = in.createTypedArrayList(AppInfoClientPlace.CREATOR);
        this.consigneeClientPlaces = in.createTypedArrayList(AppInfoClientPlace.CREATOR);
        this.officeName = in.readString();
        this.clientId = in.readString();
    }

    public static final Creator<AppOwnerForwardOrderTemplate> CREATOR = new Creator<AppOwnerForwardOrderTemplate>() {
        @Override
        public AppOwnerForwardOrderTemplate createFromParcel(Parcel source) {
            return new AppOwnerForwardOrderTemplate(source);
        }

        @Override
        public AppOwnerForwardOrderTemplate[] newArray(int size) {
            return new AppOwnerForwardOrderTemplate[size];
        }
    };
}