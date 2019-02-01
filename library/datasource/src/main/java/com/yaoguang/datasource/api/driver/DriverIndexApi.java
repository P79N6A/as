package com.yaoguang.datasource.api.driver;

import com.yaoguang.greendao.entity.driver.UnreadNum;
import com.yaoguang.greendao.entity.driver.DriverOrderProgressWrapper;
import com.yaoguang.lib.net.bean.BaseResponse;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * 有关于index的api，即是首页的
 * Created by zhongjh on 2018/5/16.
 */

public interface DriverIndexApi {

    String DRIVER_INDEX = "driver/index/";

    /**
     * 首页订单进度
     *
     * @param driverId 司机id
     */
    @POST(DRIVER_INDEX + "progress.do?")
    Observable<BaseResponse<DriverOrderProgressWrapper>> getOrderCurrentProgress(@Query("driverId") String driverId);

    /**
     * 获取未读数量/平台公告
     *
     * @param platformType 0pc 1司机 2供应链 3货主
     */
    @GET(DRIVER_INDEX + "msgNumber.do?")
    Observable<BaseResponse<UnreadNum>> msgNumber(@Query("driverId") String driverId, @Query("platformType") int platformType);

}
