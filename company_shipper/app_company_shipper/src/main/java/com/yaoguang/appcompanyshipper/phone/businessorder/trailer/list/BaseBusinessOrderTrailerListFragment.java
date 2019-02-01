package com.yaoguang.appcompanyshipper.phone.businessorder.trailer.list;

import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewTreeObserver;

import com.scwang.smartrefresh.layout.api.RefreshFooter;
import com.scwang.smartrefresh.layout.api.RefreshHeader;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.constant.RefreshState;
import com.scwang.smartrefresh.layout.listener.OnMultiPurposeListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;
import com.yaoguang.appcommon.publicsearch.PublicSearchInteractorImpl;
import com.yaoguang.appcommon.search.SearchFragment;
import com.yaoguang.appcompanyshipper.R;
import com.yaoguang.appcompanyshipper.databinding.FragmentBusinessOrderTrailerListBinding;
import com.yaoguang.appcompanyshipper.phone.businessorder.TipsConstant;
import com.yaoguang.appcompanyshipper.phone.businessorder.TipsUtil;
import com.yaoguang.appcompanyshipper.phone.businessorder.trailer.list.adapter.BaseBusinessOrderListAdapter;
import com.yaoguang.appcompanyshipper.phone.businessorder.forwarder.list.event.BusinessOrderListEvent;
import com.yaoguang.greendao.entity.company.AppCompanyOrderCondition;
import com.yaoguang.lib.base.BaseApplication;
import com.yaoguang.lib.base.BaseFragmentListConditionDataBind;
import com.yaoguang.lib.base.interfaces.BasePresenterListCondition;
import com.yaoguang.lib.common.ObjectUtils;
import com.yaoguang.lib.common.SPUtils;
import com.yaoguang.lib.common.android.InputMethodUtil;
import com.yaoguang.lib.common.constants.Constants;
import com.yaoguang.lib.common.debounceclick.NoDoubleClickListener;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * 查看预订单管理列表(拖车)
 * Created by zhongjh on 2017/7/28.
 */
public abstract class BaseBusinessOrderTrailerListFragment<T, B extends ViewDataBinding> extends BaseFragmentListConditionDataBind<T, AppCompanyOrderCondition, BaseBusinessOrderListAdapter, FragmentBusinessOrderTrailerListBinding>
        implements BaseBusinessOrderTrailerListContact.View<T, AppCompanyOrderCondition>, Toolbar.OnMenuItemClickListener {

    protected BaseBusinessOrderTrailerListContact.Presenter mPresenter;
    protected AppCompanyOrderCondition mCondition;
    protected String mClientName;// 公司名称
    int guide = 0;//因为初始刷新的原因,必须=1才能执行

    @Override
    public int getLayoutId() {
        return R.layout.fragment_business_order_trailer_list;
    }


    @Override
    public BasePresenterListCondition getPresenterrConditionList() {
        return mPresenter;
    }


    @Override
    public void init() {
        EventBus.getDefault().register(this);
        if (mDataBinding.toolbarCommon != null) {
            initToolbarNav(mToolbarCommonBinding.toolbar, "预订单管理", R.menu.shipschedule, BaseBusinessOrderTrailerListFragment.this);
        }
        mLayoutRecyclerviewBinding.refreshLayout.autoRefresh();

        // 判断物流还是货主，隐藏某个控件
        if (BaseApplication.getAppType().equals(Constants.APP_COMPANY)) {
            mDataBinding.llCompany.setVisibility(View.GONE);
            mDataBinding.llShipper.setVisibility(View.VISIBLE);
        } else {
            mDataBinding.llCompany.setVisibility(View.VISIBLE);
            mDataBinding.llShipper.setVisibility(View.GONE);
        }

        // 引导操作界面
        initTipsView();

        // 初始化状态栏
        initSweetSheets(mDataBinding.rlStatus.getId(), mDataBinding.flMain, "是否已导入", R.menu.sheet_business_order_status, (position, menuEntity) -> {
            mDataBinding.tvValueStatus.setText(menuEntity.title);
            return true;
        });

        // 初始化装卸
        initSweetSheets(mDataBinding.rlLoadingAndUnloading.getId(), mDataBinding.flMain, "装/卸", R.menu.sheet_loading_unloading, (position, menuEntity) -> {
            mDataBinding.tvValueLoadingAndUnloading.setText(menuEntity.title);
            return true;
        });
    }

    @Override
    public void initListener() {
        // 退出事件
        mToolbarCommonBinding.imgReturn.setOnClickListener(new NoDoubleClickListener() {
            @Override
            public void onNoDoubleClick(View v) {
                if (!isTipsView())
                    pop();
            }
        });

        setStartEnable(true);

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
            mDataBinding.tvValueStatus.setText("");
            mDataBinding.tvValueLoadingAndUnloading.setText("");
            mDataBinding.tvValueShipper.setText("");
            mDataBinding.tvValueCompany.setText("");
            mDataBinding.tvValueDockOfLoading.setText("");
            mDataBinding.tvValueFinalDestination.setText("");
        });

        //状态
        mDataBinding.rlStatus.setOnClickListener(v -> showSweetSheets(mDataBinding.rlStatus.getId()));
        //装卸
        mDataBinding.rlLoadingAndUnloading.setOnClickListener(v -> {
            showSweetSheets(mDataBinding.rlLoadingAndUnloading.getId());
        });
        //托运人
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

        // 清除
        mDataBinding.imgStatus.setOnClickListener(v -> mDataBinding.tvValueStatus.setText(""));
        mDataBinding.imgLoadingAndUnloading.setOnClickListener(v -> mDataBinding.tvValueLoadingAndUnloading.setText(""));
        mDataBinding.imgShipper.setOnClickListener(v -> mDataBinding.tvValueShipper.setText(""));
        mDataBinding.imgDockOfLoading.setOnClickListener(v -> mDataBinding.tvValueDockOfLoading.setText(""));
        mDataBinding.imgFinalDestination.setOnClickListener(v -> mDataBinding.tvValueFinalDestination.setText(""));
    }

    @Override
    public void onEnterAnimationEnd(Bundle savedInstanceState) {
        mPresenter.subscribe();
        super.onEnterAnimationEnd(savedInstanceState);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public boolean onBackPressedSupport() {
        return isTipsView() || super.onBackPressedSupport();
    }

    @Override
    public void onFragmentResult(int requestCode, int resultCode, Bundle data) {
        super.onFragmentResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case PublicSearchInteractorImpl.TYPE_INFOCLIENTMANAGER:
                    //托运人
                    mDataBinding.tvValueShipper.setText(data.getString("name"));
                    break;
                // 物流企业
                case PublicSearchInteractorImpl.TYPE_CONTACT_COMPANY:
                    mDataBinding.tvValueCompany.setText(data.getString("name"));
                    mDataBinding.tvValueCompany.setTag(data.getString("id"));
                    break;
                case PublicSearchInteractorImpl.TYPE_PORTOFSHIPMENT555:
                    //港口
                    mDataBinding.tvValueDockOfLoading.setText(data.getString("name"));
                    break;
                case PublicSearchInteractorImpl.TYPE_DEPARTURE:
                    //地区
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
                //是否导入
                if (ObjectUtils.parseString(mDataBinding.tvValueStatus.getText().toString()).equals("")) {
                    mCondition.setStatus(null);
                } else if (mDataBinding.tvValueStatus.getText().toString().equals(getResources().getString(R.string.not_imported))) {
                    mCondition.setStatus("0");
                } else if (mDataBinding.tvValueStatus.getText().toString().equals(getResources().getString(R.string.imported))) {
                    mCondition.setStatus("1");
                }
                //是否装卸
                if (ObjectUtils.parseString(mDataBinding.tvValueLoadingAndUnloading.getText().toString()).equals("")) {
                    mCondition.setOtherService(null);
                } else if (mDataBinding.tvValueLoadingAndUnloading.getText().toString().equals(getResources().getString(R.string.loading_goods))) {
                    mCondition.setOtherService("0");
                } else if (mDataBinding.tvValueLoadingAndUnloading.getText().toString().equals(getResources().getString(R.string.unloading_goods))) {
                    mCondition.setOtherService("1");
                }
                mCondition.setShipper(mDataBinding.tvValueShipper.getText().toString());
                mCondition.setClientId(ObjectUtils.parseString(mDataBinding.tvValueCompany.getTag()));
                mClientName = mDataBinding.tvValueCompany.getText().toString();
                mCondition.setPortLoading(mDataBinding.tvValueDockOfLoading.getText().toString());
                mCondition.setDockOfLoading(mDataBinding.tvValueFinalDestination.getText().toString());
            }
        }
        return mCondition;
    }

    @Override
    public void setConditionView(AppCompanyOrderCondition condition) {
        if (mCondition != null) {
            //是否导入
            if (mCondition.getStatus() == null) {
                mDataBinding.tvValueStatus.setText("");
            } else if (mCondition.getStatus().equals("0")) {
                mDataBinding.tvValueStatus.setText(getResources().getString(R.string.not_imported));
            } else if (mCondition.getStatus().equals("1")) {
                mDataBinding.tvValueStatus.setText(getResources().getString(R.string.imported));
            }

            //是否装卸
            if (mCondition.getOtherService() == null) {
                mDataBinding.tvValueLoadingAndUnloading.setText("");
            } else if (mCondition.getOtherService().equals("0")) {
                mDataBinding.tvValueLoadingAndUnloading.setText(getResources().getString(R.string.loading_goods));
            } else if (mCondition.getOtherService().equals("1")) {
                mDataBinding.tvValueLoadingAndUnloading.setText(getResources().getString(R.string.unloading_goods));
            }

            // 托运人
            mDataBinding.tvValueShipper.setText(mCondition.getShipper());

            // 物流企业
            mDataBinding.tvValueCompany.setText(mClientName);

            // 港口
            mDataBinding.tvValueDockOfLoading.setText(mCondition.getPortLoading());

            // 地区
            mDataBinding.tvValueFinalDestination.setText(mCondition.getDockOfLoading());
        }
    }

    //如果接收到了刷新则刷新表格
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(BusinessOrderListEvent businessOrderListEvent) {
        if (businessOrderListEvent.isRefresh()) {
            mLayoutRecyclerviewBinding.refreshLayout.autoRefresh();
        }
    }

    /**
     * 初始化引导界面事件
     */
    private void initTipsView() {
        // 引导操作界面
        boolean isShow = SPUtils.getInstance().getBoolean(TipsConstant.TIPSCONSTANT + TipsConstant.BUSINESSORDERLISTFRAGMENTTRAILER);
        // 处于有数据并且引导界面没有出现过的情况
        if (!isShow && mBaseLoadMoreRecyclerAdapter.getList().size() > 0) {
            mDataBinding.flMain.setOnTouchListener((v, event) -> true);
            mDataBinding.flMain.setTag("1");
            mLayoutRecyclerviewBinding.refreshLayout.setOnMultiPurposeListener(new OnMultiPurposeListener() {
                @Override
                public void onHeaderPulling(RefreshHeader header, float percent, int offset, int headerHeight, int extendHeight) {

                }

                @Override
                public void onHeaderReleased(RefreshHeader header, int headerHeight, int extendHeight) {

                }

                @Override
                public void onHeaderReleasing(RefreshHeader header, float percent, int offset, int headerHeight, int extendHeight) {

                }

                @Override
                public void onHeaderStartAnimator(RefreshHeader header, int headerHeight, int extendHeight) {

                }

                @Override
                public void onHeaderFinish(RefreshHeader header, boolean success) {
                    if (success)
                        if (mLayoutRecyclerviewBinding.rlView != null && mLayoutRecyclerviewBinding.rlView.getViewTreeObserver() != null)
                            mLayoutRecyclerviewBinding.rlView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                                @Override
                                public void onGlobalLayout() {
                                    guide++;
                                    TipsUtil.startTipsBusinessOrderList(getActivity(), mDataBinding.flMain, mLayoutRecyclerviewBinding.rlView, guide, mBaseLoadMoreRecyclerAdapter, this, TipsConstant.BUSINESSORDERLISTFRAGMENTTRAILER);
                                }
                            });
                }

                @Override
                public void onFooterPulling(RefreshFooter footer, float percent, int offset, int footerHeight, int extendHeight) {

                }

                @Override
                public void onFooterReleased(RefreshFooter footer, int footerHeight, int extendHeight) {

                }

                @Override
                public void onFooterReleasing(RefreshFooter footer, float percent, int offset, int footerHeight, int extendHeight) {

                }

                @Override
                public void onFooterStartAnimator(RefreshFooter footer, int footerHeight, int extendHeight) {

                }

                @Override
                public void onFooterFinish(RefreshFooter footer, boolean success) {

                }

                @Override
                public void onLoadMore(RefreshLayout refreshLayout) {
                    loadMoreData();
                }

                @Override
                public void onRefresh(RefreshLayout refreshLayout) {
                    refreshData();
                }

                @Override
                public void onStateChanged(RefreshLayout refreshLayout, RefreshState oldState, RefreshState newState) {

                }
            });
        } else {
            mDataBinding.flMain.setOnTouchListener((v, event) -> false);
            mDataBinding.flMain.setTag("0");
            mLayoutRecyclerviewBinding.refreshLayout.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
                @Override
                public void onLoadMore(RefreshLayout refreshLayout) {
                    loadMoreData();
                }

                @Override
                public void onRefresh(RefreshLayout refreshLayout) {
                    refreshData();
                }
            });
        }
    }

    /**
     * 判断是否处于引导模式，如果是引导模式，那么返回事件都禁止
     *
     * @return true 则处于引导模式，否则相反
     */
    private boolean isTipsView() {
        if (mDataBinding.flMain.getTag().equals("1")) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void setStartEnable(boolean isEnable) {
        if (isEnable) {
            mBaseLoadMoreRecyclerAdapter.setOnRecyclerViewItemRLClickListener((itemView, item, position) -> {
                //进行添加进模版
                mPresenter.addTemplate(item);
            });
        } else {
            mBaseLoadMoreRecyclerAdapter.setOnRecyclerViewItemRLClickListener(null);
        }
    }

}
