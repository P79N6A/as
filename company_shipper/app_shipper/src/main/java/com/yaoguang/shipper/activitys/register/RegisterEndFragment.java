package com.yaoguang.shipper.activitys.register;

import android.os.Bundle;

import com.yaoguang.appcommon.phone.register.BaseRegisterEndFragment;
import com.yaoguang.greendao.entity.UserOwner;

/**
 * Created by zhongjh on 2017/7/4.
 */

public class RegisterEndFragment extends BaseRegisterEndFragment<UserOwner> {

    public static RegisterEndFragment newInstance() {
        return new RegisterEndFragment();
    }

    @Override
    protected void toLoginActivity() {
        getActivity().finish();
    }

    @Override
    public void startShowCountdown() {

    }

    @Override
    public void customSetting() {
        getRegisterActivity().setRegisterNode(2);
    }

    @Override
    public void nextPage() {

    }

    @Override
    public UserOwner getModel() {
        return getRegisterActivity().getModel();
    }

    @Override
    public void stopShowCountdown() {

    }

    private RegisterActivity getRegisterActivity() {
        return (RegisterActivity) getActivity();
    }

    @Override
    public void setSubmitProgressInit() {

    }

    @Override
    public void setSubmitProgressOK() {

    }

    @Override
    public void setSubmitProgressSuccess(Bundle bundle) {

    }

    @Override
    public void setSubmitProgressLoading() {

    }

    @Override
    public void setSubmitProgressError() {

    }
}
