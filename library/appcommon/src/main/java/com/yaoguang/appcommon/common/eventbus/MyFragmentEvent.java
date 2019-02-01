package com.yaoguang.appcommon.common.eventbus;

/**
 * Created by 韦理英
 * on 2017/7/14 0014.
 */

public class MyFragmentEvent extends BaseEvent {

    public MyFragmentEvent(Object object, String type) {
        super(type, object);
    }

    public MyFragmentEvent(String type) {
        super(type);
    }
}
