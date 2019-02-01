package com.yaoguang.appcommon.publicsearch.addaddress;

import android.content.Context;

import com.yaoguang.datasource.common.ProvinceBeansDataSource;
import com.yaoguang.lib.net.BaseObserver;
import com.yaoguang.lib.net.bean.BaseResponse;
import com.yaoguang.greendao.entity.company.InfoClientPlace;
import com.yaoguang.lib.entity.ProvinceBeans;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by zhongjh on 2017/6/12.
 */
public class AddAddressPresenterImpl implements AddAddressContact.CAddAddressPresenter {

    private AddAddressContact.CAddAddressView mCAddAddressView;
    private ProvinceBeansDataSource mProvinceBeansDataSource = new ProvinceBeansDataSource();
    private AddAddressContact.CAddAddressInteractor mCAddAddressInteractor;
    private CompositeDisposable mCompositeDisposable;
    private Context mContext;

    public AddAddressPresenterImpl(AddAddressContact.CAddAddressView cAddAddressView, Context context) {
        mCAddAddressView = cAddAddressView;
        mCAddAddressInteractor = new AddAddressInteractorImpl();
        mCompositeDisposable = new CompositeDisposable();
        mContext = context;
    }

    @Override
    public void subscribe() {
        //解析地址
        analysisProvinceBeansJson();
    }

    @Override
    public void unSubscribe() {
        mCompositeDisposable.clear();
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
                        mCAddAddressView.showProgressDialog();
                    }

                    @Override
                    public void onNext(ProvinceBeans value) {
                        mCAddAddressView.setProvinceBeans(value);
                    }
                    @Override
                    public void onError(Throwable e) {
                        mCAddAddressView.hideProgressDialog();
                    }

                    @Override
                    public void onComplete() {
                        mCAddAddressView.hideProgressDialog();
                    }
                });
    }

    @Override
    public void addLoadPlace(InfoClientPlace infoClientPlace) {

        mCAddAddressInteractor.addLoadPlace(infoClientPlace)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<BaseResponse<String>>(mCompositeDisposable,mCAddAddressView) {

                    @Override
                    public void onSuccess(BaseResponse<String> response) {
                        mCAddAddressView.addSuccess(response.getResult());
                    }

                });
    }

    @Override
    public void updateLoadPlace(InfoClientPlace infoClientPlace){
        mCAddAddressInteractor.addLoadPlace(infoClientPlace)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<BaseResponse<String>>(mCompositeDisposable,mCAddAddressView) {


                    @Override
                    public void onSuccess(BaseResponse<String> response) {
                        mCAddAddressView.addSuccess(response.getResult());
                    }

                });
    }

}
