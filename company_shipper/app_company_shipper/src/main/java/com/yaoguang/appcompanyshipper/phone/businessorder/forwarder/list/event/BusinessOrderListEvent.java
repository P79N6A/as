package com.yaoguang.appcompanyshipper.phone.businessorder.forwarder.list.event;

/**
 * 用于刷新列表
 * Created by zhongjh on 2017/8/17.
 */
public class BusinessOrderListEvent {

    public boolean isRefresh() {
        return isRefresh;
    }

    private boolean isRefresh;

    public BusinessOrderListEvent(boolean isRefresh) {
        this.isRefresh = isRefresh;
    }


}
