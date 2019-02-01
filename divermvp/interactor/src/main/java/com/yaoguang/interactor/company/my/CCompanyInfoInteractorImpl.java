package com.yaoguang.interactor.company.my;

import com.yaoguang.lib.net.bean.BaseResponse;
import com.yaoguang.greendao.entity.UserApply;
import com.yaoguang.interactor.common.DCSBaseInteractorImpl;
import com.yaoguang.interactor.common.my.companyoinfo.CompanyInfoContact;
import com.yaoguang.lib.net.Api;
import com.yaoguang.datasource.api.company.CompanyApi;

import io.reactivex.Observable;

/**
 * Created by zhongjh on 2017/7/13.
 */
public class CCompanyInfoInteractorImpl<T> extends DCSBaseInteractorImpl implements CompanyInfoContact.CompanyInfoInteractor<UserApply> {


    @Override
    public Observable<BaseResponse<UserApply>> getInfo() {
        CompanyApi companyApi = Api.getInstance().retrofit.create(CompanyApi.class);
        return companyApi.getCompanyInfo(getAppUserWrapper().getId());
    }

    @Override
    public Observable<BaseResponse<String>> modifyPhoto(UserApply userApply, String url) {
        userApply.setShopPhoto(url);
        CompanyApi companyApi = Api.getInstance().retrofit.create(CompanyApi.class);
        return companyApi.updateCompanyInfo(userApply);
    }

    @Override
    public Observable<BaseResponse<String>> modifyLog(UserApply userApply, String url) {
        userApply.setShopLogo(url);
        CompanyApi companyApi = Api.getInstance().retrofit.create(CompanyApi.class);
        return companyApi.updateCompanyInfo(userApply);
    }

    @Override
    public Observable<BaseResponse<String>> modifyShopDetail(UserApply model, String value) {
        model.setShopDetail(value);
        CompanyApi companyApi = Api.getInstance().retrofit.create(CompanyApi.class);
        return companyApi.updateCompanyInfo(model);
    }


}
