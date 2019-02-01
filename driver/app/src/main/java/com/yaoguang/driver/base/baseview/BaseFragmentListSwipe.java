package com.yaoguang.driver.base.baseview;

import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.RelativeLayout;

import com.elvishew.xlog.XLog;
import com.yaoguang.common.R;
import com.yaoguang.common.adapter.BaseLoadMoreRecyclerAdapter;
import com.yaoguang.common.base.BaseSwipeRefresh;
import com.yaoguang.common.common.debounceclick.NoDoubleClickListener;
import com.yaoguang.common.listener.EndlessRecyclerOnScrollListener;

import java.util.List;

/**
 * 文件名：
 * 描    述：
 * 作    者：韦理英
 * 时    间：2017/9/4 0004.
 * 版    权：
 */
public abstract class BaseFragmentListSwipe<T> extends BaseFragment {

    public RecyclerView recyclerView;
    private RelativeLayout rlEmpty;
    private RelativeLayout rlError;
    private BaseSwipeRefresh swipeRefresh;
    private BaseLoadMoreRecyclerAdapter baseLoadMoreRecyclerAdapter;
    private EndlessRecyclerOnScrollListener endlessRecyclerOnScrollListener;
    private boolean openLoadMore = true;
    private boolean isShowRefresh = true;

    /**
     * 初始化入口
     *
     * @param view    View
     * @param adapter BaseLoadMoreRecyclerAdapter
     */
    public void initSwipeRecyclerView(@NonNull View view, BaseLoadMoreRecyclerAdapter adapter) {
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        rlEmpty = (RelativeLayout) view.findViewById(R.id.rlEmpty);
        rlError = (RelativeLayout) view.findViewById(R.id.rlError);
        baseLoadMoreRecyclerAdapter = adapter;
        swipeRefresh = new BaseSwipeRefresh(view);
        initPageRecyclerview();
        initRecyclerviewListener();
    }


    private void initPageRecyclerview() {
        recyclerView.setAdapter(baseLoadMoreRecyclerAdapter);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
    }

    public void refreshAdapter(List<T> list, boolean isHas) {
        if (recyclerView == null) return;
        //回到顶部
        recyclerView.scrollToPosition(0);
        baseLoadMoreRecyclerAdapter.clear();
        setAdapter(list, isHas);
    }

    public void nextAdapter(List<T> list, boolean isHas) {
        setAdapter(list, isHas);
    }

    public void setAdapter(List<T> list, boolean isHas) {
        recyclerView.setVisibility(View.VISIBLE);
        baseLoadMoreRecyclerAdapter.appendToList(list);
        baseLoadMoreRecyclerAdapter.notifyDataSetChanged();

        if (rlEmpty != null && rlError != null) {
            rlEmpty.setVisibility(View.GONE);
            rlError.setVisibility(View.GONE);
        }
    }

    public void recyclerViewShowError(String strMessage) {
        if (recyclerView != null) recyclerView.setVisibility(View.GONE);
//        showToast(strMessage);
        if (rlEmpty != null && rlError != null) {
            rlEmpty.setVisibility(View.GONE);
            rlError.setVisibility(View.VISIBLE);
        }
    }

    public void recyclerViewShowEmpty() {
        baseLoadMoreRecyclerAdapter.clear();
        baseLoadMoreRecyclerAdapter.notifyDataSetChanged();

        recyclerView.setVisibility(View.GONE);
        if (rlEmpty != null && rlError != null) {
            rlError.setVisibility(View.GONE);
            rlEmpty.setVisibility(View.VISIBLE);
        }
    }

    public void startRefresh() {
        if (swipeRefresh == null || !isShowRefresh) return;
        swipeRefresh.startRefresh();
    }

    public void finishRefreshing() {
        if (swipeRefresh == null || !isShowRefresh) return;
        swipeRefresh.finishRefreshing();
    }

    public void initRecyclerviewListener() {
        //可以初始刷新
        recyclerView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    recyclerView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                }
                refreshData();
            }
        });
        //下拉加载
        if (openLoadMore) {
            endlessRecyclerOnScrollListener = new EndlessRecyclerOnScrollListener((LinearLayoutManager) recyclerView.getLayoutManager()) {
                @Override
                public void onLoadMore(int currentPage) {
                    XLog.i("onLoadMore");
                    loadMoreData();
                }
            };
            endlessRecyclerOnScrollListener.previousTotal = 0;
            recyclerView.addOnScrollListener(endlessRecyclerOnScrollListener);
        }
        //上拉刷新
        if (swipeRefresh != null)
            swipeRefresh.setOnRefreshData(new BaseSwipeRefresh.OnRefreshData() {
                @Override
                public void refreshData2() {
                    refreshData();
                }
            });
        if (rlError != null) {
            //点击重新获取
            rlEmpty.setOnClickListener(new NoDoubleClickListener() {
                @Override
                public void onNoDoubleClick(View v) {
                    refreshData();
                }
            });
            //点击重新获取
            rlError.setOnClickListener(new NoDoubleClickListener() {
                @Override
                public void onNoDoubleClick(View v) {
                    refreshData();
                }
            });
        }
    }

    public void setOpenLoadMore(boolean openLoadMore) {
        this.openLoadMore = openLoadMore;
    }

    /**
     * 是否显示 全部数据加载完成
     * @param isShow true 加载完成 false 没有加载完成
     */
    public void setShowAllDataLoadFinish(boolean isShow) {
        if (baseLoadMoreRecyclerAdapter == null) return;

        if (isShow) {  //  有下页，不显示加载完成
            baseLoadMoreRecyclerAdapter.setHasFooter(true);
        } else {    //  没有下页，显示加载完成
            baseLoadMoreRecyclerAdapter.setHasFooter(false);
        }
    }

    /**
     * 描    述：主要用来存储上一个,totalItemCount已经加载出来的Item的数量
     * 作    者：韦理英
     * 时    间：
     *
     * @param i 保存已加载数据
     */
    public void setPreviousTotal(int i) {
        if (endlessRecyclerOnScrollListener == null) return;
        endlessRecyclerOnScrollListener.previousTotal = i;
    }

    /**
     * 描    述： 禁止rv滑动，防止冲突
     * 作    者：韦理英
     * 时    间：
     *
     * @param isScroll true 启动 false 不启动
     */

    public void nestedScrollingEnabled(boolean isScroll) {
        recyclerView.setNestedScrollingEnabled(isScroll);
    }

    protected void loadMoreData() {

    }

    protected abstract void refreshData();

    public boolean isShowRefresh() {
        return isShowRefresh;
    }

    public void setShowRefresh(boolean showRefresh) {
        isShowRefresh = showRefresh;
    }

    public void setRlError(RelativeLayout rlError) {
        this.rlError = rlError;
    }
}
