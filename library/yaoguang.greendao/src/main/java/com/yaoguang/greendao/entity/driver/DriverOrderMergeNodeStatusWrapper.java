package com.yaoguang.greendao.entity.driver;

import java.util.ArrayList;

/**
 * 包含列表和状态
 */
public class DriverOrderMergeNodeStatusWrapper {

    /**
     * 状态
     * 1代表显示,可以对订单进行相关操作
     * 不是1的代表隐藏底部操作，并且只能查看
     */
    private int orderStatus;

    /**
     * 列表
     */
    private ArrayList<DriverOrderMergeNodeWrapper> mergeNodes;

    /**
     * 公司id
     */
    private String clientId;

    public int getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(int orderStatus) {
        this.orderStatus = orderStatus;
    }

    public ArrayList<DriverOrderMergeNodeWrapper> getMergeNodes() {
        return mergeNodes;
    }

    public void setMergeNodes(ArrayList<DriverOrderMergeNodeWrapper> mergeNodes) {
        this.mergeNodes = mergeNodes;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }
}
