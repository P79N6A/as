package com.yaoguang.shipper.businessorder.trailer.business;

import android.content.Context;

import com.mingle.entity.MenuEntity;
import com.yaoguang.appcompanyshipper.phone.businessorder.trailer.business.BaseBusinessOrderContact;
import com.yaoguang.appcompanyshipper.phone.businessorder.trailer.business.BaseBusinessOrderPresenter;
import com.yaoguang.datasource.shipper.OwnerBaseInfoDataSource;
import com.yaoguang.datasource.shipper.OwnerTruckOrderDataSource;
import com.yaoguang.greendao.entity.AppPublicInfoWrapper;
import com.yaoguang.greendao.entity.InfoContType;
import com.yaoguang.greendao.entity.shipper.AppOwnerTruckOrder;
import com.yaoguang.lib.net.BaseObserver;
import com.yaoguang.lib.net.bean.BaseResponse;
import com.yaoguang.lib.net.bean.PageList;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by zhongjh on 2018/9/10.
 */

public class BusinessOrderPresenter extends BaseBusinessOrderPresenter<AppOwnerTruckOrder> {

    private OwnerBaseInfoDataSource mOwnerBaseInfoDataSource = new OwnerBaseInfoDataSource();
    private OwnerTruckOrderDataSource mOwnerTruckOrderDataSource = new OwnerTruckOrderDataSource();

    public BusinessOrderPresenter(BaseBusinessOrderContact.View cBusinessOrderView, Context context, String id, AppOwnerTruckOrder appOwnerTruckOrder) {
        super(cBusinessOrderView, context, id, appOwnerTruckOrder);
    }

    @Override
    public void analysisSonos() {
        mOwnerBaseInfoDataSource.getConts()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<BaseResponse<List<InfoContType>>>(mCompositeDisposable, mCBusinessOrderView) {

                    @Override
                    public void onSuccess(BaseResponse<List<InfoContType>> response) {
                        if (response != null) {
                            List<MenuEntity> options1Items = new ArrayList<>();
                            for (InfoContType infoContType : response.getResult()) {
                                MenuEntity menuEntity = new MenuEntity();
                                menuEntity.title = infoContType.getContChina();
                                options1Items.add(menuEntity);
                            }
                            mCBusinessOrderView.setSonos(options1Items);
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
                .subscribe(new BaseObserver<BaseResponse<PageList<AppPublicInfoWrapper>>>(mCompositeDisposable, mCBusinessOrderView) {

                    @Override
                    public void onSuccess(BaseResponse<PageList<AppPublicInfoWrapper>> response) {
                        List<MenuEntity> options1Items = new ArrayList<>();
                        for (AppPublicInfoWrapper appPublicInfoWrapper : response.getResult().getResult()) {
                            MenuEntity menuEntity = new MenuEntity();
                            menuEntity.title = appPublicInfoWrapper.getRemark1();
                            options1Items.add(menuEntity);
                        }
                        mCBusinessOrderView.setClause(options1Items);
                        //初始化明细，判断是否有id
                        initDetail();
                    }

                });
    }

    @Override
    public void initDetail() {
        if (mID != null)
            mOwnerTruckOrderDataSource.getAppOrder(mID)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new BaseObserver<BaseResponse<AppOwnerTruckOrder>>(mCompositeDisposable,mCBusinessOrderView) {

                        @Override
                        public void onSuccess(BaseResponse<AppOwnerTruckOrder> response) {
                            mCBusinessOrderView.showDetail(response.getResult(), false);
                        }


                    });
        if (mModel != null)
            mCBusinessOrderView.showDetail(mModel, true);
    }

    @Override
    public void editTruckOrder(AppOwnerTruckOrder appOwnerTruckOrder) {
        mOwnerTruckOrderDataSource.addCompanyOrder(appOwnerTruckOrder)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<BaseResponse<String>>(mCompositeDisposable,mCBusinessOrderView) {

                    @Override
                    public void onSuccess(BaseResponse<String> response) {
                        mCBusinessOrderView.addContainerSuccess(response.getResult());
                    }

                });
    }

    @Override
    public void cancelOrder(String id) {
        mOwnerTruckOrderDataSource.cancelOrder(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<BaseResponse<String>>(mCompositeDisposable, mCBusinessOrderView) {

                    @Override
                    public void onSuccess(BaseResponse<String> response) {
                        mCBusinessOrderView.addContainerSuccess(response.getResult());
                    }

                });
    }

}
