package com.yaoguang.greendao.entity.common;

import android.os.Parcel;
import android.os.Parcelable;

import com.yaoguang.greendao.entity.AppLogBillTrackWrapper;
import com.yaoguang.greendao.entity.common.ViewForwardOrderPack.AccountFee;
import com.yaoguang.greendao.entity.common.ViewForwardOrderPack.FreightBills;
import com.yaoguang.greendao.entity.common.ViewForwardOrderPack.FreightConsignee;
import com.yaoguang.greendao.entity.common.ViewForwardOrderPack.FreightInsurance;
import com.yaoguang.greendao.entity.common.ViewForwardOrderPack.FreightOther;
import com.yaoguang.greendao.entity.common.ViewForwardOrderPack.FreightShip;
import com.yaoguang.greendao.entity.common.ViewForwardOrderPack.FreightShipper;
import com.yaoguang.greendao.entity.common.ViewForwardOrderPack.FreightSono;
import com.yaoguang.greendao.entity.common.ViewForwardOrderPack.FreightTruck;
import com.yaoguang.greendao.entity.common.ViewForwardOrderPack.LogBillsTrack;
import com.yaoguang.greendao.entity.common.ViewForwardOrderPack.FreightSonoUsed;
import com.yaoguang.greendao.entity.common.ViewForwardOrderPack.Remark;

import java.util.ArrayList;
import java.util.List;
/**
 * 货代-订单管理信息
 * 
 * @author ZhangDeQuan
 * @time 2016年8月17日 上午11:14:12
 */
public class ViewForwardOrder implements Parcelable {

	// 工作单主信息
	private FreightBills freightBills;

	// 工作单卸货信息
	private List<FreightConsignee> freightConsigneeList;

	private FreightConsignee freightConsignees;

	// 工作单保险信息
	private FreightInsurance freightInsurance;

	// 工作单其他信息
	private FreightOther freightOther;

	// 工作单船舶信息
	private FreightShip freightShip;

	// 工作单预订舱使用记录信息
	private FreightSonoUsed freightSonoUsed;

	// 工作单装货信息
	private List<FreightShipper> freightShipperList;

	private FreightShipper freightShippers;

	// 工作单货柜信息
	private List<FreightSono> freightSonoList;

	private FreightSono freightSonos;

	// 工作单拖车信息
	private FreightTruck freightTruck;

	// 工作单业务状态跟踪信息
	private LogBillsTrack logBillsTrack;

	// 工作单备注信息
	private Remark remark;

	// 托运人总欠款
	private Double totalFee;

	// 工作单毛利
	private Double gross;

	// 工作单合计毛重(货柜毛重合计)
	private Double grossWeight;

	// teu总计
	private Double teu;

	// 单柜毛利
	private Double sonoGross;

	// 应收托运人运费
	private Double shipperMoney;

	// 获取应收托运人费用（供短信获取）
	private Double fee = 0.0D;

	private Integer count20gp = 0;

	private Integer count40gp = 0;

	private Integer count45gp = 0;

	private Integer count40hq = 0;

	private Integer count40rf = 0;

	// 费用项目-始驳费
	private Double startFee;

	// 费用项目-达驳费
	private Double endFee;

	// 代收款
	private Double collectionFee;

	// 工作单柜号
	private String guihao;

	// 柜号/封号
	private String guiAndFeng;

	// 柜型/柜量
	private String conQty;

	// 封号
	private String fenghao;
	// 货柜跟踪
	private List<AppLogBillTrackWrapper> appLogBillTrackWrappers;

	// 协作人信息
	private String collaborator;

	// 装货地址信息汇总
	private String orderLoadingAddrs;

	// 卸货地址信息汇总
	private String orderUnloadingAddrs;

	//拖车资料汇总
	private String truckData;

	//工作单托运人关联往来单位客户等级(1-一般客户，2-基础客户，3-重点客户，4-VIP客户)
	private String customerLevel;

	public FreightBills getFreightBills() {
		return freightBills;
	}

	public void setFreightBills(FreightBills freightBills) {
		this.freightBills = freightBills;
	}

	public List<FreightConsignee> getFreightConsigneeList() {
		return freightConsigneeList;
	}

	public void setFreightConsigneeList(List<FreightConsignee> freightConsigneeList) {
		this.freightConsigneeList = freightConsigneeList;
	}

	public FreightConsignee getFreightConsignees() {
		return freightConsignees;
	}

	public void setFreightConsignees(FreightConsignee freightConsignees) {
		this.freightConsignees = freightConsignees;
	}

	public FreightInsurance getFreightInsurance() {
		return freightInsurance;
	}

	public void setFreightInsurance(FreightInsurance freightInsurance) {
		this.freightInsurance = freightInsurance;
	}

	public FreightOther getFreightOther() {
		return freightOther;
	}

	public void setFreightOther(FreightOther freightOther) {
		this.freightOther = freightOther;
	}

	public FreightShip getFreightShip() {
		return freightShip;
	}

	public void setFreightShip(FreightShip freightShip) {
		this.freightShip = freightShip;
	}

	public FreightSonoUsed getFreightSonoUsed() {
		return freightSonoUsed;
	}

	public void setFreightSonoUsed(FreightSonoUsed freightSonoUsed) {
		this.freightSonoUsed = freightSonoUsed;
	}

	public List<FreightShipper> getFreightShipperList() {
		return freightShipperList;
	}

	public void setFreightShipperList(List<FreightShipper> freightShipperList) {
		this.freightShipperList = freightShipperList;
	}

	public FreightShipper getFreightShippers() {
		return freightShippers;
	}

	public void setFreightShippers(FreightShipper freightShippers) {
		this.freightShippers = freightShippers;
	}

	public List<FreightSono> getFreightSonoList() {
		return freightSonoList;
	}

	public void setFreightSonoList(List<FreightSono> freightSonoList) {
		this.freightSonoList = freightSonoList;
	}

	public FreightSono getFreightSonos() {
		return freightSonos;
	}

	public void setFreightSonos(FreightSono freightSonos) {
		this.freightSonos = freightSonos;
	}

	public FreightTruck getFreightTruck() {
		return freightTruck;
	}

	public void setFreightTruck(FreightTruck freightTruck) {
		this.freightTruck = freightTruck;
	}

	public LogBillsTrack getLogBillsTrack() {
		return logBillsTrack;
	}

	public void setLogBillsTrack(LogBillsTrack logBillsTrack) {
		this.logBillsTrack = logBillsTrack;
	}

	public Remark getRemark() {
		return remark;
	}

	public void setRemark(Remark remark) {
		this.remark = remark;
	}

	public Double getTotalFee() {
		return totalFee;
	}

	public void setTotalFee(Double totalFee) {
		this.totalFee = totalFee;
	}

	public Double getGross() {
		return gross;
	}

	public void setGross(Double gross) {
		this.gross = gross;
	}

	public Double getGrossWeight() {
		return grossWeight;
	}

	public void setGrossWeight(Double grossWeight) {
		this.grossWeight = grossWeight;
	}

	public Double getTeu() {
		return teu;
	}

	public void setTeu(Double teu) {
		this.teu = teu;
	}

	public Double getSonoGross() {
		return sonoGross;
	}

	public void setSonoGross(Double sonoGross) {
		this.sonoGross = sonoGross;
	}

	public Double getShipperMoney() {
		return shipperMoney;
	}

	public void setShipperMoney(Double shipperMoney) {
		this.shipperMoney = shipperMoney;
	}

	public Double getFee() {
		return fee;
	}

	public void setFee(Double fee) {
		this.fee = fee;
	}

	public Integer getCount20gp() {
		return count20gp;
	}

	public void setCount20gp(Integer count20gp) {
		this.count20gp = count20gp;
	}

	public Integer getCount40gp() {
		return count40gp;
	}

	public void setCount40gp(Integer count40gp) {
		this.count40gp = count40gp;
	}

	public Integer getCount45gp() {
		return count45gp;
	}

	public void setCount45gp(Integer count45gp) {
		this.count45gp = count45gp;
	}

	public Integer getCount40hq() {
		return count40hq;
	}

	public void setCount40hq(Integer count40hq) {
		this.count40hq = count40hq;
	}

	public Integer getCount40rf() {
		return count40rf;
	}

	public void setCount40rf(Integer count40rf) {
		this.count40rf = count40rf;
	}

	public Double getStartFee() {
		return startFee;
	}

	public void setStartFee(Double startFee) {
		this.startFee = startFee;
	}

	public Double getEndFee() {
		return endFee;
	}

	public void setEndFee(Double endFee) {
		this.endFee = endFee;
	}

	public Double getCollectionFee() {
		return collectionFee;
	}

	public void setCollectionFee(Double collectionFee) {
		this.collectionFee = collectionFee;
	}

	public String getGuihao() {
		return guihao;
	}

	public void setGuihao(String guihao) {
		this.guihao = guihao;
	}

	public String getGuiAndFeng() {
		return guiAndFeng;
	}

	public void setGuiAndFeng(String guiAndFeng) {
		this.guiAndFeng = guiAndFeng;
	}

	public String getConQty() {
		return conQty;
	}

	public void setConQty(String conQty) {
		this.conQty = conQty;
	}

	public String getFenghao() {
		return fenghao;
	}

	public void setFenghao(String fenghao) {
		this.fenghao = fenghao;
	}

	public List<AppLogBillTrackWrapper> getAppLogBillTrackWrappers() {
		return appLogBillTrackWrappers;
	}

	public void setAppLogBillTrackWrappers(List<AppLogBillTrackWrapper> appLogBillTrackWrappers) {
		this.appLogBillTrackWrappers = appLogBillTrackWrappers;
	}

	public String getCollaborator() {
		return collaborator;
	}

	public void setCollaborator(String collaborator) {
		this.collaborator = collaborator;
	}

	public String getOrderLoadingAddrs() {
		return orderLoadingAddrs;
	}

	public void setOrderLoadingAddrs(String orderLoadingAddrs) {
		this.orderLoadingAddrs = orderLoadingAddrs;
	}

	public String getOrderUnloadingAddrs() {
		return orderUnloadingAddrs;
	}

	public void setOrderUnloadingAddrs(String orderUnloadingAddrs) {
		this.orderUnloadingAddrs = orderUnloadingAddrs;
	}

	public String getTruckData() {
		return truckData;
	}

	public void setTruckData(String truckData) {
		this.truckData = truckData;
	}

	public String getCustomerLevel() {
		return customerLevel;
	}

	public void setCustomerLevel(String customerLevel) {
		this.customerLevel = customerLevel;
	}


	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeParcelable(this.freightBills, flags);
		dest.writeList(this.freightConsigneeList);
		dest.writeSerializable(this.freightConsignees);
		dest.writeParcelable(this.freightInsurance, flags);
		dest.writeSerializable(this.freightOther);
		dest.writeParcelable(this.freightShip, flags);
		dest.writeSerializable(this.freightSonoUsed);
		dest.writeList(this.freightShipperList);
		dest.writeSerializable(this.freightShippers);
		dest.writeTypedList(this.freightSonoList);
		dest.writeParcelable(this.freightSonos, flags);
		dest.writeParcelable(this.freightTruck, flags);
		dest.writeParcelable(this.logBillsTrack, flags);
		dest.writeSerializable(this.remark);
		dest.writeValue(this.totalFee);
		dest.writeValue(this.gross);
		dest.writeValue(this.grossWeight);
		dest.writeValue(this.teu);
		dest.writeValue(this.sonoGross);
		dest.writeValue(this.shipperMoney);
		dest.writeValue(this.fee);
		dest.writeValue(this.count20gp);
		dest.writeValue(this.count40gp);
		dest.writeValue(this.count45gp);
		dest.writeValue(this.count40hq);
		dest.writeValue(this.count40rf);
		dest.writeValue(this.startFee);
		dest.writeValue(this.endFee);
		dest.writeValue(this.collectionFee);
		dest.writeString(this.guihao);
		dest.writeString(this.guiAndFeng);
		dest.writeString(this.conQty);
		dest.writeString(this.fenghao);
		dest.writeList(this.appLogBillTrackWrappers);
		dest.writeString(this.collaborator);
		dest.writeString(this.orderLoadingAddrs);
		dest.writeString(this.orderUnloadingAddrs);
		dest.writeString(this.truckData);
		dest.writeString(this.customerLevel);
	}

	public ViewForwardOrder() {
	}

	protected ViewForwardOrder(Parcel in) {
		this.freightBills = in.readParcelable(FreightBills.class.getClassLoader());
		this.freightConsigneeList = new ArrayList<FreightConsignee>();
		in.readList(this.freightConsigneeList, FreightConsignee.class.getClassLoader());
		this.freightConsignees = (FreightConsignee) in.readSerializable();
		this.freightInsurance = in.readParcelable(FreightInsurance.class.getClassLoader());
		this.freightOther = (FreightOther) in.readSerializable();
		this.freightShip = in.readParcelable(FreightShip.class.getClassLoader());
		this.freightSonoUsed = (FreightSonoUsed) in.readSerializable();
		this.freightShipperList = new ArrayList<FreightShipper>();
		in.readList(this.freightShipperList, FreightShipper.class.getClassLoader());
		this.freightShippers = (FreightShipper) in.readSerializable();
		this.freightSonoList = in.createTypedArrayList(FreightSono.CREATOR);
		this.freightSonos = in.readParcelable(FreightSono.class.getClassLoader());
		this.freightTruck = in.readParcelable(FreightTruck.class.getClassLoader());
		this.logBillsTrack = in.readParcelable(LogBillsTrack.class.getClassLoader());
		this.remark = (Remark) in.readSerializable();
		this.totalFee = (Double) in.readValue(Double.class.getClassLoader());
		this.gross = (Double) in.readValue(Double.class.getClassLoader());
		this.grossWeight = (Double) in.readValue(Double.class.getClassLoader());
		this.teu = (Double) in.readValue(Double.class.getClassLoader());
		this.sonoGross = (Double) in.readValue(Double.class.getClassLoader());
		this.shipperMoney = (Double) in.readValue(Double.class.getClassLoader());
		this.fee = (Double) in.readValue(Double.class.getClassLoader());
		this.count20gp = (Integer) in.readValue(Integer.class.getClassLoader());
		this.count40gp = (Integer) in.readValue(Integer.class.getClassLoader());
		this.count45gp = (Integer) in.readValue(Integer.class.getClassLoader());
		this.count40hq = (Integer) in.readValue(Integer.class.getClassLoader());
		this.count40rf = (Integer) in.readValue(Integer.class.getClassLoader());
		this.startFee = (Double) in.readValue(Double.class.getClassLoader());
		this.endFee = (Double) in.readValue(Double.class.getClassLoader());
		this.collectionFee = (Double) in.readValue(Double.class.getClassLoader());
		this.guihao = in.readString();
		this.guiAndFeng = in.readString();
		this.conQty = in.readString();
		this.fenghao = in.readString();
		this.appLogBillTrackWrappers = new ArrayList<AppLogBillTrackWrapper>();
		in.readList(this.appLogBillTrackWrappers, AppLogBillTrackWrapper.class.getClassLoader());
		this.collaborator = in.readString();
		this.orderLoadingAddrs = in.readString();
		this.orderUnloadingAddrs = in.readString();
		this.truckData = in.readString();
		this.customerLevel = in.readString();
	}

	public static final Parcelable.Creator<ViewForwardOrder> CREATOR = new Parcelable.Creator<ViewForwardOrder>() {
		@Override
		public ViewForwardOrder createFromParcel(Parcel source) {
			return new ViewForwardOrder(source);
		}

		@Override
		public ViewForwardOrder[] newArray(int size) {
			return new ViewForwardOrder[size];
		}
	};
}
