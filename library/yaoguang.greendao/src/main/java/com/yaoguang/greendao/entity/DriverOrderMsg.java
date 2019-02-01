package com.yaoguang.greendao.entity;

import java.io.Serializable;
import java.util.List;

/**
 * 司机订单消息
 *
 * @author liyangbin
 * @date 2017年7月10日下午2:36:50
 */
public class DriverOrderMsg implements Serializable {

    private static final long serialVersionUID = -4350874159780597353L;

    private String id;    // 主键

    // 消息类型(0:派 1：改 2：关)
    private Integer msgType;

    private Integer msgTypeRes;// 消息图片资源

    private String driverId;    // 司机主键

    private String truckDriverOrderId;    // 订单主键

    private Integer flag;    // 0：未读 1：已读

    private String createdBy;

    private String voice;

    private String createdDeptId;

    private String created;

    private String updatedBy;

    private String updatedDeptId;

    private String updated;
    private Order driverOrderWrapper;
    private DriverOrderMsg driverOrderMsg;

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public Order getDriverOrderWrapper() {
        return driverOrderWrapper;
    }

    public void setDriverOrderWrapper(Order driverOrderWrapper) {
        this.driverOrderWrapper = driverOrderWrapper;
    }

    public String getUpdated() {
        return updated;
    }

    public void setUpdated(String updated) {
        this.updated = updated;
    }

    private Integer isValid;    // 0：无效 1：有效

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getDriverId() {
        return driverId;
    }

    public void setDriverId(String driverId) {
        this.driverId = driverId == null ? null : driverId.trim();
    }

    public String getTruckDriverOrderId() {
        return truckDriverOrderId;
    }

    public void setTruckDriverOrderId(String truckDriverOrderId) {
        this.truckDriverOrderId = truckDriverOrderId == null ? null : truckDriverOrderId.trim();
    }

    public Integer getFlag() {
        return flag;
    }

    public void setFlag(Integer flag) {
        this.flag = flag;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy == null ? null : createdBy.trim();
    }

    public String getCreatedDeptId() {
        return createdDeptId;
    }

    public void setCreatedDeptId(String createdDeptId) {
        this.createdDeptId = createdDeptId == null ? null : createdDeptId.trim();
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy == null ? null : updatedBy.trim();
    }

    public String getUpdatedDeptId() {
        return updatedDeptId;
    }

    public void setUpdatedDeptId(String updatedDeptId) {
        this.updatedDeptId = updatedDeptId == null ? null : updatedDeptId.trim();
    }

    public Integer getIsValid() {
        return isValid;
    }

    public void setIsValid(Integer isValid) {
        this.isValid = isValid;
    }

    public Integer getMsgType() {
        return msgType;
    }

    public void setMsgType(Integer msgType) {
        this.msgType = msgType;
    }

    public void setDriverOrderMsg(DriverOrderMsg driverOrderMsg) {
        this.driverOrderMsg = driverOrderMsg;
    }

    public DriverOrderMsg getDriverOrderMsg() {
        return driverOrderMsg;
    }

    public String getVoice() {
        return voice;
    }

    public void setVoice(String voice) {
        this.voice = voice;
    }

    public Integer getMsgTypeRes() {
        return msgTypeRes;
    }

    public void setMsgTypeRes(Integer msgTypeRes) {
        this.msgTypeRes = msgTypeRes;
    }
}