package com.yaoguang.lib.base;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;
import com.yaoguang.lib.appcommon.widget.head.*;
import com.yaoguang.lib.adapter.BaseLoadMoreRecyclerAdapter;
import com.yaoguang.lib.appcommon.widget.recyclerview.BallPulseView;
import com.yaoguang.lib.base.BaseFragment;

import java.util.List;

import ezy.ui.layout.LoadingLayout;

/**
 * 这是一个纯粹表格的fragment
 * Created by zhongjh on 2017/7/19.
 */
public abstract class BaseFragmentList<T> extends BaseFragment {

    RecyclerView mRecyclerview;
    LoadingLayout mLoading;
    protected RefreshLayout mRefreshLayout; // 刷新控件
    BaseLoadMoreRecyclerAdapter mBaseLoadMoreRecyclerAdapter;
    protected boolean mHasFooter;


    //回调接口
    protected OnRecyclerViewCallback mOnRecyclerViewCallback;

    protected interface OnRecyclerViewCallback<T> {
        //如果返回true，则后面的就不执行了
        boolean setAdapter(List<T> list, boolean isHas);
    }

    /**
     *
     * @param recyclerview 列表
     */
    public void setRecyclerview( RecyclerView recyclerview,RefreshLayout refreshLayout,  LoadingLayout loadingLayout, BaseLoadMoreRecyclerAdapter baseLoadMoreRecyclerAdapter) {

        this.mRecyclerview = recyclerview;
        this.mLoading = loadingLayout;
        this.mRefreshLayout = refreshLayout;
        this.mBaseLoadMoreRecyclerAdapter = baseLoadMoreRecyclerAdapter;
        setRefreshLayout();
    }


    /**
     * 设置顶部可以滑动
     */
    void setRefreshLayout() {
        mRefreshLayout.setRefreshHeader(new Pull2Header(getContext()));
    }

    /**
     * 刷新适配器
     *
     * @param list  数据源
     * @param isHas 是否开启下一页
     */
    public void refreshAdapter(List<T> list, boolean isHas) {
        //回到顶部
//        mRecyclerview.scrollToPosition(0);

        mBaseLoadMoreRecyclerAdapter.clear();
        mBaseLoadMoreRecyclerAdapter.appendToList(list);
        mBaseLoadMoreRecyclerAdapter.notifyDataSetChanged();
        initListView(isHas);
    }

    /**
     * 下一页的适配器
     *
     * @param list  数据源
     * @param isHas 是否开启下一页
     */
    public void nextAdapter(List<T> list, boolean isHas) {
        mBaseLoadMoreRecyclerAdapter.appendToList(list);
        mBaseLoadMoreRecyclerAdapter.notifyItemRangeInserted(mBaseLoadMoreRecyclerAdapter.getList().size(), list.size());
        initListView(isHas);
    }

    /**
     * 适配器数据
     *
     * @param list  数据源
     * @param isHas 是否开启下一页
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

    public void startRefresh() {
        mRefreshLayout.autoRefresh();
    }

    public void finishRefreshing() {
        mRefreshLayout.finishRefresh();
    }

    public void startLoadMore() {
        mRefreshLayout.autoLoadMore();
    }

    public void finishLoadmore() {
        mRefreshLayout.finishLoadMore();
    }

    public void setEnableLoadmore(boolean enableLoadmore) {
        mRefreshLayout.setEnableLoadMore(enableLoadmore);
        if (!enableLoadmore) {
            mBaseLoadMoreRecyclerAdapter.setHasFooter(true);
        } else {
            mBaseLoadMoreRecyclerAdapter.setHasFooter(false);
        }
    }

    public void recyclerViewShowError(String strMessage) {
        //清除缓存数据
        mBaseLoadMoreRecyclerAdapter.clear();
        mBaseLoadMoreRecyclerAdapter.notifyDataSetChanged();
        mLoading.showError();
        mRefreshLayout.setEnableLoadMore(false);
    }

    public void recyclerViewShowEmpty() {
        //清除缓存数据
        mBaseLoadMoreRecyclerAdapter.clear();
        mBaseLoadMoreRecyclerAdapter.notifyDataSetChanged();
        mLoading.showEmpty();
        mRefreshLayout.setEnableLoadMore(false);
    }

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
    }

    /**
     * 刷新数据
     */
    public abstract void refreshData();

    /**
     * 加载数据
     */
    public abstract void loadMoreData();

}
