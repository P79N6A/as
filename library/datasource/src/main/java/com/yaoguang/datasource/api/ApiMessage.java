package com.yaoguang.datasource.api;

import com.yaoguang.greendao.entity.common.SysMsg;
import com.yaoguang.lib.net.bean.BaseResponse;
import com.yaoguang.lib.net.bean.PageList;
import com.yaoguang.greendao.entity.DriverOrderMsg;
import com.yaoguang.greendao.entity.driver.UnreadNum;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * 消息接口
 * Created by wly on 2017/4/25 0025.
 */

public interface ApiMessage {

    /**
     * 获取平台公告列表
     */
    @GET("sysMsg/list.do?")
    Observable<BaseResponse<PageList<SysMsg>>> platformMessageList(@Query("userId") String userId, @Query("pageIndex") int pageIndex, @Query("platformType") int platformType, @Query("noticeType") int noticeType);

    /**
     * 删除平台公告
     */
    @GET("sysMsg/delete.do?")
    Observable<BaseResponse<String>> deletePlatformMessage(@Query("userId") String userId, @Query("sysMsgIds") String sysMsgId);

    /**
     * 将平台消息设为已读  userId
     */
    @GET("sysMsg/readBatch.do?")
    Observable<BaseResponse<String>> setReadPlatformMessage(@Query("userId") String userId, @Query("sysMsgIds") String sysMsgId);

    /**
     * 获取订单中心列表
     *
     * @param driverId  司机id
     * @param pageIndex 页码
     */
    @GET("driver/orderMsg/list.do?")
    Observable<BaseResponse<PageList<DriverOrderMsg>>> getMessageOrderList(@Query("driverId") String driverId, @Query("pageIndex") int pageIndex);

    /**
     * 获取订单信息列表
     * 由于旧的未实现  Observable<BaseResponse<Pages<List<DriverOrderMsg>>>>
     * 所以先用旧的 Call<ResponseBody>，晚点再改
     *
     * @param driverId  司机id
     * @param pageIndex 页码
     * @param type      页码 type(0：业务消息 1：平台公告)
     */
    @GET("driver/index/msgList.do?")
    Observable<BaseResponse<PageList<SysMsg>>> getHomeMessageList_tmp(@Query("driverId") String driverId, @Query("pageIndex") String pageIndex, @Query("type") String type, @Query("noticeType") String noticeType);

    /**
     * 获取订单信息列表
     *
     * @param driverId  司机id
     * @param pageIndex 页码
     * @param type      页码 type(0：业务消息 1：平台公告)
     * @param flag      // 0：未读 1：已读
     */
    @GET("driver/orderMsg/list.do?")
    Observable<BaseResponse<PageList<DriverOrderMsg>>> getHomeMessageList(@Query("driverId") String driverId, @Query("pageIndex") int pageIndex, @Query("type") String type, @Query("noticeType") String noticeType, @Query("flag") int flag);

    /**
     * 删除订单消息
     *
     * @param ids          消息id
     * @param platformType 平台类型
     */
    @GET("driver/orderMsg/delete.do?")
    Observable<BaseResponse<String>> orderMessageDeleted(@Query("ids") String ids, @Query("platformType") int platformType);

    /**
     * 获取未读数量
     */
    @GET("driver/index/msgNumber.do?")
    Observable<BaseResponse<UnreadNum>> getUnreadNum(@Query("driverId") String driverId, @Query("platformType") int platformType);

    /**
     * 忽略 和 忽略所有
     * type 1 ：业务消息 2：平台公告
     */
    @GET("driver/index/read.do?")
    Observable<BaseResponse<String>> ignoreAll(@Query("driverId") String driverId, @Query("type") String type, @Query("ids") String ids);


    /**
     * 批量设置已读
     *
     * @param ids 订单id 列表
     */
    @GET("driver/orderMsg/readBatch.do?")
    Observable<BaseResponse<String>> readBatch(@Query("ids") String ids);
}
