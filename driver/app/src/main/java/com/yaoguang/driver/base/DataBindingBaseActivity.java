package com.yaoguang.driver.base;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.yaoguang.common.R;
import com.yaoguang.driver.base.baseview.BaseActivity;
import com.yaoguang.driver.base.baseview.BasePresenter;
import com.yaoguang.common.common.InputMethodUtil;
import com.yaoguang.common.common.debounceclick.NoDoubleClickListener;


/**
 * 一个基类
 * Created by wly on 2017/3/24.
 */

public abstract class DataBindingBaseActivity<P extends BasePresenter, B extends ViewDataBinding> extends BaseActivity<P> {
    public Context mContext;
    private B b;

    static {
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
    }

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
        b = DataBindingUtil.setContentView(this, getLayoutId());
        setContentView(b.getRoot());
        mContext = this;
        try {
            initPresenter();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }
        initView();
        initListener();
    }

    protected abstract void initView();

    protected abstract int getLayoutId();

    protected abstract void initListener();

    @Override
    protected void onPause() {
        InputMethodUtil.hideKeyboard(DataBindingBaseActivity.this);
        super.onPause();
    }

    protected void initToolbarNav(Toolbar toolbar, String title, int flateMenu, Toolbar.OnMenuItemClickListener listener) {
        ((TextView) toolbar.findViewById(R.id.toolbar_title)).setText(title);
        if (flateMenu != -1)
            toolbar.inflateMenu(flateMenu);
        if (listener != null)
            toolbar.setOnMenuItemClickListener(listener);
        toolbar.findViewById(R.id.imgReturn).setOnClickListener(new NoDoubleClickListener() {
            @Override
            public void onNoDoubleClick(View v) {
                DataBindingBaseActivity.this.finish();
            }
        });
    }

    protected void initToolbarNav(Toolbar toolbar) {
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_white_24dp);
        toolbar.setNavigationOnClickListener(new NoDoubleClickListener() {
            @Override
            public void onNoDoubleClick(View v) {
                InputMethodUtil.hideKeyboard(DataBindingBaseActivity.this);
                pop();
            }
        });
    }
}
