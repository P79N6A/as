package com.yaoguang.driver.phone.activitys;

import com.umeng.analytics.MobclickAgent;
import com.yaoguang.appcommon.phone.activitys.start.BaseStartActivity;
import com.yaoguang.driver.phone.activitys.login.LoginActivity;
import com.yaoguang.lib.base.interfaces.BasePresenter;
import com.yaoguang.lib.common.constants.Constants;
import com.yaoguang.driver.phone.main.MainActivity;

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

    // BaseActivity中统一调用MobclickAgent 类的 onResume/onPause 接口
    // 子类中无需再调用
    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onResume(this); // 基础指标统计，不能遗漏
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPause(this); // 基础指标统计，不能遗漏
    }
}
