package com.yaoguang.greendao.entity;

import java.util.List;

/**
 * Created by Administrator on 2017/7/19 0019.
 */

public class OrderDetailNodeList {
    public List<DriverOrderPositionLog>  positionLogs;
    public List<DriverOrderNode> nodeWrappers;

    public List<DriverOrderNode> getNodeWrappers() {
        return nodeWrappers;
    }

    public void setNodeWrappers(List<DriverOrderNode> nodeWrappers) {
        this.nodeWrappers = nodeWrappers;
    }

    public List<DriverOrderPositionLog> getPositionLogs() {
        return positionLogs;
    }

    public void setPositionLogs(List<DriverOrderPositionLog> positionLogs) {
        this.positionLogs = positionLogs;
    }
}
