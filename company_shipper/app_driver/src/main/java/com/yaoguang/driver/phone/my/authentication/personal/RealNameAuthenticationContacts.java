package com.yaoguang.driver.phone.my.authentication.personal;

import com.yaoguang.lib.base.interfaces.BasePresenter;
import com.yaoguang.lib.base.interfaces.BaseView;
import com.yaoguang.greendao.entity.driver.LoginDriver;
import com.yaoguang.greendao.entity.Driver;

/**
 * =====================================
 * 作    者: 韦理英
 * 版    本：$version
 * 创建日期：2018/02/08
 * 描    述：
 * 实名认证MPV
 * <p>
 * Update : zhongjh
 * Data 2018/3/15
 * <p>
 * =====================================
 */

public interface RealNameAuthenticationContacts {

    interface View extends BaseView {

        /**
         * 给控制层控制退出
         */
        void presenterPop();

        void refreshData(Driver result);

        void succeed(LoginDriver loginDriver);
    }

    interface Presenter extends BasePresenter {

        void getData();

        void save(Driver driver);

        void submit(Driver driver);
    }
}
