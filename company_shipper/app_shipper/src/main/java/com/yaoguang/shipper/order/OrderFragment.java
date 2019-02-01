package com.yaoguang.shipper.order;

import android.os.Bundle;
import android.view.View;

import com.yaoguang.appcommon.databinding.FragmentOrderBinding;
import com.yaoguang.appcommon.phone.order.BaseOrderFragment;
import com.yaoguang.appcommon.phone.order.OrderContract;
import com.yaoguang.appcommon.search.SearchFragment;
import com.yaoguang.greendao.entity.AppPublicInfoWrapper;
import com.yaoguang.lib.common.constants.Constants;
import com.yaoguang.lib.common.ObjectUtils;
import com.yaoguang.greendao.entity.AppOrderWrapper;
import com.yaoguang.greendao.entity.SysCondition;
import com.yaoguang.greendao.entity.SysConditionWrapper;
import com.yaoguang.appcommon.publicsearch.PublicSearchInteractorImpl;
import com.yaoguang.shipper.R;
import com.yaoguang.shipper.order.adapter.OrderAdapter;
import com.yaoguang.shipper.order.detail.forwarder.ForwarderDetailFragment;
import com.yaoguang.shipper.order.detail.trailerorder.TrailerDetailFragment;

import java.util.List;

/**
 * 订单跟踪
 * Created by zhongjh on 2017/6/16.
 */
public class OrderFragment extends BaseOrderFragment<AppOrderWrapper, OrderAdapter, FragmentOrderBinding> {

    String mCompanyName;// 默认第一个关注的公司
    String mCompanyId;  // 默认第一个关注的公司id

    public static OrderFragment newInstance() {
        OrderFragment orderFragment = new OrderFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(BUNDLE_BILLTYPE, 1);
        orderFragment.setArguments(bundle);
        return orderFragment;
    }

    @Override
    public OrderAdapter getAdapter() {
        return new OrderAdapter(OrderFragment.this);
    }

    @Override
    public OrderContract.Presenter newPresenter() {
        return new OrderPresenter(OrderFragment.this, mBillType);
    }

    @Override
    protected void customInitView() {
        mDataBinding.rlMulitCheck.setVisibility(View.VISIBLE);
        mToolbarCommonBinding.toolbarTitle.setText("订单跟踪");
        mDataBinding.tvUser.setText("公司名称");
        mDataBinding.tvValueUser.setHint(getResources().getString(R.string.unlimited));

        //默认获取第一个
        mPresenter.analysisContactCompany();

        mDataBinding.llLoadingOrUnLoading.setVisibility(View.GONE);
        mDataBinding.vLoadingOrUnLoading.setVisibility(View.GONE);
        mDataBinding.rlDateType.setVisibility(View.GONE);
        mDataBinding.vDateType.setVisibility(View.GONE);

        // 隐藏业务员
        mDataBinding.llEmployeeId.setVisibility(View.GONE);
        mDataBinding.vEmployeeId.setVisibility(View.GONE);

    }

    @Override
    protected void customInitListener() {
        mBaseLoadMoreRecyclerAdapter.setOnItemClickListener((itemView, item, position) -> {
            AppOrderWrapper appOrderWrapper = (AppOrderWrapper) item;
            //判断类型打开
            if (appOrderWrapper.getType() == 0) {
                start(ForwarderDetailFragment.newInstance(appOrderWrapper.getId(), appOrderWrapper.getClientId()));
            } else {
                start(TrailerDetailFragment.newInstance(appOrderWrapper.getId(), appOrderWrapper.getClientId()));
            }
        });
    }

    @Override
    protected void customGetCondition(List<SysCondition> sysCondition) {
        //类型
        int type = 0;
        if (mDataBinding.cbForwarder.isChecked() && mDataBinding.cbTrailer.isChecked()) {
            type = 2;
        } else if (mDataBinding.cbForwarder.isChecked() && !mDataBinding.cbTrailer.isChecked()) {
            type = 0;
        } else if (mDataBinding.cbTrailer.isChecked() && !mDataBinding.cbForwarder.isChecked()) {
            type = 1;
        }
        SysCondition tvValueOrderId = new SysCondition();
        tvValueOrderId.setConditionType("type");
        tvValueOrderId.setInputValue(ObjectUtils.parseString(type));
        sysCondition.add(tvValueOrderId);

        //公司名称
        SysCondition tvValueUser = new SysCondition();
        tvValueUser.setConditionType("companyName");
        tvValueUser.setInputValue(ObjectUtils.parseString(mDataBinding.tvValueUser.getTag()));
        tvValueUser.setInputValue2(ObjectUtils.parseString(mDataBinding.tvValueUser.getText()));
        sysCondition.add(tvValueUser);
    }

    @Override
    protected void customSetConditionView(SysConditionWrapper mCondition) {

        for (SysCondition sysCondition : mCondition.getsysConditions()) {
            switch (sysCondition.getConditionType()) {
                //类型
                case "type":
                    if (sysCondition.getInputValue().equals("0")) {
                        mDataBinding.cbForwarder.setChecked(true);
                    } else if (sysCondition.getInputValue().equals("1")) {
                        mDataBinding.cbTrailer.setChecked(true);
                    } else if (sysCondition.getInputValue().equals("2")) {
                        mDataBinding.cbForwarder.setChecked(true);
                        mDataBinding.cbTrailer.setChecked(true);
                    }
                    break;
                //公司名称
                case "companyName":
                    mDataBinding.tvValueUser.setText(sysCondition.getInputValue2());
                    mDataBinding.tvValueUser.setTag(sysCondition.getInputValue());
                    break;
            }
        }
    }

    @Override
    protected void customReset() {
        mDataBinding.tvValueUser.setText(mCompanyName);
        mDataBinding.tvValueUser.setTag(mCompanyId);
    }

    @Override
    public void setRlUserOnClick() {
        //托运人
        SearchFragment fragment = SearchFragment.newInstance(PublicSearchInteractorImpl.TYPE_CONTACT_COMPANY);
        startForResult(fragment, ObjectUtils.parseInt(PublicSearchInteractorImpl.TYPE_CONTACT_COMPANY));
    }

    @Override
    public void setContactCompany(AppPublicInfoWrapper appPublicInfoWrapper) {
        mCompanyName = appPublicInfoWrapper.getFullName();
        mCompanyId = appPublicInfoWrapper.getId();
        mDataBinding.tvValueUser.setText(appPublicInfoWrapper.getFullName());
        mDataBinding.tvValueUser.setTag(appPublicInfoWrapper.getId());
        mLayoutRecyclerviewBinding.refreshLayout.autoRefresh();
    }

}
