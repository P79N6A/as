package com.yaoguang.appcommon.phone.my.modifyphone;

import android.content.Context;
import android.text.TextUtils;

import com.yaoguang.datasource.common.DataStatic;
import com.yaoguang.datasource.company.CompanyUserDataSource;
import com.yaoguang.datasource.driver.DriverDataSource;
import com.yaoguang.datasource.shipper.OwnerUserDataSource;
import com.yaoguang.greendao.entity.AppUserWrapper;
import com.yaoguang.greendao.entity.Driver;
import com.yaoguang.greendao.entity.UserOwner;
import com.yaoguang.interactor.driver.verficationcode.impl.SupportVerficationCodePresenter;
import com.yaoguang.lib.base.BaseApplication;
import com.yaoguang.lib.common.constants.Constants;
import com.yaoguang.lib.common.RegExpValidatorUtils;
import com.yaoguang.lib.common.SPUtils;
import com.yaoguang.lib.net.Api;
import com.yaoguang.lib.net.BaseObserver;
import com.yaoguang.lib.net.bean.BaseResponse;
import com.yaoguang.datasource.api.company.CompanyApi;
import com.yaoguang.datasource.api.DriverApi;
import com.yaoguang.datasource.api.shipper.OwnerApi;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * 包括修改手机号的第一步和第二步
 * Created by zhongjh on 2017/9/18.
 */
public class ModifyPhonePresenterImpl<T> extends SupportVerficationCodePresenter implements ModifyPhoneContact.Presenter<T> {

    private CompositeDisposable mCompositeDisposable;
    protected ModifyPhoneContact.View mView;
    protected ModifyPhoneContact.ViewStep2 mViewStep2;
    ModifyPhoneContact.Interactor mInteractor;
    String mCodeType;

    public ModifyPhonePresenterImpl(ModifyPhoneContact.View view, ModifyPhoneContact.ViewStep2 viewStep2, String codeType) {
        mView = view;
        mViewStep2 = viewStep2;
        mCompositeDisposable = new CompositeDisposable();
        mCodeType = codeType;
        mInteractor = new ModifyPhoneInteractorImpl();
    }

    @Override
    public void subscribe() {

    }

    @Override
    public void unSubscribe() {
        mCompositeDisposable.clear();
    }

    @Override
    public void login(String strPassword, Context context) {
        String id;
        switch (mCodeType) {
            case Constants.APP_DRIVER:
                id = DataStatic.getInstance().getDriver().getId();
                DriverDataSource driverDataSource = new DriverDataSource();

                driverDataSource.checkOldPassword(id, strPassword)
                        .subscribeOn(Schedulers.io())               //在IO线程进行网络请求
                        .observeOn(AndroidSchedulers.mainThread())  //回到主线程去处理请求结果
                        .subscribe(new BaseObserver<BaseResponse<String>>(mCompositeDisposable, mView) {
                            @Override
                            public void onSuccess(BaseResponse<String> response) {
                                //成功就跳转
                                mView.success();
                            }

                        });
                break;
            case Constants.APP_COMPANY:
                id = DataStatic.getInstance().getAppUserWrapper().getId();
                CompanyUserDataSource companyUserDataSource = new CompanyUserDataSource();
                Observable<BaseResponse<String>> observable = companyUserDataSource.checkOldPassword(id, strPassword);
                observable.subscribeOn(Schedulers.io())               //在IO线程进行网络请求
                        .observeOn(AndroidSchedulers.mainThread())  //回到主线程去处理请求结果
                        .subscribe(new BaseObserver<BaseResponse<String>>(mCompositeDisposable, mView) {
                            @Override
                            public void onSuccess(BaseResponse<String> response) {
                                //成功就跳转
                                mView.success();
                            }

                        });
                break;
            case Constants.APP_SHIPPER:
                id = DataStatic.getInstance().getUserOwner().getId();
                OwnerUserDataSource ownerUserDataSource = new OwnerUserDataSource();
                Observable<BaseResponse<String>> observables = ownerUserDataSource.checkOldPassword(id, strPassword);
                observables.subscribeOn(Schedulers.io())               //在IO线程进行网络请求
                        .observeOn(AndroidSchedulers.mainThread())  //回到主线程去处理请求结果
                        .subscribe(new BaseObserver<BaseResponse<String>>(mCompositeDisposable, mView) {
                            @Override
                            public void onSuccess(BaseResponse<String> response) {
                                //成功就跳转
                                mView.success();
                            }

                        });
                break;
        }
    }

    @Override
    public void getVerficationCode(String codeType, String newMobile, String isBind) {
        if (!check(newMobile)) {
            return;
        }
        String id = "";
        if (BaseApplication.getAppType().equals(Constants.APP_DRIVER)) {
            id = DataStatic.getInstance().getDriver().getId();
        } else if (BaseApplication.getAppType().equals(Constants.APP_COMPANY)) {
            id = DataStatic.getInstance().getAppUserWrapper().getId();
        } else if (BaseApplication.getAppType().equals(Constants.APP_COMPANY)) {
            id = DataStatic.getInstance().getUserOwner().getId();
        }
        getModityPhoneCode(codeType, newMobile, id, isBind)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<BaseResponse<String>>(mCompositeDisposable) {
                    @Override
                    public void onSubscribe(Disposable d) {
                        mViewStep2.startCountDown();
                    }

                    @Override
                    public void onSuccess(BaseResponse<String> response) {
                        switch (response.getState()) {
                            case "200":
                                mViewStep2.startCountDown();
                                break;
                            case "106":
                                //弹出提示
                                mViewStep2.showBindPhoneTips();
                                break;
                        }
                    }

                    @Override
                    public void onFail(BaseResponse<String> response) {
                        super.onFail(response);
                        mViewStep2.stopCountDown();
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        mViewStep2.stopCountDown();
                    }


                });
    }

    @Override
    public void setNewMobile(final String codeType, final String mobile, String authCode, String password) {
        if (!check(mobile)) {
            return;
        }

        if (TextUtils.isEmpty(authCode)) {
            mViewStep2.showToast("验证码没有输入");
            return;
        }

        String id;
        Observable<BaseResponse<String>> observables = null;
        switch (codeType) {
            case Constants.APP_DRIVER:
                id = DataStatic.getInstance().getDriver().getId();
                observables = Api.getInstance().retrofit.create(DriverApi.class).updateMobile(id, mobile, authCode, password);
                break;
            case Constants.APP_COMPANY:
                id = DataStatic.getInstance().getAppUserWrapper().getId();
                observables = Api.getInstance().retrofit.create(CompanyApi.class).updatePhone(id, mobile, authCode, password);
                break;
            case Constants.APP_SHIPPER:
                id = DataStatic.getInstance().getUserOwner().getId();
                observables = Api.getInstance().retrofit.create(OwnerApi.class).updatePhone(id, mobile, authCode, password);
                break;
        }
        if (observables != null)
            observables
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new BaseObserver<BaseResponse<String>>(mCompositeDisposable, mViewStep2) {

                        @Override
                        public void onSuccess(BaseResponse<String> response) {
                            switch (codeType) {
                                case Constants.APP_DRIVER:
                                    Driver d = DataStatic.getInstance().getDriver();
                                    d.setMobile(mobile);
                                    DataStatic.getInstance().setDriver(d);
                                    break;
                                case Constants.APP_COMPANY:
                                    AppUserWrapper appUserWrapper = DataStatic.getInstance().getAppUserWrapper();
                                    appUserWrapper.setMobile(mobile);
                                    DataStatic.getInstance().setAppUserWrapper(appUserWrapper);
                                    break;
                                case Constants.APP_SHIPPER:
                                    UserOwner userOwner = DataStatic.getInstance().getUserOwner();
                                    userOwner.setPhoto(mobile);
                                    DataStatic.getInstance().setUserOwner(userOwner);
                                    break;
                            }

                            // 清除token缓存
                            SPUtils.getInstance().put(Constants.TOKEN_ID, "");
                            SPUtils.getInstance().put(Constants.TOKEN_TOKEN, "");
                            // 清除自动登录
                            SPUtils.getInstance().put(Constants.USERNAME, "");
                            SPUtils.getInstance().put(Constants.PASSWORD, "");
                            SPUtils.getInstance().put(Constants.AUTOLOGIN, false);

                            mViewStep2.moditySuccess();
                        }

                    });
    }

    private boolean check(String newMobile) {
        if (TextUtils.isEmpty(newMobile) || !RegExpValidatorUtils.isMobile(newMobile)) {
            mViewStep2.showToast("新的手机号输入错误，请重新输入");
            return false;
        }
        return true;
    }


}
