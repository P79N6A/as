package com.yaoguang.appcompanyshipper.phone.businessorder.trailer.templist;

import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;

import com.yaoguang.appcommon.publicsearch.PublicSearchInteractorImpl;
import com.yaoguang.appcommon.search.SearchFragment;
import com.yaoguang.appcompanyshipper.R;
import com.yaoguang.appcompanyshipper.databinding.FragmentBusinessOrderTrailerTempListBinding;
import com.yaoguang.appcompanyshipper.phone.businessorder.trailer.templist.adapter.BaseBusinessOrderTempListAdapter;
import com.yaoguang.greendao.entity.company.AppCompanyOrderCondition;
import com.yaoguang.lib.adapter.BaseLoadMoreRecyclerAdapter;
import com.yaoguang.lib.base.BaseFragmentListConditionDataBind;
import com.yaoguang.lib.base.interfaces.BasePresenterListCondition;
import com.yaoguang.lib.common.ObjectUtils;
import com.yaoguang.lib.common.android.InputMethodUtil;

/**
 * 查看预订单模版列表
 * Created by zhongjh on 2017/7/28.
 */
public abstract class BaseBusinessOrderTempListFragment<T,B extends ViewDataBinding>
        extends BaseFragmentListConditionDataBind<T,AppCompanyOrderCondition,BaseBusinessOrderTempListAdapter,FragmentBusinessOrderTrailerTempListBinding>
        implements BaseBusinessOrderTempListContact.View<T, AppCompanyOrderCondition>, Toolbar.OnMenuItemClickListener {

    protected BaseBusinessOrderTempListContact.Presenter mPresenter;
    protected AppCompanyOrderCondition mCondition;
    // 创建 ViewHolder的缓存共享池
    protected RecyclerView.RecycledViewPool mRecycledViewPool;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_business_order_trailer_temp_list;
    }

    @Override
    public void initDataBind(View view, LayoutInflater inflater) {
        super.initDataBind(view, inflater);
        mRecycledViewPool  = new RecyclerView.RecycledViewPool();
    }

    @Override
    protected void initView(View view, BaseLoadMoreRecyclerAdapter adapter) {
        super.initView(view, adapter);
        LinearLayoutManager layoutManager = (LinearLayoutManager) mLayoutRecyclerviewBinding.rlView.getLayoutManager();
        // 需要注意：要使用RecycledViewPool的话,如果使用的LayoutManager是LinearLayoutManager或其子类（如GridLayoutManager），需要手动开启这个特性
        layoutManager.setRecycleChildrenOnDetach(true);
        mLayoutRecyclerviewBinding.rlView.setRecycledViewPool(mRecycledViewPool);
    }

    @Override
    public void init() {
        initToolbarNav(mToolbarCommonBinding.toolbar, "预订单模版", R.menu.shipschedule, BaseBusinessOrderTempListFragment.this);
    }

    @Override
    public void initListener() {
        mDataBinding.cvTop.setOnTouchListener((v, event) -> true);
        mDataBinding.llTop.setOnTouchListener((v, event) -> {
            mDataBinding.llTop.setVisibility(View.GONE);
            return true;
        });
        mDataBinding.btnComit.setOnClickListener(v -> {
            InputMethodUtil.hideKeyboard(getActivity());
            mDataBinding.llTop.setVisibility(View.GONE);
            mLayoutRecyclerviewBinding.refreshLayout.autoRefresh();
        });
        mDataBinding.btnEmpty.setOnClickListener(v -> {
            mDataBinding.tvValueShipper.setText("");
            mDataBinding.tvValueDockOfLoading.setText("");
            mDataBinding.tvValueFinalDestination.setText("");
        });
        mDataBinding.rlShipper.setOnClickListener(v -> {
            SearchFragment fragment = SearchFragment.newInstance(PublicSearchInteractorImpl.TYPE_INFOCLIENTMANAGER);
            startForResult(fragment, ObjectUtils.parseInt(PublicSearchInteractorImpl.TYPE_INFOCLIENTMANAGER));
        });

        // 港口 - 起运港
        mDataBinding.rlDockOfLoading.setOnClickListener(v -> {
            SearchFragment fragment = SearchFragment.newInstance(PublicSearchInteractorImpl.TYPE_PORTOFSHIPMENT555);
            startForResult(fragment, ObjectUtils.parseInt(PublicSearchInteractorImpl.TYPE_PORTOFSHIPMENT555));
        });

        // 地区 - 起运地
        mDataBinding.rlFinalDestination.setOnClickListener(v -> {
            SearchFragment fragment = SearchFragment.newInstance(PublicSearchInteractorImpl.TYPE_DEPARTURE);
            startForResult(fragment, ObjectUtils.parseInt(PublicSearchInteractorImpl.TYPE_DEPARTURE));
        });
        mDataBinding.imgShipper.setOnClickListener(v -> mDataBinding.tvValueShipper.setText(""));
        mDataBinding.imgDockOfLoading.setOnClickListener(v -> mDataBinding.tvValueDockOfLoading.setText(""));
        mDataBinding.imgFinalDestination.setOnClickListener(v -> mDataBinding.tvValueFinalDestination.setText(""));
    }

    @Override
    public BasePresenterListCondition getPresenterrConditionList() {
        return mPresenter;
    }

    @Override
    public void onEnterAnimationEnd(Bundle savedInstanceState) {
        mPresenter.subscribe();
        mLayoutRecyclerviewBinding.refreshLayout.autoRefresh();
        super.onEnterAnimationEnd(savedInstanceState);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onFragmentResult(int requestCode, int resultCode, Bundle data) {
        super.onFragmentResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case PublicSearchInteractorImpl.TYPE_INFOCLIENTMANAGER:
                    mDataBinding.tvValueShipper.setText(data.getString("name"));
                    break;
                case PublicSearchInteractorImpl.TYPE_PORTOFSHIPMENT555:
                    mDataBinding.tvValueDockOfLoading.setText(data.getString("name"));
                    break;
                case PublicSearchInteractorImpl.TYPE_DEPARTURE:
                    mDataBinding.tvValueFinalDestination.setText(data.getString("name"));
                    break;
            }
        }
    }

    @Override
    public AppCompanyOrderCondition getCondition(boolean isRegain) {
        if (isRegain) {
            if (mCondition == null)
                mCondition = new AppCompanyOrderCondition();
            if (mDataBinding != null) {
                mCondition.setShipper(mDataBinding.tvValueShipper.getText().toString());
                mCondition.setPortLoading(mDataBinding.tvValueDockOfLoading.getText().toString());
                mCondition.setDockOfLoading(mDataBinding.tvValueFinalDestination.getText().toString());
            }
        }
        return mCondition;
    }

    @Override
    public void setConditionView(AppCompanyOrderCondition condition) {
        if (mCondition != null) {
            mDataBinding.tvValueShipper.setText(mCondition.getShipper());
            mDataBinding.tvValueDockOfLoading.setText(mCondition.getPortLoading());
            mDataBinding.tvValueFinalDestination.setText(mCondition.getDockOfLoading());
        }
    }

}
