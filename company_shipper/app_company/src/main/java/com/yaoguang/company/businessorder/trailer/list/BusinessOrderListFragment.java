package com.yaoguang.company.businessorder.trailer.list;

import com.yaoguang.appcompanyshipper.databinding.FragmentBusinessOrderListBinding;
import com.yaoguang.appcompanyshipper.phone.businessorder.trailer.list.BaseBusinessOrderTrailerListFragment;
import com.yaoguang.company.businessorder.trailer.business.BusinessOrderFragment;
import com.yaoguang.company.businessorder.trailer.list.adapter.BusinessOrderListAdapter;
import com.yaoguang.greendao.entity.shipper.AppOwnerTruckOrder;
import com.yaoguang.lib.adapter.BaseLoadMoreRecyclerAdapter;


/**
 * 预订单列表
 * Created by zhongjh on 2018/9/4.
 */
public class BusinessOrderListFragment extends BaseBusinessOrderTrailerListFragment<AppOwnerTruckOrder,FragmentBusinessOrderListBinding> {

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
        mBaseLoadMoreRecyclerAdapter.setOnItemClickListener((itemView, item, position) -> start(BusinessOrderFragment.newInstance(((AppOwnerTruckOrder) item).getId())));

        // 添加下单
        mDataBinding.tvAdd.setOnClickListener(v -> start(BusinessOrderFragment.newInstance(null), SINGLETOP));
    }

    @Override
    public BaseLoadMoreRecyclerAdapter getAdapter() {
        return new BusinessOrderListAdapter(this);
    }



}
