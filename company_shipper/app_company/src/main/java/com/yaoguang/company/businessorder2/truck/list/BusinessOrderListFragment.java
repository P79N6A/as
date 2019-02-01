package com.yaoguang.company.businessorder2.truck.list;

import android.annotation.SuppressLint;

import com.yaoguang.company.R;
import com.yaoguang.company.businessorder2.common.list.BaseBusinessOrderListContact;
import com.yaoguang.company.businessorder2.common.list.BaseBusinessOrderListFragment;
import com.yaoguang.company.businessorder2.truck.businessmain.BusinessMainFragment;
import com.yaoguang.company.businessorder2.truck.list.adapter.BusinessOrderListAdapter;
import com.yaoguang.greendao.entity.common.TruckBills;
import com.yaoguang.greendao.entity.company.AppBusinessOrderListCondition;
import com.yaoguang.lib.adapter.BaseLoadMoreRecyclerAdapter;
import com.yaoguang.lib.base.interfaces.BasePresenterListCondition;

/**
 * Created by zhongjh on 2018/11/14.
 */
@SuppressLint("ValidFragment")
public class BusinessOrderListFragment extends BaseBusinessOrderListFragment<TruckBills, BusinessOrderListAdapter> {

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
            initToolbarNav(mToolbarCommonBinding.toolbar, "拖车工作单管理", R.menu.menu_business_list, BusinessOrderListFragment.this);
        }
        mPresenter.subscribe();
    }

    @Override
    public void initListener() {
        super.initListener();
        // 列表点击
        mBaseLoadMoreRecyclerAdapter.setOnItemClickListener((itemView, item, position) -> start(BusinessMainFragment.newInstance(((TruckBills) item).getId(), mBillType,"")));
    }

}
