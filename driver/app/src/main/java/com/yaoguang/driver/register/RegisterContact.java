package com.yaoguang.driver.register;

import com.yaoguang.common.base.interfaces.BasePresenter;
import com.yaoguang.common.base.interfaces.BaseSubmitProgressView;
import com.yaoguang.interfaces.driver.view.QRCodeBaseView;

/**
 * 注册
 * Created by zhongjh on 2017/7/3.
 */
public class RegisterContact {

    public interface RegisterPresenter extends BasePresenter {

        /**
         * 成功
         */
        void success();

        /**
         * 失败
         *
         * @param strMessage 信息
         */
        void fail(String strMessage);

        /**
         * 获取验证码
         *  @param strMobile 手机号
         *
         */
        void getVerificationCode(String strMobile);

        /**
         * 失败
         *
         * @param intType 作废
         * @param strCode 失败文本
         */
        void fail(int intType, String strCode);

        void handleOneAuth(String mobile, String pass, String auth, boolean cbProtocol);

    }

    public interface RegisterView<T> extends QRCodeBaseView, BaseSubmitProgressView {

        /**
         * 开始倒数
         */
        void startShowCountdown();

        /**
         * 设置当前步骤
         */
        void customSetting();

        /**
         * 结束倒数
         */
        void stopShowCountdown();

    }
}
