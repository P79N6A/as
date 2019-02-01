package com.yaoguang.company.home.backstagelogon;

import com.yaoguang.lib.base.interfaces.BasePresenter;
import com.yaoguang.lib.base.interfaces.BaseView;

/**
 * @author zhongjh
 * @Package com.yaoguang.company.home.backstagelogon
 * @Description: 后台登录 关联接口
 * @date 2018/01/30
 */
public interface BackstageLogonContract {

    interface Presenter extends BasePresenter {

        /**
         * 利用网址登录后台url
         * @param mUrl
         */
        void backstageLogon(String mUrl);

    }

    interface View extends BaseView {

        /**
         * 退出
         */
        void signOut();
    }
}
