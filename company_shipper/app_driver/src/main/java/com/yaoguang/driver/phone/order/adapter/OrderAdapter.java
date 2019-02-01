package com.yaoguang.driver.phone.order.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import com.yaoguang.lib.adapter.FragmentPagerAdapterCompat;
import com.yaoguang.driver.phone.order.child.OrderChildFragment;

import static com.yaoguang.driver.phone.order.child.OrderChildPresenter.ACCEPT;
import static com.yaoguang.driver.phone.order.child.OrderChildPresenter.FINISH;
import static com.yaoguang.driver.phone.order.child.OrderChildPresenter.REFUSE;
import static com.yaoguang.driver.phone.order.child.OrderChildPresenter.WAIT;


/**
 * 订单查询Tab适配器
 * Created by wly on 2017/4/13.
 */
public class OrderAdapter extends FragmentPagerAdapterCompat {

    private String[] mTitles = new String[]{"待接单", "已接单", "已完成", "已关闭"};

    public OrderAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return OrderChildFragment.newInstance(WAIT);
            case 1:
                return OrderChildFragment.newInstance(ACCEPT);
            case 2:
                return OrderChildFragment.newInstance(FINISH);
            case 3:
                return OrderChildFragment.newInstance(REFUSE);
            default:
                return OrderChildFragment.newInstance(ACCEPT);
        }
    }

    @Override
    public int getCount() {
        return mTitles.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mTitles[position];
    }
}
