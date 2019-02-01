package com.yaoguang.company.businessorder2.common.list;

import android.support.annotation.NonNull;

import com.yaoguang.datasource.company.CompanyOrderDataSource;
import com.yaoguang.greendao.entity.SysConditionWrapper;
import com.yaoguang.greendao.entity.company.AppBusinessOrderListCondition;
import com.yaoguang.lib.base.impl.BasePresenterListCondition;
import com.yaoguang.lib.base.interfaces.BaseListConditionView;
import com.yaoguang.lib.net.BaseObserver;
import com.yaoguang.lib.net.bean.BaseResponse;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by zhongjh on 2018/11/14.
 */
public abstract class BaseBusinessOrderListPresenter<T> extends BasePresenterListCondition<SysConditionWrapper, T> implements BaseBusinessOrderListContact.Presenter {

    protected BaseBusinessOrderListContact.View mView;
    protected CompanyOrderDataSource mCompanyOrderDataSource = new CompanyOrderDataSource();
    protected String mBillType; // 0-货代，1-拖车

    public BaseBusinessOrderListPresenter(BaseBusinessOrderListContact.View view,String mBillType) {
        mView = view;
        this.mBillType = mBillType;
    }

    @Override
    public void subscribe() {
        loadOrderCondition2();
    }

    @Override
    public void loadOrderCondition2() {
        mCompanyOrderDataSource.loadOrderCondition2(mBillType)
                .subscribeOn(Schedulers.io())               //在IO线程进行网络请求
                .observeOn(AndroidSchedulers.mainThread())  //回到主线程去处理请求结果
                .subscribe(new BaseObserver<BaseResponse<AppBusinessOrderListCondition>>(mCompositeDisposable) {

                    @Override
                    public void onSuccess(BaseResponse<AppBusinessOrderListCondition> response) {
                        mView.initCondition(response.getResult());
                    }
                });
    }

    @NonNull
    @Override
    protected BaseListConditionView getBaseListView() {
        return mView;
    }
}
