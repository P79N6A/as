package com.yaoguang.appcommon.phone.my.presonalcenter.interactorl;

import com.yaoguang.appcommon.phone.my.presonalcenter.PersonalCenterContact;
import com.yaoguang.interactor.common.DCSBaseInteractorImpl;
import com.yaoguang.lib.net.bean.BaseResponse;
import com.yaoguang.datasource.common.DataStatic;
import com.yaoguang.greendao.entity.UserOwner;
import com.yaoguang.lib.net.Api;
import com.yaoguang.datasource.api.shipper.OwnerApi;

import io.reactivex.Observable;

/**
 * 货主个人中心
 * Created by zhongjh on 2017/7/5.
 */
public class SPersonalCenterInteractorImpl extends DCSBaseInteractorImpl implements PersonalCenterContact.PersonalCenterInteractor {

    @Override
    public UserOwner getInfo() {
        return DataStatic.getInstance().getUserOwner();
    }

    @Override
    public Observable<BaseResponse<String>> modifyPhoto(String s) {
        UserOwner userOwner = DataStatic.getInstance().getUserOwner();
        userOwner.setHeadPortrait(s);
        OwnerApi ownerApi = Api.getInstance().retrofit.create(OwnerApi.class);
        return ownerApi.updateInfo(userOwner);
    }

    @Override
    public void modifySqlPhoto(String s) {
        UserOwner userOwner = DataStatic.getInstance().getUserOwner();
        userOwner.setHeadPortrait(s);
        DataStatic.getInstance().setUserOwner(userOwner);
    }

    @Override
    public Observable<BaseResponse<String>> modifyMobile(String s) {
        UserOwner userOwner = DataStatic.getInstance().getUserOwner();
        userOwner.setTel(s);
        OwnerApi ownerApi = Api.getInstance().retrofit.create(OwnerApi.class);
        return ownerApi.updateInfo(userOwner);
    }

    @Override
    public void modifySqlMobile(String s) {
        UserOwner userOwner = DataStatic.getInstance().getUserOwner();
        userOwner.setTel(s);
        DataStatic.getInstance().setUserOwner(userOwner);
    }

    @Override
    public Observable<BaseResponse<String>> modifyEmail(String s) {
        //判断邮箱
        UserOwner userOwner = DataStatic.getInstance().getUserOwner();
        userOwner.setEmail(s);
        OwnerApi ownerApi = Api.getInstance().retrofit.create(OwnerApi.class);
        return ownerApi.updateInfo(userOwner);
    }

    @Override
    public void modifySqlEmail(String s) {
        UserOwner userOwner = DataStatic.getInstance().getUserOwner();
        userOwner.setEmail(s);
        DataStatic.getInstance().setUserOwner(userOwner);
    }

    @Override
    public Observable<BaseResponse<String>> modifyQQ(String s) {
        UserOwner userOwner = DataStatic.getInstance().getUserOwner();
        userOwner.setQq(s);
        OwnerApi ownerApi = Api.getInstance().retrofit.create(OwnerApi.class);
        return ownerApi.updateInfo(userOwner);
    }

    @Override
    public void modifySqlQQ(String s) {
        UserOwner userOwner = DataStatic.getInstance().getUserOwner();
        userOwner.setQq(s);
        DataStatic.getInstance().setUserOwner(userOwner);
    }

    @Override
    public Observable<BaseResponse<String>> modifySign(String s) {
        UserOwner userOwner = DataStatic.getInstance().getUserOwner();
        userOwner.setSign(s);
        OwnerApi ownerApi = Api.getInstance().retrofit.create(OwnerApi.class);
        return ownerApi.updateInfo(userOwner);
    }

    @Override
    public Observable<BaseResponse<String>> modifyNickName(String s) {
        return null;
    }

    @Override
    public void modifySqlSign(String s) {
        UserOwner userOwner = DataStatic.getInstance().getUserOwner();
        userOwner.setSign(s);
        DataStatic.getInstance().setUserOwner(userOwner);
    }

    @Override
    public Observable<BaseResponse<String>> changePassword(String id, String newPass1, String oldPass) {
        if (id == null)
            id = getUserOwner().getId();
        OwnerApi ownerApi = Api.getInstance().retrofit.create(OwnerApi.class);
        return ownerApi.changePassword(id, oldPass, newPass1);
    }

    @Override
    public Observable<BaseResponse<String>> modifyPhone(String newPhone, String code, String password) {
        OwnerApi ownerApi = Api.getInstance().retrofit.create(OwnerApi.class);
        return ownerApi.updatePhone(getUserOwner().getId(), newPhone, code, password);
    }

    @Override
    public void modifySqlPhone(String newPhone, String code) {
        UserOwner userOwner = DataStatic.getInstance().getUserOwner();
        userOwner.setPhone(newPhone);
        DataStatic.getInstance().setUserOwner(userOwner);
    }

    @Override
    public Observable<BaseResponse<String>> modifyBackgroundPicture(String s) {
        UserOwner userOwner = DataStatic.getInstance().getUserOwner();
        userOwner.setBackgroundPicture(s);
        OwnerApi ownerApi = Api.getInstance().retrofit.create(OwnerApi.class);
        return ownerApi.updateInfo(userOwner);
    }

}
