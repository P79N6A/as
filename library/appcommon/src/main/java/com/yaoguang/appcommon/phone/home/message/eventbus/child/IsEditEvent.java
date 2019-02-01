package com.yaoguang.appcommon.phone.home.message.eventbus.child;

/**
 * 通知是否开启编辑模式
 * Created by zhongjh on 2017/7/19.
 */
public class IsEditEvent {

    private boolean isEdit;

    public IsEditEvent(boolean isEdit) {
        this.isEdit = isEdit;
    }

    public boolean getIsEdit() {
        return isEdit;
    }

}
