package com.yaoguang.datasource.api.company;

import com.yaoguang.lib.net.bean.BaseResponse;
import com.yaoguang.lib.net.bean.PageList;
import com.yaoguang.greendao.entity.company.AppCompanyOrderCondition;
import com.yaoguang.greendao.entity.company.AppTruckOrder;
import com.yaoguang.greendao.entity.company.AppTruckOrderTemplate;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * 拖车订单
 * Created by zhongjh on 2017/9/27.
 */
public interface CompanyTruckOrderApi {

    String TRUCK_TRUCKORDER = "truck/truckOrder";

    /**
     * 业务下单查询
     *
     * @param appCompanyOrderCondition 条件实体
     * @param userId                   用户id
     * @param pageIndex                页码
     * @return 返回数据源
     */
    @POST(TRUCK_TRUCKORDER + "/getAppOrders.do?")
    @Headers("Cache-Control: public, max-age=5")
    Observable<BaseResponse<PageList<AppTruckOrder>>> getAppOrders(@Body AppCompanyOrderCondition appCompanyOrderCondition, @Query("userId") String userId, @Query("pageIndex") int pageIndex);

    /**
     * 业务下单明细
     *
     * @param id 主键
     * @return 返回实体的明细
     */
    @POST(TRUCK_TRUCKORDER + "/getAppOrder.do?")
    Observable<BaseResponse<AppTruckOrder>> getAppOrder(@Query("id") String id);


    /**
     * 企业端拖车业务下单 - 编辑或新增
     *
     * @param appTruckOrder 拖车订单实体类
     * @param userId        用户id
     * @return 成功或者错误的信息
     */
    @POST(TRUCK_TRUCKORDER + "/editTruckOrder.do?")
    Observable<BaseResponse<String>> editTruckOrder(@Body AppTruckOrder appTruckOrder, @Query("userId") String userId);

    /**
     * 获取业务下单模版
     *
     * @param condition 条件类
     * @param userId    用户id
     * @param pageIndex 页码
     * @return 数据模版
     */
    @POST(TRUCK_TRUCKORDER + "/getOrderTemplates.do?")
    Observable<BaseResponse<PageList<AppTruckOrderTemplate>>> getOrderTemplates(@Body AppCompanyOrderCondition condition, @Query("userId") String userId, @Query("pageIndex") int pageIndex);

    /**
     * 编辑模版
     *
     * @param appTruckOrderTemplate 实体类
     * @param userId                用户id
     * @return 编辑信息
     */
    @POST(TRUCK_TRUCKORDER + "/editTemplate.do?")
    Observable<BaseResponse<String>> editTemplate(@Body AppTruckOrderTemplate appTruckOrderTemplate, @Query("userId") String userId);

    /**
     * 批量删除模版
     *
     * @param ids ,分隔，多个id
     * @return 编辑信息
     */
    @POST(TRUCK_TRUCKORDER + "/deleteTemplate.do?")
    Observable<BaseResponse<String>> editTemplate(@Query("ids") String ids);


}
