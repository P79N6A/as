package com.yaoguang.driver.phone.my.personal;

import android.content.Context;
import android.text.TextUtils;

import com.yaoguang.datasource.common.DataStatic;
import com.yaoguang.datasource.driver.DriverDataSource;
import com.yaoguang.greendao.entity.Driver;
import com.yaoguang.greendao.entity.driver.LoginDriver;
import com.yaoguang.lib.base.impl.BasePresenter;
import com.yaoguang.lib.net.Api;
import com.yaoguang.lib.net.BaseObserver;
import com.yaoguang.lib.net.bean.BaseResponse;
import com.yaoguang.datasource.api.DriverApi;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by 韦理英
 * on 2017/4/25 0025.
 *
 * update zhongjh
 *
 */
public class PersonalInformationPresenter extends BasePresenter implements PersonalInformationContacts.Presenter {

    private PersonalInformationContacts.View mView;
    DriverDataSource mDriverDataSource = new DriverDataSource();

    PersonalInformationPresenter(PersonalInformationContacts.View view, Context context) {
        mView = view;
    }

    @Override
    public void showRemoteDriverInfo() {
        mDriverDataSource.getInfo()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<BaseResponse<LoginDriver>>(mCompositeDisposable) {
                    @Override
                    public void onSuccess(BaseResponse<LoginDriver> response) {
                        showLocalDriverInfo();
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        showLocalDriverInfo();
                    }

                    @Override
                    public void onFail(BaseResponse<LoginDriver> response) {
                        super.onFail(response);
                        showLocalDriverInfo();
                    }
                });
    }

    @Override
    public void showLocalDriverInfo() {
        // 显示信息
        mView.showDriverInfo(DataStatic.getInstance().getLoginDriver());
    }

    /**
     * 家乡
     *
     * @param province 省
     * @param city     城市
     * @param district 区
     */
    @Override
    public void modifyNativePlace(final String province, final String city, String district) {
        if (TextUtils.isEmpty(province) || TextUtils.isEmpty(city)) {
            mView.showToast("你没有选择省和市");
        } else {
            Driver driver = new Driver();
            driver.setId(DataStatic.getInstance().getDriver().getId());
            driver.setNativePlaceProvince(province);
            driver.setNativePlaceCity(city);
            driver.setNativePlaceDistrict(district);

            Observable<BaseResponse<String>> baseResponseObservable = Api.getInstance().retrofit.create(DriverApi.class).update(driver)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread());
            baseResponseObservable.subscribe(new BaseObserver<BaseResponse<String>>(mCompositeDisposable) {
                @Override
                public void onSuccess(BaseResponse<String> response) {
                    Driver driver = DataStatic.getInstance().getDriver();
                    driver.setNativePlaceProvince(province);
                    driver.setNativePlaceCity(city);
                    driver.setNativePlaceDistrict(district);
                    DataStatic.getInstance().setDriver(driver);
                    showLocalDriverInfo();
                }
            });
        }
    }

    @Override
    public void modifyCity(final String provinces, final String city) {
        if (TextUtils.isEmpty(provinces) || TextUtils.isEmpty(city)) {
            mView.showToast("你没有选择省和市");
        } else {
            Driver driver = new Driver();
            driver.setId(DataStatic.getInstance().getDriver().getId());
            driver.setPlaceProvince(provinces);
            driver.setPlaceCity(city);

            Observable<BaseResponse<String>> baseResponseObservable = Api.getInstance().retrofit.create(DriverApi.class).update(driver)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread());
            baseResponseObservable.subscribe(new BaseObserver<BaseResponse<String>>(mCompositeDisposable) {
                @Override
                public void onSuccess(BaseResponse<String> response) {
                    Driver driver = DataStatic.getInstance().getDriver();
                    driver.setPlaceProvince(provinces);
                    driver.setPlaceCity(city);
                    DataStatic.getInstance().setDriver(driver);
                    showLocalDriverInfo();
                }
            });
        }
    }

    @Override
    public void openDriverFragment(int i) {
        mDriverDataSource.getInfo()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<BaseResponse<LoginDriver>>(mCompositeDisposable, mView) {

                    @Override
                    public void onSuccess(BaseResponse<LoginDriver> loginDriver) {
                        switch (i){
                            case 0:
                                mView.openRealNameAuthenticationFragment(loginDriver.getResult());
                                break;
                            case 1:
                                mView.openDrivingLicenseAuthenticationFragment(loginDriver.getResult());
                                break;
                            case 2:
                                mView.openHeavyVehicleAuthenticationFragment(loginDriver.getResult());
                                break;
                            case 3:
                                mView.openHeavyVehicleAuthenticationFragment1(loginDriver.getResult());
                                break;
                        }
                    }

                });
    }

    @Override
    public void subscribe() {

    }

}
  