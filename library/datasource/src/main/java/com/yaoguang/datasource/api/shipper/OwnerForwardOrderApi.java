package com.yaoguang.datasource.api.shipper;

import com.yaoguang.greendao.entity.company.AppCompanyOrderCondition;
import com.yaoguang.greendao.entity.shipper.AppOwnerForwardOrder;
import com.yaoguang.greendao.entity.shipper.AppOwnerForwardOrderTemplate;
import com.yaoguang.lib.net.bean.BaseResponse;
import com.yaoguang.lib.net.bean.PageList;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * 货代订单
 * Created by zhongjh on 2018/9/4.
 */
public interface OwnerForwardOrderApi {

    String OWNER_FORWARDORDER = "owner/forwardOrder";

    /**
     * 业务下单
     *
     * @param appOwnerForwardOrder 下单模型
     * @param userId          用户id
     * @return 返回的信息文本
     */
    @POST(OWNER_FORWARDORDER + "/addCompanyOrder.do?")
    Observable<BaseResponse<String>> addCompanyOrder(@Body AppOwnerForwardOrder appOwnerForwardOrder, @Query("userId") String userId);

    /**
     * 业务下单明细
     *
     *
     * @param id
     *            业务下单主键id
     */
    @POST(OWNER_FORWARDORDER + "/getAppOrder.do?")
    @Headers("Cache-Control: public, max-age=5")
    Observable<BaseResponse<AppOwnerForwardOrder>> getAppOrder( @Query("id") String id);

    /**
     * 业务下单查询
     *
     * @param appCompanyOrderCondition 条件实体
     * @param userId                   用户id
     * @param pageIndex                页码
     * @return 返回数据源
     */
    @POST(OWNER_FORWARDORDER + "/getAppOrders.do?")
    @Headers("Cache-Control: public, max-age=5")
    Observable<BaseResponse<PageList<AppOwnerForwardOrder>>> getAppOrders(@Body AppCompanyOrderCondition appCompanyOrderCondition, @Query("userId") String userId, @Query("pageIndex") int pageIndex);

    /**
     * 业务下单查询 (模版)
     *
     * @param appCompanyOrderCondition 条件实体
     * @param userId                   用户id
     * @param pageIndex                页码
     * @return 返回数据源
     */
    @POST(OWNER_FORWARDORDER + "/getForwardOrderTemplates.do?")
    @Headers("Cache-Control: public, max-age=5")
    Observable<BaseResponse<PageList<AppOwnerForwardOrderTemplate>>> getForwardOrderTemplates(@Body AppCompanyOrderCondition appCompanyOrderCondition, @Query("userId") String userId, @Query("pageIndex") int pageIndex);

    /**
     * 编辑模版
     *
     * @param appOwnerForwardOrderTemplate 实体类
     * @param userId                  用户id
     * @return 编辑信息
     */
    @POST(OWNER_FORWARDORDER + "/editTemplate.do?")
    Observable<BaseResponse<String>> editTemplate(@Body AppOwnerForwardOrderTemplate appOwnerForwardOrderTemplate, @Query("userId") String userId);

    /**
     * 删除模板
     * @param ids id
     * @return 返回消息
     */
    @POST(OWNER_FORWARDORDER + "/cancelOrder.do?")
    Observable<BaseResponse<String>> cancelOrder( @Query("ids") String ids);

}
