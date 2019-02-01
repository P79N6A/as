package com.yaoguang.appcommon.phone.home.message.messagechild;

import com.yaoguang.greendao.entity.common.SysMsg;
import com.yaoguang.interfaces.BaseListView;
import com.yaoguang.lib.base.interfaces.BasePresenter;
import com.yaoguang.lib.base.interfaces.BaseView;

/**
 * 信息
 * Created by zhongjh on 2017/7/18.
 */
public class MessageChildContact {

    public interface MessagePresenter extends BasePresenter {

        /**
         * 刷新数据
         */
        void refreshData();

        /**
         * 获取下一页
         *
         * @param dataSize
         */
        void loadMoreData(int dataSize);

        /**
         * 获取消息
         *
         * @param dataSize
         * @param isNext
         */
        void getData(int dataSize, boolean isNext);

        /**
         * 删除消息
         *
         * @param codeType
         * @param sysMsgIds
         */
        void deleteBatch(String codeType, String sysMsgIds);

        /**
         * 设置已读
         *
         * @param codeType  类型 ConstantUtil
         * @param sysMsgIds 消息id
         */
        @Deprecated
        void updateBath(String codeType, String sysMsgIds, int position);
    }

    public interface MessageView extends BaseView, BaseListView<SysMsg> {

        /**
         * 刷新
         */
        void refreshData();

        /**
         * 加载下一页
         */
        void loadMoreData();

        /**
         * 已阅读
         *
         * @param position
         */
        void alreadyRead(int position);
    }

}
