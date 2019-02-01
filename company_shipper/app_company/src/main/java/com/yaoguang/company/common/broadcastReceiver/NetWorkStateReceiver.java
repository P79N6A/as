package com.yaoguang.company.common.broadcastReceiver;

import android.content.Context;
import android.content.Intent;

import com.yaoguang.company.home.event.HomeFragmentEvent;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by zhongjh on 2018/12/21.
 */

public class NetWorkStateReceiver extends com.yaoguang.lib.broadcastReceiver.NetWorkStateReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);
        if (type == 0)
            EventBus.getDefault().post(new HomeFragmentEvent(type));
    }
}
