package com.yaoguang.company.businessorder2.common.temp;

import android.support.annotation.NonNull;

import com.yaoguang.datasource.company.CompanyOrderDataSource;
import com.yaoguang.greendao.entity.company.WebOrderTemplateWrapper;
import com.yaoguang.lib.base.impl.BasePresenterListCondition;
import com.yaoguang.lib.base.interfaces.BaseListConditionView;
import com.yaoguang.lib.net.bean.BaseResponse;
import com.yaoguang.lib.net.bean.PageList;

import io.reactivex.Observable;

/**
 * Created by zhongjh on 2018/11/14.
 */

public class TempListPresenter  extends BasePresenterListCondition<String, WebOrderTemplateWrapper> implements TempListContact.Presenter {

    private TempListContact.View mView;
    CompanyOrderDataSource mCompanyOrderDataSource = new CompanyOrderDataSource();

    public TempListPresenter(TempListContact.View view) {
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
    protected Observable<BaseResponse<PageList<WebOrderTemplateWrapper>>> initDatas(String condition, int pageIndex) {
        return mCompanyOrderDataSource.templateList(condition,pageIndex);
    }

}
