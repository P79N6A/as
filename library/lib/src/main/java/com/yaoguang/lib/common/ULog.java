package com.yaoguang.lib.common;

import android.util.Log;

import com.tencent.bugly.crashreport.BuglyLog;
import com.yaoguang.lib.BuildConfig;

/**
 * 日志上传工具
 * Created by wly on 2017/12/22 0022.
 */

public class ULog {
    public static String TAG = "ULog";
    private static final boolean DEBUG = BuildConfig.DEBUG;

    public static void i(String log) {
        if (DEBUG) Log.i(TAG, log);
        else BuglyLog.i(TAG, log);
    }

    public static void v(String log) {
        if (DEBUG) Log.v(TAG, log);
        else BuglyLog.v(TAG, log);
    }

    public static void e(String log) {
        if (DEBUG) Log.e(TAG, log);
        else BuglyLog.e(TAG, log);
    }

    public static void e(Throwable e) {
        if (DEBUG) Log.e(TAG, e.toString());
        else BuglyLog.e(TAG, e.toString());
    }

    public static void d(String log) {
        if (DEBUG) Log.d(TAG, log);
        else BuglyLog.d(TAG, log);
    }

}
