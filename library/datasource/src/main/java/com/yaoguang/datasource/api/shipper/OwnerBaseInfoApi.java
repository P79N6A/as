package com.yaoguang.datasource.api.shipper;

import com.yaoguang.greendao.entity.AppPublicInfoWrapper;
import com.yaoguang.greendao.entity.InfoContType;
import com.yaoguang.greendao.entity.InfoVoyageTable;
import com.yaoguang.greendao.entity.InfoVoyageTableCondition;
import com.yaoguang.lib.net.bean.BaseResponse;
import com.yaoguang.lib.net.bean.PageList;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by zhongjh on 2018/8/24.
 */
public interface OwnerBaseInfoApi {

    String OWNERBASEINFO = "owner/baseInfo";


    /**
     * 船期查询
     *
     * @param condition 条件
     * @param userId    用户id
     * @param pageIndex 页码
     * @return 返回列表
     */
    @POST(OWNERBASEINFO + "/getInfoVoyageTables.do?")
    @Headers("Cache-Control: public, max-age=60")
    Observable<BaseResponse<PageList<InfoVoyageTable>>> getInfoVoyageTables(@Body InfoVoyageTableCondition condition, @Query("userId") String userId, @Query("pageIndex") int pageIndex);

    /**
     * 关注的公司
     *
     * @param userId    托运人id
     * @param name      搜索名称
     * @param pageIndex 页码
     */
    @GET(OWNERBASEINFO + "/getContactCompany.do?")
    Observable<BaseResponse<PageList<AppPublicInfoWrapper>>> getContactCompany(@Query("userId") String userId, @Query("name") String name, @Query("pageIndex") int pageIndex);

    /**
     * 查询柜型，缓存一天
     */
    @POST(OWNERBASEINFO + "/getConts.do?")
    @Headers("Cache-Control: public, max-age=86400")
    Observable<BaseResponse<List<InfoContType>>> getConts();

    /**
     * 查询柜型柜量，缓存一天
     */
    @POST(OWNERBASEINFO + "/getContForSearch.do?")
    @Headers("Cache-Control: public, max-age=86400")
    Observable<BaseResponse<List<String>>> getContForSearch();

    /**
     * 选择起始港、目的港 货主端的
     *
     * @param userId    用户id
     * @param name      名称
     * @param pageIndex 页码
     * @return
     */
    @GET(OWNERBASEINFO + "/selectAllPort.do?")
    @Headers("Cache-Control: public, max-age=60")
    Observable<BaseResponse<PageList<AppPublicInfoWrapper>>> selectAllPort(@Query("userId") String userId, @Query("name") String name, @Query("pageIndex") int pageIndex);

    /**
     * 选择起运地、目的地列表（供查询）
     *
     * @param userId    用户id
     * @param name      名称
     * @param pageIndex 页码
     * @return 数据源
     */
    @GET(OWNERBASEINFO + "/selectAllCity.do?")
    @Headers("Cache-Control: public, max-age=60")
    Observable<BaseResponse<PageList<AppPublicInfoWrapper>>> selectAllCity(@Query("userId") String userId, @Query("name") String name, @Query("pageIndex") int pageIndex);

    /**
     * 选择托运人列表 货主端的
     *
     * @param userId    用户id
     * @param companyId 公司id
     * @param name      名称
     * @param pageIndex 页码
     * @param type      公司类型 （托运人-null，拖车公司-IS_TRUCK，船公司-IS_SHIP_COMPANY，保险公司-IS_INSURER）
     * @return 数据源
     */
    @GET(OWNERBASEINFO + "/getCompany.do?")
    @Headers("Cache-Control: public, max-age=60")
    Observable<BaseResponse<PageList<AppPublicInfoWrapper>>> getCompanyShipper(@Query("userId") String userId,@Query("companyId") String companyId, @Query("name") String name, @Query("pageIndex") int pageIndex, @Query("type") String type);

    /**
     * 业务下单-选择货物名称
     *
     * @param companyId 关联公司id
     * @param name      名称
     * @param pageIndex 页码
     * @return 数据源
     */
    @GET(OWNERBASEINFO + "/selectGood.do?")
    @Headers("Cache-Control: public, max-age=60")
    Observable<BaseResponse<PageList<AppPublicInfoWrapper>>> selectGood(@Query("companyId") String companyId, @Query("name") String name, @Query("pageIndex") int pageIndex);

    /**
     * 业务下单-选择操作、运输条款
     *
     * @param name      名称
     * @param pageIndex 页码
     * @return 数据源
     */
    @GET(OWNERBASEINFO + "/selectTraffic.do?")
    @Headers("Cache-Control: public, max-age=60")
    Observable<BaseResponse<PageList<AppPublicInfoWrapper>>> selectTraffic(@Query("name") String name, @Query("pageIndex") int pageIndex);

    /**
     * 业务下单-选择起运地、目的地列表
     *
     * @param companyId 关联公司id
     * @param name      名称
     * @param pageIndex 页码
     * @return 数据源
     */
    @GET(OWNERBASEINFO + "/selectDock.do?")
    @Headers("Cache-Control: public, max-age=60")
    Observable<BaseResponse<PageList<AppPublicInfoWrapper>>> selectDock(@Query("companyId") String companyId, @Query("name") String name, @Query("pageIndex") int pageIndex);

    /**
     * 选择起始港、目的港 货主端的
     *
     * @param companyId 关联公司id
     * @param name      名称
     * @param pageIndex 页码
     * @return
     */
    @GET(OWNERBASEINFO + "/selectPort.do?")
    @Headers("Cache-Control: public, max-age=60")
    Observable<BaseResponse<PageList<AppPublicInfoWrapper>>> selectPortShipper(@Query("companyId") String companyId, @Query("name") String name, @Query("pageIndex") int pageIndex);

    /**
     * 选择码头
     *
     * @param companyId 关联公司id
     * @param name      名称
     * @param pageIndex 页码
     * @return
     */
    @GET(OWNERBASEINFO + "/selectInfoDock.do?")
    @Headers("Cache-Control: selectInfoDock, max-age=60")
    Observable<BaseResponse<PageList<AppPublicInfoWrapper>>> selectInfoDock(@Query("companyId") String companyId, @Query("name") String name, @Query("pageIndex") int pageIndex);

    /**
     * 运单号 - 工作单查询条件获取运单号
     *
     * @param companyId 关联公司id
     * @param name      查询内容
     * @param pageIndex 页数
     */
    @GET(OWNERBASEINFO + "/getMBlNos.do?")
    Observable<BaseResponse<PageList<AppPublicInfoWrapper>>> getMBlNos(@Query("name") String name, @Query("companyId") String companyId, @Query("pageIndex") int pageIndex);

    /**
     * 柜号
     *
     * @param companyId 关联公司id
     * @param name      查询内容
     * @param pageIndex 页数
     */
    @GET(OWNERBASEINFO + "/getContNos.do?")
    Observable<BaseResponse<PageList<AppPublicInfoWrapper>>> getContNos(@Query("name") String name, @Query("companyId") String companyId, @Query("pageIndex") int pageIndex);

    /**
     * 工作单号
     *
     * @param companyId 关联公司id
     * @param name      查询内容
     * @param pageIndex 页数
     */
    @GET(OWNERBASEINFO + "/getOrderSns.do?")
    Observable<BaseResponse<PageList<AppPublicInfoWrapper>>> getOrderSns(@Query("name") String name, @Query("companyId") String companyId, @Query("pageIndex") int pageIndex);

    /**
     * 货柜跟踪获取运单号
     *
     * @param name      查询内容
     * @param pageIndex 页数
     */
    @GET(OWNERBASEINFO + "/getSonoMBlNos.do?")
    Observable<BaseResponse<PageList<AppPublicInfoWrapper>>> getSonoMBlNos(@Query("name") String name,  @Query("pageIndex") int pageIndex);

    /**
     * 货柜跟踪获取柜号
     *
     * @param name      查询内容
     * @param pageIndex 页数
     */
    @GET(OWNERBASEINFO + "/getSonoContNos.do?")
    Observable<BaseResponse<PageList<AppPublicInfoWrapper>>> getSonoContNos(@Query("name") String name,  @Query("pageIndex") int pageIndex);

}
