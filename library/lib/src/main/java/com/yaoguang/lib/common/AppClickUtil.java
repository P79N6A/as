package com.yaoguang.lib.common;

/**
 * Created by
 * 韦理英
 * on 2017/7/27 0027.
 */

public class AppClickUtil {
    private static final int MIN_DELAY_TIME= 500;  // 两次点击间隔不能少于1000ms
    private static long lastClickTime;

    public static boolean isDuplicateClick() {
        boolean flag = true;
        long currentClickTime = System.currentTimeMillis();
        if ((currentClickTime - lastClickTime) >= MIN_DELAY_TIME) {
            flag = false;
        }
        lastClickTime = currentClickTime;
        return flag;
    }
}
