package com.yaoguang.interactor.driver.verficationcode;

import com.yaoguang.lib.net.bean.BaseResponse;

import io.reactivex.Observable;

/**
 * Created by Administrator on 2017/5/3 0003.
 */

public interface VerficationCode {

    Observable<BaseResponse<String>> getRegisterCode(String codeType, String mobile);

    Observable<BaseResponse<String>> getForgetPassCode(String codeType, String mobile);

    Observable<BaseResponse<String>> getModityPhoneCode(String codeType, String mobile, String userId, String isBind);
}
