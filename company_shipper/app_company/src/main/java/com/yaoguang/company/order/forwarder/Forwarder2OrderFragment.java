//package com.yaoguang.company.order.forwarder;
//
//import android.os.Bundle;
//import android.view.View;
//import android.widget.TextView;
//
//import com.yaoguang.appcommon.phone.order.BaseOrderFragment;
//import com.yaoguang.appcommon.search.SearchFragment;
//import com.yaoguang.lib.adapter.BaseLoadMoreRecyclerAdapter;
//import com.yaoguang.lib.common.Constants;
//import com.yaoguang.lib.common.ObjectUtils;
//import com.yaoguang.company.R;
//import com.yaoguang.company.order.forwarder.adapter.ForwarderOrderAdapter;
//import com.yaoguang.greendao.entity.FreightBills;
//import com.yaoguang.greendao.entity.SysCondition;
//import com.yaoguang.greendao.entity.SysConditionWrapper;
//import com.yaoguang.shipper.order.OrderContact;
//import com.yaoguang.shipper.order.forwarderorder.ForwarderOrderInteractorImpl;
//import com.yaoguang.interactor.company.businessorder.DCSPublicSearchInteractorImpl;
//
//import java.util.List;
//
///**
// * 货代订单跟踪
// * Created by zhongjh on 2017/6/16.
// */
//public class ForwarderOrderFragment extends BaseOrderFragment<FreightBills> {
//
//    public static ForwarderOrderFragment newInstance() {
//        ForwarderOrderFragment forwarderOrderFragment = new ForwarderOrderFragment();
//        Bundle bundle = new Bundle();
//        bundle.putInt(BUNDLE_BILLTYPE, 0);
//        forwarderOrderFragment.setArguments(bundle);
//        return forwarderOrderFragment;
//    }
//
//    @Override
//    protected OrderContact.OrderInteractor getOrderInteractor() {
//        return new ForwarderOrderInteractorImpl();
//    }
//
//    @Override
//    protected BaseLoadMoreRecyclerAdapter getAdapter() {
//        return new ForwarderOrderAdapter();
//    }
//
//    @Override
//    protected void customInitView(InitialView initialView) {
//        initialView.viewHolder.rlMulitCheck.setVisibility(View.GONE);// 这个没有线所以不需要隐藏
//        initialView.viewHolder.llLoadingOrUnLoading.setVisibility(View.GONE);
//        initialView.viewHolder.vLoadingOrUnLoading.setVisibility(View.GONE);
//
//        initialView.viewHolder.llBusinessDate.setVisibility(View.GONE);
//        initialView.viewHolder.vBusinessDate.setVisibility(View.GONE);
//        initialView.viewHolder.llLoadDate.setVisibility(View.GONE);
//        initialView.viewHolder.vLoadDate.setVisibility(View.GONE);
//
//        ((TextView) initialView.viewHolder.toolbar.findViewById(R.id.toolbar_title)).setText("货代订单跟踪");
//        initialView.viewHolder.tvUser.setText("托运人");
//        initialView.viewHolder.tvValueUser.setHint(getResources().getString(R.string.unlimited));
//
//        //重新写清空事件
//        initialView.viewHolder.btnEmpty.setOnClickListener(v -> {
//            initialView.viewHolder.tvValueCount.setText("");
//            initialView.viewHolder.tvValueUser.setText("");
//            initialView.viewHolder.tvValueLoadingOrUnLoading.setText("");
//            initialView.viewHolder.tvValueOrderId.setText("");
//            initialView.viewHolder.tvValueOrderNo.setText("");
//            initialView.viewHolder.tvStartDateType.setText("");
//            initialView.viewHolder.tvEndDateType.setText("");
//            initialView.viewHolder.tvDateType.setText("");
//            initialView.viewHolder.tvStartBusinessDate.setText("");
//            initialView.viewHolder.tvEndBusinessDate.setText("");
//            initialView.viewHolder.tvStartLoadDate.setText("");
//            initialView.viewHolder.tvEndLoadDate.setText("");
//        });
//    }
//
//    @Override
//    protected void customInitListener(InitialView initialView) {
//        initialView.adapter.setOnItemClickListener((itemView, item, position) -> {
//            FreightBills freightBills = (FreightBills) item;
//            start(ForwarderDetailFragment.newInstance(freightBills.getId()),SINGLETOP);
//        });
//    }
//
//    @Override
//    protected void customGetCondition(List<SysCondition> sysCondition, InitialView initialView) {
//
//    }
//
//    @Override
//    protected void customSetConditionView(SysConditionWrapper mCondition, InitialView mInitialView) {
//
//    }
//
//    @Override
//    protected String getCodeType() {
//        return Constants.APP_COMPANY;
//    }
//
//    @Override
//    public void setRlUserOnClick() {
//        //托运人
//        SearchFragment fragment = SearchFragment.newInstance(Constants.APP_COMPANY, DCSPublicSearchInteractorImpl.TYPE_INFOCLIENTMANAGER);
//        startForResult(fragment, ObjectUtils.parseInt(DCSPublicSearchInteractorImpl.TYPE_INFOCLIENTMANAGER));
//    }
//
//
//
//}
