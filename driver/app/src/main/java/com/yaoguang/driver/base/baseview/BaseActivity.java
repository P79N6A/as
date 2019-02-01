package com.yaoguang.driver.base.baseview;

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

import com.yaoguang.common.R;
import com.yaoguang.common.base.interfaces.BaseView;
import com.yaoguang.common.common.debounceclick.NoDoubleClickListener;
import com.yaoguang.common.appcommon.utils.ProgressDialogHelper;
import com.yaoguang.driver.util.InstanceUtil;

import java.lang.reflect.ParameterizedType;
import java.util.List;

import me.yokeyword.fragmentation.SupportActivity;


/**
 * Created by wly on 2017/3/24.
 * 一个基类
 */
public abstract class BaseActivity<P extends BasePresenter> extends SupportActivity {

    private String TAG = "BaseActivity";

    private P p;
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

        progressDialogHelper = new ProgressDialogHelper(this);

        super.onCreate(savedInstanceState);
    }

    /**
     * 重回界面或者进入界面都调用
     */
    @Override
    protected void onResume() {
        super.onResume();
    }

    /**
     * 暂停界面调用
     */
    @Override
    protected void onPause() {
        hideDialog();
        hideKeyboard();
        super.onPause();
    }

    /**
     * 销毁界面
     */
    @Override
    protected void onDestroy() {
        if (p != null) {
            p.unSubscribe();
        }
        hideKeyboard();
        super.onDestroy();
    }

    /**
     * 初始化控制层
     */
    protected void initPresenter() throws IllegalAccessException, java.lang.InstantiationException {
        if (this instanceof BaseView &&
                this.getClass().getGenericSuperclass() instanceof ParameterizedType &&
                ((ParameterizedType) (this.getClass().getGenericSuperclass())).getActualTypeArguments().length > 0) {
            Class mPresenterClass = (Class) ((ParameterizedType) (this.getClass()
                    .getGenericSuperclass())).getActualTypeArguments()[0];
            p = (P) InstanceUtil.getInstance(mPresenterClass);
            if (p != null) p.setView(this);
        }
    }

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
                if (fragment == null)
                    Log.w(TAG, "Activity click no fragment exists for index: 0x"
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
        if (progressDialogHelper != null)
            progressDialogHelper.showProgressDialog(title, msg);
    }

    /**
     * 显示等待框
     *
     * @param msg 消息
     */
    public void showProgressDialog(String msg) {
        if (progressDialogHelper != null)
            progressDialogHelper.showProgressDialog(null, msg);
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
    public void hideDialog() {
        if (progressDialogHelper != null) progressDialogHelper.dismiss();
    }




}
