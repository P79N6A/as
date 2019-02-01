package com.yaoguang.lib.common.tip;

import android.app.Activity;
import android.content.Intent;

import com.yaoguang.lib.common.displaymetrics.DisplayMetricsSPUtils;
import com.yaoguang.lib.common.displaymetrics.DisplayMetricsUtils;
import com.yaoguang.lib.entity.TipView;

import java.util.ArrayList;

/**
 * 这是封装提示的工具类
 * Created by zhongjh on 2017/11/22.
 */
public class TipUtils {

    /**
     * 开启 start
     *
     * @param tipViews 所有需要显示tip的view
     * @param activity 母窗体
     */
    public static void start(ArrayList<TipView> tipViews, Activity activity) {
        // 循环这些view,计算图片位置，偏移等
        for (TipView tipView : tipViews) {
            int[] location = new int[2];
            tipView.getView().getLocationOnScreen(location);
            // 10dp是图片的额外符号，需要減掉
            tipView.setLeft(location[0] + DisplayMetricsUtils.dip2px(tipView.getLeft()));
            // 加上状态栏
            tipView.setTop(location[1] - DisplayMetricsSPUtils.getStatusBarHeight(tipView.getView().getContext()) + DisplayMetricsUtils.dip2px(tipView.getTop()));
            tipView.setRight(tipView.getLeft() + tipView.getView().getWidth());
            tipView.setBottom(tipView.getTop() + tipView.getView().getHeight());
        }
        Intent intent = new Intent(activity, TipsActivity.class);
        intent.putExtra("tipViews", tipViews);
        activity.startActivity(intent);
    }

}
