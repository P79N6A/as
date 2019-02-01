package com.yaoguang.lib.common.android;

import android.text.method.ReplacementTransformationMethod;

/**
 * 用于身份证，驾驶证等信息，小写x转大写X
 * Created by zhongjh on 2018/7/10.
 */
public class A2bigA extends ReplacementTransformationMethod {


    @Override
    protected char[] getOriginal() {
        return new char[]{'1', '2', '3', '4', '5', '6', '7', '8', '9', '0', 'x', 'X'};
    }

    @Override
    protected char[] getReplacement() {
        return new char[]{'1', '2', '3', '4', '5', '6', '7', '8', '9', '0', 'X', 'X'};
    }
}
