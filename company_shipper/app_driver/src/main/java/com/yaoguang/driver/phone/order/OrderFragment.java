package com.yaoguang.driver.phone.order;

import android.view.View;
import android.widget.Toast;

import com.yaoguang.appcommon.common.eventbus.OrderFragmentEvent;
import com.yaoguang.driver.phone.App;
import com.yaoguang.driver.R;
import com.yaoguang.driver.databinding.FragmentOrderDriverBinding;
import com.yaoguang.driver.phone.order.adapter.OrderAdapter;
import com.yaoguang.driver.phone.order.child.OrderChildFragment;
import com.yaoguang.lib.base.BaseApplication;
import com.yaoguang.lib.base.BaseFragmentDataBind;
import com.yaoguang.lib.base.interfaces.BasePresenter;
import com.yaoguang.lib.common.ULog;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

/**
 * 订单查询的fragment
 * Created by wly on 2017/4/13..
 *
 * update zhongjh
 * data 2018/3/15
 */
public class OrderFragment extends BaseFragmentDataBind<FragmentOrderDriverBinding> {
    public final static String FLAG_TO_TAG = "tab";

    // 再点一次退出程序时间设置
    private static final long WAIT_TIME = 2000L;
    private long TOUCH_TIME = 0;

    public OrderAdapter mOrderAdapter;

    public static OrderFragment newInstance() {
        return new OrderFragment();
    }

    @Override
    public void onDestroyView() {
        EventBus.getDefault().unregister(this);
        super.onDestroyView();
    }

    @Override
    public void init() {

        EventBus.getDefault().register(this);

        mToolbarCommonBinding.toolbarTitle.setText(getString(R.string.order));
        mToolbarCommonBinding.imgReturn.setVisibility(View.GONE);

        mDataBinding.tabLayout.addTab(mDataBinding.tabLayout.newTab().setText(R.string.order_wait));
        mDataBinding.tabLayout.addTab(mDataBinding.tabLayout.newTab().setText(R.string.order_received));
        mDataBinding.tabLayout.addTab(mDataBinding.tabLayout.newTab().setText(R.string.order_completed));
        mDataBinding.tabLayout.addTab(mDataBinding.tabLayout.newTab().setText(R.string.order_close));
        mOrderAdapter = new OrderAdapter(getChildFragmentManager());
        mDataBinding.viewPager.setAdapter(mOrderAdapter);
        mDataBinding.viewPager.setOffscreenPageLimit(3);
        mDataBinding.tabLayout.setupWithViewPager(mDataBinding.viewPager);
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_order_driver;
    }

    public void initListener() {

    }

    @Subscribe(sticky = true)
    public void event(OrderFragmentEvent event) {
        ULog.i("OrderFragment event=" + event.getType());
        switch (event.getType()) {
            case FLAG_TO_TAG:
                // 通知完成订单的一系列动作
                ((OrderChildFragment) mOrderAdapter.getFragment(1)).refreshData();  // 刷新已接单
                mDataBinding.viewPager.setCurrentItem(((Integer) event.getObject()));    // 跳到已完成
                ((OrderChildFragment) mOrderAdapter.getFragment(((Integer) event.getObject()))).refreshData();  // 刷新已完成
                break;
        }
        EventBus.getDefault().removeStickyEvent(event);
    }


    /**
     * 处理回退事件
     */
    @Override
    public boolean onBackPressedSupport() {
        if (System.currentTimeMillis() - TOUCH_TIME < WAIT_TIME) {
            App.getInstance().finishAllActivity();
        } else {
            TOUCH_TIME = System.currentTimeMillis();
            Toast.makeText(BaseApplication.getInstance(), R.string.keydownquitapp, Toast.LENGTH_SHORT).show();
        }
        return true;
    }

    @Override
    public BasePresenter getPresenter() {
        return null;
    }

}
