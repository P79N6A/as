package com.yaoguang.greendao.entity;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.SpannableStringBuilder;

import java.io.Serializable;
import java.util.List;

/**
 * Created by zhongjh on 2017/4/18.
 */

public class Order implements Parcelable {

    private static final long serialVersionUID = -1804747382355499125L;

    public static String STRGSON = "result";

    /**
     * 司机派单信息主键
     */
    private String driverOrderId;

    /**
     * 公司名
     */
    private String companyName;
    /**
     * 类型(0:吉出重回 1:重出吉回 2:重出重回)
     */
    private String driverOrderType;

    /**
     * 中文
     * 类型(0:吉出重回 1:重出吉回 2:重出重回)
     */
    private String orderType;
    /**
     * 船公司
     */
    private String shipCompany;
    /**
     * 运货地点(可能是多个地址拼一起)
     */
    private String deliveryPlaces;
    /**
     * 拖车工作单2
     */

    private String orderMark;
    // orderMark资源列表
    private List<Integer> orderMarkList;

    // 司机派单信息主键
    private String id;

    // 订单标记(拼，套)
    private String mark;

    // 司机订单编号
    private String orderId;

    // 司机ID
    private String driverId;

    // 委托公司
    private String entrustCompany;

    // 公司电话
    private String phone;

    // 联系人
    private String entrustPeople;

    // 联系人手机
    private String mobile;

    // 工作单状态(0:待接单 1：已接单 2：已完成 3:关闭)
    private String orderStatus;

    // 拼单1类型（0：吉出重回 1：重出吉回 2：重出重回）
    private String truckBillFirstType;

    // 拼单2类型（0：吉出重回 1：重出吉回 2：重出重回）
    private String truckBillSecondType;

    // 装卸时间
    private String deliveryTime;

    // 运价
    private String vehiclePrice;

    // 柜型柜量
    private String contQty;

    // 船公司一
    private String shipCompanyOne;

    // 船公司二
    private String shipCompanyTwo;

    // 运货路线（可能是多个地址拼一起）
    private List<String> deliveryRoute;

    // 运货线路箭头
    private SpannableStringBuilder spannableStringBuilder;

    // 是否出车（0：未出车 1：已出车）
    private String vehicleFlag;

    // 推单时间
    private String createTime;

    // 接单时间
    private String receiveTime;

    // 完成时间（或关闭时间）
    private String finishTime;

    // 拖车工作单1
    private String truckBillsFirst;

    // 拖车工作单2
    private String truckBillsSecond;

    // 已读未读信息，首页和业务消息需要用的
    private DriverOrderMsg driverOrderMsg;

    // 备注
    private String remark;

    // 能否拒单（0:非可拒 1:可拒）
    private String refusable;

    // 图标
    private String logo;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public static String getSTRGSON() {
        return STRGSON;
    }

    public static void setSTRGSON(String STRGSON) {
        Order.STRGSON = STRGSON;
    }

    public String getDriverOrderId() {
        return driverOrderId;
    }

    public void setDriverOrderId(String driverOrderId) {
        this.driverOrderId = driverOrderId;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getDriverOrderType() {
        return driverOrderType;
    }

    public void setDriverOrderType(String driverOrderType) {
        this.driverOrderType = driverOrderType;
    }

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    public String getShipCompany() {
        return shipCompany;
    }

    public void setShipCompany(String shipCompany) {
        this.shipCompany = shipCompany;
    }

    public String getDeliveryPlaces() {
        return deliveryPlaces;
    }

    public void setDeliveryPlaces(String deliveryPlaces) {
        this.deliveryPlaces = deliveryPlaces;
    }

    public String getOrderMark() {
        return orderMark;
    }

    public void setOrderMark(String orderMark) {
        this.orderMark = orderMark;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMark() {
        return mark;
    }

    public void setMark(String mark) {
        this.mark = mark;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getDriverId() {
        return driverId;
    }

    public void setDriverId(String driverId) {
        this.driverId = driverId;
    }

    public String getEntrustCompany() {
        return entrustCompany;
    }

    public void setEntrustCompany(String entrustCompany) {
        this.entrustCompany = entrustCompany;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEntrustPeople() {
        return entrustPeople;
    }

    public void setEntrustPeople(String entrustPeople) {
        this.entrustPeople = entrustPeople;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public int getOrderStatus() {
        return Integer.parseInt(orderStatus);
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getTruckBillFirstType() {
        return truckBillFirstType;
    }

    public void setTruckBillFirstType(String truckBillFirstType) {
        this.truckBillFirstType = truckBillFirstType;
    }

    public String getTruckBillSecondType() {
        return truckBillSecondType;
    }

    public void setTruckBillSecondType(String truckBillSecondType) {
        this.truckBillSecondType = truckBillSecondType;
    }

    public String getDeliveryTime() {
        return deliveryTime;
    }

    public void setDeliveryTime(String deliveryTime) {
        this.deliveryTime = deliveryTime;
    }

    public String getVehiclePrice() {
        return vehiclePrice;
    }

    public void setVehiclePrice(String vehiclePrice) {
        this.vehiclePrice = vehiclePrice;
    }

    public String getContQty() {
        return contQty;
    }

    public void setContQty(String contQty) {
        this.contQty = contQty;
    }

    public String getShipCompanyOne() {
        return shipCompanyOne;
    }

    public void setShipCompanyOne(String shipCompanyOne) {
        this.shipCompanyOne = shipCompanyOne;
    }

    public String getShipCompanyTwo() {
        return shipCompanyTwo;
    }

    public void setShipCompanyTwo(String shipCompanyTwo) {
        this.shipCompanyTwo = shipCompanyTwo;
    }

    public List<String> getDeliveryRoute() {
        return deliveryRoute;
    }

    public void setDeliveryRoute(List<String> deliveryRoute) {
        this.deliveryRoute = deliveryRoute;
    }

    public int getVehicleFlag() {
        return Integer.parseInt(vehicleFlag);
    }

    public void setVehicleFlag(String vehicleFlag) {
        this.vehicleFlag = vehicleFlag;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getReceiveTime() {
        return receiveTime;
    }

    public void setReceiveTime(String receiveTime) {
        this.receiveTime = receiveTime;
    }

    public String getFinishTime() {
        return finishTime;
    }

    public void setFinishTime(String finishTime) {
        this.finishTime = finishTime;
    }

    public String getTruckBillsFirst() {
        return truckBillsFirst;
    }

    public void setTruckBillsFirst(String truckBillsFirst) {
        this.truckBillsFirst = truckBillsFirst;
    }

    public String getTruckBillsSecond() {
        return truckBillsSecond;
    }

    public void setTruckBillsSecond(String truckBillsSecond) {
        this.truckBillsSecond = truckBillsSecond;
    }

    public DriverOrderMsg getDriverOrderMsg() {
        return driverOrderMsg;
    }

    public void setDriverOrderMsg(DriverOrderMsg driverOrderMsg) {
        this.driverOrderMsg = driverOrderMsg;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Integer getRefusable() {
        if (refusable == null) {
            return null;
        }
        return Integer.parseInt(refusable);
    }

    public void setRefusable(String refusable) {
        this.refusable = refusable;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.driverOrderId);
        dest.writeString(this.companyName);
        dest.writeString(this.driverOrderType);
        dest.writeString(this.orderType);
        dest.writeString(this.shipCompany);
        dest.writeString(this.deliveryPlaces);
        dest.writeString(this.orderMark);
        dest.writeString(this.id);
        dest.writeString(this.mark);
        dest.writeString(this.orderId);
        dest.writeString(this.driverId);
        dest.writeString(this.entrustCompany);
        dest.writeString(this.phone);
        dest.writeString(this.entrustPeople);
        dest.writeString(this.mobile);
        dest.writeString(this.orderStatus);
        dest.writeString(this.truckBillFirstType);
        dest.writeString(this.truckBillSecondType);
        dest.writeString(this.deliveryTime);
        dest.writeString(this.vehiclePrice);
        dest.writeString(this.contQty);
        dest.writeString(this.shipCompanyOne);
        dest.writeString(this.shipCompanyTwo);
        dest.writeStringList(this.deliveryRoute);
        dest.writeString(this.vehicleFlag);
        dest.writeString(this.createTime);
        dest.writeString(this.receiveTime);
        dest.writeString(this.finishTime);
        dest.writeString(this.truckBillsFirst);
        dest.writeString(this.truckBillsSecond);
        dest.writeSerializable(this.driverOrderMsg);
        dest.writeString(this.remark);
        dest.writeString(this.refusable);
        dest.writeString(this.logo);
    }

    public Order() {
    }

    protected Order(Parcel in) {
        this.driverOrderId = in.readString();
        this.companyName = in.readString();
        this.driverOrderType = in.readString();
        this.orderType = in.readString();
        this.shipCompany = in.readString();
        this.deliveryPlaces = in.readString();
        this.orderMark = in.readString();
        this.id = in.readString();
        this.mark = in.readString();
        this.orderId = in.readString();
        this.driverId = in.readString();
        this.entrustCompany = in.readString();
        this.phone = in.readString();
        this.entrustPeople = in.readString();
        this.mobile = in.readString();
        this.orderStatus = in.readString();
        this.truckBillFirstType = in.readString();
        this.truckBillSecondType = in.readString();
        this.deliveryTime = in.readString();
        this.vehiclePrice = in.readString();
        this.contQty = in.readString();
        this.shipCompanyOne = in.readString();
        this.shipCompanyTwo = in.readString();
        this.deliveryRoute = in.createStringArrayList();
        this.vehicleFlag = in.readString();
        this.createTime = in.readString();
        this.receiveTime = in.readString();
        this.finishTime = in.readString();
        this.truckBillsFirst = in.readString();
        this.truckBillsSecond = in.readString();
        this.driverOrderMsg = (DriverOrderMsg) in.readSerializable();
        this.remark = in.readString();
        this.refusable = in.readString();
        this.logo = in.readString();
    }

    public static final Creator<Order> CREATOR = new Creator<Order>() {
        @Override
        public Order createFromParcel(Parcel source) {
            return new Order(source);
        }

        @Override
        public Order[] newArray(int size) {
            return new Order[size];
        }
    };

    public SpannableStringBuilder getSpannableStringBuilder() {
        return spannableStringBuilder;
    }

    public void setSpannableStringBuilder(SpannableStringBuilder spannableStringBuilder) {
        this.spannableStringBuilder = spannableStringBuilder;
    }

    public List<Integer> getOrderMarkList() {
        return orderMarkList;
    }

    public void setOrderMarkList(List<Integer> orderMarkList) {
        this.orderMarkList = orderMarkList;
    }
}
