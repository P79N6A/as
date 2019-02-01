package com.yaoguang.shipper.order.common;

import android.os.Bundle;

import com.yaoguang.lib.common.constants.Constants;
import com.yaoguang.greendao.entity.NodesBean;

/**
 * 装卸货 - 动态
 * Created by zhongjh on 2017/7/25.
 */
public class OrderDetailNodeFragment extends com.yaoguang.appcommon.phone.order.orderdetailnode.OrderDetailNodeFragment {

    /**
     *
     * @param driverOrderId
     * @param orderSn
     * @param loadingType 0装货 1卸货
     * @return
     */
    public static OrderDetailNodeFragment newInstance(String driverOrderId, String orderSn, String loadingType,String pcSonoId) {
        OrderDetailNodeFragment fragment = new OrderDetailNodeFragment();
        Bundle args = new Bundle();
        args.putString(DRIVER_ORDER, driverOrderId);
        args.putString(ORDERSN, orderSn);
        args.putString(LOADINGTYPE, loadingType);
        args.putString(PCSONOID,pcSonoId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected void startOrderFeedBackListFragment(NodesBean nodesBean) {
        start(OrderFeedBackListFragment.newInstance(nodesBean.getId(), 1));
    }
}
