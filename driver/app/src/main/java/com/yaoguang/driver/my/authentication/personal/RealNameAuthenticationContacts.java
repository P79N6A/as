package com.yaoguang.driver.my.authentication.personal;

import com.yaoguang.driver.base.baseview.BasePresenter;
import com.yaoguang.driver.base.baseview.BaseView;
import com.yaoguang.driver.data.source.DriverRepository;
import com.yaoguang.greendao.LoginResult;
import com.yaoguang.greendao.entity.Driver;

/**
 * =====================================
 * 作    者: 韦理英
 * 版    本：$version
 * 创建日期：2018/02/08
 * 描    述：
 * 实名认证MPV
 * =====================================
 */

public interface RealNameAuthenticationContacts {

    interface View extends BaseView {
        void refreshData(Driver result);

        void succeed(LoginResult loginResult);
    }

    abstract class Presenter extends BasePresenter<View, DriverRepository> {
        abstract void getData();
        abstract void save(Driver driver);
        abstract void submit(Driver driver);
    }
}
