package com.yaoguang.lib.appcommon.widget.recyclerview;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.util.AttributeSet;

/**
 * Created by zhongjh on 2018/8/6.
 */

public class LinearLayoutManagerAutoMeasure extends LinearLayoutManager {
    public LinearLayoutManagerAutoMeasure(Context context) {
        super(context);
    }

    public LinearLayoutManagerAutoMeasure(Context context, int orientation, boolean reverseLayout) {
        super(context, orientation, reverseLayout);
    }

    public LinearLayoutManagerAutoMeasure(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    public boolean isAutoMeasureEnabled() {
        return true;
    }
}
