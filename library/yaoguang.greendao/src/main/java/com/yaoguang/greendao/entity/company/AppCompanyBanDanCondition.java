package com.yaoguang.greendao.entity.company;

public class AppCompanyBanDanCondition {

    // 柜号
    private String contNo;
    // 运单号
    private String mBlNo;
    // 船公司
    private String shipCompany;
    // 干线船名
    private String vessel;
    // 干线航次
    private String voyage;
    // 开始业务日期
    private String startModifyDate;
    // 结束业务日期
    private String endModifyDate;
    // 办单状态(0:否 1:是)
    private String bdStatus;
    // 船到状态(0:否 1:是)
    private String cdStatus;
    // 打单状态(0:否 1:是)
    private String mtddStatus;
    // 总公司id
    private String clientId;
    // 业务类型(0:货代 1:拖车)
    private String bizType;
    // 日期区间(0:自定义 1:今天 2:近15天 3:近1月 4:近3月 5:近半年)
    private String dateScopeType;
    // 装卸货(0:装1:卸)
    private String otherservice;


    public String getContNo() {
        return contNo;
    }

    public void setContNo(String contNo) {
        this.contNo = contNo;
    }

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

    public String getStartModifyDate() {
        return startModifyDate;
    }

    public void setStartModifyDate(String startModifyDate) {
        this.startModifyDate = startModifyDate;
    }

    public String getEndModifyDate() {
        return endModifyDate;
    }

    public void setEndModifyDate(String endModifyDate) {
        this.endModifyDate = endModifyDate;
    }

    public String getBdStatus() {
        return bdStatus;
    }

    public void setBdStatus(String bdStatus) {
        this.bdStatus = bdStatus;
    }

    public String getCdStatus() {
        return cdStatus;
    }

    public void setCdStatus(String cdStatus) {
        this.cdStatus = cdStatus;
    }

    public String getMtddStatus() {
        return mtddStatus;
    }

    public void setMtddStatus(String mtddStatus) {
        this.mtddStatus = mtddStatus;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getBizType() {
        return bizType;
    }

    public void setBizType(String bizType) {
        this.bizType = bizType;
    }

    public String getDateScopeType() {
        return dateScopeType;
    }

    public void setDateScopeType(String dateScopeType) {
        this.dateScopeType = dateScopeType;
    }

    public String getOtherservice() {
        return otherservice;
    }

    public void setOtherservice(String otherservice) {
        this.otherservice = otherservice;
    }
}
