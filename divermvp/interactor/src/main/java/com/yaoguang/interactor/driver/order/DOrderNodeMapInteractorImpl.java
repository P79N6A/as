package com.yaoguang.interactor.driver.order;

import com.yaoguang.lib.net.bean.BaseResponse;
import com.yaoguang.greendao.entity.NodesBean;
import com.yaoguang.interactor.common.DCSBaseInteractorImpl;
import com.yaoguang.interfaces.driver.interactor.order.DOrderNodeMapInteractor;
import com.yaoguang.lib.net.Api;
import com.yaoguang.datasource.api.OrderApi;

import java.util.List;

import io.reactivex.Observable;

/**
 * Created by zhongjh on 2017/5/15.
 */
public class DOrderNodeMapInteractorImpl extends DCSBaseInteractorImpl implements DOrderNodeMapInteractor {

    @Override
    public NodesBean getNodesBeansFirst(List<NodesBean> nodesBeans) {
        NodesBean nodesBean = null;
        int nodesBeanListSize = nodesBeans.size();

        for (int i = 0; i < nodesBeanListSize; i++) {
            if (nodesBeans.get(i).getIsValid() != 1) {
                nodesBean = nodesBeans.get(i);
                break;
            }
        }
        return nodesBean;
    }

    @Override
    public NodesBean getNodesBeansNext(List<NodesBean> nodesBeans) {
        NodesBean nodesBean = null;
        int nodesBeanListSize = nodesBeans.size();

        for (int i = 0; i < nodesBeanListSize; i++) {
            if (nodesBeans.get(i).getIsValid() != 1) {
                int len = i + 1;
                if (nodesBeanListSize > len) {
                    nodesBean = nodesBeans.get(i);
                }
                break;
            }
        }
        return nodesBean;
    }

    @Override
    public Observable<BaseResponse<String>> switchToNode(String id, String switchToNodeId) {
       return Api.getInstance().retrofit.create(OrderApi.class).switchToNode(id, switchToNodeId);
    }

}
