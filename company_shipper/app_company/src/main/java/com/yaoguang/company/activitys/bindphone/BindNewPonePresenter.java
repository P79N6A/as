package com.yaoguang.company.activitys.bindphone;

import android.content.Context;
import android.text.TextUtils;

import com.yaoguang.company.activitys.login.LoginContact;
import com.yaoguang.company.activitys.login.LoginInteractorImpl;
import com.yaoguang.lib.common.constants.Constants;
import com.yaoguang.lib.common.RegExpValidatorUtils;
import com.yaoguang.lib.net.BaseObserver;
import com.yaoguang.lib.net.bean.BaseResponse;
import com.yaoguang.greendao.entity.AppLoginUser;
import com.yaoguang.interactor.driver.verficationcode.impl.SupportVerficationCodePresenter;
import com.yaoguang.lib.net.Api;
import com.yaoguang.datasource.api.company.CompanyApi;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * 对于员工分配的账户，需要有登陆前的验证,只需一次的登录验证，验证通过后即可登录，以后该账户登陆时无需再
 * Created by zhongjh on 2017/8/30.
 */
public class BindNewPonePresenter extends SupportVerficationCodePresenter implements BindNewPoneContact.Presenter {

    private BindNewPoneContact.View view;
    private LoginContact.CLoginInteractor mCLoginInteractor;
    private CompositeDisposable mCompositeDisposable;

    public BindNewPonePresenter(BindNewPoneContact.View view) {
        this.view = view;
        mCLoginInteractor = new LoginInteractorImpl();
        mCompositeDisposable = new CompositeDisposable();
    }

    @Override
    public void subscribe() {

    }

    @Override
    public void unSubscribe() {
        mCompositeDisposable.clear();
    }

    @Override
    public void getVerficationCode(String codeType, String newMobile, String userId, String isBind) {
        if (!check(newMobile)) {
            view.showToast("新的手机号输入错误，请重新输入");
            return;
        }

        getModityPhoneCode(codeType, newMobile, userId, isBind)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<BaseResponse<String>>(mCompositeDisposable) {

                    @Override
                    public void onSubscribe(Disposable d) {
                        super.onSubscribe(d);
                        view.startCountDown();
                    }

                    @Override
                    public void onSuccess(BaseResponse<String> response) {
                        switch (response.getState()) {
                            case "200":
                                view.startCountDown();
                                break;
                            case "106":
                                //弹出提示
                                view.showBindPhoneTips();
                                break;
                        }
                    }

                    @Override
                    public void onFail(BaseResponse<String> response) {
                        super.onFail(response);
                        view.stopCountDown();
                    }

                    @Override
                    public void onError(Throwable e) {
                        view.showToast("获取验证码失败");
                        view.stopCountDown();
                    }


                });
    }

    private boolean check(String newMobile) {
        if (TextUtils.isEmpty(newMobile) || !RegExpValidatorUtils.isMobile(newMobile)) {
            view.showToast("新的手机号输入错误，请重新输入");
            return false;
        }
        return true;
    }


    @Override
    public void setNewMobile(String codeType, String id, String mobile, String authCode, final String passWord, final String loginMobile) {
        if (!check(mobile)) {
            return;
        }

        if (TextUtils.isEmpty(authCode)) {
            view.showToast("验证码没有输入");
            return;
        }

        Observable<BaseResponse<String>> observables = null;
        switch (codeType) {
            //暂时只有物流端有用
            case Constants.APP_DRIVER:
//                id = Injections.getDriverBiz().queryIsLogin().getId();
//                observables = Api.getInstance().retrofit.create(DriverApi.class).updateMobile(id, mobile, authCode);
                break;
            case Constants.APP_COMPANY:
//                id = Injections.getCompanyUserBiz().queryIsLogin().getId();
                observables = Api.getInstance().retrofit.create(CompanyApi.class).bindMobile(id, mobile, authCode);
                break;
            case Constants.APP_SHIPPER:
//                id = Injections.getUserOwnerBiz().queryIsLogin().getId();
//                observables = Api.getInstance().retrofit.create(OwnerApi.class).updatePhone(id, mobile, authCode);
                break;
        }
        if (observables != null)
            observables
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new BaseObserver<BaseResponse<String>>(mCompositeDisposable,view) {
                        @Override
                        public void onSuccess(BaseResponse<String> response) {
                            view.moditySuccess(loginMobile, passWord);
                        }
                    });
    }

    @Override
    public void login(final String loginMobile, final String strPassword, Context baseContext) {
        mCLoginInteractor.loginVersion2(loginMobile, strPassword, baseContext)
                .subscribeOn(Schedulers.io())               //在IO线程进行网络请求
                .observeOn(AndroidSchedulers.mainThread())  //回到主线程去处理请求结果
                .subscribe(new BaseObserver<BaseResponse<AppLoginUser>>(mCompositeDisposable) {

                    @Override
                    public void onSuccess(BaseResponse<AppLoginUser> response) {
                        mCLoginInteractor.saveCompanyUserIsLogin(response.getResult().getUser(), loginMobile, strPassword);
                        view.loginSuccess();
                    }

                });
    }

}
