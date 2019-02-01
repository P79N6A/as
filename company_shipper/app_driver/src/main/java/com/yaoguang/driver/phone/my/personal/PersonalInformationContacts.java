package com.yaoguang.driver.phone.my.personal;

import com.yaoguang.greendao.entity.driver.LoginDriver;
import com.yaoguang.lib.base.interfaces.BasePresenter;
import com.yaoguang.lib.base.interfaces.BaseView;

/**
 * 文件名：
 * 描    述：
 * 作    者：韦理英
 * 时    间：2017/8/11 0011.
 * 版    权：
 */
public interface PersonalInformationContacts {

    interface View extends BaseView {

        void showDriverInfo(LoginDriver result);

        /**
         * 打开个人身份认证
         *
         * @param result 用户数据源
         */
        void openRealNameAuthenticationFragment(LoginDriver result);

        /**
         * 打开驾驶技能认证
         *
         * @param result 用户数据源
         */
        void openDrivingLicenseAuthenticationFragment(LoginDriver result);

        /**
         * 打开牵引车
         *
         * @param result 用户数据源
         */
        void openHeavyVehicleAuthenticationFragment(LoginDriver result);

        /**
         * 打开半挂车
         *
         * @param result 用户数据源
         */
        void openHeavyVehicleAuthenticationFragment1(LoginDriver result);

    }

    interface Presenter extends BasePresenter {
        void showRemoteDriverInfo();

        void showLocalDriverInfo();

        void modifyNativePlace(String province, String city, String district);

        void modifyCity(String provinces, String city);

        /**
         * 重新获取个人数据，根据i打开不同的fragment
         *
         * @param i 0代表个人身份认证 1代表驾驶技能认证 2代表重型半挂牵引车 3代表重型低平板半挂车
         */
        void openDriverFragment(int i);
    }
}
