package com.yaoguang.driver;

import android.content.Context;
import android.content.res.Configuration;
import android.os.Handler;
import android.support.multidex.MultiDex;

import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechUtility;
import com.tencent.bugly.Bugly;
import com.yaoguang.appcommon.common.base.push.BasePushApplication;
import com.yaoguang.common.base.BaseApplication;
import com.yaoguang.common.common.Constants;
import com.yaoguang.common.common.CrashHandler;
import com.yaoguang.common.common.FileDownloaderUtils;
import com.yaoguang.common.common.PreferenceManager;
import com.yaoguang.common.common.SPUtils;
import com.yaoguang.common.common.ULog;
import com.yaoguang.common.common.XLogSetting;
import com.yaoguang.common.qinui.QiNiuManager;
import com.yaoguang.driver.activitys.LoginActivity;
import com.yaoguang.greendao.DbCore;
import com.yaoguang.greendao.entity.DriverOrderProgressWrapper;
import com.yaoguang.map.common.TTSController;
import com.yaoguang.map.location.impl.LocationHistoryManager;
import com.yaoguang.map.location.impl.LocationManager;
import com.yaoguang.taobao.wantu.impl.WanTuManager;


/**
 * 基类Application
 * Created by wly on 2017/3/27.
 */

public class App extends BasePushApplication {
    public static Handler handler = new Handler();
    public static App mApp;

    public static App getInstance() {
        return mApp;
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    public final static LocationHistoryManager locationHistoryManager = new LocationHistoryManager();

    @Override
    public void startLogin(String message) {
        LoginActivity.newInstance(getCurActivity(), true, message, null);
    }

    @Override
    public void onCreate() {
        super.onCreate();

        try {
            mApp = this;
            // 设置用户类型
            setAppType(Constants.APP_DRIVER);
            // 初始化XLog
            XLogSetting.init();
            // 异常保存本地
            CrashHandler.getInstance().init(this);
            // 初始化sp
            SPUtils.getInstance().init(Constants.APP_DRIVER);
            // Fragmentation
//            FragmentationUtils.init();
            // SP初始化
            PreferenceManager.init(App.this);
            // 初始化七牛
            QiNiuManager.getInstance().init();
            // 上传初始化
            WanTuManager.getInstance().init(App.this);
            // 定位初始化
            locationInit();
            //讯飞语音初始化
            SpeechUtility.createUtility(this, SpeechConstant.APPID + "=" + getString(R.string.speechapp_id));
            //初始化数据库
            initDB();
            // 下载工具初始化
            FileDownloaderUtils.init(this);
            // tts初始化
            TTSController.getInstance(this).init();
            // bugly param true=线上 false=调试
            Bugly.init(getApplicationContext(), "8b098c4578", BuildConfig.DEBUG);
        } catch (Exception e) {
            e.printStackTrace();
            ULog.i(e.toString());
        }
    }

    /**
     * 描    述：只做定位初始化,用完就关闭定位
     * 作    者：韦理英
     */
    public static void locationInit() {
        final LocationManager locationManager = new LocationManager();
        locationManager.init(BaseApplication.getInstance().getBaseContext());
        locationManager.setComeback(location -> {
            locationManager.save(location);
            ULog.i("已保存定位信息：" + location.getLat() + "  " + location.getLon());

            locationManager.destroyLocation();
        });
    }

    /**
     * 描    述：定位轨迹保存，当司机是出车状态时
     * 作    者：韦理英
     *
     * @param value 订单
     */
    public static void launchLocationHistory(DriverOrderProgressWrapper value) {
        locationHistoryManager.driverOrderProgressWrapper = value;
        locationHistoryManager.initCache(value.getNodeId());
        locationHistoryManager.startLocation(BaseApplication.getInstance().getBaseContext());
    }

    public void pushTts(String msg) {
        sayTts(msg);
    }

    /**
     * 描    述：推送声音提醒
     * 作    者：韦理英
     *
     * @param msg 推送信息
     */
    public static void sayTts(String msg) {
        // 语音播报
        TTSController.getInstance(getInstance()).onGetNavigationText(1, msg);
    }


    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        //解决 method ID not in [0, 0xffff]: 65536 的问题
        MultiDex.install(this);
    }

    /* *
     * 初始化数据库
     */
    public void initDB() {
        DbCore.init(this);
        DbCore.enableQueryBuilderLog();
    }

    public void toLoginActivity() {
        LoginActivity.newInstance(getCurActivity(), true, null, null);
        getCurActivity().finish();
    }
}
