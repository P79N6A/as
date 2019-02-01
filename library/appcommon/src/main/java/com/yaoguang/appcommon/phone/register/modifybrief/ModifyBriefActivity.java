package com.yaoguang.appcommon.phone.register.modifybrief;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.yaoguang.appcommon.R;
import com.yaoguang.lib.base.BaseActivity;
import com.yaoguang.lib.base.interfaces.BasePresenter;

/**
 * 商户简介
 * Created by zhongjh on 2017/10/23.
 */
public class ModifyBriefActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_container);

        if (savedInstanceState == null) {
            ModifyBriefFragment modifyBriefFragment = new ModifyBriefFragment();
            loadRootFragment(R.id.fl_container, modifyBriefFragment.newInstance( getIntent().getStringExtra("value")));
        }
    }

    @Override
    public BasePresenter getPresenter() {
        return null;
    }
}