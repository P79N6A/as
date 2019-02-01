package com.yaoguang.driver.phone.my.modify.password;

import android.widget.Toast;

import com.yaoguang.lib.base.BaseApplication;
import com.yaoguang.lib.common.constants.Constants;


/**
 * 文件名：
 * 描    述：
 * 作    者：韦理英
 * 时    间：2017/8/17 0017.
 * 版    权：
 */
public class ModifyPassFragment extends BaseModifyPhoneFragment {

    public static ModifyPassFragment newInstance() {
        return new ModifyPassFragment();
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
        startWithPop(ModifyPass2Fragment.newInstanceType0(mViewHolder.petPassWord.getText().toString()));
    }

    @Override
    public void fail(String message) {
        Toast.makeText(BaseApplication.getInstance(), message, Toast.LENGTH_SHORT).show();
    }


}
