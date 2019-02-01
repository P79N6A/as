package com.yaoguang.appcommon.phone.register2.doorway;

import com.yaoguang.lib.base.interfaces.BasePresenter;
import com.yaoguang.lib.base.interfaces.BaseSubmitProgressView;
import com.yaoguang.interfaces.driver.view.QRCodeBaseView;

/**
 * 注册
 * Created by zhongjh on 2017/7/3.
 */
public class RegisterDoorwayContact {

    public interface Presenter<T> extends BasePresenter {

        /**
         * 获取验证码
         *
         * @param strMobile 手机号
         */
        void getVerficationCode(String strMobile);

        /**
         * 失败
         *
         * @param intType 作废
         * @param strCode 失败文本
         */
        void fail(int intType, String strCode);

        /**
         * 处理入口
         *
         * @param mobile     手机号
         * @param pass       密码
         * @param auth       验证码
         * @param cbProtocol 是否勾选协议
         */
        void handleOneAuth(String mobile, String pass, String auth, boolean cbProtocol);

    }

    public interface View<T> extends QRCodeBaseView, BaseSubmitProgressView {

        /**
         * 开始倒数
         */
        void startShowCountdown();

        /**
         * 结束倒数
         */
        void stopShowCountdown();

    }

}
