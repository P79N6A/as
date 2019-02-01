package com.yaoguang.driver.order;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.Toast;

import com.yaoguang.appcommon.common.eventbus.OrderFragmentEvent;
import com.yaoguang.common.common.ULog;
import com.yaoguang.driver.App;
import com.yaoguang.driver.R;
import com.yaoguang.driver.base.DataBindingFragment;
import com.yaoguang.driver.base.baseview.BasePresenter;
import com.yaoguang.driver.databinding.FragmentOrder2Binding;
import com.yaoguang.driver.order.adapter.OrderAdapter;
import com.yaoguang.driver.order.child.OrderChildFragment;
import com.yaoguang.interfaces.driver.view.order.DOrderView;
import com.yaoguang.lib.annotation.aspect.BackKey;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

/**
 * 订单查询的fragment
 * Created by wly on 2017/4/13.
 */
public class OrderFragment extends DataBindingFragment<BasePresenter, FragmentOrder2Binding> implements DOrderView {
    public final static String FLAG_TO_TAG = "tab";
    public OrderAdapter mOrderAdapter;
    public ViewPager viewPager;

    // 再点一次退出程序时间设置
    private static final long WAIT_TIME = 2000L;
    private long TOUCH_TIME = 0;

    public static OrderFragment newInstance() {
        return new OrderFragment();
    }

    @Override
    protected void initView(View view) {
        if (mToolbarCommonBinding == null) return;

        mToolbarCommonBinding.toolbarTitle.setText(getString(R.string.order));
        mToolbarCommonBinding.imgReturn.setVisibility(View.GONE);
        viewPager = mDataBinding.viewPager;
        
        mDataBinding.tabLayout.addTab(mDataBinding.tabLayout.newTab().setText(R.string.order_wait));
        mDataBinding.tabLayout.addTab(mDataBinding.tabLayout.newTab().setText(R.string.order_received));
        mDataBinding.tabLayout.addTab(mDataBinding.tabLayout.newTab().setText(R.string.order_completed));
        mDataBinding.tabLayout.addTab(mDataBinding.tabLayout.newTab().setText(R.string.order_close));
        mOrderAdapter = new OrderAdapter(getChildFragmentManager());
        viewPager.setAdapter(mOrderAdapter);
        viewPager.setOffscreenPageLimit(3);
        mDataBinding.tabLayout.setupWithViewPager(viewPager);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_order2;
    }

    protected void initListener() {
        //  无网络，无数据时，点击刷新数据，防止出现【无订单,网络错误】状态页面
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                tabRefresh(position);

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    /**
     * 每次点击tab，就刷新数据
     *
     * @param position tab页
     */
    private void tabRefresh(final int position) {
        if (mOrderAdapter == null || mOrderAdapter.getFragment(position) == null) return;
        // 延时解决卡顿问题
        App.handler.postDelayed(() -> {
            // 每次点击tab，就刷新数据
            ((OrderChildFragment) mOrderAdapter.getFragment(position)).setShowRefresh(false);
            ((OrderChildFragment) mOrderAdapter.getFragment(position)).refreshData();  // 刷新
        }, 500);

        // 延时设置可以手动刷新
        App.handler.postDelayed(() -> {
            // 设置可以手动刷新动画
            ((OrderChildFragment) mOrderAdapter.getFragment(position)).setShowRefresh(true);
            ((OrderChildFragment) mOrderAdapter.getFragment(position)).finishRefreshing();

        }, 1000);
    }


    @Subscribe(sticky = true)
    public void event(OrderFragmentEvent event) {
        ULog.i("OrderFragment event=" + event.getType());
        switch (event.getType()) {
            case FLAG_TO_TAG:
                ((OrderChildFragment) mOrderAdapter.getFragment(1)).refreshData();  // 刷新待接单
                viewPager.setCurrentItem(((Integer) event.getObject()));    // 跳到已完成
                ((OrderChildFragment) mOrderAdapter.getFragment(((Integer) event.getObject()))).refreshData();  // 刷新已完成
                break;
        }
        EventBus.getDefault().removeStickyEvent(event);
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onResume() {
        super.onResume();
    }


    /**
     * 处理回退事件
     */
    @Override
    @BackKey
    public boolean onBackPressedSupport() {
        if (System.currentTimeMillis() - TOUCH_TIME < WAIT_TIME) {
            App.getInstance().finishAllActivity();
        } else {
            TOUCH_TIME = System.currentTimeMillis();
            Toast.makeText(App.getInstance(), R.string.keydownquitapp, Toast.LENGTH_SHORT).show();
        }
        return true;
    }

    @Override
    public void initView() {

    }
}
