package com.yaoguang.greendao.entity;


/**
 * 基础资料-航期表
 *
 * @author ZhangDeQuan
 * @time 2017年5月10日 上午10:13:24
 */
public class InfoVoyageTable {

    private String id;

    private String vesselName;

    private String voyage;

    private String placeLoading;

    private String placeDelivery;

    private String onboardDate;

    private String arrival;

    private Integer flag;

    private String portTime;

    private String shipLine;

    private String shipCompany;

    private String contId;

    private Double oceanfee;

    private Double contFee;

    private Double orderFee;

    private Double fuelFee;

    private Double portFee;

    private Double thc;

    private Double thd;

    private String remark;

    private String clientId;

    private String createdBy;

    private String createdDeptId;

    private String created;

    private String updatedBy;

    private String updatedDeptId;

    private String updated;

    // 机构名称
    private String officeName;

    private String totalFee;

    private String shipLine1;
    private String shipLine2;
    private String shipLine3;
    private String shipLine4;

    public String getShipLine1() {
        return shipLine1;
    }

    public String getShipLine2() {
        return shipLine2;
    }

    public String getShipLine3() {
        return shipLine3;
    }

    public String getShipLine4() {
        return shipLine4;
    }


    public String getTotalFee() {
        return totalFee;
    }

    public void setTotalFee(String totalFee) {
        this.totalFee = totalFee;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getVesselName() {
        return vesselName;
    }

    public void setVesselName(String vesselName) {
        this.vesselName = vesselName;
    }

    public String getVoyage() {
        return voyage;
    }

    public void setVoyage(String voyage) {
        this.voyage = voyage;
    }

    public String getPlaceLoading() {
        return placeLoading;
    }

    public void setPlaceLoading(String placeLoading) {
        this.placeLoading = placeLoading;
    }

    public String getPlaceDelivery() {
        return placeDelivery;
    }

    public void setPlaceDelivery(String placeDelivery) {
        this.placeDelivery = placeDelivery;
    }

    public String getOnboardDate() {
        return onboardDate;
    }

    public void setOnboardDate(String onboardDate) {
        this.onboardDate = onboardDate;
    }

    public String getArrival() {
        return arrival;
    }

    public void setArrival(String arrival) {
        this.arrival = arrival;
    }

    public Integer getFlag() {
        return flag;
    }

    public void setFlag(Integer flag) {
        this.flag = flag;
    }

    public String getPortTime() {
        return portTime;
    }

    public void setPortTime(String portTime) {
        this.portTime = portTime;
    }

    public String getShipLine() {
        return shipLine;
    }

    public void setShipLine(String shipLine) {
        //拆分出来4个，赋值到另外4个属性
        shipLine = shipLine.replace("--", "-");
        String[] shipLines = shipLine.split("-");
        switch (shipLines.length) {
            case 1:
                this.shipLine1 = shipLines[0];
                break;
            case 2:
                this.shipLine1 = shipLines[0];
                this.shipLine2 = shipLines[1];
                break;
            case 3:
                this.shipLine1 = shipLines[0];
                this.shipLine2 = shipLines[1];
                this.shipLine3 = shipLines[2];
                break;
            case 4:
                this.shipLine1 = shipLines[0];
                this.shipLine2 = shipLines[1];
                this.shipLine3 = shipLines[2];
                this.shipLine4 = shipLines[3];
                break;
        }
        this.shipLine = shipLine;
    }

    public String getShipCompany() {
        return shipCompany;
    }

    public void setShipCompany(String shipCompany) {
        this.shipCompany = shipCompany;
    }

    public String getContId() {
        return contId;
    }

    public void setContId(String contId) {
        this.contId = contId;
    }

    public Double getOceanfee() {
        return oceanfee;
    }

    public void setOceanfee(Double oceanfee) {
        this.oceanfee = oceanfee;
    }

    public Double getContFee() {
        return contFee;
    }

    public void setContFee(Double contFee) {
        this.contFee = contFee;
    }

    public Double getOrderFee() {
        return orderFee;
    }

    public void setOrderFee(Double orderFee) {
        this.orderFee = orderFee;
    }

    public Double getFuelFee() {
        return fuelFee;
    }

    public void setFuelFee(Double fuelFee) {
        this.fuelFee = fuelFee;
    }

    public Double getPortFee() {
        return portFee;
    }

    public void setPortFee(Double portFee) {
        this.portFee = portFee;
    }

    public Double getThc() {
        return thc;
    }

    public void setThc(Double thc) {
        this.thc = thc;
    }

    public Double getThd() {
        return thd;
    }

    public void setThd(Double thd) {
        this.thd = thd;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
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

    public String getOfficeName() {
        return officeName;
    }

    public void setOfficeName(String officeName) {
        this.officeName = officeName;
    }
}