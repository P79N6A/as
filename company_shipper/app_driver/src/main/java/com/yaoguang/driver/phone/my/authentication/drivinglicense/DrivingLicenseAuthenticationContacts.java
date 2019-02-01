package com.yaoguang.driver.phone.my.authentication.drivinglicense;

import com.yaoguang.lib.base.interfaces.BasePresenter;
import com.yaoguang.lib.base.interfaces.BaseView;
import com.yaoguang.greendao.entity.driver.LoginDriver;
import com.yaoguang.greendao.entity.Driver;
import com.yaoguang.greendao.entity.driver.UserDriverLicence;

/**
 * =====================================
 * 作    者: 韦理英
 * 版    本：1.2.9
 * 创建日期：2018/02/08
 * 描    述：
 * 驾驶证认证MPV
 *
 * update zhongjh
 * data 2018/03/15
 *
 * =====================================
 */

public interface DrivingLicenseAuthenticationContacts {

    interface View extends BaseView {

        void refreshData(UserDriverLicence result);

        void succeed(LoginDriver loginDriver);

    }

    interface Presenter extends BasePresenter {

        void getData();

        void save(Driver driver);

        void submit(UserDriverLicence driver);

    }
}
