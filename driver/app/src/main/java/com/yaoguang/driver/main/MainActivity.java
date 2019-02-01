package com.yaoguang.driver.main;

import android.app.Activity;
import android.content.Intent;
import android.text.TextUtils;
import android.view.MotionEvent;

import com.yaoguang.appcommon.BaseMainActivity;
import com.yaoguang.driver.App;
import com.yaoguang.driver.R;
import com.yaoguang.driver.activitys.FestivalActivity;
import com.yaoguang.driver.base.DataBindingBaseActivity;
import com.yaoguang.driver.databinding.ActivityMainBinding;

import java.util.ArrayList;

import me.yokeyword.fragmentation.anim.DefaultHorizontalAnimator;
import me.yokeyword.fragmentation.anim.FragmentAnimator;

/**
 * 仿微信交互方式Demo
 * Created by YoKeyword on 16/6/30.
 */
public class MainActivity extends DataBindingBaseActivity<MainContact.MainPresenter, ActivityMainBinding> {

    @Override
    protected void initView() {
        if (findFragment(MainFragment.class) == null) {
            loadRootFragment(R.id.fl_container, MainFragment.newInstance());
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initListener() {

    }

    @Override
    public void onBackPressedSupport() {
        // 对于 4个类别的主Fragment内的回退back逻辑,已经在其onBackPressedSupport里各自处理了
        super.onBackPressedSupport();
    }

    @Override
    public FragmentAnimator onCreateFragmentAnimator() {
        // 设置横向(和安卓4.x动画相同)
        return new DefaultHorizontalAnimator();
    }

    @Override
    protected void onDestroy() {
        /*
          关闭定位
         */
        App.locationHistoryManager.destroyLocation();
        super.onDestroy();
    }

    //触摸事件
    public interface MyDispatchTouchEvent {
        boolean dispatchTouchEvent(MotionEvent ev);
    }

    private ArrayList<MainActivity.MyDispatchTouchEvent> dispatchTouchEvents = new ArrayList<>();

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        for (MainActivity.MyDispatchTouchEvent listener : dispatchTouchEvents) {
            if (listener != null) {
                listener.dispatchTouchEvent(ev);
            }
        }
        return super.dispatchTouchEvent(ev);
    }

    public void registerMyOnTouchListener(MainActivity.MyDispatchTouchEvent myDispatchTouchEvent) {
        dispatchTouchEvents.add(myDispatchTouchEvent);
    }

    public void unregisterMyOnTouchListener(MainActivity.MyDispatchTouchEvent myDispatchTouchEvent) {
        dispatchTouchEvents.remove(myDispatchTouchEvent);
    }

    public static void newInstance(Activity activity, String url) {
        if (TextUtils.isEmpty(url)) {
            activity.startActivity(new Intent(activity, MainActivity.class));
            activity.overridePendingTransition(R.anim.slide_right_in, R.anim.slide_left_out);
        } else {
            FestivalActivity.newInstance(activity, url, FestivalActivity.TYPE_MAIN);
        }
    }
}
