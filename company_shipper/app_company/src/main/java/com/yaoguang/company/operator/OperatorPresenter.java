package com.yaoguang.company.operator;

import android.support.annotation.NonNull;

import com.yaoguang.datasource.company.CompanyBandanDataSource;
import com.yaoguang.greendao.entity.company.AppCompanyBanDanCondition;
import com.yaoguang.greendao.entity.company.AppCompanyBanDanWrapper;
import com.yaoguang.lib.base.impl.BasePresenterListCondition;
import com.yaoguang.lib.base.interfaces.BaseListConditionView;
import com.yaoguang.lib.net.BaseObserver;
import com.yaoguang.lib.net.bean.BaseResponse;
import com.yaoguang.lib.net.bean.PageList;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by zhongjh on 2018/7/26.
 */

public class OperatorPresenter extends BasePresenterListCondition<AppCompanyBanDanCondition, AppCompanyBanDanWrapper> implements OperatorContract.Presenter {

    private OperatorContract.View mView;
    private CompanyBandanDataSource mCompanyBandanDataSource = new CompanyBandanDataSource();

    OperatorPresenter(OperatorContract.View view) {
        mView = view;
    }

    @Override
    public void subscribe() {

    }

    @NonNull
    @Override
    protected BaseListConditionView getBaseListView() {
        return mView;
    }

    @Override
    protected Observable<BaseResponse<PageList<AppCompanyBanDanWrapper>>> initDatas(AppCompanyBanDanCondition appCompanyBanDanCondition, int pageIndex) {
        return mCompanyBandanDataSource.getList(appCompanyBanDanCondition, pageIndex);
    }

    @Override
    public void truckUpdate(String sonoIds, String operateType) {
        mCompanyBandanDataSource.truckUpdate(sonoIds, operateType)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<BaseResponse<String>>(mCompositeDisposable, mView) {
                    @Override
                    public void onSuccess(BaseResponse<String> value) {
                        mView.showToast(value.getResult());
                        mView.refreshDataAnimation();
                    }
                });
    }

    @Override
    public void freightUpdate(String sonoIds, String operateType) {
        mCompanyBandanDataSource.freightUpdate(sonoIds, operateType)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<BaseResponse<String>>(mCompositeDisposable, mView) {
                    @Override
                    public void onSuccess(BaseResponse<String> value) {
                        mView.showToast(value.getResult());
                        mView.refreshDataAnimation();
                    }
                });
    }

}
