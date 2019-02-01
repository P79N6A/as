package com.yaoguang.lib.common;

import android.graphics.Rect;
import android.support.annotation.NonNull;
import android.view.View;

/**
 * 文件名：
 * 描    述： view 工具
 * 作    者：韦理英
 * 时    间：2017/8/10 0010.
 * 版    权：
 */
public class ViewUtils {

    /**
     * 文件名：
     * 描    述：当 View 有一点点不可见时立即返回false!
     * 作    者：韦理英
     * 时    间：
     * 版    权：
     * @param target
     *        [不看见的view]
     * return
     *        [false 是可见 ture是可见]
     */
    public static boolean isVisibleLocal(@NonNull View target){
        Rect rect =new Rect();
        target.getLocalVisibleRect(rect);
        return rect.top==0;
    }
}
