package com.yaoguang.appcommon.common.eventbus;

/**
 * Created by Administrator on 2017/7/13 0013.
 */

public class HomeMessageOrderChildEvent extends BaseEvent {

    public HomeMessageOrderChildEvent(String type) {
        super(type);
    }

    public HomeMessageOrderChildEvent(String type, Object object) {
        super(type, object);
    }
}
