package com.yaoguang.appcommon.phone.activitys.welcome;

import android.view.View;

import com.yaoguang.lib.base.BaseActivity;

/**
 * Created by 韦理英
 * on 2017/6/8 0008.
 */

public abstract class BaseGuidance extends BaseActivity {
    protected abstract View[] getViewList();
    protected abstract void toLoginActivity();
}
