package com.yaoguang.interactor.driver.contact;

import com.yaoguang.lib.base.interfaces.BasePresenter;

/**
 * 挂靠企业管理 的 逻辑分发层
 * 作者：zhongjh
 * 时间：2017-04-6
 */
public interface DContactChildPresenter extends BasePresenter {

    void refreshData(int mType);

    void loadMoreData(int mType, int size);

    /**
     * 初始化企业管理
     * 作者：zhongjh
     * 时间：2017-04-6
     *
     * @param companyName 公司名称
     * @param companyId   公司id
     * @param intType     类型
     * @param dataSize
     * @param isNext
     */
    void initContactCompanys(String companyName, String companyId, int intType, final int dataSize, final boolean isNext);
}