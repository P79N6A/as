package com.yaoguang.lib.common.mobile;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

/**
 * 有关包的工具
 * Created by zhongjh on 2017/12/21.
 */
public class PackageUtil {

    /**
     * 获取app当前版本号
     *
     * @param context 上下文
     * @return 版本号
     */
    public static String getPackageVersion(Context context) {

        String version = "1.0";

        //通过上下文获取Packagemanager
        PackageManager manager = context.getPackageManager();

        try {
            //通过manager获取package信息
            PackageInfo packageInfo = manager.getPackageInfo(context.getPackageName(), PackageManager.GET_ACTIVITIES);

            //获取了app版本号
            version = packageInfo.versionName;


        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return version;

    }


}
