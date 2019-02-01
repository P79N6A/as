package com.yaoguang.driver.base.baseview;


import io.reactivex.disposables.CompositeDisposable;

/**
 * Project Name:driver
 * Created by weiliying
 * on 2017 2017/4/19.16:24
 */

public abstract class BasePresenter<V, D> {
    protected CompositeDisposable mCompositeDisposable = new CompositeDisposable();
    protected V mView;
    protected D mData;

    public void setView(V view) {
        this.mView = view;
    }

    public void setData(D mData) {
        this.mData = mData;
    }

    public abstract void subscribe();

    public void unSubscribe() {
        mCompositeDisposable.clear();
    }
}
