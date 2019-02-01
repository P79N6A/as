package com.yaoguang.appcommon.phone.activitys.splash;

import com.umeng.message.inapp.InAppMessageManager;
import com.umeng.message.inapp.UmengSplashMessageActivity;

public abstract class BaseSplashActivity extends UmengSplashMessageActivity {

    protected abstract String activityPath();

    @Override
    public boolean onCustomPretreatment() {
        InAppMessageManager mInAppMessageManager = InAppMessageManager.getInstance(this);
        mInAppMessageManager.setInAppMsgDebugMode(false);
        mInAppMessageManager.setMainActivityPath(activityPath());
        return super.onCustomPretreatment();
    }
}
