package com.yaoguang.company.activitys.forgetpassword;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.yaoguang.lib.base.BaseActivity;
import com.yaoguang.lib.base.interfaces.BasePresenter;
import com.yaoguang.company.R;
import com.yaoguang.company.my.modity.moditypass.ModifyPass2Fragment;

/**
 * 忘记密码的第二步
 * Created by zhongjh on 2017/9/20.
 */
public class ForgetPasswordStep2Activity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_container);

        if (savedInstanceState == null) {
            loadRootFragment(R.id.fl_container, ModifyPass2Fragment.newInstanceType1(getIntent().getStringExtra("mobile"),getIntent().getStringExtra("autoCode")));
        }
    }

    @Override
    public BasePresenter getPresenter() {
        return null;
    }


}
