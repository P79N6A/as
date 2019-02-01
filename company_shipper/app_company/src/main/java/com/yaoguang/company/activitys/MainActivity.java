package com.yaoguang.company.activitys;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.widget.Toast;

import com.yaoguang.appcommon.BaseMainActivity;
import com.yaoguang.appcommon.phone.home.message.MessageFragment;
import com.yaoguang.company.activitys.login.LoginActivity;
import com.yaoguang.lib.common.constants.Constants;
import com.yaoguang.appcommon.common.eventbus.NotificationEvent;
import com.yaoguang.company.R;
import com.yaoguang.company.home.HomeFragment;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import me.yokeyword.fragmentation.SupportFragment;
import me.yokeyword.fragmentation.anim.DefaultHorizontalAnimator;
import me.yokeyword.fragmentation.anim.FragmentAnimator;

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

    @Override
    public String getCodtType() {
        return Constants.APP_COMPANY;
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
                start(MessageFragment.newInstance(Constants.APP_COMPANY, 1));
            } else {
                start(messageFragment, SupportFragment.SINGLETASK);
            }
        } else if (mToPage.equals(FLAG_ENTERPRISE_NEWS)) {
            MessageFragment messageFragment = findFragment(MessageFragment.class);
            if (messageFragment == null) {
                start(MessageFragment.newInstance(Constants.APP_COMPANY, 0));
            } else {
                start(messageFragment, SupportFragment.SINGLETASK);
            }
        }
        super.onResume();
        mToPage = "";
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
                Toast.makeText(this, "再按一次退出", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //解除注册
        EventBus.getDefault().unregister(this);
    }

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
}
