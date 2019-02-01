package com.yaoguang.datasource.common;

import com.yaoguang.greendao.entity.AppUserWrapper;
import com.yaoguang.greendao.entity.UserOwner;

/**
 * 基类业务层
 * Created by zhongjh on 2017/6/5.
 */
public class DCSBaseDataSource {

    private AppUserWrapper appUserWrapper;
    private UserOwner userOwner;

    public AppUserWrapper getAppUserWrapper() {
        if (appUserWrapper == null) {
            appUserWrapper = DataStatic.getInstance().getAppUserWrapper();
        }
        return appUserWrapper;
    }

    public UserOwner getUserOwner() {
        if (userOwner == null) {
            userOwner = DataStatic.getInstance().getUserOwner();
        }
        return userOwner;
    }
}
