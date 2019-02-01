package com.yaoguang.driver.my.personal;

import com.yaoguang.driver.base.baseview.BasePresenter;
import com.yaoguang.driver.base.baseview.BaseView;
import com.yaoguang.driver.data.source.DriverRepository;
import com.yaoguang.greendao.LoginResult;

/**
 * 文件名：
 * 描    述：
 * 作    者：韦理英
 * 时    间：2017/8/11 0011.
 * 版    权：
 */
public interface PersonalInformationContacts {
    interface View extends BaseView {
        void showDriverInfo(LoginResult result);
    }

    abstract class Presenter extends BasePresenter<View, DriverRepository> {
        abstract void showRemoteDriverInfo();

        abstract void showLocalDriverInfo();

        abstract void modifyNativePlace(String province, String city, String district);

        abstract void modifyCity(String provinces, String city);

    }
}
