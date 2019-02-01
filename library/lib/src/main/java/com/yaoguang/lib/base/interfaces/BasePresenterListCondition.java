package com.yaoguang.lib.base.interfaces;

/**
 * 基类控制层：列表+条件 的接口
 * Created by zhongjh on 2017/12/14.
 */
public interface BasePresenterListCondition<C> extends BasePresenter {


    void refreshData(C condition);

    void loadMoreData(C condition, int size);

}
