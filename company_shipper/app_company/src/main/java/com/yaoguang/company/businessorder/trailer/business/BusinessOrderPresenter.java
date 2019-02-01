package com.yaoguang.company.businessorder.trailer.business;

import android.content.Context;

import com.mingle.entity.MenuEntity;
import com.yaoguang.appcompanyshipper.phone.businessorder.trailer.business.BaseBusinessOrderContact;
import com.yaoguang.appcompanyshipper.phone.businessorder.trailer.business.BaseBusinessOrderPresenter;
import com.yaoguang.datasource.company.TruckOrderDataSource;
import com.yaoguang.datasource.shipper.OwnerBaseInfoDataSource;
import com.yaoguang.greendao.entity.AppPublicInfoWrapper;
import com.yaoguang.greendao.entity.InfoContType;
import com.yaoguang.greendao.entity.company.AppTruckOrder;
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
public class BusinessOrderPresenter extends BaseBusinessOrderPresenter<AppTruckOrder> {

    private OwnerBaseInfoDataSource mOwnerBaseInfoDataSource = new OwnerBaseInfoDataSource();
    private TruckOrderDataSource mTruckOrderDataSource = new TruckOrderDataSource();

    public BusinessOrderPresenter(BaseBusinessOrderContact.View cBusinessOrderView, Context context, String id, AppTruckOrder appTruckOrder) {
        super(cBusinessOrderView, context, id, appTruckOrder);
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
            mTruckOrderDataSource.getAppOrder(mID)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new BaseObserver<BaseResponse<AppTruckOrder>>(mCompositeDisposable,mCBusinessOrderView) {

                        @Override
                        public void onSuccess(BaseResponse<AppTruckOrder> response) {
                            mCBusinessOrderView.showDetail(response.getResult(), false);
                        }


                    });
        if (mModel != null)
            mCBusinessOrderView.showDetail(mModel, true);
    }

    @Override
    public void editTruckOrder(AppTruckOrder appTruckOrder) {
        mTruckOrderDataSource.addCompanyOrder(appTruckOrder)
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

    }

}
