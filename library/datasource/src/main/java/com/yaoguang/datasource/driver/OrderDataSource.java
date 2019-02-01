package com.yaoguang.datasource.driver;

import com.yaoguang.datasource.common.DataStatic;
import com.yaoguang.datasource.util.OrderAssistant;
import com.yaoguang.greendao.entity.InfoPutorderPlace;
import com.yaoguang.greendao.entity.driver.DriverEntrustCompany;
import com.yaoguang.greendao.entity.driver.DriverOrderCondition;
import com.yaoguang.greendao.entity.driver.DriverOrderDetailVo;
import com.yaoguang.greendao.entity.driver.DriverOrderDetailVoSec;
import com.yaoguang.greendao.entity.driver.DriverOrderWrapper;
import com.yaoguang.greendao.entity.driver.DriverOrderWrapperPage;
import com.yaoguang.lib.net.Api;
import com.yaoguang.lib.net.bean.BaseResponse;
import com.yaoguang.lib.net.bean.PageList;
import com.yaoguang.datasource.api.driver.OrderApi;

import java.util.List;

import io.reactivex.Observable;

/**
 * 有关订单的
 * Created by zhongjh on 2018/4/4.
 */
public class OrderDataSource {

    private OrderApi mOrderApi = Api.getInstance().retrofit.create(OrderApi.class);

    /**
     * 订单明细
     *
     * @param orderId 订单id
     * @return 返回数据源
     */
    public Observable<BaseResponse<DriverOrderDetailVo>> detail2(String orderId) {
        return mOrderApi.detail2(orderId);
    }

    /**
     * 订单明细(第二版)
     *
     * @param orderId 订单id
     * @return 返回数据源
     */
    public Observable<BaseResponse<DriverOrderDetailVoSec>> detailnew(String orderId) {
        return mOrderApi.detailnew(orderId);
    }

    /**
     * 我的订单查询
     *
     * @param driverOrderCondition 查询条件
     * @param pageIndex            页数
     * @return 返回数据源
     */
    public Observable<BaseResponse<DriverOrderWrapperPage<PageList<DriverOrderWrapper>>>> countv2(DriverOrderCondition driverOrderCondition, int pageIndex) {
        return mOrderApi.count2(DataStatic.getInstance().getDriver().getId(), driverOrderCondition.getCompanyIds(), driverOrderCondition.getStartDate(), driverOrderCondition.getEndDate(), driverOrderCondition.getDateScopeType(), pageIndex);
    }

    /**
     * 获取订单中心列表
     *
     * @param type      订单类型
     * @param pageIndex 页码
     */
    public Observable<BaseResponse<PageList<DriverOrderWrapper>>> getList(int type, int pageIndex, int ic_dc_s02) {
        return mOrderApi.getList(DataStatic.getInstance().getDriver().getId(), type, pageIndex)
                .map(OrderAssistant.getInstance(ic_dc_s02).deliveryRoutmapper)
                .map(OrderAssistant.getInstance(ic_dc_s02).orderCreateTimeMapper);
    }

    /**
     * 委托公司查询
     * <p>
     * //     * @param userId    用户id
     * //     * @param pageIndex 页数（暂时作废，直接查询所有）
     *
     * @return 返回数据源
     */
    public Observable<BaseResponse<PageList<DriverEntrustCompany>>> companies() {
        return mOrderApi.companies(DataStatic.getInstance().getDriver().getId());
    }

    /**
     * 更新工作单状态
     *
     * @param orderId     订单id
     * @param operateType 1:接单 2:完成 3:关闭 4:出车
     * @param remark
     * @param placeId     存放点id
     */
    public Observable<BaseResponse<String>> update(String orderId, String operateType, String remark, String placeId) {
        return mOrderApi.update(DataStatic.getInstance().getId(), orderId, operateType, remark, placeId);
    }

    /**
     * 获取订单存放地址列表
     *
     * @param id id
     */
    public Observable<BaseResponse<List<InfoPutorderPlace>>> places(String id) {
        return mOrderApi.places(id);
    }

}
