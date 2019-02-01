package com.yaoguang.appcommon.phone.activitys.start;

import com.yaoguang.lib.base.interfaces.BasePresenter;
import com.yaoguang.lib.base.interfaces.BaseView;

/**
 * 开始的关联
 * Created by zhongjh on 2017/12/26.
 */
public class StartContact {

    public interface Presenter extends BasePresenter {
        /**
         * 根据条件判断打开某个窗体
         */
        void openActivity();

        /**
         * 获取公钥
         */
        void getCodeKey();
    }

    public interface View extends BaseView {

        /**
         * 打开Main
         * @param url 根据是否有值来判断是否先打开Activity
         */
        void startMainActivity(String url);

        /**
         * 打开Login
         * @param url 根据是否有值来判断是否先打开Activity
         */
        void startLoginActivity(String url);

        /**
         * 打开引导
         * @param url 根据是否有值来判断是否先打开Activity
         */
        void startGuidanceActivity(String url);
    }


}
