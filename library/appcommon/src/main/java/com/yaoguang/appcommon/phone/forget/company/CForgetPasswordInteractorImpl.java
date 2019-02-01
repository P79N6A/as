package com.yaoguang.appcommon.phone.forget.company;

import com.yaoguang.appcommon.phone.forget.ForgetPasswordInteractorImpl;
import com.yaoguang.lib.net.bean.BaseResponse;
import com.yaoguang.lib.net.Api;
import com.yaoguang.datasource.api.company.CompanyApi;
import com.yaoguang.datasource.api.common.SMSApi;

import io.reactivex.Observable;

/**
 * Created by zhongjh on 2017/7/4.
 */
public class CForgetPasswordInteractorImpl extends ForgetPasswordInteractorImpl {

    @Override
    public Observable<BaseResponse<String>> getVerficationCode(String strMobile) {
        SMSApi smsApi = Api.getInstance().retrofit.create(SMSApi.class);
        return smsApi.getCodeCompany(strMobile, "2", null, null);
    }

    @Override
    public Observable<BaseResponse<String>> handleForgotPassword(String mobile, String strValidatorCode, String password1, String password2) {
        CompanyApi companyApi = Api.getInstance().retrofit.create(CompanyApi.class);
        return companyApi.changeLoginPassword(mobile, password1, strValidatorCode);
    }

    @Override
    public Observable<BaseResponse<String>> checkAuthCode(String mobile, String strValidatorCode) {
        CompanyApi companyApi = Api.getInstance().retrofit.create(CompanyApi.class);
        return companyApi.checkAuthCode(mobile, strValidatorCode);
    }

}
