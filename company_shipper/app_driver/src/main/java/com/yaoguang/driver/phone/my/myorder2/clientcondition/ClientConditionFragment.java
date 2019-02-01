package com.yaoguang.driver.phone.my.myorder2.clientcondition;


import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import com.yaoguang.datasource.common.DataStatic;
import com.yaoguang.driver.R;
import com.yaoguang.driver.databinding.FragmentMyOrderClientConditionBinding;
import com.yaoguang.greendao.entity.driver.DriverEntrustCompany;
import com.yaoguang.lib.adapter.BaseLoadMoreRecyclerAdapter;
import com.yaoguang.lib.base.BaseFragmentListConditionDataBind;
import com.yaoguang.lib.base.interfaces.BasePresenterListCondition;
import com.yaoguang.lib.common.debounceclick.NoDoubleClickListener;

import java.util.HashMap;

/**
 * @author zhongjh
 * @Package com.yaoguang.driver.phone.my.myorder2.clientcondition
 * @Description: 委托人条件筛选 窗体
 * @date 2018/04/09
 */
public class ClientConditionFragment extends BaseFragmentListConditionDataBind<DriverEntrustCompany, String, ClientConditionAdapter, FragmentMyOrderClientConditionBinding> implements ClientConditionContract.View, Toolbar.OnMenuItemClickListener {

    ClientConditionContract.Presenter mPresenter = new ClientConditionPresenter(this);

    public static ClientConditionFragment newInstance(HashMap<String, String> companyIds) {
        ClientConditionFragment fragment = new ClientConditionFragment();
        Bundle args = new Bundle();
        args.putSerializable("CompanyIds", companyIds);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_my_order_client_condition;
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        if (item.getItemId() == R.id.action_sure) {
            Bundle bundle = new Bundle();
            bundle.putSerializable("CompanyIds", mBaseLoadMoreRecyclerAdapter.getmCompanyNameHas());
            setFragmentResult(RESULT_OK, bundle);
            pop();
        }
        return false;
    }

    @Override
    public BaseLoadMoreRecyclerAdapter getAdapter() {
        Bundle bundle = getArguments();
        HashMap<String, String> companyIdList = new HashMap<>();
        if (bundle != null) {
            companyIdList = (HashMap<String, String>) bundle.getSerializable("CompanyIds");
        }
        return new ClientConditionAdapter(ClientConditionFragment.this, companyIdList);
    }

    @Override
    public BasePresenterListCondition getPresenterrConditionList() {
        return mPresenter;
    }


    @Override
    public String getCondition(boolean isRegain) {
        return DataStatic.getInstance().getDriver().getId();
    }

    @Override
    public void setConditionView(String condition) {

    }

    @Override
    public void init() {
        if (mDataBinding.toolbarCommon != null) {
            initToolbarNav(mToolbarCommonBinding.toolbar, "选择委托人", R.menu.menu_order_data_type, this);
        }
//        //绑定数据
//        mViewBinding.setclientConditionCondition(mclientConditionCondition);
    }

    @Override
    public void initListener() {
        // 全部清空
        mDataBinding.tvClearAll.setOnClickListener(new NoDoubleClickListener() {
            @Override
            public void onNoDoubleClick(View v) {
                mBaseLoadMoreRecyclerAdapter.setmCompanyNameHas(new HashMap<>());
                // 刷新
                mBaseLoadMoreRecyclerAdapter.notifyDataSetChanged();
            }
        });
    }

}
