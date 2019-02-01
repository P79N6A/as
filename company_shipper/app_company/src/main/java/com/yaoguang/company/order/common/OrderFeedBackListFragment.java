package com.yaoguang.company.order.common;

import android.os.Bundle;

import com.yaoguang.appcommon.phone.order.feedback.BaseOrderFeedBackListFragment;

/**
 * Created by zhongjh on 2017/7/25.
 */

public class OrderFeedBackListFragment extends BaseOrderFeedBackListFragment {

    public static OrderFeedBackListFragment newInstance(String orderId, int type) {
        OrderFeedBackListFragment fragment = new OrderFeedBackListFragment();
        Bundle bundle = new Bundle();
        bundle.putString(ORDERID, orderId);
        bundle.putInt(FLAG_TYPE, type);
        fragment.setArguments(bundle);
        return fragment;
    }


}
