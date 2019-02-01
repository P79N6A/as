package com.yaoguang.interactor.driver.login.forgetpassword;

import android.text.TextUtils;

import com.yaoguang.lib.net.bean.BaseResponse;
import com.yaoguang.interfaces.driver.interactor.DForgotPasswordInteractor;
import com.yaoguang.interfaces.driver.presenter.DForgotPasswordPresenter;
import com.yaoguang.interfaces.driver.view.DForgetPasswordView;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * 忘记密码的逻辑分发层
 * Created by zhongjh
 * on 2017 2017/3/31.9:54
 */

public class DForgotPasswordPresenterImpl implements DForgotPasswordPresenter {

    private DForgotPasswordInteractor mDriverForgotPasswordInteractor;
    private DForgetPasswordView mForgetPassWordView;

    public DForgotPasswordPresenterImpl(DForgetPasswordView forgetPassWordView) {
        this.mForgetPassWordView = forgetPassWordView;
        mDriverForgotPasswordInteractor = new DForgotPasswordInteractorImpl();
    }

    @Override
    public void onModitySuccess() {
        mForgetPassWordView.showToast("修改密码成功");
        mForgetPassWordView.onModitySuccess();
    }

    @Override
    public void getVerficationCode(String strMobile) {
        mDriverForgotPasswordInteractor.getVerficationCode(strMobile)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BaseResponse<String>>() {

                    @Override
                    public void onSubscribe(Disposable d) {
                        mForgetPassWordView.startShowCountdown();
                    }

                    @Override
                    public void onNext(BaseResponse<String> value) {
                        if (!value.getState().equals("200")) {
                            onFail("获取验证码失败：" + value.getMessage());
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        onFail("获取验证码失败：" + e.getMessage());
                    }

                    @Override
                    public void onComplete() {
                        mForgetPassWordView.hideProgressDialog();
                    }
                });
    }

    @Override
    public void handleForgotPassword(String mobile, String strVerficationCode, String strPassWord1, String strPassWord2) {
        if (TextUtils.isEmpty(mobile)) {
            onFail("请输入手机号码");
            return;
        }
        if (TextUtils.isEmpty(strVerficationCode)) {
            onFail("请输入验证码");
            return;
        }
        //验证密码
        if (strPassWord1.length() < 6 || strPassWord1.length() > 12) {
            onFail("密码长度在6-12之间");
            return;
        }
        if (strPassWord2.length() < 6 || strPassWord2.length() > 12) {
            onFail("密码长度在6-12之间");
            return;
        }
        if (!strPassWord1.equals(strPassWord2)) {
            onFail("两个密码不相同");
            return;
        }

        mDriverForgotPasswordInteractor.handleForgotPassword(mobile, strVerficationCode, strPassWord1, strPassWord2)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BaseResponse<String>>() {

                    @Override
                    public void onSubscribe(Disposable d) {
                        mForgetPassWordView.showProgressDialog("密码修改中，请稍候");
                    }

                    @Override
                    public void onNext(BaseResponse<String> value) {
                        if (value.getState().equals("200")) {
                            //存储数据
                            onModitySuccess();
                        } else {
                            onFail("修改密码失败：" + value.getMessage());
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        mForgetPassWordView.hideProgressDialog();
                        onFail("修改密码失败");
                    }

                    @Override
                    public void onComplete() {
                        mForgetPassWordView.hideProgressDialog();
                    }
                });


    }

    @Override
    public void onFail(String strCode) {
        mForgetPassWordView.showToast(strCode);
        mForgetPassWordView.stopShowCountdown();
    }


    @Override
    public void subscribe() {

    }

    @Override
    public void unSubscribe() {

    }
}
