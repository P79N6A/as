package com.yaoguang.company.activitys;

import android.os.Bundle;

import com.yaoguang.appcommon.phone.activitys.start.BaseStartActivity;
import com.yaoguang.company.R;
import com.yaoguang.company.activitys.login.LoginActivity;
import com.yaoguang.lib.base.interfaces.BasePresenter;
import com.yaoguang.lib.common.constants.Constants;

/**
 * on 2017/6/14 0014.
 */

public class StartActivity extends BaseStartActivity {

    @Override
    public void startMainActivity(String url) {
//        MainActivity.newInstance(this, url);
        com.yaoguang.company.camer.MainActivity.newInstance(this);
        this.finish();
    }

    @Override
    public void startLoginActivity(String url) {
//        LoginActivity.newInstance(this, true, null, url);
        com.yaoguang.company.camer.MainActivity.newInstance(this);
        this.finish();
    }

    @Override
    public void startGuidanceActivity(String url) {
        GuidanceActivity.newInstance(this, url);
        this.finish();
    }

    @Override
    public String getCodtType() {
        return Constants.APP_COMPANY;
    }

    @Override
    public BasePresenter getPresenter() {
        return null;
    }

}
