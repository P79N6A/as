package com.yaoguang.driver.data.source.remote;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.yaoguang.common.common.ObjectUtils;
import com.yaoguang.common.net.bean.BaseResponse;
import com.yaoguang.common.net.bean.PageList;
import com.yaoguang.driver.data.source.OrderDataSource;
import com.yaoguang.driver.net.factory.ApiOrderFactory;
import com.yaoguang.greendao.entity.DriverOrderDetailVo;
import com.yaoguang.greendao.entity.DriverOrderNode;
import com.yaoguang.greendao.entity.DriverOrderNodeDetail;
import com.yaoguang.greendao.entity.DriverOrderProgressWrapper;
import com.yaoguang.greendao.entity.FreightStatistic;
import com.yaoguang.greendao.entity.InfoPutorderPlace;
import com.yaoguang.greendao.entity.Order;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

import io.reactivex.Flowable;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * 订单远程数据源
 * Created by wly on 2018/1/9 0009.
 */

public class OrderRemoteDataSource implements OrderDataSource {
    private static OrderRemoteDataSource INSTANCE;

    public static OrderRemoteDataSource getInstance() {
        if (INSTANCE == null) {
            synchronized (OrderRemoteDataSource.class) {
                if (INSTANCE == null) {
                    INSTANCE = new OrderRemoteDataSource();
                }
            }
        }
        return INSTANCE;
    }

    @NonNull
    @Override
    public Flowable<BaseResponse<String>> handleUpdate(@Nullable String driverId, @NonNull String orderId, @NonNull String operateType, @NonNull String remark, @Nullable String placeId) {
        return ApiOrderFactory.update(checkNotNull(driverId), checkNotNull(orderId), checkNotNull(operateType), checkNotNull(remark), checkNotNull(placeId));
    }

    @NonNull
    @Override
    public Flowable<BaseResponse<String>> outCar(@Nullable String driverId, @Nullable String orderId, @Nullable DriverOrderNodeDetail driverOrderNodeDetail) {
        return ApiOrderFactory.outCar(checkNotNull(driverId), checkNotNull(driverOrderNodeDetail));
    }

    @NonNull
    @Override
    public Flowable<ArrayList<InfoPutorderPlace>> getPutOrderAddressList(@NonNull String id) {
        return ApiOrderFactory.getPutOrderAddressList(id).map(listBaseResponse -> {
            if (listBaseResponse.getState().equals("200"))
                if (!listBaseResponse.getResult().isEmpty())
                    return new ArrayList(Collections.singletonList(listBaseResponse.getResult()));
            return null;
        });
    }

    @NonNull
    @Override
    public Flowable<BaseResponse<DriverOrderProgressWrapper>> getOrderCurrentProgress(@Nullable String driverId) {
        return ApiOrderFactory.getOrderCurrentProgress(checkNotNull(driverId));
    }

    @NonNull
    public Flowable<BaseResponse<List<DriverOrderNode>>> nextNode(@NonNull String orderId) {
        return ApiOrderFactory.next(checkNotNull(orderId));
    }

    @NonNull
    @Override
    public Flowable<BaseResponse<PageList<Order>>> getOrderList(@Nullable String driverId, int type, int pageIndex) {
        return ApiOrderFactory.getList(checkNotNull(driverId), type, pageIndex);
    }

    @NonNull
    @Override
    public Flowable<BaseResponse<FreightStatistic>> calculationData(@Nullable String driverId, @NonNull HashSet<String> companyIds, @Nullable String startDate, @Nullable String endDate, @NonNull String type) {
        return ApiOrderFactory.count(checkNotNull(driverId), ObjectUtils.subString(checkNotNull(companyIds)), startDate, endDate, checkNotNull(type));
    }

    @NonNull
    @Override
    public Flowable<BaseResponse<DriverOrderDetailVo>> getOrderDetail(@Nullable String driverId, @NonNull String orderId) {
        return ApiOrderFactory.getOrderDetail(checkNotNull(driverId), checkNotNull(orderId));
    }
}
