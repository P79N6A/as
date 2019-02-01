package com.yaoguang.greendao.entity.company.userLoginTimeWrapper;


import java.io.Serializable;
import java.util.Date;

public class UserLoginWlan {

    private String id; // 主键

    private String groupId; // 群组id

    private String name; // 名称

    private String address; // 地址

    private String clientId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

}