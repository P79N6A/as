package com.yaoguang.shipper.activitys.register2;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.View;

import com.yaoguang.appcommon.phone.register2.authentication.BaseRegisterAuthenticationActivity;
import com.yaoguang.greendao.entity.UserOwner;
import com.yaoguang.shipper.R;

/**
 * 提示认证的界面(正在审核)
 * Created by zhongjh on 2017/12/1.
 */
public class RegisterAuthenticationIngActivity extends BaseRegisterAuthenticationActivity {

    UserOwner value;

    public static void newInstance(Activity activity, UserOwner value, String phone, String password) {
        Intent intent = new Intent(activity, RegisterAuthenticationIngActivity.class);
        intent.putExtra("value", value);
        intent.putExtra("phone", phone);
        intent.putExtra("password", password);
        activity.startActivity(intent);
    }

    @Override
    protected void toRegisterDetail() {
        RegisterDetailActivity.newInstance(RegisterAuthenticationIngActivity.this, value,value.getPhone(), getIntent().getStringExtra("password"));
    }

    @Override
    protected void customInitView() {
        Bundle bundle = getIntent().getExtras();
        if (bundle != null)
            value = bundle.getParcelable("value");

        vTop.setBackgroundResource(R.drawable.background_authentication_gradient_blue);

        imgIcon.setImageDrawable(ContextCompat.getDrawable(getBaseContext(), R.drawable.ic_sh_wtg01));

        tvTitle.setText("资料认证审核中");

        tvContent.setText("审核结果将在提交后的2个工作日内发到您注册的手机上,请耐心等待。");

        btnOK.setVisibility(View.GONE);

        btnClose.setBackgroundResource(R.drawable.ic_close_blue);
        btnClose.setText("关闭");
        btnClose.setTextColor(ContextCompat.getColor(getBaseContext(), R.color.white));


    }

}
