package com.yaoguang.interactor.company.analysis;

import com.yaoguang.lib.net.BaseObserver;
import com.yaoguang.lib.net.bean.BaseResponse;
import com.yaoguang.greendao.entity.AppPriceAnalysisWrapper;
import com.yaoguang.greendao.entity.PriceAnalysisCondition;
import com.yaoguang.interactor.BasePresenterImpl;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by zhongjh on 2017/6/12.
 */
public class AnalysisPresenterImpl extends BasePresenterImpl<AppPriceAnalysisWrapper> implements AnalysisContact.AnalysisPresenter {

    private AnalysisContact.AnalysisView mAnalysisView;
    private AnalysisContact.AnalysisInteractor mAnalysisInteractor;


    public AnalysisPresenterImpl(AnalysisContact.AnalysisView view) {
        mAnalysisView = view;
        mAnalysisInteractor = new AnalysisInteractorImpl();
        mCompositeDisposable = new CompositeDisposable();
    }

    @Override
    public void subscribe() {
        //解析柜型柜量数据
        analysisSonos();
    }

    @Override
    public void analysisSonos() {
        mAnalysisInteractor.analysisSonos()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<BaseResponse<List<String>>>(mCompositeDisposable,mAnalysisView) {

                    @Override
                    public void onSuccess(BaseResponse<List<String>> response) {
                        mAnalysisView.setSonos(response.getResult());
                    }

                });
    }

    @Override
    public void unSubscribe() {
        mCompositeDisposable.clear();
    }

    @Override
    public void refreshData(PriceAnalysisCondition condition) {
        pageIndex = 1;
        hasNextPage = true;
        getData(condition, 0, false);
    }

    @Override
    public void getData(PriceAnalysisCondition condition, int dataSize, boolean isNext) {
        Observable observable = mAnalysisInteractor.initDatas(condition, pageIndex)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
        subscribe(observable, mCompositeDisposable, mAnalysisView, dataSize, isNext, pageIndex);
    }

    @Override
    public void loadMoreData(PriceAnalysisCondition condition, int size) {
        if (hasNextPage) {
            pageIndex++;
            getData(condition, size, true);
        }
    }
}
