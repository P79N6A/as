package com.yaoguang.datasource.api.common;

import com.yaoguang.greendao.entity.common.UserOffice;
import com.yaoguang.greendao.entity.common.DriverContactCompany;
import com.yaoguang.greendao.entity.common.DriverFollowCompany;
import com.yaoguang.greendao.entity.common.UserOfficeWrapper;
import com.yaoguang.lib.net.bean.BaseResponse;
import com.yaoguang.lib.net.bean.PageList;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * 关注
 * Created by zhongjh on 2018/4/12.
 */

public interface ContactApi {

    String contact = "contact";

    /**
     * @param type      关注类型(0:司机端 1：物流端 2：货主端)
     * @param userId    用户id
     * @param companyId 公司id
     * @return 关注数据
     */
    @POST(contact + "/add.do?")
    Observable<BaseResponse<String>> contact(@Query("type") String type, @Query("userId") String userId, @Query("companyId") String companyId);

    /**
     * 已经关注成功的公司信息列表
     *
     * @param userId    用户id
     * @param type      关注类型(0:司机端 1：物流端 2：货主端)
     * @param pageIndex 页码
     * @return 列表数据
     */
    @POST(contact + "/pass/listVersion2.do?")
    Observable<BaseResponse<PageList<DriverContactCompany>>> getPassListVersion2(@Query("type") String type, @Query("userId") String userId, @Query("pageIndex") int pageIndex);

    /**
     * 已经关注成功的公司信息列表,获取所有
     *
     * @param userId    用户id
     * @param type      关注类型(0:司机端 1：物流端 2：货主端)
     * @param pageIndex 页码
     * @return 列表数据
     */
    @POST(contact + "/pass/listVersion2.do?")
    Observable<BaseResponse<PageList<DriverContactCompany>>> getPassListVersion2All(@Query("type") String type, @Query("userId") String userId, @Query("pageIndex") int pageIndex, @Query("pageCount") int pageCount);

    /**
     * 删除关注
     *
     * @param type      关注类型(0:司机端 1：物流端 2：货主端)
     * @param contactId 关注id
     * @param userId    用户id
     * @return
     */
    @POST(contact + "/delete.do?")
    Observable<BaseResponse<String>> delete(@Query("type") String type, @Query("contactId") String contactId, @Query("userId") String userId);

    /**
     * 审核通过
     *
     * @param contactId 关注的id
     * @param userId    用户id
     * @return 返回消息
     */
    @POST(contact + "/passAduit.do?")
    Observable<BaseResponse<String>> passAduit(@Query("contactId") String contactId, @Query("userId") String userId);

    /**
     * 忽略申请信息
     *
     * @param contactId 关注的id
     * @param userId    用户id
     * @return 返回消息
     */
    @POST(contact + "/ignoreAduit.do?")
    Observable<BaseResponse<String>> ignoreAduit(@Query("contactId") String contactId, @Query("userId") String userId);

    /**
     * 新的关联-获取关联消息列表
     *
     * @param type      关注类型(0:司机端 1：物流端 2：货主端)
     * @param userId    用户id
     * @param name      名称
     * @param auditFlag 状态（0-未同意，1-已关联，2-已拒绝）
     * @param pageIndex 页码
     * @return 数据源
     */
    @POST(contact + "/getApplyInfo.do?")
    Observable<BaseResponse<PageList<DriverFollowCompany>>> getApplyInfo(@Query("type") String type, @Query("userId") String userId, @Query("name") String name, @Query("auditFlag") String auditFlag, @Query("pageIndex") int pageIndex);

    /**
     * 获取公司信息
     *
     * @param userId    用户id
     * @param companyId 公司id
     * @param type      0 司机 1物流 2 货主
     * @return 返回登录对象
     */
    @GET("contact/getCompanyInfo.do?")
    Observable<BaseResponse<UserOfficeWrapper>> getCompanyInfo(@Query("userId") String userId, @Query("companyId") String companyId, @Query("type") String type);


}
