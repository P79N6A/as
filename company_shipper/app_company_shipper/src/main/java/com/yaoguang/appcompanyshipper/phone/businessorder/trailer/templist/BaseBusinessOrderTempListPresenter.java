package com.yaoguang.appcompanyshipper.phone.businessorder.trailer.templist;

import android.support.annotation.NonNull;

import com.yaoguang.lib.base.impl.BasePresenterListCondition;
import com.yaoguang.lib.base.interfaces.BaseListConditionView;

/**
 * 业务下单控制类
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
