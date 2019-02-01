package com.yaoguang.lib.appcommon.utils;

import android.os.CountDownTimer;
import android.support.v4.content.ContextCompat;
import android.widget.TextView;

import com.yaoguang.lib.R;
import com.yaoguang.lib.base.BaseApplication;

/**
 * 按钮时间倒计时
 * Created by zhongjh on 2017/4/1.
 */
public class CountDownButtonHelper {


    private TextView button;

    // 倒计时timer
    private CountDownTimer countDownTimer;
    // 计时结束的回调接口
    private OnFinishListener listener;
    // 是否倒数中
    private boolean isReciprocal = false;

    public boolean isReciprocal() {
        return isReciprocal;
    }

    public CountDownButtonHelper() {
    }

    private int getBaseColor(int value) {
        return ContextCompat.getColor(BaseApplication.getInstance().getBaseContext(), value);
    }

    /**
     * 初始化
     *
     * @param button        需要显示倒计时的Button
     * @param defaultString 默认显示的字符串
     * @param max           需要进行倒计时的最大值,单位是秒
     * @param interval      倒计时的间隔，单位是秒
     */
    public void init(final TextView button,
                     final String defaultString, int max, int interval) {
        this.button = button;
        // 由于CountDownTimer并不是准确计时，在onTick方法调用的时候，time会有1-10ms左右的误差，这会导致最后一秒不会调用onTick()
        // 因此，设置间隔的时候，默认减去了10ms，从而减去误差。
        // 经过以上的微调，最后一秒的显示时间会由于10ms延迟的积累，导致显示时间比1s长max*10ms的时间，其他时间的显示正常,总时间正常
        countDownTimer = new CountDownTimer(max * 1000, interval * 1000 - 10) {


            @Override
            public void onTick(long time) {
                // 第一次调用会有1-10ms的误差，因此需要+15ms，防止第一个数不显示，第二个数显示2s
                button.setText("" + ((time + 15) / 1000)
                        + "秒");
            }

            @Override
            public void onFinish() {
                button.setEnabled(true);
                isReciprocal = false;
                button.setText(defaultString);
                if (listener != null) {
                    listener.finish();
                }
            }
        };
    }

    /**
     * 开始倒计时
     */
    public void start() {
        button.setEnabled(false);
        countDownTimer.start();
        isReciprocal = true;
    }

    /**
     * 停止倒计时
     */
    public void stop() {
        countDownTimer.cancel();
        countDownTimer.onFinish();
    }

    /**
     * 设置倒计时结束的监听器
     *
     * @param listener
     */
    public void setOnFinishListener(OnFinishListener listener) {
        this.listener = listener;
    }

    /**
     * 计时结束的回调接口
     *
     * @author zhaokaiqiang
     */
    public interface OnFinishListener {
        void finish();
    }


}
