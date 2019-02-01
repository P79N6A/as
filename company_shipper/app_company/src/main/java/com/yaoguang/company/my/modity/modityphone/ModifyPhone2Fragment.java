package com.yaoguang.company.my.modity.modityphone;

import android.os.Bundle;

import com.yaoguang.appcommon.phone.my.modifyphone.BaseModifyPhone2Fragment;
import com.yaoguang.lib.common.constants.Constants;
import com.yaoguang.company.activitys.login.LoginActivity;

/**
 * 修改手机第二步
 * Created by zhongjh on 2017/9/18.
 */
public class ModifyPhone2Fragment extends BaseModifyPhone2Fragment {

    public static ModifyPhone2Fragment newInstance(String password) {
        ModifyPhone2Fragment modifyPhone2Fragment = new ModifyPhone2Fragment();
        Bundle bundle = new Bundle();
        bundle.putString("password", password);
        modifyPhone2Fragment.setArguments(bundle);
        return modifyPhone2Fragment;
    }

    @Override
    public String getCodeType() {
        return Constants.APP_COMPANY;
    }

    @Override
    protected void toLoginActivity() {
        LoginActivity.newInstance(getActivity(), true, null, null);
        getActivity().finish();
    }


}
