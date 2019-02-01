package com.yaoguang.lib.common.displaymetrics;

import android.content.Context;
import android.graphics.Point;
import android.util.DisplayMetrics;
import android.view.WindowManager;

import com.yaoguang.lib.common.constants.Constants;
import com.yaoguang.lib.common.SPUtils;

/**
 * 获取手机分辨率的宽高，先从缓存获取，如果缓存都没有，则重新计算，并且保存
 * Created by zhongjh on 2017/10/25.
 */
public class DisplayMetricsSPUtils {

    /**
     * 获取屏幕分辨率-高
     *
     * @param context 上下文
     * @return 高
     */
    public static int getScreenHeight(Context context) {
        if (SPUtils.getInstance().getInt(Constants.ScreenHeight, 0) == 0) {
            WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
            Point size = new Point();
            wm.getDefaultDisplay().getSize(size);
            int screenWidth = size.x;
            int screenHeight = size.y;
            SPUtils.getInstance().put(Constants.ScreenHeight, screenHeight);
            SPUtils.getInstance().put(Constants.ScreenWidth, screenWidth);
        }
        return SPUtils.getInstance().getInt(Constants.ScreenHeight, 0);
    }

    /**
     * 获取屏幕分辨率- 宽
     *
     * @param context 上下文
     * @return 宽
     */
    public static int getScreenWidth(Context context) {
        if (SPUtils.getInstance().getInt(Constants.ScreenWidth, 0) == 0) {
//            WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
//            Point size = new Point();
//            wm.getDefaultDisplay().getSize(size);
//            int screenWidth = size.x;
//            int screenHeight = size.y;
            DisplayMetrics dm = context.getResources().getDisplayMetrics();
            int screenWidth = dm.widthPixels;
            int screenHeight = dm.heightPixels;
            SPUtils.getInstance().put(Constants.ScreenHeight, screenHeight);
            SPUtils.getInstance().put(Constants.ScreenWidth, screenWidth);
        }
        return SPUtils.getInstance().getInt(Constants.ScreenWidth, 0);
    }

    /**
     * 获取屏幕状态栏高度
     *
     * @param context 上下文
     * @return 高度
     */
    public static int getStatusBarHeight(Context context) {
        if (SPUtils.getInstance().getInt(Constants.StatusBarHeight, 0) == 0) {
            int statusBarHeight = 0;
            int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
            if (resourceId > 0) {
                statusBarHeight = context.getResources().getDimensionPixelSize(resourceId);
            }
            SPUtils.getInstance().put(Constants.StatusBarHeight, statusBarHeight);
        }
        return SPUtils.getInstance().getInt(Constants.StatusBarHeight, 0);
    }

}
