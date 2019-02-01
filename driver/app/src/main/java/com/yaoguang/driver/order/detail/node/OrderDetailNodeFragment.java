package com.yaoguang.driver.order.detail.node;

import android.os.Bundle;

import com.yaoguang.appcommon.order.AbstractOrderDetailNodeFragment;
import com.yaoguang.common.common.Constants;
import com.yaoguang.driver.App;
import com.yaoguang.driver.order.feedback.OrderFeedBackListFragment;
import com.yaoguang.greendao.entity.NodesBean;

/**
 * 韦理英
 * 订单明细的节点
 * Created by wly on 2017/5/15.
 */
public class OrderDetailNodeFragment extends AbstractOrderDetailNodeFragment {

    /**
     * 实例化
     *
     * @param driverOrderId 订单id
     * @return 实例化
     */
    public static OrderDetailNodeFragment newInstance(String driverOrderId) {
        OrderDetailNodeFragment fragment = new OrderDetailNodeFragment();
        Bundle args = new Bundle();
        args.putString(DRIVER_ORDER, driverOrderId);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public String getType() {
        return Constants.APP_DRIVER;
    }

    @Override
    protected void updateLocation() {
        App.locationInit();
    }

    @Override
    protected void startOrderFeedBackListFragment(NodesBean nodesBean) {
        start(OrderFeedBackListFragment.newInstance(nodesBean.getId(), 1, false));
    }
}
