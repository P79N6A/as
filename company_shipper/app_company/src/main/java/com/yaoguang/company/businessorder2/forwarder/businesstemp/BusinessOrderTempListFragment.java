package com.yaoguang.company.businessorder2.forwarder.businesstemp;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.yaoguang.company.R;
import com.yaoguang.company.businessorder2.forwarder.businesstemp.adapter.BusinessOrderTempListAdapter;
import com.yaoguang.company.databinding.FragmentBusinessOrderTempList2Binding;
import com.yaoguang.greendao.entity.company.AppCompanyOrderTemplate;
import com.yaoguang.lib.adapter.BaseLoadMoreRecyclerAdapter;
import com.yaoguang.lib.base.BaseFragmentListConditionDataBind;
import com.yaoguang.lib.base.interfaces.BasePresenterListCondition;

/**
 * Created by zhongjh on 2018/10/29.
 */

public class BusinessOrderTempListFragment extends BaseFragmentListConditionDataBind<AppCompanyOrderTemplate, String, BusinessOrderTempListAdapter, FragmentBusinessOrderTempList2Binding>
        implements BusinessOrderTempListContact.View {

    BusinessOrderTempListContact.Presenter mPresenter;

    public static BusinessOrderTempListFragment newInstance() {
        return new BusinessOrderTempListFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        mPresenter = new BusinessOrderTempListPresenter(this);
        super.onCreate(savedInstanceState);
    }

    @Override
    public BaseLoadMoreRecyclerAdapter getAdapter() {
        return new BusinessOrderTempListAdapter();
    }

    @Override
    public BasePresenterListCondition getPresenterrConditionList() {
        return mPresenter;
    }

    @Override
    public String getCondition(boolean isRegain) {
        return "";
    }

    @Override
    public void setConditionView(String condition) {

    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_business_order_temp_list2;
    }

    @Override
    public void init() {

    }

    @Override
    public void initListener() {

    }

}
