package com.yaoguang.interactor.driver;

import com.yaoguang.lib.base.interfaces.BasePresenter;

import io.reactivex.disposables.CompositeDisposable;

/**
 * 作    者：韦理英
 * 版    权：
 */
public class DBasePresenterImpl implements BasePresenter {
    public CompositeDisposable mCompositeDisposable;

    public DBasePresenterImpl() {
        mCompositeDisposable = new CompositeDisposable();
    }

    @Override
    public void subscribe() {

    }

    @Override
    public void unSubscribe() {
        mCompositeDisposable.clear();
    }

}
