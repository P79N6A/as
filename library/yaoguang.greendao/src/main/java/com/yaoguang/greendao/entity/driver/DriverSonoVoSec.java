package com.yaoguang.greendao.entity.driver;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by liyangbin on 2018/6/1.
 * 订单货柜 - 版本2
 */
public class DriverSonoVoSec {

    // 船公司
    private String shipCompany;
    // 柜号
    private String contNo;
    // 提柜点
    private String carryPort;
    // 还柜点
    private String getPort;
    // 拆/装箱任务
    private List<SonoTaskVo> taskList = new ArrayList<>();
    // 提柜点坐标
    private Site carryPortSite;
    // 还柜点坐标
    private Site getPortSite;

    public String getShipCompany() {
        return shipCompany;
    }

    public void setShipCompany(String shipCompany) {
        this.shipCompany = shipCompany;
    }

    public String getContNo() {
        return contNo;
    }

    public void setContNo(String contNo) {
        this.contNo = contNo;
    }

    public String getCarryPort() {
        return carryPort;
    }

    public void setCarryPort(String carryPort) {
        this.carryPort = carryPort;
    }

    public String getGetPort() {
        return getPort;
    }

    public void setGetPort(String getPort) {
        this.getPort = getPort;
    }

    public List<SonoTaskVo> getTaskList() {
        return taskList;
    }

    public void setTaskList(List<SonoTaskVo> taskList) {
        this.taskList = taskList;
    }

    public Site getCarryPortSite() {
        return carryPortSite;
    }

    public void setCarryPortSite(Site carryPortSite) {
        this.carryPortSite = carryPortSite;
    }

    public Site getGetPortSite() {
        return getPortSite;
    }

    public void setGetPortSite(Site getPortSite) {
        this.getPortSite = getPortSite;
    }
}
