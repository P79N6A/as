package com.yaoguang.company.my.modity.moditypass;

import android.widget.Toast;

import com.yaoguang.appcommon.phone.my.modifyphone.BaseModifyPhoneFragment;
import com.yaoguang.lib.base.BaseApplication;
import com.yaoguang.lib.common.constants.Constants;

/**
 * 修改密码第一步
 * Created by zhongjh on 2017/8/18.
 */
public class ModifyPassFragment extends BaseModifyPhoneFragment {

    public static ModifyPassFragment newInstance() {
        return new ModifyPassFragment();
    }

    @Override
    public String getCodeType() {
        return Constants.APP_COMPANY;
    }

    @Override
    public void onViewCreated() {
        mViewHolder.toolbar_title.setText("修改密码");
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
