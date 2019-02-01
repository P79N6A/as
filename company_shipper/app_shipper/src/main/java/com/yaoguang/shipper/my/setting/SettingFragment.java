package com.yaoguang.shipper.my.setting;

import android.view.View;

import com.yaoguang.appcommon.html.HtmlFragment;
import com.yaoguang.appcommon.phone.my.setting.BaseSettingFragment;
import com.yaoguang.lib.BuildConfig;
import com.yaoguang.lib.appcommon.dialog.DialogHelper;
import com.yaoguang.lib.common.constants.Constants;
import com.yaoguang.shipper.activitys.login.LoginActivity;
import com.yaoguang.shipper.my.aboutus.AboutUsFragment;
import com.yaoguang.shipper.my.feedback.FeedbackFragment;
import com.yaoguang.shipper.my.setting.pushsetting.PushSettingFragment;


/**
 * Created by zhongjh on 2017/7/6.
 */
public class SettingFragment extends BaseSettingFragment {

    private DialogHelper mDialogHelper;

    public static SettingFragment newInstance() {
        return new SettingFragment();
    }

    @Override
    protected void setLoginFlag() {
        LoginActivity.newInstance(getActivity(), false, null, null);
        getActivity().finish();
    }

    public String getCodeType() {
        return Constants.APP_SHIPPER;
    }

    @Override
    public void init() {
        super.init();
        // 隐藏账户安全
        mDataBinding.llAccountSecurity.setVisibility(View.GONE);
    }


    @Override
    protected void openAccountSecurityFragment() {

    }


    @Override
    protected void openUsingHelpFragment() {
        start(HtmlFragment.newInstance("使用帮助",  BuildConfig.ENDPOINT + "page/help/owner/help/index.html"), SINGLETOP);
    }

    @Override
    public void onDestroy() {
        if (mDialogHelper != null) {
            mDialogHelper.hideDialog();
        }
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
        start(FeedbackFragment.newInstance(true), SINGLETOP);
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
