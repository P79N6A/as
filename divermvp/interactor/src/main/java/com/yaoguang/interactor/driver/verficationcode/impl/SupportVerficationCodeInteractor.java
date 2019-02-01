package com.yaoguang.interactor.driver.verficationcode.impl;

import com.yaoguang.lib.net.bean.BaseResponse;
import com.yaoguang.lib.net.Api;
import com.yaoguang.datasource.api.common.SMSApi;

import io.reactivex.Observable;

/**
 * Created by 韦理英
 * on 2017/5/3 0003.
 */

public class SupportVerficationCodeInteractor {

    public Observable<BaseResponse<String>> getVerficationCode(final String strMobile, final String type) {
        return Api.getInstance().retrofit.create(SMSApi.class).getCode(strMobile, type);
    }

}
