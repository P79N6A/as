package com.yaoguang.driver.phone.my.modify.password;

import android.os.Bundle;

import com.yaoguang.appcommon.phone.my.modifypass.BaseModifyPass2Fragment;

import com.yaoguang.lib.common.constants.Constants;
import com.yaoguang.driver.phone.activitys.login.LoginActivity;

/**
 * 修改密码第二步
 * Created by wly on 2017/8/18.
 */
public class ModifyPass2Fragment extends BaseModifyPass2Fragment {

    /**
     * 在登录页面修改密码，提示是否确定更换密码
     *
     * @param mobile 手机号
     */
    public static ModifyPass2Fragment newInstanceType1(String mobile, String autoCode) {
        ModifyPass2Fragment modifyPass2Fragment = new ModifyPass2Fragment();
        Bundle bundle = new Bundle();
        bundle.putString(BUNDLE_KEY_MOBILE, mobile);
        bundle.putString(BUNDLE_KEY_AUTOCODE, autoCode);
        bundle.putInt(BUNDLE_KEY_TYPE, 1);
        modifyPass2Fragment.setArguments(bundle);
        return modifyPass2Fragment;
    }

    /**
     * 在进入首页后，直接修改密码，并且提示确定更换密码并退出账户?
     *
     * @param password 密码
     */
    public static ModifyPass2Fragment newInstanceType0(String password) {
        ModifyPass2Fragment modifyPass2Fragment = new ModifyPass2Fragment();
        Bundle bundle = new Bundle();
        bundle.putString(BUNDLE_KEY_PASSWORD, password);
        bundle.putInt(BUNDLE_KEY_TYPE, 0);
        modifyPass2Fragment.setArguments(bundle);
        return modifyPass2Fragment;
    }

    @Override
    protected void toLoginActivity() {
        LoginActivity.newInstance(getActivity(), true, null, null);
        getActivity().finish();
    }

    @Override
    public String getCodeType() {
        return Constants.APP_DRIVER;
    }


    @Override
    public void submitProgressSuccess() {

    }
}
