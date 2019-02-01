package com.yaoguang.datasource.api.driver;

import com.yaoguang.lib.net.bean.BaseResponse;
import com.yaoguang.greendao.entity.LatLons;
import com.yaoguang.greendao.entity.OrderDetailNodeList;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * 这是物流端和货主端引用司机界面所需要的接口
 * Created by zhongjh on 2017/11/7.
 */
public interface DriverOrderApi {

    String DRIVERORDER_NODE = "driverOrder/node";

    /**
     * 订单轨迹经纬度
     *
     * @param type     类型(4:装货 5:送货)
     * @param pcSonoId 全平台货柜id
     * @param dataType 1:节点 2：轨迹 3：提交节点记录
     * @return
     */
    @POST(DRIVERORDER_NODE + "/getSonoTransferNews.do?")
    Observable<BaseResponse<List<LatLons>>> logs(@Query("type") int type, @Query("pcSonoId") String pcSonoId, @Query("dataType") int dataType);

    /**
     * 订单明细节点
     *
     * @param type     类型(4:装货 5:送货)
     * @param pcSonoId 全平台货柜id
     * @param dataType 1:节点 2：轨迹 3：提交节点记录
     * @return
     */
    @POST(DRIVERORDER_NODE + "/getSonoTransferNews.do?")
    Observable<BaseResponse<OrderDetailNodeList>> orderDetailNote(@Query("type") int type, @Query("pcSonoId") String pcSonoId, @Query("dataType") int dataType);



}
