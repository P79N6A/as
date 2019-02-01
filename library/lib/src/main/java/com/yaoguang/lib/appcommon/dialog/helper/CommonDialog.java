package com.yaoguang.lib.appcommon.dialog.helper;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yaoguang.lib.R;
import com.yaoguang.lib.appcommon.utils.LinearLayoutUtils;
import com.yaoguang.lib.common.ConvertUtils;
import com.yaoguang.lib.common.ObjectUtils;
import com.yaoguang.lib.common.android.InputMethodUtil;
import com.yaoguang.lib.common.debounceclick.NoDoubleClickListener;


/**
 * 这是一个公共的弹窗类
 * Created by zhongjh on 2017/9/14.
 */
public class CommonDialog extends Dialog {


    ViewHolder mViewHolder;
    Context mContext;
    String mStrContent;
    String mStrHint;
    String mStrTitle;
    String mStrClose;
    String mStrOk;

    Listener mListener;

    boolean mIsSingle;// 是否只有一个按钮

    boolean mIsEditText = false;// 是否编辑文本框，默认为否
    int mMaxLength = -1;// 编辑文本框的长度

    public CommonDialog(Context context, String content) {
        super(context, R.style.Dialog);
        mContext = context;
        mStrContent = content;
    }

    public CommonDialog(Context context, String content, String ok, String close) {
        this(context, null, content, null, ok, close, null, false, false, -1);
    }

    public CommonDialog(Context context, String title, String content, String ok, String close, Listener listener, boolean isSingle) {
        this(context, title, content, null, ok, close, listener, isSingle, false, -1);
    }

    public CommonDialog(Context context, String title, String content, String hint, String ok, String close, Listener listener, boolean isSingle, boolean isEditText, int maxLength) {
        super(context, R.style.Dialog);
        mContext = context;
        mStrTitle = title;
        mStrContent = content;
        mStrHint = hint;
        mStrClose = close;
        mStrOk = ok;
        mListener = listener;
        mIsSingle = isSingle;
        mIsEditText = isEditText;
        mMaxLength = maxLength;
    }


    public interface Listener {

        void ok(String content);

        void cancel();


    }

    public void setListener(Listener mListener) {
        this.mListener = mListener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LayoutInflater inflater = (LayoutInflater) mContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View layout = inflater.inflate(R.layout.dialog_common, null);
        mViewHolder = new ViewHolder(layout);
        mViewHolder.tvContent.setText(mStrContent);
        mViewHolder.etContent.setText(mStrContent);
        if (mStrHint != null)
            mViewHolder.etContent.setHint(mStrHint);
        mViewHolder.tvClose.setOnClickListener(new NoDoubleClickListener() {
            @Override
            public void onNoDoubleClick(View v) {
                if (mListener != null) {
                    mListener.cancel();
                }
                if (mIsEditText)
                    InputMethodUtil.hideKeyboard(getContext(), mViewHolder.etContent);
                // 关闭自身
                CommonDialog.this.dismiss();
            }
        });
        mViewHolder.tvOK.setOnClickListener(new NoDoubleClickListener() {
            @Override
            public void onNoDoubleClick(View v) {
                if (mListener != null) {
                    // 如果是编辑文本框，就传值
                    if (mIsEditText) {
                        mListener.ok(mViewHolder.etContent.getText().toString());
                    } else {
                        mListener.ok(mViewHolder.tvContent.getText().toString());
                    }
                }
                if (mIsEditText)
                    InputMethodUtil.hideKeyboard(getContext(), mViewHolder.etContent);
                // 关闭自身
                CommonDialog.this.dismiss();
            }
        });
        //  设置确定标题
        if (!TextUtils.isEmpty(mStrOk)) {
            mViewHolder.tvOK.setText(mStrOk);
        } else {
            mViewHolder.tvOK.setText("是的");
        }
        //  设置取消标题
        if (!TextUtils.isEmpty(mStrClose)) {
            mViewHolder.tvClose.setText(mStrClose);
        } else {
            mViewHolder.tvClose.setText("我再想想");
        }

        // 是否单个按钮
        if (mIsSingle)
            mViewHolder.tvClose.setVisibility(View.GONE);

        // 是否编辑文本框
        if (mIsEditText) {
            mViewHolder.etContent.setVisibility(View.VISIBLE);
            mViewHolder.llNumber.setVisibility(View.INVISIBLE);
            mViewHolder.etContent.setFilters(new InputFilter[]{new InputFilter.LengthFilter(mMaxLength)});
            mViewHolder.tvContent.setVisibility(View.GONE);

            // 如果是编辑文本框并且有显示最大字数的，就显示最大字数提示文本
            if (mMaxLength != -1) {
                mViewHolder.llNumber.setVisibility(View.VISIBLE);
                mViewHolder.tvNumber.setText(ObjectUtils.parseString(mViewHolder.etContent.getText().length()));
                mViewHolder.tvNumberSum.setText(ObjectUtils.parseString(mMaxLength));
                // 添加事件
                mViewHolder.etContent.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        mViewHolder.tvNumber.setText(ObjectUtils.parseString(s.length()));
                    }

                    @Override
                    public void afterTextChanged(Editable s) {

                    }
                });
            }

        } else {
            mViewHolder.etContent.setVisibility(View.GONE);
            mViewHolder.llNumber.setVisibility(View.GONE);
            mViewHolder.tvContent.setVisibility(View.VISIBLE);
        }

        //  设置标题
        if (!TextUtils.isEmpty(mStrTitle)) {
            mViewHolder.tvTitle.setText(mStrTitle);
            mViewHolder.tvTitle.setVisibility(View.VISIBLE);
        } else {
            // 设置间距
            LinearLayout.LayoutParams layoutParams = LinearLayoutUtils.geWidthMatchView();
            layoutParams.setMargins(ConvertUtils.dp2px(20), ConvertUtils.dp2px(42), ConvertUtils.dp2px(20), ConvertUtils.dp2px(42));
            mViewHolder.tvContent.setLayoutParams(layoutParams);
            mViewHolder.etContent.setLayoutParams(layoutParams);
            mViewHolder.tvTitle.setText("");
            mViewHolder.tvTitle.setVisibility(View.GONE);
        }
        this.setContentView(layout);
    }

    public static class ViewHolder {
        public View rootView;
        public TextView tvTitle;
        public TextView tvContent;
        public EditText etContent;
        public TextView tvClose;
        public TextView tvOK;
        public CardView log_in_layout;
        public LinearLayout llNumber;
        public TextView tvNumber;
        public TextView tvNumberSum;

        public ViewHolder(View rootView) {
            this.rootView = rootView;
            this.tvTitle = rootView.findViewById(R.id.tvTitle);
            this.tvContent = rootView.findViewById(R.id.tvContent);
            this.etContent = rootView.findViewById(R.id.etContent);
            this.tvClose = rootView.findViewById(R.id.tvClose);
            this.tvOK = rootView.findViewById(R.id.tvOK);
            this.log_in_layout = rootView.findViewById(R.id.log_in_layout);
            this.llNumber = rootView.findViewById(R.id.llNumber);
            this.tvNumber = rootView.findViewById(R.id.tvNumber);
            this.tvNumberSum = rootView.findViewById(R.id.tvNumberSum);
        }

    }
}
