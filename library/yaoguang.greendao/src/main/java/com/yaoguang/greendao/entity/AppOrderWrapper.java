package com.yaoguang.greendao.entity;

public class AppOrderWrapper {

    // 主键id
    private String id;

    // 订单编号
    private String orderSn;

    // 订单类型 0-货代，1-拖车
    private Integer type;

    // 公司名称
    private String companyName;

    // 装拆箱类型0-装箱，1-拆箱
    private Integer otherservice;

    // 起运地
    private String dockLoading;

    // 起运港
    private String portLoading;

    // 目的地
    private String portDelivery;

    // 目的港
    private String finalDestination;

    // 货名
    private String goodsName;

    // 柜型
    private String contQty;

    // 业务时间
    private String created;

    //运单号
    private String mBlNo;

    // 企业id
    private String clientId;

    public String getmBlNo() {
        return mBlNo;
    }

    public void setmBlNo(String mBlNo) {
        this.mBlNo = mBlNo;
    }

    public String getOrderSn() {
        return orderSn;
    }

    public void setOrderSn(String orderSn) {
        this.orderSn = orderSn;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public String getContQty() {
        return contQty;
    }

    public void setContQty(String contQty) {
        this.contQty = contQty;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public Integer getOtherservice() {
        return otherservice;
    }

    public void setOtherservice(Integer otherservice) {
        this.otherservice = otherservice;
    }

    public String getDockLoading() {
        return dockLoading;
    }

    public void setDockLoading(String dockLoading) {
        this.dockLoading = dockLoading;
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

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }
}