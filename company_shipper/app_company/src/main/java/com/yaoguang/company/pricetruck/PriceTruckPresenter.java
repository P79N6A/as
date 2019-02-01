package com.yaoguang.company.pricetruck;

import android.support.annotation.NonNull;

import com.yaoguang.lib.base.impl.BasePresenterListCondition;
import com.yaoguang.lib.base.interfaces.BaseListConditionView;
import com.yaoguang.lib.net.BaseObserver;
import com.yaoguang.lib.net.bean.BaseResponse;
import com.yaoguang.lib.net.bean.PageList;
import com.yaoguang.datasource.company.CompanyBaseInfoDataSource;
import com.yaoguang.datasource.company.CompanyPriceDataSource;
import com.yaoguang.greendao.entity.AppPriceTruckWrapper;
import com.yaoguang.greendao.entity.PriceTruckCondition;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * @author zhongjh
 * @Package com.yaoguang.company.pricetruck
 * @Description: 拖车报价查询 控制层
 * @date 2018/01/05
 */
public class PriceTruckPresenter extends BasePresenterListCondition<PriceTruckCondition, AppPriceTruckWrapper> implements PriceTruckContract.Presenter {

    private PriceTruckContract.View mView;
    private CompanyBaseInfoDataSource mCompanyBaseInfoDataSource;
    private CompanyPriceDataSource mCompanyPriceDataSource;

    PriceTruckPresenter(PriceTruckContract.View view) {
        mView = view;
        mCompanyBaseInfoDataSource = new CompanyBaseInfoDataSource();
        mCompanyPriceDataSource = new CompanyPriceDataSource();
    }

    @Override
    public void subscribe() {
        //解析柜型柜量数据
        analysisSonos();
    }

    @NonNull
    @Override
    protected BaseListConditionView getBaseListView() {
        return mView;
    }

    @Override
    protected Observable<BaseResponse<PageList<AppPriceTruckWrapper>>> initDatas(PriceTruckCondition condition, int pageIndex) {
        return mCompanyPriceDataSource.getTruckFee(condition, pageIndex);
    }

    @Override
    public void analysisSonos() {
        mCompanyBaseInfoDataSource.getContForSearch()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<BaseResponse<List<String>>>(mCompositeDisposable) {

                    @Override
                    public void onSuccess(BaseResponse<List<String>> response) {
                        mView.setSonos(response.getResult());
                    }
                });
    }

}
