package com.yaoguang.interfaces.driver.interactor;

import com.yaoguang.lib.net.bean.BaseResponse;

import io.reactivex.Observable;

/**
 * 忘记密码 的 业务层
 * 作者：zhongjh
 * 时间：2017-04-5
 */
public interface DForgotPasswordInteractor {

    /**
     * 检查手机号码
     *
     * @param strMobile 手机号码
     * @return true为通过，反之不通过
     */
    boolean checkValidatorMobile(String strMobile);

    /**
     * 获取验证码
     *
     * @param strMobile 手机号码
     * @return 数据源
     */
    Observable<BaseResponse<String>> getVerficationCode(String strMobile);

    /**
     * 修改密码
     *
     * @param mobile           手机号码
     * @param strValidatorCode 验证码
     * @param password1        密码1
     * @param password2        密码2
     */
    Observable<BaseResponse<String>> handleForgotPassword(String mobile, String strValidatorCode, String password1, String password2);
}