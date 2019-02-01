package com.yaoguang.driver.phone.my.bindphone;

import android.os.Bundle;
import android.widget.Toast;

import com.yaoguang.appcommon.phone.my.modifyphone.BaseModifyPhoneFragment;
import com.yaoguang.lib.base.BaseApplication;
import com.yaoguang.lib.common.constants.Constants;


/**
 * 文件名：绑定手机号
 * 描    述：
 * 作    者：韦理英
 * 时    间：2017/8/17 0017.
 * 版    权：
 */
public class BindNewPhoneFragment extends BaseModifyPhoneFragment {

    public static BindNewPhoneFragment newInstance() {
        Bundle args = new Bundle();
        BindNewPhoneFragment fragment = new BindNewPhoneFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public String getCodeType() {
        return Constants.APP_DRIVER;
    }

    @Override
    public void onViewCreated() {
        mViewHolder.toolbar_title.setText("安全验证");
    }

    @Override
    public void success() {
        startWithPop(ModifyPhone2Fragment.newInstance(mViewHolder.petPassWord.getText().toString()));
    }

    @Override
    public void fail(String message) {
        Toast.makeText(BaseApplication.getInstance(), message, Toast.LENGTH_SHORT).show();
    }
}
