package com.yaoguang.greendao.entity.driver;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.SpannableStringBuilder;

import com.yaoguang.greendao.entity.InfoPutorderPlace;

import java.util.List;

/**
 * 订单中心包装类
 *
 * @author heyonggang
 * @date 2017年5月5日上午10:48:24
 */
public class DriverOrderWrapper implements Parcelable {

    // 运货线路箭头
    private SpannableStringBuilder spannableStringBuilder;

    public SpannableStringBuilder getSpannableStringBuilder() {
        return spannableStringBuilder;
    }

    public void setSpannableStringBuilder(SpannableStringBuilder spannableStringBuilder) {
        this.spannableStringBuilder = spannableStringBuilder;
    }

    // 司机订单主键
    private String id;

    // 订单标记(拼，套) 套 = 2  拼 = 4 套 + 拼 = 6
    private String mark;

    // 司机订单编号
    private String orderId;

    // 司机ID
    private String driverId;

    // 委托公司
    private String entrustCompany;

    // 公司图标
    private String logo;

    // 公司电话
    private String phone;

    // 联系人
    private String entrustPeople;

    // 联系人手机
    private String mobile;

    // 工作单状态(0:待接单 1：已接单 2：已完成 3:关闭 4:后台取消)
    private String orderStatus;

    // 可否拒单（0:非可拒 1:可拒）
    private String refusable;

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

    // 船公司
    private String shipCompany;

    // 运货路线（可能是多个地址拼一起）
    private List<String> deliveryRoute;

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

    // 订单备注
    private String remark;

    // 拒绝原因
    private String refuseRemark;

    // 委托公司Id
    private String createdDeptId;

    // 司机名称
    private String driverName;

    // 司机电话
    private String driverMobile;

    // 委托总公司ID
    private String clientId;

    // 放单地点
    private InfoPutorderPlace place = new InfoPutorderPlace();

    private String orderType;

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
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

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
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

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getRefusable() {
        return refusable;
    }

    public void setRefusable(String refusable) {
        this.refusable = refusable;
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

    public String getShipCompany() {
        return shipCompany;
    }

    public void setShipCompany(String shipCompany) {
        this.shipCompany = shipCompany;
    }

    public List<String> getDeliveryRoute() {
        return deliveryRoute;
    }

    public void setDeliveryRoute(List<String> deliveryRoute) {
        this.deliveryRoute = deliveryRoute;
    }

    public String getVehicleFlag() {
        return vehicleFlag;
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

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getRefuseRemark() {
        return refuseRemark;
    }

    public void setRefuseRemark(String refuseRemark) {
        this.refuseRemark = refuseRemark;
    }

    public String getCreatedDeptId() {
        return createdDeptId;
    }

    public void setCreatedDeptId(String createdDeptId) {
        this.createdDeptId = createdDeptId;
    }

    public String getDriverName() {
        return driverName;
    }

    public void setDriverName(String driverName) {
        this.driverName = driverName;
    }

    public String getDriverMobile() {
        return driverMobile;
    }

    public void setDriverMobile(String driverMobile) {
        this.driverMobile = driverMobile;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public InfoPutorderPlace getPlace() {
        return place;
    }

    public void setPlace(InfoPutorderPlace place) {
        this.place = place;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.mark);
        dest.writeString(this.orderId);
        dest.writeString(this.driverId);
        dest.writeString(this.entrustCompany);
        dest.writeString(this.logo);
        dest.writeString(this.phone);
        dest.writeString(this.entrustPeople);
        dest.writeString(this.mobile);
        dest.writeString(this.orderStatus);
        dest.writeString(this.refusable);
        dest.writeString(this.truckBillFirstType);
        dest.writeString(this.truckBillSecondType);
        dest.writeString(this.deliveryTime);
        dest.writeValue(this.vehiclePrice);
        dest.writeString(this.contQty);
        dest.writeString(this.shipCompany);
        dest.writeStringList(this.deliveryRoute);
        dest.writeString(this.vehicleFlag);
        dest.writeString(this.createTime);
        dest.writeString(this.receiveTime);
        dest.writeString(this.finishTime);
        dest.writeString(this.truckBillsFirst);
        dest.writeString(this.truckBillsSecond);
        dest.writeString(this.remark);
        dest.writeString(this.refuseRemark);
        dest.writeString(this.createdDeptId);
        dest.writeString(this.driverName);
        dest.writeString(this.driverMobile);
        dest.writeString(this.clientId);
        dest.writeParcelable(this.place, flags);
        dest.writeString(this.orderType);
    }

    public DriverOrderWrapper() {
    }

    protected DriverOrderWrapper(Parcel in) {
        this.id = in.readString();
        this.mark = in.readString();
        this.orderId = in.readString();
        this.driverId = in.readString();
        this.entrustCompany = in.readString();
        this.logo = in.readString();
        this.phone = in.readString();
        this.entrustPeople = in.readString();
        this.mobile = in.readString();
        this.orderStatus = in.readString();
        this.refusable = in.readString();
        this.truckBillFirstType = in.readString();
        this.truckBillSecondType = in.readString();
        this.deliveryTime = in.readString();
        this.vehiclePrice =  in.readString();
        this.contQty = in.readString();
        this.shipCompany = in.readString();
        this.deliveryRoute = in.createStringArrayList();
        this.vehicleFlag = in.readString();
        this.createTime = in.readString();
        this.receiveTime = in.readString();
        this.finishTime = in.readString();
        this.truckBillsFirst = in.readString();
        this.truckBillsSecond = in.readString();
        this.remark = in.readString();
        this.refuseRemark = in.readString();
        this.createdDeptId = in.readString();
        this.driverName = in.readString();
        this.driverMobile = in.readString();
        this.clientId = in.readString();
        this.place = in.readParcelable(InfoPutorderPlace.class.getClassLoader());
        this.orderType = in.readString();
    }

    public static final Creator<DriverOrderWrapper> CREATOR = new Creator<DriverOrderWrapper>() {
        @Override
        public DriverOrderWrapper createFromParcel(Parcel source) {
            return new DriverOrderWrapper(source);
        }

        @Override
        public DriverOrderWrapper[] newArray(int size) {
            return new DriverOrderWrapper[size];
        }
    };
}
