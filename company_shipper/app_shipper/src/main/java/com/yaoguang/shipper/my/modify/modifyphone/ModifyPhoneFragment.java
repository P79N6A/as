package com.yaoguang.shipper.my.modify.modifyphone;

import android.widget.Toast;

import com.yaoguang.appcommon.phone.my.modifyphone.BaseModifyPhoneFragment;
import com.yaoguang.lib.base.BaseApplication;
import com.yaoguang.lib.common.constants.Constants;

/**
 * 修改手机第一步
 * Created by zhongjh on 2017/8/18.
 */
public class ModifyPhoneFragment extends BaseModifyPhoneFragment {

    public static ModifyPhoneFragment newInstance() {
        return new ModifyPhoneFragment();
    }

    @Override
    public String getCodeType() {
        return Constants.APP_SHIPPER;
    }

    @Override
    public void onViewCreated() {
        mViewHolder.toolbar_title.setText("更换手机号码");
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
