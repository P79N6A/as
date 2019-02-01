package com.yaoguang.appcompanyshipper.phone.businessorder.forwarder.templist;

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
import com.yaoguang.appcompanyshipper.databinding.FragmentBusinessOrderTempListBinding;
import com.yaoguang.appcompanyshipper.phone.businessorder.forwarder.templist.adapter.BaseBusinessOrderTempListAdapter;
import com.yaoguang.greendao.entity.company.AppCompanyOrderCondition;
import com.yaoguang.lib.adapter.BaseLoadMoreRecyclerAdapter;
import com.yaoguang.lib.base.BaseApplication;
import com.yaoguang.lib.base.BaseFragmentListConditionDataBind;
import com.yaoguang.lib.base.interfaces.BasePresenter;
import com.yaoguang.lib.base.interfaces.BasePresenterListCondition;
import com.yaoguang.lib.common.ObjectUtils;
import com.yaoguang.lib.common.android.InputMethodUtil;
import com.yaoguang.lib.common.constants.Constants;
import com.yaoguang.lib.common.debounceclick.NoDoubleClickListener;

/**
 * 查看预订单模版列表
 * Created by zhongjh on 2017/7/28.
 */
public abstract class BaseBusinessOrderTempListFragment<T,B extends ViewDataBinding>
        extends BaseFragmentListConditionDataBind<T,AppCompanyOrderCondition,BaseBusinessOrderTempListAdapter,FragmentBusinessOrderTempListBinding>
        implements BaseBusinessOrderTempListContact.View<T, AppCompanyOrderCondition>, Toolbar.OnMenuItemClickListener {

    protected BaseBusinessOrderTempListContact.Presenter mPresenter;
    protected AppCompanyOrderCondition mCondition;
    protected String mClientName;// 公司名称

    // 其实RecycledViewPool的内部维护了一个Map，里面以不同的viewType为Key存储了各自对应的ViewHolder集合，所以这边设置了常量防止父适配器和子适配器的ViewType冲突
    public static final int PARENT_VIEW_TYPE = 0;
    public static final int CHILD_VIEW_TYPE = 1;

    // 创建 ViewHolder的缓存共享池
    protected RecyclerView.RecycledViewPool mRecycledViewPool;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_business_order_temp_list;
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

        // 判断类型显示相应的
        if (BaseApplication.getAppType().equals(Constants.APP_COMPANY)){
            mDataBinding.llShipper.setVisibility(View.VISIBLE);
            mDataBinding.llCompany.setVisibility(View.GONE);
        }else{
            mDataBinding.llShipper.setVisibility(View.GONE);
            mDataBinding.llCompany.setVisibility(View.VISIBLE);
        }
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
            mDataBinding.tvValueCompany.setText("");
            mDataBinding.tvValueShipper.setText("");
            mDataBinding.tvValueDockOfLoading.setText("");
            mDataBinding.tvValueFinalDestination.setText("");
            mDataBinding.tvValuePortLoading.setText("");
            mDataBinding.tvValuePortDelivery.setText("");
        });

        // 托运人
        mDataBinding.rlShipper.setOnClickListener(v -> {
            SearchFragment fragment = SearchFragment.newInstance(PublicSearchInteractorImpl.TYPE_INFOCLIENTMANAGER);
            startForResult(fragment, ObjectUtils.parseInt(PublicSearchInteractorImpl.TYPE_INFOCLIENTMANAGER));
        });

        // 物流企业
        mDataBinding.rlCompany.setOnClickListener(new NoDoubleClickListener() {
            @Override
            public void onNoDoubleClick(View v) {
                SearchFragment fragment = SearchFragment.newInstance(PublicSearchInteractorImpl.TYPE_CONTACT_COMPANY);
                startForResult(fragment, ObjectUtils.parseInt(PublicSearchInteractorImpl.TYPE_CONTACT_COMPANY));
            }
        });

        // 起运地
        mDataBinding.rlDockOfLoading.setOnClickListener(v -> {
            SearchFragment fragment = SearchFragment.newInstance( PublicSearchInteractorImpl.TYPE_DEPARTURE);
            startForResult(fragment, ObjectUtils.parseInt(PublicSearchInteractorImpl.TYPE_DEPARTURE));
        });

        // 目的地
        mDataBinding.rlFinalDestination.setOnClickListener(v -> {
            SearchFragment fragment = SearchFragment.newInstance(PublicSearchInteractorImpl.TYPE_DESTINATION);
            startForResult(fragment, ObjectUtils.parseInt(PublicSearchInteractorImpl.TYPE_DESTINATION));
        });

        // 起运港
        mDataBinding.rlPortLoading.setOnClickListener(new NoDoubleClickListener() {
            @Override
            public void onNoDoubleClick(View v) {
                SearchFragment fragment = SearchFragment.newInstance(PublicSearchInteractorImpl.TYPE_PORTOFSHIPMENT555);
                startForResult(fragment, ObjectUtils.parseInt(PublicSearchInteractorImpl.TYPE_PORTOFSHIPMENT555));
            }
        });

        // 目的港
        mDataBinding.rlPortDelivery.setOnClickListener(new NoDoubleClickListener() {
            @Override
            public void onNoDoubleClick(View v) {
                SearchFragment fragment = SearchFragment.newInstance(PublicSearchInteractorImpl.TYPE_PORTOFDESTINATION666);
                startForResult(fragment, ObjectUtils.parseInt(PublicSearchInteractorImpl.TYPE_PORTOFDESTINATION666));
            }
        });

        // 清除
        mDataBinding.imgShipper.setOnClickListener(v -> mDataBinding.tvValueShipper.setText(""));
        mDataBinding.imgCompany.setOnClickListener(new NoDoubleClickListener() {

            @Override
            public void onNoDoubleClick(View v) {
                mDataBinding.tvValueCompany.setText("");
                mDataBinding.tvValueCompany.setTag("");
            }
        });
        mDataBinding.imgDockOfLoading.setOnClickListener(v -> mDataBinding.tvValueDockOfLoading.setText(""));
        mDataBinding.imgFinalDestination.setOnClickListener(v -> mDataBinding.tvValueFinalDestination.setText(""));
        mDataBinding.imgPortLoading.setOnClickListener(new NoDoubleClickListener() {

            @Override
            public void onNoDoubleClick(View v) {
                mDataBinding.tvValuePortLoading.setText("");
            }
        });
        mDataBinding.imgPortDelivery.setOnClickListener(new NoDoubleClickListener() {
            @Override
            public void onNoDoubleClick(View v) {
                mDataBinding.tvValuePortDelivery.setText("");
            }
        });
    }

    @Override
    public BasePresenterListCondition getPresenterrConditionList() {
        return mPresenter;
    }

    @Override
    public BasePresenter getPresenter() {
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
                // 托运人
                case PublicSearchInteractorImpl.TYPE_INFOCLIENTMANAGER:
                    mDataBinding.tvValueShipper.setText(data.getString("name"));
                    break;
                // 物流企业
                case PublicSearchInteractorImpl.TYPE_CONTACT_COMPANY:
                    mDataBinding.tvValueCompany.setText(data.getString("name"));
                    mDataBinding.tvValueCompany.setTag(data.getString("id"));
                    break;
                // 起运地
                case PublicSearchInteractorImpl.TYPE_DEPARTURE:
                    mDataBinding.tvValueDockOfLoading.setText(data.getString("name"));
                    break;
                // 目的地
                case PublicSearchInteractorImpl.TYPE_DESTINATION:
                    mDataBinding.tvValueFinalDestination.setText(data.getString("name"));
                    break;
                // 起运港
                case PublicSearchInteractorImpl.TYPE_PORTOFSHIPMENT555:
                    mDataBinding.tvValuePortLoading.setText(data.getString("name"));
                    break;
                // 目的港
                case PublicSearchInteractorImpl.TYPE_PORTOFDESTINATION666:
                    mDataBinding.tvValuePortDelivery.setText(data.getString("name"));
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
                mCondition.setClientId(ObjectUtils.parseString(mDataBinding.tvValueCompany.getTag()));
                mClientName = mDataBinding.tvValueCompany.getText().toString();
                mCondition.setDockOfLoading(mDataBinding.tvValueDockOfLoading.getText().toString());
                mCondition.setFinalDestination(mDataBinding.tvValueFinalDestination.getText().toString());
                mCondition.setPortDelivery(mDataBinding.tvValuePortDelivery.getText().toString());
                mCondition.setPortLoading(mDataBinding.tvValuePortLoading.getText().toString());
            }
        }
        return mCondition;
    }

    @Override
    public void setConditionView(AppCompanyOrderCondition condition) {
        if (mCondition != null) {
            mDataBinding.tvValueShipper.setText(mCondition.getShipper());
            mDataBinding.tvValueCompany.setTag(mCondition.getClientId());
            mDataBinding.tvValueCompany.setText(mClientName);

            mDataBinding.tvValueDockOfLoading.setText(mCondition.getDockOfLoading());
            mDataBinding.tvValueFinalDestination.setText(mCondition.getFinalDestination());
            mDataBinding.tvValuePortDelivery.setText(mCondition.getPortDelivery());
            mDataBinding.tvValuePortLoading.setText(mCondition.getPortLoading());
        }
    }


}
