package com.yaoguang.shipper.activitys;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.widget.Toast;

import com.umeng.analytics.MobclickAgent;
import com.yaoguang.appcommon.BaseMainActivity;
import com.yaoguang.appcommon.common.eventbus.NotificationEvent;
import com.yaoguang.appcommon.phone.contact2.contactnew.event.ContactNewRefreshEvent;
import com.yaoguang.appcommon.phone.home.message.MessageFragment;
import com.yaoguang.appcommon.phone.my.my.event.MyEvent;
import com.yaoguang.datasource.api.RongCloudApi;
import com.yaoguang.datasource.common.DataStatic;
import com.yaoguang.lib.base.BaseApplication;
import com.yaoguang.lib.common.ObjectUtils;
import com.yaoguang.lib.common.constants.Constants;
import com.yaoguang.lib.net.Api;
import com.yaoguang.lib.net.bean.BaseResponse;
import com.yaoguang.shipper.R;
import com.yaoguang.shipper.activitys.login.LoginActivity;
import com.yaoguang.shipper.home.HomeFragment;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import io.rong.imkit.RongIM;
import io.rong.imlib.RongIMClient;
import me.yokeyword.fragmentation.SupportFragment;
import me.yokeyword.fragmentation.anim.DefaultHorizontalAnimator;
import me.yokeyword.fragmentation.anim.FragmentAnimator;

import static com.yaoguang.appcommon.phone.my.my.event.MyEvent.REFRESH_UNREAD_CONTACT_COUNT;
import static com.yaoguang.lib.common.constants.Constants.FLAG_ENTERPRISE_NEWS;
import static com.yaoguang.lib.common.constants.Constants.FLAG_PLATFORM_MESSAGE;

/**
 * 类知乎 复杂嵌套Demo tip: 多使用右上角的"查看栈视图"
 * Created by YoKeyword on 16/6/2.
 */
public class MainActivity extends BaseMainActivity {

    // 再点一次退出程序时间设置
    private static final long WAIT_TIME = 2000L;
    private long TOUCH_TIME = 0;

    String mToPage = "";

    // 融云
    int getTokenI = -1;
    CompositeDisposable mCompositeDisposable = new CompositeDisposable();

    /**
     * @param activity 要跳转的activity
     * @param url      如果有url，就是要跳转广告界面的
     */
    public static void newInstance(Activity activity, String url) {
        if (TextUtils.isEmpty(url)) {
            activity.startActivity(new Intent(activity, MainActivity.class));
            activity.overridePendingTransition(R.anim.slide_right_in, R.anim.slide_left_out);
        } else {
            FestivalActivity.newInstance(activity, url, FestivalActivity.TYPE_MAIN);
        }
    }

    @Override
    public String getCodtType() {
        return Constants.APP_SHIPPER;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_container);

        HomeFragment fragment = findFragment(HomeFragment.class);
        if (fragment == null) {
            loadRootFragment(R.id.fl_container, HomeFragment.newInstance());
        }

        initView();

        //注册成为订阅者
        EventBus.getDefault().register(this);
    }

    @Override
    public FragmentAnimator onCreateFragmentAnimator() {
        // 设置默认Fragment动画  默认竖向(和安卓5.0以上的动画相同)
//        return super.onCreateFragmentAnimator();
        // 设置横向(和安卓4.x动画相同)
        return new DefaultHorizontalAnimator();
    }

    private void initView() {

    }

    @Override
    protected void onResume() {
        if (mToPage.equals(FLAG_PLATFORM_MESSAGE)) {
            MessageFragment messageFragment = findFragment(MessageFragment.class);
            if (messageFragment == null) {
                start(MessageFragment.newInstance(Constants.APP_SHIPPER, 1));
            } else {
                start(messageFragment, SupportFragment.SINGLETASK);
            }
        } else if (mToPage.equals(FLAG_ENTERPRISE_NEWS)) {
            MessageFragment messageFragment = findFragment(MessageFragment.class);
            if (messageFragment == null) {
                start(MessageFragment.newInstance(Constants.APP_SHIPPER, 0));
            } else {
                start(messageFragment, SupportFragment.SINGLETASK);
            }
        }
        super.onResume();
        mToPage = "";
        MobclickAgent.onResume(this); // 基础指标统计，不能遗漏
//        loginRongCloud();
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPause(this); // 基础指标统计，不能遗漏
        RongIM.getInstance().disconnect();
    }

    @Override
    public void onBackPressedSupport() {
        // 主页的Fragment
        if (getSupportFragmentManager().getBackStackEntryCount() > 1) {
            pop();
        } else {
            if (System.currentTimeMillis() - TOUCH_TIME < WAIT_TIME) {
                finish();
            } else {
                TOUCH_TIME = System.currentTimeMillis();
                Toast.makeText(BaseApplication.getInstance(), "再按一次退出", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //解除注册
        EventBus.getDefault().unregister(this);

        mCompositeDisposable.clear();
    }


    /**
     * 接收消息推送
     *
     * @param event
     */
    @Subscribe(sticky = true)
    public void onNotificationEvent(NotificationEvent event) {
        mToPage = event.getToPage();
        EventBus.getDefault().removeStickyEvent(event);
    }

    @Override
    public void startLoginActivity() {
        LoginActivity.newInstance(this, true, null, null);
        MainActivity.this.finish();
    }

    //region 有关融云的

//    // 登录融云
//    private void loginRongCloud() {
//        if (TextUtils.isEmpty(DataStatic.getInstance().getRongCloudToken())){
//            // 如果为空的，就重新获取
//            getRongCloudToken();
//            return;
//        }
//        RongIM.connect(DataStatic.getInstance().getRongCloudToken(), new RongIMClient.ConnectCallback() {
//            @Override
//            public void onTokenIncorrect() {
//                getTokenI++;
//                getRongCloudToken();
//            }
//
//            @Override
//            public void onSuccess(String s) {
//                RongIMClient.setOnReceiveMessageListener((message, i) -> {
//                    // 刷新有关消息
//                    EventBus.getDefault().postSticky(new ContactNewRefreshEvent());
//                    EventBus.getDefault().postSticky(new MyEvent(ObjectUtils.parseString(REFRESH_UNREAD_CONTACT_COUNT)));
//                    return false;
//                });
//            }
//
//            @Override
//            public void onError(RongIMClient.ErrorCode errorCode) {
//                if (RongIMClient.ErrorCode.RC_CONN_USER_OR_PASSWD_ERROR == errorCode) {
//                    getTokenI++;
//                    getRongCloudToken();
//                }
//
//            }
//        });
//    }
//
//    // 获取融云token
//    private void getRongCloudToken() {
//        // 获取token
//        if (getTokenI == 0) {
//            RongCloudApi rongCloudApi = Api.getInstance().retrofit.create(RongCloudApi.class);
//            rongCloudApi.getRongyunToken("2", DataStatic.getInstance().getUserOwner().getId())
//                    .subscribeOn(Schedulers.io())
//                    .observeOn(AndroidSchedulers.mainThread())
//                    .subscribe(new Observer<BaseResponse<String>>() {
//                        @Override
//                        public void onSubscribe(Disposable d) {
//                            mCompositeDisposable.add(d);
//                        }
//
//                        @Override
//                        public void onNext(BaseResponse<String> String) {
//                            // 保存token
//                            DataStatic.getInstance().setRongCloudToken(String.getResult());
//                            if (String.getState().equals("200")) {
//                                // 重新登录融云
//                                loginRongCloud();
//                            } else {
//                                getRongCloudToken();
//                            }
//                        }
//
//                        @Override
//                        public void onError(Throwable e) {
//                            getRongCloudToken();
//                        }
//
//                        @Override
//                        public void onComplete() {
//
//                        }
//                    });
//        } else {
//            RongCloudApi rongCloudApi = Api.getInstance().retrofit.create(RongCloudApi.class);
//            rongCloudApi.refreshRongyunToken("2", DataStatic.getInstance().getUserOwner().getId())
//                    .subscribeOn(Schedulers.io())
//                    .observeOn(AndroidSchedulers.mainThread())
//                    .subscribe(new Observer<BaseResponse<String>>() {
//                        @Override
//                        public void onSubscribe(Disposable d) {
//                            mCompositeDisposable.add(d);
//                        }
//
//                        @Override
//                        public void onNext(BaseResponse<String> String) {
//                            // 保存token
//                            DataStatic.getInstance().setRongCloudToken(String.getResult());
//                            if (String.getState().equals("200") && !String.getResult().equals("")) {
//                                //重新登录融云
//                                loginRongCloud();
//                            } else {
//                                getRongCloudToken();
//                            }
//                        }
//
//                        @Override
//                        public void onError(Throwable e) {
//                            getRongCloudToken();
//                        }
//
//                        @Override
//                        public void onComplete() {
//
//                        }
//                    });
//        }
//
//
//    }

    //endregion 有关融云的

}
