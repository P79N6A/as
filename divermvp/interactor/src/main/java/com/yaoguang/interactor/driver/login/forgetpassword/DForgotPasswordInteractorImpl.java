package com.yaoguang.interactor.driver.login.forgetpassword;

import com.yaoguang.lib.common.RegExpValidatorUtils;
import com.yaoguang.lib.net.bean.BaseResponse;
import com.yaoguang.interactor.common.DCSBaseInteractorImpl;
import com.yaoguang.interfaces.driver.interactor.DForgotPasswordInteractor;
import com.yaoguang.lib.net.Api;
import com.yaoguang.datasource.api.DriverApi;
import com.yaoguang.datasource.api.common.SMSApi;

import io.reactivex.Observable;

/**
 * 忘记密码业务类
 * Created by zhongjh on 2017/3/31.
 */
public class DForgotPasswordInteractorImpl extends DCSBaseInteractorImpl implements DForgotPasswordInteractor {

    @Override
    public Observable<BaseResponse<String>> getVerficationCode(final String strMobile) {

        return Api.getInstance().retrofit.create(SMSApi.class).getCode(strMobile, "2");
    }

    @Override
    public boolean checkValidatorMobile(String strMobile) {
        return RegExpValidatorUtils.IsHandset(strMobile);
    }

    @Override
    public Observable<BaseResponse<String>> handleForgotPassword(final String strMobile, final String strValidatorCode, final String strPassWord1, String strPassWord2) {
        return Api.getInstance().retrofit.create(DriverApi.class).updateMobile(strMobile, getDriver().getId(), strValidatorCode, strPassWord1);
    }
}
