package com.yaoguang.company.businessorder2.forwarder.businesstemp;

import android.support.annotation.NonNull;

import com.yaoguang.datasource.company.CompanyOrderDataSource;
import com.yaoguang.greendao.entity.company.AppCompanyOrderTemplate;
import com.yaoguang.lib.base.impl.BasePresenterListCondition;
import com.yaoguang.lib.base.interfaces.BaseListConditionView;
import com.yaoguang.lib.net.bean.BaseResponse;
import com.yaoguang.lib.net.bean.PageList;

import io.reactivex.Observable;

/**
 * Created by zhongjh on 2018/10/29.
 */

public class BusinessOrderTempListPresenter extends BasePresenterListCondition<String, AppCompanyOrderTemplate> implements BusinessOrderTempListContact.Presenter {

    private BusinessOrderTempListContact.View mView;
    private CompanyOrderDataSource mCompanyOrderDataSource = new CompanyOrderDataSource();

    public BusinessOrderTempListPresenter(BusinessOrderTempListContact.View view) {
        mView = view;
    }

    @Override
    public void subscribe() {

    }

    @NonNull
    @Override
    protected BaseListConditionView getBaseListView() {
        return mView;
    }

    @Override
    protected Observable<BaseResponse<PageList<AppCompanyOrderTemplate>>> initDatas(String condition, int pageIndex) {
        return mCompanyOrderDataSource.getOrderTemplates( pageIndex);
    }

}
