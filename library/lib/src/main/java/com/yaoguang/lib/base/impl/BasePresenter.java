package com.yaoguang.lib.base.impl;

import io.reactivex.disposables.CompositeDisposable;

/**
 * 基类的实现控制层
 * Created by zhongjh on 2018/1/22.
 */
public class BasePresenter {

    public CompositeDisposable mCompositeDisposable;

    /**
     * 构造函数
     */
    protected BasePresenter() {
        mCompositeDisposable = new CompositeDisposable();
    }

    /**
     * 清除
     */
    public void unSubscribe() {
        mCompositeDisposable.clear();
    }

}
