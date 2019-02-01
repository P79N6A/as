package com.yaoguang.lib.appcommon.utils;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.yaoguang.lib.R;
import com.yaoguang.lib.appcommon.widget.recyclerview.HorizontalDividerItemDecoration;


/**
 * 一些初始化
 * Created by zhongjh on 2017/6/7.
 */
public class RecyclerViewUtils {

    /**
     * 初始化分页的RecyclerView的样式 （5dp的高度）
     *
     * @param recyclerView        列表
     * @param adapter             适配器
     * @param swipeRefreshLayout  刷新控件
     * @param context             上下文
     * @param isAddItemDecoration 是否添加分割线
     */
    public static void initPage5(RecyclerView recyclerView, RecyclerView.Adapter adapter, SwipeRefreshLayout swipeRefreshLayout, Context context, boolean isAddItemDecoration) {
        initPage(recyclerView, adapter, swipeRefreshLayout, context, isAddItemDecoration, R.dimen.divider5, R.dimen.margin0);
    }

    /**
     * 初始化分页的RecyclerView的样式
     *
     * @param recyclerView        列表
     * @param adapter             适配器
     * @param swipeRefreshLayout  刷新控件
     * @param context             上下文
     * @param isAddItemDecoration 是否添加分割线
     */
    public static void initPage(RecyclerView recyclerView, RecyclerView.Adapter adapter, SwipeRefreshLayout swipeRefreshLayout, Context context, boolean isAddItemDecoration) {
        initPage(recyclerView, adapter, swipeRefreshLayout, context, isAddItemDecoration, R.dimen.divider, R.dimen.margin0);
    }

    /**
     * 初始化分页的RecyclerView的样式
     *
     * @param recyclerView        列表
     * @param adapter             适配器
     * @param swipeRefreshLayout  刷新控件
     * @param context             上下文
     * @param isAddItemDecoration 是否添加分割线
     * @param margin 线的左右间隔
     */
    public static void initPageMargin(RecyclerView recyclerView, RecyclerView.Adapter adapter, SwipeRefreshLayout swipeRefreshLayout, Context context, boolean isAddItemDecoration,int margin) {
        initPage(recyclerView, adapter, swipeRefreshLayout, context, isAddItemDecoration, R.dimen.divider, margin);
    }

    public static void initPage(RecyclerView recyclerView, RecyclerView.Adapter adapter, SwipeRefreshLayout swipeRefreshLayout, Context context, boolean isAddItemDecoration, int divider, int margin) {
        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(layoutManager);

        //添加分割线
        if (isAddItemDecoration)
            recyclerView.addItemDecoration(
                    new HorizontalDividerItemDecoration.Builder(context)
                            .showLastDivider()
                            .color(ContextCompat.getColor(context, R.color.black_dividers_text))
                            .sizeResId(divider)
                            .margin(margin)
                            .build());

        //初始化上拉更新颜色
        if (swipeRefreshLayout != null)
            swipeRefreshLayout.setColorSchemeResources(
                    R.color.blue_primary,
                    R.color.green_primary,
                    R.color.red_primary,
                    R.color.yellow_primary
            );
    }


}
