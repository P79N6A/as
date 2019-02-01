package com.yaoguang.appcommon.phone.activitys.login;

import android.content.Context;
import android.text.TextUtils;

import com.yaoguang.greendao.entity.AppUserWrapper;

import io.reactivex.disposables.CompositeDisposable;

/**
 * 登录的逻辑分发层
 * Created by zhongjh
 * on 2017 2017/4/5
 */
public abstract class BaseLoginPresenterImpl<T> implements LoginContact.LoginPresenter<T> {

    protected LoginContact.LoginView loginView;
    protected CompositeDisposable mCompositeDisposable = new CompositeDisposable();

    public BaseLoginPresenterImpl(LoginContact.LoginView loginView) {
        this.loginView = loginView;
    }

    @Override
    public void fail(String code) {
        loginView.showToast(code);
        loginView.hideProgressDialog();
    }

    @Override
    public void loginAuthentication(String strUsername, String strPassword, Context context) {
        boolean isOK = true;

        if (TextUtils.isEmpty(strUsername)) {
            onFail(0, "用户名不能为空");
            isOK = false;
        } else if (TextUtils.isEmpty(strPassword)) {
            onFail(1, "密码不能为空");
            isOK = false;
        }

        if (isOK)
            loginVersion2(strUsername,strPassword,context);
    }

    protected abstract void login(String strUsername, String strPassword, Context context);

    protected abstract void loginVersion2(String strUsername, String strPassword, Context context);



    @Override
    public void onFail(int intType, String strCode) {
        if (intType == 0) {
            loginView.showToastUserName(strCode);
        } else {
            loginView.showToastPassWord(strCode);
        }
    }

    @Override
    public void subscribe() {
        loginView.rememberAccount();
    }

    @Override
    public void unSubscribe() {
        mCompositeDisposable.clear();
    }


}
