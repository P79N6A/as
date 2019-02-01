package com.yaoguang.lib.appcommon.utils;

import android.text.InputType;
import android.text.method.DigitsKeyListener;
import android.text.method.ReplacementTransformationMethod;
import android.widget.TextView;

/**
 * 输入法
 * Created by zhongjh on 2017/7/25.
 */
public class TextViewUtils {

    /**
     * 字母数字
     *
     * @param textView 编辑文本框
     */
    public static void setAlphaNumeric(TextView textView) {
//        textView.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);
//        String digits = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
//        textView.setKeyListener(DigitsKeyListener.getInstance(digits));


        textView.setKeyListener(new DigitsKeyListener() {
            @Override
            public int getInputType() {
                return InputType.TYPE_TEXT_VARIATION_PASSWORD;
            }
            @Override
            protected char[] getAcceptedChars() {
                char[] data = "@0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();
                return data;
            }
        });

    }

    /**
     * 数字和- 用于电话的
     *
     * @param textView 编辑文本框
     */
    public static void setPhone(TextView textView) {
        textView.setKeyListener(new DigitsKeyListener() {
            @Override
            public int getInputType() {
                return InputType.TYPE_TEXT_VARIATION_PASSWORD;
            }
            @Override
            protected char[] getAcceptedChars() {
                char[] data = "0123456789-".toCharArray();
                return data;
            }
        });

    }

    /**
     * 自动小写转大写
     */
    public static ReplacementTransformationMethod replacementTransformationMethod = new ReplacementTransformationMethod() {
        @Override
        protected char[] getOriginal() {
            return new char[]{'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'};
        }

        @Override
        protected char[] getReplacement() {
            return new char[]{'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'};
        }
    };


}
