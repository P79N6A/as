package com.yaoguang.driver.phone.activitys.login;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;

import com.yaoguang.datasource.common.DataStatic;
import com.yaoguang.greendao.entity.driver.LoginDriver;
import com.yaoguang.lib.common.constants.Constants;
import com.yaoguang.lib.common.SPUtils;
import com.yaoguang.lib.net.bean.BaseResponse;
import com.yaoguang.interactor.common.DCSBaseInteractorImpl;
import com.yaoguang.interfaces.driver.interactor.DLoginInteractor;
import com.yaoguang.lib.net.Api;
import com.yaoguang.datasource.api.DriverApi;
import com.yaoguang.taobao.push.impl.PushManager;

import io.reactivex.Observable;

/**
 * 登录的业务逻辑
 * Created by zhongjh on 2017/3/29.
 */
public class DLoginInteractorImpl extends DCSBaseInteractorImpl implements DLoginInteractor {

    private String aliasType = "Driver";
    public static String DRIVER_STRGSON = "userDriver";


    @Override
    public Observable<BaseResponse<LoginDriver>> login(final String username, final String password, final Context context) {
        return Api.getInstance().retrofit.create(DriverApi.class).login2(username, password, PushManager.deviceToken,
                android.os.Build.MANUFACTURER + " SDKINT：" + android.os.Build.VERSION.SDK_INT, android.os.Build.VERSION.RELEASE, getAppVersionName(context));
    }

    /**
    * 描述：设置登录用户消息与保存登录实体
    */

    @Override
    public void loginSuccess(@NonNull LoginDriver loginDriver, final String username, final String password) {
        //存储本地数据库
        DataStatic.getInstance().setDriver(loginDriver.getUserInfo());
        DataStatic.getInstance().setUserDriverCars(loginDriver.getCarInfo());
        DataStatic.getInstance().setUserDriverLicence(loginDriver.getLicenceInfo());
        SPUtils.getInstance().put(Constants.USERNAME, username);
        SPUtils.getInstance().put(Constants.PASSWORD, password);
        SPUtils.getInstance().put(Constants.AUTOLOGIN, true);

        PushManager.getInstance().addAlias(loginDriver.getUserInfo().getId(), Constants.APP_ALIAS);
        PushManager.getInstance().AddExclusiveAlias(loginDriver.getUserInfo().getId(), Constants.APP_ALIAS);

        // 初始化一些token,id值
        SPUtils.getInstance().put(Constants.TOKEN_ID, loginDriver.getUserInfo().getId());
        SPUtils.getInstance().put(Constants.TOKEN_TOKEN, loginDriver.getUserInfo().getSingleToken());
    }

    //获取当前版本号
    private String getAppVersionName(Context context) {
        String versionName = "";
        try {
            // ---get the package info---
            PackageManager pm = context.getPackageManager();
            PackageInfo pi = pm.getPackageInfo(context.getPackageName(), 0);
            versionName = pi.versionName;
            if (versionName == null || versionName.length() <= 0) {
                return "";
            }
        } catch (Exception ignored) {
        }
        return versionName;
    }

}
