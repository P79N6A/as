package com.yaoguang.appcommon.common.base;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.MotionEvent;
import android.view.View;

import com.dd.CircularProgressButton;
import com.yaoguang.lib.base.BaseActivity;
import com.yaoguang.lib.base.BaseActivity2;

import java.lang.ref.WeakReference;
import java.util.Timer;
import java.util.TimerTask;

/**
 * 确认按钮默认方法
 * Created by zhongjh on 2017/9/25.
 */
public abstract class BaseSubmitProgressActivity extends BaseActivity2 {

    protected CircularProgressButton mCpbSubmit;
    protected View.OnClickListener mCpbSubmitOnClick;

    /**
     * 调用的时候，记得使用该方法
     * @param circularProgressButton
     */
    protected void setCpbSubmit(CircularProgressButton circularProgressButton){
        mCpbSubmit = circularProgressButton;
        mCpbSubmit.setIndeterminateProgressMode(true);
    }

    public void setSubmitProgressInit() {
        mCpbSubmit.setProgress(0);
        mCpbSubmit.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                return false;
            }
        });
    }

    public void setSubmitProgressOK() {
        mCpbSubmit.setProgress(100);
        mCpbSubmit.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                return true;
            }
        });
    }

    public void setSubmitProgressLoading() {
        mCpbSubmit.setProgress(50);
        mCpbSubmit.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                return true;
            }
        });
    }

    public void setSubmitProgressError() {
        mCpbSubmit.setProgress(-1);
        mCpbSubmit.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                return true;
            }
        });
    }

    /**
     * 按钮延时1.5秒后执行动画
     */
    public abstract void submitProgressSuccess(Bundle bundle);

    public void setSubmitProgressSuccess(Bundle bundle) {
        Timer timer = new Timer();
        TimerTask tast = new TimerTask() {
            @Override
            public void run() {
                Message message = new Message();
                message.setData(bundle);
                message.what = 0;
                myHandler.sendMessage(message);
            }
        };
        timer.schedule(tast, 1500);
    }

    MyHandler myHandler = new MyHandler(this);

    static class MyHandler extends Handler {
        WeakReference<BaseSubmitProgressActivity> mBaseSubmitProgressActivity;

        MyHandler(BaseSubmitProgressActivity baseSubmitProgressActivity) {
            mBaseSubmitProgressActivity = new WeakReference<>(baseSubmitProgressActivity);
        }

        @Override
        public void handleMessage(Message msg) {
            BaseSubmitProgressActivity baseSubmitProgressActivity = mBaseSubmitProgressActivity.get();
            baseSubmitProgressActivity.submitProgressSuccess(msg.getData());
        }
    }

}
