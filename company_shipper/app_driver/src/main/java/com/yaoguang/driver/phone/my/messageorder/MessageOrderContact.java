package com.yaoguang.driver.phone.my.messageorder;

import com.yaoguang.greendao.entity.driver.DriverOrderMsgWrapper;
import com.yaoguang.lib.base.interfaces.BaseListConditionView;
import com.yaoguang.lib.base.interfaces.BasePresenterListCondition;
import com.yaoguang.lib.base.interfaces.BaseView;

import java.util.HashSet;

/**
 * Created by zhongjh on 2018/5/14.
 */
public class MessageOrderContact {

    interface View extends BaseView, BaseListConditionView<DriverOrderMsgWrapper, Integer> {

        void deleteMessageSuccess(String msg);

        void setReadSuccess(int position);

    }

    interface Presenter extends BasePresenterListCondition<Integer> {

        /**
         * 提交删除信息
         *
         * @param selectIds 消息id列表
         */
        void submitDeleteMessages(HashSet<String> selectIds);

        /**
         * 批量设置已读
         *
         * @param ids      id列表
         * @param position 消息位置
         */
        void readBatch(String ids, int position);

    }

}
