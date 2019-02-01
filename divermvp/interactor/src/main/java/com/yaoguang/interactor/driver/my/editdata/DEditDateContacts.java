package com.yaoguang.interactor.driver.my.editdata;

import com.yaoguang.lib.base.interfaces.BasePresenter;
import com.yaoguang.lib.base.interfaces.BaseView;

/**
 * 文件名：
 * 描    述：
 * 作    者：韦理英
 * 时    间：2017/8/11 0011.
 * 版    权：
 */
public interface DEditDateContacts {
    interface View extends BaseView {
        void showDriverInfo(String photo, String mobile, String dsc, String email, String nativePlace, String liveCity, String name, String backgroundPicture);
    }

    interface Presenter extends BasePresenter {
        void showLocalDriverInfo();

        void modifyNativePlace(String province, String city);

        void modifyCity(String provinces, String city);

    }
}
