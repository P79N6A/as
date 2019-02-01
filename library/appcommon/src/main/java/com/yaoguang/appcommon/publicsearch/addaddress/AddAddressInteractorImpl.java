package com.yaoguang.appcommon.publicsearch.addaddress;

import com.yaoguang.lib.net.bean.BaseResponse;
import com.yaoguang.greendao.entity.company.InfoClientPlace;
import com.yaoguang.interactor.common.DCSBaseInteractorImpl;
import com.yaoguang.lib.net.Api;
import com.yaoguang.datasource.api.company.CompanyBaseInfoApi;

import io.reactivex.Observable;

/**
 * Created by zhongjh on 2017/6/12.
 */
public class AddAddressInteractorImpl extends DCSBaseInteractorImpl implements AddAddressContact.CAddAddressInteractor {


    @Override
    public Observable<BaseResponse<String>> addLoadPlace(InfoClientPlace infoClientPlace) {
        CompanyBaseInfoApi companyBaseInfoApi = Api.getInstance().retrofit.create(CompanyBaseInfoApi.class);
        return companyBaseInfoApi.addLoadPlace(infoClientPlace, getAppUserWrapper().getId());
    }
}
