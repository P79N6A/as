package com.yaoguang.driver.base;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yaoguang.driver.R;
import com.yaoguang.driver.base.baseview.BaseFragment;
import com.yaoguang.driver.base.baseview.BasePresenter;
import com.yaoguang.driver.databinding.ToolbarCommonBinding;

/**
 * DataBinding数据绑定
 * Created by wly on 2017/12/4 0004.
 */

public abstract class DataBindingFragment<P extends BasePresenter, B extends ViewDataBinding> extends BaseFragment<P> {
    @NonNull
    public B mDataBinding;
    @Nullable
    public Context mContext;
    public ToolbarCommonBinding mToolbarCommonBinding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mDataBinding = DataBindingUtil.inflate(inflater, getLayoutId(), null, false);
        if (mDataBinding == null)
            throw new NullPointerException("Please set the layout item in the layout!");
        if (mDataBinding.getRoot().findViewById(R.id.toolbarCommon) != null) {
            mToolbarCommonBinding = DataBindingUtil.bind(mDataBinding.getRoot().findViewById(R.id.toolbarCommon));
        }
        mContext = getContext();
        initView(mDataBinding.getRoot());
        initPresenter();
        initListener();
        return mDataBinding.getRoot();
    }

    protected abstract void initView(View view);
    protected abstract int getLayoutId();
    protected abstract void initListener();

    @Override
    public void onDestroyView() {
        if (mPresenter != null) {
            mPresenter.unSubscribe();
        }
        super.onDestroyView();
    }
}
