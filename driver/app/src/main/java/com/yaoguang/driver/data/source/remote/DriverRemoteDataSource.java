package com.yaoguang.driver.data.source.remote;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.yaoguang.common.net.bean.BaseResponse;
import com.yaoguang.driver.data.source.DriverDataSource;
import com.yaoguang.driver.net.factory.ApiDriverFactory;
import com.yaoguang.greendao.LoginResult;
import com.yaoguang.greendao.entity.Driver;
import com.yaoguang.greendao.entity.DriverStatusSwitch;
import com.yaoguang.greendao.entity.UserDriverStatusDetail;
import com.yaoguang.greendao.entity.driver.DriverAuthentication;
import com.yaoguang.greendao.entity.driver.UserDriverCar;
import com.yaoguang.greendao.entity.driver.UserDriverLicence;

import io.reactivex.Flowable;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * 司机远程数据源
 * Created by wly on 2017/12/8 0008.
 */

public class DriverRemoteDataSource implements DriverDataSource {

    private static DriverRemoteDataSource INSTANCE;

    public static DriverRemoteDataSource getInstance() {
        if (INSTANCE == null) {
            synchronized (DriverRemoteDataSource.class) {
                if (INSTANCE == null) {
                    INSTANCE = new DriverRemoteDataSource();
                }
            }
        }
        return INSTANCE;
    }

    @NonNull
    @Override
    public Flowable<BaseResponse<String>> setAuthentication(Object o) {
        if (o instanceof Driver) {
            return ApiDriverFactory.setAuthentication((Driver) o,"real","1");
        } else if (o instanceof UserDriverLicence) {
            return ApiDriverFactory.setAuthentication((UserDriverLicence) o,"lic","1");
        } else if (o instanceof UserDriverCar) {
            return ApiDriverFactory.setAuthentication((UserDriverCar) o,"car","1");
        }
        return null;
    }

    @NonNull
    @Override
    public Flowable<BaseResponse<String>> saveAuthentication(Object o) {
        if (o instanceof Driver) {
            return ApiDriverFactory.setAuthentication((Driver) o,"real","0");
        } else if (o instanceof UserDriverLicence) {
            return ApiDriverFactory.setAuthentication((UserDriverLicence) o,"lic","0");
        } else if (o instanceof UserDriverCar) {
            return ApiDriverFactory.setAuthentication((UserDriverCar) o,"car","0");
        }
        return null;
    }

    /**
     * 获取认证信息
     * @param driverId
     */
    @NonNull
    @Override
    public Flowable<BaseResponse<DriverAuthentication>> getUserInfo(String driverId) {
        return ApiDriverFactory.getAuthentication(driverId);
    }

    @NonNull
    @Override
    public Flowable<BaseResponse<String>> simpleRegister(String mobile, String password, String authCode) {
        return ApiDriverFactory.simpleRegister(mobile, password, authCode);
    }

    @Nullable
    @Override
    @Deprecated
    public Flowable<BaseResponse<DriverStatusSwitch>> getDriverStatus() {
        return null;
    }

    @NonNull
    @Override
    public Flowable<BaseResponse<DriverStatusSwitch>> getDriverStatus(@NonNull String driverId) {
        checkNotNull(driverId);
        return ApiDriverFactory.getDriverStatus(driverId);
    }

    @NonNull
    public Flowable<BaseResponse<String>> deleteLeavePlan(@NonNull String planId) {
        checkNotNull(planId);
        return ApiDriverFactory.deleteLeavePlan(planId);
    }

    @NonNull
    @Override
    @Deprecated
    public Driver getDriver() {
        return null;
    }

    @Deprecated
    @NonNull
    @Override
    public LoginResult getLoginResult() {
        return null;
    }

    @Override
    @Deprecated
    public void saveOrUpdate(@NonNull LoginResult loginResult) {
    }

    @Override
    @Deprecated
    public void logout() {

    }

    @NonNull
    @Override
    public Flowable<BaseResponse<String>> update(@NonNull Driver driver) {
        return ApiDriverFactory.update(checkNotNull(driver));
    }

    @NonNull
    @Override
    public Flowable<BaseResponse<String>> updateLeavePlan(@NonNull String id, @NonNull String endDate, @NonNull String sendFlag) {
        checkNotNull(id);
        checkNotNull(endDate);
        checkNotNull(sendFlag);
        return ApiDriverFactory.updateLeavePlan(id, endDate, sendFlag);
    }

    @NonNull
    @Override
    public Flowable<BaseResponse<String>> addDriverRestPlan(@NonNull UserDriverStatusDetail userDriverStatusDetail) {
        checkNotNull(userDriverStatusDetail);
        return ApiDriverFactory.addDriverRestPlan(userDriverStatusDetail);
    }

    @NonNull
    public Flowable<BaseResponse<LoginResult>> login(@NonNull String username, @NonNull String password, @NonNull String driverToken, @NonNull String loginMachine, @NonNull String loginMachineVersion, @NonNull String loginAppVersion) {
        checkNotNull(username);
        checkNotNull(password);
        return ApiDriverFactory.login(username, password, driverToken, loginMachine, loginMachineVersion, loginAppVersion);
    }
}
