package com.yaoguang.appcommon.common.base;

import android.content.Context;
import android.databinding.ViewDataBinding;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.yaoguang.lib.R;
import com.yaoguang.lib.base.BaseFragment;
import com.yaoguang.lib.base.BaseFragment2;
import com.yaoguang.lib.base.BaseFragmentDataBind;
import com.yaoguang.lib.common.debounceclick.NoDoubleClickListener;


/**
 * Created by zhongjh on 2017/3/27.
 * 一个基类,属于main的Fragment
 */
public abstract class BaseMainFragment<B extends ViewDataBinding> extends BaseFragmentDataBind<B> {

    protected OnFragmentOpenDrawerListener mOpenDraweListener;

    protected void initToolbarNav(Toolbar toolbar) {
        initToolbarNav(toolbar, false);
    }

    /**
     * 初始化toolbar
     *
     * @param toolbar toolbar
     * @param isMenu  是否显示menu
     */
    protected void initToolbarNav(Toolbar toolbar, boolean isMenu) {
        if (isMenu) {
            toolbar.setNavigationIcon(R.drawable.ic_menu_white_24dp);
            toolbar.setNavigationOnClickListener(new NoDoubleClickListener() {
                @Override
                public void onNoDoubleClick(View v) {
                    if (mOpenDraweListener != null) {
                        mOpenDraweListener.onOpenDrawer();
                    }
                }
            });
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentOpenDrawerListener) {
            mOpenDraweListener = (OnFragmentOpenDrawerListener) context;
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mOpenDraweListener = null;
    }

    public interface OnFragmentOpenDrawerListener {
        void onOpenDrawer();
    }
}
