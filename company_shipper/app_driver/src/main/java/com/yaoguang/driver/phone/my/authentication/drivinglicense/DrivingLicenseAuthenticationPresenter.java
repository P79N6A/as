package com.yaoguang.driver.phone.my.authentication.drivinglicense;

import com.yaoguang.datasource.common.DataStatic;
import com.yaoguang.datasource.driver.DriverAuthDataSource;
import com.yaoguang.datasource.driver.DriverDataSource;
import com.yaoguang.greendao.entity.Driver;
import com.yaoguang.greendao.entity.driver.LoginDriver;
import com.yaoguang.greendao.entity.driver.UserDriverLicence;
import com.yaoguang.lib.base.impl.BasePresenter;
import com.yaoguang.lib.net.BaseObserver;
import com.yaoguang.lib.net.bean.BaseResponse;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * =====================================
 * 作    者: zhongjh
 * 版    本：1.0
 * 创建日期：2018/05/16
 * 描    述：
 * 驾驶证认证控制层
 * =====================================
 */
public class DrivingLicenseAuthenticationPresenter extends BasePresenter implements DrivingLicenseAuthenticationContacts.Presenter {

    private DrivingLicenseAuthenticationContacts.View mView;
    private DriverAuthDataSource mDriverAuthDataSource = new DriverAuthDataSource();
    private DriverDataSource mDriverDataSource = new DriverDataSource();

    DrivingLicenseAuthenticationPresenter(DrivingLicenseAuthenticationContacts.View view) {
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
                .subscribe(new BaseObserver<BaseResponse<LoginDriver>>(mCompositeDisposable, mView) {
                    @Override
                    public void onSuccess(BaseResponse<LoginDriver> response) {
                        if (response.getResult() != null && response.getResult().getUserInfo() != null) {
                            mView.refreshData(response.getResult().getLicenceInfo());
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
    public void submit(UserDriverLicence userDriverLicence) {
        mDriverAuthDataSource.setAuthentication(userDriverLicence)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<BaseResponse<String>>(mCompositeDisposable, mView) {
                    @Override
                    public void onSuccess(BaseResponse<String> response) {
                        // 审核通过
                        LoginDriver loginDriver = DataStatic.getInstance().getLoginDriver();
                        loginDriver.getLicenceInfo().setAuditStatus(1);
                        DataStatic.getInstance().setUserDriverLicence(loginDriver.getLicenceInfo());
                        mView.succeed(loginDriver);
                    }
                });
    }
}
