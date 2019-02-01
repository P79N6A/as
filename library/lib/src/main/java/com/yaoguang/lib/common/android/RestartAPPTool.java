package com.yaoguang.lib.common.android;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;

import com.yaoguang.lib.base.BaseApplication;

/**
 * 此工具类用来重启APP，只是单纯的重启，不做任何处理。
 * Created by zhongjh on 2017/12/22.
 */
public class RestartAPPTool {


    private Handler handler = new Handler();

    /**
     * 重启整个APP
     */
    public void restartAPP() {
        // 重启app
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = BaseApplication.getInstance().getPackageManager()
                        .getLaunchIntentForPackage(BaseApplication.getInstance().getPackageName());
                PendingIntent restartIntent = PendingIntent.getActivity(BaseApplication.getInstance(), 0, intent, PendingIntent.FLAG_ONE_SHOT);
                AlarmManager mgr = (AlarmManager) BaseApplication.getInstance().getSystemService(Context.ALARM_SERVICE);
                assert mgr != null;
                mgr.set(AlarmManager.RTC, System.currentTimeMillis(), restartIntent); // 重启应用
                System.exit(0);
            }
        }, 500);
    }


}
