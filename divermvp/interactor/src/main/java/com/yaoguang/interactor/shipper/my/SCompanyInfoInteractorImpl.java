package com.yaoguang.interactor.shipper.my;

import com.yaoguang.lib.net.bean.BaseResponse;
import com.yaoguang.greendao.entity.UserOwner;
import com.yaoguang.interactor.common.DCSBaseInteractorImpl;
import com.yaoguang.interactor.common.my.companyoinfo.CompanyInfoContact;
import com.yaoguang.lib.net.Api;
import com.yaoguang.datasource.api.shipper.OwnerApi;

import io.reactivex.Observable;

/**
 * Created by zhongjh on 2017/7/13.
 */
public class SCompanyInfoInteractorImpl<T> extends DCSBaseInteractorImpl implements CompanyInfoContact.CompanyInfoInteractor<UserOwner> {

    @Override
    public Observable<BaseResponse<UserOwner>> getInfo() {
        OwnerApi ownerApi = Api.getInstance().retrofit.create(OwnerApi.class);
        return ownerApi.getInfo(getUserOwner().getId());
    }

    @Override
    public Observable<BaseResponse<String>> modifyPhoto(UserOwner userOwner, String url) {
        userOwner.setShopPhoto(url);
        OwnerApi ownerapi = Api.getInstance().retrofit.create(OwnerApi.class);
        return ownerapi.updateInfo(userOwner);
    }

    @Override
    public Observable<BaseResponse<String>> modifyLog(UserOwner userOwner, String url) {
        userOwner.setShopLogo(url);
        OwnerApi ownerapi = Api.getInstance().retrofit.create(OwnerApi.class);
        return ownerapi.updateInfo(userOwner);
    }

    @Override
    public Observable<BaseResponse<String>> modifyShopDetail(UserOwner model, String value) {
        model.setShopDetail(value);
        OwnerApi ownerapi = Api.getInstance().retrofit.create(OwnerApi.class);
        return ownerapi.updateInfo(model);
    }


}
