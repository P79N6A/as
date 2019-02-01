package com.yaoguang.shipper.sonos;

import com.yaoguang.greendao.entity.AppOwnerSonoCondition;
import com.yaoguang.greendao.entity.AppOwnerSonoWrapper;
import com.yaoguang.interactor.BasePresenterImpl;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

/**
 * 货柜查询控制层
 * Created by zhongjh on 2017/6/12.
 */
public class SonosPresenterImpl extends BasePresenterImpl<AppOwnerSonoWrapper> implements SonosContact.SonosPresenter {

    private SonosContact.SonosView mView;
    private SonosContact.SonosInteractor mInteractor;

    public SonosPresenterImpl(SonosContact.SonosView view) {
        mView = view;
        mInteractor = new SonosInteractorImpl();
        mCompositeDisposable = new CompositeDisposable();
    }

    @Override
    public void subscribe() {

    }

    @Override
    public void unSubscribe() {
        mCompositeDisposable.clear();
    }

    @Override
    public void refreshData(AppOwnerSonoCondition condition) {
        pageIndex = 1;
        hasNextPage = true;
        getData(condition, 0, false);
    }

    @Override
    public void getData(AppOwnerSonoCondition condition, int dataSize, boolean isNext) {
        Observable observable = mInteractor.initDatas(condition, pageIndex)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
        subscribe(observable, mCompositeDisposable, mView, dataSize, isNext, pageIndex);
    }

    @Override
    public void loadMoreData(AppOwnerSonoCondition condition, int size) {
        if (hasNextPage) {
            pageIndex++;
            getData(condition, size, true);
        }
    }

}
