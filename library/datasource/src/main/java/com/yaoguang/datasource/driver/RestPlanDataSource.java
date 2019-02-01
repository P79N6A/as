package com.yaoguang.datasource.driver;

import com.yaoguang.datasource.common.DataStatic;
import com.yaoguang.greendao.entity.DriverStatusSwitch;
import com.yaoguang.greendao.entity.UserDriverStatusDetail;
import com.yaoguang.lib.net.Api;
import com.yaoguang.lib.net.bean.BaseResponse;
import com.yaoguang.datasource.api.driver.RestPlanApi;

import io.reactivex.Observable;

/**
 * 休假、工作状态
 * Created by zhongjh on 2018/5/16.
 */
public class RestPlanDataSource {

    private RestPlanApi restPlanApi = Api.getInstance().retrofit.create(RestPlanApi.class);

    /**
     * 获取司机工作状态
     */
    public Observable<BaseResponse<DriverStatusSwitch>> getDriverStatus() {
        return restPlanApi.getDriverStatus(DataStatic.getInstance().getDriver().getId());
    }

    /**
     * 更新司机工作状态
     *
     * @param userDriverStatusDetail 状态实体
     */
    public Observable<BaseResponse<String>> addDriverRestPlan(UserDriverStatusDetail userDriverStatusDetail) {
        return restPlanApi.addDriverRestPlan(userDriverStatusDetail);
    }

    /**
     * 删除休假计划
     */
    public Observable<BaseResponse<String>> deleteLeavePlan(String id) {
        return restPlanApi.deleteLeavePlan(id);
    }

    /**
     * 更新休假计划
     *
     * @param id       计划id
     * @param endDate  结束时间
     * @param sendFlag 是否可向我派单
     * @return 成功失败标识
     */
    public Observable<BaseResponse<String>> updateLeavePlan(String id, String endDate, String sendFlag) {
        return restPlanApi.updateLeavePlan(id, endDate, sendFlag);
    }


}
