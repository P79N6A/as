package com.yaoguang.appcommon.common.eventbus;

/**
 * Created by zhongjh on 2017/5/17.
 */

public class OrderNodeRichTextEvent {

    public boolean isOK() {
        return isOK;
    }

    public void setOK(boolean OK) {
        isOK = OK;
    }

    /**
     * 设置地图界面是否OK
     */
    boolean isOK;

    public OrderNodeRichTextEvent(boolean isOK) {
        this.isOK = isOK;
    }

}
