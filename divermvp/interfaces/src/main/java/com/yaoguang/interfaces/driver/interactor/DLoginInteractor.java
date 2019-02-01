package com.yaoguang.interfaces.driver.interactor;

import android.content.Context;
import android.support.annotation.NonNull;

import com.yaoguang.greendao.entity.driver.LoginDriver;
import com.yaoguang.lib.net.bean.BaseResponse;

import io.reactivex.Observable;

/**
 * 登录 的 业务层
 * 作者：zhongjh
 * 时间：2017-04-5
 */
public interface DLoginInteractor {

    /**
     * 登录
     *
     * @param username 用户名
     * @param password 密码
     * @param context  上下文
     * @return 回调登录后的用户
     */
    Observable<BaseResponse<LoginDriver>> login(String username, String password, Context context);

    void loginSuccess(@NonNull LoginDriver driver, String username, String password);
}