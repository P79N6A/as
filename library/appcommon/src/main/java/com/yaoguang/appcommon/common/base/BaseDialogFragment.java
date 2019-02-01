package com.yaoguang.appcommon.common.base;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.app.DialogFragment;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;

import com.yaoguang.lib.common.ConvertUtils;

/**
 * 作    者：韦理英
 * 时    间：2017/9/11 0011.
 * 描    述：底部显示
 */
public class BaseDialogFragment<T> extends DialogFragment {
    public Comeback<T> comeback;
    private T t;
    public int showLocation = Gravity.BOTTOM;
    public int backgroundColor = Color.WHITE;
    public int height = ConvertUtils.dp2px(280);

    @Override
    public void onStart() {
        Window window = getDialog().getWindow();
        WindowManager.LayoutParams params = window.getAttributes();
        // 右部显示
        params.gravity = showLocation;
        params.height = height;
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        window.setAttributes(params);
        window.setBackgroundDrawable(new ColorDrawable(backgroundColor));
        super.onStart();
    }

    public void notice(T t) {
        if (t != null) {
            comeback.success(t);
        }
    }

    public void setComeback(Comeback comeback) {
        this.comeback = comeback;
    }

    public interface Comeback<T> {
        public void success(T t);
    }
}
