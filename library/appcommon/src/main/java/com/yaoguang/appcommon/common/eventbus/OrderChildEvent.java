package com.yaoguang.appcommon.common.eventbus;

import com.yaoguang.greendao.entity.Order;
import com.yaoguang.greendao.entity.driver.DriverOrderWrapper;

/**
 * 通知子订单的event
 * Created by zhongjh on 2018/4/10.
 */
public class OrderChildEvent extends BaseEvent {

    /**
     * 订单类型
     */
    private int orderType;

    /**
     * 订单数据
     */
    private DriverOrderWrapper driverOrderWrapper;

    private int position;

    private String placeId;

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public String getPlaceId() {
        return placeId;
    }

    public void setPlaceId(String placeId) {
        this.placeId = placeId;
    }

    public void setOrderType(int orderType) {
        this.orderType = orderType;
    }

    public int getOrderType() {
        return orderType;
    }


    public DriverOrderWrapper getDriverOrderWrapper() {
        return driverOrderWrapper;
    }

    public void setOrder(DriverOrderWrapper driverOrderWrapper) {
        this.driverOrderWrapper = driverOrderWrapper;
    }


    public OrderChildEvent() {
        super();
    }

    public OrderChildEvent(String type) {
        super(type);
    }

    public OrderChildEvent(String type, Object object) {
        super(type, object);
    }


}
