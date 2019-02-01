package com.yaoguang.lib.appcommon.widget.recyclerview;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;

/**
 * 控制是否允许滑动
 * Created by zhongjh on 2017/11/28.
 */
public class ScrollLinearLayoutManager extends LinearLayoutManager {

    private boolean isScrollEnabled = true;

    public ScrollLinearLayoutManager(Context context) {
        super(context);
    }

    public void setScrollEnabled(boolean flag) {
        this.isScrollEnabled = flag;
    }

    @Override
    public boolean canScrollVertically() {
        return isScrollEnabled && super.canScrollVertically();
    }


}
