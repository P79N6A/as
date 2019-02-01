package com.yaoguang.appcommon.phone.my.my.event;

import com.yaoguang.appcommon.common.eventbus.BaseEvent;
import com.yaoguang.appcommon.common.finalrequest.DriverRequest;

/**
 * 司机端-我的-有关event和requset的有关常量
 * Created by zhongjh on 2018/6/7.
 */
public class MyEvent extends BaseEvent {

    public final static int UPDATE = DriverRequest.REQUESTCODE_MY + 1;
    public final static int FLAG_SIGN = DriverRequest.REQUESTCODE_MY + 2;
    public final static int FLAG_AVATAR = DriverRequest.REQUESTCODE_MY + 3;
    public final static int FLAG_UPDATE_AVATAR = DriverRequest.REQUESTCODE_MY + 31;
    public final static int EDIT_BACKGROUND_PHOTO = DriverRequest.REQUESTCODE_MY + 5;
    // 平台公告的未读消息的request,也是event的type识别
    public static final int REFRESH_UNREAD_COUNT = DriverRequest.REQUESTCODE_MY + 4;
    // 我的关联 未读消息
    public static final int REFRESH_UNREAD_CONTACT_COUNT = DriverRequest.REQUESTCODE_MY + 6;

    public MyEvent(String type) {
        super(type);
    }
}
