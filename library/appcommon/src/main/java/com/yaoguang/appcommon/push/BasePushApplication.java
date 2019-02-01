package com.yaoguang.appcommon.push;

import android.app.Notification;
import android.content.Context;
import android.os.Handler;
import android.text.TextUtils;
import android.widget.Toast;

import com.umeng.commonsdk.UMConfigure;
import com.umeng.message.IUmengRegisterCallback;
import com.umeng.message.MsgConstant;
import com.umeng.message.PushAgent;
import com.umeng.message.UTrack;
import com.umeng.message.UmengMessageHandler;
import com.umeng.message.UmengNotificationClickHandler;
import com.umeng.message.entity.UMessage;
import com.yaoguang.appcommon.common.eventbus.OrderChildEvent;
import com.yaoguang.appcommon.common.eventbus.OrderDetailEvent;
import com.yaoguang.datasource.common.DataStatic;
import com.yaoguang.lib.base.BaseApplication;
import com.yaoguang.lib.common.constants.Constants;
import com.yaoguang.lib.common.SPUtils;
import com.yaoguang.appcommon.common.eventbus.HomeEvent;
import com.yaoguang.appcommon.phone.home.event.HomeMessageEvent;
import com.yaoguang.appcommon.common.eventbus.NotificationEvent;
import com.yaoguang.lib.common.ULog;
import com.yaoguang.map.common.tts.TTSController;
import com.yaoguang.taobao.push.impl.PushManager;

import org.android.agoo.huawei.HuaWeiRegister;
import org.android.agoo.xiaomi.MiPushRegistar;
import org.greenrobot.eventbus.EventBus;

import static com.yaoguang.lib.common.constants.Constants.FLAG_ENTERPRISE_NEWS;
import static com.yaoguang.lib.common.constants.Constants.FLAG_ORDER;
import static com.yaoguang.lib.common.constants.Constants.FLAG_ORDER_DETAIL;
import static com.yaoguang.lib.common.constants.Constants.FLAG_PLATFORM_MESSAGE;
import static com.yaoguang.lib.common.constants.Constants.FLAG_REFRESH_PAGE;

public abstract class BasePushApplication extends BaseApplication {

    private static final String REFRESH = "refresh";
    private static final String TO_PAGE = "ToPage";
    public static final String SAY_TEXT = "Say";
    public static final String ID = "Id";

    public static final String UPDATE_STATUS_ACTION = "com.umeng.message.example.action.UPDATE_STATUS";

    private Handler handler;
    private PushAgent mPushAgent;

    @Override
    public void onCreate() {
        super.onCreate();
//        //设置LOG开关，默认为false
        UMConfigure.setLogEnabled(true);
//        //初始化组件化基础库, 统计SDK/推送SDK/分享SDK都必须调用此初始化接口
        /**
         * 初始化common库
         * 参数1:上下文，不能为空
         * 参数2:设备类型，UMConfigure.DEVICE_TYPE_PHONE为手机、UMConfigure.DEVICE_TYPE_BOX为盒子，默认为手机
         * 参数3:Push推送业务的secret
         */
        UMConfigure.init(this, UMConfigure.DEVICE_TYPE_PHONE, "483917c4572f79516f914cfde274d6a7");
//        UMConfigure.init(this, "59892f08310c9307b60023d0", "Umeng", UMConfigure.DEVICE_TYPE_PHONE,
//                "669c30a9584623e70e8cd01b0381dcb4");

        initUpush();

    }

    private void initUpush() {
        mPushAgent = PushAgent.getInstance(this);
        handler = new Handler(getMainLooper());

        //sdk开启通知声音
        mPushAgent.setNotificationPlaySound(MsgConstant.NOTIFICATION_PLAY_SDK_ENABLE);
        // 开启震动
         mPushAgent.setNotificationPlayVibrate(MsgConstant.NOTIFICATION_PLAY_SDK_ENABLE);

        // sdk关闭通知声音
        // mPushAgent.setNotificationPlaySound(MsgConstant.NOTIFICATION_PLAY_SDK_DISABLE);
        // 通知声音由服务端控制
        // mPushAgent.setNotificationPlaySound(MsgConstant.NOTIFICATION_PLAY_SERVER);

        // 关闭指示器
        // mPushAgent.setNotificationPlayLights(MsgConstant.NOTIFICATION_PLAY_SDK_DISABLE);
        // 关闭震动
        // mPushAgent.setNotificationPlayVibrate(MsgConstant.NOTIFICATION_PLAY_SDK_DISABLE);

        UmengMessageHandler messageHandler = new UmengMessageHandler() {

            /**
             * 通知的回调方法（通知送达时会回调）
             */
            @Override
            public void dealWithNotificationMessage(Context context, UMessage msg) {
                //调用super，会展示通知，不调用super，则不展示通知。
                super.dealWithNotificationMessage(context, msg);
            }

            /**
             * 自定义消息的回调方法
             */
            @Override
            public void dealWithCustomMessage(final Context context, final UMessage msg) {

                handler.post(new Runnable() {

                    @Override
                    public void run() {
                        // TODO Auto-generated method stub
                        // 对自定义消息的处理方式，点击或者忽略
                        boolean isClickOrDismissed = true;
                        if (isClickOrDismissed) {
                            //自定义消息的点击统计
                            UTrack.getInstance(getApplicationContext()).trackMsgClick(msg);
                        } else {
                            //自定义消息的忽略统计
                            UTrack.getInstance(getApplicationContext()).trackMsgDismissed(msg);
                        }
                        Toast.makeText(BaseApplication.getInstance(), msg.custom, Toast.LENGTH_LONG).show();
                    }
                });
            }

            /**
             * 自定义通知栏样式的回调方法
             */
            @Override
            public Notification getNotification(Context context, UMessage msg) {
                // 判断是否登录，如果没登录，直接返回
                String userId = null;
                if (BaseApplication.getAppType().equals(Constants.APP_DRIVER)) {
                    userId = DataStatic.getInstance().getDriver().getId();
                } else if (BaseApplication.getAppType().equals(Constants.APP_COMPANY)) {
                    userId = DataStatic.getInstance().getAppUserWrapper().getId();
                } else if (BaseApplication.getAppType().equals(Constants.APP_COMPANY)) {
                    userId = DataStatic.getInstance().getUserOwner().getId();
                }
                if (TextUtils.isEmpty(userId)){
                    return null;
                }

                ULog.i("push id=" + msg.msg_id);

                // 实时刷新数据
                EventBus.getDefault().post(new HomeEvent(FLAG_REFRESH_PAGE));
                // 刷新订单列表
                EventBus.getDefault().post(new OrderChildEvent(FLAG_REFRESH_PAGE));
                // 刷新明细订单
                EventBus.getDefault().post(new OrderDetailEvent(true));
                EventBus.getDefault().post(new HomeMessageEvent(true, true));

                // 传入信息，不要使用msg.text 因为不是所有消息都要发声 by wly
                if (msg.extra != null && msg.extra.get(SAY_TEXT) != null) {
                    pushTts(msg.extra.get(SAY_TEXT));
                }

                Notification notification = super.getNotification(context, msg);
                if (!SPUtils.getInstance().getBoolean(Constants.Sound, true)) {
//                    notification.sound = null;
//                  取消铃声
                    mPushAgent.setNotificationPlaySound(MsgConstant.NOTIFICATION_PLAY_SDK_DISABLE);
                }else{
                    mPushAgent.setNotificationPlaySound(MsgConstant.NOTIFICATION_PLAY_SDK_ENABLE);
                }
                if (!SPUtils.getInstance().getBoolean(Constants.Vibrate, true)) {
//                    notification.vibrate = null;//取消震动
                    mPushAgent.setNotificationPlayVibrate(MsgConstant.NOTIFICATION_PLAY_SDK_DISABLE);
                }else{
                    // 开启震动
                    mPushAgent.setNotificationPlayVibrate(MsgConstant.NOTIFICATION_PLAY_SDK_ENABLE);
                }

                //默认为0，若填写的builder_id并不存在，也使用默认。
//                switch (msg.builder_id) {
//                    case 1:
//                        NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
//                        RemoteViews myNotificationView = new RemoteViews(context.getPackageName(), R.layout.notification_view);
//                        myNotificationView.setTextViewText(R.id.notification_title, msg.title);
//                        myNotificationView.setTextViewText(R.id.notification_text, msg.text);
//                        myNotificationView.setImageViewBitmap(R.id.notification_large_icon, getLargeIcon(context, msg));
//                        myNotificationView.setImageViewResource(R.id.notification_small_icon, getSmallIconId(context, msg));
//                        builder.setContent(myNotificationView)
//                                .setSmallIcon(getSmallIconId(context, msg))
//                                .setTicker(msg.ticker)
//                                .setAutoCancel(true);
//
//                        return builder.build();

//                    default:
                return notification;
//                }
            }
        };
        mPushAgent.setMessageHandler(messageHandler);


        /**
         * 自定义行为的回调处理，参考文档：高级功能-通知的展示及提醒-自定义通知打开动作
         * UmengNotificationClickHandler是在BroadcastReceiver中被调用，故
         * 如果需启动Activity，需添加Intent.FLAG_ACTIVITY_NEW_TASK
         * */
        UmengNotificationClickHandler notificationClickHandler = new UmengNotificationClickHandler() {

            @Override
            public void launchApp(Context context, UMessage msg) {
                super.launchApp(context, msg);
            }

            @Override
            public void openUrl(Context context, UMessage msg) {
                super.openUrl(context, msg);
            }

            @Override
            public void openActivity(Context context, UMessage msg) {
                super.openActivity(context, msg);
            }

            @Override
            public void dealWithCustomAction(Context context, UMessage msg) {
                Toast.makeText(BaseApplication.getInstance(), msg.custom, Toast.LENGTH_LONG).show();
            }

            @Override
            public void handleMessage(Context context, UMessage uMessage) {
                // 去到指定页,判断帐号是否登录，如果没登录，就不起效
                String userId = null;
                if (BaseApplication.getAppType().equals(Constants.APP_DRIVER)) {
                    userId = DataStatic.getInstance().getDriver().getId();
                } else if (BaseApplication.getAppType().equals(Constants.APP_COMPANY)) {
                    userId = DataStatic.getInstance().getAppUserWrapper().getId();
                } else if (BaseApplication.getAppType().equals(Constants.APP_COMPANY)) {
                    userId = DataStatic.getInstance().getUserOwner().getId();
                }
                if (!TextUtils.isEmpty(userId)){
                    if (uMessage.extra != null && uMessage.extra.get(TO_PAGE) != null) {
                        switch (uMessage.extra.get(TO_PAGE)) {
                            case FLAG_ORDER:
                                // 订单中心
                                EventBus.getDefault().postSticky(new NotificationEvent(FLAG_ORDER));
                                break;
                            case FLAG_ORDER_DETAIL:
                                //去订单详情
                                String id = uMessage.extra.get("Id");
                                if (!TextUtils.isEmpty(id)) {
                                    EventBus.getDefault().postSticky(new NotificationEvent(FLAG_ORDER_DETAIL, id));
                                }
                                break;
                            case FLAG_PLATFORM_MESSAGE:
                                // 去平台列表
                                EventBus.getDefault().postSticky(new NotificationEvent(FLAG_PLATFORM_MESSAGE));
                                break;
                            case FLAG_ENTERPRISE_NEWS:
                                // 去企业消息列表
                                EventBus.getDefault().postSticky(new NotificationEvent(FLAG_ENTERPRISE_NEWS));
                                break;
                        }
                    }

                    super.handleMessage(context, uMessage);
                }
            }
        };
        //使用自定义的NotificationHandler
        mPushAgent.setNotificationClickHandler(notificationClickHandler);


        //注册推送服务 每次调用register都会回调该接口
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
//        //开启推送并设置注册的回调处理
//        mPushAgent.enable(ne);

        //使用完全自定义处理
        //mPushAgent.setPushIntentServiceClass(UmengNotificationService.class);

//        小米通道
        MiPushRegistar.register(this, "5261783093510", "OqprEShsCIOxBmi3aI/Nsw==");
//        华为通道
        HuaWeiRegister.register(this);
//        魅族通道
//        MeizuRegister.register(this, MEIZU_APPID, MEIZU_APPKEY);
    }

    /**
     * 发送声音
     *
     * @param msg 信息文字
     */
    protected void pushTts(String msg) {
        TTSController ttsManager = TTSController.getInstance(BasePushApplication.this);
        ttsManager.init();
        ttsManager.onGetNavigationText(msg);
    }


}
