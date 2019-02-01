package com.yaoguang.greendao.entity.company;


import java.util.List;

public class AppCompanyBanDanWrapper  {

    // 运单号
    private String mBlNo;
    // 船公司
    private String shipCompany;
    // 干线船名
    private String vessel;
    // 干线航次
    private String voyage;
    // 柜型
    private String contId;
    // 装卸货(0:装1:卸)
    private String otherservice;
    // 起运港
    private String portLoading;
    // 目的港
    private String portDelivery;
    // 货名
    private String goodsName;
    // 货柜
    private List<AppCompanyBanDanSonoWrapper> sonoList;

    public String getmBlNo() {
        return mBlNo;
    }

    public void setmBlNo(String mBlNo) {
        this.mBlNo = mBlNo;
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

    public String getContId() {
        return contId;
    }

    public void setContId(String contId) {
        this.contId = contId;
    }

    public String getOtherservice() {
        return otherservice;
    }

    public void setOtherservice(String otherservice) {
        this.otherservice = otherservice;
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

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public List<AppCompanyBanDanSonoWrapper> getSonoList() {
        return sonoList;
    }

    public void setSonoList(List<AppCompanyBanDanSonoWrapper> sonoList) {
        this.sonoList = sonoList;
    }
}
