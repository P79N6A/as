package com.yaoguang.interactor.common.other.register;

import com.yaoguang.lib.common.constants.Constants;
import com.yaoguang.lib.common.RegexValidator;
import com.yaoguang.lib.net.bean.BaseResponse;
import com.yaoguang.interactor.common.DCSBaseInteractorImpl;
import com.yaoguang.lib.net.Api;
import com.yaoguang.datasource.api.common.SMSApi;

import io.reactivex.Observable;

/**
 * 注册业务类
 * Created by zhongjh on 2017/3/31.
 */
public abstract class RegisterInteractorImpl<T> extends DCSBaseInteractorImpl implements RegisterContact.RegisterInteractor<T> {

    @Override
    public boolean checkValidatorMobile(String strMobile) {
        return RegexValidator.isMobile(strMobile);
    }

    @Override
    public Observable<BaseResponse<String>> getVerficationCode(final String strMobile, String codeType) {
        SMSApi smsApi = Api.getInstance().retrofit.create(SMSApi.class);
        if (codeType.equals(Constants.APP_COMPANY)) {
            return smsApi.getCodeCompany(strMobile, "1", null, null);
        } else {
            return smsApi.getCodeOwner(strMobile, "1");
        }
    }


}
