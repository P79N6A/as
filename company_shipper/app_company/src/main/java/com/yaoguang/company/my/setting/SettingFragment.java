package com.yaoguang.company.my.setting;

import android.view.View;

import com.yaoguang.appcommon.html.HtmlFragment;
import com.yaoguang.appcommon.phone.my.setting.BaseSettingFragment;
import com.yaoguang.lib.BuildConfig;
import com.yaoguang.lib.common.constants.Constants;
import com.yaoguang.company.activitys.login.LoginActivity;
import com.yaoguang.company.my.aboutus.AboutUsFragment;
import com.yaoguang.company.my.feedback.FeedbackFragment;
import com.yaoguang.company.my.setting.pushsetting.PushSettingFragment;


/**
 * Created by zhongjh on 2017/7/6.
 */
public class SettingFragment extends BaseSettingFragment {

    public static SettingFragment newInstance() {
        return new SettingFragment();
    }

    @Override
    public void init() {
        super.init();
        mDataBinding.rlAccountSecurity.setVisibility(View.GONE);
    }

    @Override
    protected void setLoginFlag() {
        LoginActivity.newInstance(getActivity(), false, null, null);
        getActivity().finish();
    }

    public String getCodeType() {
        return Constants.APP_COMPANY;
    }

    @Override
    protected void openAccountSecurityFragment() {

    }

    @Override
    protected void openUsingHelpFragment() {

        start(HtmlFragment.newInstance("使用帮助",  BuildConfig.ENDPOINT + "page/help/company/help/index.html"), SINGLETOP);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void openAboutUsFragment() {
        //关于货云集
        start(AboutUsFragment.newInstance(true), SINGLETOP);
    }

    @Override
    protected void openFeedbackFragment() {
        //意见反馈
        start(FeedbackFragment.newInstance(), SINGLETOP);
    }

    @Override
    protected void openPushSettingFramgnet() {
        //消息推送
        start(PushSettingFragment.newInstance(), SINGLETOP);
    }


    @Override
    public void showPushStatus(boolean value) {

    }

}
