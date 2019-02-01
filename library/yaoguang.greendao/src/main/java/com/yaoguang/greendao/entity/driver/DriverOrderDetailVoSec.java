package com.yaoguang.greendao.entity.driver;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by liyangbin on 2018/6/1.
 * 订单详情 - 版本2
 */
public class DriverOrderDetailVoSec {

    // 订单
    private DriverOrderWrapper order;

    // 货柜
    private List<DriverSonoVoSec> sonoList = new ArrayList<>();

    public DriverOrderWrapper getOrder() {
        return order;
    }

    public void setOrder(DriverOrderWrapper order) {
        this.order = order;
    }

    public List<DriverSonoVoSec> getSonoList() {
        return sonoList;
    }

    public void setSonoList(List<DriverSonoVoSec> sonoList) {
        this.sonoList = sonoList;
    }
}
