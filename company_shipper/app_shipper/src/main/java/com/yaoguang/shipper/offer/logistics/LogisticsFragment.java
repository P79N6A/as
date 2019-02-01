package com.yaoguang.shipper.offer.logistics;

import android.view.View;

import com.yaoguang.greendao.entity.AppPublicInfoWrapper;
import com.yaoguang.lib.adapter.BaseLoadMoreRecyclerAdapter;
import com.yaoguang.lib.base.BaseFragmentListConditionDataBind;
import com.yaoguang.lib.base.interfaces.BasePresenterListCondition;
import com.yaoguang.shipper.R;
import com.yaoguang.shipper.databinding.FragmentLogisticsBinding;
import com.yaoguang.shipper.offer.OfferFragment;
import com.yaoguang.shipper.offer.logistics.adapter.LogisticsAdapter;

/**
 * 报价查询 - 企业管理
 * Created by zhongjh on 2018/8/29.
 */
public class LogisticsFragment extends BaseFragmentListConditionDataBind<AppPublicInfoWrapper, String, LogisticsAdapter, FragmentLogisticsBinding> implements LogisticsContact.View {

    LogisticsContact.Presenter mLogisticsPresenter = new LogisticsPresenter(this);
    String mName = "";

    public static LogisticsFragment newInstance() {
        return new LogisticsFragment();
    }

    @Override
    public BaseLoadMoreRecyclerAdapter getAdapter() {
        return new LogisticsAdapter(this);
    }

    @Override
    public BasePresenterListCondition getPresenterrConditionList() {
        return mLogisticsPresenter;
    }

    @Override
    public String getCondition(boolean isRegain) {
        return mName;
    }

    @Override
    public void setConditionView(String condition) {

    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_logistics;
    }

    @Override
    public void init() {
        if (mDataBinding.toolbarCommon != null) {
            initToolbarNav(mToolbarCommonBinding.toolbar, "报价查询", -1, null);
        }
    }

    @Override
    public void initListener() {
        // 打开该公司的报价表格
        mBaseLoadMoreRecyclerAdapter.setOnItemClickListener((itemView, item, position) -> start(OfferFragment.newInstance(mBaseLoadMoreRecyclerAdapter.getItem(position).getId(),mBaseLoadMoreRecyclerAdapter.getItem(position).getFullName())));
    }

}
