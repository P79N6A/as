package com.yaoguang.greendao.entity;

import java.io.Serializable;

/**
 * App订单状态包装对象
 *
 * @author wangxiaohui
 * @date 2017年6月21日 下午2:34:03
 */
public class AppLogBillTrackWrapper implements Serializable {

    private static final long serialVersionUID = -1658468461974760057L;

    //状态名称
    private String name;

    //状态值
    private Integer status;

    //状态完成时间
    private String time;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }


}