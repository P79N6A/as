package com.yaoguang.driver.base.baseview;


import java.util.List;

/**
 * 列表接口
 * Created by 韦理英 on 2017/7/19.
 */
public interface BaseListViewV2<T> extends BaseView {

    void refreshAdapter(List<T> list, boolean isHas);

    /**
     * 下一页适配器添加
     *
     * @param value       数据源
     * @param hasNextPage 是否下一页
     */
    void nextAdapter(List<T> value, boolean hasNextPage);

    /**
     * 刷新适配器的公共方法
     *
     * @param value       数据源
     * @param hasNextPage 是否下一页
     */
    void setAdapter(List<T> value, boolean hasNextPage);

    /**
     * 显示列表加载中的框
     */
    void startRefresh();

    /**
     * 隐藏列表加载中的框
     */
    void finishRefreshing();

    void recyclerViewShowError(String strMessage);

    void recyclerViewShowEmpty();

    /**
     * 设置列表View的当前页数
     *
     * @param i
     */
    void setPreviousTotal(int i);

    /**
     * 显示加载更多
     * @param b
     */
    void setShowAllDataLoadFinish(boolean b);
}
