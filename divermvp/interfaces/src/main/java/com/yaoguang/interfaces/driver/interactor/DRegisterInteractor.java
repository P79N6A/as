package com.yaoguang.interfaces.driver.interactor;

import com.yaoguang.lib.net.bean.BaseResponse;

import io.reactivex.Observable;

/**
 * 注册 的 业务层
 * 作者：zhongjh
 * 时间：2017-04-5
 */
public interface DRegisterInteractor {

    /**
     * 检查手机号码
     *
     * @param strMobile 手机号码
     */
    boolean checkValidatorMobile(String strMobile);

    /**
     * 获取验证码
     *
     * @param strMobile 手机号码
     */
    Observable<BaseResponse<String>> getVerficationCode(String strMobile);

    /**
     * 进行注册
     *  @param mobile    手机号码
     * @param password1 密码1
     */
//    Observable<BaseResponse<Driver>> handleRegister(String mobile, String strValidatorCode, String password1);
}