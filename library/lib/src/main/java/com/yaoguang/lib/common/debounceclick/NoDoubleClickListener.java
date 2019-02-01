package com.yaoguang.lib.common.debounceclick;

import android.os.SystemClock;
import android.view.View;


/**
 * 防止抖动处理
 * Created by zhongjh on 2017/10/27.
 */
public abstract class NoDoubleClickListener implements View.OnClickListener {
    protected static final String TAG = "NoDoubleClickListener";
    private static final int MIN_CLICK_DELAY_TIME = 1000;
    private long lastClickTime = 0;

    public abstract void onNoDoubleClick(View v);

    @Override
    public void onClick(View v) {
        long currentTime = SystemClock.uptimeMillis();
        if (currentTime - lastClickTime > MIN_CLICK_DELAY_TIME) {
            lastClickTime = currentTime;
            onNoDoubleClick(v);
        }
    }
}