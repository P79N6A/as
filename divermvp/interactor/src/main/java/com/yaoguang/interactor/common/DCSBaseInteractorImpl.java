package com.yaoguang.interactor.common;

import com.yaoguang.greendao.entity.AppUserWrapper;
import com.yaoguang.greendao.entity.Driver;
import com.yaoguang.greendao.entity.UserOwner;
import com.yaoguang.datasource.common.DataStatic;

/**
 * Created by zhongjh on 2017/6/5.
 */
public class DCSBaseInteractorImpl implements DCSBaseInteractor {

    private AppUserWrapper appUserWrapper;
    private UserOwner userOwner;
    private Driver driver;

    @Override
    public AppUserWrapper getAppUserWrapper() {
        if (appUserWrapper == null) {
            appUserWrapper = DataStatic.getInstance().getAppUserWrapper();
        }
        return appUserWrapper;
    }

    @Override
    public UserOwner getUserOwner() {
        if (userOwner == null) {
            userOwner = DataStatic.getInstance().getUserOwner();
        }
        return userOwner;
    }

    @Override
    public Driver getDriver() {
        if (driver == null) {
            driver = DataStatic.getInstance().getDriver();
        }
        return driver;
    }

}
