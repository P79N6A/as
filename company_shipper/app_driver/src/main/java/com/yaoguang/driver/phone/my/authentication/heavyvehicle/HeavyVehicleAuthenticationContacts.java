package com.yaoguang.driver.phone.my.authentication.heavyvehicle;

import com.yaoguang.lib.base.interfaces.BasePresenter;
import com.yaoguang.lib.base.interfaces.BaseView;
import com.yaoguang.greendao.entity.driver.LoginDriver;
import com.yaoguang.greendao.entity.Driver;
import com.yaoguang.greendao.entity.driver.UserDriverCar;

import java.util.List;

/**
 * =====================================
 * 作    者: 韦理英
 * 版    本：1.2.9
 * 创建日期：2018/02/08
 * 描    述：
 * 重型半挂索引车认证MPV
 * <p>
 * <p>
 * Update : zhongjh
 * Data 2018/3/15
 * <p>
 * =====================================
 */

public interface HeavyVehicleAuthenticationContacts {

    interface View extends BaseView {

        void refreshData(List<UserDriverCar> result);

        void succeed(LoginDriver userDriverCar, int licenceType);

    }

    interface Presenter extends BasePresenter {

        void getData();

        void save(Driver driver);

        void submit(UserDriverCar driver, int licenceType);

    }
}
