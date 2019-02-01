package com.yaoguang.interactor.driver.my;

import android.content.Context;

import com.yaoguang.lib.base.interfaces.BasePresenter;
import com.yaoguang.lib.base.interfaces.BaseView;
import com.yaoguang.greendao.entity.driver.UnreadNum;

/**
 * Created by 韦理英
 * on 2017/4/24 0024.
 */

public interface DMyContacts {

    interface View extends BaseView {
        void showDriverInfo(String photo, String mobile, String dsc, String email, String nativePlace, String liveCity, String name, String backgroundPicture);

        void showProgress();

        void setProgress(long max, long i);

        void hideProgress();

        void setUnreadNum(UnreadNum result);
    }


    interface Presenter extends BasePresenter {

        void showLocalDriverInfo();

        void uploadWantu(String file, int select, boolean isCompress, Context context);

        void uploadAvatar(String avatarUrl, Context context);

        void uploadBackgroundUI(String userBackgroundUrl, Context context);

        void getUnreadNum();
    }
}
