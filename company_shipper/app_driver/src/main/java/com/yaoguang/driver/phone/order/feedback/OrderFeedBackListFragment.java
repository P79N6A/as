package com.yaoguang.driver.phone.order.feedback;

import android.os.Bundle;

import com.yaoguang.appcommon.phone.order.feedback.BaseOrderFeedBackListFragment;

/**
 * 订单反馈列表
 * Created by wly on 2017/5/17.
 */
public class OrderFeedBackListFragment extends BaseOrderFeedBackListFragment {


    /**
     *
     * @param orderId 订单id
     * @param type 0 故障反馈列表 1 动态记录
     */
    public static OrderFeedBackListFragment newInstance(String orderId, int type) {
        OrderFeedBackListFragment fragment = new OrderFeedBackListFragment();
        Bundle bundle = new Bundle();
        bundle.putString(ORDERID, orderId);
        bundle.putInt(FLAG_TYPE, type);
        fragment.setArguments(bundle);
        return fragment;
    }


}
