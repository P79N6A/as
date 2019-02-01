package com.yaoguang.interactor.driver.verficationcode.impl;

import com.yaoguang.lib.common.constants.Constants;
import com.yaoguang.lib.net.bean.BaseResponse;
import com.yaoguang.interactor.driver.BasePresenterImplV2;
import com.yaoguang.interactor.driver.verficationcode.VerficationCode;
import com.yaoguang.lib.net.Api;
import com.yaoguang.datasource.api.common.SMSApi;

import io.reactivex.Observable;

/**
 * Created by Administrator on 2017/5/3 0003.
 */
public abstract class SupportVerficationCodePresenter extends BasePresenterImplV2 implements VerficationCode {
    SupportVerficationCodeInteractor interactor;

    public SupportVerficationCodePresenter() {
        interactor = new SupportVerficationCodeInteractor();
    }

    @Override
    public Observable<BaseResponse<String>> getRegisterCode(String codeType, String mobile) {
        SMSApi smsApi = Api.getInstance().retrofit.create(SMSApi.class);
        switch (codeType) {
            case Constants.APP_DRIVER:
                return smsApi.getCodeDriver(mobile, "1");
            case Constants.APP_COMPANY:
                return smsApi.getCodeCompany(mobile, "1", null, null);
            case Constants.APP_SHIPPER:
                return smsApi.getCodeOwner(mobile, "1");
        }
        return smsApi.getCodeDriver(mobile, "1");
    }

    @Override
    public Observable<BaseResponse<String>> getForgetPassCode(String codeType, String mobile) {
        SMSApi smsApi = Api.getInstance().retrofit.create(SMSApi.class);
        switch (codeType) {
            case Constants.APP_DRIVER:
                return smsApi.getCodeDriver(mobile, "2");
            case Constants.APP_COMPANY:
                return smsApi.getCodeCompany(mobile, "2", null, null);
            case Constants.APP_SHIPPER:
                return smsApi.getCodeOwner(mobile, "2");
        }
        return smsApi.getCodeDriver(mobile, "2");
    }

    @Override
    public Observable<BaseResponse<String>> getModityPhoneCode(String codeType, String mobile, String userId, String isBind) {
        SMSApi smsApi = Api.getInstance().retrofit.create(SMSApi.class);
        switch (codeType) {
            case Constants.APP_DRIVER:
                return smsApi.getCodeDriver(mobile, "3");
            case Constants.APP_COMPANY:
                return smsApi.getCodeCompany(mobile, "3", userId, isBind);
            case Constants.APP_SHIPPER:
                return smsApi.getCodeOwner(mobile, "3");
        }
        return smsApi.getCodeDriver(mobile, "3");
    }

}
