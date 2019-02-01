package com.yaoguang.lib.qinui;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;

/**
 * Created by zhongjh on 2018/5/30.
 */
public class SafeProgressDialog extends ProgressDialog {

    Activity mParentActivity;

    public SafeProgressDialog(Context context) {
        super(context);
        mParentActivity = (Activity) context;
    }


    @Override
    public void dismiss() {
        if (mParentActivity != null && !mParentActivity.isFinishing()) {
            super.dismiss();    //调用超类对应方法
        }
    }

}