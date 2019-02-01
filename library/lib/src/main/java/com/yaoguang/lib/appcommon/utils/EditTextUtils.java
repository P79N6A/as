package com.yaoguang.lib.appcommon.utils;

import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.text.method.ReplacementTransformationMethod;
import android.widget.EditText;
import android.widget.TextView;

import com.yaoguang.lib.common.EmojiFilterUtils;
import com.yaoguang.lib.common.ObjectUtils;

/**
 * Created by 韦理英
 * on 2017/6/7 0007.
 */

public class EditTextUtils {
    /**
     * 自动小写转大小
     * eg:
     * etXxx.setTransformationMethod(replacementTransformationMethod);
     */
    public static ReplacementTransformationMethod replacementTransformationMethod = new ReplacementTransformationMethod() {
        @Override
        protected char[] getOriginal() {
            char[] aa = {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'};
            return aa;
        }

        @Override
        protected char[] getReplacement() {
            char[] cc = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'};
            return cc;
        }
    };

    public static void setRemark(EditText etRemark, final TextView tvNumber, int maxLength) {
        //过滤表情
        etRemark.setFilters(new InputFilter[]{new EmojiFilterUtils(), new InputFilter.LengthFilter(maxLength)});
        //记录字数
        etRemark.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                tvNumber.setText(ObjectUtils.parseString(s.length()));
            }
        });
    }
}
