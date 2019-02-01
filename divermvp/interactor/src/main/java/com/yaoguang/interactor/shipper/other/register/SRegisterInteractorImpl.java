package com.yaoguang.interactor.shipper.other.register;

import com.yaoguang.lib.net.bean.BaseResponse;
import com.yaoguang.greendao.entity.UserOwner;
import com.yaoguang.interactor.common.other.register.RegisterInteractorImpl;
import com.yaoguang.lib.net.Api;
import com.yaoguang.datasource.api.shipper.OwnerApi;

import io.reactivex.Observable;

/**
 * Created by zhongjh on 2017/7/3.
 */
public class SRegisterInteractorImpl extends RegisterInteractorImpl<UserOwner> {

    @Override
    public Observable<BaseResponse<String>> handleOneAuth(String mobile, String pass, String auth) {
        OwnerApi ownerApi = Api.getInstance().retrofit.create(OwnerApi.class);
        return ownerApi.auth(mobile, pass, auth);
    }

    @Override
    public Observable<BaseResponse<String>> handleRegister(UserOwner model, String authCode) {
        OwnerApi ownerApi = Api.getInstance().retrofit.create(OwnerApi.class);
        return ownerApi.register(model, model.getPhone(), model.getPassword(), authCode);
    }

}
