package com.yaoguang.greendao.entity.driver;


import android.text.SpannableStringBuilder;

/**
 * 司机订单消息包装类
 *
 * @author liyangbin
 * @date 2017年7月10日下午2:36:50
 */
public class DriverOrderMsgWrapper extends DriverOrderMsg {

    private DriverOrderWrapper driverOrderWrapper;    // 订单数据

    public DriverOrderWrapper getDriverOrderWrapper() {
        return driverOrderWrapper;
    }

    public void setDriverOrderWrapper(DriverOrderWrapper driverOrderWrapper) {
        this.driverOrderWrapper = driverOrderWrapper;
    }



    public String getMsgTypeDisplay() {
        switch (getMsgType()) {
            case "0":
                return "派";
            case "1":
                return "改";
            default:
                return "关";
        }
    }
}