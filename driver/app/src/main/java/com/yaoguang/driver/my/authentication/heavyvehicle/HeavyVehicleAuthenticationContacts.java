package com.yaoguang.driver.my.authentication.heavyvehicle;

import com.yaoguang.driver.base.baseview.BasePresenter;
import com.yaoguang.driver.base.baseview.BaseView;
import com.yaoguang.driver.data.source.DriverRepository;
import com.yaoguang.greendao.LoginResult;
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
 * =====================================
 */

public interface HeavyVehicleAuthenticationContacts {

    interface View extends BaseView {
        void refreshData(List<UserDriverCar> result);

        void succeed(LoginResult userDriverCar, int licenceType);
    }

    abstract class Presenter extends BasePresenter<View, DriverRepository> {
        abstract void getData();
        abstract void save(Driver driver);
        abstract void submit(UserDriverCar driver, int licenceType);
    }
}
