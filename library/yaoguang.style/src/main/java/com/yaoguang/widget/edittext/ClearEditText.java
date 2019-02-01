package com.yaoguang.widget.edittext;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;

import com.yaoguang.style.R;

/**
 * 输入文本框 右边有自带的删除按钮 当有输入时，显示删除按钮，当无输入时，不显示删除按钮。
 * Created by zhongjh on 2017/8/22.
 */
public class ClearEditText extends EditText implements View.OnFocusChangeListener, TextWatcher {

    public ClearEditText(Context context) {
        this(context, null);
        init(context, null, 0, 0);
    }

    public ClearEditText(Context context, AttributeSet attrs) {
        this(context, attrs, android.R.attr.editTextStyle);
        init(context, attrs, 0, 0);
    }

    public ClearEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr, 0);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public ClearEditText(Context context, AttributeSet attrs, int defStyleAttr, int
            defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context, attrs, defStyleAttr, defStyleRes);
    }

    private Drawable mClearDrawable;
    /**
     * 记录Right Drawable 是否可见
     */
    private boolean mIsClearVisible;

    /**
     * 是否启用Clear
     */
    private boolean isEnableClear = true;

    public void setEnableClear(boolean enableClear) {
        isEnableClear = enableClear;
        setCompoundDrawables(getCompoundDrawables()[0], getCompoundDrawables()[1],
                isEnableClear ? mClearDrawable : null, getCompoundDrawables()[3]);
    }

    private void init(Context context, AttributeSet attrs, int defStyleAttr, int
            defStyleRes) {

        // 获取EditText的DrawableRight,假如没有设置我们就使用默认的图片
        mClearDrawable = getCompoundDrawables()[2];
        if (mClearDrawable == null) {
            mClearDrawable = getResources().getDrawable(R.drawable.ic_qk);
        }
        mClearDrawable.setBounds(0, 0, mClearDrawable.getIntrinsicWidth(), mClearDrawable.getIntrinsicHeight());

        // 默认设置隐藏图标
        setClearDrawableVisible(false);
        // 设置焦点改变的监听
        setOnFocusChangeListener(this);
        // 设置输入框里面内容发生改变的监听
        addTextChangedListener(this);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        // error drawable 不显示 && clear drawable 显示 && action up
        if (getError() == null && mIsClearVisible && event.getAction() == MotionEvent.ACTION_UP) {

            float x = event.getX();
            if (x >= getWidth() - getTotalPaddingRight() && x <= getWidth() - getPaddingRight()) {
                clearText();

            }
        }

        return super.onTouchEvent(event);
    }


    /**
     * 清空输入框
     */
    private void clearText() {
        if (getText().length() > 0) {
            setText("");
        }
    }


    /**
     * 设置Right Drawable是否可见
     *
     * @param isVisible true for visible , false for invisible
     */
    private void setClearDrawableVisible(boolean isVisible) {

        if (isEnableClear) {

            setCompoundDrawables(getCompoundDrawables()[0], getCompoundDrawables()[1],
                    isVisible ? mClearDrawable : null, getCompoundDrawables()[3]);

            mIsClearVisible = isVisible;
        }
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        // error drawable 不显示的时候
        if (getError() == null) {
            if (hasFocus) {
                if (getText().length() > 0) {
                    setClearDrawableVisible(true);
                }
            } else {
                setClearDrawableVisible(false);
            }
        }
    }

    @Override
    public void setError(CharSequence error, Drawable icon) {
        if (error != null) {
            setClearDrawableVisible(true);
        }
        super.setError(error, icon);
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void afterTextChanged(Editable s) {

    }

    @Override
    public void onTextChanged(CharSequence text, int start, int lengthBefore, int lengthAfter) {
        super.onTextChanged(text, start, lengthBefore, lengthAfter);
        setClearDrawableVisible(text.length() > 0);
    }
}
