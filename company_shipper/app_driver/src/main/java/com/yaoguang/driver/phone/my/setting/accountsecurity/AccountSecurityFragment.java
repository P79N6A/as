package com.yaoguang.driver.phone.my.setting.accountsecurity;

import android.os.Bundle;

import com.yaoguang.appcommon.phone.my.setting.accountsecurity.BaseAccountSecurityFragment;
import com.yaoguang.driver.phone.my.bindphone.BindNewPhoneFragment;
import com.yaoguang.driver.phone.my.modify.password.ModifyPassFragment;

/**
 * 帐号安全
 * Created by zhongjh on 2018/3/12.
 */
public class AccountSecurityFragment extends BaseAccountSecurityFragment {

    public static BaseAccountSecurityFragment newInstance() {

        Bundle args = new Bundle();

        AccountSecurityFragment fragment = new AccountSecurityFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected void openBindNewPhoneFragment() {
        start(BindNewPhoneFragment.newInstance());
    }

    @Override
    protected void openModifyPassFragment() {
        start(ModifyPassFragment.newInstance());
    }


}
