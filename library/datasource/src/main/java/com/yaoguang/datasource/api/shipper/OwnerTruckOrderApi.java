package com.yaoguang.datasource.api.shipper;

import com.yaoguang.greendao.entity.company.AppCompanyOrderCondition;
import com.yaoguang.greendao.entity.shipper.AppOwnerTruckOrder;
import com.yaoguang.greendao.entity.shipper.AppOwnerTruckOrderTemplate;
import com.yaoguang.lib.net.bean.BaseResponse;
import com.yaoguang.lib.net.bean.PageList;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * 拖车 - 业务下单
 * Created by zhongjh on 2018/9/7.
 */
public interface OwnerTruckOrderApi {

    String OWNER_TRUCKORDER = "owner/truckOrder";

    /**
     * 业务下单查询
     *
     * @param appCompanyOrderCondition 条件实体
     * @param userId                   用户id
     * @param pageIndex                页码
     * @return 返回数据源
     */
    @POST(OWNER_TRUCKORDER + "/getAppOrders.do?")
    @Headers("Cache-Control: public, max-age=5")
    Observable<BaseResponse<PageList<AppOwnerTruckOrder>>> getAppOrders(@Body AppCompanyOrderCondition appCompanyOrderCondition, @Query("userId") String userId, @Query("pageIndex") int pageIndex);

    /**
     * 业务下单明细
     *
     * @param id 主键
     * @return 返回实体的明细
     */
    @POST(OWNER_TRUCKORDER + "/getAppOrder.do?")
    Observable<BaseResponse<AppOwnerTruckOrder>> getAppOrder(@Query("id") String id);


    /**
     * 拖车业务下单 - 编辑或新增
     *
     * @param appTruckOrder 拖车订单实体类
     * @param userId        用户id
     * @return 成功或者错误的信息
     */
    @POST(OWNER_TRUCKORDER + "/editTruckOrder.do?")
    Observable<BaseResponse<String>> editTruckOrder(@Body AppOwnerTruckOrder appTruckOrder, @Query("userId") String userId);

    /**
     * 获取业务下单模版
     *
     * @param condition 条件类
     * @param userId    用户id
     * @param pageIndex 页码
     * @return 数据模版
     */
    @POST(OWNER_TRUCKORDER + "/getOrderTemplates.do?")
    Observable<BaseResponse<PageList<AppOwnerTruckOrderTemplate>>> getOrderTemplates(@Body AppCompanyOrderCondition condition, @Query("userId") String userId, @Query("pageIndex") int pageIndex);

    /**
     * 编辑模版
     *
     * @param appTruckOrderTemplate 实体类
     * @param userId                用户id
     * @return 编辑信息
     */
    @POST(OWNER_TRUCKORDER + "/editTemplate.do?")
    Observable<BaseResponse<String>> editTemplate(@Body AppOwnerTruckOrderTemplate appTruckOrderTemplate, @Query("userId") String userId);

    /**
     * 批量删除模版
     *
     * @param ids ,分隔，多个id
     * @return 编辑信息
     */
    @POST(OWNER_TRUCKORDER + "/deleteTemplate.do?")
    Observable<BaseResponse<String>> editTemplate(@Query("ids") String ids);

    /**
     * 删除业务单
     * @param ids id
     * @return
     */
    @POST(OWNER_TRUCKORDER + "/cancelOrder.do?")
    Observable<BaseResponse<String>> cancelOrder(@Query("ids") String ids);
}
