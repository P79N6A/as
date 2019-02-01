package com.yaoguang.lib.appcommon.utils;

import android.app.ActionBar;
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.view.View;
import android.widget.PopupWindow;

/**
 * Created by Administrator on 2017/7/4 0004.
 */

public class PopupWindowUtils {

    private PopupWindow mPopupWindow;

    public void init(Activity context, View view) {
        mPopupWindow = new PopupWindow(view, ActionBar.LayoutParams.WRAP_CONTENT, ActionBar.LayoutParams.WRAP_CONTENT, true);
        mPopupWindow.setTouchable(true);
        mPopupWindow.setOutsideTouchable(true);
        mPopupWindow.setBackgroundDrawable(new BitmapDrawable(context.getResources(), (Bitmap) null));
    }

    public void showAsDropDown(View showAsDropDown) {
        mPopupWindow.showAsDropDown(showAsDropDown);
    }
    public void showAsDropDown(View showAsDropDown, int x, int y) {
        mPopupWindow.showAsDropDown(showAsDropDown, x, y);
    }


    public void onDestroyPopupView() {
        if (mPopupWindow != null) {
            mPopupWindow.dismiss();
            mPopupWindow = null;
        }
    }
}
