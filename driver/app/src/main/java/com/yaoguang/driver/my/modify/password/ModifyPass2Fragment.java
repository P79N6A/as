package com.yaoguang.driver.my.modify.password;

import android.os.Bundle;

import com.yaoguang.appcommon.my.modifypass.BaseModifyPass2Fragment;

import com.yaoguang.common.common.Constants;
import com.yaoguang.driver.activitys.LoginActivity;

/**
 * 修改密码第二步
 * Created by wly on 2017/8/18.
 */
public class ModifyPass2Fragment extends BaseModifyPass2Fragment {

    public static ModifyPass2Fragment newInstanceActivity(String mobile, String code) {
        ModifyPass2Fragment modifyPass2Fragment = new ModifyPass2Fragment();
        Bundle bundle = new Bundle();
        bundle.putString("mobile", mobile);
        bundle.putString("autoCode", code);
        bundle.putBoolean("isFragment", false);
        modifyPass2Fragment.setArguments(bundle);
        return modifyPass2Fragment;
    }

    public static ModifyPass2Fragment newInstanceFragment(String password) {
        ModifyPass2Fragment modifyPass2Fragment = new ModifyPass2Fragment();
        Bundle bundle = new Bundle();
        bundle.putString("password", password);
        bundle.putBoolean("isFragment", true);
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
