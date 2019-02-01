package com.yaoguang.datasource.company;

import com.yaoguang.lib.net.Api;
import com.yaoguang.lib.net.bean.BaseResponse;
import com.yaoguang.datasource.common.RegisterDataSource;
import com.yaoguang.greendao.entity.UserApply;
import com.yaoguang.datasource.api.company.CompanyApi;

import io.reactivex.Observable;

/**
 * 企业数据源
 * Created by zhongjh on 2017/11/29.
 */
public class CompanyUserDataSource implements RegisterDataSource<UserApply> {


    @Override
    public Observable<BaseResponse<String>> handleOneAuth(String mobile, String pass, String auth) {
        CompanyApi companyApi = Api.getInstance().retrofit.create(CompanyApi.class);
        return companyApi.authVersion2(mobile, pass, auth);
    }

    @Override
    public Observable<BaseResponse<String>> handleRegister(UserApply model, String authCode) {
        CompanyApi companyApi = Api.getInstance().retrofit.create(CompanyApi.class);
        return companyApi.registerVersion2(model, authCode);
    }

    @Override
    public Observable<BaseResponse<String>> checkOldPassword(String userId, String oldPassword) {
        CompanyApi companyApi = Api.getInstance().retrofit.create(CompanyApi.class);
        return companyApi.checkOldPassword(userId, oldPassword);
    }

    /**
     * 更新商户用户数据
     *
     * @param userApply 商户用户模型
     */
    public Observable<BaseResponse<String>> updateCompanyInfo(UserApply userApply) {
        CompanyApi companyApi = Api.getInstance().retrofit.create(CompanyApi.class);
        return companyApi.updateCompanyInfo(userApply);
    }

}
