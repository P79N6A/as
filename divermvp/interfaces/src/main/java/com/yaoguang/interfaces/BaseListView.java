package com.yaoguang.interfaces;

import java.util.List;

/**
 * 列表接口
 * Created by zhongjh on 2017/7/19.
 */
public interface BaseListView<T> {

    /**
     * 刷新适配器
     *
     * @param list  数据源
     * @param isHas 是否开启下一页
     */
    void refreshAdapter(List<T> list, boolean isHas);

    /**
     * 下一页的适配器
     *
     * @param list  数据源
     * @param isHas 是否开启下一页
     */
    void nextAdapter(List<T> list, boolean isHas);

    /**
     * 适配器数据
     *
     * @param list  数据源
     * @param isHas 是否开启下一页
     */
    void setAdapter(List<T> list, boolean isHas);

    /**
     * 列表开始刷新
     */
    void startRefresh();

    /**
     * 列表结束刷新
     */
    void finishRefreshing();

    /**
     * 列表开始下一页
     */
    void startLoadMore();

    /**
     * 列表结束下一页
     */
    void finishLoadmore();

    /**
     * 列表显示异常信息
     *
     * @param strMessage 异常信息
     */
    void recyclerViewShowError(String strMessage);

    /**
     * 列表显示空信息
     */
    void recyclerViewShowEmpty();

    /**
     * 初始化列表事件
     */
    void initRecyclerviewListener();

    /**
     * 刷新器和适配器是否开启下一页
     *
     * @param b 是否开启
     */
    void setEnableLoadmore(boolean b);
}
