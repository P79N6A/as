package com.yaoguang.appcommon.phone.home.event;

/**
 * 提示首页刷新的
 * Created by zhongjh on 2017/8/31.
 */
public class HomeMessageEvent {

    public boolean isInitMessage() {
        return isInitMessage;
    }

    public void setInitMessage(boolean initMessage) {
        isInitMessage = initMessage;
    }

    public boolean isInitDialog() {
        return isInitDialog;
    }

    public void setInitDialog(boolean initDialog) {
        isInitDialog = initDialog;
    }

    /**
     * 判断是否初始化信息
     */
    private boolean isInitMessage;
    /**
     * 判断是否初始化弹窗
     */
    private boolean isInitDialog;

    public HomeMessageEvent(boolean isInitMessage, boolean isInitDialog) {
        this.isInitMessage = isInitMessage;
        this.isInitDialog = isInitDialog;
    }


}
