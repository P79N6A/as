package com.yaoguang.lib.common.view;

import android.support.design.widget.TabLayout;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;

/**
 * TabLayout控件
 */
public class TabLayoutUtils {

    /**
     * 设置启用/禁用点击
     *
     * @param tabLayout
     */
    public static void setEnable(TabLayout tabLayout, final boolean isEnable) {
        LinearLayout tabStrip = ((LinearLayout) tabLayout.getChildAt(0));
        for (int k = 0; k < tabStrip.getChildCount(); k++) {
            // 因为所有触摸事件，返回false都是继续下一个的，所以跟enable是相反的
            tabStrip.getChildAt(k).setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View view, MotionEvent motionEvent) {
                    return !isEnable;
                }
            });
        }
    }

}
