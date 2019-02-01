package com.yaoguang.datasource.api.common;

import com.yaoguang.greendao.entity.common.SysMsg;
import com.yaoguang.lib.net.bean.BaseResponse;
import com.yaoguang.lib.net.bean.PageList;

import java.util.HashMap;
import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;

/**
 * 公告和私人消息
 * Created by zhongjh on 2018/5/16.
 */

public interface SysMsgApi {

    String SYS_MSG = "sysMsg/";

    /**
     * @param platformType 0pc 1司机 2供应链 3货主
     * @param noticeType   0是公告 1是消息
     */
    @GET(SYS_MSG + "list.do?")
    @Headers("Cache-Control: public, max-age=60")
    Observable<BaseResponse<PageList<SysMsg>>> list(@Query("platformType") String platformType, @Query("userId") String userId, @Query("pageIndex") int pageIndex, @Query("noticeType") int noticeType);

    /**
     * @param platformType 0pc 1司机 2供应链 3货主
     * @param noticeType   0是公告 1是消息
     */
    @GET(SYS_MSG + "indexList.do?")
    @Headers("Cache-Control: public, max-age=60")
    Observable<BaseResponse<List<SysMsg>>> indexList(@Query("platformType") String platformType, @Query("pageIndex") int pageIndex, @Query("noticeType") int noticeType);

    /**
     * 删除平台公告
     */
    @GET(SYS_MSG + "delete.do?")
    Observable<BaseResponse<String>> delete(@Query("userId") String userId, @Query("sysMsgIds") String sysMsgId);

    /**
     * 将平台消息设为已读  userId
     */
    @GET(SYS_MSG + "readBatch.do?")
    Observable<BaseResponse<String>> readBatch(@Query("userId") String userId, @Query("sysMsgIds") String sysMsgId);

    /**
     * 获取未读消息
     *
     * @param userId 用户id
     * @param type   1司机 2供应链 3货主
     * @return unReadSystemNumber unReadCompanyNumber
     */
    @GET(SYS_MSG + "unReadNumber.do?")
    Observable<BaseResponse<HashMap<String, Integer>>> unReadNumber(@Query("userId") String userId, @Query("type") String type);

    /**
     * 获取弹窗消息
     *
     * @param userId 用户id
     * @param type   1司机 2供应链 3货主
     */
    @GET(SYS_MSG + "getPopUpMsg.do?")
    Observable<BaseResponse<List<SysMsg>>> getPopUpMsg(@Query("userId") String userId, @Query("plateformType") String type);

    /**
     * 设置已弹窗
     *
     * @param userId 用户id
     * @param msgId  信息id
     */
    @GET(SYS_MSG + "updatePopUp.do?")
    Observable<BaseResponse<String>> updatePopUp(@Query("userId") String userId, @Query("msgId") String msgId);

}
