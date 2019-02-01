

package com.yaoguang.interfaces.driver.presenter;

import com.yaoguang.lib.base.interfaces.BasePresenter;

/**
 * 忘记密码页面的  逻辑分发层
 * 作者：zhongjh
 * 时间：2017-04-5
 */
public interface DForgotPasswordPresenter extends BasePresenter {

    void onModitySuccess();

    void getVerficationCode(String strMobile);

    void handleForgotPassword(String mobile, String strVerficationCode, String strPassWord1, String strPassWord2);

    void onFail(String strCode);

    @Override
    void subscribe();

    @Override
    void unSubscribe();
}