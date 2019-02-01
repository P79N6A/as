package com.yaoguang.lib.appcommon.utils;

import android.app.ProgressDialog;
import android.content.Context;
import android.text.TextUtils;

/**
 * 等待的对话框
 * Created by zhongjh on 2017/5/5.
 */
public class ProgressDialogHelper {

    private Context mContext;
    private ProgressDialog mProgressDialog;

    public ProgressDialogHelper(Context context) {
        mContext = context;
        mProgressDialog = new ProgressDialog(mContext);
    }

    /**
     * 创建等待的对话框
     */
    public void showProgressDialog(String title, String msg) {
        if (mProgressDialog != null && !mProgressDialog.isShowing())
            mProgressDialog = ProgressDialog.show(mContext, TextUtils.isEmpty(title) ? "加载中..." : title, TextUtils.isEmpty(msg) ? "请等待..." : msg, true, false);
    }

    public void showProgressDialog() {
        showProgressDialog(null, null);
    }

    /**
     * 关闭等待的对话框
     */
    public void dismiss() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
    }

}
