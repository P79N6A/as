package com.yaoguang.greendao.entity.company.user;


import com.yaoguang.greendao.entity.company.userLoginTimeWrapper.UserLoginAllowLocation;
import com.yaoguang.greendao.entity.company.userLoginTimeWrapper.UserLoginAllowWlan;
import com.yaoguang.greendao.entity.company.userLoginTimeWrapper.UserLoginAuthDevice;
import com.yaoguang.greendao.entity.company.userLoginTimeWrapper.UserLoginTime;

import java.util.List;

/**
 * Created by LiYangBin on 2018/10/29.
 */
public class ViewUserSetting extends User  {

    private String roleName; // 角色显示

    private List<UserRole> userRoles; // 角色

    private UserLoginTime loginTime; // web登录时间

    private List<UserLoginAuthDevice> loginAuthDevices; // web已授权设备

    private UserLoginTime appLoginTime; // 物流登录时间

    private List<UserLoginAllowWlan> appAllowWlans; // 物流网络环境

    private List<UserLoginAllowLocation> appAllowLocations; // 物流使用位置

    private List<UserLoginAuthDevice> appAuthDevices; // 物流已授权设备

    private UserLoginTime bossLoginTime; // BOSS登录时间

    private List<UserLoginAllowWlan> bossAllowWlans; // BOSS网络环境

    private List<UserLoginAllowLocation> bossAllowLocations; // BOSS使用位置

    private List<UserLoginAuthDevice> bossAuthDevices; // BOSS已授权设备

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public List<UserRole> getUserRoles() {
        return userRoles;
    }

    public void setUserRoles(List<UserRole> userRoles) {
        this.userRoles = userRoles;
    }

    public UserLoginTime getLoginTime() {
        return loginTime;
    }

    public void setLoginTime(UserLoginTime loginTime) {
        this.loginTime = loginTime;
    }

    public List<UserLoginAuthDevice> getLoginAuthDevices() {
        return loginAuthDevices;
    }

    public void setLoginAuthDevices(List<UserLoginAuthDevice> loginAuthDevices) {
        this.loginAuthDevices = loginAuthDevices;
    }

    public UserLoginTime getAppLoginTime() {
        return appLoginTime;
    }

    public void setAppLoginTime(UserLoginTime appLoginTime) {
        this.appLoginTime = appLoginTime;
    }

    public List<UserLoginAllowWlan> getAppAllowWlans() {
        return appAllowWlans;
    }

    public void setAppAllowWlans(List<UserLoginAllowWlan> appAllowWlans) {
        this.appAllowWlans = appAllowWlans;
    }

    public List<UserLoginAllowLocation> getAppAllowLocations() {
        return appAllowLocations;
    }

    public void setAppAllowLocations(List<UserLoginAllowLocation> appAllowLocations) {
        this.appAllowLocations = appAllowLocations;
    }

    public List<UserLoginAuthDevice> getAppAuthDevices() {
        return appAuthDevices;
    }

    public void setAppAuthDevices(List<UserLoginAuthDevice> appAuthDevices) {
        this.appAuthDevices = appAuthDevices;
    }

    public UserLoginTime getBossLoginTime() {
        return bossLoginTime;
    }

    public void setBossLoginTime(UserLoginTime bossLoginTime) {
        this.bossLoginTime = bossLoginTime;
    }

    public List<UserLoginAllowWlan> getBossAllowWlans() {
        return bossAllowWlans;
    }

    public void setBossAllowWlans(List<UserLoginAllowWlan> bossAllowWlans) {
        this.bossAllowWlans = bossAllowWlans;
    }

    public List<UserLoginAllowLocation> getBossAllowLocations() {
        return bossAllowLocations;
    }

    public void setBossAllowLocations(List<UserLoginAllowLocation> bossAllowLocations) {
        this.bossAllowLocations = bossAllowLocations;
    }

    public List<UserLoginAuthDevice> getBossAuthDevices() {
        return bossAuthDevices;
    }

    public void setBossAuthDevices(List<UserLoginAuthDevice> bossAuthDevices) {
        this.bossAuthDevices = bossAuthDevices;
    }
}
