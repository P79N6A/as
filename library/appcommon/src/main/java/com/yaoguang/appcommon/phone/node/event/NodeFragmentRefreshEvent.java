package com.yaoguang.appcommon.phone.node.event;

/**
 * 刷新
 * Created by zhongjh on 2018/5/3.
 */
public class NodeFragmentRefreshEvent {

    public NodeFragmentRefreshEvent(boolean isTTS) {

        this.isTTs = isTTS;

    }

    private boolean isTTs; // 是否语音

    public boolean isTTs() {
        return isTTs;
    }

    public void setTTs(boolean TTs) {
        isTTs = TTs;
    }
}
