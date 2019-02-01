package com.yaoguang.interactor.driver.contact;

import com.yaoguang.lib.base.interfaces.BasePresenter;

/**
 * 企业查找
 * Created by zhongjh on 2017/4/11.
 */
public interface DContactSearchPresenter extends BasePresenter {

    /**
     * 刷新企业列表
     *
     * @param strCompanyName 企业名称
     */
    void refreshData(String strCompanyName);

    /**
     * 加载下一页数据
     *
     * @param strCompanyName 企业名称
     * @param dataSize       当前数据源长度
     */
    void loadMoreData(String strCompanyName, int dataSize);

    /**
     * 刷新企业列表
     *
     * @param strCompanyName 搜索条件(可空)
     * @param dataSize       列表数据源长度
     * @param isNext         是否加载下一页的
     */
    void getData(String strCompanyName, int dataSize, final boolean isNext);
}
