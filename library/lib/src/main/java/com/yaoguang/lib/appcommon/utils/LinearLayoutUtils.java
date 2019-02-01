package com.yaoguang.lib.appcommon.utils;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.LinearLayout;

/**
 * Created by Administrator on 2017/6/8 0008.
 */
public class LinearLayoutUtils {
    public LinearLayoutUtils() {
        super();
    }

    public static LinearLayout getFullScreenView(Context context, int resid) {
        LinearLayout ll = new LinearLayout(context);
        ll.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        ll.addView(ImageViewUtils.getImageView(context, resid));
        return ll;
    }
    public static LinearLayout.LayoutParams geWidthMatchView() {
        return new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
    }
}
