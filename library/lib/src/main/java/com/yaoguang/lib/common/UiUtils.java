package com.yaoguang.lib.common;

import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yaoguang.lib.R;
import com.yaoguang.lib.adapter.BaseLoadMoreRecyclerAdapter;
import com.yaoguang.lib.base.BaseApplication;

/**
 * 文件名：
 * 描    述： fragment 的 常用工具
 * 作    者：韦理英
 * 时    间：2017/8/10 0010.
 * 版    权：
 */
public class UiUtils {

    /**
     * 描    述：监听fragment返回键
     * 作    者：韦理英
     * 时    间：
     *
     * @param view     [监听的 view]
     * @param runnable [运行方法]
     */

    public static void listenerBackKey(@NonNull View view, @NonNull final Runnable runnable) {
        if (view == null) return;
        // 获取触摸焦点
        view.setFocusableInTouchMode(true);
        // view 获取焦点
        view.requestFocus();
        // 监听按键
        view.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                // 如果是返回键
                if (event.getAction() == KeyEvent.ACTION_UP && keyCode == KeyEvent.KEYCODE_BACK) {
                    // 执行对应方法
                    runnable.run();
                    return false;
                }
                return true;
            }
        });
    }

    /**
     * 描    述：获取view的tag并转成boolean
     * 作    者：韦理英
     * 时    间：
     *
     * @param view view
     * @return true
     */
    public static boolean getViewTag(View view) {
        return view.getTag() != null && (Boolean) view.getTag();
    }

    /**
     * 描    述：兼容性 获取color
     * 作    者：韦理英
     * 时    间：
     *
     * @param value [参数说明] 颜色值
     *              return [return说明]
     */
    public static int getColor(int value) {
        return ContextCompat.getColor(BaseApplication.getInstance().getBaseContext(), value);
    }

    /**
     * 描    述：兼容性 获取drawable
     * 作    者：韦理英
     * 时    间：
     *
     * @param res [参数说明]
     *            return
     *            [return说明]
     */

    public static Drawable getDrawable(int res) {
        return ContextCompat.getDrawable(BaseApplication.getInstance().getBaseContext(), res);
    }

    /**
     * 描    述：获取string
     * 作    者：韦理英
     * 时    间：
     *
     * @param resid [参数说明]
     */
    @NonNull
    public static String getString(int resid) {
        return BaseApplication.getInstance().getBaseContext().getString(resid);
    }

    /**
     * textView 获取文字
     * @param view textView
     * @return 文字
     */
    public static String getText(TextView view) {
        return view.getText().toString();
    }

    public static void emptyStatus(PageStatus pageStatus, ImageView ivEmpty, RelativeLayout rlEmpty, BaseLoadMoreRecyclerAdapter adapter) {
        if (pageStatus == PageStatus.HAS_DATA) {
            rlEmpty.setVisibility(View.GONE);
            return;
        }

        adapter.getList().clear();
        adapter.notifyDataSetChanged();
        rlEmpty.setVisibility(View.VISIBLE);
        if (pageStatus == PageStatus.NO_ORDER) {
            ivEmpty.setImageResource(R.drawable.ic_dd_k);
        } else if (pageStatus == PageStatus.NO_NET) {
            ivEmpty.setImageResource(R.drawable.ic_wl_k);
        } else if (pageStatus == PageStatus.NO_DATA) {
            ivEmpty.setImageResource(R.drawable.ic_sj_k);
        } else if (pageStatus == PageStatus.NO_MESSAGE) {
            ivEmpty.setImageResource(R.drawable.ic_xx_k);
        }
    }

    /**
     * HAS_DATA,  有数据
     * NO_DATA,   无数据
     * NO_NET,    无网络
     * NO_ORDER,  无订单
     * NO_MESSAGE 无消息
     */
    public enum PageStatus {
        HAS_DATA, NO_DATA, NO_NET, NO_ORDER, NO_MESSAGE
    }


}
