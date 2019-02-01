package com.yaoguang.company.activitys.login;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import com.yaoguang.lib.common.constants.Constants;
import com.yaoguang.lib.common.SPUtils;
import com.yaoguang.lib.net.bean.BaseResponse;
import com.yaoguang.datasource.common.DataStatic;
import com.yaoguang.greendao.entity.AppLoginUser;
import com.yaoguang.greendao.entity.AppUserWrapper;
import com.yaoguang.interactor.common.DCSBaseInteractorImpl;
import com.yaoguang.lib.net.Api;
import com.yaoguang.datasource.api.company.CompanyApi;
import com.yaoguang.taobao.push.impl.PushManager;

import io.reactivex.Observable;

/**
 * 登录的业务逻辑
 * Created by zhongjh on 2017/3/29.
 */
public class LoginInteractorImpl extends DCSBaseInteractorImpl implements LoginContact.CLoginInteractor {

    private String aliasType = "Company";

    @Override
    public Observable<BaseResponse<AppUserWrapper>> login(final String username, final String password, final Context context) {
        CompanyApi companyApi = Api.getInstance().retrofit.create(CompanyApi.class);
        return companyApi.login(username, password, PushManager.deviceToken,
                android.os.Build.MANUFACTURER + " SDKINT：" + android.os.Build.VERSION.SDK_INT, android.os.Build.VERSION.RELEASE, getAppVersionName(context));
    }

    @Override
    public Observable<BaseResponse<AppLoginUser>> loginVersion2(String username, String password, Context context) {
        CompanyApi companyApi = Api.getInstance().retrofit.create(CompanyApi.class);
        return companyApi.loginVersion2(username, password, PushManager.deviceToken,
                android.os.Build.MANUFACTURER + " SDKINT：" + android.os.Build.VERSION.SDK_INT, android.os.Build.VERSION.RELEASE, getAppVersionName(context));
    }

    @Override
    public void saveCompanyUserIsLogin(AppUserWrapper appUserWrapper, String strUsername, String strPassword) {
        //存储本地数据库
        DataStatic.getInstance().setAppUserWrapper(appUserWrapper);
        SPUtils.getInstance().put(Constants.USERNAME, strUsername);
        SPUtils.getInstance().put(Constants.PASSWORD, strPassword);
        SPUtils.getInstance().put(Constants.AUTOLOGIN, true);

        PushManager.getInstance().addAlias(appUserWrapper.getId(), Constants.APP_ALIAS);
        PushManager.getInstance().AddExclusiveAlias(appUserWrapper.getId(), Constants.APP_ALIAS);
        PushManager.getInstance().addAlias(appUserWrapper.getRemarks(),Constants.APP_TOPOFFICEID);
        PushManager.getInstance().AddExclusiveAlias(appUserWrapper.getRemarks(),Constants.APP_TOPOFFICEID);

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
