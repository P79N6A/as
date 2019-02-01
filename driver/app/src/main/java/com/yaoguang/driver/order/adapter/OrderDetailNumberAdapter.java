package com.yaoguang.driver.order.adapter;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.ViewGroup;

import com.yaoguang.driver.order.detail.number.OrderDetailNumberFragment;
import com.yaoguang.greendao.entity.DriverBillsVo;
import com.yaoguang.greendao.entity.DriverOrderDetailVo;

import java.util.ArrayList;
import java.util.List;


/**
 * 订单号 Tab适配器
 * Created by wly on 2017/4/13.
 */
public class OrderDetailNumberAdapter extends FragmentPagerAdapter {
    private final FragmentManager fm;
    private ArrayList<String> mTitles;
    private List<DriverBillsVo> mDiverOrderAddrWrappers;
    private DriverOrderDetailVo mOrderDetailResult;
    private ArrayList<String> tabs = new ArrayList<>();

    public OrderDetailNumberAdapter(FragmentManager fm, ArrayList<String> titles, List<DriverBillsVo> diverOrderAddrWrappers, DriverOrderDetailVo orderDetailResult) {
        super(fm);
        this.fm = fm;
        mTitles = titles;
        mDiverOrderAddrWrappers = diverOrderAddrWrappers;
        mOrderDetailResult = orderDetailResult;
    }

    private static String makeFragmentName(int viewId, long id) {
        return "android:switcher:" + viewId + ":" + id;
    }

    @Override
    public Fragment getItem(int position) {
        if (position == 0) {
            return OrderDetailNumberFragment.newInstance(mDiverOrderAddrWrappers.get(position), mOrderDetailResult);
        } else {
            return OrderDetailNumberFragment.newInstance(mDiverOrderAddrWrappers.get(position), mOrderDetailResult);
        }
    }

    @NonNull
    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        tabs.add(makeFragmentName(container.getId(), getItemId(position)));
        return super.instantiateItem(container, position);
    }

    public void update() {
        for (int i = 0; i < getCount(); i++) {
            update(i);
        }
    }
    public void update(int item) {
        Fragment fragment = fm.findFragmentByTag(tabs.get(item));
        if (fragment != null) {
            switch (item) {
                case 0:
                    ((OrderDetailNumberFragment) fragment).update(mDiverOrderAddrWrappers.get(item), mOrderDetailResult);
                    break;
                case 1:
                    ((OrderDetailNumberFragment) fragment).update(mDiverOrderAddrWrappers.get(item), mOrderDetailResult);
                    break;
            }
        }
    }

    @Override
    public int getCount() {
        return mTitles.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mTitles.get(position);
    }

    public void setmDiverOrderAddrWrappers(List<DriverBillsVo> mDiverOrderAddrWrappers) {
        this.mDiverOrderAddrWrappers = mDiverOrderAddrWrappers;
    }

    public void setmOrderDetailResult(DriverOrderDetailVo mOrderDetailResult) {
        this.mOrderDetailResult = mOrderDetailResult;
    }
}
