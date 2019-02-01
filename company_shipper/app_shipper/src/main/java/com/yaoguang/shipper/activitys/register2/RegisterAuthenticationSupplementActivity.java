package com.yaoguang.shipper.activitys.register2;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;

import com.yaoguang.appcommon.phone.register2.authentication.BaseRegisterAuthenticationActivity;
import com.yaoguang.greendao.entity.UserOwner;
import com.yaoguang.shipper.R;

/**
 * 提示认证的界面
 * Created by zhongjh on 2017/12/1.
 */
public class RegisterAuthenticationSupplementActivity extends BaseRegisterAuthenticationActivity {

    UserOwner value;

    public static void newInstance(Activity activity, UserOwner value, String phone, String password) {
        Intent intent = new Intent(activity, RegisterAuthenticationSupplementActivity.class);
        intent.putExtra("value", value);
        intent.putExtra("phone", phone);
        intent.putExtra("password", password);
        activity.startActivity(intent);
    }

    @Override
    protected void toRegisterDetail() {
        RegisterDetailActivity.newInstance(RegisterAuthenticationSupplementActivity.this, value, value.getPhone(), getIntent().getStringExtra("password"));
    }

    @Override
    protected void customInitView() {
        Bundle bundle = getIntent().getExtras();
        if (bundle != null)
            value = bundle.getParcelable("value");
        btnOK.setText("现在认证");
        btnOK.setBackgroundResource(R.drawable.ic_xzrz_yellow);
        btnClose.setText("以后再说");
        tvTitle.setText("账户已注册");
        imgIcon.setImageDrawable(ContextCompat.getDrawable(getBaseContext(), R.drawable.ic_zl_rz01));
        vTop.setBackgroundResource(R.drawable.background_authentication_gradient_orange);
        tvContent.setText("认证通过后,您方可登录使用本系统服务");
    }

}
