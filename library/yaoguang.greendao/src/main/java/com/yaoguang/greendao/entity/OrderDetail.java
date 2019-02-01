package com.yaoguang.greendao.entity;

import com.yaoguang.greendao.entity.driver.DriverOrderWrapper;

import java.io.Serializable;
import java.util.List;

/**
 * 订单详情，包含多个对象
 * Created by zhongjh on 2017/4/21.
 */
public class OrderDetail implements Serializable {

    public static String STRGSON = "";

    private DriverOrderWrapper driverOrderWrapper;

    private List<DriverBillsVo> driverBillsVo;

    public DriverOrderWrapper getDriverOrderWrapper() {
        return driverOrderWrapper;
    }

    public void setDriverOrderWrapper(DriverOrderWrapper driverOrderWrapper) {
        this.driverOrderWrapper = driverOrderWrapper;
    }

    public List<DriverBillsVo> getDriverBillsVo() {
        return driverBillsVo;
    }

    public void setDriverBillsVo(List<DriverBillsVo> driverBillsVo) {
        this.driverBillsVo = driverBillsVo;
    }

}
