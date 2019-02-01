package com.yaoguang.shipper.activitys.login;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import com.yaoguang.interactor.common.DCSBaseInteractorImpl;
import com.yaoguang.lib.common.constants.Constants;
import com.yaoguang.lib.common.SPUtils;
import com.yaoguang.lib.net.bean.BaseResponse;
import com.yaoguang.greendao.entity.AppUserOwner;
import com.yaoguang.greendao.entity.UserOwner;
import com.yaoguang.datasource.common.DataStatic;
import com.yaoguang.lib.net.Api;
import com.yaoguang.datasource.api.shipper.OwnerApi;
import com.yaoguang.taobao.push.impl.PushManager;

import io.reactivex.Observable;

/**
 * 登录的业务逻辑
 * Created by zhongjh on 2017/3/29.
 */
public class LoginInteractorImpl extends DCSBaseInteractorImpl implements LoginContact.SLoginInteractor {

    private String aliasType = "Company";

    @Override
    public Observable<BaseResponse<UserOwner>> login(final String username, final String password, final Context context) {
        OwnerApi ownerApi = Api.getInstance().retrofit.create(OwnerApi.class);
        return ownerApi.login(username, password, PushManager.deviceToken,
                android.os.Build.MANUFACTURER + " SDKINT：" + android.os.Build.VERSION.SDK_INT, android.os.Build.VERSION.RELEASE, getAppVersionName(context));
    }

    @Override
    public Observable<BaseResponse<AppUserOwner>> loginVersion2(final String username, final String password, final Context context) {
        OwnerApi ownerApi = Api.getInstance().retrofit.create(OwnerApi.class);
        return ownerApi.loginVersion2(username, password, PushManager.deviceToken,
                android.os.Build.MANUFACTURER + " SDKINT：" + android.os.Build.VERSION.SDK_INT, android.os.Build.VERSION.RELEASE, getAppVersionName(context));
    }

    @Override
    public void saveUserOwnerIsLogin(UserOwner userOwner, String strUsername, String strPassword) {
        //存储本地数据库
        DataStatic.getInstance().setUserOwner(userOwner);
        SPUtils.getInstance().put(Constants.USERNAME, strUsername);
        SPUtils.getInstance().put(Constants.PASSWORD, strPassword);
        SPUtils.getInstance().put(Constants.AUTOLOGIN, true);

        PushManager.getInstance().addAlias(userOwner.getId(),Constants.APP_ALIAS);
        PushManager.getInstance().AddExclusiveAlias(userOwner.getId(), Constants.APP_ALIAS);

        // 初始化一些token,id值
        SPUtils.getInstance().put(Constants.TOKEN_ID, userOwner.getId());
        SPUtils.getInstance().put(Constants.TOKEN_TOKEN, userOwner.getSingleToken());
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
