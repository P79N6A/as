package com.yaoguang.appcommon.common.eventbus;

/**
 * Created by 韦理英
 * on 2017/7/14 0014.
 * <p>
 * update zhongjh
 * data 2018-04-10
 */
public class BaseEvent {
    private String type;
    private Object object;

    public BaseEvent() {
    }

    public BaseEvent(String type) {
        this.type = type;
    }

    public BaseEvent(String type, Object object) {
        this.object = object;
        this.type = type;
    }

    public Object getObject() {
        return object;
    }

    public String getType() {
        return type;
    }
}
