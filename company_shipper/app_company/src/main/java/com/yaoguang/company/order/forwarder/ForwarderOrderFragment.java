package com.yaoguang.company.order.forwarder;

import android.os.Bundle;
import android.view.View;

import com.yaoguang.appcommon.databinding.FragmentOrderBinding;
import com.yaoguang.appcommon.phone.order.BaseOrderFragment;
import com.yaoguang.appcommon.phone.order.OrderContract;
import com.yaoguang.appcommon.search.SearchFragment;
import com.yaoguang.greendao.entity.AppPublicInfoWrapper;
import com.yaoguang.lib.adapter.BaseLoadMoreRecyclerAdapter;
import com.yaoguang.lib.common.ObjectUtils;
import com.yaoguang.company.R;
import com.yaoguang.company.order.forwarder.adapter.ForwarderOrderAdapter;
import com.yaoguang.greendao.entity.common.ViewForwardOrderPack.FreightBills;
import com.yaoguang.greendao.entity.SysCondition;
import com.yaoguang.greendao.entity.SysConditionWrapper;
import com.yaoguang.appcommon.publicsearch.PublicSearchInteractorImpl;

import java.util.List;


/**
 * 货代订单跟踪
 * Created by zhongjh on 2018/1/8.
 */
public class ForwarderOrderFragment extends BaseOrderFragment<FreightBills, ForwarderOrderAdapter, FragmentOrderBinding> {

    public static ForwarderOrderFragment newInstance() {
        ForwarderOrderFragment forwarderOrderFragment = new ForwarderOrderFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(BUNDLE_BILLTYPE, 0);
        forwarderOrderFragment.setArguments(bundle);
        return forwarderOrderFragment;
    }


    @Override
    public void setRlUserOnClick() {
        //托运人
        SearchFragment fragment = SearchFragment.newInstance( PublicSearchInteractorImpl.TYPE_INFOCLIENTMANAGER);
        startForResult(fragment, ObjectUtils.parseInt(PublicSearchInteractorImpl.TYPE_INFOCLIENTMANAGER));
    }

    @Override
    public void setContactCompany(AppPublicInfoWrapper appPublicInfoWrapper) {
        // 目前只有货主用到
    }


    @Override
    public OrderContract.Presenter newPresenter() {
        return new ForwarderOrderPresenter(ForwarderOrderFragment.this, mBillType);
    }


    @Override
    public BaseLoadMoreRecyclerAdapter getAdapter() {
        return new ForwarderOrderAdapter();
    }

    @Override
    protected void customInitView() {
        mDataBinding.rlMulitCheck.setVisibility(View.GONE);// 这个没有线所以不需要隐藏
        mDataBinding.llLoadingOrUnLoading.setVisibility(View.GONE);
        mDataBinding.vLoadingOrUnLoading.setVisibility(View.GONE);

        mDataBinding.llBusinessDate.setVisibility(View.GONE);
        mDataBinding.vBusinessDate.setVisibility(View.GONE);
        mDataBinding.llLoadDate.setVisibility(View.GONE);
        mDataBinding.vLoadDate.setVisibility(View.GONE);

        mToolbarCommonBinding.toolbarTitle.setText("货代订单跟踪");
        mDataBinding.tvUser.setText("托运人");
        mDataBinding.tvValueUser.setHint(getResources().getString(R.string.unlimited));

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
    protected void customInitListener() {
        mBaseLoadMoreRecyclerAdapter.setOnItemClickListener((itemView, item, position) -> {
            FreightBills freightBills = (FreightBills) item;
            start(ForwarderDetailFragment.newInstance(freightBills.getId()), SINGLETOP);
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
