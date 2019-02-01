package com.yaoguang.shipper.businessorder.forwarder.business;

import android.content.Context;

import com.mingle.entity.MenuEntity;
import com.yaoguang.appcompanyshipper.phone.businessorder.forwarder.business.BaseBusinessOrderContact;
import com.yaoguang.appcompanyshipper.phone.businessorder.forwarder.business.BaseBusinessOrderPresenter;
import com.yaoguang.datasource.shipper.OwnerForwardOrderDataSource;
import com.yaoguang.datasource.shipper.OwnerBaseInfoDataSource;
import com.yaoguang.greendao.entity.AppPublicInfoWrapper;
import com.yaoguang.greendao.entity.InfoContType;
import com.yaoguang.greendao.entity.shipper.AppOwnerForwardOrder;
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

public class BusinessOrderPresenter extends BaseBusinessOrderPresenter<AppOwnerForwardOrder> {

    private OwnerBaseInfoDataSource mOwnerBaseInfoDataSource = new OwnerBaseInfoDataSource();
    private OwnerForwardOrderDataSource mOwnerForwardOrderDataSource = new OwnerForwardOrderDataSource();

    public BusinessOrderPresenter(BaseBusinessOrderContact.View view, Context context, String id, AppOwnerForwardOrder appCompanyOrderWrapper) {
        super(view, context, id, appCompanyOrderWrapper);
    }

    @Override
    public void analysisSonos() {
        mOwnerBaseInfoDataSource.getConts()
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
        mOwnerBaseInfoDataSource.selectTraffic()
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
            mOwnerForwardOrderDataSource.getAppOrder(mID)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new BaseObserver<BaseResponse<AppOwnerForwardOrder>>(mCompositeDisposable, mView) {

                        @Override
                        public void onSuccess(BaseResponse<AppOwnerForwardOrder> response) {
                            mView.showDetail(response.getResult(), false);
                        }

                    });
        if (mAppCompanyOrderWrapper != null)
            mView.showDetail(mAppCompanyOrderWrapper, true);
    }

    @Override
    public void addCompanyOrder(AppOwnerForwardOrder appOwnerForwardOrder) {
        mOwnerForwardOrderDataSource.addCompanyOrder(appOwnerForwardOrder)
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
        mOwnerForwardOrderDataSource.cancelOrder(id)
            .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new BaseObserver<BaseResponse<String>>(mCompositeDisposable, mView) {

                        @Override
                        public void onSuccess(BaseResponse<String> response) {
                            mView.addContainerSuccess(response.getResult());
                        }

                    });
    }

}
