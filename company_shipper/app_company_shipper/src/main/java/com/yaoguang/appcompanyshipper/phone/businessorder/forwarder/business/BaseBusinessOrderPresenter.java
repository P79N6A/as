package com.yaoguang.appcompanyshipper.phone.businessorder.forwarder.business;

import android.content.Context;

import com.yaoguang.greendao.entity.Container;

import io.reactivex.disposables.CompositeDisposable;

/**
 * Created by zhongjh on 2017/5/31.
 */
public abstract class BaseBusinessOrderPresenter<T> implements BaseBusinessOrderContact.Presenter<T> {

    protected BaseBusinessOrderContact.View mView;
    protected CompositeDisposable mCompositeDisposable;

    /**
     * 从列表传递过来的对象(用于进行修改或者查看)
     */
    protected String mID;
    /**
     * 从模版列表传递过来的对象(用于复用模版)
     */
    protected T mAppCompanyOrderWrapper;

    @Override
    public void subscribe() {
        //解析柜型柜量数据,解析完后，判断id是否存在，存在则读取这个订单的详情
        analysisSonos();
    }

    public BaseBusinessOrderPresenter(BaseBusinessOrderContact.View view, Context context, String id, T appCompanyOrderWrapper) {
        mView = view;
        mCompositeDisposable = new CompositeDisposable();
        mID = id;
        mAppCompanyOrderWrapper = appCompanyOrderWrapper;
    }

    @Override
    public void unSubscribe() {
        mCompositeDisposable.clear();
    }

    @Override
    public void addContainer() {
        Container container = new Container();
        container.setTitle("40GP");
        container.setCount(1);
        mView.addContainer(container);
    }




}
