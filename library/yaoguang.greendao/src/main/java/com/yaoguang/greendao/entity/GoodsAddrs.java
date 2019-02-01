package com.yaoguang.greendao.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * 装/卸 地址
 * Created by zhongjh on 2017/5/10.
 */
public class GoodsAddrs implements Serializable {


    /**
     * address : 江西省吉安市泰和县马市镇423
     * id : c1b3effa294541698eb38ffc344b6460
     * createdBy : hyx
     * flag : 0
     * tel : 0796-0021445
     * goodsUnit : 23131
     * unitFax : 0796-0021445
     * goodsPriority : 1
     * billsId : GZYG201705091151361
     * contacts : 1231
     * mobile : 13945141665
     * remarks : 21313
     * createdDeptId : 23a9be55f8d44257b61c181561e45696
     * created : 2017-05-09 11:51:36
     * updatedBy : 495a58d8cfe645498523c18baa13548d
     * updatedDeptId : 23a9be55f8d44257b61c181561e45696
     * updated : 2017-05-09 11:55:14
     * delFlag : 1
     */
    /**
     * 0装货，1卸货
     */


    private String id;

    private String billsId;

    private String sort;

    private Integer flag;

    private String address;

    private String contacts;

    private String mobile;

    private String tel;

    private String remarks;

    private String goodsUnit;

    private String unitFax;

    private String goodsPriority;

    private String createdBy;

    private Integer delFlag;

    private String createdDeptId;

    private String created;

    private String updatedBy;

    private String updatedDeptId;

    private String updated;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getGoodsUnit() {
        return goodsUnit;
    }

    public void setGoodsUnit(String goodsUnit) {
        this.goodsUnit = goodsUnit;
    }

    public String getUnitFax() {
        return unitFax;
    }

    public void setUnitFax(String unitFax) {
        this.unitFax = unitFax;
    }

    public String getGoodsPriority() {
        return goodsPriority;
    }

    public void setGoodsPriority(String goodsPriority) {
        this.goodsPriority = goodsPriority;
    }

    public String getBillsId() {
        return billsId;
    }

    public void setBillsId(String billsId) {
        this.billsId = billsId;
    }

    public String getContacts() {
        return contacts;
    }

    public void setDelFlag(Integer delFlag) {
        this.delFlag = delFlag;
    }

    public void setFlag(Integer flag) {
        this.flag = flag;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public void setContacts(String contacts) {
        this.contacts = contacts;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
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

    public int getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(int delFlag) {
        this.delFlag = delFlag;
    }
}
