package com.yaoguang.driver.phone.my.message;

import com.yaoguang.greendao.entity.common.SysMsg;
import com.yaoguang.lib.base.interfaces.BaseListConditionView;
import com.yaoguang.lib.base.interfaces.BasePresenter;
import com.yaoguang.lib.base.interfaces.BasePresenterListCondition;
import com.yaoguang.lib.base.interfaces.BaseView;

/**
 * 平台公平MVP
 * Created by wly on 2018/1/12 0012.
 * <p>
 * update zhongjh
 * data 2018/3/15
 */

public interface PlatformMessageContacts {

    interface View extends BaseView, BaseListConditionView<SysMsg, String> {

        /**
         * 设置列表已读
         *
         * @param position 位置
         */
        void refreshMessageRead(int position);

        /**
         * 删除列表消息
         */
        void refreshMessageDelete();

    }

    interface Presenter extends BasePresenter, BasePresenterListCondition<String> {

        /**
         * 删除消息
         *
         * @param msgId 消息id
         */
        void deleteMessage(String msgId);

        /**
         * 设置已读
         *
         * @param id       id
         * @param position 索引
         */
        void setMessageReadState(String id, int position);

    }
}
