package com.yaoguang.greendao.entity.company.userLoginTimeWrapper;

import java.util.List;

/**
 * Created by zhongjh on 2018/12/3.
 */

public class LoginRestrict {
    /**
     * BSSID : [{"id":"676faddc2a424f7c824b0f4889916c87","groupName":"123","macAddress":["d4:61:fe:5f:66:60"],"clientId":null,"createdBy":null,"createdDeptId":null,"created":null,"updatedBy":null,"updatedDeptId":null,"updated":null,"delFlag":null,"appType":1},{"id":"7qC6Pob8K5kmMOsWJZC","groupName":"29g","macAddress":["b4:8b:19:32:f8:d3"],"clientId":null,"createdBy":null,"createdDeptId":null,"created":null,"updatedBy":null,"updatedDeptId":null,"updated":null,"delFlag":null,"appType":1}]
     * sim : null
     * location : [{"id":"yLy3l6HBaLSXCdTYtXr","address":"车陂北街9号车陂四社农贸市场南外1号","lon":113.396857,"lat":23.125707,"radius":2000,"clientId":null,"delFlag":null,"createdBy":null,"createdDeptId":null,"created":null,"updatedBy":null,"updatedDeptId":null,"updated":null},{"id":"MK8O6hjEQ8Fzj8pH0t3","address":"健明四路与天坤二路交叉口西南100米","lon":113.394949,"lat":23.136738,"radius":2000,"clientId":null,"delFlag":null,"createdBy":null,"createdDeptId":null,"created":null,"updatedBy":null,"updatedDeptId":null,"updated":null}]
     */

    private String sim;
    private List<UserLoginAllowWlan> BSSID;
    private List<UserLoginAllowLocation> location;

    public String getSim() {
        return sim;
    }

    public void setSim(String sim) {
        this.sim = sim;
    }

    public List<UserLoginAllowWlan> getBSSID() {
        return BSSID;
    }

    public void setBSSID(List<UserLoginAllowWlan> BSSID) {
        this.BSSID = BSSID;
    }

    public List<UserLoginAllowLocation> getLocation() {
        return location;
    }

    public void setLocation(List<UserLoginAllowLocation> location) {
        this.location = location;
    }
}
