package com.yaoguang.driver.widget;

import android.graphics.Color;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.mingle.sweetpick.Effect;

/**
 * @author zzz40500
 * @version 1.0
 * @date 2015/8/9.
 * @github: https://github.com/zzz40500
 *
 */
public class BlackEffect implements Effect {

    private float Value;

    public BlackEffect(float value) {
        Value = value;
    }

    public void setValue(float value) {
        Value = value;
    }

    @Override
    public float getValue() {
        return Value;
    }

    @Override
    public void effect(ViewGroup vp, ImageView view) {
        view.setBackgroundColor(Color.argb((int) (150 * Value), 0, 0, 0));
    }
}
