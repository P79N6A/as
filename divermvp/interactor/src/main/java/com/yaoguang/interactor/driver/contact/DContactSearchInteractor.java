package com.yaoguang.interactor.driver.contact;


import com.yaoguang.greendao.entity.common.UserOfficeWrapper;
import com.yaoguang.lib.net.bean.BaseResponse;
import com.yaoguang.lib.net.bean.PageList;
import com.yaoguang.greendao.entity.Company;

import io.reactivex.Observable;

/**
 * 企业关联查询 的 业务层
 * 作者：zhongjh
 * 时间：2017-04-11
 */
public interface DContactSearchInteractor {

    /**
     * 查询企业
     * 作者：zhongjh
     * 时间：2017-04-26
     *
     * @param companyName 企业名称,可以非空
     * @param pageIndex   当前页数
     * @return 返回list
     */
    Observable<BaseResponse<PageList<UserOfficeWrapper>>> initContactCompanys(String companyName, int pageIndex, final String type);

    /**
     * 进行添加关联企业处理
     * 作者：zhongjh
     * 时间：2017-04-12
     *
     *
     * @param companyId 企业id
     * @return 返回数据源
     */
    Observable<BaseResponse<String>> handleAddContact(String companyId, String reason, final String type);

}