package com.yaoguang.greendao.entity;

import com.yaoguang.greendao.entity.common.UserOffice;

import java.util.List;

/**
 * 订单统计
 * Created by zhongjh on 2017/5/4.
 */
public class FreightStatistic {

    String startDate;
    String endDate;
    //订单数
    private int amount;
    //运费总额
    private Double totalFreight;
    private List<UserOffice> companies;
    private List<Order> orderList;

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public Double getTotalFreight() {
        return totalFreight;
    }

    public void setTotalFreight(Double totalFreight) {
        this.totalFreight = totalFreight;
    }

    public List<Order> getOrderList() {
        return orderList;
    }

    public void setOrderList(List<Order> orderList) {
        this.orderList = orderList;
    }

    public List<UserOffice> getCompanies() {
        return companies;
    }

    public void setCompanies(List<UserOffice> companies) {
        this.companies = companies;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }
}
