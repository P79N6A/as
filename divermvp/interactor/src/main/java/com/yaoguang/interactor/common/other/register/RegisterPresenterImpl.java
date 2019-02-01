package com.yaoguang.interactor.common.other.register;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;

import com.yaoguang.datasource.common.ProvinceBeansDataSource;
import com.yaoguang.lib.common.constants.Constants;
import com.yaoguang.lib.net.BaseObserver;
import com.yaoguang.lib.net.bean.BaseResponse;
import com.yaoguang.lib.entity.ProvinceBeans;
import com.yaoguang.interactor.company.other.register.CRegisterInteractorImpl;
import com.yaoguang.interactor.driver.login.register.DRegisterInteractorImpl;
import com.yaoguang.interactor.shipper.other.register.SRegisterInteractorImpl;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * 注册的逻辑分发层
 * Created by zhongjh
 * on 2017 2017/3/31.9:54
 */
public class RegisterPresenterImpl<T> implements RegisterContact.RegisterPresenter<T> {

    private String mCodeType;
    protected RegisterContact.RegisterView<T> mView;
    protected RegisterContact.RegisterDetailView mDetailView;
    RegisterContact.RegisterInteractor mRegisterInteractor;
    private ProvinceBeansDataSource mProvinceBeansDataSource = new ProvinceBeansDataSource();
    protected CompositeDisposable mCompositeDisposable;
    private Context mContext;

    public RegisterPresenterImpl(RegisterContact.RegisterView<T> view, RegisterContact.RegisterDetailView detailView, String codeType, Context context) {
        mView = view;
        mDetailView = detailView;
        mCompositeDisposable = new CompositeDisposable();
        mCodeType = codeType;
        mContext = context;
        switch (mCodeType) {
            case Constants.APP_COMPANY:
                mRegisterInteractor = new CRegisterInteractorImpl();
                break;
            case Constants.APP_SHIPPER:
                mRegisterInteractor = new SRegisterInteractorImpl();
                break;
            case Constants.APP_DRIVER:
                mRegisterInteractor = new DRegisterInteractorImpl();
                break;
        }
    }


    @Override
    public void success() {
        mView.showToast("注册成功");
        mView.setSubmitProgressSuccess(new Bundle());
    }

    @Override
    public void fail(String strMessage) {
        mView.showToast(strMessage);
        mView.stopShowCountdown();
    }

    @Override
    public void getVerficationCode(String strMobile, String codeType) {
        if (TextUtils.isEmpty(strMobile)) {
            fail("请输入手机号码");
            return;
        }
        mRegisterInteractor.getVerficationCode(strMobile, codeType)
                .subscribeOn(Schedulers.io())               //在IO线程进行网络请求
                .observeOn(AndroidSchedulers.mainThread())  //回到主线程去处理请求结果
                .subscribe(new BaseObserver<BaseResponse<String>>(mCompositeDisposable) {

                    @Override
                    public void onSubscribe(Disposable d) {
                        super.onSubscribe(d);
                        mView.startShowCountdown();
                    }

                    @Override
                    public void onSuccess(BaseResponse<String> response) {

                    }

                });
    }

    @Override
    public void handleRegister(T t, String strValidatorCode) {
        mRegisterInteractor.handleRegister(t, strValidatorCode)
                .delay(1200, TimeUnit.MILLISECONDS)
                .subscribeOn(Schedulers.io())               //在IO线程进行网络请求
                .observeOn(AndroidSchedulers.mainThread())  //回到主线程去处理请求结果
                .subscribe(new BaseObserver<BaseResponse<String>>(mCompositeDisposable,mView,true) {

                    @Override
                    public void onSuccess(BaseResponse<String> response) {
                        mView.setSubmitProgressSuccess(new Bundle());
                    }

                });
    }

    @Override
    public void initDetail() {
        //解析地址
        analysisProvinceBeansJson();
    }

    @Override
    public void fail(int intType, String strCode) {
        mView.showToast(strCode);
    }

    @Override
    public void handleOneAuth(String mobile, String pass, String auth, boolean cbProtocol) {
//        mView.setSubmitProgressOK();
//        mView.setSubmitProgressSuccess();
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
        mRegisterInteractor.handleOneAuth(mobile, pass, auth)
                .delay(1200, TimeUnit.MILLISECONDS)
                .subscribeOn(Schedulers.io())               //在IO线程进行网络请求
                .observeOn(AndroidSchedulers.mainThread())  //回到主线程去处理请求结果
                .subscribe(new BaseObserver<BaseResponse<String>>(mCompositeDisposable,mView,true) {


                    @Override
                    public void onSuccess(BaseResponse<String> response) {
                        mView.setSubmitProgressSuccess(new Bundle());
                    }

                });
    }

    @Override
    public void analysisProvinceBeansJson() {
        mProvinceBeansDataSource.analysisProvinceBeansJson(mContext)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ProvinceBeans>() {

                    @Override
                    public void onSubscribe(Disposable d) {
                        mCompositeDisposable.add(d);
                        mView.showProgressDialog();
                    }

                    @Override
                    public void onNext(ProvinceBeans value) {
                        mDetailView.setProvinceBeans(value);
                    }

                    @Override
                    public void onError(Throwable e) {
                        mView.hideProgressDialog();
                    }

                    @Override
                    public void onComplete() {
                        mView.hideProgressDialog();
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
