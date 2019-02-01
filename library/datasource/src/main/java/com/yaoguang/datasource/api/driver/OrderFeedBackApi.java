package com.yaoguang.datasource.api.driver;

import com.yaoguang.greendao.entity.driver.DriverOrderNodeFeedback;
import com.yaoguang.lib.net.bean.BaseResponse;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * 意见反馈
 * Created by zhongjh on 2018/5/17.
 */
public interface OrderFeedBackApi {

    /**
     * 提交反馈
     *
     */
    @POST("driverOrder/feedback/add.do?")
    Observable<BaseResponse<String>> feedbackAdd(@Body DriverOrderNodeFeedback driverOrderNodeFeedback);

    /**
     * 查看反馈
     *
     * @param orderId  订单id
     * @param driverId 用户id
     */
    @POST("driverOrder/feedback/list.do?")
    Observable<BaseResponse<List<DriverOrderNodeFeedback>>> feedbackList(@Query("orderId") String orderId, @Query("driverId") String driverId);

}
