package com.yaoguang.company;

import android.content.Context;
import android.support.multidex.MultiDex;

import com.elvishew.xlog.XLog;
import com.tencent.bugly.Bugly;
import com.yaoguang.appcommon.push.BasePushApplication;
import com.yaoguang.lib.common.constants.Constants;
import com.yaoguang.lib.common.CrashHandler;
import com.yaoguang.lib.common.file.FileDownloaderUtils;
import com.yaoguang.lib.common.PreferenceManager;
import com.yaoguang.lib.common.SPUtils;
import com.yaoguang.lib.common.XLogSetting;
import com.yaoguang.lib.common.displaymetrics.DisplayMetricsSPUtils;
import com.yaoguang.appcommon.common.eventbus.WatherEvent;
import com.yaoguang.lib.qinui.QiNiuManager;
import com.yaoguang.company.activitys.login.LoginActivity;
import com.yaoguang.greendao.DbCore;
import com.yaoguang.datasource.common.DataStatic;
import com.yaoguang.map.location.impl.LocationManager;

import org.greenrobot.eventbus.EventBus;


/**
 * 基类Application
 * Created by zhongjh on 2017/3/27.
 */
public class App extends BasePushApplication {

    @Override
    public void startLogin(String message) {
        LoginActivity.newInstance(getCurActivity(), true, message,null);
    }

    @Override
    public void onCreate() {
        super.onCreate();

        try {
            // 初始化XLog
            XLogSetting.init();
            // 打印信息
            showLog();
            // 异常保存本地
            CrashHandler.getInstance().init(this);
            // 初始化sp
            SPUtils.getInstance().init(Constants.APP_COMPANY);
            // 初始化缓存数据
            DataStatic.getInstance().init(Constants.APP_COMPANY);
            // Fragmentation
//            FragmentationUtils.init();
            // SP初始化
            PreferenceManager.init(App.this);
            // 初始化七牛
            QiNiuManager.getInstance().init();
            // 初始化数据库
            initDB();
            // 第一次初始化屏幕信息并且保存进缓存
            initWindow();
            // 第一次初始化状态栏信息并且保存进缓存
            initStatusBarHeight();
            // 下载工具初始化
            FileDownloaderUtils.init(this);
            // 设置用户类型，用于权限包路径的
            setAppType(Constants.APP_COMPANY);
            // 全量更新
            // bugly param true=线上 false=调试
            if (!BuildConfig.DEBUG) {
                Bugly.init(getApplicationContext(), "a83fe12101", true);
            }
        } catch (Exception e) {
            e.printStackTrace();
            XLog.i(e.toString());
        }
    }

    private void showLog() {
        XLog.i("服务器Ip:" + com.yaoguang.lib.BuildConfig.ENDPOINT);
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

    /**
     * 第一次初始化屏幕信息并且保存进缓存
     */
    public void initWindow() {
        DisplayMetricsSPUtils.getScreenHeight(App.this);
        DisplayMetricsSPUtils.getScreenWidth(App.this);
    }

    /**
     * 第一次初始化状态栏信息并且保存进缓存
     */
    public void initStatusBarHeight() {
        DisplayMetricsSPUtils.getStatusBarHeight(App.this);
    }

}
