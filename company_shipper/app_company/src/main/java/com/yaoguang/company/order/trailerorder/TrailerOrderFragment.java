package com.yaoguang.company.order.trailerorder;

import android.os.Bundle;
import android.view.View;

import com.yaoguang.appcommon.databinding.FragmentOrderBinding;
import com.yaoguang.appcommon.phone.order.OrderContract;
import com.yaoguang.appcommon.phone.order.BaseOrderFragment;
import com.yaoguang.appcommon.search.SearchFragment;
import com.yaoguang.greendao.entity.AppPublicInfoWrapper;
import com.yaoguang.lib.adapter.BaseLoadMoreRecyclerAdapter;
import com.yaoguang.lib.common.constants.Constants;
import com.yaoguang.lib.common.ObjectUtils;
import com.yaoguang.company.R;
import com.yaoguang.company.order.trailerorder.adapter.TrailerOrderAdapter;
import com.yaoguang.greendao.entity.SysCondition;
import com.yaoguang.greendao.entity.SysConditionWrapper;
import com.yaoguang.greendao.entity.common.TruckBills;
import com.yaoguang.appcommon.publicsearch.PublicSearchInteractorImpl;

import java.util.List;

/**
 * 拖车
 * Created by zhongjh on 2018/1/11.
 */

public class TrailerOrderFragment extends BaseOrderFragment<TruckBills, TrailerOrderAdapter, FragmentOrderBinding> {

    public static TrailerOrderFragment newInstance() {
        TrailerOrderFragment trailerOrderFragment = new TrailerOrderFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(BUNDLE_BILLTYPE, 1);
        trailerOrderFragment.setArguments(bundle);
        return trailerOrderFragment;
    }

    @Override
    public void setRlUserOnClick() {
        //托运人
        SearchFragment fragment = SearchFragment.newInstance(PublicSearchInteractorImpl.TYPE_INFOCLIENTMANAGER);
        startForResult(fragment, ObjectUtils.parseInt(PublicSearchInteractorImpl.TYPE_INFOCLIENTMANAGER));
    }

    @Override
    public void setContactCompany(AppPublicInfoWrapper appPublicInfoWrapper) {
        // 目前只有货主用到
    }

    @Override
    public OrderContract.Presenter newPresenter() {
        return new TrailerOrderPresenter(TrailerOrderFragment.this, mBillType);
    }

    @Override
    public BaseLoadMoreRecyclerAdapter getAdapter() {
        return new TrailerOrderAdapter();
    }

    @Override
    protected void customInitView() {
        mDataBinding.rlMulitCheck.setVisibility(View.GONE);// 这个没有线所以不需要隐藏

        mDataBinding.llBusinessDate.setVisibility(View.GONE);
        mDataBinding.vBusinessDate.setVisibility(View.GONE);
        mDataBinding.llLoadDate.setVisibility(View.GONE);
        mDataBinding.vLoadDate.setVisibility(View.GONE);

        mToolbarCommonBinding.toolbarTitle.setText("拖车订单跟踪");
        mDataBinding.tvUser.setText("托运人");
        mDataBinding.tvValueUser.setHint(getResources().getString(R.string.unlimited));
    }

    @Override
    protected void customInitListener() {
        mBaseLoadMoreRecyclerAdapter.setOnItemClickListener((itemView, item, position) -> {
            TruckBills truckBills = (TruckBills) item;
            start(TrailerDetailFragment.newInstance(truckBills.getId()), SINGLETOP);
        });
        //重新写清空事件
        mDataBinding.btnEmpty.setOnClickListener(v -> {
            mDataBinding.etValueCount.setText("");
            mDataBinding.tvValueUser.setText("");
            mDataBinding.tvValueLoadingOrUnLoading.setText("");
            mDataBinding.etValueOrderId.setText("");
            mDataBinding.etValueOrderNo.setText("");
            mDataBinding.tvStartDateType.setText("");
            mDataBinding.tvEndDateType.setText("");
            mDataBinding.tvDateType.setText("");
            mDataBinding.tvStartBusinessDate.setText("");
            mDataBinding.tvEndBusinessDate.setText("");
            mDataBinding.tvStartLoadDate.setText("");
            mDataBinding.tvEndLoadDate.setText("");
        });
    }

    @Override
    protected void customGetCondition(List<SysCondition> sysCondition) {

    }

    @Override
    protected void customSetConditionView(SysConditionWrapper mCondition) {

    }

    @Override
    protected void customReset() {

    }
}
