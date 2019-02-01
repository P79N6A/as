package com.yaoguang.interactor.driver.my.setting.impl;

import com.yaoguang.lib.common.constants.Constants;
import com.yaoguang.lib.net.bean.BaseResponse;
import com.yaoguang.greendao.entity.AppUserWrapper;
import com.yaoguang.greendao.entity.Driver;
import com.yaoguang.greendao.entity.Update;
import com.yaoguang.greendao.entity.UserOwner;
import com.yaoguang.datasource.common.DataStatic;
import com.yaoguang.interactor.common.DCSBaseInteractorImpl;
import com.yaoguang.interactor.driver.my.setting.SettingInteractor;
import com.yaoguang.lib.net.Api;
import com.yaoguang.datasource.api.common.AppApi;
import com.yaoguang.datasource.api.DriverApi;
import com.yaoguang.lib.net.RetrofitProvider;

import java.io.IOException;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by 韦理英
 * on 2017/5/22 0022.
 */

public class SettingInteractorImpl extends DCSBaseInteractorImpl implements SettingInteractor {


    @Override
    public Observable<BaseResponse<String>> setPush(final boolean isOpen, final String codeType) {
        int flag = isOpen ? 1 : 0;
        String id = "";
        if (codeType.equals(Constants.APP_DRIVER)) {
            id = getDriver().getId();
        } else if (codeType.equals(Constants.APP_SHIPPER)) {
            id = getUserOwner().getId();
        } else if (codeType.equals(Constants.APP_COMPANY)) {
            id = getAppUserWrapper().getId();
        }
        return Api.getInstance().retrofit.create(DriverApi.class).updateMessageFlag(id, flag);
    }

    /**
     * 保存推送状态
     */
    @Override
    public void setPushInfo(final boolean isOpen, final String codeType) {
        int flag = isOpen ? 1 : 0;

        if (codeType.equals(Constants.APP_DRIVER)) {
            Driver driver = DataStatic.getInstance().getDriver();
            driver.setMessageFlag(flag);
            DataStatic.getInstance().setDriver(driver);
        } else if (codeType.equals(Constants.APP_SHIPPER)) {
            UserOwner userOwner = DataStatic.getInstance().getUserOwner();
            userOwner.setMessageFlag(flag);
            DataStatic.getInstance().setUserOwner(userOwner);
        } else if (codeType.equals(Constants.APP_COMPANY)) {
            AppUserWrapper appUserWrapper = DataStatic.getInstance().getAppUserWrapper();
            appUserWrapper.setMessageFlag(flag);
            DataStatic.getInstance().setAppUserWrapper(appUserWrapper);
        }
    }

    @Override
    public Observable<Update> checkUpdate() {
        return Observable.create(new ObservableOnSubscribe<Update>() {
            @Override
            public void subscribe(ObservableEmitter<Update> e) throws Exception {
                AppApi api = RetrofitProvider.get().create(AppApi.class);
                Call<Update> call = api.checkUpdate("0");
                try {
                    Response<Update> bodyResponse = call.execute();
                    if (bodyResponse != null) {
                        Update update = bodyResponse.body();
                        if (update.getState().equals("200")) {
                            e.onNext(update);
                        }
                    }
                } catch (IOException e1) {
                    e1.printStackTrace();
                    e.onError(e1);
                }
                e.onComplete();
            }
        });
    }
}
