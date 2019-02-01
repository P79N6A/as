package com.yaoguang.driver.phone.activitys;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.yaoguang.lib.base.BaseActivity;
import com.yaoguang.lib.base.interfaces.BasePresenter;
import com.yaoguang.driver.R;
import com.yaoguang.driver.phone.my.modify.password.ModifyPass2Fragment;

/**
 * Created by wly on 2017/9/20.
 */

public class ForgetPasswordStep2Activity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null) {
            loadRootFragment(R.id.fl_container, ModifyPass2Fragment.newInstanceType1(getIntent().getStringExtra("mobile"), getIntent().getStringExtra("autoCode")));
        }
    }

    @Override
    public BasePresenter getPresenter() {
        return null;
    }

    public void onModitySuccess(String mobile) {
        //忘记密码修改完成之后，所修改密码的手机号就复制到登录界面（前提是登录界面的帐号为空）
        Intent intent = new Intent();
        intent.putExtra("mobile", mobile);
        setResult(RESULT_OK, intent);
        finish();
    }

}
