package com.yaoguang.appcommon.push.huawei;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.umeng.message.UmengNotifyClickActivity;
import com.yaoguang.appcommon.R;

import org.android.agoo.common.AgooConstants;

/**
 * 华为对后台进程做了诸多限制。若使用一键清理，应用的channel进程被清除，将接收不到推送。为了增加推送的送达率，可选择接入华为托管弹窗功能。
 * 通知将由华为系统托管弹出，点击通知栏将跳转到指定的Activity。
 * 该Activity需继承自UmengNotifyClickActivity，同时实现父类的onMessage方法，对该方法的intent参数进一步解析即可，该方法异步调用，不阻塞主线程。
 * 示例如下（与小米弹窗使用方式相同）：
 * Created by zhongjh on 2018/6/28.
 */
public class MipushTestActivity extends UmengNotifyClickActivity {

    private static String TAG = MipushTestActivity.class.getName();

    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_register);
    }

    @Override
    public void onMessage(Intent intent) {
        super.onMessage(intent);  //此方法必须调用，否则无法统计打开数
        String body = intent.getStringExtra(AgooConstants.MESSAGE_BODY);
        Log.i(TAG, body);
    }
}
