package com.yaoguang.greendao.entity.driver;


import android.text.SpannableStringBuilder;

import java.io.Serializable;
import java.util.List;

public class DriverOrderProgressWrapper {

    // 节点id
    private String nodeId;
    // 节点名称
    private String nodeName;
    // 订单id,获取订单详情
    private String orderId;
    // 订单号, 进度更新
    private String orderSn;
    // 标题
    private String title;
    // 语音
    private String voice;


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    private List<String> deliveryRoute;

    // 运货线路箭头
    private SpannableStringBuilder spannableStringBuilder;

    private String orderType;

    private boolean overTime;

    public String getOrderSn() {
        return orderSn;
    }

    public void setOrderSn(String orderSn) {
        this.orderSn = orderSn;
    }

    public String getNodeName() {
        return nodeName;
    }

    public void setNodeName(String nodeName) {
        this.nodeName = nodeName;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    public boolean isOverTime() {
        return overTime;
    }

    public void setOverTime(boolean overTime) {
        this.overTime = overTime;
    }

    public List<String> getDeliveryRoute() {
        return deliveryRoute;
    }

    public void setDeliveryRoute(List<String> deliveryRoute) {
        this.deliveryRoute = deliveryRoute;
    }

    public String getNodeId() {
        return nodeId;
    }

    public void setNodeId(String nodeId) {
        this.nodeId = nodeId;
    }

    public SpannableStringBuilder getSpannableStringBuilder() {
        return spannableStringBuilder;
    }

    public void setSpannableStringBuilder(SpannableStringBuilder spannableStringBuilder) {
        this.spannableStringBuilder = spannableStringBuilder;
    }

    public String getVoice() {
        return voice;
    }

    public void setVoice(String voice) {
        this.voice = voice;
    }


}
