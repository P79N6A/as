package com.yaoguang.driver.nav;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yaoguang.common.base.BaseFragment;
import com.yaoguang.common.base.interfaces.BasePresenter;


/**
 * 文件名：
 * 描    述：
 * 作    者：韦理英
 * 时    间：2017/8/24 0024.
 * 版    权：
 */
public class NullFragment extends BaseFragment {

    public static NullFragment newInstance() {
        
        Bundle args = new Bundle();
        
        NullFragment fragment = new NullFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public BasePresenter getPresenter() {
        return null;
    }
}
