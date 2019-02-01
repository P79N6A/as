package com.yaoguang.appcommon.common.eventbus;


/**
 * 作者：韦理英
 * 时间： 2017/5/8 0008.
 */

public class HomeEvent extends BaseEvent{
    public HomeEvent(String type, Object object) {
        super(type, object);
    }

    public HomeEvent(String type) {
        super(type);
    }
}
