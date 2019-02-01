package com.yaoguang.appcompanyshipper.phone.businessorder.forwarder.templist;

import android.support.annotation.NonNull;

import com.yaoguang.greendao.entity.company.AppCompanyOrder;
import com.yaoguang.greendao.entity.company.AppCompanyOrderCondition;
import com.yaoguang.interactor.BasePresenterImpl;
import com.yaoguang.lib.base.impl.BasePresenterListCondition;
import com.yaoguang.lib.base.interfaces.BaseListConditionView;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

/**
 * 模板控制类
 * Created by zhongjh on 2017/6/12.
 */
public abstract class BaseBusinessOrderTempListPresenter<T,C> extends BasePresenterListCondition<C,T> implements BaseBusinessOrderTempListContact.Presenter<T,C>  {

    protected BaseBusinessOrderTempListContact.View mView;

    public BaseBusinessOrderTempListPresenter(BaseBusinessOrderTempListContact.View view) {
        mView = view;
    }

    @NonNull
    @Override
    protected BaseListConditionView getBaseListView() {
        return mView;
    }

    @Override
    public void subscribe() {
    }




}

