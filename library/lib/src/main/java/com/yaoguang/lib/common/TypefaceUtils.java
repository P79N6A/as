package com.yaoguang.lib.common;

import android.graphics.Typeface;
import android.widget.TextView;

/**
 * 文字字体
 * Created by zhongjh on 2017/10/17.
 */
public enum TypefaceUtils {

    TYPEFACE;

    private static Typeface helveticaNeueLTProLtCn;

    public void setHelveticaNeueLTProLtCn(TextView textView) {
        if (helveticaNeueLTProLtCn == null)
            helveticaNeueLTProLtCn = Typeface.createFromAsset(textView.getContext().getAssets(), "fonts/HelveticaNeueLTPro-LtCn.otf");
        textView.setTypeface(helveticaNeueLTProLtCn);
    }


}
