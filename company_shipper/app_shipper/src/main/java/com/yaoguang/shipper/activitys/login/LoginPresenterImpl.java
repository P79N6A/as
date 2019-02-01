package com.yaoguang.shipper.activitys.login;

import android.content.Context;

import com.yaoguang.appcommon.phone.activitys.login.BaseLoginPresenterImpl;
import com.yaoguang.lib.common.ObjectUtils;
import com.yaoguang.lib.net.bean.BaseResponse;
import com.yaoguang.greendao.entity.AppUserOwner;
import com.yaoguang.greendao.entity.UserOwner;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by zhongjh on 2017/6/6.
 */
public class LoginPresenterImpl extends BaseLoginPresenterImpl<UserOwner> {

    private LoginContact.SLoginInteractor mSLoginInteractor;

    public LoginPresenterImpl(com.yaoguang.appcommon.phone.activitys.login.LoginContact.LoginView loginView) {
        super(loginView);
        mSLoginInteractor = new LoginInteractorImpl();
    }

    @Override
    protected void login(final String strUsername, final String strPassword, Context context) {
    }

    @Override
    protected void loginVersion2(final String strUsername, final String strPassword, Context context) {
        mSLoginInteractor.loginVersion2(strUsername, strPassword, context)
                .delay(1200, TimeUnit.MILLISECONDS)
                .subscribeOn(Schedulers.io())               //在IO线程进行网络请求
                .observeOn(AndroidSchedulers.mainThread())  //回到主线程去处理请求结果
                .subscribe(new Observer<BaseResponse<AppUserOwner>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        mCompositeDisposable.add(d);
                        loginView.setSubmitProgressLoading();
                    }

                    @Override
                    public void onNext(BaseResponse<AppUserOwner> value) {
                        if (value.getState().equals("200")) {
                            if (ObjectUtils.parseInt(value.getResult().getType()) == 0) {
                                // 登录成功
                                loginView.setSubmitProgressOK();
                                success(value.getResult().getUserOwner(), strUsername, strPassword);
                            } else if (ObjectUtils.parseInt(value.getResult().getType()) == 1) {
                                // 审核中
                                loginView.setSubmitProgressError();
                                loginView.startAuthenticationIngActivity(value.getResult().getUserOwner());
                                loginView.setSubmitProgressInit();
                            } else if (ObjectUtils.parseInt(value.getResult().getType()) == 2) {
                                // 审核不通过
                                loginView.setSubmitProgressError();
                                loginView.startAuthenticationErrorActivity(value.getResult().getUserOwner());
                                loginView.setSubmitProgressInit();
                            } else if (ObjectUtils.parseInt(value.getResult().getType()) == 3) {
                                // 资料未完善
                                loginView.setSubmitProgressError();
                                loginView.startAuthenticationActivity(value.getResult().getUserOwner());
                                loginView.setSubmitProgressInit();
                            }
                        } else {
                            loginView.setSubmitProgressError();
                            fail(value.getMessage());
                            loginView.setSubmitProgressInit();
                        }
                    }


                    @Override
                    public void onError(Throwable e) {
                        loginView.setSubmitProgressError();
                        fail("登录失败");
                        loginView.setSubmitProgressInit();
                    }

                    @Override
                    public void onComplete() {
                    }
                });
    }

    @Override
    public void success(UserOwner value, String strUsername, String strPassword) {
        loginView.toMainActivityCustom();
        mSLoginInteractor.saveUserOwnerIsLogin(value, strUsername, strPassword);
    }

    @Override
    public void toMain(UserOwner value, String strUsername, String strPassword) {

    }


}
