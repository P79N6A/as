package com.yaoguang.lib.base;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;
import android.widget.Toast;

import com.yaoguang.lib.R;
import com.yaoguang.lib.appcommon.utils.ProgressDialogHelper;
import com.yaoguang.lib.base.interfaces.BasePresenter;
import com.yaoguang.lib.common.debounceclick.NoDoubleClickListener;

import java.util.List;

import me.yokeyword.fragmentation.SupportActivity;


/**
 * Created by zhongjh on 2017/3/24.
 * 一个基类
 */
public abstract class BaseActivity extends SupportActivity {


    private ProgressDialogHelper progressDialogHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            WindowManager.LayoutParams localLayoutParams = getWindow().getAttributes();
            localLayoutParams.flags = (WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS | localLayoutParams.flags);
        }



        super.onCreate(savedInstanceState);

//        StatusBarUtil.setColor(BaseActivity.this,getResources().getColor(R.color.primary),0);
    }

    /**
     * 取消订阅
     */
    public void unSubscribe() {
        if (getPresenter() != null) {
            getPresenter().unSubscribe();
        }
    }

    /**
     * 注入 presenter,执行通用操作
     */
    public abstract BasePresenter getPresenter();

    /**
     * Activity的返回
     *
     * @param requestCode 请求码
     * @param resultCode  返回码
     * @param data        数据源
     */
    @SuppressLint("RestrictedApi")
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        FragmentManager fragmentManager = getSupportFragmentManager();
        if (fragmentManager.getFragments() != null)
            for (int indext = 0; indext < fragmentManager.getFragments().size(); indext++) {
                Fragment fragment = fragmentManager.getFragments().get(indext); //找到第一层Fragment
                String TAG = "BaseActivity";
                if (fragment == null)
                    Log.w(TAG, "Activity result no fragment exists for index: 0x"
                            + Integer.toHexString(requestCode));
                else
                    handleResult(fragment, requestCode, resultCode, data);
            }
    }

    /**
     * 递归调用，对所有子Fragement生效
     *
     * @param frag        递归的fragment
     * @param requestCode 请求码
     * @param resultCode  返回码
     * @param data        数据源
     */
    private void handleResult(Fragment frag, int requestCode, int resultCode,
                              Intent data) {
        frag.onActivityResult(requestCode & 0xffff, resultCode, data);
        @SuppressLint("RestrictedApi") List<Fragment> frags = frag.getChildFragmentManager().getFragments();
        if (frags != null) {
            for (Fragment f : frags) {
                if (f != null)
                    handleResult(f, requestCode, resultCode, data);
            }
        }
    }

    /**
     * 重回界面或者进入界面都调用
     */
    @Override
    protected void onResume() {
//        ULog.i("onResume");
        super.onResume();
    }

    /**
     * 暂停界面调用
     */
    @Override
    protected void onPause() {
        hideProgressDialog();
        hideKeyboard();
        super.onPause();
    }

    /**
     * 销毁界面
     */
    @Override
    protected void onDestroy() {
        unSubscribe();
        hideKeyboard();
        super.onDestroy();
    }

    /**
     * 初始化顶部
     *
     * @param toolbar   状态栏
     * @param title     标题
     * @param flateMenu 菜单资源文件
     * @param listener  事件
     */
    protected void initToolbarNav(Toolbar toolbar, String title, int flateMenu, Toolbar.OnMenuItemClickListener listener) {
        ((TextView) toolbar.findViewById(R.id.toolbar_title)).setText(title);
        if (flateMenu != -1)
            toolbar.inflateMenu(flateMenu);
        if (listener != null)
            toolbar.setOnMenuItemClickListener(listener);
        toolbar.findViewById(R.id.imgReturn).setOnClickListener(new NoDoubleClickListener() {
            @Override
            public void onNoDoubleClick(View v) {
                BaseActivity.this.finish();
            }
        });
    }

    /**
     * 显示相关消息
     *
     * @param msg 消息
     */
    public void showToast(String msg) {
        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
    }

    /**
     * 隐藏软键盘
     */
    public void hideKeyboard() {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null) {
            imm.hideSoftInputFromWindow(getWindow().getDecorView().getWindowToken(), 0);
        }
    }

    /**
     * 显示等待框
     *
     * @param title 标题
     * @param msg   消息
     */
    public void showProgressDialog(String title, String msg) {
        if (progressDialogHelper != null) {
            progressDialogHelper.showProgressDialog(title, msg);
        } else {
            progressDialogHelper = new ProgressDialogHelper(this);
            progressDialogHelper.showProgressDialog(title, msg);
        }
    }

    /**
     * 显示等待框
     *
     * @param msg 消息
     */
    public void showProgressDialog(String msg) {
        if (progressDialogHelper != null) {
            progressDialogHelper.showProgressDialog(null, msg);
        } else {
            progressDialogHelper = new ProgressDialogHelper(this);
            progressDialogHelper.showProgressDialog(null, msg);
        }
    }

    /**
     * 显示等待框
     */
    public void showProgressDialog() {
        showProgressDialog(null, null);
    }

    /**
     * 隐藏等待框
     */
    public void hideProgressDialog() {
        if (progressDialogHelper != null) progressDialogHelper.dismiss();
    }


}
