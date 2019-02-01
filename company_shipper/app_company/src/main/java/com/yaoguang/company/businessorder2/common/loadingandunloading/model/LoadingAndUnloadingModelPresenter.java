package com.yaoguang.company.businessorder2.common.loadingandunloading.model;

import android.content.Context;

import com.yaoguang.datasource.company.CompanyBaseInfoDataSource;
import com.yaoguang.greendao.entity.company.InfoClientPlace;
import com.yaoguang.lib.net.BaseObserver;
import com.yaoguang.lib.net.bean.BaseResponse;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by zhongjh on 2018/11/6.
 */

public class LoadingAndUnloadingModelPresenter implements LoadingAndUnloadingModelContact.Presenter {

    LoadingAndUnloadingModelContact.View mView;
    CompositeDisposable mCompositeDisposable;
    CompanyBaseInfoDataSource mCompanyBaseInfoDataSource = new CompanyBaseInfoDataSource();


    /**
     * 从列表传递过来的对象(用于进行修改或者查看)
     */
    protected String mID;

    public LoadingAndUnloadingModelPresenter(LoadingAndUnloadingModelContact.View view, Context context, String id) {
        mView = view;
        mCompositeDisposable = new CompositeDisposable();
        mID = id;
    }

    @Override
    public void subscribe() {

    }

    @Override
    public void unSubscribe() {

    }

    @Override
    public void addLoadPlace(InfoClientPlace infoClientPlace){
        mCompanyBaseInfoDataSource.addLoadPlace(infoClientPlace)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<BaseResponse<String>>(mCompositeDisposable, mView) {
                    @Override
                    public void onSuccess(BaseResponse<String> value) {
                        mView.showToast(value.getResult());
                        mView.addLoadPlace();
                    }
                });
    }


}
