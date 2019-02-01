package com.yaoguang.driver.data.source.local;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.google.gson.Gson;
import com.yaoguang.common.base.BaseApplication;
import com.yaoguang.common.common.Constants;
import com.yaoguang.common.common.SPUtils;
import com.yaoguang.common.net.bean.BaseResponse;
import com.yaoguang.driver.App;
import com.yaoguang.driver.data.source.DriverDataSource;
import com.yaoguang.driver.util.Injection;
import com.yaoguang.greendao.Injections;
import com.yaoguang.greendao.LoginResult;
import com.yaoguang.greendao.entity.Driver;
import com.yaoguang.greendao.entity.DriverStatusSwitch;
import com.yaoguang.greendao.entity.UserDriverStatusDetail;
import com.yaoguang.greendao.entity.driver.DriverAuthentication;

import io.reactivex.Flowable;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * 司机本地数据源
 * Created by Administrator on 2017/12/8 0008.
 */

public class DriverLocalDataSource implements DriverDataSource{
    private final Context mContext;
    private String KEY = "Driver";

    private static DriverLocalDataSource INSTANCE;

    private DriverLocalDataSource(@NonNull Context context) {
        mContext = context;
    }

    public static DriverLocalDataSource getInstance(@NonNull Context context) {
        if (INSTANCE == null) {
            synchronized (DriverLocalDataSource.class) {
                if (INSTANCE == null) {
                    INSTANCE = new DriverLocalDataSource(context);
                }
            }
        }
        return INSTANCE;
    }


    @NonNull
    @Override
    public LoginResult getLoginResult() {
        SharedPreferences sp = mContext.getSharedPreferences(KEY, Context.MODE_PRIVATE);
        String json = sp.getString(KEY, null);

        if (TextUtils.isEmpty(json)) {
            logout();
            App.getInstance().toLoginActivity();
            try {
                throw new NullPointerException();
            } catch (NullPointerException e) {
                e.printStackTrace();
            }
        }
        return new Gson().fromJson(json, LoginResult.class);
    }

    @NonNull
    @Override
    public Driver getDriver() {
        return getLoginResult().getUserInfo();
    }

    @Override
    public void saveOrUpdate(@NonNull LoginResult loginResult) {
        loginResult = checkNotNull(loginResult);
        SharedPreferences sp = mContext.getSharedPreferences(KEY, Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = sp.edit();
        String json = new Gson().toJson(loginResult);
        edit.putString(KEY, json);
        edit.apply();
    }

    @Override
    public void logout() {
        // 清除token缓存
        SPUtils.getInstance().put(Constants.TOKEN_ID, "");
        SPUtils.getInstance().put(Constants.TOKEN_TOKEN, "");
        // 清除自动登录
        SPUtils.getInstance().put(Constants.PASSWORD, "");
        SPUtils.getInstance().put(Constants.AUTOLOGIN, false);
        // 清空司机数据
        Injection.provideDriverRepository(BaseApplication.getInstance()).saveOrUpdate(new LoginResult());
    }

    @NonNull
    @Override
    @Deprecated
    public Flowable<BaseResponse<String>> setAuthentication(Object object) {
        return null;
    }

    @NonNull
    @Override
    @Deprecated
    public Flowable<BaseResponse<String>> saveAuthentication(Object object) {
        return null;
    }

    @NonNull
    @Override
    @Deprecated
    public Flowable<BaseResponse<DriverAuthentication>> getUserInfo(String driverId) {
        return null;
    }

    @Deprecated
    @NonNull
    @Override
    public Flowable<BaseResponse<String>> simpleRegister(@NonNull String mobile, @NonNull String password, @NonNull String authCode) {
        return null;
    }

    @NonNull
    @Override
    @Deprecated
    public Flowable<BaseResponse<DriverStatusSwitch>> getDriverStatus() {
        return null;
    }

    @Nullable
    @Override
    @Deprecated
    public Flowable<BaseResponse<DriverStatusSwitch>> getDriverStatus(@NonNull String driverId) {
        return null;
    }

    @Nullable
    @Override
    @Deprecated
    public Flowable<BaseResponse<String>> deleteLeavePlan(@NonNull String planId) {
        return null;
    }

    @Nullable
    @Override
    @Deprecated
    public Flowable<BaseResponse<String>> update(@NonNull Driver driver) {
        return null;
    }

    @Nullable
    @Override
    @Deprecated
    public Flowable<BaseResponse<String>> updateLeavePlan(@NonNull String id, @NonNull String endDate, @NonNull String sendFlag) {
        return null;
    }

    @Nullable
    @Override
    @Deprecated
    public Flowable<BaseResponse<String>> addDriverRestPlan(@NonNull UserDriverStatusDetail userDriverStatusDetail) {
        return null;
    }

    @Nullable
    @Override
    @Deprecated
    public Flowable<BaseResponse<LoginResult>> login(@NonNull String username, @NonNull String password, @NonNull String driverToken, @NonNull String loginMachine, @NonNull String loginMachineVersion, @NonNull String loginAppVersion) {
        return null;
    }
}
