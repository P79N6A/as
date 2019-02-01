package com.yaoguang.driver.phone.my.my;

import android.app.Activity;
import android.content.Context;

import com.yaoguang.greendao.entity.Driver;
import com.yaoguang.greendao.entity.driver.LoginDriver;
import com.yaoguang.greendao.entity.driver.UserDriverCar;
import com.yaoguang.greendao.entity.driver.UserDriverLicence;
import com.yaoguang.lib.base.interfaces.BasePresenter;
import com.yaoguang.lib.base.interfaces.BaseView;
import com.yaoguang.greendao.entity.driver.UnreadNum;

import java.util.List;

/**
 * 我 MVP
 * Created by Administrator on 2018/1/12 0012.
 * <p>
 * update zhongjh
 * data 2018/3/15
 */

public interface MyFragmentContacts {

    interface View extends BaseView {

        /**
         * 显示用户信息
         *
         * @param driver            司机信息
         * @param carInfo           司机信息
         * @param userDriverLicence 司机信息
         */
        void showDriverInfo(Driver driver, List<UserDriverCar> carInfo, UserDriverLicence userDriverLicence);

        void setUnreadNum(UnreadNum result);

        /**
         * 打开身份证窗体或者驾驶窗体
         *
         * @param loginDriver 司机信息
         */
        void openDrivingLicenseFragment(LoginDriver loginDriver);

        /**
         * 打开索引车
         *
         * @param result 数据源
         */
        void openMotorTractorFragment(LoginDriver result);

        /**
         * 打开半挂车
         *
         * @param result 数据源
         */
        void openSemiTrailerFragment(LoginDriver result);
    }

    interface Presenter extends BasePresenter {
        void showLocalDriverInfo();

        void showRemoteDriverInfo();

        /**
         * 上传图片
         *
         * @param activity 界面
         * @param context  上下文
         * @param file     文件名称
         * @param select   枚举类型
         */
        void uploadImage(Activity activity, Context context, String file, int select);

        /**
         * 上传头像
         * @param activity 界面
         * @param context 上下文
         * @param avatarUrl 头像file
         */
        void uploadAvatar(Activity activity, Context context, String avatarUrl);

        /**
         * 上传背景
         * @param activity 界面
         * @param context 上下文
         * @param userBackgroundUrl 背景url
         */
        void uploadBackgroundUI(Activity activity, Context context, String userBackgroundUrl);

        void getUnreadNum();

        /**
         * 打开身份证窗体或者驾驶窗体
         */
        void openDrivingLicenseFragment();

        /**
         * 打开牵引车或者半挂车
         *
         * @param type 0代表牵引车 1代表半挂车
         */
        void openMotorTractorOrSemiTrailerFragment(int type);
    }

}
