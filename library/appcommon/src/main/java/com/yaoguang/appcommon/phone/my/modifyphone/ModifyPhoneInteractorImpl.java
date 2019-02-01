package com.yaoguang.appcommon.phone.my.modifyphone;

import android.content.Context;

import com.yaoguang.lib.net.bean.BaseResponse;

import io.reactivex.Observable;

/**
 * Created by zhongjh on 2017/9/18.
 */

public class ModifyPhoneInteractorImpl implements ModifyPhoneContact.Interactor {
    @Override
    public Observable<BaseResponse<Object>> login(String strUsername, String strPassword, Context context) {
        return null;
    }
}
