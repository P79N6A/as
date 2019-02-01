package com.yaoguang.datasource.api.driver;

import com.yaoguang.greendao.entity.InfoPutorderPlace;
import com.yaoguang.greendao.entity.driver.DriverEntrustCompany;
import com.yaoguang.greendao.entity.driver.DriverOrderDetailVo;
import com.yaoguang.greendao.entity.driver.DriverOrderDetailVoSec;
import com.yaoguang.greendao.entity.driver.DriverOrderWrapper;
import com.yaoguang.greendao.entity.driver.DriverOrderWrapperPage;
import com.yaoguang.lib.net.bean.BaseResponse;
import com.yaoguang.lib.net.bean.PageList;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by zhongjh on 2017/4/19.
 * 订单接口类
 */
public interface OrderApi {

    String ORDER = "driver/order/";

    /**
     * 订单明细
     *
     * @param orderId 订单id
     * @return 订单明细数据源
     */
    @GET(ORDER + "detail/v2.do?")
    Observable<BaseResponse<DriverOrderDetailVo>> detail2(@Query("orderId") String orderId);

    /**
     * 订单明细
     *
     * @param orderId 订单id
     * @return 订单明细数据源
     */
    @GET(ORDER + "detail/new.do?")
    Observable<BaseResponse<DriverOrderDetailVoSec>> detailnew(@Query("orderId") String orderId);

    /**
     * 获取订单中心列表
     *
     * @param driverId      司机id
     * @param companyIds    公司ids(id以逗号分隔)
     * @param startDate     起始日期
     * @param endDate       结束日期
     * @param dateScopeType 时间范围(0、自定义，1、近1周，2、近1月，3、近3月，4、近6月 5、近1年)
     * @param pageNo        页码
     * @return 数据源
     */
    @GET(ORDER + "count/v2.do?")
    Observable<BaseResponse<DriverOrderWrapperPage<PageList<DriverOrderWrapper>>>> count2(@Query("driverId") String driverId, @Query("companyIds") String companyIds,
                                                                                          @Query("startDate") String startDate, @Query("endDate") String endDate, @Query("dateScopeType") String dateScopeType,
                                                                                          @Query("pageNo") int pageNo);

    /**
     * 用户id
     *
     * @param userId 用户id
     * @return 数据源
     */
    @GET(ORDER + "companies.do?")
    Observable<BaseResponse<PageList<DriverEntrustCompany>>> companies(@Query("userId") String userId);

    /**
     * 获取订单中心列表
     *
     * @param driverId  司机id
     * @param type      订单类型
     * @param pageIndex 页码
     */
    @GET(ORDER + "list.do?")
    Observable<BaseResponse<PageList<DriverOrderWrapper>>> getList(@Query("driverId") String driverId, @Query("type") int type, @Query("pageIndex") int pageIndex);

    /**
     * 更新工作单状态
     *
     * @param driverId    司机id
     * @param orderId     订单id
     * @param operateType 1:接单 2:完成 3:关闭 4:出车
     * @param placeId     存放点id
     */
    @GET(ORDER + "update.do?")
    Observable<BaseResponse<String>> update(@Query("driverId") String driverId, @Query("orderId") String orderId, @Query("operateType") String operateType, @Query("remark") String remark, @Query("placeId") String placeId);

    /**
     * 获取订单存放地址列表
     *
     * @param id id
     */
    @GET(ORDER + "places.do?")
    Observable<BaseResponse<List<InfoPutorderPlace>>> places(@Query("id") String id);

}
