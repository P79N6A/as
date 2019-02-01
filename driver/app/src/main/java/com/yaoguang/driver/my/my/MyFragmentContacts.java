package com.yaoguang.driver.my.my;

import android.content.Context;

import com.yaoguang.driver.base.baseview.BasePresenter;
import com.yaoguang.driver.base.baseview.BaseView;
import com.yaoguang.driver.data.source.MessageDataSource;
import com.yaoguang.greendao.LoginResult;
import com.yaoguang.greendao.entity.UnreadNum;

/**
 * 我 MVP
 * Created by Administrator on 2018/1/12 0012.
 */

public interface MyFragmentContacts {
    interface View extends BaseView {
        void showDriverInfo(LoginResult result);

        void setUnreadNum(UnreadNum result);
    }

    abstract class Presenter extends BasePresenter<View, MessageDataSource> {

        abstract void showLocalDriverInfo();

        abstract void showRemoteDriverInfo();

        abstract void uploadWantu(String file, int select, boolean isCompress, Context context);

        abstract void uploadAvatar(String avatarUrl, Context context);

        abstract void uploadBackgroundUI(String userBackgroundUrl, Context context);

        abstract void getUnreadNum();
    }
}
