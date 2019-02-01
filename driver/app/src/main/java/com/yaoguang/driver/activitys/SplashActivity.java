package com.yaoguang.driver.activitys;

import android.app.Activity;
import android.content.Intent;

import com.yaoguang.appcommon.activitys.splash.BaseSplashActivity;

/**
 * Created by 韦理英
 * on 2017/6/8 0008.
 */

public class SplashActivity extends BaseSplashActivity {

    @Override
    protected String activityPath() {
        return "com.yaoguang.driver.activitys.StartActivity";
    }
}
