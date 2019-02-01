package com.yaoguang.driver.my.message;

import com.yaoguang.driver.base.baseview.BaseListViewV2;
import com.yaoguang.driver.base.baseview.BasePresenter;
import com.yaoguang.driver.data.source.MessageDataSource;
import com.yaoguang.greendao.entity.MessageInfo;

/**
 * 平台公平MVP
 * Created by wly on 2018/1/12 0012.
 */

public interface PlatformMessageContacts {
    interface View extends BaseListViewV2<MessageInfo> {
        void refreshData();

        void refreshMessageRead(int position);

        void refreshMessageDelete();
    }

    abstract class Presenter extends BasePresenter<View, MessageDataSource> {
        abstract void loadMessage(int pageIndex, String noticeType, int dataSize, boolean isNextPage);

        abstract void nextPage(String noticeType, int dataSize);

        abstract void homePage(String noticeType);

        abstract void deleteMessage(String msgId);

        abstract void setMessageReadState(String id, int position);

    }
}
