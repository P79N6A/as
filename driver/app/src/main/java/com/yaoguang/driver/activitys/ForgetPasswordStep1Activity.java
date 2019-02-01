package com.yaoguang.driver.activitys;

import android.app.Activity;
import android.content.Intent;

import com.yaoguang.appcommon.forget.BaseForgetPasswordStep1Activity;
import com.yaoguang.common.common.Constants;
import com.yaoguang.driver.R;

import java.util.Timer;
import java.util.TimerTask;

/**
 * 忘记密码的第一步
 * Created by wly on 2017/9/20.
 */
public class ForgetPasswordStep1Activity extends BaseForgetPasswordStep1Activity {

    public static void newInstance(Activity activity, String mobile) {
        Intent intent = new Intent(activity, ForgetPasswordStep1Activity.class);
        intent.putExtra("mobile", mobile);
        activity.startActivityForResult(intent, 50);
        //(A启动B A不动 B从下面滑入) 第一个参数是进入activity的动画，第二个参数是当前activity退出时的动画。
        activity.overridePendingTransition(R.anim.up_in, R.anim.up_out);
    }

    @Override
    protected String getCodeType() {
        return Constants.APP_DRIVER;
    }

    @Override
    public void success() {
        final Intent intent = new Intent();
        intent.putExtra("mobile",mMobile);
        intent.putExtra("autoCode",mInitialView.mViewHolder.actvCode.getText().toString());
        intent.setClass(ForgetPasswordStep1Activity.this, ForgetPasswordStep2Activity.class);


        Timer timer = new Timer();
        TimerTask tast = new TimerTask() {
            @Override
            public void run() {
                //跳转第二步
                ForgetPasswordStep1Activity.this.finish();
                ForgetPasswordStep1Activity.this.startActivity(intent);
            }
        };
        timer.schedule(tast, 1000);
    }


    @Override
    public void submitProgressSuccess() {

    }
}
