package com.yaoguang.driver.data.source;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.yaoguang.common.net.bean.BaseResponse;
import com.yaoguang.driver.data.source.local.DriverLocalDataSource;
import com.yaoguang.greendao.LoginResult;
import com.yaoguang.greendao.entity.Driver;
import com.yaoguang.greendao.entity.DriverStatusSwitch;
import com.yaoguang.greendao.entity.UserDriverStatusDetail;
import com.yaoguang.greendao.entity.driver.DriverAuthentication;
import com.yaoguang.greendao.entity.driver.UserDriverCar;
import com.yaoguang.greendao.entity.driver.UserDriverLicence;

import io.reactivex.Flowable;
import io.reactivex.functions.Function;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * =====================================
 * 作    者: 韦理英
 * 版    本：1.2.9
 * 创建日期：2018/1/10
 * 描    述：司机数据仓库
 * =====================================
 */

public class DriverRepository implements DriverDataSource {
    @Nullable
    private static DriverRepository INSTANCE;

    @NonNull
    private final DriverDataSource mDriverRemoteDataSource;

    @NonNull
    private final DriverLocalDataSource mDriverLocalDataSource;

    public static DriverRepository getInstance(@NonNull DriverDataSource driverRemoteDataSource,
                                               @NonNull DriverLocalDataSource driverLocalDataSource) {
        if (INSTANCE == null) {
            synchronized (DriverRepository.class) {
                if (INSTANCE == null) {
                    INSTANCE = new DriverRepository(driverRemoteDataSource, driverLocalDataSource);
                }
            }
        }
        return INSTANCE;
    }

    private DriverRepository(@NonNull DriverDataSource mDriverRemoteDataSource, @NonNull DriverLocalDataSource mDriverLocalDataSource) {
        this.mDriverRemoteDataSource = checkNotNull(mDriverRemoteDataSource);
        this.mDriverLocalDataSource = checkNotNull(mDriverLocalDataSource);
    }


    @NonNull
    @Override
    public Flowable<BaseResponse<String>> setAuthentication(Object o) {
        if (o instanceof Driver) {
            Driver driver = (Driver) o;
            driver.setId(getDriver().getId());
            return mDriverRemoteDataSource.setAuthentication(o);
        } else if (o instanceof UserDriverLicence) {
            UserDriverLicence driver = (UserDriverLicence) o;
            driver.setId(driver.getId());
            return mDriverRemoteDataSource.setAuthentication(o);
        } else if (o instanceof UserDriverCar) {
            UserDriverCar driver = (UserDriverCar) o;
            driver.setId(driver.getId());
            return mDriverRemoteDataSource.setAuthentication(o);
        }

        return null;
    }

    @NonNull
    @Override
    public Flowable<BaseResponse<String>> saveAuthentication(Object o) {
        if (o instanceof Driver) {
            Driver driver = (Driver) o;
            driver.setId(getDriver().getId());
            return mDriverRemoteDataSource.saveAuthentication(o);
        } else if (o instanceof UserDriverLicence) {

        } else if (o instanceof UserDriverCar) {

        }
        return null;
    }

    @NonNull
    @Override
    public Flowable<BaseResponse<DriverAuthentication>> getUserInfo(@Nullable String driverId) {
        if (driverId == null) {
            driverId = getDriver().getId();
        }
        return mDriverRemoteDataSource.getUserInfo(driverId).map(new Function<BaseResponse<DriverAuthentication>, BaseResponse<DriverAuthentication>>() {
            @Override
            public BaseResponse<DriverAuthentication> apply(BaseResponse<DriverAuthentication> driverAuthenticationBaseResponse) throws Exception {
                // 更新本地数据
                LoginResult loginResult = new LoginResult();
                loginResult.setUserInfo(driverAuthenticationBaseResponse.getResult().getUserInfo());
                loginResult.setCarInfo(driverAuthenticationBaseResponse.getResult().getCarInfo());
                loginResult.setLicenceInfo(driverAuthenticationBaseResponse.getResult().getLicenceInfo());
                mDriverLocalDataSource.saveOrUpdate(loginResult);
                return driverAuthenticationBaseResponse;
            }
        });
    }

    /**
     * 注册
     * @param mobile    手机
     * @param password  密码
     * @param authCode  验证码
     */
    @NonNull
    @Override
    public Flowable<BaseResponse<String>> simpleRegister(@NonNull String mobile, @NonNull String password, @NonNull String authCode) {
        return mDriverRemoteDataSource.simpleRegister(mobile, password, authCode);
    }

    @NonNull
    @Override
    public Flowable<BaseResponse<DriverStatusSwitch>> getDriverStatus() {
        return getDriverStatus(checkNotNull(getDriver().getId()));
    }

    /**
     * 获取司机休假状态
     *
     * @param driverId 司机id
     * @return 休假实体
     */
    @NonNull
    @Override
    public Flowable<BaseResponse<DriverStatusSwitch>> getDriverStatus(@NonNull String driverId) {
        return mDriverRemoteDataSource.getDriverStatus(checkNotNull(driverId));
    }

    /**
     * 删除司机休假状态
     *
     * @param planId 计划id
     * @return 结果
     */
    @NonNull
    @Override
    public Flowable<BaseResponse<String>> deleteLeavePlan(@NonNull String planId) {
        return mDriverRemoteDataSource.deleteLeavePlan(checkNotNull(planId));
    }

    /**
     * 获取司机帐号信息
     *
     * @return 司机实体
     */
    @NonNull
    @Override
    public Driver getDriver() {
        return mDriverLocalDataSource.getDriver();
    }

    @NonNull
    @Override
    public LoginResult getLoginResult() {
        return mDriverLocalDataSource.getLoginResult();
    }

    /**
     * 更新司机帐号信息
     *
     * @param loginResult 司机信息
     */
    @Override
    public void saveOrUpdate(@NonNull LoginResult loginResult) {
        mDriverLocalDataSource.saveOrUpdate(checkNotNull(loginResult));
    }

    /**
     * 注销登录
     */
    @Override
    public void logout() {
        mDriverLocalDataSource.logout();
    }

    /**
     * 更新司机信息
     *
     * @param driver 实体
     */
    @NonNull
    @Override
    public Flowable<BaseResponse<String>> update(@NonNull Driver driver) {
        return mDriverRemoteDataSource.update(driver);
    }

    /**
     * 更新休假计划
     *
     * @param id       计划id
     * @param endDate  结束时间
     * @param sendFlag 是否可向我派单
     * @return 成功失败标识
     */
    @NonNull
    @Override
    public Flowable<BaseResponse<String>> updateLeavePlan(@NonNull String id, @NonNull String endDate, @NonNull String sendFlag) {
        return mDriverRemoteDataSource.updateLeavePlan(checkNotNull(id), checkNotNull(endDate), checkNotNull(sendFlag));
    }

    /**
     * 添加司机工作状态
     *
     * @param userDriverStatusDetail 状态实体
     * @return 实时
     */
    @NonNull
    @Override
    public Flowable<BaseResponse<String>> addDriverRestPlan(@NonNull UserDriverStatusDetail userDriverStatusDetail) {
        userDriverStatusDetail.setDriverId(mDriverLocalDataSource.getDriver().getId());
        return mDriverRemoteDataSource.addDriverRestPlan(checkNotNull(userDriverStatusDetail));
    }


    /**
     * 登录
     *
     * @param username            用户名
     * @param password            密码
     * @param driverToken         推送token
     * @param loginMachine        设备信息
     * @param loginMachineVersion 设备信息
     * @param loginAppVersion     设备信息
     * @return 登录结果
     */
    @NonNull
    @Override
    public Flowable<BaseResponse<LoginResult>> login(@NonNull String username, @NonNull String password, @NonNull String driverToken, @NonNull String loginMachine, @NonNull String loginMachineVersion, @NonNull String loginAppVersion) {
        return mDriverRemoteDataSource.login(checkNotNull(username), checkNotNull(password), driverToken, loginMachine, loginMachineVersion, loginAppVersion);
    }
}
