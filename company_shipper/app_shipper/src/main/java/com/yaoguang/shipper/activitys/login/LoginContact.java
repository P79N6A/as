package com.yaoguang.shipper.activitys.login;

import android.content.Context;

import com.yaoguang.lib.net.bean.BaseResponse;
import com.yaoguang.greendao.entity.AppUserOwner;
import com.yaoguang.greendao.entity.UserOwner;

import java.util.HashMap;

import io.reactivex.Observable;


/**
 * 登录
 * Created by zhongjh on 2017/6/6.
 */
public class LoginContact {

    public interface SLoginInteractor {

        /**
         * 登录
         *
         * @param username 用户名
         * @param password 密码
         * @param context  上下文
         * @return
         */
        Observable<BaseResponse<UserOwner>> login(String username, String password, Context context);

        /**
         * 登录
         *
         * @param username 用户名
         * @param password 密码
         * @param context  上下文
         * @return
         */
        Observable<BaseResponse<AppUserOwner>> loginVersion2(String username, String password, Context context);


        /**
         * 存储本地数据库
         *
         * @param userOwner   实体
         * @param strUsername 用户名
         * @param strPassword 密码
         */
        void saveUserOwnerIsLogin(UserOwner userOwner, String strUsername, String strPassword);
    }

}
