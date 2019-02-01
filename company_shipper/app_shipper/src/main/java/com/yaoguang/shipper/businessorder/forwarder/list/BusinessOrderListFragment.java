package com.yaoguang.shipper.businessorder.forwarder.list;

import com.yaoguang.appcompanyshipper.databinding.FragmentBusinessOrderListBinding;
import com.yaoguang.appcompanyshipper.phone.businessorder.forwarder.list.BaseBusinessOrderListFragment;
import com.yaoguang.greendao.entity.company.AppCompanyOrderCondition;
import com.yaoguang.greendao.entity.shipper.AppOwnerForwardOrder;
import com.yaoguang.lib.adapter.BaseLoadMoreRecyclerAdapter;
import com.yaoguang.lib.common.ObjectUtils;
import com.yaoguang.shipper.R;
import com.yaoguang.shipper.businessorder.forwarder.business.BusinessOrderFragment;
import com.yaoguang.shipper.businessorder.forwarder.list.adapter.BusinessOrderListAdapter;

import java.util.List;

/**
 * 预订单列表
 * Created by zhongjh on 2018/9/4.
 */
public class BusinessOrderListFragment extends BaseBusinessOrderListFragment<AppOwnerForwardOrder,FragmentBusinessOrderListBinding> {

    public static BusinessOrderListFragment newInstance() {
        return new BusinessOrderListFragment();
    }

    @Override
    public void init() {
        super.init();
        mPresenter = new BusinessOrderListPresenter(this);
    }

    @Override
    public void initListener() {
        super.initListener();

        // 点击事件
        mBaseLoadMoreRecyclerAdapter.setOnItemClickListener((itemView, item, position) -> start(BusinessOrderFragment.newInstance(((AppOwnerForwardOrder) item).getId())));

        // 添加下单
        mDataBinding.tvAdd.setOnClickListener(v -> start(BusinessOrderFragment.newInstance(null), SINGLETOP));
    }

    @Override
    public BaseLoadMoreRecyclerAdapter getAdapter() {
        return new BusinessOrderListAdapter();
    }



}
