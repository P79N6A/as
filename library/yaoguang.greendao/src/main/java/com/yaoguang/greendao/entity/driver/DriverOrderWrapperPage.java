package com.yaoguang.greendao.entity.driver;

/**
 * 订单返回的格式
 * Created by zhongjh on 2018/4/8.
 */
public class DriverOrderWrapperPage<T> {

    public T getOrderList() {
        return orderList;
    }

    public void setOrderList(T orderList) {
        this.orderList = orderList;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getTotalFreight() {
        return totalFreight;
    }

    public void setTotalFreight(String totalFreight) {
        this.totalFreight = totalFreight;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    private T orderList;
    private String endDate;
    private String totalFreight;
    private String startDate;

}
