package com.yaoguang.appcommon.phone.my.setting.accountsecurity;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import com.yaoguang.appcommon.R;
import com.yaoguang.appcommon.databinding.FragmentAccountSecurityBinding;
import com.yaoguang.datasource.common.DataStatic;
import com.yaoguang.lib.base.BaseApplication;
import com.yaoguang.lib.base.BaseFragment2;
import com.yaoguang.lib.base.interfaces.BasePresenter;
import com.yaoguang.lib.common.constants.Constants;
import com.yaoguang.lib.databinding.ToolbarCommonBinding;

/**
 * =====================================
 * 作    者: 韦理英
 * 版    本：1.2.9
 * 创建日期：2018/02/23
 * 描    述：
 * 帐户安全
 * <p>
 * Update:zhongjh
 * Data:2018/03/12
 * <p>
 * =====================================
 */
public abstract class BaseAccountSecurityFragment extends BaseFragment2 {

    public FragmentAccountSecurityBinding mDataBinding;
    public ToolbarCommonBinding mToolbarCommonBinding;

    // 打开修改手机号码
    protected abstract void openBindNewPhoneFragment();

    // 打开修改密码
    protected abstract void openModifyPassFragment();

    @Override
    public void onEnterAnimationEnd(Bundle savedInstanceState) {
        super.onEnterAnimationEnd(savedInstanceState);
        // 判断app类型
        switch (BaseApplication.getAppType()) {
            case Constants.APP_DRIVER:
                mDataBinding.tvChangeThePhoneNumber.setText(DataStatic.getInstance().getDriver().getMobile());
                break;
            case Constants.APP_COMPANY:
                mDataBinding.tvChangeThePhoneNumber.setText(DataStatic.getInstance().getAppUserWrapper().getMobile());
                break;
            case Constants.APP_SHIPPER:
                mDataBinding.tvChangeThePhoneNumber.setText(DataStatic.getInstance().getUserOwner().getMobile());
                break;
        }
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_account_security;
    }

    @Override
    public void initDataBind(View view, LayoutInflater inflater) {
        mDataBinding = DataBindingUtil.bind(view);
        if (mDataBinding.getRoot().findViewById(R.id.toolbar) != null) {
            mToolbarCommonBinding = DataBindingUtil.bind(mDataBinding.getRoot().findViewById(R.id.toolbarCommon));
        }
    }

    @Override
    public BasePresenter getPresenter() {
        return null;
    }

    @Override
    public void init() {
        initToolbarNav(mToolbarCommonBinding.toolbar, "账户安全", -1, null);
    }

    @Override
    public void initListener() {
        mDataBinding.rlChangeThePhoneNumber.setOnClickListener(v -> openBindNewPhoneFragment());
        mDataBinding.rlModifyTheLoginPassword.setOnClickListener(v -> openModifyPassFragment());
    }


}

