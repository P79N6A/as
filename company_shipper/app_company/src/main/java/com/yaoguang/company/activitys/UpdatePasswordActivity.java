package com.yaoguang.company.activitys;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.yaoguang.lib.base.BaseActivity;
import com.yaoguang.lib.base.interfaces.BasePresenter;
import com.yaoguang.company.R;
import com.yaoguang.company.my.modity.moditypass.ModifyPass2Fragment;

/**
 * 修改密码，登录之后判断是否需要强制修改密码
 * Created by zhongjh on 2018/1/11.
 */
public class UpdatePasswordActivity extends BaseActivity {

    public static final int UPDATE_PASSWORD_REQUEST_CODE = 2;

    public static void newInstance(Activity activity, String id, String mobile, String oldpassWord, int requestCode) {
        Intent intent = new Intent(activity, UpdatePasswordActivity.class);
        intent.putExtra("id", id);
        intent.putExtra("oldpassWord", oldpassWord);
        intent.putExtra("mobile", mobile);
        activity.startActivityForResult(intent, requestCode);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_container);

        if (savedInstanceState == null) {
            loadRootFragment(R.id.fl_container, ModifyPass2Fragment.newInstanceType2(getIntent().getStringExtra("id"),getIntent().getStringExtra("mobile"),getIntent().getStringExtra("oldpassWord")));
        }
    }

    @Override
    public BasePresenter getPresenter() {
        return null;
    }


}




