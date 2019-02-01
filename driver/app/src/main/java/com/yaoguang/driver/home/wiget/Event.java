package com.yaoguang.driver.home.wiget;

import com.yaoguang.appcommon.common.eventbus.HomeEvent;

import org.greenrobot.eventbus.EventBus;

import static com.yaoguang.common.common.Constants.FLAG_REFRESH_TOOLBAR;

/**
 * Created by Administrator on 2017/9/21 0021.
 */

public class Event {
    /**
     * 通知更新司机状态
     */
    public static void updateDriverStatus() {
        // 通知更新司机状态
        EventBus.getDefault().postSticky(new HomeEvent(FLAG_REFRESH_TOOLBAR));
    }
}
