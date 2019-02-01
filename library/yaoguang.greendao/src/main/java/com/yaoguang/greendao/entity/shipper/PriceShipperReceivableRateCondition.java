package com.yaoguang.greendao.entity.shipper;


import android.os.Parcel;
import android.os.Parcelable;

/**
 * App端应收托运人费率查询条件
 * 
 * @author wangxiaohui
 * @date 2017年6月9日 下午2:08:59
 */
public class PriceShipperReceivableRateCondition implements Parcelable {

	// 起运地
	private String dockLoading;
	
	// 目的地
	private String dockDevilery;

	// 托运人
	private String shipper;

	// 总公司编号
	private String clientId;

	// 分公司编号
	private String companyId;

	public String getShipper() {
		return shipper;
	}

	public void setShipper(String shipper) {
		this.shipper = shipper;
	}

	public String getDockLoading() {
		return dockLoading;
	}

	public void setDockLoading(String dockLoading) {
		this.dockLoading = dockLoading;
	}

	public String getDockDevilery() {
		return dockDevilery;
	}

	public void setDockDevilery(String dockDevilery) {
		this.dockDevilery = dockDevilery;
	}

	public String getClientId() {
		return clientId;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

	public String getCompanyId() {
		return companyId;
	}

	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(this.dockLoading);
		dest.writeString(this.dockDevilery);
		dest.writeString(this.shipper);
		dest.writeString(this.clientId);
		dest.writeString(this.companyId);
	}

	public PriceShipperReceivableRateCondition() {
	}

	protected PriceShipperReceivableRateCondition(Parcel in) {
		this.dockLoading = in.readString();
		this.dockDevilery = in.readString();
		this.shipper = in.readString();
		this.clientId = in.readString();
		this.companyId = in.readString();
	}

	public static final Parcelable.Creator<PriceShipperReceivableRateCondition> CREATOR = new Parcelable.Creator<PriceShipperReceivableRateCondition>() {
		@Override
		public PriceShipperReceivableRateCondition createFromParcel(Parcel source) {
			return new PriceShipperReceivableRateCondition(source);
		}

		@Override
		public PriceShipperReceivableRateCondition[] newArray(int size) {
			return new PriceShipperReceivableRateCondition[size];
		}
	};
}
