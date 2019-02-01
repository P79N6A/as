package com.yaoguang.driver.phone.my.setting;

import com.yaoguang.appcommon.html.HtmlFragment;
import com.yaoguang.appcommon.phone.my.setting.BaseSettingFragment;
import com.yaoguang.lib.BuildConfig;
import com.yaoguang.lib.common.constants.Constants;
import com.yaoguang.driver.phone.activitys.login.LoginActivity;
import com.yaoguang.driver.phone.my.aboutus.AboutUsFragment;
import com.yaoguang.driver.phone.my.setting.feedback.FeedbackFragment;
import com.yaoguang.driver.phone.my.setting.accountsecurity.AccountSecurityFragment;
import com.yaoguang.driver.phone.my.setting.push.PushSettingFragment;
import com.yaoguang.driver.phone.my.setting.usinghelp.UsingHelpFragment;

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
    protected void openAccountSecurityFragment() {
        start(AccountSecurityFragment.newInstance());
    }

    @Override
    protected void openUsingHelpFragment() {
        start(HtmlFragment.newInstance("使用帮助",  BuildConfig.ENDPOINT + "page/help/driver/help/help.html"), SINGLETOP);
//        start(UsingHelpFragment.newInstance());
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
