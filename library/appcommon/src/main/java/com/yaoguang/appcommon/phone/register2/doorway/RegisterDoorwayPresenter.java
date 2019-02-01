package com.yaoguang.appcommon.phone.register2.doorway;

import android.os.Bundle;

import com.yaoguang.lib.base.BaseApplication;
import com.yaoguang.lib.common.SPUtils;
import com.yaoguang.lib.common.constants.Constants;
import com.yaoguang.lib.net.BaseObserver;
import com.yaoguang.lib.net.bean.BaseResponse;
import com.yaoguang.datasource.common.RegisterDataSource;
import com.yaoguang.datasource.common.SMSDataSource;
import com.yaoguang.datasource.company.CompanyUserDataSource;
import com.yaoguang.datasource.driver.DriverDataSource;
import com.yaoguang.datasource.shipper.OwnerUserDataSource;

import java.util.concurrent.TimeUnit;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * 注册的逻辑分发层
 * Created by zhongjh
 * on 2017 2017/3/31.9:54
 */
public class RegisterDoorwayPresenter<T> implements RegisterDoorwayContact.Presenter<T> {

    private RegisterDoorwayContact.View<T> mView;
    private RegisterDataSource mRegisterDataSource;
    private SMSDataSource mSMSDataSource;
    private CompositeDisposable mCompositeDisposable;

    RegisterDoorwayPresenter(RegisterDoorwayContact.View<T> view) {
        mView = view;
        mCompositeDisposable = new CompositeDisposable();
        mSMSDataSource = new SMSDataSource();
        switch (BaseApplication.getAppType()) {
            default:
                mRegisterDataSource = new CompanyUserDataSource();
                break;
            case Constants.APP_SHIPPER:
                mRegisterDataSource = new OwnerUserDataSource();
                break;
            case Constants.APP_DRIVER:
                mRegisterDataSource = new DriverDataSource();
                break;
        }
    }

    @Override
    public void getVerficationCode(String strMobile) {
        mSMSDataSource.getVerficationCode(strMobile)
                .subscribeOn(Schedulers.io())               //在IO线程进行网络请求
                .observeOn(AndroidSchedulers.mainThread())  //回到主线程去处理请求结果
                .subscribe(new BaseObserver<BaseResponse<String>>(mCompositeDisposable) {

                    @Override
                    public void onSubscribe(Disposable d) {
                        mView.startShowCountdown();
                    }

                    @Override
                    public void onFail(BaseResponse<String> response) {
                        super.onFail(response);
                        mView.stopShowCountdown();
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        mView.stopShowCountdown();
                    }

                    @Override
                    public void onSuccess(BaseResponse<String> response) {

                    }
                });
    }

    @Override
    public void fail(int intType, String strCode) {
        mView.showToast(strCode);
    }

    @Override
    public void handleOneAuth(String mobile, String pass, String auth, boolean cbProtocol) {
//        mViewStep2.nextPage();
//        return;
        if (mobile.length() != 11) {
            fail(2, "手机号码为11位");
            return;
        }
        if (auth.length() <= 0) {
            fail(0, "验证码不能为空");
            return;
        }
        if (pass.length() < 6 || pass.length() > 12) {
            fail(0, "密码长度在6-12之间");
            return;
        }
        if (!cbProtocol) {
            fail(2, "请先同意协议，才可以注册");
            return;
        }
        mRegisterDataSource.handleOneAuth(mobile, pass, auth)
                .delay(1200, TimeUnit.MILLISECONDS)
                .subscribeOn(Schedulers.io())               //在IO线程进行网络请求
                .observeOn(AndroidSchedulers.mainThread())  //回到主线程去处理请求结果
                .subscribe(new BaseObserver<BaseResponse<String>>(mCompositeDisposable, mView) {

                    @Override
                    public void onSuccess(BaseResponse<String> response) {
                        Bundle bundle = new Bundle();
                        bundle.putString("mobile", mobile);
                        bundle.putString("pass", pass);
                        // 存到缓存
                        SPUtils.getInstance().put(Constants.USERNAME, mobile);
                        SPUtils.getInstance().put(Constants.PASSWORD, pass);
                        mView.setSubmitProgressSuccess(bundle);
                    }

                });
    }

    @Override
    public void subscribe() {

    }

    @Override
    public void unSubscribe() {
        mCompositeDisposable.clear();
    }

}
