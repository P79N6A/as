package com.yaoguang.interactor.company.pricetruck;

import com.yaoguang.lib.net.BaseObserver;
import com.yaoguang.lib.net.bean.BaseResponse;
import com.yaoguang.greendao.entity.AppPriceTruckWrapper;
import com.yaoguang.greendao.entity.PriceTruckCondition;
import com.yaoguang.interactor.BasePresenterImpl;
import com.yaoguang.interactor.company.analysis.AnalysisContact;
import com.yaoguang.interactor.company.analysis.AnalysisInteractorImpl;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by zhongjh on 2017/6/12.
 */
public class PriceTruckPresenterImpl extends BasePresenterImpl<AppPriceTruckWrapper> implements PriceTruckContact.PriceTruckPresenter {

    private PriceTruckContact.PriceTruckView mView;
    private PriceTruckContact.PriceTruckInteractor mInteractor;
    private AnalysisContact.AnalysisInteractor mAnalysisInteractor;
    private CompositeDisposable mCompositeDisposable;

    private int pageIndex;//下一页
    private boolean hasNextPage = true;//是否下一页

    public PriceTruckPresenterImpl(PriceTruckContact.PriceTruckView view) {
        mView = view;
        mInteractor = new PriceTruckInteractorImpl();
        mAnalysisInteractor = new AnalysisInteractorImpl();
        mCompositeDisposable = new CompositeDisposable();
    }

    @Override
    public void subscribe() {
        //解析柜型柜量数据
        analysisSonos();
    }

    @Override
    public void unSubscribe() {
        mCompositeDisposable.clear();
    }

    @Override
    public void analysisSonos() {
        mAnalysisInteractor.analysisSonos()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<BaseResponse<List<String>>>(mCompositeDisposable, mView) {

                    @Override
                    public void onSuccess(BaseResponse<List<String>> response) {
                        mView.setSonos(response.getResult());
                    }
                });
    }

    @Override
    public void refreshData(PriceTruckCondition condition) {
        pageIndex = 1;
        hasNextPage = true;
        getData(condition, 0, false);
    }

    @Override
    public void getData(PriceTruckCondition condition, int dataSize, boolean isNext) {
        Observable observable = mInteractor.initDatas(condition, pageIndex)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
        subscribe(observable, mCompositeDisposable, mView, dataSize, isNext, pageIndex);
    }

    @Override
    public void loadMoreData(PriceTruckCondition condition, int size) {
        if (hasNextPage) {
            pageIndex++;
            getData(condition, size, true);
        }
    }
}
