package com.yaoguang.driver.phone.my.setting.usinghelp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.yaoguang.lib.base.BaseFragment2;
import com.yaoguang.lib.base.interfaces.BasePresenter;
import com.yaoguang.driver.R;

/**
 * =====================================
 * 作    者: 韦理英
 * 版    本：1.3.0
 * 创建日期：2018/03/07
 * 描    述：
 * 使用帮助
 * <p>
 * update zhongjh
 * data 2018-03-24
 * <p>
 * =====================================
 */

public class UsingHelpFragment extends BaseFragment2 {

    public static UsingHelpFragment newInstance() {

        Bundle args = new Bundle();

        UsingHelpFragment fragment = new UsingHelpFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_using_help;
    }

    @Override
    public void initDataBind(View view, LayoutInflater inflater) {
        view.findViewById(R.id.imgReturn).setOnClickListener(v -> pop());
        ((TextView) view.findViewById(R.id.toolbar_title)).setText("使用帮助");
    }

    @Override
    public void init() {

    }

    @Override
    public void initListener() {

    }

    @Override
    public BasePresenter getPresenter() {
        return null;
    }

}
