package com.yaoguang.appcommon.phone.register2.detail;

import com.yaoguang.lib.base.interfaces.BasePresenter;
import com.yaoguang.lib.base.interfaces.BaseSubmitProgressView;
import com.yaoguang.lib.entity.ProvinceBeans;

/**
 * 详情
 * Created by zhongjh on 2017/11/29.
 */
public class RegisterDetailContact {

    public interface Presenter<T> extends BasePresenter {

        /**
         * 最后一步进行注册
         *
         * @param model 注册的实体类
         * @param code  验证码
         */
        void handleRegister(T model, String code);

        /**
         * 解析具体地址
         */
        void analysisProvinceBeansJson();

    }

    public interface View<T> extends BaseSubmitProgressView {

        /**
         * 获取实体类
         *
         * @return 实体类
         */
        T getModel();

        /**
         * 赋值城市数据
         *
         * @param value 值
         */
        void setProvinceBeans(ProvinceBeans value);

        /**
         * 第一步
         */
        void setStep1();

        /**
         * 第二步
         */
        void setStep2();

        /**
         * 第三步
         */
        void setStep3();
    }

}
