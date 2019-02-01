package com.yaoguang.shipper.activitys.register;

import com.yaoguang.appcommon.phone.register.BaseRegisterDoorwayFragment;
import com.yaoguang.lib.common.constants.Constants;
import com.yaoguang.greendao.entity.UserOwner;

/**
 * 第一步的注册界面
 * Created by zhongjh on 2017/7/3.
 */
public class RegisterDoorwayFragment extends BaseRegisterDoorwayFragment<UserOwner> {

    public static RegisterDoorwayFragment newInstance() {
        return new RegisterDoorwayFragment();
    }

    @Override
    protected void setCode(String code) {
        getRegisterActivity().setCode(code);
    }

    @Override
    protected String getCodeType() {
        return Constants.APP_SHIPPER;
    }

    @Override
    protected void clickBtnRegister(InitialView initialView) {
        getModel().setPassword(initialView.etPassWord.getText().toString());
        getModel().setPhone(initialView.etMobile.getText().toString());
        setCode(initialView.actvCode.getText().toString());
        presenter.handleOneAuth(initialView.getMobile(),
                getModel().getPassword(),
                initialView.actvCode.getText().toString(),
                initialView.cbProtocol.isChecked());
    }

    @Override
    public void customSetting() {
        getRegisterActivity().setRegisterNode(0);
    }

    @Override
    public void nextPage() {
//        getRegisterActivity().nextPage();
    }


    @Override
    public UserOwner getModel() {
        return getRegisterActivity().getModel();
    }

    public RegisterActivity getRegisterActivity() {
        return (RegisterActivity) getActivity();
    }

    @Override
    public void submitProgressSuccess() {
        getRegisterActivity().nextPage();
        //重新初始化，为了让返回的时候再次点击
        setSubmitProgressInit();
    }

}
