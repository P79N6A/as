package com.yaoguang.greendao.entity;

import java.io.Serializable;
import java.util.Date;


/**
 * 基本资料-运输条款
 * 
 * @author heyonggang
 * @date 2016年10月18日下午2:18:40
 */
public class InfoTraffic implements Serializable{

	private static final long serialVersionUID = 669581502034131362L;

	private String id;

    private String number;

    private String trafficChinese;

    private String goodsEnglish;

    private String remark;

    private Integer flag;

    private String createdBy;

    private String createdDeptId;

    private Date created;

    private String updatedBy;

    private String updatedDeptId;

    private Date updated;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number == null ? null : number.trim();
    }

    public String getTrafficChinese() {
        return trafficChinese;
    }

    public void setTrafficChinese(String trafficChinese) {
        this.trafficChinese = trafficChinese == null ? null : trafficChinese.trim();
    }

    public String getGoodsEnglish() {
        return goodsEnglish;
    }

    public void setGoodsEnglish(String goodsEnglish) {
        this.goodsEnglish = goodsEnglish == null ? null : goodsEnglish.trim();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
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

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
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

    public Date getUpdated() {
        return updated;
    }

    public void setUpdated(Date updated) {
        this.updated = updated;
    }
}