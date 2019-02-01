package com.yaoguang.appcommon.common.eventbus;

/**
 * Created by Administrator on 2017/7/13 0013.
 */

public class MessageOrderChildEvent extends BaseEvent {

    public MessageOrderChildEvent(String type) {
        super(type);
    }

    public MessageOrderChildEvent(String type, Object object) {
        super(type, object);
    }
}
