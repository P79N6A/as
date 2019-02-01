package com.yaoguang.appcommon.phone.forget;

import com.yaoguang.lib.base.interfaces.BaseSubmitProgressView;
import com.yaoguang.lib.net.bean.BaseResponse;
import com.yaoguang.lib.base.interfaces.BasePresenter;

import io.reactivex.Observable;

/**
 * Created by zhongjh on 2017/7/4.
 */

public class ForgetPasswordContact {

    public interface ForgetPasswordInteractor<T> {
        /**
         * 检查手机号码
         *
         * @param strMobile 手机号码
         * @return true为通过，反之不通过
         */
        boolean checkValidatorMobile(String strMobile);

        /**
         * 获取验证码
         *
         * @param strMobile 手机号码
         * @return 数据源
         */
        Observable<BaseResponse<String>> getVerficationCode(String strMobile);

        /**
         * 修改密码
         *
         * @param mobile           手机号码
         * @param strValidatorCode 验证码
         * @param password1        密码1
         * @param password2        密码2
         */
        Observable<BaseResponse<String>> handleForgotPassword(String mobile, String strValidatorCode, String password1, String password2);

        /**
         * 验证验证码
         *
         * @param mobile           手机号码
         * @param strValidatorCode 　验证码
         * @return 服务器信息
         */
        Observable<BaseResponse<String>> checkAuthCode(String mobile, String strValidatorCode);

    }

    public interface ForgetPasswordPresenter<T> extends BasePresenter {
        /**
         * 验证手机验证码
         *
         * @param mobile   手机
         * @param autoCode 验证码
         */
        void checkAuthCode(String mobile, String autoCode);


        /**
         * 修改密码，类型0
         *
         * @param newPass 新密码
         * @param oldPass 旧密码
         */
        void modifyLoginPasswordType0(String newPass, String oldPass);

        /**
         * 修改密码，类型1
         *
         * @param mobile   　手机
         * @param password 　密码
         * @param autoCode 　验证码
         */
        void modifyLoginPasswordType1(String mobile, String password, String autoCode);

        /**
         * 修改密码，类型2
         *
         * @param mId     用户id
         * @param mobile  手机号码
         * @param newPass 新密码
         * @param oldPass 旧密码
         */
        void modifyLoginPasswordType2(String mId, String mobile, String newPass, String oldPass);

        /**
         * 获取验证码
         *
         * @param strMobile
         */
        void getVerficationCode(String strMobile);


    }

    public interface ForgetPasswordViewStep1<T> extends BaseSubmitProgressView {

        /**
         * 成功跳转第二步
         */
        void success();

        /**
         * 失败
         *
         * @param message 信息
         */
        void fail(String message);

        /**
         * 计时器开始倒数
         */
        void startShowCountdown();

        /**
         * 隐藏弹框
         */
        void hideProgressDialog();

        /**
         * 显示toast
         *
         * @param s 值
         */
        void showToast(String s);

        /**
         * 停止弹框
         */
        void stopShowCountdown();
    }

    public interface ForgetPasswordViewStep2<T> extends BaseSubmitProgressView {

        /**
         * 修改成功,类型0
         */
        void onModitySuccessType0();

        /**
         * 修改成功,类型1
         */
        void onModitySuccessType1();

        /**
         * 修改成功,类型2
         *
         * @param newPass 密码
         */
        void onModitySuccessType2(String newPass);

        /**
         * 提示失败
         *
         * @param message 信息
         */
        void fail(String message);


    }

}
