package com.yaoguang.driver.my.setting;

import com.yaoguang.common.common.Constants;
import com.yaoguang.driver.activitys.LoginActivity;
import com.yaoguang.driver.my.aboutus.AboutUsFragment;
import com.yaoguang.driver.my.feedback.FeedbackFragment;
import com.yaoguang.driver.my.setting.push.PushSettingFragment;

/**
 * 设置
 * Created by wly on 2017/7/6.
 */
public class SettingFragment extends BaseSettingFragment {

    public static SettingFragment newInstance() {
        return new SettingFragment();
    }

    @Override
    protected void setLoginFlag() {
        if (getActivity() == null) return;
        LoginActivity.newInstance(getActivity(), true, null, null);
        getActivity().finish();
    }

    @Override
    protected void openAboutUsFragment() {
        start(AboutUsFragment.newInstance());
    }

    @Override
    protected void openFeedbackFragment() {
        start(FeedbackFragment.newInstance());
    }

    @Override
    protected void openPushSettingFramgnet() {
        start(PushSettingFragment.newInstance());
    }

    public String getCodeType() {
        return Constants.APP_DRIVER;
    }

    @Override
    @Deprecated
    public void showPushStatus(boolean value) {

    }
}
