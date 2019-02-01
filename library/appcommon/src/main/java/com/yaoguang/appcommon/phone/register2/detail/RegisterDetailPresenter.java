package com.yaoguang.appcommon.phone.register2.detail;

import android.content.Context;
import android.os.Bundle;

import com.yaoguang.datasource.common.ProvinceBeansDataSource;
import com.yaoguang.lib.common.constants.Constants;
import com.yaoguang.lib.entity.ProvinceBeans;
import com.yaoguang.lib.net.BaseObserver;
import com.yaoguang.lib.net.bean.BaseResponse;
import com.yaoguang.datasource.common.RegisterDataSource;
import com.yaoguang.datasource.company.CompanyUserDataSource;
import com.yaoguang.datasource.shipper.OwnerUserDataSource;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * 控制实现层
 * Created by zhongjh on 2017/11/29.
 */
public class RegisterDetailPresenter<T> implements RegisterDetailContact.Presenter<T> {

    RegisterDataSource mRegisterDataSource;
    private ProvinceBeansDataSource mProvinceBeansDataSource = new ProvinceBeansDataSource();
    protected CompositeDisposable mCompositeDisposable;
    private RegisterDetailContact.View<T> mView;
    private Context mContext;

    RegisterDetailPresenter(RegisterDetailContact.View<T> view, String codeType, Context context) {
        mView = view;
        mCompositeDisposable = new CompositeDisposable();
        mContext = context;
        switch (codeType) {
            default:
                mRegisterDataSource = new CompanyUserDataSource();
                break;
            case Constants.APP_SHIPPER:
                mRegisterDataSource = new OwnerUserDataSource();
                break;
            case Constants.APP_DRIVER:
                break;
        }
    }

    @Override
    public void subscribe() {
        analysisProvinceBeansJson();
    }

    @Override
    public void unSubscribe() {
        mCompositeDisposable.clear();
    }

    @Override
    public void handleRegister(T t, String strValidatorCode) {
        mRegisterDataSource.handleRegister(t, strValidatorCode)
                .delay(1200, TimeUnit.MILLISECONDS)
                .subscribeOn(Schedulers.io())               //在IO线程进行网络请求
                .observeOn(AndroidSchedulers.mainThread())  //回到主线程去处理请求结果
                .subscribe(new BaseObserver<BaseResponse<String>>(mCompositeDisposable, mView, true) {

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
                        mView.setProvinceBeans(value);
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
}
