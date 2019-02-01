package com.yaoguang.datasource.api;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * 订单统计
 * Created by zhongjh on 2017/5/5.
 */
public interface FreightStatisticApi {

    String STATISTIC = "statistic";

    /**
     * 获取企业列表
     *
     * @param driverId  司机id
     * @param companyId 公司id
     * @param startDate 开始时间
     * @param endDate   结束时间
     * @param dateScopeType 时间范围 0、全部，1、近一周，2、近一月，3、近三月
     * @return 列表
     */
    @GET(STATISTIC + "/freightStatistic.do?")
    Call<ResponseBody> freightStatistic(@Query("driverId") String driverId, @Query("companyId") String companyId, @Query("startDate") String startDate, @Query("endDate") String endDate, @Query("dateScopeType") String dateScopeType);


}
