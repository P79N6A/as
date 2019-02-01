package com.yaoguang.driver.phone.activitys.login;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;

import com.yaoguang.appcommon.phone.activitys.login.BaseLoginActivity;
import com.yaoguang.driver.phone.activitys.FestivalActivity;
import com.yaoguang.driver.phone.activitys.ForgetPasswordStep1Activity;
import com.yaoguang.driver.phone.activitys.RegisterDoorwayActivity;
import com.yaoguang.lib.base.interfaces.BasePresenter;
import com.yaoguang.lib.common.RegexValidator;
import com.yaoguang.driver.R;
import com.yaoguang.driver.phone.main.MainActivity;


/**
 * Created by 钟景华 on 2018/3/12.
 * 登录界面
 */
public class LoginActivity extends BaseLoginActivity {


    @Override
    public BasePresenter getPresenter() {
        if (mLoginPresenter == null)
            mLoginPresenter = new DLoginPresenterImpl(LoginActivity.this);
        return mLoginPresenter;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    /**
     * EasyPermissions
     *
     * @param activity 上一个activity
     * @param isIn     是否进入
     * @param message  进入登录页的时候，弹出的信息
     * @param url      地址
     */
    public static void newInstance(Activity activity, boolean isIn, String message, String url) {
        if (TextUtils.isEmpty(url)) {
            Intent intent = new Intent(activity, LoginActivity.class);
            if (message != null)
                intent.putExtra("message", message);
            activity.startActivity(intent);
            if (isIn) {
                activity.overridePendingTransition(R.anim.up_in, R.anim.up_out);
            } else {
                activity.overridePendingTransition(R.anim.up_in, R.anim.up_out);
            }
        } else {
            FestivalActivity.newInstance(activity, url, FestivalActivity.TYPE_LOGIN);
        }
    }

    @Override
    public void toRegisterActivityCustom() {
        RegisterDoorwayActivity.newInstance(this);
    }

    @Override
    public void toForgetPasswordActivityCustom() {
        //判断当前登录名是否是手机号，如果是就传过去
        String mobile = "";
        if (RegexValidator.isMobile(mInitialView.mACTVUserName.getText().toString())) {
            mobile = mInitialView.mACTVUserName.getText().toString();
        }
        ForgetPasswordStep1Activity.newInstance(this, mobile);
    }

    @Override
    public void customSetting() {

    }

    @Override
    public void toMainActivityCustom() {
        MainActivity.newInstance(LoginActivity.this, null);
        finish();
    }

    @Override
    public void startUpdatePasswordActivity(String id, String mobile, String oldpassWord) {

    }

    @Override
    public void startBindPhoneActivity(Object value, String strUsername, String strPassword) {

    }

    @Override
    public void startAuthenticationIngActivity(Object value) {

    }

    @Override
    public void startAuthenticationErrorActivity(Object value) {

    }

    @Override
    public void startAuthenticationActivity(Object value) {

    }

    @Override
    public void startGenerateTwoDimensionalCode(String userId) {

    }

}

