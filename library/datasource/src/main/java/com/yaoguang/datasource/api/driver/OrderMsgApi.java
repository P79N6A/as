package com.yaoguang.datasource.api.driver;

import com.yaoguang.greendao.entity.driver.DriverOrderMsgWrapper;
import com.yaoguang.lib.net.bean.BaseResponse;
import com.yaoguang.lib.net.bean.PageList;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * 订单消息
 * Created by zhongjh on 2018/5/11.
 */
public interface OrderMsgApi {

    String DRIVER_ORDERMSG = "driver/orderMsg/";

    /**
     * 获取订单中心列表
     *
     * @param driverId  司机id
     * @param pageIndex 页码
     */
    @GET(DRIVER_ORDERMSG + "list.do?")
    Observable<BaseResponse<PageList<DriverOrderMsgWrapper>>> getMessageOrderList(@Query("driverId") String driverId, @Query("pageIndex") int pageIndex);

    /**
     * 获取订单信息列表
     *
     * @param driverId  司机id
     * @param pageIndex 页码
     * @param type      页码 type(0：业务消息 1：平台公告)
     * @param flag      // 0：未读 1：已读
     */
    @GET(DRIVER_ORDERMSG + "list.do?")
    Observable<BaseResponse<PageList<DriverOrderMsgWrapper>>> getHomeMessageList(@Query("driverId") String driverId, @Query("pageIndex") int pageIndex, @Query("type") String type, @Query("noticeType") String noticeType, @Query("flag") int flag);

    /**
     * 删除订单消息
     *
     * @param ids          消息id
     * @param platformType 平台类型
     */
    @GET(DRIVER_ORDERMSG + "delete.do?")
    Observable<BaseResponse<String>> orderMessageDeleted(@Query("ids") String ids, @Query("platformType") int platformType);

    /**
     * 批量设置已读
     *
     * @param ids 订单id 列表
     */
    @GET(DRIVER_ORDERMSG + "readBatch.do?")
    Observable<BaseResponse<String>> readBatch(@Query("ids") String ids);

}
