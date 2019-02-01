package com.yaoguang.datasource.api.company;

import com.yaoguang.greendao.entity.InfoVoyageTable;
import com.yaoguang.greendao.entity.InfoVoyageTableCondition;
import com.yaoguang.greendao.entity.common.ViewForwardOrderPack.InfoPackType;
import com.yaoguang.greendao.entity.company.InfoClientBookingconsignee;
import com.yaoguang.greendao.entity.company.InfoPaymentMethod;
import com.yaoguang.greendao.entity.company.InfoSendOrderTemp;
import com.yaoguang.lib.net.bean.BaseResponse;
import com.yaoguang.lib.net.bean.PageList;
import com.yaoguang.greendao.entity.AppPublicInfoWrapper;
import com.yaoguang.greendao.entity.company.InfoClientPlace;
import com.yaoguang.greendao.entity.InfoContType;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * 业务下单
 * Created by zhongjh on 2017/6/5.
 */
public interface CompanyBaseInfoApi {

    String COMPANYBASEINFO = "company/baseInfo";

    /**
     * 选择托运人列表
     *
     * @param userId    用户id
     * @param name      名称
     * @param pageIndex 页码
     * @param type      公司类型 （托运人-""，货代公司-icm.IS_FORWARD，拖车公司-icm.IS_TRUCK，船公司-icm.IS_SHIP_COMPANY）
     * @return 数据源
     */
    @GET(COMPANYBASEINFO + "/getAllCompany.do?")
    @Headers("Cache-Control: public, max-age=60")
    Observable<BaseResponse<PageList<AppPublicInfoWrapper>>> getAllCompany(@Query("userId") String userId, @Query("name") String name, @Query("pageIndex") int pageIndex, @Query("type") String type);

    /**
     * 选择业务员列表
     *
     * @param userId    用户id
     * @param name      名称
     * @param pageIndex 页码
     * @return 列表数据
     */
    @POST(COMPANYBASEINFO + "/getEmployees.do?")
    Observable<BaseResponse<PageList<AppPublicInfoWrapper>>> getEmployees(@Query("userId") String userId, @Query("name") String name, @Query("pageIndex") int pageIndex);

    /**
     * 业务下单-选择托运人列表
     *
     * @param userId    用户id
     * @param name      名称
     * @param pageIndex 页码
     * @param type      公司类型 （托运人-null，拖车公司-IS_TRUCK，船公司-IS_SHIP_COMPANY）
     * @return 数据源
     */
    @GET(COMPANYBASEINFO + "/getCompany.do?")
    @Headers("Cache-Control: public, max-age=60")
    Observable<BaseResponse<PageList<AppPublicInfoWrapper>>> getCompany(@Query("userId") String userId, @Query("name") String name, @Query("pageIndex") int pageIndex, @Query("type") String type);

    /**
     * 选择起运地、目的地列表
     *
     * @param userId    用户id
     * @param name      名称
     * @param pageIndex 页码
     * @return 数据源
     */
    @GET(COMPANYBASEINFO + "/selectAllDock.do?")
    @Headers("Cache-Control: public, max-age=60")
    Observable<BaseResponse<PageList<AppPublicInfoWrapper>>> selectAllDock(@Query("userId") String userId, @Query("name") String name, @Query("pageIndex") int pageIndex);

    /**
     * 业务下单-选择起运地、目的地列表
     *
     * @param userId    用户id
     * @param name      名称
     * @param pageIndex 页码
     * @return 数据源
     */
    @GET(COMPANYBASEINFO + "/selectDock.do?")
    @Headers("Cache-Control: public, max-age=60")
    Observable<BaseResponse<PageList<AppPublicInfoWrapper>>> selectDock(@Query("userId") String userId, @Query("name") String name, @Query("pageIndex") int pageIndex);

    /**
     * 业务下单-选择货物名称
     *
     * @param userId    用户id
     * @param name      名称
     * @param pageIndex 页码
     * @return 数据源
     */
    @GET(COMPANYBASEINFO + "/selectGood.do?")
    @Headers("Cache-Control: public, max-age=60")
    Observable<BaseResponse<PageList<AppPublicInfoWrapper>>> selectGood(@Query("userId") String userId, @Query("name") String name, @Query("pageIndex") int pageIndex);

    /**
     * 业务下单-选择操作、运输条款
     *
     * @param pageIndex 页码
     * @return 数据源
     */
    @GET(COMPANYBASEINFO + "/selectTraffic.do?")
    @Headers("Cache-Control: public, max-age=60")
    Observable<BaseResponse<PageList<AppPublicInfoWrapper>>> selectTraffic(@Query("pageIndex") int pageIndex);

    /**
     * 选择起始港、目的港
     *
     * @param userId    用户id
     * @param name      名称
     * @param pageIndex 页码
     * @param type      类型 起运港workbills_portLoading  目的港workbills_portDelivery
     * @return 数据源
     */
    @GET(COMPANYBASEINFO + "/selectPort.do?")
    @Headers("Cache-Control: public, max-age=60")
    Observable<BaseResponse<PageList<AppPublicInfoWrapper>>> selectPort(@Query("userId") String userId, @Query("name") String name, @Query("pageIndex") int pageIndex, @Query("type") String type);

    /**
     * 选择起始港、目的港 不需要type的
     *
     * @param userId    用户id
     * @param name      名称
     * @param pageIndex 页码
     * @return 数据源
     */
    @GET(COMPANYBASEINFO + "/selectPort.do?")
    @Headers("Cache-Control: public, max-age=60")
    Observable<BaseResponse<PageList<AppPublicInfoWrapper>>> selectPort(@Query("userId") String userId, @Query("name") String name, @Query("pageIndex") int pageIndex);

    /**
     * 选择码头
     *
     * @param userId    用户id
     * @param name      名称
     * @param pageIndex 页码
     * @return
     */
    @GET(COMPANYBASEINFO + "/selectInfoDock.do?")
    @Headers("Cache-Control: selectInfoDock, max-age=60")
    Observable<BaseResponse<PageList<AppPublicInfoWrapper>>> selectInfoDock(@Query("userId") String userId, @Query("name") String name, @Query("pageIndex") int pageIndex);

    /**
     * 装卸货信息
     *
     * @param codeId    托运人id
     * @param name      搜索名称
     * @param areaName  装卸货区域名称
     * @param pageIndex 页码
     */
    @GET(COMPANYBASEINFO + "/selectLoadPlaces.do?")
    Observable<BaseResponse<PageList<AppPublicInfoWrapper>>> selectLoadPlaces(@Query("codeId") String codeId, @Query("name") String name, @Query("areaName") String areaName, @Query("pageIndex") int pageIndex);


    /**
     * 运单号
     *
     * @param name      查询内容
     * @param pageIndex 页数
     * @param type      物流为0 货主为1
     */
    @GET(COMPANYBASEINFO + "/getMBlNos.do?")
    Observable<BaseResponse<PageList<AppPublicInfoWrapper>>> getMBlNos(@Query("name") String name, @Query("type") int type, @Query("userId") String userId, @Query("pageIndex") int pageIndex);

    /**
     * 柜号
     *
     * @param name      查询内容
     * @param pageIndex 页数
     * @param type      物流为0 货主为1
     */
    @GET(COMPANYBASEINFO + "/getContNos.do?")
    Observable<BaseResponse<PageList<AppPublicInfoWrapper>>> getContNos(@Query("name") String name, @Query("type") int type, @Query("userId") String userId, @Query("pageIndex") int pageIndex);

    /**
     * 工作单号
     *
     * @param name      查询内容
     * @param pageIndex 页数
     */
    @GET(COMPANYBASEINFO + "/getOrderSns.do?")
    Observable<BaseResponse<PageList<AppPublicInfoWrapper>>> getOrderSns(@Query("name") String name, @Query("userId") String userId, @Query("pageIndex") int pageIndex);

    /**
     * 查询柜型，缓存一天
     */
    @POST(COMPANYBASEINFO + "/getConts.do?")
    @Headers("Cache-Control: public, max-age=86400")
    Observable<BaseResponse<List<InfoContType>>> getConts();

    /**
     * 查询包装类型
     */
    @POST(COMPANYBASEINFO + "/packType.do?")
    @Headers("Cache-Control: public, max-age=86400")
    Observable<BaseResponse<List<InfoPackType>>> packType();

    /**
     * 查询柜型柜量，缓存一天
     */
    @POST(COMPANYBASEINFO + "/getContForSearch.do?")
    @Headers("Cache-Control: public, max-age=86400")
    Observable<BaseResponse<List<String>>> getContForSearch();

    /**
     * 添加/修改 装卸货地址
     *
     * @param infoClientPlace 装卸货地址
     * @param userId          用户id
     * @return 返回的信息文本
     */
    @POST(COMPANYBASEINFO + "/addLoadPlace.do?")
    Observable<BaseResponse<String>> addLoadPlace(@Body InfoClientPlace infoClientPlace, @Query("userId") String userId);

    /**
     * 批量删除装卸货地址
     *
     * @param ids    逗号拼接
     * @param codeId 托运人id
     * @param userId 用户id
     * @return 返回的信息文本
     */
    @POST(COMPANYBASEINFO + "/batchDeletePlace.do?")
    Observable<BaseResponse<String>> batchDeletePlace(@Query("ids") String ids, @Query("codeId") String codeId, @Query("userId") String userId);

    /**
     * 船期查询
     *
     * @param infoVoyageTableCondition 条件实体
     * @param userId                   用户id
     * @param pageIndex                页码
     * @return 返回數據源
     */
    @POST(COMPANYBASEINFO + "/getInfoVoyageTables.do?")
    @Headers("Cache-Control: public, max-age=60")
    Observable<BaseResponse<PageList<InfoVoyageTable>>> getInfoVoyageTables(@Body InfoVoyageTableCondition infoVoyageTableCondition, @Query("userId") String userId, @Query("pageIndex") int pageIndex);

    /**
     * 业务下单 - 收款方式
     */
    @GET(COMPANYBASEINFO + "/searchPaymentMethod.do?")
    @Headers("Cache-Control: public, max-age=60")
    Observable<BaseResponse<List<InfoPaymentMethod>>> searchPaymentMethod();

    /**
     * 业务下单 - 干线船名
     *
     * @param name      查询内容
     * @param pageIndex 页数
     */
    @GET(COMPANYBASEINFO + "/searchUserInfoShips.do?")
    @Headers("Cache-Control: public, max-age=60")
    Observable<BaseResponse<PageList<AppPublicInfoWrapper>>> searchUserInfoShips(@Query("name") String name, @Query("pageIndex") int pageIndex);

    /**
     * 费用项目
     * @param name 查询内容
     * @param pageIndex 页数
     */
    @GET(COMPANYBASEINFO + "/feeTypes.do?")
    @Headers("Cache-Control: public, max-age=60")
    Observable<BaseResponse<PageList<AppPublicInfoWrapper>>> feeTypes(@Query("name") String name, @Query("pageIndex") int pageIndex);

    /**
     * 按托运人及起运地（目的地）获取装卸货地址
     *
     * @param codeId    托运人
     * @param areaName  起运地（目的地）
     * @param name 查询名称
     * @param pageIndex 页数
     */
    @GET(COMPANYBASEINFO + "/selectInfoPlaces.do?")
    @Headers("Cache-Control: public, max-age=60")
    Observable<BaseResponse<PageList<InfoClientPlace>>> selectInfoPlaces(@Query("codeId") String codeId, @Query("areaName") String areaName,@Query("name") String name, @Query("pageIndex") int pageIndex);

    /**
     * 按目的拖车查询订舱收货人
     * @param destTruck 目的拖车
     */
    @POST(COMPANYBASEINFO + "/searchInfoClientBookingconsignees.do?")
    @Headers("Cache-Control: public, max-age=60")
    Observable<BaseResponse<List<InfoClientBookingconsignee>>> searchInfoClientBookingconsignees(@Query("destTruck") String destTruck);

    /**
     * 派单模板列表
     * @param type 0：货代 1：装货 2：送货 3：拖车
     */
    @POST(COMPANYBASEINFO + "/transferTemp/list.do?")
    @Headers("Cache-Control: public, max-age=60")
    Observable<BaseResponse<List<InfoSendOrderTemp>>> transferTempList(@Query("type") String type);

    /**
     * 查找车牌号
     * @param name 查询内容
     * @param pageIndex 页数
     */
    @GET(COMPANYBASEINFO + "/searchInfoTruckNos.do?")
    @Headers("Cache-Control: public, max-age=60")
    Observable<BaseResponse<PageList<AppPublicInfoWrapper>>> searchInfoTruckNos(@Query("name") String name, @Query("pageIndex") int pageIndex);

    /**
     * 选择司机
     * @param name 查询内容
     * @param pageIndex 页数
     */
    @GET(COMPANYBASEINFO + "/searchInfoHackmans.do?")
    @Headers("Cache-Control: public, max-age=60")
    Observable<BaseResponse<PageList<AppPublicInfoWrapper>>> searchInfoHackmans(@Query("name") String name, @Query("pageIndex") int pageIndex);


}
