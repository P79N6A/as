package com.yaoguang.driver.phone.activitys.login;

import android.content.Context;
import android.os.Bundle;

import com.yaoguang.appcommon.phone.activitys.login.BaseLoginPresenterImpl;
import com.yaoguang.appcommon.phone.activitys.login.LoginContact;
import com.yaoguang.greendao.entity.driver.LoginDriver;
import com.yaoguang.lib.net.BaseObserver;
import com.yaoguang.lib.net.bean.BaseResponse;
import com.yaoguang.interfaces.driver.interactor.DLoginInteractor;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * 登录的逻辑分发层
 * Created by zhongjh
 * on 2017 2017/4/5
 */
public class DLoginPresenterImpl extends BaseLoginPresenterImpl<LoginDriver> {

    private DLoginInteractor mDLoginInteractor;

    public DLoginPresenterImpl(LoginContact.LoginView loginView) {
        super(loginView);
        mDLoginInteractor = new DLoginInteractorImpl();
    }

    @Override
    protected void login(final String strUsername, final String strPassword, final Context context) {

    }

    @Override
    protected void loginVersion2(final String strUsername, final String strPassword, Context context) {
        mDLoginInteractor.login(strUsername, strPassword, context)
                .delay(1200, TimeUnit.MILLISECONDS)
                .subscribeOn(Schedulers.io())               //在IO线程进行网络请求
                .observeOn(AndroidSchedulers.mainThread())  //回到主线程去处理请求结果
                .subscribe(new BaseObserver<BaseResponse<LoginDriver>>(mCompositeDisposable, loginView) {

                    @Override
                    public void onNext(BaseResponse<LoginDriver> value) {
                        if (value.getState().equals("200")) {
                            loginView.setSubmitProgressOK();
                            success(value.getResult(), strUsername, strPassword);
                        } else {
                            loginView.setSubmitProgressError();
                            fail(value.getMessage());
                            loginView.setSubmitProgressInit();
                        }
                    }

                    @Override
                    public void onSuccess(BaseResponse<LoginDriver> response) {

                    }
                });
    }


    @Override
    public void success(LoginDriver value, String strUsername, String strPassword) {
        mDLoginInteractor.loginSuccess(value, strUsername, strPassword);
        loginView.setSubmitProgressSuccess(new Bundle());
    }

    @Override
    public void toMain(LoginDriver value, String strUsername, String strPassword) {

    }
}
