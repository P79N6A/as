package com.yaoguang.appcommon.common.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.yaoguang.lib.R;
import com.yaoguang.lib.base.BaseFragment;
import com.yaoguang.lib.common.debounceclick.NoDoubleClickListener;


/**
 * zhongjh:
 * 一些子fragment继承它
 */
public abstract class BaseBackFragment extends BaseFragment {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    /**
     * 默认的一个toolbar
     *
     * @param toolbar 状态栏
     */
    protected void initToolbarNav(Toolbar toolbar) {
        toolbar.setNavigationIcon(R.drawable.ic_return);
        toolbar.setNavigationOnClickListener(new NoDoubleClickListener() {
            @Override
            public void onNoDoubleClick(View v) {
                hideKeyboard();
                pop();
            }
        });
    }

    /**
     * 默认的一个toolbar
     *
     * @param toolbar 状态栏
     * @param isShow  是否显示
     */
    protected void initToolbarNav(Toolbar toolbar, boolean isShow) {
        if (!isShow) {
            toolbar.setVisibility(View.GONE);
            return;
        }
        toolbar.setNavigationIcon(R.drawable.ic_return);
        toolbar.setNavigationOnClickListener(new NoDoubleClickListener() {
            @Override
            public void onNoDoubleClick(View v) {
                hideKeyboard();
                pop();
            }
        });
    }

    /**
     * 默认的一个toolbar
     *
     * @param toolbar  状态栏
     * @param runnable 退出的事件（这个需要优化，写接口）
     */
    protected void initToolbarNav(Toolbar toolbar, final Runnable runnable) {
        toolbar.setNavigationIcon(R.drawable.ic_return);
        toolbar.setNavigationOnClickListener(new NoDoubleClickListener() {
            @Override
            public void onNoDoubleClick(View v) {
                hideKeyboard();
                runnable.run();
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
