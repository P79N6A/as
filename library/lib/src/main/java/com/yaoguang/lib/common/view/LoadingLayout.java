package com.yaoguang.lib.common.view;

import android.content.Context;
import android.util.AttributeSet;

/**
 * Created by zhongjh on 2019/1/11.
 */

public class LoadingLayout extends ezy.ui.layout.LoadingLayout {

    public LoadingLayout(Context context) {
        super(context);
    }

    public LoadingLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public LoadingLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void showLoading() {
//        super.showLoading();
    }

    @Override
    public void showError() {
        super.showError();
        this.setErrorText("");
    }
}
