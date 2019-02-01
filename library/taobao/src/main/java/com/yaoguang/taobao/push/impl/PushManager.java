package com.yaoguang.taobao.push.impl;

import android.content.Context;
import android.text.TextUtils;

import com.umeng.message.IUmengCallback;
import com.umeng.message.IUmengRegisterCallback;
import com.umeng.message.MsgConstant;
import com.umeng.message.PushAgent;
import com.umeng.message.UTrack;
import com.umeng.message.common.inter.ITagManager;
import com.umeng.message.tag.TagManager;
import com.yaoguang.lib.base.BaseApplication;
import com.yaoguang.lib.common.ULog;

import java.util.List;

/**
 * Project Name:driver
 * Created by weiliying
 * on 2017 2017/4/10.10:12
 *
 * update zhongjh
 */

public class PushManager  {
    public static String deviceToken;
    private volatile static PushManager mInstance;
    private PushAgent mPushAgent;

    public PushManager() {
        mPushAgent = PushAgent.getInstance(BaseApplication.getInstance().getBaseContext());
        mPushAgent.setNotificationPlaySound(MsgConstant.NOTIFICATION_PLAY_SDK_ENABLE);

        mPushAgent.onAppStart();
    }

    public static PushManager getInstance() {
        if (mInstance == null) {
            synchronized (PushManager.class) {
                if (mInstance == null) {
                    mInstance = new PushManager();
                }
            }
        }
        return mInstance;
    }

    /**
     * 添加标签
     * @param tag
     */
    public void addTag(String tag) {
        if (TextUtils.isEmpty(tag)) {
            ULog.i("请先输入Tag");
            return;
        }
        mPushAgent.getTagManager().addTags(new TagManager.TCallBack() {
            @Override
            public void onMessage(final boolean isSuccess, final ITagManager.Result result) {
                if (isSuccess) {
                    ULog.i("Add Tag:" + result);
                } else {
                    ULog.i("Add Tag:" + "加入tag失败");
                }
            }
        }, tag);
    }

    /**
     * 删除标签
     * @param tag
     */
    public void deletTag(String tag) {
        if (TextUtils.isEmpty(tag)) {
            ULog.i("请输入要删除的Tag");
            return;
        }
        mPushAgent.getTagManager().deleteTags(new TagManager.TCallBack() {
            @Override
            public void onMessage(boolean isSuccess, final ITagManager.Result result) {
                ULog.i("isSuccess:" + isSuccess);
                if (isSuccess)
                    ULog.i("deletTag was set successfully.");
                final boolean success = isSuccess;
                ULog.i("delet Tags:" + (success ? "Success" : "Fail"));
            }
        }, tag);
    }

    /**
    * 描    述：登录打开推送
    * 作    者：韦理英
    * 时    间：
    */
    public void loginPush() {
        mPushAgent.register(new IUmengRegisterCallback() {
            @Override
            public void onSuccess(String deviceToken) {
                PushManager.deviceToken = deviceToken;
                ULog.i("device token: " + deviceToken);
            }

            @Override
            public void onFailure(String s, String s1) {
                ULog.i("register failed: " + s + " " + s1);
            }
        });

        //开启推送并设置注册的回调处理
        mPushAgent.enable(new IUmengCallback() {
            @Override
            public void onSuccess() {
                ULog.i("成功启动推送");
            }

            @Override
            public void onFailure(String s, String s1) {
                ULog.i("启动推送失败！s=" + s + "s1=" + s1);
            }
        });
    }

    /**
     * 启动推送
     */
    public void openPush(IUmengCallback iUmengCallback, Context context) {
        mPushAgent.enable(iUmengCallback);
    }

    /**
     * 关闭推送
     */
    public void closePush(IUmengCallback iUmengCallback, Context context) {
        mPushAgent.disable(iUmengCallback);
    }

    /**
     * 按用户id向用户推送消息
     * @param alias 类似value
     * @param aliasType 类似key
     */
    public void addAlias(String alias, String aliasType) {
        if (TextUtils.isEmpty(alias)) {
            ULog.i("请先输入Alias");
            return;
        }
        if (TextUtils.isEmpty(aliasType)) {
            ULog.i("请先输入Alias Type");
            return;
        }
        mPushAgent.addAlias(alias, aliasType, new UTrack.ICallBack() {
            @Override
            public void onMessage(boolean isSuccess, String message) {
                ULog.i("isSuccess:" + isSuccess + "," + message);
                if (isSuccess)
                    ULog.i("alias was set successfully.");

                final boolean success = isSuccess;
                ULog.i("Add Alias:" + (success ? "Success" : "Fail"));

            }
        });
    }

    /**
     * 设置用户id和device_token的一一映射关系，确保同一个alias只对应一台设备
     * @param exclusiveAlias 类似value
     * @param aliasType 类似key
     */
    public void AddExclusiveAlias(String exclusiveAlias, final String aliasType) {
        if (TextUtils.isEmpty(exclusiveAlias)) {
            ULog.i("请先输入Exclusive Alias");
            return;
        }
        if (TextUtils.isEmpty(aliasType)) {
            ULog.i("请先输入Alias Type");
            return;
        }
        mPushAgent.setAlias(exclusiveAlias, aliasType, new UTrack.ICallBack() {
            @Override
            public void onMessage(boolean isSuccess, String message) {
                ULog.i("isSuccess:" + isSuccess + "," + message + ",aliasType:" + aliasType);
                if (Boolean.TRUE.equals(isSuccess))
                    ULog.i("exclusive alias was set successfully.");

                final boolean success = isSuccess;
                ULog.i("Add Exclusive Alias:" + (success ? "Success" : "Fail"));
            }
        });
    }

    /**
     * 删除推送
     * @param exclusiveAlias 类似value
     * @param aliasType 类似键
     */
    public void deletAlias(String exclusiveAlias, String aliasType) {
        if (TextUtils.isEmpty(exclusiveAlias)) {
            ULog.i("请先输入要删除的 Alias");
            return;
        }
        if (TextUtils.isEmpty(aliasType)) {
            ULog.i("请先输入要删除的 Alias Type");
            return;
        }
        mPushAgent.deleteAlias(exclusiveAlias, aliasType, new UTrack.ICallBack() {
            @Override
            public void onMessage(boolean isSuccess, String s) {
                if (Boolean.TRUE.equals(isSuccess))
                    ULog.i("delet alias was set successfully.");

                final boolean success = isSuccess;
                ULog.i("Delet Alias:" + (success ? "Success" : "Fail"));
            }
        });
    }

    /**
     * 获取所有键
     */
    public void listTag() {
        mPushAgent.getTagManager().getTags(new TagManager.TagListCallBack() {
            @Override
            public void onMessage(final boolean isSuccess, final List<String> result) {
                if (isSuccess) {
                    if (result != null) {
                        StringBuilder info = new StringBuilder();
                        info.append("Tags:");
                        for (int i = 0; i < result.size(); i++) {
                            String tag = result.get(i);
                            info.append("\n" + tag);
                        }
                        ULog.i(info.toString());
                    } else {
                        ULog.i("result is null");
                    }
                } else {
                    ULog.i("Tags获取失败");
                }
            }
        });
    }

}