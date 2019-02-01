package com.yaoguang.shipper.activitys;

import com.yaoguang.appcommon.phone.activitys.start.BaseStartActivity;
import com.yaoguang.lib.base.interfaces.BasePresenter;
import com.yaoguang.lib.common.constants.Constants;
import com.yaoguang.shipper.activitys.login.LoginActivity;

/**
 * Created by 韦理英
 * on 2017/6/14 0014.
 * Update by zhongjh
 * on 2017-12-26
 */

public class StartActivity extends BaseStartActivity {

    @Override
    public void startMainActivity(String url) {
        MainActivity.newInstance(this, url);
        this.finish();
    }

    @Override
    public void startLoginActivity(String url) {
        LoginActivity.newInstance(this, true, null, url);
        this.finish();
    }

    @Override
    public void startGuidanceActivity(String url) {
        GuidanceActivity.newInstance(this, url);
        this.finish();
    }

    @Override
    public String getCodtType() {
        return Constants.APP_SHIPPER;
    }

    @Override
    public BasePresenter getPresenter() {
        return null;
    }
}
