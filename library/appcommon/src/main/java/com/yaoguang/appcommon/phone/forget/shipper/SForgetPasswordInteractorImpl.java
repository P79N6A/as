package com.yaoguang.appcommon.phone.forget.shipper;

import com.yaoguang.appcommon.phone.forget.ForgetPasswordInteractorImpl;
import com.yaoguang.lib.net.bean.BaseResponse;
import com.yaoguang.lib.net.Api;
import com.yaoguang.datasource.api.shipper.OwnerApi;
import com.yaoguang.datasource.api.common.SMSApi;

import io.reactivex.Observable;

/**
 * Created by zhongjh on 2017/7/4.
 */
public class SForgetPasswordInteractorImpl extends ForgetPasswordInteractorImpl {

    @Override
    public Observable<BaseResponse<String>> getVerficationCode(String strMobile) {
        SMSApi smsApi = Api.getInstance().retrofit.create(SMSApi.class);
        return smsApi.getCodeOwner(strMobile, "2");
    }

    @Override
    public Observable<BaseResponse<String>> handleForgotPassword(String mobile, String strValidatorCode, String password1, String password2) {
        OwnerApi ownerApi = Api.getInstance().retrofit.create(OwnerApi.class);
        return ownerApi.changeLoginPassword(mobile, password1, strValidatorCode);
    }

    @Override
    public Observable<BaseResponse<String>> checkAuthCode(String mobile, String strValidatorCode) {
        OwnerApi ownerApi = Api.getInstance().retrofit.create(OwnerApi.class);
        return ownerApi.checkAuthCode(mobile, strValidatorCode);
    }

}
