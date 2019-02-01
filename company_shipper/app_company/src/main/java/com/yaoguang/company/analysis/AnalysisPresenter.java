package com.yaoguang.company.analysis;

import android.support.annotation.NonNull;

import com.yaoguang.lib.net.bean.BaseResponse;
import com.yaoguang.lib.net.bean.PageList;
import com.yaoguang.datasource.company.CompanyBaseInfoDataSource;
import com.yaoguang.datasource.company.CompanyPriceDataSource;
import com.yaoguang.greendao.entity.AppPriceAnalysisWrapper;
import com.yaoguang.greendao.entity.PriceAnalysisCondition;
import com.yaoguang.lib.base.impl.BasePresenterListCondition;
import com.yaoguang.lib.base.interfaces.BaseListConditionView;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * 控制层
 * Created by zhongjh on 2017/6/12.
 */
public class AnalysisPresenter extends BasePresenterListCondition<PriceAnalysisCondition, AppPriceAnalysisWrapper> implements AnalysisContact.Presenter {

    private AnalysisContact.View mView;
    private CompanyBaseInfoDataSource mCompanyBaseInfoDataSource;
    private CompanyPriceDataSource mCompanyPriceDataSource;


    AnalysisPresenter(AnalysisContact.View view) {
        mView = view;
        mCompanyBaseInfoDataSource = new CompanyBaseInfoDataSource();
        mCompanyPriceDataSource = new CompanyPriceDataSource();
    }

    @NonNull
    @Override
    protected BaseListConditionView getBaseListView() {
        return mView;
    }

    @Override
    public void subscribe() {
        //解析柜型柜量数据
        analysisSonos();
    }

    @Override
    protected Observable<BaseResponse<PageList<AppPriceAnalysisWrapper>>> initDatas(PriceAnalysisCondition condition, int pageIndex) {
        return mCompanyPriceDataSource.getAnalysis(condition, pageIndex);
    }

    @Override
    public void analysisSonos() {
        mCompanyBaseInfoDataSource.getContForSearch()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BaseResponse<List<String>>>() {

                    @Override
                    public void onSubscribe(Disposable d) {
                        mCompositeDisposable.add(d);
                    }

                    @Override
                    public void onNext(BaseResponse<List<String>> value) {
                        mView.setSonos(value.getResult());
                    }

                    @Override
                    public void onError(Throwable e) {
                    }

                    @Override
                    public void onComplete() {
                    }
                });
    }


}
