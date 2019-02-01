package com.yaoguang.datasource.driver;

import android.support.annotation.NonNull;

import com.yaoguang.datasource.common.DataStatic;
import com.yaoguang.datasource.util.OrderAssistant;
import com.yaoguang.greendao.entity.driver.DriverOrderMsgWrapper;
import com.yaoguang.lib.net.Api;
import com.yaoguang.lib.net.bean.BaseResponse;
import com.yaoguang.lib.net.bean.PageList;
import com.yaoguang.datasource.api.driver.OrderMsgApi;

import io.reactivex.Observable;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * 订单消息
 * Created by zhongjh on 2018/5/11.
 */
public class OrderMsgDataSource {

    private OrderMsgApi mOrderMsgApi = Api.getInstance().retrofit.create(OrderMsgApi.class);

    /**
     * 删除订单消息
     *
     * @param ids          消息id
     */
    public Observable<BaseResponse<String>> orderMessageDeleted(String ids) {
        return mOrderMsgApi.orderMessageDeleted(ids,1);
    }

    /**
     * 获取消息
     *
     * @param pageIndex  页码
     * @param isHomePage 是否首页
     */
    public Observable<BaseResponse<PageList<DriverOrderMsgWrapper>>> getMessageList(int pageIndex, boolean isHomePage, int ic_dc_s02) {
        String driverId = DataStatic.getInstance().getDriver().getId();

        Observable<BaseResponse<PageList<DriverOrderMsgWrapper>>> homeMessageList;
        if (isHomePage) { // getHomeMessageList(getDriverId(), ObjectUtils.parseString(pageIndex), type, "0");
            homeMessageList = mOrderMsgApi.getHomeMessageList(driverId, pageIndex, "1", "0", 0)
                    .map(OrderAssistant.getInstance(ic_dc_s02).deliveryRoutmapperMsg)
                    .map(OrderAssistant.getInstance(ic_dc_s02).orderCreateTimeMapperMsg);
        } else {
            homeMessageList = mOrderMsgApi.getMessageOrderList(driverId, pageIndex)
                    .map(OrderAssistant.getInstance(ic_dc_s02).deliveryRoutmapperMsg)
                    .map(OrderAssistant.getInstance(ic_dc_s02).orderCreateTimeMapperMsg);
        }
        return homeMessageList;
    }

    /**
     * 获取消息订单列表
     *
     * @param driverId  司机id
     * @param pageIndex 页码
     */
    public Observable<BaseResponse<PageList<DriverOrderMsgWrapper>>> getMessageOrderList(@NonNull String driverId, int pageIndex) {
        return mOrderMsgApi.getMessageOrderList(checkNotNull(driverId), pageIndex);
    }

    /**
     * 批量设置已读
     *
     * @param ids id列表
     */
    public Observable<BaseResponse<String>> readBatch(@NonNull String ids) {
        return mOrderMsgApi.readBatch(checkNotNull(ids));
    }

}
