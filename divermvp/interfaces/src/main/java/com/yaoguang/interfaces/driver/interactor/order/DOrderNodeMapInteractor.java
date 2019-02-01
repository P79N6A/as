package com.yaoguang.interfaces.driver.interactor.order;

import com.yaoguang.lib.net.bean.BaseResponse;
import com.yaoguang.greendao.entity.DriverOrderNodeList;
import com.yaoguang.greendao.entity.NodesBean;

import java.util.List;

import io.reactivex.Observable;

/**
 * Created by zhongjh on 2017/5/15.
 */

public interface DOrderNodeMapInteractor {

    /**
     * 获取第一个未完成的节点
     *
     * @param nodesBeans 节点列表
     * @return 获取第一个未完成的节点
     */
    NodesBean getNodesBeansFirst(List<NodesBean> nodesBeans);

    NodesBean getNodesBeansNext(List<NodesBean> nodesBeans);

    Observable<BaseResponse<String>> switchToNode(String id, String switchToNodeId);
}
