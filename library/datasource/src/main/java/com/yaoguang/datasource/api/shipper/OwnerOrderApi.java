package com.yaoguang.datasource.api.shipper;

import com.yaoguang.greendao.entity.common.ViewForwardOrder;
import com.yaoguang.lib.net.bean.BaseResponse;
import com.yaoguang.lib.net.bean.PageList;
import com.yaoguang.greendao.entity.AppOrderWrapper;
import com.yaoguang.greendao.entity.SysConditionWrapper;
import com.yaoguang.greendao.entity.common.TruckBills;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by zhongjh on 2017/6/27.
 */

public interface OwnerOrderApi {

    String OWNER_ORDER = "owner/order";


    /**
     * 工作单查询
     *
     * @param condition 条件实体
     * @param userId    用户id
     * @param pageIndex 页码
     * @return 返回数据源
     */
    @POST(OWNER_ORDER + "/getForwardOrders.do?")
    @Headers("Cache-Control: public, max-age=60")
    Observable<BaseResponse<PageList<AppOrderWrapper>>> getForwardOrders(@Body SysConditionWrapper condition, @Query("userId") String userId, @Query("pageIndex") int pageIndex);

    /**
     * 货代工作单明细
     *
     * @param billId 工作单主键id
     * @return 返回货代工作单明细
     */
    @POST(OWNER_ORDER + "/getForwardOrderById.do?")
    @Headers("Cache-Control: public, max-age=60")
    Observable<BaseResponse<ViewForwardOrder>> getForwardOrderById(@Query("clientId") String clientId, @Query("billId") String billId);

    /**
     * 拖车工作单明细
     *
     * @param billId 工作单主键id
     * @return 返回货代工作单明细
     */
    @POST(OWNER_ORDER + "/getTruckOrderById.do?")
    @Headers("Cache-Control: public, max-age=60")
    Observable<BaseResponse<TruckBills>> getTruckOrderById(@Query("clientId") String clientId, @Query("billId") String billId);


}
