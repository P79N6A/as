package com.yaoguang.company.activitys.bindphone;

import android.content.Context;

import com.yaoguang.lib.base.interfaces.BasePresenter;
import com.yaoguang.lib.base.interfaces.BaseView;

/**
 * Created by zhongjh on 2017/8/30.
 */

public class BindNewPoneContact {

    public interface View extends BaseView {

        /**
         * 验证码倒数
         */
        void startCountDown();

        /**
         * 验证码停止倒数
         */
        void stopCountDown();

        /**
         * 修改成功
         *
         * @param strUsername 手机号
         * @param strPassword 密码
         */
        void moditySuccess(String strUsername, String strPassword);

        /**
         * 登录成功
         */
        void loginSuccess();

        /**
         * 弹出是否要解除绑定帐号
         */
        void showBindPhoneTips();
    }

    public interface Presenter extends BasePresenter {

        /**
         * 获取验证码
         *
         * @param codeType  验证码
         * @param newMobile 手机号
         */
        void getVerficationCode(String codeType, String newMobile, String userId, String isBind);

        /**
         * 绑定手机号
         *
         * @param codeType    类型
         * @param id          id
         * @param newMobile   手机号
         * @param authCode    验证码
         * @param passWord    密码
         * @param loginMobile 登录手机号
         */
        void setNewMobile(String codeType, String id, String newMobile, String authCode, String passWord, String loginMobile);

        /**
         * 登录
         *
         * @param loginMobile 手机号
         * @param strPassword 密码
         * @param baseContext 上下文
         */
        void login(String loginMobile, String strPassword, Context baseContext);
    }

    public interface Interactor {

    }

}
