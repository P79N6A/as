package com.yaoguang.greendao.entity;

import com.yaoguang.greendao.entity.common.UserOffice;

import java.io.Serializable;

/**
 * Created by zhongjh on 2017/4/6.
 */
public class Company implements Serializable {


    private String firstletter;
    private long firstletterid;
    /**
     * address : 广州天河车陂大岗路14号业顺商务大厦409
     * name : 广州千寻货运代理有限公司
     * id : 66
     * companyRemark : GZQX
     * email : beney@163.com
     * phone : 13899996666
     * photo : p/201704/3958ea18212048f99228200e54e8fb6623868.jpg
     * companyCode : GZQX
     * zipCode : 510660
     * master : 曾庆斌
     * fax : 020-85852020
     * shortName : GZQX
     */

    private String address;
    private String name;
    private String companyId;
    private String id;
    private String companyRemark;
    private String email;
    private String phone;
    private String photo;
    private String companyCode;
    private String zipCode;
    private String master;
    private String fax;
    private String shortName;
    private UserOffice userOffice;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCompanyCode() {
        return companyCode;
    }

    public void setCompanyCode(String companyCode) {
        this.companyCode = companyCode;
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public String getCompanyRemark() {
        return companyRemark;
    }

    public void setCompanyRemark(String companyRemark) {
        this.companyRemark = companyRemark;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    public String getFirstletter() {
        return firstletter;
    }

    public void setFirstletter(String firstletter) {
        this.firstletter = firstletter;
    }

    public long getFirstletterid() {
        return firstletterid;
    }

    public void setFirstletterid(long firstletterid) {
        this.firstletterid = firstletterid;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMaster() {
        return master;
    }

    public void setMaster(String master) {
        this.master = master;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public UserOffice getUserOffice() {
        return userOffice;
    }

    public void setUserOffice(UserOffice userOffice) {
        this.userOffice = userOffice;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

}
