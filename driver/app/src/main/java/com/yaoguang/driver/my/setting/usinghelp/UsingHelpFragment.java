package com.yaoguang.driver.my.setting.usinghelp;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yaoguang.appcommon.common.base.BaseFragmentV2;
import com.yaoguang.common.base.interfaces.BasePresenter;
import com.yaoguang.driver.R;

/**
 * =====================================
 * 作    者: 韦理英
 * 版    本：1.3.0
 * 创建日期：2018/03/07
 * 描    述：
 * 使用帮助
 * =====================================
 */

public class UsingHelpFragment extends BaseFragmentV2 {

    public static UsingHelpFragment newInstance() {

        Bundle args = new Bundle();

        UsingHelpFragment fragment = new UsingHelpFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_using_help, null);

        view.findViewById(R.id.imgReturn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pop();
            }
        });
        ((TextView) view.findViewById(R.id.toolbar_title)).setText("使用帮助");
        return view;
    }

    @Override
    public BasePresenter getPresenter() {
        return null;
    }
}
