package com.yaoguang.interfaces.driver.presenter.order;

import com.yaoguang.lib.base.interfaces.BasePresenter;
import com.yaoguang.greendao.entity.DriverOrderNode;
import com.yaoguang.greendao.entity.DriverOrderNodeList;
import com.yaoguang.greendao.entity.NodesBean;

import java.util.List;

/**
 * Created by zhongjh on 2017/5/15.
 */

public interface DOrderNodeMapPresenter extends BasePresenter {
    /**
     * 获取第一个未完成的节点
     *
     * @param mNodesBeans 节点列表
     * @return 获取第一个未完成的节点
     */
    NodesBean getNodesBeansFirst(List<NodesBean> mNodesBeans);

    NodesBean getNodesBeansNext(List<NodesBean> nodesBeans);


    String getCarNumber();

    void getNodes(String orderId);

    void switchToNode(List<DriverOrderNode> driverOrderNodes, String id, String switchToNodeId);
}
