package com.yaoguang.greendao.entity.common;

import android.os.Parcel;
import android.os.Parcelable;

import com.yaoguang.greendao.entity.AppLogBillTrackWrapper;
import com.yaoguang.greendao.entity.common.ViewForwardOrderPack.LogBillsTrack;
import com.yaoguang.greendao.entity.TruckGoodsTruck;
import com.yaoguang.greendao.entity.TruckOil;
import com.yaoguang.greendao.entity.TruckOther;
import com.yaoguang.greendao.entity.TruckSono;
import com.yaoguang.greendao.entity.driver.TruckGoodsAddr;

import java.util.ArrayList;
import java.util.List;


/**
 * 拖车-工作单主信息
 *
 * @author heyonggang
 * @date 2016年9月5日下午2:26:10
 */
public class TruckBills implements Parcelable {

    private String id;

    private String billSource;

    private String billsId;

    private String orderSn;

    private String shipper;

    private String shipperId;

    private String consigneeId;

    private String modifyDate;

    private String prepaidCollect;

    private String dockOfLoading;

    private String portLoading;

    private String portDelivery;

    private String finalDestination;

    private Integer otherservice;

    private Integer sonoGroup;

    private String freeStorage;

    private String freecontdate;

    private String loadDate;

    private Integer servicetype;

    private String serviceTypeZn;

    private String employeeId;

    private String shipCompany;

    private String vessel;

    private String voyage;

    private String mBlNo;

    private String inVesselPlanTime;

    private String etaPlan;

    private String consolCode;

    private String thId;

    private String goodsName;

    private Double goodsGrossWeight;

    private Integer isPart;

    private String partSigner;

    private Integer isForesee;

    private String shipOrder;

    private String goodsNameE;

    private String clientId;

    private Integer orderStatus;

    private String createdBy;

    private String createdDeptId;

    private String created;

    private String updatedBy;

    private String updatedDeptId;

    private String updated;

    // 20'
    private Integer guige20;

    // 40'
    private Integer guige40;

    private Integer HQ40;

    private Integer RF40;

    // 柜号/封号
    private String guiAndFeng;

    // 柜型/柜量
    private String conQty;

    // 拖车-装/卸货的派车
    private TruckGoodsTruck truckGoodsTruck;

    // 里程及油耗说明表
    private TruckOil truckOil;

    // 拖车-其它信息
    private TruckOther truckOther;

    // 拖车-货柜信息
    private List<TruckSono> truckSonos;

    // 地址信息
    private List<TruckGoodsAddr> truckGoodsAddrs;


    // 业务跟踪
    private LogBillsTrack logBillsTrack;

    // 托运人总欠款
    private Double totalFee;

    // app司机Id
    private String appDriverId;

    // 派车状态（0：正常 1:已派车）
    private Integer sendOrderStatus;

    private String driverOrderId;

    private String clientName;

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }


    public String getDriverOrderId() {
        return driverOrderId;
    }

    public void setDriverOrderId(String driverOrderId) {
        this.driverOrderId = driverOrderId;
    }




    public List<AppLogBillTrackWrapper> getAppLogBillTrackWrappers() {
        return appLogBillTrackWrappers;
    }

    public void setAppLogBillTrackWrappers(List<AppLogBillTrackWrapper> appLogBillTrackWrappers) {
        this.appLogBillTrackWrappers = appLogBillTrackWrappers;
    }

    private List<AppLogBillTrackWrapper> appLogBillTrackWrappers;

//	// 装卸货动态
//	private List<DriverOrderNodeWrapper> orderNodes;
//
//	public List<DriverOrderNodeWrapper> getOrderNodes() {
//		return orderNodes;
//	}
//
//	public void setOrderNodes(List<DriverOrderNodeWrapper> orderNodes) {
//		this.orderNodes = orderNodes;
//	}

    // 业务类型中文描述
    public String getServiceTypeZn() {
        return serviceTypeZn;
    }

    public String getAppDriverId() {
        return appDriverId;
    }

    public void setAppDriverId(String appDriverId) {
        this.appDriverId = appDriverId;
    }

    public Integer getSendOrderStatus() {
        return sendOrderStatus;
    }

    public void setSendOrderStatus(Integer sendOrderStatus) {
        this.sendOrderStatus = sendOrderStatus;
    }

    public void setTotalFee(Double totalFee) {
        this.totalFee = totalFee;
    }

    public Double getTotalFee() {
        return this.totalFee;
    }

    public Integer getSonoGroup() {
        return sonoGroup;
    }

    public void setSonoGroup(Integer sonoGroup) {
        this.sonoGroup = sonoGroup;
    }

    public String getShipperId() {
        return shipperId;
    }

    public void setShipperId(String shipperId) {
        this.shipperId = shipperId;
    }

    public List<TruckSono> getTruckSonos() {
        return truckSonos;
    }

    public void setTruckSonos(List<TruckSono> truckSonos) {
        this.truckSonos = truckSonos;
    }

    public List<TruckGoodsAddr> getTruckGoodsAddrs() {
        return truckGoodsAddrs;
    }

    public void setTruckGoodsAddrs(List<TruckGoodsAddr> truckGoodsAddrs) {
        this.truckGoodsAddrs = truckGoodsAddrs;
    }

    public Integer getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(Integer orderStatus) {
        this.orderStatus = orderStatus;
    }

    public Integer getGuige20() {
        return guige20;
    }

    public void setGuige20(Integer guige20) {
        this.guige20 = guige20;
    }

    public Integer getGuige40() {
        return guige40;
    }

    public void setGuige40(Integer guige40) {
        this.guige40 = guige40;
    }

    public Integer getHQ40() {
        return HQ40;
    }

    public void setHQ40(Integer hQ40) {
        HQ40 = hQ40;
    }

    public Integer getRF40() {
        return RF40;
    }

    public void setRF40(Integer rF40) {
        RF40 = rF40;
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

    public TruckGoodsTruck getTruckGoodsTruck() {
        return truckGoodsTruck;
    }

    public void setTruckGoodsTruck(TruckGoodsTruck truckGoodsTruck) {
        this.truckGoodsTruck = truckGoodsTruck;
    }

    public TruckOil getTruckOil() {
        return truckOil;
    }

    public void setTruckOil(TruckOil truckOil) {
        this.truckOil = truckOil;
    }

    public TruckOther getTruckOther() {
        return truckOther;
    }

    public void setTruckOther(TruckOther truckOther) {
        this.truckOther = truckOther;
    }


    public LogBillsTrack getLogBillsTrack() {
        return logBillsTrack;
    }

    public void setLogBillsTrack(LogBillsTrack logBillsTrack) {
        this.logBillsTrack = logBillsTrack;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBillSource() {
        return billSource;
    }

    public void setBillSource(String billSource) {
        this.billSource = billSource;
    }

    public String getBillsId() {
        return billsId;
    }

    public void setBillsId(String billsId) {
        this.billsId = billsId;
    }

    public String getOrderSn() {
        return orderSn;
    }

    public void setOrderSn(String orderSn) {
        this.orderSn = orderSn;
    }

    public String getShipper() {
        return shipper;
    }

    public void setShipper(String shipper) {
        this.shipper = shipper;
    }

    public String getConsigneeId() {
        return consigneeId;
    }

    public void setConsigneeId(String consigneeId) {
        this.consigneeId = consigneeId;
    }

    public String getModifyDate() {
        return modifyDate;
    }

    public void setModifyDate(String modifyDate) {
        this.modifyDate = modifyDate;
    }

    public String getPrepaidCollect() {
        return prepaidCollect;
    }

    public void setPrepaidCollect(String prepaidCollect) {
        this.prepaidCollect = prepaidCollect;
    }

    public String getDockOfLoading() {
        return dockOfLoading;
    }

    public void setDockOfLoading(String dockOfLoading) {
        this.dockOfLoading = dockOfLoading;
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

    public String getFinalDestination() {
        return finalDestination;
    }

    public void setFinalDestination(String finalDestination) {
        this.finalDestination = finalDestination;
    }

    public Integer getOtherservice() {
        return otherservice;
    }

    public void setOtherservice(Integer otherservice) {
        this.otherservice = otherservice;
    }

    public String getFreeStorage() {
        return freeStorage;
    }

    public void setFreeStorage(String freeStorage) {
        this.freeStorage = freeStorage;
    }

    public String getFreecontdate() {
        return freecontdate;
    }

    public void setFreecontdate(String freecontdate) {
        this.freecontdate = freecontdate;
    }

    public String getLoadDate() {
        return loadDate;
    }

    public void setLoadDate(String loadDate) {
        this.loadDate = loadDate;
    }

    public Integer getServicetype() {
        return servicetype;
    }

    public void setServicetype(Integer servicetype) {
        this.servicetype = servicetype;
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    public String getShipCompany() {
        return shipCompany;
    }

    public void setShipCompany(String shipCompany) {
        this.shipCompany = shipCompany;
    }

    public String getVessel() {
        return vessel;
    }

    public void setVessel(String vessel) {
        this.vessel = vessel;
    }

    public String getVoyage() {
        return voyage;
    }

    public void setVoyage(String voyage) {
        this.voyage = voyage;
    }

    public String getmBlNo() {
        return mBlNo;
    }

    public void setmBlNo(String mBlNo) {
        this.mBlNo = mBlNo;
    }

    public String getInVesselPlanTime() {
        return inVesselPlanTime;
    }

    public void setInVesselPlanTime(String inVesselPlanTime) {
        this.inVesselPlanTime = inVesselPlanTime;
    }

    public String getEtaPlan() {
        return etaPlan;
    }

    public void setEtaPlan(String etaPlan) {
        this.etaPlan = etaPlan;
    }

    public String getConsolCode() {
        return consolCode;
    }

    public void setConsolCode(String consolCode) {
        this.consolCode = consolCode;
    }

    public String getThId() {
        return thId;
    }

    public void setThId(String thId) {
        this.thId = thId;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public Double getGoodsGrossWeight() {
        return goodsGrossWeight;
    }

    public void setGoodsGrossWeight(Double goodsGrossWeight) {
        this.goodsGrossWeight = goodsGrossWeight;
    }

    public Integer getIsPart() {
        return isPart;
    }

    public void setIsPart(Integer isPart) {
        this.isPart = isPart;
    }

    public String getPartSigner() {
        return partSigner;
    }

    public void setPartSigner(String partSigner) {
        this.partSigner = partSigner;
    }

    public Integer getIsForesee() {
        return isForesee;
    }

    public void setIsForesee(Integer isForesee) {
        this.isForesee = isForesee;
    }

    public String getShipOrder() {
        return shipOrder;
    }

    public void setShipOrder(String shipOrder) {
        this.shipOrder = shipOrder;
    }

    public String getGoodsNameE() {
        return goodsNameE;
    }

    public void setGoodsNameE(String goodsNameE) {
        this.goodsNameE = goodsNameE;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
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


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.billSource);
        dest.writeString(this.billsId);
        dest.writeString(this.orderSn);
        dest.writeString(this.shipper);
        dest.writeString(this.shipperId);
        dest.writeString(this.consigneeId);
        dest.writeString(this.modifyDate);
        dest.writeString(this.prepaidCollect);
        dest.writeString(this.dockOfLoading);
        dest.writeString(this.portLoading);
        dest.writeString(this.portDelivery);
        dest.writeString(this.finalDestination);
        dest.writeValue(this.otherservice);
        dest.writeValue(this.sonoGroup);
        dest.writeString(this.freeStorage);
        dest.writeString(this.freecontdate);
        dest.writeString(this.loadDate);
        dest.writeValue(this.servicetype);
        dest.writeString(this.serviceTypeZn);
        dest.writeString(this.employeeId);
        dest.writeString(this.shipCompany);
        dest.writeString(this.vessel);
        dest.writeString(this.voyage);
        dest.writeString(this.mBlNo);
        dest.writeString(this.inVesselPlanTime);
        dest.writeString(this.etaPlan);
        dest.writeString(this.consolCode);
        dest.writeString(this.thId);
        dest.writeString(this.goodsName);
        dest.writeValue(this.goodsGrossWeight);
        dest.writeValue(this.isPart);
        dest.writeString(this.partSigner);
        dest.writeValue(this.isForesee);
        dest.writeString(this.shipOrder);
        dest.writeString(this.goodsNameE);
        dest.writeString(this.clientId);
        dest.writeValue(this.orderStatus);
        dest.writeString(this.createdBy);
        dest.writeString(this.createdDeptId);
        dest.writeString(this.created);
        dest.writeString(this.updatedBy);
        dest.writeString(this.updatedDeptId);
        dest.writeString(this.updated);
        dest.writeValue(this.guige20);
        dest.writeValue(this.guige40);
        dest.writeValue(this.HQ40);
        dest.writeValue(this.RF40);
        dest.writeString(this.guiAndFeng);
        dest.writeString(this.conQty);
        dest.writeParcelable(this.truckGoodsTruck, flags);
        dest.writeParcelable(this.truckOil, flags);
        dest.writeParcelable(this.truckOther, flags);
        dest.writeTypedList(this.truckSonos);
        dest.writeList(this.truckGoodsAddrs);
        dest.writeParcelable(this.logBillsTrack, flags);
        dest.writeValue(this.totalFee);
        dest.writeString(this.appDriverId);
        dest.writeValue(this.sendOrderStatus);
        dest.writeString(this.driverOrderId);
        dest.writeString(this.clientName);
        dest.writeList(this.appLogBillTrackWrappers);
    }

    public TruckBills() {
    }

    protected TruckBills(Parcel in) {
        this.id = in.readString();
        this.billSource = in.readString();
        this.billsId = in.readString();
        this.orderSn = in.readString();
        this.shipper = in.readString();
        this.shipperId = in.readString();
        this.consigneeId = in.readString();
        this.modifyDate = in.readString();
        this.prepaidCollect = in.readString();
        this.dockOfLoading = in.readString();
        this.portLoading = in.readString();
        this.portDelivery = in.readString();
        this.finalDestination = in.readString();
        this.otherservice = (Integer) in.readValue(Integer.class.getClassLoader());
        this.sonoGroup = (Integer) in.readValue(Integer.class.getClassLoader());
        this.freeStorage = in.readString();
        this.freecontdate = in.readString();
        this.loadDate = in.readString();
        this.servicetype = (Integer) in.readValue(Integer.class.getClassLoader());
        this.serviceTypeZn = in.readString();
        this.employeeId = in.readString();
        this.shipCompany = in.readString();
        this.vessel = in.readString();
        this.voyage = in.readString();
        this.mBlNo = in.readString();
        this.inVesselPlanTime = in.readString();
        this.etaPlan = in.readString();
        this.consolCode = in.readString();
        this.thId = in.readString();
        this.goodsName = in.readString();
        this.goodsGrossWeight = (Double) in.readValue(Double.class.getClassLoader());
        this.isPart = (Integer) in.readValue(Integer.class.getClassLoader());
        this.partSigner = in.readString();
        this.isForesee = (Integer) in.readValue(Integer.class.getClassLoader());
        this.shipOrder = in.readString();
        this.goodsNameE = in.readString();
        this.clientId = in.readString();
        this.orderStatus = (Integer) in.readValue(Integer.class.getClassLoader());
        this.createdBy = in.readString();
        this.createdDeptId = in.readString();
        this.created = in.readString();
        this.updatedBy = in.readString();
        this.updatedDeptId = in.readString();
        this.updated = in.readString();
        this.guige20 = (Integer) in.readValue(Integer.class.getClassLoader());
        this.guige40 = (Integer) in.readValue(Integer.class.getClassLoader());
        this.HQ40 = (Integer) in.readValue(Integer.class.getClassLoader());
        this.RF40 = (Integer) in.readValue(Integer.class.getClassLoader());
        this.guiAndFeng = in.readString();
        this.conQty = in.readString();
        this.truckGoodsTruck = in.readParcelable(TruckGoodsTruck.class.getClassLoader());
        this.truckOil = in.readParcelable(TruckOil.class.getClassLoader());
        this.truckOther = in.readParcelable(TruckOther.class.getClassLoader());
        this.truckSonos = in.createTypedArrayList(TruckSono.CREATOR);
        this.truckGoodsAddrs = new ArrayList<TruckGoodsAddr>();
        in.readList(this.truckGoodsAddrs, TruckGoodsAddr.class.getClassLoader());
        this.logBillsTrack = in.readParcelable(LogBillsTrack.class.getClassLoader());
        this.totalFee = (Double) in.readValue(Double.class.getClassLoader());
        this.appDriverId = in.readString();
        this.sendOrderStatus = (Integer) in.readValue(Integer.class.getClassLoader());
        this.driverOrderId = in.readString();
        this.clientName = in.readString();
        this.appLogBillTrackWrappers = new ArrayList<AppLogBillTrackWrapper>();
        in.readList(this.appLogBillTrackWrappers, AppLogBillTrackWrapper.class.getClassLoader());
    }

    public static final Parcelable.Creator<TruckBills> CREATOR = new Parcelable.Creator<TruckBills>() {
        @Override
        public TruckBills createFromParcel(Parcel source) {
            return new TruckBills(source);
        }

        @Override
        public TruckBills[] newArray(int size) {
            return new TruckBills[size];
        }
    };
}