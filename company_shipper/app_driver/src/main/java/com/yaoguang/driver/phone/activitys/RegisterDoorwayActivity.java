package com.yaoguang.driver.phone.activitys;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.yaoguang.appcommon.phone.register2.doorway.BaseRegisterDoorwayActivity;
import com.yaoguang.driver.R;

/**
 * 注册入口
 * Created by zhongjh on 2018/3/12.
 */
public class RegisterDoorwayActivity extends BaseRegisterDoorwayActivity {



    public static void newInstance(Activity activity) {
        //(A启动B A不动 B从下面滑入) 第一个参数是进入activity的动画，第二个参数是当前activity退出时的动画。
        Intent intent = new Intent(activity, RegisterDoorwayActivity.class);
        activity.startActivityForResult(intent, REQUEST_REGISTERDOORWAY);
        activity.overridePendingTransition(R.anim.up_in, R.anim.up_out);
    }

    @Override
    public void initUICustom(InitialView initialView) {
        initialView.toolbar_title.setText("用户注册");
        initialView.cpbSubmit.setText("注册");
    }

    @Override
    public void submitProgressSuccess(Bundle bundle) {
        // 传输数据给登录
        Intent intent = new Intent();
        intent.putExtra("mobile", bundle.getString("mobile"));
        intent.putExtra("pass", bundle.getString("pass"));
        setResult(RESULT_OK,intent);

        // 关闭本身
        RegisterDoorwayActivity.this.finish();
    }
}
