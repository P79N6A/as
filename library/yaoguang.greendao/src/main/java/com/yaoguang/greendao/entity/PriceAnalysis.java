package com.yaoguang.greendao.entity;


/**
 * 报价分析查询对象
 *
 * @author wangxiaohui
 * @date 2017年4月27日 下午6:17:14
 */
public class PriceAnalysis {

    //船公司
    private String shipCompany;

    //起运地
    private String dockOfLoading;

    //起运港
    private String portLoading;

    //目的港
    private String portDelivery;

    //目的地
    private String finalDestination;

    //起始拖车
    private String loadingTruckCompany;

    //目的拖车
    private String destinationTruckCompany;

    //柜型
    private String contId;

    //海运费
    private String oceanFreightPrice;

    //装箱费
    private String packingPrice;

    //办单费
    private String orderPrice;

    //燃油附加费
    private String fuelsurPrice;

    //码头包干费
    private String dockPrice;

    //始驳费（单拖）
    private String singleStartBargeFee;

    //始驳费（双拖）
    private String doubleStartBargeFee;

    //达驳费（单）
    private String singleEndBargeFee;

    //达驳费（双）
    private String doubleEndBargeFee;

    //备注
    private String remark;

    public String getDockPrice() {
        return dockPrice;
    }

    public void setDockPrice(String dockPrice) {
        this.dockPrice = dockPrice;
    }

    public String getShipCompany() {
        return shipCompany;
    }

    public void setShipCompany(String shipCompany) {
        this.shipCompany = shipCompany;
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

    public String getLoadingTruckCompany() {
        return loadingTruckCompany;
    }

    public void setLoadingTruckCompany(String loadingTruckCompany) {
        this.loadingTruckCompany = loadingTruckCompany;
    }

    public String getDestinationTruckCompany() {
        return destinationTruckCompany;
    }

    public void setDestinationTruckCompany(String destinationTruckCompany) {
        this.destinationTruckCompany = destinationTruckCompany;
    }

    public String getContId() {
        return contId;
    }

    public void setContId(String contId) {
        this.contId = contId;
    }

    public String getOceanFreightPrice() {
        return oceanFreightPrice;
    }

    public void setOceanFreightPrice(String oceanFreightPrice) {
        this.oceanFreightPrice = oceanFreightPrice;
    }

    public String getPackingPrice() {
        return packingPrice;
    }

    public void setPackingPrice(String packingPrice) {
        this.packingPrice = packingPrice;
    }

    public String getOrderPrice() {
        return orderPrice;
    }

    public void setOrderPrice(String orderPrice) {
        this.orderPrice = orderPrice;
    }

    public String getFuelsurPrice() {
        return fuelsurPrice;
    }

    public void setFuelsurPrice(String fuelsurPrice) {
        this.fuelsurPrice = fuelsurPrice;
    }

    public String getSingleStartBargeFee() {
        return singleStartBargeFee;
    }

    public void setSingleStartBargeFee(String singleStartBargeFee) {
        this.singleStartBargeFee = singleStartBargeFee;
    }

    public String getDoubleStartBargeFee() {
        return doubleStartBargeFee;
    }

    public void setDoubleStartBargeFee(String doubleStartBargeFee) {
        this.doubleStartBargeFee = doubleStartBargeFee;
    }

    public String getSingleEndBargeFee() {
        return singleEndBargeFee;
    }

    public void setSingleEndBargeFee(String singleEndBargeFee) {
        this.singleEndBargeFee = singleEndBargeFee;
    }

    public String getDoubleEndBargeFee() {
        return doubleEndBargeFee;
    }

    public void setDoubleEndBargeFee(String doubleEndBargeFee) {
        this.doubleEndBargeFee = doubleEndBargeFee;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getAgentBookingCompany() {
        return agentBookingCompany;
    }

    public void setAgentBookingCompany(String agentBookingCompany) {
        this.agentBookingCompany = agentBookingCompany;
    }

    public String getGoodsType() {
        return goodsType;
    }

    public void setGoodsType(String goodsType) {
        this.goodsType = goodsType;
    }

    //订舱公司
    private String agentBookingCompany;

    //货物类型
    private String goodsType;


}