package com.yaoguang.driver.phone.my.authentication.personal;

import com.yaoguang.datasource.common.DataStatic;
import com.yaoguang.datasource.driver.DriverAuthDataSource;
import com.yaoguang.datasource.driver.DriverDataSource;
import com.yaoguang.greendao.entity.Driver;
import com.yaoguang.greendao.entity.driver.LoginDriver;
import com.yaoguang.lib.base.impl.BasePresenter;
import com.yaoguang.lib.net.BaseObserver;
import com.yaoguang.lib.net.bean.BaseResponse;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * =====================================
 * 作    者: 韦理英
 * 版    本：$version
 * 创建日期：2018/02/08
 * 描    述：
 * 实名认证控制层
 * <p>
 * update zhongjh
 * data 2018/3/15
 * =====================================
 */

public class RealNameAuthenticationPresenter extends BasePresenter implements RealNameAuthenticationContacts.Presenter {

    private RealNameAuthenticationContacts.View mView;
    private DriverAuthDataSource mDriverAuthDataSource = new DriverAuthDataSource();
    private DriverDataSource mDriverDataSource = new DriverDataSource();

    RealNameAuthenticationPresenter(RealNameAuthenticationContacts.View view) {
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
                            mView.refreshData(response.getResult().getUserInfo());
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
                        mView.presenterPop();
                    }
                });
    }

    @Override
    public void submit(Driver driver) {
        mDriverAuthDataSource.setAuthentication(driver)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<BaseResponse<String>>(mCompositeDisposable, mView) {
                    @Override
                    public void onSuccess(BaseResponse<String> response) {
                        // 审核通过
                        LoginDriver loginDriver = DataStatic.getInstance().getLoginDriver();
                        loginDriver.getUserInfo().setIdAuditStatus(1);
                        DataStatic.getInstance().setDriver(loginDriver.getUserInfo());
                        mView.succeed(loginDriver);
                    }
                });
    }
}
