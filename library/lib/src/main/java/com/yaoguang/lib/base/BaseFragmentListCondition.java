package com.yaoguang.lib.base;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;
import com.yaoguang.lib.R;
import com.yaoguang.lib.adapter.BaseLoadMoreRecyclerAdapter;
import com.yaoguang.lib.appcommon.utils.RecyclerViewUtils;
import com.yaoguang.lib.appcommon.widget.head.Pull2Header;
import com.yaoguang.lib.base.interfaces.BaseListConditionView;
import com.yaoguang.lib.base.interfaces.BasePresenter;
import com.yaoguang.lib.base.interfaces.BasePresenterListCondition;
import com.yaoguang.lib.common.android.InputMethodUtil;
import com.yaoguang.lib.common.debounceclick.NoDoubleClickListener;

import java.util.List;

import ezy.ui.layout.LoadingLayout;

/**
 * 这是一个表格+条件框的fragment
 * Created by zhongjh on 2017/7/19.
 */
public abstract class BaseFragmentListCondition<T, C, A extends BaseLoadMoreRecyclerAdapter> extends BaseFragment2 implements Toolbar.OnMenuItemClickListener, BaseListConditionView<T, C> {

    RecyclerView mRLView; // 列表
    LoadingLayout mLoading;
    public A mBaseLoadMoreRecyclerAdapter; // 适配器
    protected RefreshLayout mRefreshLayout; // 刷新控件
    View mLLTop;// 顶部view
    View mCVTop;// 同样是顶部view,但是是mLlTop的内部view,控制点击事件的
    Button btnComit;
    Button btnEmpty;
    protected boolean mIsInitialLoad = true; // 是否初次加载

    protected boolean mHasFooter;

    // 回调接口
    protected OnRecyclerViewCallback mOnRecyclerViewCallback;

    protected interface OnRecyclerViewCallback<T> {
        // 如果返回true，则后面的就不执行了
        boolean setAdapter(List<T> list, boolean isHas);
    }

    /**
     * 当前列表的适配器
     */
    public abstract BaseLoadMoreRecyclerAdapter getAdapter();

    /**
     * 注入 列表presenter,执行列表通用操作
     */
    public abstract BasePresenterListCondition getPresenterrConditionList();

    @Override
    public BasePresenter getPresenter() {
        return getPresenterrConditionList();
    }

    /**
     * 获取查询条件
     *
     * @param isRegain 是否重新获取查询条件
     * @return 查询对象, 不能为null
     */
    public abstract C getCondition(boolean isRegain);

    /**
     * 如果使用databind绑定值，就不需要管这个方法，否则需要实现这个方法
     *
     * @param condition 条件实体类
     */
    public abstract void setConditionView(C condition);

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(getLayoutId(), container, false);
        mLayoutInflater = inflater;
        initDataBind(view, inflater);
        initView(view, getAdapter());
        initRecyclerviewListener();
        init();
        initListener();
        return view;
    }

    /**
     * 返回事件
     *
     * @return
     */
    @Override
    public boolean onBackPressedSupport() {
        if (super.onBackPressedSupport())
            return true;
        if (mLLTop != null && mLLTop.getVisibility() == View.VISIBLE) {
            mLLTop.setVisibility(View.GONE);
            return true;
        }
        return false;
    }

    /**
     * 初始化控件
     *
     * @param view    当前view
     * @param adapter 适配器
     */
    protected void initView(View view, BaseLoadMoreRecyclerAdapter adapter) {
        // 初始化控件
        mRLView = view.findViewById(R.id.rlView);
        mLoading = view.findViewById(R.id.loading);
        mRefreshLayout = view.findViewById(R.id.refreshLayout);
        mLLTop = view.findViewById(R.id.llTop);
        mCVTop = view.findViewById(R.id.cvTop);
        btnComit = view.findViewById(R.id.btnComit);
        btnEmpty = view.findViewById(R.id.btnEmpty);
        mBaseLoadMoreRecyclerAdapter = (A) adapter;
        initRecyclerView();

        setRefreshLayout();
    }

    /**
     * 初始化RecyclerView
     */
    protected void initRecyclerView(){
        RecyclerViewUtils.initPage(mRLView, mBaseLoadMoreRecyclerAdapter, null, getContext(), false);
    }

    /**
     * 动画结束才刷新
     */
    @Override
    public void onEnterAnimationEnd(Bundle savedInstanceState) {
        if (mIsInitialLoad)
            // 动画结束则刷新
            mRefreshLayout.autoRefresh();
        super.onEnterAnimationEnd(savedInstanceState);
    }

    /**
     * 右上角的菜单事件
     *
     * @param item 菜单项
     */
    @Override
    public boolean onMenuItemClick(MenuItem item) {
        int i = item.getItemId();
        if (i == R.id.action_search) {
            if (mLLTop.getVisibility() == View.GONE) {
                YoYo.with(Techniques.FadeIn).duration(250).playOn(mLLTop);
                mLLTop.setVisibility(View.VISIBLE);
                // 使用databind绑定值
                setConditionView(getCondition(false));
            } else {
                YoYo.with(Techniques.FadeOut).duration(250).playOn(mLLTop);
                mLLTop.setVisibility(View.GONE);
            }
        }
        return true;
    }

    /**
     * 刷新适配器
     *
     * @param list  数据源
     * @param isHas 控件是否允许请求下一页
     */
    public void refreshAdapter(List<T> list, boolean isHas) {
        //回到顶部
//        mRLView.scrollToPosition(0);
        mBaseLoadMoreRecyclerAdapter.clear();
        mBaseLoadMoreRecyclerAdapter.appendToList(list);
        mRLView.post(() -> {
            // 防止滑动的时候更新数据
            mBaseLoadMoreRecyclerAdapter.notifyDataSetChanged();
            initListView(isHas);
        });
    }

    /**
     * 适配器-下一页
     *
     * @param list  数据源
     * @param isHas 控件是否允许请求下一页
     */
    public void nextAdapter(List<T> list, boolean isHas) {
        mBaseLoadMoreRecyclerAdapter.appendToList(list);
        mBaseLoadMoreRecyclerAdapter.notifyItemRangeInserted(mBaseLoadMoreRecyclerAdapter.getList().size(), list.size());
        initListView(isHas);
    }

    /**
     * 赋值适配器
     *
     * @param list  数据源
     * @param isHas 控件是否允许请求下一页
     */
    public void setAdapter(List<T> list, boolean isHas) {
        mBaseLoadMoreRecyclerAdapter.appendToList(list);
        if (mOnRecyclerViewCallback != null)
            if (mOnRecyclerViewCallback.setAdapter(list, isHas))
                return;
        initListView(isHas);
    }

    /**
     * 刷新或者初始化适配器调用
     */
    private void initListView(boolean isHas) {
        mLoading.showContent();
        mHasFooter = isHas;
        mRefreshLayout.setEnableLoadMore(isHas);
    }

    /**
     * 刷新数据(有动画)
     */
    public void refreshDataAnimation() {
        mRefreshLayout.autoRefresh();
    }

    /**
     * 刷新数据(无动画)
     */
    public void refreshData() {
        getPresenterrConditionList().refreshData(getCondition(true));
    }

    /**
     * 加载数据
     */
    public void loadMoreData() {
        getPresenterrConditionList().loadMoreData(getCondition(false), mBaseLoadMoreRecyclerAdapter.getList().size());
    }

    /**
     * 关闭刷新
     */
    public void finishRefreshing() {
        mRefreshLayout.finishRefresh();
    }

    /**
     * 开始下一页
     */
    public void startLoadMore() {
        mRefreshLayout.autoLoadMore();
    }

    /**
     * 关闭下一页
     */
    public void finishLoadmore() {
        mRefreshLayout.finishLoadMore();
    }

    /**
     * 启用/禁止 下一页,包括适配器
     *
     * @param enableLoadmore 是否启用
     */
    public void setEnableLoadmore(boolean enableLoadmore) {
        mRefreshLayout.setEnableLoadMore(enableLoadmore);
        if (!enableLoadmore) {
            mBaseLoadMoreRecyclerAdapter.setHasFooter(true);
        } else {
            mBaseLoadMoreRecyclerAdapter.setHasFooter(false);
        }
    }

    /**
     * 显示异常信息
     *
     * @param strMessage 信息
     */
    public void recyclerViewShowError(String strMessage) {
        mRLView.post(() -> {
            //清除缓存数据
            mBaseLoadMoreRecyclerAdapter.clear();
            // 防止滑动的时候更新数据
            mBaseLoadMoreRecyclerAdapter.notifyDataSetChanged();
            mLoading.showError();
            mRefreshLayout.setEnableLoadMore(false);
        });
    }

    /**
     * 显示空的列表信息
     */
    public void recyclerViewShowEmpty(boolean isShowRecyclerView) {
        mRLView.post(() -> {
            //清除缓存数据
            mBaseLoadMoreRecyclerAdapter.clear();
            // 防止滑动的时候更新数据
            mBaseLoadMoreRecyclerAdapter.notifyDataSetChanged();
            mLoading.showEmpty();
            if (isShowRecyclerView){
                mRLView.setVisibility(View.VISIBLE);
            }else{
                mRLView.setVisibility(View.GONE);
            }
            mRefreshLayout.setEnableLoadMore(false);
        });
    }

    /**
     * 设置顶部滑动控件
     */
    private void setRefreshLayout() {
        mRefreshLayout.setRefreshHeader(new Pull2Header(getContext()));
    }

    /**
     * 初始化事件
     */
    public void initRecyclerviewListener() {
        //点击重新获取
        mLoading.setOnErrorInflateListener(inflated -> mRefreshLayout.autoRefresh());
        //点击重新获取
        mLoading.setOnEmptyInflateListener(inflated -> mRefreshLayout.autoRefresh());
        // 刷新和加载
        mRefreshLayout.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshLayout) {
                // 上拉加载
                loadMoreData();
            }

            @Override
            public void onRefresh(RefreshLayout refreshLayout) {
                // 下拉刷新
                refreshData();
            }
        });

        // 如果mLLTop为null,那就是没有顶部控件的，不需要实例化下面这些事件
        if (mLLTop != null && mCVTop != null) {
            mCVTop.setOnTouchListener((v, event) -> true);
            mLLTop.setOnTouchListener((v, event) -> {
                mLLTop.setVisibility(View.GONE);
                return true;
            });
            btnComit.setOnClickListener(v -> {
                InputMethodUtil.hideKeyboard(getActivity());
                mLLTop.setVisibility(View.GONE);
                mRefreshLayout.autoRefresh();
            });
            btnEmpty.setOnClickListener(v -> {
//            tvValueDockOfLoading.setText("");
//            tvValuePlaceLoading.setText("");
//            tvValuePlaceDelivery.setText("");
//            tvValueFinalDestination.setText("");
//            tvValueContId.setText("");
            });
        }


    }


}
