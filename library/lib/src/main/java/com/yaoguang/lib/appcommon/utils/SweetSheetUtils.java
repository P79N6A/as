package com.yaoguang.lib.appcommon.utils;

import android.app.Activity;
import android.content.Context;
import android.widget.FrameLayout;

import com.mingle.sweetpick.DimEffect;
import com.mingle.sweetpick.RecyclerViewDelegate;
import com.mingle.sweetpick.SweetSheet;
import com.yaoguang.lib.common.android.InputMethodUtil;
import com.yaoguang.lib.common.UiUtils;
import com.yaoguang.lib.common.displaymetrics.DisplayMetricsUtils;

/**
 * Created by 韦理英
 * on 2017/7/20 0020.
 */

public class SweetSheetUtils {

    public static void show(final Context context, FrameLayout frameView, String title, int menuId, int height, int requestCode, SweetSheet.OnMenuItemClickListener onItemListener) {
        InputMethodUtil.hideKeyboard((Activity) context);

        final SweetSheet sweetSheet = new SweetSheet(frameView);
        sweetSheet.setMenuList(menuId);
        sweetSheet.setTitle(title);
        RecyclerViewDelegate recyclerViewDelegate = new RecyclerViewDelegate(true);
        recyclerViewDelegate.setContentHeight(DisplayMetricsUtils.dip2px(height == 0 ? 200 : height));
        sweetSheet.setDelegate(recyclerViewDelegate);
        sweetSheet.setBackgroundEffect(new DimEffect(4));
        sweetSheet.setOnMenuItemClickListener(onItemListener);
        sweetSheet.show();

        //  按返回键，hide
        UiUtils.listenerBackKey(sweetSheet.getDelegate().mRootView, new Runnable() {
            @Override
            public void run() {
                if (sweetSheet != null && sweetSheet.isShow()) {
                    sweetSheet.dismiss();
                } else if (context != null) {
                    ((Activity) context).onBackPressed();
                }
            }
        });
    }

    public static void showAndHideKeyboard(Context context, FrameLayout frameView, int menuId,int requestCode, SweetSheet.OnMenuItemClickListener onItemListener) {

        show(context, frameView, "选择图片来源", menuId, 0, requestCode, onItemListener);
    }

    public static void showAndHideKeyboard(Context context, FrameLayout frameView, int menuId, int height, int requestCode,SweetSheet.OnMenuItemClickListener onItemListener) {
        show(context, frameView, "选择图片来源", menuId, height, requestCode, onItemListener);
    }
}
