package com.yaoguang.company.businessorder.forwarder.business;

import android.content.Context;

import com.mingle.entity.MenuEntity;
import com.yaoguang.appcompanyshipper.phone.businessorder.forwarder.business.BaseBusinessOrderContact;
import com.yaoguang.appcompanyshipper.phone.businessorder.forwarder.business.BaseBusinessOrderPresenter;
import com.yaoguang.datasource.company.CompanyBaseInfoDataSource;
import com.yaoguang.datasource.company.CompanyOrderDataSource;
import com.yaoguang.greendao.entity.AppPublicInfoWrapper;
import com.yaoguang.greendao.entity.InfoContType;
import com.yaoguang.greendao.entity.company.AppCompanyOrder;
import com.yaoguang.lib.net.BaseObserver;
import com.yaoguang.lib.net.bean.BaseResponse;
import com.yaoguang.lib.net.bean.PageList;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by zhongjh on 2018/9/5.
 */

public class BusinessOrderPresenter extends BaseBusinessOrderPresenter<AppCompanyOrder> {


    private CompanyBaseInfoDataSource mCompanyBaseInfoDataSource = new CompanyBaseInfoDataSource();
    private CompanyOrderDataSource mCompanyOrderDataSource = new CompanyOrderDataSource();

    public BusinessOrderPresenter(BaseBusinessOrderContact.View view, Context context, String id, AppCompanyOrder appCompanyOrder) {
        super(view, context, id, appCompanyOrder);
    }

    @Override
    public void analysisSonos() {
        mCompanyBaseInfoDataSource.getConts()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<BaseResponse<List<InfoContType>>>(mCompositeDisposable, mView) {

                    @Override
                    public void onSuccess(BaseResponse<List<InfoContType>> response) {
                        if (response != null) {
                            List<MenuEntity> options1Items = new ArrayList<>();
                            for (InfoContType infoContType : response.getResult()) {
                                MenuEntity menuEntity = new MenuEntity();
                                menuEntity.title = infoContType.getContChina();
                                options1Items.add(menuEntity);
                            }
                            mView.setSonos(options1Items);
                            //解析操作条款、运输条款
                            analysisClause();
                        }
                    }
                });
    }

    @Override
    public void analysisClause() {
        mCompanyBaseInfoDataSource.selectTraffic()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<BaseResponse<PageList<AppPublicInfoWrapper>>>(mCompositeDisposable, mView) {


                    @Override
                    public void onSuccess(BaseResponse<PageList<AppPublicInfoWrapper>> response) {
                        List<MenuEntity> options1Items = new ArrayList<>();
                        for (AppPublicInfoWrapper appPublicInfoWrapper : response.getResult().getResult()) {
                            MenuEntity menuEntity = new MenuEntity();
                            menuEntity.title = appPublicInfoWrapper.getRemark1();
                            options1Items.add(menuEntity);
                        }
                        mView.setClause(options1Items);
                        //初始化明细，判断是否有id
                        initDetail();
                    }


                });
    }

    @Override
    public void initDetail() {
        if (mID != null)
            mCompanyOrderDataSource.getAppOrder(mID)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new BaseObserver<BaseResponse<AppCompanyOrder>>(mCompositeDisposable, mView) {

                        @Override
                        public void onSuccess(BaseResponse<AppCompanyOrder> response) {
                            mView.showDetail(response.getResult(), false);
                        }

                    });
        if (mAppCompanyOrderWrapper != null)
            mView.showDetail(mAppCompanyOrderWrapper, true);
    }

    @Override
    public void addCompanyOrder(AppCompanyOrder appCompanyOrder) {
        mCompanyOrderDataSource.addCompanyOrder(appCompanyOrder)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<BaseResponse<String>>(mCompositeDisposable, mView) {

                    @Override
                    public void onSuccess(BaseResponse<String> response) {
                        mView.addContainerSuccess(response.getResult());
                    }

                });
    }

    @Override
    public void cancelOrder(String id) {
        
    }

}
