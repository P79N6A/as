package com.yaoguang.company.activitys.register2;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.yaoguang.appcommon.phone.register2.doorway.BaseRegisterDoorwayActivity;
import com.yaoguang.company.R;
import com.yaoguang.greendao.entity.UserApply;

/**
 * 注册的入口
 * Created by zhongjh on 2017/11/29.
 */
public class RegisterDoorwayActivity extends BaseRegisterDoorwayActivity<UserApply> {

    public static void newInstance(Activity activity) {
        //(A启动B A不动 B从下面滑入) 第一个参数是进入activity的动画，第二个参数是当前activity退出时的动画。
        Intent intent = new Intent(activity, RegisterDoorwayActivity.class);
        activity.startActivity(intent);
        activity.overridePendingTransition(R.anim.up_in, R.anim.up_out);
    }

    @Override
    public void initUICustom(InitialView initialView) {
        initialView.toolbar_title.setText("商户注册");
    }

    @Override
    public void submitProgressSuccess(Bundle bundle) {
        //重新初始化，为了让返回的时候再次点击
        setSubmitProgressInit();

        // 跳转详情的注册Activity
        RegisterDetailActivity.newInstance(RegisterDoorwayActivity.this, null,mInitialView.etMobile.getText().toString(), mInitialView.etPassWord.getText().toString());


        // 关闭本身
        RegisterDoorwayActivity.this.finish();
    }
}
