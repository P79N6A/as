package com.yaoguang.appcommon.common.eventbus;

/**
 * Created by zhongjh on 2017/5/17.
 */
public class OrderDetailNodeEvent {

    /**
     * 设置当前界面是否刷新
     * @return
     */
    public boolean isRefresh() {
        return isRefresh;
    }

    public void setRefresh(boolean Refresh) {
        isRefresh = Refresh;
    }

    boolean isRefresh;

    public OrderDetailNodeEvent(boolean isRefresh) {
        this.isRefresh = isRefresh;
    }

}
