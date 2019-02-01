package com.yaoguang.driver.data.source;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.yaoguang.common.net.bean.BaseResponse;
import com.yaoguang.common.net.bean.PageList;
import com.yaoguang.greendao.entity.DriverOrderDetailVo;
import com.yaoguang.greendao.entity.DriverOrderNode;
import com.yaoguang.greendao.entity.DriverOrderNodeDetail;
import com.yaoguang.greendao.entity.DriverOrderProgressWrapper;
import com.yaoguang.greendao.entity.FreightStatistic;
import com.yaoguang.greendao.entity.InfoPutorderPlace;
import com.yaoguang.greendao.entity.Order;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import io.reactivex.Flowable;

/**
 * =====================================
 * 作    者: 韦理英
 * 版    本：1.2.9
 * 创建日期：2018/1/10
 * 描    述：订单数据源接口
 * =====================================
 */

public interface OrderDataSource {
    @NonNull
    Flowable<BaseResponse<String>> handleUpdate(@Nullable String driverId, @NonNull String orderId, @NonNull String operateType, @NonNull String remark, @Nullable String placeId);

    @NonNull
    Flowable<BaseResponse<String>> outCar(@Nullable String driverId, @NonNull String orderId, @Nullable DriverOrderNodeDetail driverOrderNodeDetail);

    @NonNull
    Flowable<ArrayList<InfoPutorderPlace>> getPutOrderAddressList(@NonNull String driverId);

    @NonNull
    Flowable<BaseResponse<DriverOrderProgressWrapper>> getOrderCurrentProgress(@Nullable String driverId);

    @NonNull
    Flowable<BaseResponse<List<DriverOrderNode>>> nextNode(@NonNull String orderId);

    @NonNull
    Flowable<BaseResponse<PageList<Order>>> getOrderList(@Nullable String driverId, int type, int pageIndex);

    @NonNull
    Flowable<BaseResponse<FreightStatistic>> calculationData(@Nullable String driverId, @NonNull HashSet<String> companyIds, @Nullable String startDate, @Nullable String endDate, @NonNull String type);

    @NonNull
    Flowable<BaseResponse<DriverOrderDetailVo>> getOrderDetail(@Nullable String driverId, @NonNull String orderId);
}
