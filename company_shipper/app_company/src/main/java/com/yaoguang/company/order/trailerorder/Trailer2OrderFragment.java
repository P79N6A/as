//package com.yaoguang.company.order.trailerorder;
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
//import com.yaoguang.company.order.trailerorder.adapter.TrailerOrderAdapter;
//import com.yaoguang.greendao.entity.SysCondition;
//import com.yaoguang.greendao.entity.SysConditionWrapper;
//import com.yaoguang.greendao.entity.TruckBills;
//import com.yaoguang.interactor.common.order.OrderContact;
//import com.yaoguang.shipper.order.trailerorder.TrailerOrderInteractorImpl;
//import com.yaoguang.interactor.company.businessorder.DCSPublicSearchInteractorImpl;
//
//import java.util.List;
//
///**
// * 拖车
// * Created by zhongjh on 2017/6/16.
// */
//public class TrailerOrderFragment extends BaseOrderFragment<TruckBills> {
//
//    public static TrailerOrderFragment newInstance() {
//        TrailerOrderFragment trailerOrderFragment = new TrailerOrderFragment();
//        Bundle bundle = new Bundle();
//        bundle.putInt(BUNDLE_BILLTYPE, 1);
//        trailerOrderFragment.setArguments(bundle);
//        return trailerOrderFragment;
//    }
//
//    @Override
//    protected OrderContact.OrderInteractor getOrderInteractor() {
//        return new TrailerOrderInteractorImpl();
//    }
//
//    @Override
//    protected BaseLoadMoreRecyclerAdapter getAdapter() {
//        return new TrailerOrderAdapter();
//    }
//
//    @Override
//    protected void customInitView(InitialView initialView) {
//        initialView.viewHolder.rlMulitCheck.setVisibility(View.GONE);// 这个没有线所以不需要隐藏
//
//        initialView.viewHolder.llBusinessDate.setVisibility(View.GONE);
//        initialView.viewHolder.vBusinessDate.setVisibility(View.GONE);
//        initialView.viewHolder.llLoadDate.setVisibility(View.GONE);
//        initialView.viewHolder.vLoadDate.setVisibility(View.GONE);
//
//        ((TextView) initialView.viewHolder.toolbar.findViewById(R.id.toolbar_title)).setText("拖车订单跟踪");
//        initialView.viewHolder.tvUser.setText("托运人");
//        initialView.viewHolder.tvValueUser.setHint(getResources().getString(R.string.unlimited));
//    }
//
//    @Override
//    protected void customInitListener(InitialView initialView) {
//        initialView.adapter.setOnItemClickListener((itemView, item, position) -> {
//            TruckBills truckBills = (TruckBills) item;
//            start(TrailerDetailFragment.newInstance(truckBills.getId()), SINGLETOP);
//        });
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
//}
