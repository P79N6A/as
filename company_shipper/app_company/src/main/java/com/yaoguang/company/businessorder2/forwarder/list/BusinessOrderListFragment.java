package com.yaoguang.company.businessorder2.forwarder.list;

import android.annotation.SuppressLint;

import com.yaoguang.company.R;
import com.yaoguang.company.businessorder2.common.list.BaseBusinessOrderListContact;
import com.yaoguang.company.businessorder2.common.list.BaseBusinessOrderListFragment;
import com.yaoguang.company.businessorder2.forwarder.businessmain.BusinessMainFragment;
import com.yaoguang.company.businessorder2.forwarder.list.adapter.BusinessOrderListAdapter;
import com.yaoguang.greendao.entity.common.ViewForwardOrderPack.FreightBills;
import com.yaoguang.lib.adapter.BaseLoadMoreRecyclerAdapter;
import com.yaoguang.lib.base.interfaces.BasePresenterListCondition;

/**
 * 工作单管理
 * Created by zhongjh on 2018/10/24.
 */
@SuppressLint("ValidFragment")
public class BusinessOrderListFragment extends BaseBusinessOrderListFragment<FreightBills, BusinessOrderListAdapter> {

    private BaseBusinessOrderListContact.Presenter mPresenter;

    @SuppressLint("ValidFragment")
    public BusinessOrderListFragment(String billType) {
        super(billType);
    }

    /**
     * @param billType 0-货代，1-拖车
     */
    public static BusinessOrderListFragment newInstance(String billType) {
        return new BusinessOrderListFragment(billType);
    }

    @Override
    public BaseLoadMoreRecyclerAdapter getAdapter() {
        return new BusinessOrderListAdapter();
    }

    @Override
    public BasePresenterListCondition getPresenterrConditionList() {
        return mPresenter;
    }

    @Override
    public void init() {
        super.init();
        mPresenter = new BusinessOrderListPresenter(this, mBillType);
        if (mDataBinding.toolbarCommon != null) {
            initToolbarNav(mToolbarCommonBinding.toolbar, "货代工作单管理", R.menu.menu_business_list, BusinessOrderListFragment.this);
        }
        mPresenter.subscribe();
    }

    @Override
    public void initListener() {
        super.initListener();
        // 列表点击
        mBaseLoadMoreRecyclerAdapter.setOnItemClickListener((itemView, item, position) -> start(BusinessMainFragment.newInstance(((FreightBills) item).getId(), mBillType,"")));
    }


}
