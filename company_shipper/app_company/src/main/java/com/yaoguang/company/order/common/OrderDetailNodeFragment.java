package com.yaoguang.company.order.common;

import android.os.Bundle;

import com.amap.api.maps.model.LatLng;
import com.amap.api.services.geocoder.RegeocodeResult;
import com.yaoguang.lib.common.constants.Constants;
import com.yaoguang.greendao.entity.NodesBean;

/**
 * Created by zhongjh on 2017/7/25.
 */
public class OrderDetailNodeFragment extends com.yaoguang.appcommon.phone.order.orderdetailnode.OrderDetailNodeFragment {

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
        start(OrderFeedBackListFragment.newInstance(nodesBean.getId(), 1), SINGLETOP);
    }


}
