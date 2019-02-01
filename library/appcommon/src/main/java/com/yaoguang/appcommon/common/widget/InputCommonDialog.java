package com.yaoguang.appcommon.common.widget;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.yaoguang.appcommon.R;
import com.yaoguang.lib.base.BaseApplication;
import com.yaoguang.lib.common.EmojiFilterUtils;
import com.yaoguang.lib.common.android.InputMethodUtil;
import com.yaoguang.lib.common.ObjectUtils;
import com.yaoguang.lib.common.debounceclick.NoDoubleClickListener;


/**
 * 输入通用对话框，比如：拒接，关联 等等
 * Created by 韦理英 on 2017/11/2 0002.
 */

public class InputCommonDialog extends AlertDialog {

    private final Context mContext;
    private TextView tvTitle;
    private EditText etValue;
    private View line;
    private TextView tvNumber;
    private TextView tvNumberSum;
    private LinearLayout llNumber;
    private TextView btnRefuse;
    private TextView btnAccept;
    private String toastAlert;
    /**
     * 允许最大字数
     */
    private int mMaxLength = 400;
    /**
     * 对话框回调
     */
    ComeBack mComeBack;

    @Override
    protected void onStart() {
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        super.onStart();
    }

    public InputCommonDialog(@NonNull Context context) {
        super(context);
//        super(context, R.style.AlertDialogCustom);
        View view = View.inflate(context, R.layout.view_edittext_style2, null);

        mContext = context;
        initView(view);
        initListener(view);
        setView(view);
    }

    private void initView(View view) {
        // 输入hint
        etValue = view.findViewById(R.id.etValue);
        tvTitle = view.findViewById(R.id.tvTitle);
        tvNumber = view.findViewById(R.id.tvNumber);
        tvNumberSum = view.findViewById(R.id.tvNumberSum);

        tvNumber.setText(ObjectUtils.parseString(etValue.getText().toString().length()));
        etValue.setFilters(new InputFilter[]{new EmojiFilterUtils()});
    }

    private void initListener(View view) {
        // 接受
        view.findViewById(R.id.btnAccept).setOnClickListener(new NoDoubleClickListener() {
            @Override
            public void onNoDoubleClick(View v) {
                String value = etValue.getText().toString();
                if (TextUtils.isEmpty(value) && toastAlert != null) {
                    Toast.makeText(BaseApplication.getInstance(), toastAlert, Toast.LENGTH_SHORT).show();
                    return;
                }

                if (mComeBack != null) {
                    mComeBack.ok(value);
                }

                InputMethodUtil.hideKeyboard((Activity) mContext);
                dismiss();
            }
        });

        // 取消
        view.findViewById(R.id.btnRefuse).setOnClickListener(new NoDoubleClickListener() {
            @Override
            public void onNoDoubleClick(View v) {
                dismiss();
            }
        });

        //记录字数
        etValue.addTextChangedListener(new TextWatcher() {
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

    public void setTitle(@NonNull String title) {
        if (!TextUtils.isEmpty(title)) {
            tvTitle.setText(title);
        }
    }

    /**
     * 描    述：最大输入
     * 作    者：韦理英
     * 时    间：
     */

    public void setMaxLength(int max) {
        mMaxLength = max;
        //最大长度
        tvNumberSum.setText(ObjectUtils.parseString(mMaxLength));
        //过滤表情
        etValue.setFilters(new InputFilter[]{new InputFilter.LengthFilter(mMaxLength)});
    }

    /**
     * 描    述：输入hit提示
     * 作    者：韦理英
     * 时    间：
     */

    public void setHit(@NonNull String hit) {
        if (!TextUtils.isEmpty(hit)) {
            etValue.setHint(hit);
        }
    }

    /**
     * 设置toast提示
     *
     * @param toast 提示
     */
    public void setToast(@NonNull String toast) {
        toastAlert = toast;
    }

    public void setComeBack(ComeBack comeBack) {
        this.mComeBack = comeBack;
    }

    public interface ComeBack {
        void ok(String value);


    }
}
