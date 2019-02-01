package com.yaoguang.greendao.entity;

import java.io.Serializable;
import java.util.Date;

public class DriverOrderNodeList implements Serializable {

    private static final long serialVersionUID = -7982262492423104710L;

    private String id;

    private String driverOrderId;

    private String orderNodeId;

    private String orderSn;

    private Integer number;

    private String nodeName;

    private String addressId;
    private String currentOrder;
    private String address;
    private String remark;
    private Integer detailFlag;
    private Integer detailSuccess;
    private String createdBy;
    private String created;
    private String updated;
    private boolean latestFinish;

    public boolean isLatestFinish() {
        return latestFinish;
    }

    public void setLatestFinish(boolean latestFinish) {
        this.latestFinish = latestFinish;
    }

    public String getCurrentOrder() {
        return currentOrder;
    }

    public void setCurrentOrder(String currentOrder) {
        this.currentOrder = currentOrder;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getDriverOrderId() {
        return driverOrderId;
    }

    public void setDriverOrderId(String driverOrderId) {
        this.driverOrderId = driverOrderId == null ? null : driverOrderId.trim();
    }

    public String getOrderNodeId() {
        return orderNodeId;
    }

    public void setOrderNodeId(String orderNodeId) {
        this.orderNodeId = orderNodeId == null ? null : orderNodeId.trim();
    }

    public String getOrderSn() {
        return orderSn;
    }

    public void setOrderSn(String orderSn) {
        this.orderSn = orderSn == null ? null : orderSn.trim();
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public String getNodeName() {
        return nodeName;
    }

    public void setNodeName(String nodeName) {
        this.nodeName = nodeName == null ? null : nodeName.trim();
    }

    public String getAddressId() {
        return addressId;
    }

    public void setAddressId(String addressId) {
        this.addressId = addressId == null ? null : addressId.trim();
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address == null ? null : address.trim();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public Integer getDetailFlag() {
        return detailFlag;
    }

    public void setDetailFlag(Integer detailFlag) {
        this.detailFlag = detailFlag;
    }

    public Integer getDetailSuccess() {
        return detailSuccess;
    }

    public void setDetailSuccess(Integer detailSuccess) {
        this.detailSuccess = detailSuccess;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy == null ? null : createdBy.trim();
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public String getUpdated() {
        return updated;
    }

    public void setUpdated(String updated) {
        this.updated = updated;
    }

}