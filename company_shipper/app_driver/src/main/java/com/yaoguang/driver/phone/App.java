package com.yaoguang.driver.phone;

import android.content.Context;
import android.content.res.Configuration;
import android.support.multidex.MultiDex;

import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechUtility;
import com.tencent.bugly.Bugly;
import com.tencent.smtt.sdk.QbSdk;
import com.yaoguang.appcommon.push.BasePushApplication;
import com.yaoguang.appcommon.phone.contact2.entity.RelevanceMessage;
import com.yaoguang.driver.BuildConfig;
import com.yaoguang.driver.R;
import com.yaoguang.lib.base.BaseApplication;
import com.yaoguang.lib.common.constants.Constants;
import com.yaoguang.lib.common.CrashHandler;
import com.yaoguang.lib.common.file.FileDownloaderUtils;
import com.yaoguang.lib.common.PreferenceManager;
import com.yaoguang.lib.common.SPUtils;
import com.yaoguang.lib.common.ULog;
import com.yaoguang.lib.common.XLogSetting;
import com.yaoguang.lib.qinui.QiNiuManager;
import com.yaoguang.driver.phone.activitys.login.LoginActivity;
import com.yaoguang.greendao.DbCore;
import com.yaoguang.greendao.entity.driver.DriverOrderProgressWrapper;
import com.yaoguang.map.location.impl.LocationManager;

import io.rong.imkit.RongIM;
import io.rong.push.RongPushClient;
import io.rong.push.common.RongException;


/**
 * 基类Application
 * Created by wly on 2017/3/27.
 */
public class App extends BasePushApplication {

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    @Override
    public void startLogin(String message) {
        LoginActivity.newInstance(getCurActivity(), true, message, null);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        // 初始化sp
        SPUtils.getInstance().init(Constants.APP_DRIVER);
        // 设置用户类型
        setAppType(Constants.APP_DRIVER);

        // 初始化XLog
        XLogSetting.init();
        // 异常保存本地
        CrashHandler.getInstance().init(this);
        // SP初始化
        PreferenceManager.init(App.this);
        // 初始化七牛
        QiNiuManager.getInstance().init();
        // 定位初始化
//        locationInit();
        //初始化数据库
        initDB();
        // 下载工具初始化
        FileDownloaderUtils.init(this);
        // bugly param true=线上 false=调试
//            if (!BuildConfig.DEBUG) {
        Bugly.init(getApplicationContext(), "8b098c4578", BuildConfig.DEBUG);
//            }
        // 融云
        initRongIM();

        //讯飞语音初始化
        SpeechUtility.createUtility(getApplicationContext(), SpeechConstant.APPID + "=" + getResources().getString(R.string.speechapp_id));

        QbSdk.initX5Environment(this, null);
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        //解决 method ID not in [0, 0xffff]: 65536 的问题
        MultiDex.install(this);
    }

    /**
     * 初始化数据库
     */
    public void initDB() {
        DbCore.init(this);
        DbCore.enableQueryBuilderLog();
    }

    // 融云 api https://github.com/XuptScore-client/IM/blob/master/app/src/main/java/com/vivavideo/imkit/RongIMClientWrapper.java  更全点：https://community.apicloud.com/bbs/thread-20455-1-1.html
    private void initRongIM() {
        RongPushClient.registerMiPush(this, "2882303761517830510", "5261783093510");
        //融云初始化这个需要全部线上的appKey，测试的话，才是根据环境决定appKey
        if (BuildConfig.DEBUG) {
            RongIM.init(this, "pvxdm17jpimmr");
        } else {
            RongIM.init(this, "uwd1c0sxuvcc1");
        }
        RongIM.registerMessageType(RelevanceMessage.class);
//        RongIM.registerMessageType(ContactMessage2.class);
    }


}
