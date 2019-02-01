package com.yaoguang.driver.my.personal;

import android.text.TextUtils;

import com.yaoguang.common.base.BaseApplication;
import com.yaoguang.common.common.ULog;
import com.yaoguang.driver.util.Injection;
import com.yaoguang.greendao.Injections;
import com.yaoguang.common.net.bean.BaseResponse;
import com.yaoguang.greendao.LoginResult;
import com.yaoguang.greendao.entity.Driver;
import com.yaoguang.common.net.Api;
import com.yaoguang.lib.annotation.apt.InstanceFactory;
import com.yaoguang.netmodule.api.DriverApi;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by 韦理英
 * on 2017/4/25 0025.
 */

@InstanceFactory
public class PersonalInformationPresenter extends PersonalInformationContacts.Presenter {

    /**
     * 获取远程司机信息
     */
    @Override
    void showRemoteDriverInfo() {
        mData.getUserInfo(null).subscribe(driverAuthenticationBaseResponse -> {
                    ULog.i("AccountSecurityFragmentPresenter showRemoteDriverInfo 信息加载成功");
                    showLocalDriverInfo();
                },
                throwable -> {
                    ULog.i("AccountSecurityFragmentPresenter showRemoteDriverInfo 信息加载失败");
                    showLocalDriverInfo();
                });
    }

    /**
     * 本地信息
     */
    @Override
    public void showLocalDriverInfo() {
        // 显示信息
        mView.showDriverInfo(mData.getLoginResult());
    }

    private void sendToServer(Driver driver, final Runnable runnable) {

        Observable<BaseResponse<String>> baseResponseObservable = Api.getInstance().retrofit.create(DriverApi.class).update(driver)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
        baseResponseObservable.subscribe(new Consumer<BaseResponse<String>>() {
            @Override
            public void accept(BaseResponse<String> stringBaseResponse) throws Exception {
                if (!stringBaseResponse.getState().equals("200")) {
                    mView.showToast(stringBaseResponse.getResult());
                } else if (stringBaseResponse.getState().equals("200")){
                    runnable.run();
                }
            }
        }, throwable -> mView.showToast("修改失败"));

    }


    /**
     * 家乡
     *
     *  @param province 省
     * @param city      城市
     * @param district  区
     */
    @Override
    public void modifyNativePlace(final String province, final String city, String district) {
        if (TextUtils.isEmpty(province) || TextUtils.isEmpty(city)) {
            mView.showToast("你没有选择省和市");
        } else {
            Driver driver = new Driver();
            driver.setId(Injection.provideDriverRepository(BaseApplication.getInstance()).getDriver().getId());
            driver.setNativePlaceProvince(province);
            driver.setNativePlaceCity(city);
            driver.setNativePlaceDistrict(district);
            sendToServer(driver, new Runnable() {
                @Override
                public void run() {
                    LoginResult loginResult = Injection.provideDriverRepository(BaseApplication.getInstance()).getLoginResult();
                    loginResult.getUserInfo().setNativePlaceProvince(province);
                    loginResult.getUserInfo().setNativePlaceCity(city);
                    loginResult.getUserInfo().setNativePlaceDistrict(district);
                    Injection.provideDriverRepository(BaseApplication.getInstance()).saveOrUpdate(loginResult);
                    showLocalDriverInfo();
                }
            });
        }
    }

    /**
     * 居住地
     * @param provinces 省
     * @param city      市
     */
    @Override
    public void modifyCity(final String provinces, final String city) {
        if (TextUtils.isEmpty(provinces) || TextUtils.isEmpty(city)) {
            mView.showToast("你没有选择省和市");
        } else {
            Driver driver = new Driver();
            driver.setId(Injection.provideDriverRepository(BaseApplication.getInstance()).getDriver().getId());
            driver.setPlaceProvince(provinces);
            driver.setPlaceCity(city);
            sendToServer(driver, new Runnable() {
                @Override
                public void run() {
                    LoginResult d = Injection.provideDriverRepository(BaseApplication.getInstance()).getLoginResult();
                    d.getUserInfo().setPlaceProvince(provinces);
                    d.getUserInfo().setPlaceCity(city);
                    Injection.provideDriverRepository(BaseApplication.getInstance()).saveOrUpdate(d);
                    showLocalDriverInfo();
                }
            });
        }
    }


    @Override
    public void subscribe() {
        showRemoteDriverInfo();
    }

}
  