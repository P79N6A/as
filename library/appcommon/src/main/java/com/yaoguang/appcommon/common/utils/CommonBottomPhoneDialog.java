package com.yaoguang.appcommon.common.utils;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.TextView;

import com.yaoguang.appcommon.R;
import com.yaoguang.appcommon.common.base.BaseDialogFragment;
import com.yaoguang.lib.common.IntentUtils;


/**
 * 通用电话，时间较忙，只做两个电话的显示
 * Created by 韦理英 on 2017/9/14.
 */
public class CommonBottomPhoneDialog extends BaseDialogFragment {

    public static String FLAG_ARRAY = "flag_array";
    private static String FLAG_TITLE = "flag_title";
    private String[] mArray;
    private String mTitle;
    private TextView tvTitle;
    private View line;
    private TextView tvPhone1;
    private TextView tvPhone2;
    private TextView tvSubmit;
    private View tvPhone2_line;

    public static CommonBottomPhoneDialog newInstance(String[] array, String title) {
        Bundle args = new Bundle();
        args.putStringArray(FLAG_ARRAY, array);
        args.putString(FLAG_TITLE, title);
        CommonBottomPhoneDialog fragment = new CommonBottomPhoneDialog();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.view_bottom_phone, null);

        backgroundColor = Color.TRANSPARENT;
        height = WindowManager.LayoutParams.WRAP_CONTENT;
        if (getArguments() != null) {
            mArray = getArguments().getStringArray(FLAG_ARRAY);
            mTitle = getArguments().getString(FLAG_TITLE);
        }
        initView(view);
        initListener();
        return view;
    }

    private void initListener() {
        tvPhone1.setOnClickListener(v -> {
            if (getActivity() != null)
                getActivity().startActivity(IntentUtils.getDialIntent(tvPhone1.getText().toString()));
            dismiss();
        });
        tvPhone2.setOnClickListener(v -> {
            if (getActivity() != null)
                getActivity().startActivity(IntentUtils.getDialIntent(tvPhone2.getText().toString()));
            dismiss();
        });
        tvSubmit.setOnClickListener(v -> dismiss());
    }

    private void initView(View view) {
        tvTitle = (TextView) view.findViewById(R.id.tvTitle);
        line = (View) view.findViewById(R.id.line);
        tvPhone2_line = (View) view.findViewById(R.id.tvPhone2_line);
        tvPhone1 = (TextView) view.findViewById(R.id.tvPhone1);
        tvPhone2 = (TextView) view.findViewById(R.id.tvPhone2);
        tvSubmit = (TextView) view.findViewById(R.id.tvSubmit);

        // 现在开发只有两个电话
        if (mArray != null && mArray.length > 0) {
            for (int i = 0; i < mArray.length; i++) {
                if (i == 0) {
                    tvPhone1.setText(mArray[i]);
                    tvPhone1.setVisibility(View.VISIBLE);
                    tvPhone2.setVisibility(View.GONE);
                    tvPhone2_line.setVisibility(View.GONE);
                } else {
                    tvPhone2.setText(mArray[i]);
                    tvPhone2.setVisibility(View.VISIBLE);
                    tvPhone2_line.setVisibility(View.VISIBLE);
                }
            }
        }
    }
}
