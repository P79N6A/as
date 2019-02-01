package com.yaoguang.driver.phone.my.authentication.heavyvehicle;

import com.yaoguang.datasource.common.DataStatic;
import com.yaoguang.datasource.driver.DriverAuthDataSource;
import com.yaoguang.datasource.driver.DriverDataSource;
import com.yaoguang.greendao.entity.Driver;
import com.yaoguang.greendao.entity.driver.LoginDriver;
import com.yaoguang.greendao.entity.driver.UserDriverCar;
import com.yaoguang.lib.base.impl.BasePresenter;
import com.yaoguang.lib.net.BaseObserver;
import com.yaoguang.lib.net.bean.BaseResponse;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

import static com.yaoguang.driver.phone.my.authentication.heavyvehicle.HeavyVehicleAuthenticationFragment.MOTOR_TRACTOR;

/**
 * =====================================
 * 作    者: 韦理英
 * 版    本：$version
 * 创建日期：2018/02/08
 * 描    述：
 * 重型半挂索引车控制层
 * =====================================
 */

public class HeavyVehicleAuthenticationPresenter extends BasePresenter implements HeavyVehicleAuthenticationContacts.Presenter {

    private HeavyVehicleAuthenticationContacts.View mView;
    private DriverAuthDataSource mDriverAuthDataSource = new DriverAuthDataSource();
    private DriverDataSource mDriverDataSource = new DriverDataSource();

    HeavyVehicleAuthenticationPresenter(HeavyVehicleAuthenticationContacts.View view) {
        mView = view;
    }


    @Override
    public void subscribe() {

    }

    @Override
    public void getData() {
        mDriverDataSource.getInfo()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<BaseResponse<LoginDriver>>(mCompositeDisposable) {
                    @Override
                    public void onSuccess(BaseResponse<LoginDriver> response) {
                        if (response.getResult() != null && response.getResult().getUserInfo() != null) {
                            mView.refreshData(response.getResult().getCarInfo());
                        }
                    }
                });
    }

    @Override
    public void save(Driver driver) {
        mDriverAuthDataSource.saveAuthentication(driver)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<BaseResponse<String>>(mCompositeDisposable, mView) {
                    @Override
                    public void onSuccess(BaseResponse<String> response) {
                        mView.showToast(response.getResult());
                        mView.hideProgressDialog();
                    }
                });
    }

    @Override
    public void submit(UserDriverCar driver, int licenceType) {
        mDriverAuthDataSource.setAuthentication(driver)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<BaseResponse<String>>(mCompositeDisposable, mView) {
                    @Override
                    public void onSuccess(BaseResponse<String> response) {
                        // 审核通过
                        LoginDriver loginDriver = DataStatic.getInstance().getLoginDriver();
                        loginDriver.getCarInfo().get(licenceType == MOTOR_TRACTOR ? 0 : 1).setAuditStatus("1");
                        DataStatic.getInstance().setUserDriverCars(loginDriver.getCarInfo());
                        mView.succeed(loginDriver, licenceType);
                    }
                });
    }
}
