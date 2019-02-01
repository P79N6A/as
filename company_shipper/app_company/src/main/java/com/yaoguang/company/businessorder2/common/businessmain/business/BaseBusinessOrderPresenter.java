package com.yaoguang.company.businessorder2.common.businessmain.business;

import com.mingle.entity.MenuEntity;
import com.yaoguang.datasource.company.CompanyBaseInfoDataSource;
import com.yaoguang.datasource.company.CompanyOrderDataSource;
import com.yaoguang.greendao.entity.AppPublicInfoWrapper;
import com.yaoguang.greendao.entity.InfoContType;
import com.yaoguang.greendao.entity.company.InfoPaymentMethod;
import com.yaoguang.lib.net.BaseObserver;
import com.yaoguang.lib.net.bean.BaseResponse;
import com.yaoguang.lib.net.bean.PageList;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by zhongjh on 2018/11/15.
 */

public abstract class BaseBusinessOrderPresenter<T, AT> implements BaseBusinessOrderContact.Presenter<T, AT> {

    protected CompanyBaseInfoDataSource mCompanyBaseInfoDataSource = new CompanyBaseInfoDataSource();
    protected CompanyOrderDataSource mCompanyOrderDataSource = new CompanyOrderDataSource();

    protected BaseBusinessOrderContact.View mView;
    protected CompositeDisposable mCompositeDisposable;


    public BaseBusinessOrderPresenter(BaseBusinessOrderContact.View view) {
        mView = view;
        mCompositeDisposable = new CompositeDisposable();
    }

    @Override
    public void unSubscribe() {
        mCompositeDisposable.clear();
    }

    @Override
    public void analysisClause() {
        mCompanyBaseInfoDataSource.selectTraffic()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<BaseResponse<PageList<AppPublicInfoWrapper>>>(mCompositeDisposable, mView) {


                    @Override
                    public void onSuccess(BaseResponse<PageList<AppPublicInfoWrapper>> response) {
                        List<String> options1Items = new ArrayList<>();
                        for (AppPublicInfoWrapper appPublicInfoWrapper : response.getResult().getResult()) {
                            options1Items.add(appPublicInfoWrapper.getRemark1());
                        }
                        mView.setClause(options1Items);
                    }


                });
    }

    @Override
    public void searchPaymentMethod() {
        mCompanyBaseInfoDataSource.searchPaymentMethod()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<BaseResponse<List<InfoPaymentMethod>>>(mCompositeDisposable, mView) {
                    @Override
                    public void onSuccess(BaseResponse<List<InfoPaymentMethod>> response) {
                        List<String> options1Items = new ArrayList<>();
                        for (InfoPaymentMethod infoPaymentMethod : response.getResult()) {
                            options1Items.add(infoPaymentMethod.getPaymentMethod());
                        }
                        mView.searchPaymentMethod(options1Items);
                    }
                });
    }

    @Override
    public void getConts() {
        mCompanyBaseInfoDataSource.getConts()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<BaseResponse<List<InfoContType>>>(mCompositeDisposable, mView) {
                    @Override
                    public void onSuccess(BaseResponse<List<InfoContType>> response) {
                        mView.setSonos(response);
                    }
                });
    }

}
