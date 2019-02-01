package com.yaoguang.lib.appcommon.dialog.helper;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.daimajia.numberprogressbar.NumberProgressBar;
import com.yaoguang.lib.R;

/**
 * Created by 韦理英
 * on 2017/4/27 0027.
 */

public class DialogUtil {

    public static String value;
    static AlertDialog.Builder builder;
    private static AlertDialog alertDialog;
    private static NumberProgressBar mNumberProgressBar;

    /**
     * 引用方法
     * <p>
     * DialogUtil.showDialog(getActivity(), msg.getTitle(), msg.getContent(), "确认", (dialogInterface, i) -> clickSetDelete(msg.getId()));
     *
     * @param context
     * @param title
     * @param content
     * @param buttonTitle
     * @param buttonClickListener
     */
    public static void showDialog(Context context, String title, String content, String buttonTitle, DialogInterface.OnClickListener buttonClickListener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(title);
        builder.setMessage(content);
        builder.setNegativeButton("取消", null);
        builder.setPositiveButton(buttonTitle, buttonClickListener);
        builder.show();
    }

    public static void showDialog(Context context, View v) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        final AlertDialog alertDialog = builder.create();
        builder.setPositiveButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                alertDialog.dismiss();
                alertDialog.hide();
            }
        });
        alertDialog.setCancelable(false);
        alertDialog.setContentView(v);
        builder.show();
    }

    /**
     * 引用方法
     * <p>
     * DialogUtil.showDialog(getActivity(), msg.getTitle(), msg.getContent(), "确认", (dialogInterface, i) -> clickSetDelete(msg.getId()));
     *
     * @param context
     * @param title
     * @param buttonTitle
     * @param buttonClickListener
     * @param v
     */
    public static void showDialog(Context context, String title, String buttonTitle, DialogInterface.OnClickListener buttonClickListener, View v) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(title);
        builder.setView(v);
        builder.setNegativeButton("取消", null);
        final AlertDialog alertDialog = builder.create();
        alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
            }
        });
        builder.setPositiveButton(buttonTitle, buttonClickListener);
        builder.show();
        YoYo.with(Techniques.FadeIn).playOn(v);
    }

    /**
     * 引用方法
     * <p>
     * DialogUtil.showDialog(getActivity(), msg.getTitle(), msg.getContent(), "确认", (dialogInterface, i) -> clickSetDelete(msg.getId()));
     *
     * @param context
     * @param title
     * @param buttonTitle
     * @param onClickPositiveButton 点击事件
     * @param v
     */
    public static void showDialog(Context context, String title, final String buttonTitle, final OnClickPositiveButton onClickPositiveButton, View v) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(title);
        builder.setView(v);
        builder.setNegativeButton("取消", null);
        builder.setPositiveButton(buttonTitle, null);
        final AlertDialog alertDialog = builder.create();
        alertDialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialogInterface) {
                Button b = alertDialog.getButton(AlertDialog.BUTTON_POSITIVE);
                b.setText(buttonTitle);
                b.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (onClickPositiveButton != null)
                            onClickPositiveButton.OnClickPositiveButton(view, alertDialog);
                    }
                });
            }
        });
        alertDialog.show();
        YoYo.with(Techniques.FadeIn).playOn(v);
    }

    /**
     * 引用方法
     * <p>
     * DialogUtil.showDialog(getActivity(), msg.getTitle(), msg.getContent(), "确认", (dialogInterface, i) -> clickSetDelete(msg.getId()));
     *
     * @param context
     * @param title
     * @param v
     */
    public static void showDialog(Context context, String title, View v) {
        hideDialog();
        builder = new AlertDialog.Builder(context);
        alertDialog = builder.create();
        alertDialog.setView(v);
        alertDialog.setTitle(title);
        alertDialog.show();
        YoYo.with(Techniques.FadeIn).playOn(v);
    }

    public static void showProgressDialog(Context context) {
        showProgressDialog(context, null);
    }

    public static void showProgressDialog(Context context, String title) {
        View numberProgressBarView = View.inflate(context, R.layout.view_numberprogressbar, null);

        mNumberProgressBar = (NumberProgressBar) numberProgressBarView.findViewById(R.id.numberProgressBar);
        if (title != null) {
            TextView tvTitle = (TextView) numberProgressBarView.findViewById(R.id.tvTitle);
            tvTitle.setText(title);
        }
        DialogUtil.showDialog(context, "", numberProgressBarView, false);
    }

    public static void setProgress(long max, long i) {
        if (mNumberProgressBar != null) {
            mNumberProgressBar.setProgress((int) i);
            mNumberProgressBar.setMax((int) max);
        }
    }

    /**
     * 接收
     *
     * @param context
     * @param
     * @param
     * @Subscribe(sticky = true)
     * public void cameraEvent(CameraResultEvent cameraEvent) {
     * if (cameraEvent.getType() == ID_NUMBER_FRONT) {
     * idNumberFrontString = cameraEvent.getFile();
     * GlideManager.getInstance().withSquare(getContext(), idNumberFrontString, 100, 100, mInitialView.idNumberFront);
     * }
     * <p>
     * EventBus.clearCaches();
     * }
     */

    public static void showDialog(Context context, View v, boolean isCancelAble) {
        hideDialog();
        builder = new AlertDialog.Builder(context);
        alertDialog = builder.create();
        alertDialog.setCancelable(isCancelAble);
        alertDialog.setView(v);
        alertDialog.show();
    }

    public static void showSingleChoiceDialog(Context context, String[] items, String title, DialogInterface.OnClickListener selectlistener) {
        builder = new AlertDialog.Builder(context);
        builder.setTitle(title);
        builder.setSingleChoiceItems(items, 0, selectlistener);
        builder.setNegativeButton("取消", null);
        builder.show();
    }

    /**
     * 引用方法
     * <p>
     * DialogUtil.showDialog(getActivity(), msg.getTitle(), msg.getContent(), "确认", (dialogInterface, i) -> clickSetDelete(msg.getId()));
     *
     * @param context
     * @param title
     * @param v
     * @param isCancelable
     */
    public static void showDialog(Context context, String title, View v, boolean isCancelable) {
        hideDialog();
        builder = new AlertDialog.Builder(context);
        alertDialog = builder.create();
        alertDialog.setView(v);
        alertDialog.setTitle(title);
        alertDialog.setCancelable(isCancelable);
        alertDialog.show();
    }

    /**
     * 引用方法
     * <p>
     * DialogUtil.showDialog(getActivity(), msg.getTitle(), msg.getContent(), "确认", (dialogInterface, i) -> clickSetDelete(msg.getId()));
     *
     * @param context
     * @param title
     * @param v
     */
    public static void showDialog2(Context context, String title, View v) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setNegativeButton("取消", null);
        builder.setView(v);
        builder.setTitle(title);
        builder.show();
    }

    /**
     * 引用方法
     *
     * @param context 上下文
     * @param title   标题
     * @param content 内容
     */
    public static void showDialog(Context context, String title, String content) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(title);
        builder.setMessage(content);
        builder.setNegativeButton("关闭", null);
        builder.show();
    }

    public static void hideDialog() {
        if (builder != null && alertDialog != null && alertDialog.isShowing()) {
            alertDialog.dismiss();
            builder = null;
            alertDialog = null;
        }
    }

    public interface OnClickPositiveButton {
        public void OnClickPositiveButton(View view, AlertDialog alertDialog);
    }
}