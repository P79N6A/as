package com.yaoguang.datasource.api;

import com.yaoguang.lib.net.bean.BaseResponse;
import com.yaoguang.lib.net.bean.PageList;
import com.yaoguang.greendao.entity.DriverOrderMsg;
import com.yaoguang.greendao.entity.driver.UnreadNum;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Administrator on 2017/4/25 0025.
 */

public interface MessageApi {
    String MESSAGE = "userMsg";

    String MESSAGEDETAIL = "page/msg/msg_detail.jsp?id=";

    /**
     * 消息管理
     * 获取消息中心数据
     *
     * @param userId
     * @param pageIndex
     * @param noticeType
     * @return
     */
    @GET(MESSAGE + "/list.do?")
    Call<ResponseBody> messageCenterList(@Query("userId") String userId, @Query("pageIndex") int pageIndex, @Query("platformType") int platformType, @Query("noticeType") String noticeType);


    /**
     * 消息管理
     * 删除消息
     *
     * @param id 消息id
     * @return
     */
    @GET(MESSAGE + "/delete.do?")
    Call<ResponseBody> messageCenterDeleted(@Query("id") String id, @Query("platformType") int platformType);

    /**
     * 消息管理
     * 设置已读
     *
     * @param id 消息id
     * @return
     */
    @GET(MESSAGE + "/update.do?")
    Call<ResponseBody> messageCenterReadState(@Query("id") String id, @Query("platformType") int platformType);

    /**
     * 获取订单中心列表
     *
     * @param driverId  司机id
     * @param pageIndex 页码
     */
    @GET("driver/orderMsg/list.do?")
    Observable<BaseResponse<PageList<DriverOrderMsg>>> getMessageOrderList(@Query("driverId") String driverId, @Query("pageIndex") String pageIndex);

    /**
     * 获取订单信息列表
     *
     * @param driverId  司机id
     * @param pageIndex 页码
     * @param type      页码 type(0：业务消息 1：平台公告)
     */
    @GET("driver/index/msgList.do?")
    Observable<BaseResponse<PageList<DriverOrderMsg>>> getHomeMessageList(@Query("driverId") String driverId, @Query("pageIndex") String pageIndex, @Query("type") String type, @Query("noticeType") String noticeType);

    /**
     * 删除订单消息
     *
     * @param ids 消息id
     * @return
     */
    @GET("driver/orderMsg/delete.do?")
    Observable<BaseResponse<String>> orderMessageDeleted(@Query("ids") String ids, @Query("platformType") int platformType);

    /**
     * 获取未读数量
     *
     * @return
     */
    @GET("driver/index/msgNumber.do?")
    Observable<BaseResponse<UnreadNum>> getUnreadNum(@Query("driverId") String driverId, @Query("platformType") int platformType);

    /**
     * 忽略 和 忽略所有
     * <p>
     * type 1 ：业务消息 2：平台公告
     *
     * @return
     */
    @GET("driver/index/read.do?")
    Observable<BaseResponse<String>> ignoreAll(@Query("driverId") String driverId, @Query("type") String type, @Query("ids") String ids);
}
