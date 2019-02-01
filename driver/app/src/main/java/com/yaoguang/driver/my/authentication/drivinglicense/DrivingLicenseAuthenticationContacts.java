package com.yaoguang.driver.my.authentication.drivinglicense;

import com.yaoguang.driver.base.baseview.BasePresenter;
import com.yaoguang.driver.base.baseview.BaseView;
import com.yaoguang.driver.data.source.DriverRepository;
import com.yaoguang.greendao.LoginResult;
import com.yaoguang.greendao.entity.Driver;
import com.yaoguang.greendao.entity.driver.UserDriverLicence;

/**
 * =====================================
 * 作    者: 韦理英
 * 版    本：1.2.9
 * 创建日期：2018/02/08
 * 描    述：
 * 驾驶证认证MPV
 * =====================================
 */

public interface DrivingLicenseAuthenticationContacts {

    interface View extends BaseView {
        void refreshData(UserDriverLicence result);

        void succeed(LoginResult loginResult);
    }

    abstract class Presenter extends BasePresenter<View, DriverRepository> {
        abstract void getData();
        abstract void save(Driver driver);
        abstract void submit(UserDriverLicence driver);
    }
}
