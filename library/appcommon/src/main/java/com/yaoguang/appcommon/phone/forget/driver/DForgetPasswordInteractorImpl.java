package com.yaoguang.appcommon.phone.forget.driver;

import com.yaoguang.appcommon.phone.forget.ForgetPasswordInteractorImpl;
import com.yaoguang.lib.net.bean.BaseResponse;
import com.yaoguang.lib.net.Api;
import com.yaoguang.datasource.api.DriverApi;
import com.yaoguang.datasource.api.common.SMSApi;

import io.reactivex.Observable;

/**
 * Created by zhongjh on 2017/7/4.
 */
public class DForgetPasswordInteractorImpl extends ForgetPasswordInteractorImpl {

    @Override
    public Observable<BaseResponse<String>> getVerficationCode(String strMobile) {
        SMSApi smsApi = Api.getInstance().retrofit.create(SMSApi.class);
        return smsApi.getCode(strMobile, "2");
    }

    @Override
    public Observable<BaseResponse<String>> handleForgotPassword(String mobile, String strValidatorCode, String password1, String password2) {
        DriverApi driverApi = Api.getInstance().retrofit.create(DriverApi.class);
        return driverApi.changePassword(mobile, password1, strValidatorCode);
    }

    @Override
    public Observable<BaseResponse<String>> checkAuthCode(String mobile, String strValidatorCode) {
        DriverApi driverApi = Api.getInstance().retrofit.create(DriverApi.class);
        return driverApi.checkAuthCode(mobile, strValidatorCode);
    }


}
