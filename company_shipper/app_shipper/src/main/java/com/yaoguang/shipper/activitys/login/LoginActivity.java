package com.yaoguang.shipper.activitys.login;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;

import com.yaoguang.appcommon.phone.activitys.login.BaseLoginActivity;
import com.yaoguang.lib.base.interfaces.BasePresenter;
import com.yaoguang.lib.common.RegexValidator;
import com.yaoguang.lib.appcommon.utils.TextViewUtils;
import com.yaoguang.greendao.entity.UserOwner;
import com.yaoguang.shipper.R;
import com.yaoguang.shipper.activitys.FestivalActivity;
import com.yaoguang.shipper.activitys.MainActivity;
import com.yaoguang.shipper.activitys.forgetpassword.ForgetPasswordStep1Activity;
import com.yaoguang.shipper.activitys.register.RegisterActivity;
import com.yaoguang.shipper.activitys.register2.RegisterAuthenticationErrorActivity;
import com.yaoguang.shipper.activitys.register2.RegisterAuthenticationIngActivity;
import com.yaoguang.shipper.activitys.register2.RegisterAuthenticationSupplementActivity;
import com.yaoguang.shipper.activitys.register2.RegisterDoorwayActivity;


/**
 * Created by 钟景华 on 2017/3/24.
 * 登录界面
 */
public class LoginActivity extends BaseLoginActivity<UserOwner,UserOwner> {

    @Override
    public BasePresenter getPresenter() {
        if (mLoginPresenter == null)
            mLoginPresenter = new LoginPresenterImpl(LoginActivity.this);
        return mLoginPresenter;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    /**
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
//        RegisterActivity.newInstance(this);
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
        TextViewUtils.setAlphaNumeric(mInitialView.mACTVUserName);
        mInitialView.mACTVUserName.setHint("请输入手机号/登录名");
    }

    @Override
    public void toMainActivityCustom() {
        MainActivity.newInstance(LoginActivity.this, null);
        LoginActivity.this.finish();
    }

    @Override
    public void startUpdatePasswordActivity(String id, String mobile, String oldpassWord) {

    }

    @Override
    public void startBindPhoneActivity(UserOwner value, String strUsername, String strPassword) {
        //货主端是没有绑定的
    }

    @Override
    public void startAuthenticationIngActivity(UserOwner value) {
        RegisterAuthenticationIngActivity.newInstance(LoginActivity.this, value, mInitialView.mACTVUserName.getText().toString(), mInitialView.mETPassWord.getText().toString());
    }

    @Override
    public void startAuthenticationErrorActivity(UserOwner value) {
        RegisterAuthenticationErrorActivity.newInstance(LoginActivity.this, value, mInitialView.mACTVUserName.getText().toString(), mInitialView.mETPassWord.getText().toString());
    }

    @Override
    public void startAuthenticationActivity(UserOwner value) {
        RegisterAuthenticationSupplementActivity.newInstance(LoginActivity.this, value, mInitialView.mACTVUserName.getText().toString(), mInitialView.mETPassWord.getText().toString());
    }

    @Override
    public void startGenerateTwoDimensionalCode(String userId) {
        // 货主端没有授权登录
    }


}