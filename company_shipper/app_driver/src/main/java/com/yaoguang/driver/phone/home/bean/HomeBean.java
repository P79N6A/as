package com.yaoguang.driver.phone.home.bean;

import com.yaoguang.lib.net.bean.BaseResponse;
import com.yaoguang.lib.net.bean.PageList;
import com.yaoguang.greendao.entity.BannerPic;
import com.yaoguang.greendao.entity.driver.DriverOrderProgressWrapper;
import com.yaoguang.greendao.entity.DriverStatusSwitch;

/**
 * 首页bean
 * Created by wly on 2017/12/14 0014.
 */

public class HomeBean {
    private BaseResponse<PageList<BannerPic>> bannerPics;
    private BaseResponse<DriverOrderProgressWrapper> driverOrderProgressWrapperBase;
    private BaseResponse<DriverStatusSwitch> driverStatusSwitch;

    public boolean isHasEmpty() {
        return bannerPics == null || driverOrderProgressWrapperBase == null || driverStatusSwitch == null;
    }

    public HomeBean() {
    }

    public HomeBean(BaseResponse<PageList<BannerPic>> bannerPics, BaseResponse<DriverOrderProgressWrapper> driverOrderProgressWrapperBase, BaseResponse<DriverStatusSwitch> driverStatusSwitch) {
        this.bannerPics = bannerPics;
        this.driverOrderProgressWrapperBase = driverOrderProgressWrapperBase;
        this.driverStatusSwitch = driverStatusSwitch;
    }

    public BaseResponse<PageList<BannerPic>> getBannerPics() {
        return bannerPics;
    }

    public BaseResponse<DriverOrderProgressWrapper> getDriverOrderProgressWrapperBase() {
        return driverOrderProgressWrapperBase;
    }

    public BaseResponse<DriverStatusSwitch> getDriverStatusSwitch() {
        return driverStatusSwitch;
    }

    public void setBannerPics(BaseResponse<PageList<BannerPic>> bannerPics) {
        this.bannerPics = bannerPics;
    }

    public void setDriverOrderProgressWrapperBase(BaseResponse<DriverOrderProgressWrapper> driverOrderProgressWrapperBase) {
        this.driverOrderProgressWrapperBase = driverOrderProgressWrapperBase;
    }

    public void setDriverStatusSwitch(BaseResponse<DriverStatusSwitch> driverStatusSwitch) {
        this.driverStatusSwitch = driverStatusSwitch;
    }


}
