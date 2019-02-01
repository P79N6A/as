package com.yaoguang.company.activitys.login;

import android.content.Context;

import com.yaoguang.lib.net.bean.BaseResponse;
import com.yaoguang.greendao.entity.AppLoginUser;
import com.yaoguang.greendao.entity.AppUserWrapper;

import io.reactivex.Observable;

/**
 * Created by zhongjh on 2017/6/6.
 */

public class LoginContact {

    public interface CLoginInteractor {

        /**
         * 登录
         *
         * @param username 用戶名
         * @param password 密码
         * @param context 上下文
         * @return 返回值
         */
        Observable<BaseResponse<AppUserWrapper>> login(String username, String password, Context context);


        /**
         * 登录
         *
         * @param username 用戶名
         * @param password 密码
         * @param context 上下文
         * @return 返回值
         */
        Observable<BaseResponse<AppLoginUser>> loginVersion2(String username, String password, Context context);


        /**
         * 存储本地数据库
         *
         * @param appUserWrapper 当前用户
         * @param strUsername 用户名
         * @param strPassword 密码
         */
        void saveCompanyUserIsLogin(AppUserWrapper appUserWrapper, String strUsername, String strPassword);
    }

}
