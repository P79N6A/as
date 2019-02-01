package com.yaoguang.driver.order.feedback;

import android.os.Bundle;

import com.yaoguang.appcommon.order.AbstractOrderFeedBackListFragment;

/**
 * 订单反馈列表
 * Created by wly on 2017/5/17.
 */
public class OrderFeedBackListFragment extends AbstractOrderFeedBackListFragment {



    public static OrderFeedBackListFragment newInstance(String orderId, int type, boolean isShowLoadAlert) {
        OrderFeedBackListFragment fragment = new OrderFeedBackListFragment();
        Bundle bundle = new Bundle();
        bundle.putString(ORDERID, orderId);
        bundle.putBoolean(IS_SHOW_LOAD_ALERT, isShowLoadAlert);
        bundle.putInt(FLAG_TYPE, type);
        fragment.setArguments(bundle);
        return fragment;
    }
}
