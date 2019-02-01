package com.yaoguang.greendao.entity.driver;

import com.yaoguang.greendao.entity.Driver;

import java.util.List;

/**
 * =====================================
 * 作    者: 韦理英
 * 版    本：$version
 * 创建日期：2018/02/09
 * 描    述：
 * 司机认证信息
 * =====================================
 */

public class DriverAuthentication {
    UserDriverLicence licenceInfo;
    Driver userInfo;
    Driver userDriver;
    List<UserDriverCar> carInfo;

    public UserDriverLicence getLicenceInfo() {
        return licenceInfo;
    }

    public void setLicenceInfo(UserDriverLicence licenceInfo) {
        this.licenceInfo = licenceInfo;
    }

    public Driver getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(Driver userInfo) {
        this.userInfo = userInfo;
    }

    public Driver getUserDriver() {
        return userDriver;
    }

    public void setUserDriver(Driver userDriver) {
        this.userDriver = userDriver;
    }

    public List<UserDriverCar> getCarInfo() {
        return carInfo;
    }

    public void setCarInfo(List<UserDriverCar> carInfo) {
        this.carInfo = carInfo;
    }
}
