package com.yaoguang.datasource.api;

import com.yaoguang.greendao.entity.driver.DriverOrderDetailVo;
import com.yaoguang.lib.net.bean.BaseResponse;
import com.yaoguang.lib.net.bean.PageList;
import com.yaoguang.greendao.entity.DriverOrderNode;
import com.yaoguang.greendao.entity.DriverOrderNodeDetail;
import com.yaoguang.greendao.entity.DriverOrderNodeDetailWrapper;
import com.yaoguang.greendao.entity.driver.DriverOrderNodeFeedback;
import com.yaoguang.greendao.entity.FreightStatistic;
import com.yaoguang.greendao.entity.InfoPutorderPlace;
import com.yaoguang.greendao.entity.LatLons;
import com.yaoguang.greendao.entity.Order;
import com.yaoguang.greendao.entity.OrderDetailNodeList;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * 订单api
 * Created by wly on 2017/12/8 0008.
 */


public interface ApiOrder {




    /**
     * 进度更新
     *
     * @param orderId 订单id
     */
    @GET("driverOrder/node/next.do?")
    Observable<BaseResponse<List<DriverOrderNode>>> next(@Query("driverOrderId") String orderId);

    /**
     * 获取订单存放地址列表
     * @param id        id
     *
     */
    @GET("driver/order/places.do?")
    Observable<BaseResponse<List<InfoPutorderPlace>>> getPutOrderAddressList(@Query("id") String id);


    /**
     * 获取订单中心列表
     *
     * @param driverId  司机id
     * @param type      订单类型
     * @param pageIndex 页码
     */
    @GET("driver/order/list.do?")
    Observable<BaseResponse<PageList<Order>>> getList(@Query("driverId") String driverId, @Query("type") int type, @Query("pageIndex") int pageIndex);

    /**
     * 获取工作单详情
     *
     * @param driverId 司机id
     * @param orderId  订单id
     */
    @GET("driver/order/getOrderDetail.do?")
    Observable<BaseResponse<DriverOrderDetailVo>> getOrderDetail(@Query("driverId") String driverId, @Query("orderId") String orderId);

    /**
     * 更新工作单状态
     *
     * @param driverId    司机id
     * @param orderId     订单id
     * @param operateType 1:接单 2:完成 3:关闭 4:出车
     * @param placeId     存放点id
     */
    @GET("driver/order/update.do?")
    Observable<BaseResponse<String>> update(@Query("driverId") String driverId, @Query("orderId") String orderId, @Query("operateType") String operateType, @Query("remark") String remark, @Query("placeId") String placeId);

    /**
     * 出车
     *
     * @param driverId              司机id
     * @param driverOrderNodeDetail 1:接单 2:完成 3:关闭 4:出车
     */
    @POST("/api/driverOrder/node/add.do?")
    Observable<BaseResponse<String>> outCar(@Query("driverId") String driverId, @Body DriverOrderNodeDetail driverOrderNodeDetail);

    /**
     * 运费统计
     *
     * @param driverId      司机id
     * @param startDate     开始时间
     * @param endDate       结束时间
     * @param companyName   公司名称
     * @param dateScopeType 0、全部，1、近一周，2、近一月，3、近三月（优先按此条件筛选时间范围）
     */
    @GET("driver/order/count.do?")
    Observable<BaseResponse<FreightStatistic>> count(@Query("driverId") String driverId, @Query("companyIds") String companyName, @Query("startDate") String startDate,
                                                     @Query("endDate") String endDate, @Query("dateScopeType") String dateScopeType);

    /**
     * 提交节点
     *
     * @param driverId              司机id
//     * @param orderSn               订单号
//     * @param nodeId                地址id
//     * @param nodeName              节点名称
//     * @param lon                   位置经度
//     * @param lat                   位置纬度
//     * @param address               详细地址
//     * @param contNoFirst           柜号1
//     * @param sealNoFirst           封号1
//     * @param contNoSecond          柜号2
//     * @param sealNoSecond          封号2
//     * @param remark                详情描述
//     * @param picture               图片url
//     * @param videoUrl              视频url
//     * @param audioUrl              音频url
//     * @param mapType               地图类型
//     * @param province              省
//     * @param city                  市
//     * @param district              区
//     * @param street                街道
     * @param driverOrderNodeDetail  实体
     */
    @POST("driverOrder/node/add.do?")
    Observable<BaseResponse<String>> add(@Body DriverOrderNodeDetail driverOrderNodeDetail, @Query("driverId") String driverId, @Query("controlSync") int controlSync);

    /**
     * 提交反馈
     */
    @POST("driverOrder/feedback/add.do?")
    Observable<BaseResponse<String>> feedbackAdd(@Body DriverOrderNodeFeedback driverOrderNodeFeedback);


    /**
     * 订单明细节点
     *
     * @param driverOrderId 订单id
     * @param orderSn 拖车单id(货代已经拆分出来)
     */
    @GET("driverOrder/node/list.do?")
    Observable<BaseResponse<OrderDetailNodeList>> OrderDetailNote(@Query("driverOrderId") String driverOrderId, @Query("orderSn") String orderSn);




    /**
     * 获取柜号封号数据
     *
     * @param id 订单id
     */
    @POST("driverOrder/node/edit.do?")
    Observable<BaseResponse<DriverOrderNodeDetailWrapper>> edit(@Query("nodeId") String id);

    /**
     * 切换节点
     *
     * @param id                节点id
     * @param switchToNodeId    要换的节点
     */
    @POST("driverOrder/node/switch.do?")
    Observable<BaseResponse<String>> switchToNode(@Query("nodeId") String id, @Query("switchToNodeId") String switchToNodeId);

    /**
     * 查看反馈
     *
     * @param orderId   订单id
     * @param driverId  司机id
     */
    @POST("driverOrder/feedback/list.do?")
    Observable<BaseResponse<List<DriverOrderNodeFeedback>>> feedbackList(@Query("orderId") String orderId, @Query("driverId") String driverId);

    /**
     * 查看反馈
     *
     * @param nodeId 节点id
     */
    @POST("driverOrder/node/detail.do?")
    Observable<BaseResponse<DriverOrderNodeFeedback>> orderNodeDetail(@Query("nodeId") String nodeId);

    /**
     * 订单轨迹经纬度
     * @param orderSn 订单id
     */
    @POST("driver/position/logs.do?")
    Observable<BaseResponse<List<LatLons>>> OrderLatLonHistory(@Query("orderSn")String orderSn);

    /**
     * 批量设置已读
     * @param ids 订单id 列表
     */
    @GET("driver/orderMsg/readBatch.do?")
    Observable<BaseResponse<String>> readBatch(@Query("ids")String ids);

}
