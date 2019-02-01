package com.yaoguang.datasource.api.driver;

import com.yaoguang.greendao.entity.DriverStatusSwitch;
import com.yaoguang.greendao.entity.UserDriverStatusDetail;
import com.yaoguang.lib.net.bean.BaseResponse;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * 计划
 * Created by zhongjh on 2018/5/16.
 */
public interface RestPlanApi {

    String DRIVER_RESTPLAN = "driver/restPlan/";

    /**
     * 获取司机工作状态
     * @param driverId 司机id
     * @return  工作状态实体
     */
    @POST(DRIVER_RESTPLAN + "current.do?")
    Observable<BaseResponse<DriverStatusSwitch>> getDriverStatus(@Query("driverId") String driverId);

    /**
     * 更新司机工作状态
     *
     * @param userDriverStatusDetail 状态实体
     * @return json
     */
    @POST(DRIVER_RESTPLAN + "add.do")
    Observable<BaseResponse<String>> addDriverRestPlan(@Body UserDriverStatusDetail userDriverStatusDetail);

    /**
     * 更新休假计划
     *
     * @param id       计划id
     * @param endDate  结束时间
     * @param sendFlag 是否可向我派单
     * @return 成功失败标识
     */
    @POST(DRIVER_RESTPLAN + "update.do?")
    Observable<BaseResponse<String>> updateLeavePlan(@Query("id") String id, @Query("endDate") String endDate, @Query("sendFlag") String sendFlag);

    /**
     * 删除休假计划
     *
     * @return 消息
     */
    @POST(DRIVER_RESTPLAN + "delete.do?")
    Observable<BaseResponse<String>> deleteLeavePlan(@Query("id") String id);

}
