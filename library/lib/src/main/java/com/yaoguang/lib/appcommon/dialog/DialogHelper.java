package com.yaoguang.lib.appcommon.dialog;

import android.content.Context;
import android.widget.Toast;

import com.yaoguang.lib.appcommon.dialog.helper.CommonDialog;
import com.yaoguang.lib.base.BaseApplication;


/**
 * 封装的一个dialog
 * Created by zhongjh
 * on 2017 2017/4/12
 * SweetAlertDialog.ERROR_TYPE:X图标
 * SweetAlertDialog.SUCCESS_TYPE:√图标
 * SweetAlertDialog.WARNING_TYPE:!图标
 *
 * @author zhongjh
 */
public class DialogHelper {

    private CommonDialog mDialog;

    public DialogHelper(Context context, String title, String contentText, boolean isSingle, CommonDialog.Listener confirmClickListener) {
        this(context, title, contentText, null, null, isSingle, confirmClickListener);
    }

    public DialogHelper(Context context, String title, String contentText, CommonDialog.Listener confirmClickListener) {
        this(context, title, contentText, null, null, false, confirmClickListener);
    }

    public DialogHelper(Context context, String title, String contentText, String confirmText, CommonDialog.Listener confirmClickListener) {
        this(context, title, contentText, confirmText, "取消", false, confirmClickListener);
    }

    public DialogHelper(Context context, String title, String contentText, String confirmText, String cancelText, CommonDialog.Listener confirmClickListener) {
        this(context, title, contentText, confirmText, cancelText, false, confirmClickListener);
    }

    public DialogHelper(Context context, String title, String contentText, String confirmText, String cancelText, boolean isSingle, CommonDialog.Listener confirmClickListener) {
        this(context, title, contentText, null, confirmText, cancelText, isSingle, false, -1, confirmClickListener);
    }

    /**
     * @param context              上下文
     * @param title                标题
     * @param contentText          内容
     * @param hintText             提示文本
     * @param confirmText          确认按钮文字
     * @param cancelText           取消按钮文字
     * @param isSingle             是否单个按钮，如果是单个按钮，只留下确认的那个按钮
     * @param isEditText           是否编辑文本框
     * @param maxLength            長度
     * @param confirmClickListener 确认事件
     */
    public DialogHelper(Context context, String title, String contentText, String hintText, String confirmText, String cancelText, boolean isSingle, boolean isEditText, int maxLength, CommonDialog.Listener confirmClickListener) {
        mDialog = new CommonDialog(context, title, contentText, hintText, confirmText, cancelText, confirmClickListener, isSingle, isEditText, maxLength);
        mDialog.setCancelable(true);
    }

    /**
     * 隐藏
     */
    public void hideDialog() {
        if (mDialog != null && mDialog.isShowing()) {
            mDialog.dismiss();
        }
    }

    /**
     * 显示
     */
    public void show(){
        mDialog.show();
    }

    public boolean isShow() {
        return mDialog != null && mDialog.isShowing();
    }

}
