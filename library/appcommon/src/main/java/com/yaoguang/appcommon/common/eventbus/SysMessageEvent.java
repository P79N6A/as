package com.yaoguang.appcommon.common.eventbus;

/**
 * Created by Administrator on 2017/7/13 0013.
 */

public class SysMessageEvent extends BaseEvent {

    public SysMessageEvent(String type) {
        super(type);
    }

    public SysMessageEvent(String type, Object object) {
        super(type, object);
    }
}