package com.yaoguang.interactor.common.other.register;

import com.yaoguang.lib.base.interfaces.BaseSubmitProgressView;
import com.yaoguang.lib.net.bean.BaseResponse;
import com.yaoguang.lib.entity.ProvinceBeans;
import com.yaoguang.lib.base.interfaces.BasePresenter;
import com.yaoguang.interfaces.driver.view.QRCodeBaseView;

import io.reactivex.Observable;

/**
 * 注册
 * Created by zhongjh on 2017/7/3.
 */
public class RegisterContact {

    public interface RegisterInteractor<T> {
        /**
         * 检查手机号码
         *
         * @param strMobile 手机号码
         */
        boolean checkValidatorMobile(String strMobile);

        /**
         * 获取验证码
         *
         * @param strMobile 手机号码
         * @param codeType  用户类型
         */
        Observable<BaseResponse<String>> getVerficationCode(String strMobile, String codeType);

        /**
         * 第一步的验证
         *
         * @param mobile 手机
         * @param pass   密码
         * @param auth   验证码
         */
        Observable<BaseResponse<String>> handleOneAuth(String mobile, String pass, String auth);

        /**
         * 最后一步进行注册
         * T
         *
         * @param model 注册的实体类
         * @param code  验证码
         */
        Observable<BaseResponse<String>> handleRegister(T model, String code);


    }

    public interface RegisterPresenter<T> extends BasePresenter {

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
         *
         * @param strMobile 手机号
         * @param codeType  类型
         */
        void getVerficationCode(String strMobile, String codeType);

        /**
         * 失败
         *
         * @param intType 作废
         * @param strCode 失败文本
         */
        void fail(int intType, String strCode);

        /**
         * 处理第一步
         *
         * @param mobile     手机号
         * @param pass       密码
         * @param auth       验证码
         * @param cbProtocol 是否勾选协议
         */
        void handleOneAuth(String mobile, String pass, String auth, boolean cbProtocol);

        /**
         * 处理第二步
         *
         * @param model
         * @param code
         */
        void handleRegister(T model, String code);

        /**
         * 初始化注册的明细页面
         */
        void initDetail();

        /**
         * 解析具体地址
         */
        void analysisProvinceBeansJson();
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
         * 下一步
         */
        void nextPage();

        /**
         * 获取实体类
         *
         * @return
         */
        T getModel();

        /**
         * 结束倒数
         */
        void stopShowCountdown();

    }

    public interface RegisterDetailView {
        /**
         * 赋值城市数据
         *
         * @param value 值
         */
        void setProvinceBeans(ProvinceBeans value);
    }

}
