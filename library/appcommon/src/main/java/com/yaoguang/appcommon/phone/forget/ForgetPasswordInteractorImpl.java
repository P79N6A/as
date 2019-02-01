package com.yaoguang.appcommon.phone.forget;

import com.yaoguang.lib.common.RegExpValidatorUtils;

/**
 * Created by zhongjh on 2017/7/4.
 */

public abstract class ForgetPasswordInteractorImpl<T> implements ForgetPasswordContact.ForgetPasswordInteractor<T> {
    @Override
    public boolean checkValidatorMobile(String strMobile) {
        return RegExpValidatorUtils.IsHandset(strMobile);
    }

}
