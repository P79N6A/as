package com.yaoguang.appcommon.phone.my.modifyphone;

import android.content.Context;

import com.yaoguang.lib.base.interfaces.BasePresenter;
import com.yaoguang.lib.base.interfaces.BaseView;
import com.yaoguang.lib.net.bean.BaseResponse;

import io.reactivex.Observable;

/**
 * 修改帐号
 */
public interface ModifyPhoneContact {

    interface View extends BaseView {
        void success();

        void fail(String message);
    }

    interface ViewStep2 extends BaseView{

        void startCountDown();

        void moditySuccess();

        void stopCountDown();

        void showBindPhoneTips();
    }

    interface Presenter<T> extends BasePresenter {

        void login(String strPassword, Context context);

        void getVerficationCode(String codeType, String s,String isBind);

        void setNewMobile(String codeType, String mobile, String authCode, String password);
    }

    interface Interactor<T> {

        Observable<BaseResponse<Object>> login(String strUsername, String strPassword, Context context);
    }

}
