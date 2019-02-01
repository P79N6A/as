package com.yaoguang.driver.activitys;

import com.yaoguang.appcommon.activitys.BaseStartActivity;
import com.yaoguang.common.base.interfaces.BasePresenter;
import com.yaoguang.common.common.Constants;
import com.yaoguang.driver.main.MainActivity;

/**
 * Created by 韦理英
 * on 2017/6/14 0014.
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
        return Constants.APP_DRIVER;
    }



    @Override
    public BasePresenter getPresenter() {
        return null;
    }
}
