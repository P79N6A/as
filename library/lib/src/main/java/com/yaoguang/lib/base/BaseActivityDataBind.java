package com.yaoguang.lib.base;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;

/**
 * Activity 的基类
 * Created by zhongjh on 2018/3/15.
 */
public abstract class BaseActivityDataBind<B extends ViewDataBinding> extends BaseActivity2 {

    protected B mDataBinding;


    @Override
    public void initDataBind(int layoutResID) {
        mDataBinding = DataBindingUtil.setContentView(this, layoutResID);
    }


}
