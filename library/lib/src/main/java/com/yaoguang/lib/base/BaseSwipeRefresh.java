package com.yaoguang.lib.base;

import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;

import com.yaoguang.lib.R;

/**
 * 描    述： BaseSwipeRefresh 刷新
 * 作    者：韦理英
 * 时    间：2017/9/4 0004.
 */
public class BaseSwipeRefresh {

    private SwipeRefreshLayout swipeRefreshLayout;

    public BaseSwipeRefresh(View view) {
        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipeRefreshLayout);

        //初始化上拉更新颜色
        if (swipeRefreshLayout != null) {
            swipeRefreshLayout.setColorSchemeResources(
                    R.color.blue_primary,
                    R.color.green_primary,
                    R.color.red_primary,
                    R.color.yellow_primary
            );
        }
    }

    Handler handler = new Handler();

    public void startRefresh() {
        if (swipeRefreshLayout == null) return;
        //  修复努比亚兼容性问题
        handler.post(new Runnable() {
            @Override
            public void run() {
                swipeRefreshLayout.setRefreshing(true);
            }
        });
    }

    public void finishRefreshing() {
        if (swipeRefreshLayout == null) return;

        //  修复努比亚兼容性问题
        handler.post(new Runnable() {
            @Override
            public void run() {
                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }

    public void setOnRefreshData(final OnRefreshData onRefreshData) {
        //上拉刷新
        if (swipeRefreshLayout != null)
            swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    onRefreshData.refreshData2();
                }
            });
    }

    public interface OnRefreshData {
        void refreshData2();
    }
}
