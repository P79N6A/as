package com.yaoguang.datasource.api.company;

import com.yaoguang.greendao.entity.common.ViewForwardOrder;
import com.yaoguang.greendao.entity.common.ViewForwardOrderPack.AccountFee;
import com.yaoguang.greendao.entity.common.ViewForwardOrderPack.OldNewAccountFeeWrapper;
import com.yaoguang.greendao.entity.company.AppBusinessOrderListCondition;
import com.yaoguang.greendao.entity.company.AppCompanyOrderCondition;
import com.yaoguang.greendao.entity.company.AppCompanyOrder;
import com.yaoguang.greendao.entity.company.AppCompanyOrderTemplate;
import com.yaoguang.greendao.entity.company.AppViewForwardOrderWrapper;
import com.yaoguang.greendao.entity.company.RecordUpdate;
import com.yaoguang.greendao.entity.company.UpdateBusinessOrderModel;
import com.yaoguang.greendao.entity.company.WebOrderTemplateWrapper;
import com.yaoguang.lib.net.bean.BaseResponse;
import com.yaoguang.lib.net.bean.PageList;
import com.yaoguang.greendao.entity.common.ViewForwardOrderPack.FreightBills;
import com.yaoguang.greendao.entity.SysCondition;
import com.yaoguang.greendao.entity.SysConditionWrapper;
import com.yaoguang.greendao.entity.common.TruckBills;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * 货代订单
 * Created by zhongjh on 2017/6/16.
 */
public interface CompanyOrderApi {

    String COMPANY_ORDER = "company/order";
    String COMPANY_ORDER2 = "company/order2";

    /**
     * 货代工作单查询
     *
     * @param condition 条件实体
     * @param userId    用户id
     * @param pageIndex 页码
     * @return 返回数据源
     */
    @POST(COMPANY_ORDER + "/getForwardOrders.do?")
    @Headers("Cache-Control: public, max-age=60")
    Observable<BaseResponse<PageList<FreightBills>>> getForwardOrders(@Body SysConditionWrapper condition, @Query("userId") String userId, @Query("pageIndex") int pageIndex);

    /**
     * 货代工作单查询
     *
     * @param condition 条件实体
     * @param userId    用户id
     * @param pageIndex 页码
     * @return 返回数据源
     */
    @POST(COMPANY_ORDER2 + "/getForwardOrders.do?")
    @Headers("Cache-Control: public, max-age=60")
    Observable<BaseResponse<PageList<FreightBills>>> getForwardOrders2(@Body SysConditionWrapper condition, @Query("userId") String userId, @Query("pageIndex") int pageIndex);

    /**
     * 货代工作单明细
     *
     * @param userId 用户id
     * @param billId 工作单主键id
     * @return 返回货代工作单明细
     */
    @POST(COMPANY_ORDER + "/getForwardOrderById.do?")
    @Headers("Cache-Control: public, max-age=60")
    Observable<BaseResponse<ViewForwardOrder>> getForwardOrderById(@Query("userId") String userId, @Query("billId") String billId);

    /**
     * 拖车工作单查询
     *
     * @param condition 条件实体
     * @param userId    用户id
     * @param pageIndex 页码
     * @return 返回数据源
     */
    @POST(COMPANY_ORDER + "/getTruckOrders.do?")
    @Headers("Cache-Control: public, max-age=60")
    Observable<BaseResponse<PageList<TruckBills>>> getTruckOrders(@Body SysConditionWrapper condition, @Query("userId") String userId, @Query("pageIndex") int pageIndex);

    /**
     * 拖车工作单查询
     *
     * @param condition 条件实体
     * @param userId    用户id
     * @param pageIndex 页码
     * @return 返回数据源
     */
    @POST(COMPANY_ORDER2 + "/truck/list.do?")
    @Headers("Cache-Control: public, max-age=60")
    Observable<BaseResponse<PageList<TruckBills>>> truckList(@Body List<SysCondition> condition, @Query("userId") String userId, @Query("pageIndex") int pageIndex);

    /**
     * 拖车工作单明细
     *
     * @param userId 用户id
     * @param billId 工作单主键id
     * @return 返回拖车工作单明细
     */
    @POST(COMPANY_ORDER + "/getTruckOrderById.do?")
    @Headers("Cache-Control: public, max-age=60")
    Observable<BaseResponse<TruckBills>> getTruckOrderById(@Query("userId") String userId, @Query("billId") String billId);

    /**
     * 拖车工作单明细
     *
     * @param userId 用户id
     * @param id     工作单主键id
     * @return 返回拖车工作单明细
     */
    @POST(COMPANY_ORDER2 + "/truck/detail.do?")
    @Headers("Cache-Control: public, max-age=60")
    Observable<BaseResponse<TruckBills>> truckDetail(@Query("userId") String userId, @Query("id") String id);

    /**
     * 加载日期查询条件
     *
     * @param billType 0-货代，1-拖车
     * @return 日期集合
     */
    @POST(COMPANY_ORDER + "/loadOrderCondition.do?")
    @Headers("Cache-Control: public, max-age=60")
    Observable<BaseResponse<List<SysCondition>>> loadOrderCondition(@Query("billType") int billType);

    /**
     * 加载日期查询条件
     *
     * @param billType 0-货代，1-拖车
     * @return 日期集合
     */
    @POST(COMPANY_ORDER2 + "/loadOrderCondition.do?")
    @Headers("Cache-Control: public, max-age=60")
    Observable<BaseResponse<List<SysCondition>>> loadOrderCondition2(@Query("billType") int billType);

    /**
     * 业务下单
     *
     * @param appCompanyOrder 下单模型
     * @param userId          用户id
     * @return 返回的信息文本
     */
    @POST(COMPANY_ORDER + "/addCompanyOrder.do?")
    Observable<BaseResponse<String>> addCompanyOrder(@Body AppCompanyOrder appCompanyOrder, @Query("userId") String userId);

    /**
     * 业务下单第二版本
     *
     * @param viewForwardOrder 实体类
     * @param check            是否需要检查
     * @return 返回相关信息
     */
    @POST(COMPANY_ORDER2 + "/addFowardOrder.do?")
    Observable<BaseResponse<UpdateBusinessOrderModel>> addFowardOrder(@Body ViewForwardOrder viewForwardOrder, @Query("check") boolean check);

    /**
     * 业务下单第二版本
     *
     * @param appViewForwardOrderWrapper 实体类
     * @param check                      是否需要检查
     * @return 返回相关信息
     */
    @POST(COMPANY_ORDER2 + "/updateFowardOrder.do?")
    Observable<BaseResponse<UpdateBusinessOrderModel>> updateFowardOrder(@Body AppViewForwardOrderWrapper appViewForwardOrderWrapper, @Query("check") boolean check);

    /**
     * 业务下单第二版本(拖车)
     *
     * @param truckBills 实体类
     * @param check      是否需要检查
     * @return 返回相关信息
     */
    @POST(COMPANY_ORDER2 + "/truck/add.do?")
    Observable<BaseResponse<UpdateBusinessOrderModel>> addTruck(@Body TruckBills truckBills, @Query("check") boolean check);

    /**
     * 业务下单第二版本(拖车)
     *
     * @param recordUpdate 实体类
     * @param check        是否需要检查
     * @return 返回相关信息
     */
    @POST(COMPANY_ORDER2 + "/truck/update.do?")
    Observable<BaseResponse<UpdateBusinessOrderModel>> updateTruck(@Body RecordUpdate recordUpdate, @Query("check") boolean check);

    /**
     * 业务下单查询
     *
     * @param appCompanyOrderCondition 条件实体
     * @param userId                   用户id
     * @param pageIndex                页码
     * @return 返回数据源
     */
    @POST(COMPANY_ORDER + "/getAppOrders.do?")
    @Headers("Cache-Control: public, max-age=5")
    Observable<BaseResponse<PageList<AppCompanyOrder>>> getAppOrders(@Body AppCompanyOrderCondition appCompanyOrderCondition, @Query("userId") String userId, @Query("pageIndex") int pageIndex);

    /**
     * 业务下单查询 (模版)
     *
     * @param appCompanyOrderCondition 条件实体
     * @param userId                   用户id
     * @param pageIndex                页码
     * @return 返回数据源
     */
    @POST(COMPANY_ORDER + "/getForwardOrderTemplates.do?")
    @Headers("Cache-Control: public, max-age=5")
    Observable<BaseResponse<PageList<AppCompanyOrderTemplate>>> getOrderTemplates(@Body AppCompanyOrderCondition appCompanyOrderCondition, @Query("userId") String userId, @Query("pageIndex") int pageIndex);

    /**
     * 业务下单查询 (模版)
     *
     * @param userId    用户id
     * @param pageIndex 页码
     * @return 返回数据源
     */
    @POST(COMPANY_ORDER + "/getForwardOrderTemplates.do?")
    @Headers("Cache-Control: public, max-age=5")
    Observable<BaseResponse<PageList<AppCompanyOrderTemplate>>> getOrderTemplates(@Query("userId") String userId, @Query("pageIndex") int pageIndex);

    /**
     * 业务下单查询(旧版明细)
     *
     * @param id 主键id
     * @return 返回数据源
     */
    @POST(COMPANY_ORDER + "/getAppOrder.do?")
    @Headers("Cache-Control: public, max-age=60")
    Observable<BaseResponse<AppCompanyOrder>> getAppOrder(@Query("id") String id);

    /**
     * 业务下单查询(货代明细)
     *
     * @param id 主键id
     * @return 返回数据源
     */
    @POST(COMPANY_ORDER2 + "/forward/order/detail.do?")
    @Headers("Cache-Control: public, max-age=60")
    Observable<BaseResponse<ViewForwardOrder>> detail(@Query("id") String id);

    /**
     * 编辑模版
     *
     * @param appCompanyOrderTemplate 实体类
     * @param userId                  用户id
     * @return 编辑信息
     */
    @POST(COMPANY_ORDER + "/editTemplate.do?")
    Observable<BaseResponse<String>> editTemplate(@Body AppCompanyOrderTemplate appCompanyOrderTemplate, @Query("userId") String userId);

    /**
     * 获取搜索条件
     *
     * @param billType 0-货代，1-拖车
     * @param userId   用户id
     * @return 返回相关搜索数据
     */
    @POST(COMPANY_ORDER + "/loadOrderCondition2.do?")
    Observable<BaseResponse<AppBusinessOrderListCondition>> loadOrderCondition2(@Query("billType") String billType, @Query("userId") String userId);

    /**
     * 获取搜索条件
     *
     * @param billsId     工作单id
     * @param serviceType 0-货代，1-拖车
     * @return 返回相关搜索数据
     */
    @POST(COMPANY_ORDER2 + "/fee/list.do?")
    Observable<BaseResponse<List<AccountFee>>> feeList(@Query("billsId") String billsId, @Query("serviceType") String serviceType);

    /**
     * 保存/编辑模版
     *
     * @param oldNewAccountFeeWrapper 实体类
     * @return 编辑信息
     */
    @POST(COMPANY_ORDER2 + "/fee/save.do?")
    Observable<BaseResponse<String>> feeSave(@Body OldNewAccountFeeWrapper oldNewAccountFeeWrapper);

    /**
     * 删除费用
     * @param ids 多个id
     * @param billsId 订单id
     */
    @POST(COMPANY_ORDER2 + "/fee/delete.do?")
    Observable<BaseResponse<String>> feeDelete(@Query("ids") String ids, @Query("billsId")  String billsId);

    /**
     * 查询模板列表
     *
     * @param type 0-货代，1-拖车
     */
    @POST(COMPANY_ORDER2 + "/template/list.do?")
    Observable<BaseResponse<PageList<WebOrderTemplateWrapper>>> templateList(@Query("type") String type, @Query("pageIndex") int pageIndex);

    /**
     * 删货柜（货代）
     * @param id  id
     * @param billsId  billsId
     * @return 状态200成功，其他是失败
     */
    @POST(COMPANY_ORDER2 + "/forward/sono/delete.do?")
    Observable<BaseResponse<String>> forwardSonoDelete(@Query("id") String id, @Query("billsId") String billsId);

    /**
     * 删装货地址 （货代）
     * @param id  id
     * @param billsId  billsId
     * @return 状态200成功，其他是失败
     */
    @POST(COMPANY_ORDER2 + "/forward/loadAddress/delete.do?")
    Observable<BaseResponse<String>> forwardLoadAddressDelete(@Query("id") String id, @Query("billsId") String billsId);

    /**
     * 删卸货地址 （货代）
     * @param id  id
     * @param billsId  billsId
     * @return 状态200成功，其他是失败
     */
    @POST(COMPANY_ORDER2 + "/forward/unloadAddress/delete.do?")
    Observable<BaseResponse<String>> forwardUnloadAddressDelete(@Query("id") String id, @Query("billsId") String billsId);

    /**
     * 删货柜（拖车）
     * @param id  id
     * @param billsId  billsId
     * @return 状态200成功，其他是失败
     */
    @POST(COMPANY_ORDER2 + "/truck/sono/delete.do?")
    Observable<BaseResponse<String>> truckSonoDelete(@Query("id") String id, @Query("billsId") String billsId);

    /**
     * 删地址（装货或者卸货，货代或者拖车）
     * @param id  id
     * @param billsId  billsId
     * @return 状态200成功，其他是失败
     */
    @POST(COMPANY_ORDER2 + "/truck/address/delete.do?")
    Observable<BaseResponse<String>> truckAddressDelete(@Query("id") String id, @Query("billsId") String billsId);

    /**
     * 拖车APP派单
     * @param id 订单id
     */
    @POST(COMPANY_ORDER2 + "/truck/sendToDriver.do?")
    Observable<BaseResponse<String>> sendToDriver(@Query("id") String id);

    /**
     * 拖车APP取消派单
     * @param driverOrderId truckBills.driverOrderId
     */
    @POST(COMPANY_ORDER2 + "/truck/cancelDriverOrder.do?")
    Observable<BaseResponse<String>> cancelDriverOrder(@Query("driverOrderId") String driverOrderId);

}
