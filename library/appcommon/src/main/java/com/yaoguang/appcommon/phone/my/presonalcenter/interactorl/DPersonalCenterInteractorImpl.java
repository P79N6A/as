package com.yaoguang.appcommon.phone.my.presonalcenter.interactorl;

import com.yaoguang.appcommon.phone.my.presonalcenter.PersonalCenterContact;
import com.yaoguang.lib.net.bean.BaseResponse;
import com.yaoguang.greendao.entity.Driver;
import com.yaoguang.lib.net.Api;
import com.yaoguang.interactor.common.DCSBaseInteractorImpl;
import com.yaoguang.datasource.api.DriverApi;

import io.reactivex.Observable;

/**
 * 文件名：
 * 描    述：
 * 作    者：韦理英
 * 时    间：2017/8/29 0029.
 * 版    权：
 */
public class DPersonalCenterInteractorImpl extends DCSBaseInteractorImpl implements PersonalCenterContact.PersonalCenterInteractor {
    @Override
    public Object getInfo() {
        return null;
    }

    @Override
    public Observable<BaseResponse<String>> modifyPhoto(String s) {
        return null;
    }

    @Override
    public void modifySqlPhoto(String s) {

    }

    @Override
    public Observable<BaseResponse<String>> modifyMobile(String s) {
        return null;
    }

    @Override
    public void modifySqlMobile(String s) {

    }

    @Override
    public Observable<BaseResponse<String>> modifyEmail(String s) {
        Driver driver = new Driver();
        driver.setId(getDriver().getId());
        driver.setEmail(s);
        return Api.getInstance().retrofit.create(DriverApi.class).update(driver);
    }

    @Override
    public void modifySqlEmail(String s) {

    }

    @Override
    public Observable<BaseResponse<String>> modifyQQ(String s) {
        Driver driver = new Driver();
        driver.setId(getDriver().getId());
        driver.setQq(s);
        return Api.getInstance().retrofit.create(DriverApi.class).update(driver);
    }

    @Override
    public void modifySqlQQ(String s) {

    }

    @Override
    public Observable<BaseResponse<String>> modifySign(String s) {
        Driver driver = new Driver();
        driver.setId(getDriver().getId());
        driver.setSign(s);
        return Api.getInstance().retrofit.create(DriverApi.class).update(driver);
    }

    @Override
    public Observable<BaseResponse<String>> modifyNickName(String s) {
        Driver driver = new Driver();
        driver.setId(getDriver().getId());
        driver.setNickName(s);
        return Api.getInstance().retrofit.create(DriverApi.class).update(driver);
    }

    @Override
    public void modifySqlSign(String s) {
    }


    @Override
    public Observable<BaseResponse<String>> changePassword(String id, String newPass1, String oldPass) {
        Driver driver = getDriver();
        return Api.getInstance().retrofit.create(DriverApi.class).changeLoginPassword(driver.getId(), newPass1, oldPass);
    }

    @Override
    public Observable<BaseResponse<String>> modifyPhone(String newPhone, String code, String password) {
        return Api.getInstance().retrofit.create(DriverApi.class).updateMobile(getDriver().getId(), newPhone, code, password);
    }

    @Override
    public void modifySqlPhone(String newPhone, String code) {

    }

    @Override
    public Observable<BaseResponse<String>> modifyBackgroundPicture(String s) {
        return null;
    }
}
