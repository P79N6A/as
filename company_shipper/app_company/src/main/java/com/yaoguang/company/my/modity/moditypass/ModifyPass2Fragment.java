package com.yaoguang.company.my.modity.moditypass;

import android.os.Bundle;

import com.yaoguang.appcommon.phone.my.modifypass.BaseModifyPass2Fragment;
import com.yaoguang.lib.common.constants.Constants;
import com.yaoguang.company.activitys.login.LoginActivity;

import me.yokeyword.fragmentation.ISupportFragment;

/**
 * 修改密码第二步
 * Created by zhongjh on 2017/8/18.
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

    /**
     * 在登录时，如果发现密码是默认的（目前只有物流用到），那么需要强制修改密码
     *
     * @param id          用户id
     * @param mobile      帐号
     * @param oldpassWord 旧密码
     */
    public static ISupportFragment newInstanceType2(String id, String mobile, String oldpassWord) {
        ModifyPass2Fragment modifyPass2Fragment = new ModifyPass2Fragment();
        Bundle bundle = new Bundle();
        bundle.putString(BUNDLE_KEY_ID, id);
        bundle.putString(BUNDLE_KEY_MOBILE, mobile);
        bundle.putString(BUNDLE_KEY_PASSWORD, oldpassWord);
        bundle.putInt(BUNDLE_KEY_TYPE, 2);
        modifyPass2Fragment.setArguments(bundle);
        return modifyPass2Fragment;
    }

    @Override
    protected void toLoginActivity() {
        LoginActivity.newInstance(getActivity(), true, null, null);
        _mActivity.finish();
    }

    @Override
    public String getCodeType() {
        return Constants.APP_COMPANY;
    }


    @Override
    public void submitProgressSuccess() {

    }


}
