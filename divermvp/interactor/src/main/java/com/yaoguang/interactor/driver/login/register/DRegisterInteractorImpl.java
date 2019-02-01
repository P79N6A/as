package com.yaoguang.interactor.driver.login.register;

import com.yaoguang.lib.common.RegexValidator;
import com.yaoguang.lib.net.bean.BaseResponse;
import com.yaoguang.greendao.entity.Driver;
import com.yaoguang.interactor.common.DCSBaseInteractorImpl;
import com.yaoguang.interactor.common.other.register.RegisterContact;
import com.yaoguang.lib.net.Api;
import com.yaoguang.datasource.api.DriverApi;
import com.yaoguang.datasource.api.common.SMSApi;

import io.reactivex.Observable;

/**
 * 注册业务类
 * Created by zhongjh on 2017/3/31.
 */
public class DRegisterInteractorImpl extends DCSBaseInteractorImpl implements RegisterContact.RegisterInteractor {

    @Override
    public boolean checkValidatorMobile(String strMobile) {
        return RegexValidator.isMobile(strMobile);
    }

    @Override
    public Observable<BaseResponse<String>> getVerficationCode(String strMobile, String codeType) {
        return Api.getInstance().retrofit.create(SMSApi.class).getCode(strMobile, "1");
    }

    @Override
    public Observable<BaseResponse<String>> handleOneAuth(String mobile, String pass, String auth) {
        return Api.getInstance().retrofit.create(DriverApi.class).auth(mobile, pass, auth);
    }

    @Override
    public Observable<BaseResponse<String>> handleRegister(Object model, String code) {
        Driver driver = (Driver) model;
        return null;
    }

//    @Override
//    public Observable<BaseResponse<Driver>> handleRegister(finalrequest String strMobile, finalrequest String strValidatorCode, finalrequest String strPassWord1) {
//        return Observable.create(new ObservableOnSubscribe<BaseResponse<Driver>>() {
//
//            @Override
//            public void subscribeList(ObservableEmitter<BaseResponse<Driver>> observableEmitter) throws Exception {
//                DriverApi api = RetrofitProvider.get().create(DriverApi.class);
//
//                Call<ResponseBody> call = api.register(strMobile, strValidatorCode, strPassWord1, "null", "1", "2");
//                Response<ResponseBody> bodyResponse = call.execute();
//                String strJson = bodyResponse.body().string();//获取返回体的字符串
//
//                GsonUtils<Driver> gsonUtils = new GsonUtils<>();
//                BaseResponse<Driver> bDriver = gsonUtils.convert(strJson, Driver.class, Driver.STRGSON);
//
//                if (bDriver.getState().equals("200")) {
//                    //存储本地数据库
//                    mDriverBiz.saveDriverIsLogin(bDriver.getResult());
//                    getDriver().setCarNumber(bDriver.getResult().getCarNumber());
//                    getDriver().setId(bDriver.getResult().getId());
//                }
//
//                observableEmitter.onNext(bDriver);
//                observableEmitter.onComplete();
//            }
//        });
//    }


}
