package com.yaoguang.company.activitys.register2;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.View;

import com.yaoguang.appcommon.phone.register2.authentication.BaseRegisterAuthenticationActivity;
import com.yaoguang.company.R;
import com.yaoguang.greendao.entity.UserApply;

/**
 * 提示认证的界面(异常)
 * Created by zhongjh on 2017/12/1.
 */
public class RegisterAuthenticationErrorActivity extends BaseRegisterAuthenticationActivity {

    UserApply value;

    public static void newInstance(Activity activity, UserApply value, String phone, String password) {
        Intent intent = new Intent(activity, RegisterAuthenticationErrorActivity.class);
        intent.putExtra("value", value);
        intent.putExtra("phone", phone);
        intent.putExtra("password", password);
        activity.startActivity(intent);
    }

    @Override
    protected void toRegisterDetail() {
        RegisterDetailActivity.newInstance(RegisterAuthenticationErrorActivity.this, value, value.getPhone(), getIntent().getStringExtra("password"));

    }

    @Override
    protected void customInitView() {
        Bundle bundle = getIntent().getExtras();
        if (bundle != null)
            value = bundle.getParcelable("value");

        vTop.setBackgroundResource(R.drawable.background_authentication_gradient_red);

        imgIcon.setImageDrawable(ContextCompat.getDrawable(getBaseContext(), R.drawable.ic_sh_sb01));

        tvTitle.setText("认证审核未能通过");

        tvContentTitle.setVisibility(View.VISIBLE);

        if (value != null) {
            String refuseRemark = value.getRefuseRemark().replace("\\n", "\n");
            tvContent.setText(refuseRemark);
        }

        btnOK.setText("重新提交");
        btnOK.setBackgroundResource(R.drawable.ic_cxtj_red);

        btnClose.setText("暂不处理");

    }

}
