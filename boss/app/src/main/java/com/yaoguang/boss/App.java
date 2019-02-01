package com.yaoguang.boss;

import android.app.Application;
import android.os.Handler;

import com.tencent.bugly.Bugly;
import com.yaoguang.common.common.Constants;
import com.yaoguang.common.common.CrashHandler;
import com.yaoguang.common.common.SPUtils;
import com.yaoguang.common.common.XLogSetting;


/**
 * Created by Administrator on 2017/11/27 0027.
 */
public class App extends Application {
    public static Handler handler = new Handler();

    @Override
    public void onCreate() {

        // 初始化XLog
        XLogSetting.init();
        // 异常保存本地
        CrashHandler.getInstance().init(this);
        // 初始化sp
        SPUtils.getInstance().init(Constants.APP_BOSS);
        // bugly param true=线上 false=调试
        if (!BuildConfig.DEBUG) {
            Bugly.init(getApplicationContext(), "76be25920c", BuildConfig.DEBUG);
        }
        super.onCreate();
    }
}

