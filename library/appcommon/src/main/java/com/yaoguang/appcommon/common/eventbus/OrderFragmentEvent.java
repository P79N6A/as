package com.yaoguang.appcommon.common.eventbus;

/**
 * Created by Administrator on 2017/7/14 0014.
 */
public class OrderFragmentEvent extends BaseEvent {
    public OrderFragmentEvent(Object object, String type) {
        super(type, object);
    }

    public OrderFragmentEvent(String type) {
        super(type);
    }
}
