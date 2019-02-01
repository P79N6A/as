package com.yaoguang.lib.base;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;

import java.util.Stack;


/**
 * Created by 韦理英
 * on 2017/6/8 0008.
 * Update by zhongjh
 * on 2017/12/22
 */
public abstract class BaseApplication extends Application {
    public Stack<Activity> mActivitys;

    private static String appType;
    private static BaseApplication instance;

    public boolean isRequestAPI() {
        return isRequestAPI;
    }

    public void setRequestAPI(boolean requestAPI) {
        isRequestAPI = requestAPI;
    }

    public String getSSID() {
        return SSID;
    }

    public void setSSID(String SSID) {
        this.SSID = SSID;
    }

    // 是否允许请求网络
    private boolean isRequestAPI = true;
    // 网络mac地址
    private String SSID = "";

    public abstract void startLogin(String message);

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        mActivitys = new Stack<>();
        registerActivityLifecycleCallbacks(new SwitchBackgroundCallbacks());
    }

    public static BaseApplication getInstance() {
        return instance;
    }

    public static String getAppType() {
        return appType;
    }

    public static void setAppType(String appType) {
        BaseApplication.appType = appType;
    }

    private class SwitchBackgroundCallbacks implements ActivityLifecycleCallbacks {

        @Override
        public void onActivityCreated(Activity activity, Bundle bundle) {
            mActivitys.add(activity);
        }

        @Override
        public void onActivityStarted(Activity activity) {

        }

        @Override
        public void onActivityResumed(Activity activity) {

        }

        @Override
        public void onActivityPaused(Activity activity) {

        }

        @Override
        public void onActivityStopped(Activity activity) {

        }

        @Override
        public void onActivitySaveInstanceState(Activity activity, Bundle bundle) {

        }

        @Override
        public void onActivityDestroyed(Activity activity) {
            mActivitys.remove(activity);
        }
    }

    /**
     * 结束所有Activity
     */
    public void finishAllActivity() {
        if (mActivitys == null) {
            return;
        }
        for (Activity activity : mActivitys) {
            activity.finish();
        }
    }

    /**
     * 关闭所有Activity,除了第一个
     */
    public void finishAllActivityExceptFirst() {
        if (mActivitys == null) {
            return;
        }
        for (Activity activity : mActivitys) {
            if (activity != getCurActivity())
                activity.finish();
        }
    }

    /**
     * 获取当前的Activity
     */
    public Activity getCurActivity() {
        return mActivitys.lastElement();
    }
}
