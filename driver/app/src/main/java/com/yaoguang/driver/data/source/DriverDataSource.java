package com.yaoguang.driver.data.source;

import android.support.annotation.NonNull;

import com.yaoguang.common.net.bean.BaseResponse;
import com.yaoguang.greendao.LoginResult;
import com.yaoguang.greendao.entity.Driver;
import com.yaoguang.greendao.entity.DriverStatusSwitch;
import com.yaoguang.greendao.entity.UserDriverStatusDetail;
import com.yaoguang.greendao.entity.driver.DriverAuthentication;

import io.reactivex.Flowable;

/**
 * =====================================
 * 作    者: 韦理英
 * 版    本：1.2.9
 * 创建日期：2018/1/10
 * 描    述：司机数据接口
 * =====================================
 */

public interface DriverDataSource {

    @NonNull
    Flowable<BaseResponse<String>> setAuthentication(Object object);

    @NonNull
    Flowable<BaseResponse<String>> saveAuthentication(Object object);

    @NonNull
    Flowable<BaseResponse<DriverAuthentication>> getUserInfo(String driverId);

    @NonNull
    Flowable<BaseResponse<String>> simpleRegister(@NonNull String mobile, @NonNull String password, @NonNull String authCode);

    @NonNull
    Flowable<BaseResponse<DriverStatusSwitch>> getDriverStatus();

    @NonNull
    Flowable<BaseResponse<DriverStatusSwitch>> getDriverStatus(@NonNull String driverId);

    @NonNull
    Flowable<BaseResponse<String>> deleteLeavePlan(@NonNull String planId);

    @NonNull
    Driver getDriver();

    @NonNull
    LoginResult getLoginResult();

    void saveOrUpdate(@NonNull LoginResult loginResult);

    void logout();

    @NonNull
    Flowable<BaseResponse<String>> update(@NonNull Driver driver);

    @NonNull
    Flowable<BaseResponse<String>> updateLeavePlan(@NonNull String id, @NonNull String endDate, @NonNull String sendFlag);

    @NonNull
    Flowable<BaseResponse<String>> addDriverRestPlan(@NonNull UserDriverStatusDetail userDriverStatusDetail);

    @NonNull
    Flowable<BaseResponse<LoginResult>> login(@NonNull String username, @NonNull String password, @NonNull String driverToken, @NonNull String loginMachine, @NonNull String loginMachineVersion, @NonNull String loginAppVersion);
}
