package com.yaoguang.appcommon.phone.node;


import com.yaoguang.greendao.entity.driver.DriverOrderMergeNodeWrapper;
import com.yaoguang.greendao.entity.driver.DriverOrderNodeList;
import com.yaoguang.lib.base.interfaces.BaseListConditionView;
import com.yaoguang.lib.base.interfaces.BasePresenterListCondition;
import com.yaoguang.lib.base.interfaces.BaseView;

import java.util.ArrayList;

/**
 * @author zhongjh
 * @Package com.yaoguang.driver.phone.order2.node
 * @Description: 节点列表 关联接口
 * @date 2018/04/27
 */
public interface NodeContract {

    interface Presenter extends BasePresenterListCondition<String[]> {

        /**
         * 完成节点
         *
         * @param driverOrderNodeList 节点的数据源
         */
        void finish(ArrayList<DriverOrderNodeList> driverOrderNodeList);
    }

    interface View extends BaseView, BaseListConditionView<DriverOrderMergeNodeWrapper, String[]> {

        /**
         * 刷新底部view
         *
         * @param orderStatus 状态
         * @param clientId    公司id
         */
        void refreshingFootView(int orderStatus, String clientId);

        /**
         * 获取当前地址
         */
        void getCurrentAddress();

        /**
         * 提交节点
         */
        void finishBatch();
    }

}
