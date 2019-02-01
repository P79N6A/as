package com.yaoguang.appcompanyshipper.phone.businessorder.forwarder.list;

import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewTreeObserver;

import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;
import com.mingle.sweetpick.DimEffect;
import com.mingle.sweetpick.RecyclerViewDelegate;
import com.mingle.sweetpick.SweetSheet;
import com.scwang.smartrefresh.layout.api.RefreshFooter;
import com.scwang.smartrefresh.layout.api.RefreshHeader;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.constant.RefreshState;
import com.scwang.smartrefresh.layout.listener.OnMultiPurposeListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;
import com.yaoguang.appcommon.publicsearch.PublicSearchInteractorImpl;
import com.yaoguang.appcommon.search.SearchFragment;
import com.yaoguang.appcompanyshipper.R;
import com.yaoguang.appcompanyshipper.databinding.FragmentBusinessOrderListBinding;
import com.yaoguang.appcompanyshipper.phone.businessorder.TipsConstant;
import com.yaoguang.appcompanyshipper.phone.businessorder.TipsUtil;
import com.yaoguang.appcompanyshipper.phone.businessorder.forwarder.list.adapter.BaseBusinessOrderListAdapter;
import com.yaoguang.appcompanyshipper.phone.businessorder.forwarder.list.event.BusinessOrderListEvent;
import com.yaoguang.greendao.entity.company.AppCompanyOrderCondition;
import com.yaoguang.lib.adapter.BaseLoadMoreRecyclerAdapter;
import com.yaoguang.lib.base.BaseApplication;
import com.yaoguang.lib.base.BaseFragmentListConditionDataBind;
import com.yaoguang.lib.base.interfaces.BasePresenterListCondition;
import com.yaoguang.lib.common.ObjectUtils;
import com.yaoguang.lib.common.SPUtils;
import com.yaoguang.lib.common.android.InputMethodUtil;
import com.yaoguang.lib.common.constants.Constants;
import com.yaoguang.lib.common.debounceclick.NoDoubleClickListener;
import com.yaoguang.lib.common.displaymetrics.DisplayMetricsUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * 货代 - 查看预订单管理列表
 * Created by zhongjh on 2017/7/28.
 */
public abstract class BaseBusinessOrderListFragment<T, B extends ViewDataBinding> extends BaseFragmentListConditionDataBind<T, AppCompanyOrderCondition, BaseBusinessOrderListAdapter, FragmentBusinessOrderListBinding>
        implements BaseBusinessOrderListContact.View<T, AppCompanyOrderCondition>, Toolbar.OnMenuItemClickListener {

    protected BaseBusinessOrderListContact.Presenter mPresenter;
    protected AppCompanyOrderCondition mCondition;
    protected String mClientName;// 公司名称
    int guide = 0;//因为初始刷新的原因,必须=1才能执行

    @Override
    public int getLayoutId() {
        return R.layout.fragment_business_order_list;
    }

    @Override
    public BasePresenterListCondition getPresenterrConditionList() {
        return mPresenter;
    }

    @Override
    public void init() {
        EventBus.getDefault().register(this);
        if (mDataBinding.toolbarCommon != null) {
            initToolbarNav(mToolbarCommonBinding.toolbar, "预订单管理", R.menu.shipschedule, BaseBusinessOrderListFragment.this);
        }
        mLayoutRecyclerviewBinding.refreshLayout.autoRefresh();

        // 判断物流还是货主，隐藏某个控件
        if (BaseApplication.getAppType().equals(Constants.APP_COMPANY)) {
            mDataBinding.llClient.setVisibility(View.GONE);
            mDataBinding.vClient.setVisibility(View.GONE);
            mDataBinding.llShipper.setVisibility(View.VISIBLE);
            mDataBinding.vShipper.setVisibility(View.VISIBLE);
        } else {
            mDataBinding.llClient.setVisibility(View.VISIBLE);
            mDataBinding.vClient.setVisibility(View.VISIBLE);
            mDataBinding.llShipper.setVisibility(View.GONE);
            mDataBinding.vShipper.setVisibility(View.GONE);
        }

        // 引导操作界面
        initTipsView();

        // 初始化状态栏
        initSweetSheets(mDataBinding.rlStatus.getId(), mDataBinding.flMain, "是否已导入", R.menu.sheet_business_order_status, (position, menuEntity) -> {
            mDataBinding.tvValueStatus.setText(menuEntity.title);
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
            mDataBinding.tvValueClient.setText("");
            mDataBinding.tvValueShipper.setText("");
            mDataBinding.tvValueDockOfLoading.setText("");
            mDataBinding.tvValueFinalDestination.setText("");
            mDataBinding.tvValuePortLoading.setText("");
            mDataBinding.tvValuePortDelivery.setText("");
        });

        // 是否已导入
        mDataBinding.rlStatus.setOnClickListener(new NoDoubleClickListener() {
            @Override
            public void onNoDoubleClick(View v) {
                showSweetSheets(mDataBinding.rlStatus.getId());
            }
        });

        // 托运人
        mDataBinding.rlShipper.setOnClickListener(v -> {
            SearchFragment fragment = SearchFragment.newInstance(PublicSearchInteractorImpl.TYPE_INFOCLIENTMANAGER);
            startForResult(fragment, ObjectUtils.parseInt(PublicSearchInteractorImpl.TYPE_INFOCLIENTMANAGER));
        });

        // 物流企业
        mDataBinding.rlClient.setOnClickListener(new NoDoubleClickListener() {
            @Override
            public void onNoDoubleClick(View v) {
                SearchFragment fragment = SearchFragment.newInstance(PublicSearchInteractorImpl.TYPE_CONTACT_COMPANY);
                startForResult(fragment, ObjectUtils.parseInt(PublicSearchInteractorImpl.TYPE_CONTACT_COMPANY));
            }
        });

        // 起运地
        mDataBinding.rlDockOfLoading.setOnClickListener(v -> {
            SearchFragment fragment = SearchFragment.newInstance(PublicSearchInteractorImpl.TYPE_DEPARTURE);
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
        mDataBinding.imgStatus.setOnClickListener(v -> mDataBinding.tvValueStatus.setText(""));
        mDataBinding.imgShipper.setOnClickListener(v -> mDataBinding.tvValueShipper.setText(""));
        mDataBinding.imgClient.setOnClickListener(new NoDoubleClickListener() {

            @Override
            public void onNoDoubleClick(View v) {
                mDataBinding.tvValueClient.setText("");
                mDataBinding.tvValueClient.setTag("");
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
                // 托运人
                case PublicSearchInteractorImpl.TYPE_INFOCLIENTMANAGER:
                    mDataBinding.tvValueShipper.setText(data.getString("name"));
                    break;
                // 物流企业
                case PublicSearchInteractorImpl.TYPE_CONTACT_COMPANY:
                    mDataBinding.tvValueClient.setText(data.getString("name"));
                    mDataBinding.tvValueClient.setTag(data.getString("id"));
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
                if (ObjectUtils.parseString(mDataBinding.tvValueStatus.getText().toString()).equals("")) {
                    mCondition.setStatus(null);
                } else if (mDataBinding.tvValueStatus.getText().toString().equals(getResources().getString(com.yaoguang.appcompanyshipper.R.string.not_imported))) {
                    mCondition.setStatus("0");
                } else if (mDataBinding.tvValueStatus.getText().toString().equals(getResources().getString(com.yaoguang.appcompanyshipper.R.string.imported))) {
                    mCondition.setStatus("1");
                } else if (mDataBinding.tvValueStatus.getText().toString().equals("已取消")) {
                    mCondition.setStatus("2");
                }
                mCondition.setShipper(mDataBinding.tvValueShipper.getText().toString());
                mCondition.setClientId(ObjectUtils.parseString(mDataBinding.tvValueClient.getTag()));
                mClientName = mDataBinding.tvValueClient.getText().toString();
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
            if (mCondition.getStatus() == null) {
                mDataBinding.tvValueStatus.setText("");
            } else if (mCondition.getStatus().equals("0")) {
                mDataBinding.tvValueStatus.setText(getResources().getString(R.string.not_imported));
            } else if (mCondition.getStatus().equals("1")) {
                mDataBinding.tvValueStatus.setText(getResources().getString(R.string.imported));
            }
            mDataBinding.tvValueShipper.setText(mCondition.getShipper());
            mDataBinding.tvValueClient.setTag(mCondition.getClientId());
            mDataBinding.tvValueClient.setText(mClientName);

            mDataBinding.tvValueDockOfLoading.setText(mCondition.getDockOfLoading());
            mDataBinding.tvValueFinalDestination.setText(mCondition.getFinalDestination());
            mDataBinding.tvValuePortDelivery.setText(mCondition.getPortDelivery());
            mDataBinding.tvValuePortLoading.setText(mCondition.getPortLoading());
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
        boolean isShow = SPUtils.getInstance().getBoolean(TipsConstant.TIPSCONSTANT + TipsConstant.BUSINESSORDERLISTFRAGMENTFORWARDER);
        // 处于有数据并且引导界面没有出现过的情况
        if (!isShow && mBaseLoadMoreRecyclerAdapter.getList().size() > 0) {
            mDataBinding.flMain.setOnTouchListener((v, event) -> true);
            mDataBinding.flMain.setTag("1");
            mLayoutRecyclerviewBinding.refreshLayout.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
                @Override
                public void onLoadMore(RefreshLayout refreshLayout) {

                }

                @Override
                public void onRefresh(RefreshLayout refreshLayout) {
                    loadMoreData();
                }


            });
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
                                    TipsUtil.startTipsBusinessOrderList(getActivity(), mDataBinding.flMain, mLayoutRecyclerviewBinding.rlView, guide, mBaseLoadMoreRecyclerAdapter, this, TipsConstant.BUSINESSORDERLISTFRAGMENTFORWARDER);
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
                    refreshData();
                }

                @Override
                public void onRefresh(RefreshLayout refreshLayout) {
                    loadMoreData();
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
