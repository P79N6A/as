package com.yaoguang.driver.data.source;


import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.SpannableStringBuilder;

import com.yaoguang.common.net.bean.BaseResponse;
import com.yaoguang.common.net.bean.PageList;
import com.yaoguang.driver.data.source.remote.OrderRemoteDataSource;
import com.yaoguang.driver.util.OrderAssistant;
import com.yaoguang.greendao.biz.driver.impl.LocationBizImpl;
import com.yaoguang.greendao.entity.DriverOrderDetailVo;
import com.yaoguang.greendao.entity.DriverOrderNode;
import com.yaoguang.greendao.entity.DriverOrderNodeDetail;
import com.yaoguang.greendao.entity.DriverOrderProgressWrapper;
import com.yaoguang.greendao.entity.FreightStatistic;
import com.yaoguang.greendao.entity.InfoPutorderPlace;
import com.yaoguang.greendao.entity.Location;
import com.yaoguang.greendao.entity.Order;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * =====================================
 * 作    者: 韦理英
 * 版    本：1.2.9
 * 创建日期：2018/1/10
 * 描    述：订单仓库
 * =====================================
 */

public class OrderRepository implements OrderDataSource {
    private OrderRemoteDataSource mOrderRemoteDataSource;
    private DriverRepository mDriverRepository;
    private LocationBizImpl mLocationBiz;

    private static OrderRepository INSTANCE;

    public static OrderRepository getInstance(@NonNull OrderRemoteDataSource orderRemoteDataSource, @NonNull DriverRepository driverRepository, @NonNull LocationBizImpl locationBiz) {
        if (INSTANCE == null) {
            synchronized (OrderRepository.class) {
                if (INSTANCE == null) {
                    INSTANCE = new OrderRepository(orderRemoteDataSource, driverRepository, locationBiz);
                }
            }
        }
        return INSTANCE;
    }

    private OrderRepository(@NonNull OrderRemoteDataSource orderRemoteDataSource, @NonNull DriverRepository driverRepository, @NonNull LocationBizImpl locationBiz) {
        this.mOrderRemoteDataSource = checkNotNull(orderRemoteDataSource);
        this.mDriverRepository = checkNotNull(driverRepository);
        this.mLocationBiz = checkNotNull(locationBiz);
    }

    /**
     * 进度更新
     *
     * @param driverId    司机id
     * @param orderId     订单id
     * @param operateType 1:接单 2:完成 3:关闭 4:出车
     * @param remark      备注
     * @param placeId     存放点id
     */
    @NonNull
    @Override
    public Flowable<BaseResponse<String>> handleUpdate(@Nullable String driverId, @NonNull String orderId, @NonNull String operateType, @NonNull String remark, @Nullable String placeId) {
        if (driverId == null) {
            driverId = mDriverRepository.getDriver().getId();
        }
        return mOrderRemoteDataSource.handleUpdate(driverId, orderId, operateType, remark, placeId);
    }

    /**
     * 出车
     *
     * @param driverId              司机id
     * @param orderId               订单id
     * @param driverOrderNodeDetail 上传的资源
     */
    @NonNull
    @Override
    public Flowable<BaseResponse<String>> outCar(@Nullable String driverId, @NonNull String orderId, @Nullable DriverOrderNodeDetail driverOrderNodeDetail) {
        Location location = mLocationBiz.getLast();
        if (driverOrderNodeDetail == null) {
            driverOrderNodeDetail = new DriverOrderNodeDetail();
            driverOrderNodeDetail.setNodeName("出车");
            driverOrderNodeDetail.setOrderSn(orderId);

            if (location != null) {
                driverOrderNodeDetail.setLat("" + location.getLat());
                driverOrderNodeDetail.setLon("" + location.getLon());
                driverOrderNodeDetail.setAddress(location.getAddress());
            }
        }
        if (driverId == null) {
            driverId = mDriverRepository.getDriver().getId();
        }

        return mOrderRemoteDataSource.outCar(driverId, orderId, driverOrderNodeDetail);
    }

    /**
     * 获取放单列表
     *
     * @param driverId 司机id
     */
    @NonNull
    @Override
    public Flowable<ArrayList<InfoPutorderPlace>> getPutOrderAddressList(@Nullable String driverId) {
        if (driverId == null) {
            driverId = mDriverRepository.getDriver().getId();
        }
        return mOrderRemoteDataSource.getPutOrderAddressList(driverId);
    }

    /**
     * 获取当前订单进度
     *
     * @param driverId 司机id
     */
    @NonNull
    @Override
    public Flowable<BaseResponse<DriverOrderProgressWrapper>> getOrderCurrentProgress(@Nullable String driverId) {
        if (driverId == null) {
            driverId = mDriverRepository.getDriver().getId();
        }
        return mOrderRemoteDataSource.getOrderCurrentProgress(driverId)
                .observeOn(Schedulers.io())
                .map(driverOrderProgressWrapperBaseResponse -> {
                    if (driverOrderProgressWrapperBaseResponse.getResult() != null && driverOrderProgressWrapperBaseResponse.getResult().getDeliveryRoute() != null) {
                        // 设置线路箭头
                        SpannableStringBuilder spannableStringBuilder = OrderAssistant.getInstance().handlerDeliverRoute(driverOrderProgressWrapperBaseResponse.getResult().getDeliveryRoute());
                        driverOrderProgressWrapperBaseResponse.getResult().setSpannableStringBuilder(spannableStringBuilder);
                    }
                    return driverOrderProgressWrapperBaseResponse;
                }).observeOn(AndroidSchedulers.mainThread());
    }


    /**
     * 去下一个节点
     *
     * @param orderId 订单id
     */
    @NonNull
    @Override
    public Flowable<BaseResponse<List<DriverOrderNode>>> nextNode(@NonNull String orderId) {
        return mOrderRemoteDataSource.nextNode(checkNotNull(orderId));
    }


    /**
     * 获取订单中心列表
     *
     * @param driverId  司机id
     * @param type      订单类型
     * @param pageIndex 页码
     */
    @NonNull
    @Override
    public Flowable<BaseResponse<PageList<Order>>> getOrderList(@Nullable String driverId, int type, int pageIndex) {
        if (driverId == null) {
            driverId = mDriverRepository.getDriver().getId();
        }

        return mOrderRemoteDataSource.getOrderList(driverId, type, pageIndex)
                .observeOn(Schedulers.io())
                .map(OrderAssistant.getInstance().deliveryRoutmapper)
                .map(OrderAssistant.getInstance().orderCreateTimeMapper)
                .map(OrderAssistant.getInstance().driverOrderMsgMapper)
                .map(OrderAssistant.getInstance().orderMarkmapper
                ).observeOn(AndroidSchedulers.mainThread());
    }

    /**
     * 计算日期
     *
     * @param driverId   司机id
     * @param companyIds 公司列表
     * @param startDate  开始时间
     * @param endDate    结束时间
     * @param type       时间范围类型 0、全部，1、本月，2、上月，3、近三月
     */
    @NonNull
    @Override
    public Flowable<BaseResponse<FreightStatistic>> calculationData(@Nullable String driverId, @NonNull HashSet<String> companyIds, @Nullable String startDate, @Nullable String endDate, @NonNull String type) {
        if (driverId == null) {
            driverId = mDriverRepository.getDriver().getId();
        }
        return mOrderRemoteDataSource.calculationData(driverId, companyIds, startDate, endDate, type)
                .observeOn(Schedulers.io())
                .map(freightStatisticBaseResponse -> {
                    // 路线箭头
                    for (int i = 0; i < freightStatisticBaseResponse.getResult().getOrderList().size(); i++) {
                        Order order = freightStatisticBaseResponse.getResult().getOrderList().get(i);
                        if ((order.getDeliveryRoute() == null) || order.getDeliveryRoute().isEmpty())
                            return freightStatisticBaseResponse;
                        // 设置线路箭头
                        order.setSpannableStringBuilder(OrderAssistant.getInstance().handlerDeliverRoute(order.getDeliveryRoute()));
                    }
                    return freightStatisticBaseResponse;
                }).observeOn(AndroidSchedulers.mainThread());
    }


    /**
     * 获取订单详情
     *
     * @param orderId 订单id
     */
    @NonNull
    @Override
    public Flowable<BaseResponse<DriverOrderDetailVo>> getOrderDetail(@Nullable String driverId, @NonNull String orderId) {
        if (driverId == null) {
            driverId = mDriverRepository.getDriver().getId();
        }
        return mOrderRemoteDataSource.getOrderDetail(driverId, orderId);
    }
}
