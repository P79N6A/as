package com.yaoguang.driver.phone.order.nodeEdit;

import com.yaoguang.greendao.entity.driver.DriverNodeAddrVo;
import com.yaoguang.greendao.entity.driver.DriverOrderNodeWrapper;
import com.yaoguang.lib.base.interfaces.BaseListConditionView;
import com.yaoguang.lib.base.interfaces.BasePresenterListCondition;
import com.yaoguang.lib.base.interfaces.BaseView;

/**
 * 路线节点 关联
 * Created by zhongjh on 2018/6/7.
 */

public class NodeEditContract {

    interface Presenter extends BasePresenterListCondition<String> {

        /**
         * 更换路线
         *
         * @param nodeId         当前节点
         * @param switchToNodeId 需要互换的节点
         */
        void exchange(String nodeId, String switchToNodeId);

        /**
         * 编辑该路线
         * @param driverNodeAddrVo 路线
         */
        void update(DriverNodeAddrVo driverNodeAddrVo);

        /**
         * 修改码头
         * @param nodeId 节点id
         * @param portName 码头名称
         */
        void updatePort(String nodeId,String portName);
    }

    interface View extends BaseView, BaseListConditionView<DriverOrderNodeWrapper, String> {

        /**
         * 更换路线成功
         *
         * @param message 消息
         */
        void exchangeSuccess(String message);

        /**
         * 编辑路线/码头成功
         * @param message 消息
         */
        void detailubmitSuccess(String message);
    }

}
