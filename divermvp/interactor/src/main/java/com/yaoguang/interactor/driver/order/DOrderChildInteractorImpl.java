package com.yaoguang.interactor.driver.order;

import android.support.annotation.NonNull;

import com.yaoguang.lib.common.ObjectUtils;
import com.yaoguang.lib.net.bean.BaseResponse;
import com.yaoguang.lib.net.bean.PageList;
import com.yaoguang.greendao.entity.DriverOrderMsg;
import com.yaoguang.greendao.entity.DriverOrderNode;
import com.yaoguang.greendao.entity.DriverOrderNodeDetail;
import com.yaoguang.greendao.entity.InfoPutorderPlace;
import com.yaoguang.greendao.entity.Location;
import com.yaoguang.greendao.entity.Order;
import com.yaoguang.interactor.common.DCSBaseInteractorImpl;
import com.yaoguang.interfaces.driver.interactor.order.DOrderChildInteractor;
import com.yaoguang.lib.net.Api;
import com.yaoguang.datasource.api.MessageApi;
import com.yaoguang.datasource.api.OrderApi;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Observable;

/**
 * 子订单信息
 * Created by zhongjh on 2017/4/18.
 */
public class DOrderChildInteractorImpl extends DCSBaseInteractorImpl implements DOrderChildInteractor {

    @Override
    public Observable<BaseResponse<PageList<Order>>> initOrders(final int intType, final int pageIndex) {

        return Api.getInstance().retrofit.create(OrderApi.class).getList(getDriver().getId(), ObjectUtils.parseString(intType), ObjectUtils.parseString(pageIndex));
    }

    @Override
    public Observable<BaseResponse<PageList<DriverOrderMsg>>> initMessageOrder(final int intType, final int pageIndex, boolean isHomePage, String type) {
        if (isHomePage) {
            return Api.getInstance().retrofit.create(MessageApi.class).getHomeMessageList(getDriver().getId(), ObjectUtils.parseString(pageIndex), type, "0");
        }
        return Api.getInstance().retrofit.create(MessageApi.class).getMessageOrderList(getDriver().getId(), ObjectUtils.parseString(pageIndex));
    }


    /**
     * 统一处理 首页和业务消息的订单消息
     * 转成 订单中心需要的格式
     * <p>
     * * @param value
     *
     * @return 订单中心需要的格式
     */
    @NonNull
    @Override
    public ArrayList<Order> getOrders(BaseResponse<PageList<DriverOrderMsg>> value) {
        ArrayList<Order> list = new ArrayList<>();
        for (DriverOrderMsg driverOrderMsg : value.getResult().getResult()) {    // 设置订单数据
            Order driverOrderWrapper = driverOrderMsg.getDriverOrderWrapper();
            driverOrderWrapper.setDriverOrderMsg(driverOrderMsg);
            list.add(driverOrderWrapper);
        }
        return list;
    }


    @Override
    public Observable<BaseResponse<String>> update(final String orderId, final int operateType, final String remark, String placeId) {
        String driverId = getDriver().getId();
        return Api.getInstance().retrofit.create(OrderApi.class).update(driverId, orderId, ObjectUtils.parseString(operateType), remark, placeId);
    }

    @Override
    public Observable<BaseResponse<String>> readBatch(String ids) {
        return Api.getInstance().retrofit.create(OrderApi.class).readBatch(ids);
    }

    @Override
    public Observable<BaseResponse<String>> outCar(final String orderId, Location location) {
        DriverOrderNodeDetail driverOrderNodeDetail = new DriverOrderNodeDetail();
        driverOrderNodeDetail.setNodeName("出车");
        driverOrderNodeDetail.setOrderSn(orderId);

        if (location != null) {
            driverOrderNodeDetail.setLat("" + location.getLat());
            driverOrderNodeDetail.setLon("" + location.getLon());
            driverOrderNodeDetail.setAddress(location.getAddress());
        }
        return Api.getInstance().retrofit.create(OrderApi.class).outCar(getDriver().getId(), driverOrderNodeDetail);
    }

    @Override
    public Observable<BaseResponse<String>> deleteIds(HashSet<String> ids) {
        String str = ObjectUtils.subString(ids);
        return Api.getInstance().retrofit.create(MessageApi.class).orderMessageDeleted(str, 1);
    }

    @Override
    public Observable<BaseResponse<List<DriverOrderNode>>> initNodes(String orderId) {
        return Api.getInstance().retrofit.create(OrderApi.class).next(orderId);
    }

    @Override
    public Flowable<BaseResponse<List<InfoPutorderPlace>>> getPutOrderAddressData(String id) {
        return Api.getInstance().retrofit.create(OrderApi.class).getPutOrderAddressList(id);
    }
}
