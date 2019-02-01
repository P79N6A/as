package com.yaoguang.datasource.shipper;

import com.yaoguang.lib.net.bean.BaseResponse;
import com.yaoguang.datasource.common.RegisterDataSource;
import com.yaoguang.greendao.entity.UserOwner;
import com.yaoguang.lib.net.Api;
import com.yaoguang.datasource.api.shipper.OwnerApi;

import io.reactivex.Observable;

/**
 * 商户数据源
 * Created by zhongjh on 2017/11/29.
 */
public class OwnerUserDataSource implements RegisterDataSource<UserOwner> {

    @Override
    public Observable<BaseResponse<String>> handleOneAuth(String mobile, String pass, String auth) {
        OwnerApi ownerApi = Api.getInstance().retrofit.create(OwnerApi.class);
        return ownerApi.authVersion2(mobile, pass, auth);
    }

    @Override
    public Observable<BaseResponse<String>> handleRegister(UserOwner model, String code) {
        OwnerApi ownerApi = Api.getInstance().retrofit.create(OwnerApi.class);
        return ownerApi.registerVersion2(model, model.getPhone(), model.getPassword(), code);
    }

    @Override
    public Observable<BaseResponse<String>> checkOldPassword(String userId, String oldPassword) {
        OwnerApi ownerApi = Api.getInstance().retrofit.create(OwnerApi.class);
        return ownerApi.checkOldPassword(userId, oldPassword);
    }

    /**
     * 更新用户
     *
     * @param userOwner 用户数据
     */
    public Observable<BaseResponse<String>> updateInfo(UserOwner userOwner) {
        OwnerApi ownerapi = Api.getInstance().retrofit.create(OwnerApi.class);
        return ownerapi.updateInfo(userOwner);
    }

}
