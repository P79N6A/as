package com.yaoguang.datasource.common;

import com.yaoguang.lib.base.BaseApplication;
import com.yaoguang.lib.common.constants.Constants;
import com.yaoguang.lib.net.Api;
import com.yaoguang.lib.net.bean.BaseResponse;
import com.yaoguang.datasource.api.common.SMSApi;

import io.reactivex.Observable;

/**
 * 消息api
 * Created by zhongjh on 2018/1/23.
 */
public class SMSDataSource {

    /**
     * 获取验证码
     *
     * @param strMobile 手机号码
     */
    public Observable<BaseResponse<String>> getVerficationCode(final String strMobile) {
        SMSApi smsApi = Api.getInstance().retrofit.create(SMSApi.class);
        switch (BaseApplication.getAppType()) {
            case Constants.APP_COMPANY:
                return smsApi.getCodeCompanyVersion2(strMobile, "1", null, null);
            case Constants.APP_SHIPPER:
                return smsApi.getCodeOwnerVersion2(strMobile, "1");
            default:
                // 司机端
                return smsApi.getVerificationCode(strMobile, "1");
        }
    }




}
