package com.yaoguang.appcommon.phone.my.presonalcenter.interactorl;

import com.yaoguang.appcommon.phone.my.presonalcenter.PersonalCenterContact;
import com.yaoguang.interactor.common.DCSBaseInteractorImpl;
import com.yaoguang.lib.net.bean.BaseResponse;
import com.yaoguang.greendao.entity.AppUserWrapper;
import com.yaoguang.datasource.common.DataStatic;
import com.yaoguang.lib.net.Api;
import com.yaoguang.datasource.api.company.CompanyApi;

import io.reactivex.Observable;

/**
 * 个人中心
 * Created by zhongjh on 2017/7/5.
 */
public class CPersonalCenterInteractorImpl  extends DCSBaseInteractorImpl implements PersonalCenterContact.PersonalCenterInteractor {

    @Override
    public AppUserWrapper getInfo() {
        return DataStatic.getInstance().getAppUserWrapper();
    }

    @Override
    public Observable<BaseResponse<String>> modifyPhoto(String s) {
        AppUserWrapper appUserWrapper = DataStatic.getInstance().getAppUserWrapper();
        appUserWrapper.setPhoto(s);
        CompanyApi companyApi = Api.getInstance().retrofit.create(CompanyApi.class);
        return companyApi.updateInfo(appUserWrapper);
    }

    @Override
    public void modifySqlPhoto(String s) {
        AppUserWrapper appUserWrapper = DataStatic.getInstance().getAppUserWrapper();
        appUserWrapper.setPhoto(s);
        DataStatic.getInstance().setAppUserWrapper(appUserWrapper);
    }

    @Override
    public Observable<BaseResponse<String>> modifyMobile(String s) {
        AppUserWrapper appUserWrapper = DataStatic.getInstance().getAppUserWrapper();
        //物流端这边是相反的
        appUserWrapper.setPhone(s);
        CompanyApi companyApi = Api.getInstance().retrofit.create(CompanyApi.class);
        return companyApi.updateInfo(appUserWrapper);
    }

    @Override
    public void modifySqlMobile(String s) {
        AppUserWrapper appUserWrapper = DataStatic.getInstance().getAppUserWrapper();
        appUserWrapper.setPhone(s);
        DataStatic.getInstance().setAppUserWrapper(appUserWrapper);
    }

    @Override
    public Observable<BaseResponse<String>> modifyEmail(String s) {
        //判断邮箱
        AppUserWrapper appUserWrapper = DataStatic.getInstance().getAppUserWrapper();
        appUserWrapper.setEmail(s);
        CompanyApi companyApi = Api.getInstance().retrofit.create(CompanyApi.class);
        return companyApi.updateInfo(appUserWrapper);
    }

    @Override
    public void modifySqlEmail(String s) {
        AppUserWrapper appUserWrapper = DataStatic.getInstance().getAppUserWrapper();
        appUserWrapper.setEmail(s);
        DataStatic.getInstance().setAppUserWrapper(appUserWrapper);
    }

    @Override
    public Observable<BaseResponse<String>> modifyQQ(String s) {
        AppUserWrapper appUserWrapper = DataStatic.getInstance().getAppUserWrapper();
        appUserWrapper.setQq(s);
        CompanyApi companyApi = Api.getInstance().retrofit.create(CompanyApi.class);
        return companyApi.updateInfo(appUserWrapper);
    }

    @Override
    public void modifySqlQQ(String s) {
        AppUserWrapper appUserWrapper = DataStatic.getInstance().getAppUserWrapper();
        appUserWrapper.setQq(s);
        DataStatic.getInstance().setAppUserWrapper(appUserWrapper);
    }

    @Override
    public Observable<BaseResponse<String>> modifySign(String s) {
        AppUserWrapper appUserWrapper = DataStatic.getInstance().getAppUserWrapper();
        appUserWrapper.setSign(s);
        CompanyApi companyApi = Api.getInstance().retrofit.create(CompanyApi.class);
        return companyApi.updateInfo(appUserWrapper);
    }

    @Override
    public Observable<BaseResponse<String>> modifyNickName(String s) {
        return null;
    }

    @Override
    public void modifySqlSign(String s) {
        AppUserWrapper appUserWrapper = DataStatic.getInstance().getAppUserWrapper();
        appUserWrapper.setSign(s);
        DataStatic.getInstance().setAppUserWrapper(appUserWrapper);
    }


    @Override
    public Observable<BaseResponse<String>> changePassword(String id,  String newPass1, String oldPass) {
        if (id == null)
            id = getAppUserWrapper().getId();
        CompanyApi ownerApi = Api.getInstance().retrofit.create(CompanyApi.class);
        return ownerApi.changePassword(id,  oldPass, newPass1);
    }

    @Override
    public Observable<BaseResponse<String>> modifyPhone(String newPhone, String code, String password) {
        CompanyApi companyApi = Api.getInstance().retrofit.create(CompanyApi.class);
        return companyApi.updatePhone(getAppUserWrapper().getId(), newPhone, code, password);
    }

    @Override
    public void modifySqlPhone(String newPhone, String code) {
        AppUserWrapper appUserWrapper = DataStatic.getInstance().getAppUserWrapper();
        appUserWrapper.setMobile(newPhone);
        DataStatic.getInstance().setAppUserWrapper(appUserWrapper);
    }

    @Override
    public Observable<BaseResponse<String>> modifyBackgroundPicture(String s) {
        AppUserWrapper appUserWrapper = DataStatic.getInstance().getAppUserWrapper();
        appUserWrapper.setBackgroundPicture(s);
        CompanyApi companyApi = Api.getInstance().retrofit.create(CompanyApi.class);
        return companyApi.updateInfo(appUserWrapper);
    }

}
