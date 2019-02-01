package com.yaoguang.company.activitys.login;

import android.content.Context;
import android.os.Bundle;

import com.yaoguang.appcommon.phone.activitys.login.BaseLoginPresenterImpl;
import com.yaoguang.lib.common.constants.Constants;
import com.yaoguang.lib.common.ObjectUtils;
import com.yaoguang.lib.common.SPUtils;
import com.yaoguang.lib.net.BaseObserver;
import com.yaoguang.lib.net.bean.BaseResponse;
import com.yaoguang.greendao.entity.AppLoginUser;
import com.yaoguang.greendao.entity.AppUserWrapper;

import java.util.concurrent.TimeUnit;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * 登录控制层
 * Created by zhongjh on 2017/6/6.
 */
public class LoginPresenterImpl extends BaseLoginPresenterImpl<AppUserWrapper> {

    private LoginContact.CLoginInteractor mCLoginInteractor;

    public LoginPresenterImpl(com.yaoguang.appcommon.phone.activitys.login.LoginContact.LoginView loginView) {
        super(loginView);
        mCLoginInteractor = new LoginInteractorImpl();
    }

    @Override
    protected void login(final String strUsername, final String strPassword, Context context) {
    }

    @Override
    protected void loginVersion2(final String strUsername, final String strPassword, Context context) {
        mCLoginInteractor.loginVersion2(strUsername, strPassword, context)
                .delay(1200, TimeUnit.MILLISECONDS)
                .subscribeOn(Schedulers.io())               //在IO线程进行网络请求
                .observeOn(AndroidSchedulers.mainThread())  //回到主线程去处理请求结果
                .subscribe(new BaseObserver<BaseResponse<AppLoginUser>>(mCompositeDisposable, loginView) {

                    @Override
                    public void onSuccess(BaseResponse<AppLoginUser> response) {

                    }

                    @Override
                    public void onNext(BaseResponse<AppLoginUser> value) {
                        if (value.getState().equals("200")) {
                            if (value.getResult().getType() == 0) {
                                // 登录成功
                                loginView.setSubmitProgressOK();
                                success(value.getResult().getUser(), strUsername, strPassword);
                            } else if (value.getResult().getType() == 1) {
                                // 审核中
                                loginView.setSubmitProgressError();
                                loginView.startAuthenticationIngActivity(value.getResult().getUserApply());
                                loginView.setSubmitProgressInit();
                            } else if (value.getResult().getType() == 2) {
                                // 审核不通过
                                loginView.setSubmitProgressError();
                                loginView.startAuthenticationErrorActivity(value.getResult().getUserApply());
                                loginView.setSubmitProgressInit();
                            } else if (value.getResult().getType() == 3) {
                                // 资料未完善
                                loginView.setSubmitProgressError();
                                loginView.startAuthenticationActivity(value.getResult().getUserApply());
                                loginView.setSubmitProgressInit();
                            }
                            else if (value.getResult().getType() == 5) {
                                // 需要扫码验证
                                loginView.setSubmitProgressError();
                                loginView.startGenerateTwoDimensionalCode(value.getResult().getUserId());
                                loginView.setSubmitProgressInit();
                            }
                        } else {
                            loginView.setSubmitProgressError();
                            fail(value.getMessage());
                            loginView.setSubmitProgressInit();
                        }
                    }

                });
    }

    @Override
    public void success(AppUserWrapper value, String strUsername, String strPassword) {
        // 初始化一些token,id值
        SPUtils.getInstance().put(Constants.TOKEN_ID, value.getId());
        SPUtils.getInstance().put(Constants.TOKEN_TOKEN, value.getSingleToken());
        //判断是否需要显示验证用户的窗口
        if (ObjectUtils.parseInt(ObjectUtils.parseString(value.getIsPhoneValid()), 1) == 0) {
            // 清除自动登录
            SPUtils.getInstance().put(Constants.AUTOLOGIN, false);
            loginView.startBindPhoneActivity(value,  strUsername,  strPassword);
        } else if(ObjectUtils.parseInt(ObjectUtils.parseString(value.getNeedResetPassword()), 1) == 1 &&
                ObjectUtils.parseInt(ObjectUtils.parseString(value.getIsPasswordReset()), 1) == 0)  {
            // 清除自动登录
            SPUtils.getInstance().put(Constants.AUTOLOGIN, false);
            loginView.startUpdatePasswordActivity(value.getId(), value.getMobile(),strPassword);
        } else  {
            toMain(value,strUsername,strPassword);
        }
    }

    @Override
    public void toMain(AppUserWrapper value, String strUsername, String strPassword){
        // 登录进首页
        loginView.setSubmitProgressSuccess(new Bundle());
        // 初始化一些token,id值
        SPUtils.getInstance().put(Constants.TOKEN_ID, value.getId());
        SPUtils.getInstance().put(Constants.TOKEN_TOKEN, value.getSingleToken());
        mCLoginInteractor.saveCompanyUserIsLogin(value, strUsername, strPassword);
    }



}
