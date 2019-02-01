package com.yaoguang.interactor.company.other.register;

import com.yaoguang.lib.net.bean.BaseResponse;
import com.yaoguang.greendao.entity.UserApply;
import com.yaoguang.interactor.common.other.register.RegisterInteractorImpl;
import com.yaoguang.lib.net.Api;
import com.yaoguang.datasource.api.company.CompanyApi;

import io.reactivex.Observable;

/**
 * Created by zhongjh on 2017/7/3.
 */
public class CRegisterInteractorImpl extends RegisterInteractorImpl<UserApply> {

    @Override
    public Observable<BaseResponse<String>> handleOneAuth(String mobile, String pass, String auth) {
        CompanyApi companyApi = Api.getInstance().retrofit.create(CompanyApi.class);
        return companyApi.auth(mobile, pass, auth);
    }

    @Override
    public Observable<BaseResponse<String>> handleRegister(UserApply model, String authCode) {
        CompanyApi companyApi = Api.getInstance().retrofit.create(CompanyApi.class);
        return companyApi.register(model, authCode);
    }

}
