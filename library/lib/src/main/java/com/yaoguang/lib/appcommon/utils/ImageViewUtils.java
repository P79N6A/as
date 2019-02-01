package com.yaoguang.lib.appcommon.utils;

import android.content.Context;
import android.widget.ImageView;

/**
 * Created by 韦理英
 * on 2017/6/8 0008.
 */
public class ImageViewUtils {

    public ImageViewUtils() {

    }

    public static ImageView getImageView(Context context, int resid) {
        ImageView iv = new ImageView(context);
        iv.setImageResource(resid);
        return iv;
    }


}
