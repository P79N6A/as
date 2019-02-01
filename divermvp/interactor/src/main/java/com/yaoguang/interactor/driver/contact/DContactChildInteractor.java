package com.yaoguang.interactor.driver.contact;

import com.yaoguang.lib.net.bean.BaseResponse;
import com.yaoguang.lib.net.bean.PageList;
import com.yaoguang.greendao.entity.Contact;

import io.reactivex.Observable;

/**
 * 企业我的关联 的 业务层
 * 作者：zhongjh
 * 时间：2017-04-6
 */
public interface DContactChildInteractor {

    /**
     * 初始化企业管理
     * 作者：zhongjh
     * 时间：2017-04-6
     *
     * @param companyName 公司名称
     * @param companyId   公司id
     * @param intType     类型，0是关联、1是待审核、2是拒绝
     * @param type        类型，0司机端 1物流端 2货主端
     * @param pageIndex   查询页
     * @return 数据源
     */
    Observable<BaseResponse<PageList<Contact>>> initContactCompanys(String companyName, String companyId, int intType, String type, int pageIndex);

    /**
     * 取消关联企业
     * 作者：zhongjh
     * 时间：2017-04-11
     *
     * @param strContactId 关联表id
     * @param type        类型，0司机端 1物流端 2货主端
     */
    Observable<BaseResponse<String>> handleCancelContact(String strContactId, String type);

    /**
     * 删除关联企业
     * 作者：zhongjh
     * 时间：2017-04-27
     *
     * @param strContactId 关联表id
     */
    Observable<BaseResponse<String>> handleDeleteContact(String strContactId);

}