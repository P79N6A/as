package com.yaoguang.interactor.common.activity.main;

import com.yaoguang.lib.base.interfaces.BasePresenter;

/**
 * Created by zhongjh on 2017/8/8.
 */

public class MainContact {

    public interface MainPresenter extends BasePresenter {
        /**
         * 判断逻辑是否需要打开某个界面
         */
        void openActivity();
    }

    public interface MainView {

        /**
         * 打开登录
         */
        void startLoginActivity();

    }

}
