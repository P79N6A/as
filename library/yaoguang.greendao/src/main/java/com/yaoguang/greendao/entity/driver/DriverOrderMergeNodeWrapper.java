package com.yaoguang.greendao.entity.driver;


import java.util.ArrayList;
import java.util.List;

/**
 * Created by liyangbin on 2018/6/8.
 * 节点地址汇总显示
 */
public class DriverOrderMergeNodeWrapper {

    // 显示名称
    private String name;
    // 节点类型 0 : 出车，1 ：提柜码头，2 ：门点，3 ：还柜码头，4 ：完成
    private String nodeType;
    // 节点标记（提装卸还）
    private String mark;
    // 显示地址
    private String address;
    // 状态(0 : 未完成；1 : 正在前往； 2 : 已到达，未离开； 3 : 已到达，已离开)；
    private String finishStatus;
    // 节点列表
    List<DriverOrderNodeWrapper> nodes = new ArrayList<>();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNodeType() {
        return nodeType;
    }

    public void setNodeType(String nodeType) {
        this.nodeType = nodeType;
    }

    public String getMark() {
        return mark;
    }

    public void setMark(String mark) {
        this.mark = mark;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getFinishStatus() {
        return finishStatus;
    }

    public void setFinishStatus(String finishStatus) {
        this.finishStatus = finishStatus;
    }

    public List<DriverOrderNodeWrapper> getNodes() {
        return nodes;
    }

    public void setNodes(List<DriverOrderNodeWrapper> nodes) {
        this.nodes = nodes;
    }

}
