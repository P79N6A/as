package com.yaoguang.appcompanyshipper.phone.businessorder.trailer.list;

import android.support.annotation.NonNull;

import com.yaoguang.lib.base.impl.BasePresenterListCondition;
import com.yaoguang.lib.base.interfaces.BaseListConditionView;

/**
 * 业务下单控制类
 * Created by zhongjh on 2017/6/12.
 */
public abstract class BaseBusinessOrderTrailerListPresenter<C, T>  extends BasePresenterListCondition<C, T> implements BaseBusinessOrderTrailerListContact.Presenter<T,C>  {

    protected BaseBusinessOrderTrailerListContact.View mView;

    public BaseBusinessOrderTrailerListPresenter(BaseBusinessOrderTrailerListContact.View view) {
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
