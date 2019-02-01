package com.yaoguang.interactor.driver.my.editdata;

import android.text.TextUtils;

import com.yaoguang.datasource.common.DataStatic;
import com.yaoguang.lib.common.ObjectUtils;
import com.yaoguang.lib.net.bean.BaseResponse;
import com.yaoguang.greendao.entity.Driver;
import com.yaoguang.interactor.driver.BasePresenterImplV2;
import com.yaoguang.interactor.driver.my.DMyInteractorImpl;
import com.yaoguang.interfaces.driver.personalcenter.DPersonalCenterInteractor;
import com.yaoguang.lib.net.Api;
import com.yaoguang.datasource.api.DriverApi;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by 韦理英
 * on 2017/4/25 0025.
 */

public class DEditDataPresenterImpl extends BasePresenterImplV2 implements DEditDateContacts.Presenter {

    public final DPersonalCenterInteractor mInteractor;
    private DEditDateContacts.View mView;

    public DEditDataPresenterImpl(DEditDateContacts.View view) {
        this.mView = view;
        this.mInteractor = new DMyInteractorImpl();
    }

    @Override
    public void showLocalDriverInfo() {
        Driver driver = mInteractor.getLocalDriverInfo();
        if (driver == null) {
            return;
        }
        String backgroundPicture = TextUtils.isEmpty(driver.getBackgroundPicture()) ? null : driver.getBackgroundPicture();  //  头像
        String photo = TextUtils.isEmpty(driver.getPhoto()) ? "" : driver.getPhoto();  //  头像
        String nativePlace = ObjectUtils.parseString(driver.getNativePlaceProvince()) + " " + ObjectUtils.parseString(driver.getNativePlaceCity());  // 籍贯
        String liveCity = ObjectUtils.parseString(driver.getPlaceProvince()) + " " + ObjectUtils.parseString(driver.getPlaceCity());  // 所在城市
        String email = ObjectUtils.parseString(driver.getEmail());  // 电子邮件
        String name = ObjectUtils.parseString(driver.getDriverName());  // 名字
        String mobile = ObjectUtils.parseString(driver.getMobile());  // 手机
        String dsc = ObjectUtils.parseString(driver.getSign());
        // 显示信息
        mView.showDriverInfo(photo, mobile, dsc, email, nativePlace, liveCity, name, backgroundPicture);
    }

    private void sendToServer(Driver driver, final Runnable runnable) {

        Observable<BaseResponse<String>> baseResponseObservable = Api.getInstance().retrofit.create(DriverApi.class).update(driver)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
        subscribe(baseResponseObservable,mView,ShowType.PROCESS,new OnCallback() {
            @Override
            public void onNext200(BaseResponse listBaseResponse) {
                mView.showToast(listBaseResponse.getResult().toString());
                runnable.run();
            }
        });

    }


    /**
     * 籍贯
     *
     * @param province 省
     * @param city 城市
     */
    @Override
    public void modifyNativePlace(final String province, final String city) {
        if (TextUtils.isEmpty(province) || TextUtils.isEmpty(city)) {
            mView.showToast("你没有选择省和市");
        } else {
            Driver driver = new Driver();
            driver.setId(DataStatic.getInstance().getDriver().getId());
            driver.setNativePlaceProvince(province);
            driver.setNativePlaceCity(city);
            sendToServer(driver, new Runnable() {
                @Override
                public void run() {
                    Driver d = DataStatic.getInstance().getDriver();
                    d.setNativePlaceProvince(province);
                    d.setNativePlaceCity(city);
                    DataStatic.getInstance().setDriver(d);
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
            sendToServer(driver, new Runnable() {
                @Override
                public void run() {
                    Driver d = DataStatic.getInstance().getDriver();
                    d.setPlaceProvince(provinces);
                    d.setPlaceCity(city);
                    DataStatic.getInstance().setDriver(d);
                    showLocalDriverInfo();
                }
            });
        }
    }


    @Override
    public void subscribe() {
        showLocalDriverInfo();
    }

}
  