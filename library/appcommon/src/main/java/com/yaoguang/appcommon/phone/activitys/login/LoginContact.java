package com.yaoguang.appcommon.phone.activitys.login;

import android.content.Context;

import com.yaoguang.lib.base.interfaces.BasePresenter;
import com.yaoguang.lib.base.interfaces.BaseSubmitProgressView;
import com.yaoguang.lib.base.interfaces.BaseView;

/**
 * Created by zhongjh on 2017/8/30.
 */
public class LoginContact {

    public interface LoginPresenter<T> extends BasePresenter {
        /**
         * 成功
         */
        void success(T value, String strUsername, String strPassword);

        /**
         * 失败
         *
         * @param code 失败信息
         */
        void fail(String code);

        /**
         * 注册失败
         *
         * @param intType 0代表密码1文本框提示错误,1代表密码2文本框提示错误
         * @param strCode 错误信息
         */
        void onFail(int intType, String strCode);

        /**
         * 登录认证
         * 作者：韦理英
         * 时间：2017-03-28
         */
        void loginAuthentication(String driverPhone, String driverPassword, Context context);

        /**
         * 登录首页
         *
         * @param value
         * @param strUsername
         * @param strPassword
         */
        void toMain(T value, String strUsername, String strPassword);
    }


    public interface LoginView<T, T2> extends BaseView, BaseSubmitProgressView {

        /**
         * @return 手机号
         */
        String getPhone();

        /**
         * @return 密码
         */
        String getPassword();

        /**
         * 用户文本框的动画操作
         *
         * @param alert 内容
         */
        void showToastUserName(String alert);

        /**
         * 密码文本框的动画操作
         *
         * @param alert 内容
         */
        void showToastPassWord(String alert);

        /**
         * 跳转首页
         */
        void toMainActivityCustom();

        /**
         * 自动登录
         */
        void rememberAccount();

        /**
         * 清空文本框数据
         */
        void clearAccount();

        /**
         * 开启修改密码的activity
         */
        void startUpdatePasswordActivity(String id, String mobile, String oldpassWord);

        /**
         * 开启绑定的activity
         */
        void startBindPhoneActivity(T2 value,String strUsername, String strPassword);

        /**
         * 开启审核中的activity
         */
        void startAuthenticationIngActivity(T value);

        /**
         * 开启审核不通过的activity
         */
        void startAuthenticationErrorActivity(T value);

        /**
         * 开启资料未完善的activity
         */
        void startAuthenticationActivity(T value);

        /**
         * 打开需要扫码验证的activity
         */
        void startGenerateTwoDimensionalCode(String userId);
    }


}
