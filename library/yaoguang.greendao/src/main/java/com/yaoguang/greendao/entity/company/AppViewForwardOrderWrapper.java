package com.yaoguang.greendao.entity.company;


import com.yaoguang.greendao.entity.common.ViewForwardOrder;

import java.io.Serializable;

/**
 * 更新货代工作单实体类
 * Created by LiYangBin on 2018/11/9.
 */
public class AppViewForwardOrderWrapper  {

    private ViewForwardOrder oldViewForwardOrder; // 旧工作单数据

    private ViewForwardOrder newViewForwardOrder; // 新工作单数据

    public ViewForwardOrder getOldViewForwardOrder() {
        return oldViewForwardOrder;
    }

    public void setOldViewForwardOrder(ViewForwardOrder oldViewForwardOrder) {
        this.oldViewForwardOrder = oldViewForwardOrder;
    }

    public ViewForwardOrder getNewViewForwardOrder() {
        return newViewForwardOrder;
    }

    public void setNewViewForwardOrder(ViewForwardOrder newViewForwardOrder) {
        this.newViewForwardOrder = newViewForwardOrder;
    }
}
