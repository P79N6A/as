package com.yaoguang.appcommon;

import android.Manifest;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;

import com.yaoguang.interactor.common.activity.main.MainContact;
import com.yaoguang.interactor.common.activity.main.MainPresenterImpl;
import com.yaoguang.lib.base.BaseActivity;
import com.yaoguang.lib.base.interfaces.BasePresenter;

import java.util.ArrayList;
import java.util.List;

import pub.devrel.easypermissions.EasyPermissions;

//import com.yaoguang.appcommon.broadcastreceiver.TimerBroadcastReceiver;

/**
 * 将推送移动到这里来，因为一些手机的推送，需要获取电话权限，否则闪退
 * Created by zhongjh on 2017/7/31.
 */
public abstract class BaseMainActivity extends BaseActivity implements EasyPermissions.PermissionCallbacks, MainContact.MainView {

    MainContact.MainPresenter mPresenter;

    public abstract String getCodtType();

    static final int ACCESS_FINE_LOCATION = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = new MainPresenterImpl(this, getCodtType());
        mPresenter.openActivity();
    }

    @Nullable
    @Override
    public View onCreateView(String name, Context context, AttributeSet attrs) {
        if (EasyPermissions.hasPermissions(this, Manifest.permission.ACCESS_FINE_LOCATION)) {
        } else {
            EasyPermissions.requestPermissions(this, "获取天气以及查看装卸货动态需要获取位置权限",
                    ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION);
        }
        return super.onCreateView(name, context, attrs);
    }

    @Override
    public BasePresenter getPresenter() {
        return mPresenter;
    }

    //成功
    @Override
    public void onPermissionsGranted(int requestCode, List<String> list) {
        if (requestCode == ACCESS_FINE_LOCATION) {
        }
    }

    //失败
    @Override
    public void onPermissionsDenied(int requestCode, List<String> list) {
        if (requestCode == ACCESS_FINE_LOCATION) {
            EasyPermissions.requestPermissions(this, "获取天气以及查看装卸货动态需要获取位置权限",
                    ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION);
        }
    }

    //触摸事件
    public interface MyDispatchTouchEvent {
        boolean dispatchTouchEvent(MotionEvent ev);
    }

    private ArrayList<MyDispatchTouchEvent> dispatchTouchEvents = new ArrayList<>();

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        for (MyDispatchTouchEvent listener : dispatchTouchEvents) {
            if (listener != null) {
                listener.dispatchTouchEvent(ev);
            }
        }
        return super.dispatchTouchEvent(ev);
    }

    public void registerMyOnTouchListener(MyDispatchTouchEvent myDispatchTouchEvent) {
        dispatchTouchEvents.add(myDispatchTouchEvent);
    }

    public void unregisterMyOnTouchListener(MyDispatchTouchEvent myDispatchTouchEvent) {
        dispatchTouchEvents.remove(myDispatchTouchEvent);
    }

    /**
     * 根据EditText所在坐标和用户点击的坐标相对比，来判断是否隐藏键盘，因为当用户点击EditText时没必要隐藏
     *
     * @param v
     * @param event
     * @return
     */
    public static boolean isShouldHideInput(View v, MotionEvent event) {
        if (v != null && (v instanceof EditText)) {
            int[] l = {0, 0};
            v.getLocationInWindow(l);
            int left = l[0], top = l[1], bottom = top + v.getHeight(), right = left
                    + v.getWidth();
            if (event.getX() > left && event.getX() < right
                    && event.getY() > top && event.getY() < bottom) {
                // 点击EditText的事件，忽略它。
                return false;
            } else {
                return true;
            }
        }
        // 如果焦点不是EditText则忽略，这个发生在视图刚绘制完，第一个焦点不在EditView上，和用户用轨迹球选择其他的焦点
        return false;
    }

}
