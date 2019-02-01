package com.yaoguang.company.activitys.login;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;

import com.yaoguang.appcommon.phone.activitys.login.BaseLoginActivity;
import com.yaoguang.company.activitys.bindphone.BindPhoneActivity;
import com.yaoguang.company.activitys.FestivalActivity;
import com.yaoguang.company.activitys.MainActivity;
import com.yaoguang.company.activitys.UpdatePasswordActivity;
import com.yaoguang.company.activitys.qrcode.QRCodeActivity;
import com.yaoguang.greendao.entity.AppUserWrapper;
import com.yaoguang.lib.appcommon.dialog.helper.CommonDialog;
import com.yaoguang.lib.appcommon.dialog.DialogHelper;
import com.yaoguang.lib.appcommon.utils.TextViewUtils;
import com.yaoguang.lib.base.interfaces.BasePresenter;
import com.yaoguang.lib.common.RegexValidator;
import com.yaoguang.company.R;
import com.yaoguang.company.activitys.forgetpassword.ForgetPasswordStep1Activity;
import com.yaoguang.company.activitys.register2.RegisterAuthenticationErrorActivity;
import com.yaoguang.company.activitys.register2.RegisterAuthenticationIngActivity;
import com.yaoguang.company.activitys.register2.RegisterAuthenticationSupplementActivity;
import com.yaoguang.company.activitys.register2.RegisterDoorwayActivity;
import com.yaoguang.greendao.entity.UserApply;


/**
 * Created by 钟景华 on 2017/3/24.
 * 登录界面
 */
public class LoginActivity extends BaseLoginActivity<UserApply, AppUserWrapper> {

    DialogHelper dialogHelper;

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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case BindPhoneActivity.BIND_PHONE_REQUEST_CODE: // 第一次登录绑定手机号后的返回
                    // 重新登录
                    mInitialView.cpbSubmit.performClick();
                    break;
                case UpdatePasswordActivity.UPDATE_PASSWORD_REQUEST_CODE:// 第一次登录设置密码后的返回
                    // 赋值手机
                    mInitialView.mACTVUserName.setText(data.getStringExtra(BUNDLE_KEY_MOBILE));
                    // 赋值密码
                    mInitialView.mETPassWord.setText(data.getStringExtra(BUNDLE_KEY_NEWPASS));
                    // 重新登录
                    mInitialView.cpbSubmit.performClick();
                    break;
                case QRCodeActivity.QR_CODE_CODE: // 第一次登录绑定手机号后的返回
                    // 重新登录
                    mInitialView.cpbSubmit.performClick();
                    break;
            }
        }
    }

    @Override
    public void toRegisterActivityCustom() {
        RegisterDoorwayActivity.newInstance(this);
//        RegisterDetailActivity.newInstance(LoginActivity.this, null,"13536428700", "123456");
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
        UpdatePasswordActivity.newInstance(this, id, mobile, oldpassWord, UpdatePasswordActivity.UPDATE_PASSWORD_REQUEST_CODE);
    }

    @Override
    public void startBindPhoneActivity(AppUserWrapper appUserWrapper, String strUsername, String strPassword) {
        // 弹出提示是否需要绑定手机号码
        if (dialogHelper == null)
            dialogHelper = new DialogHelper(LoginActivity.this, "提示", "是否绑定手机？", "去绑定", "取消", new CommonDialog.Listener() {
                @Override
                public void ok(String content) {
                    BindPhoneActivity.newInstance(LoginActivity.this, appUserWrapper.getMobile(), appUserWrapper.getId(), mInitialView.mETPassWord.getText().toString(), mInitialView.mACTVUserName.getText().toString());
                }

                @Override
                public void cancel() {
                    mLoginPresenter.toMain(appUserWrapper, strUsername, strPassword);
                }
            });
        dialogHelper.show();
    }

    @Override
    public void startAuthenticationIngActivity(UserApply value) {
        RegisterAuthenticationIngActivity.newInstance(LoginActivity.this, value, mInitialView.mACTVUserName.getText().toString(), mInitialView.mETPassWord.getText().toString());
    }

    @Override
    public void startAuthenticationErrorActivity(UserApply value) {
        RegisterAuthenticationErrorActivity.newInstance(LoginActivity.this, value, mInitialView.mACTVUserName.getText().toString(), mInitialView.mETPassWord.getText().toString());
    }

    @Override
    public void startAuthenticationActivity(UserApply value) {
        RegisterAuthenticationSupplementActivity.newInstance(LoginActivity.this, value, mInitialView.mACTVUserName.getText().toString(), mInitialView.mETPassWord.getText().toString());
    }

    @Override
    public void startGenerateTwoDimensionalCode(String userId) {
        QRCodeActivity.newInstance(LoginActivity.this, userId);
    }


}