package com.yaoguang.company.businessorder2.common.businessmain.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import com.yaoguang.company.businessorder2.forwarder.businessmain.business.BusinessOrderFragment;
import com.yaoguang.company.businessorder2.common.businessmain.dynamic.DynamicFragment;
import com.yaoguang.greendao.entity.common.TruckBills;
import com.yaoguang.greendao.entity.common.ViewForwardOrder;
import com.yaoguang.lib.adapter.FragmentPagerAdapterCompat;

/**
 * 基本信息、动态适配器
 * Created by zjh on 2017/4/13.
 */
public class BaseBusinessMainAdapter<T> extends FragmentPagerAdapterCompat {

    private T t;
    private String mID;
    private int mType;
    private String mServiceType;
    private String mOtherService;
    private String[] mTitles = new String[]{"基本信息", "动态"};
    private int mCount;

    public BusinessOrderFragment mBusinessOrderFragment;
    public com.yaoguang.company.businessorder2.truck.businessmain.business.BusinessOrderFragment mBusinessOrderFragmentTruck;

    /**
     * @param fm           frament管理器
     * @param type         类型:添加、编辑、模板
     * @param serviceType  0 - 货代，1 - 拖车
     * @param otherService 装箱，拆箱
     * @param t            数据源
     * @param count        tab数量
     */
    public BaseBusinessMainAdapter(FragmentManager fm, int type, String serviceType, String otherService, T t, int count) {
        super(fm);
        mCount = count;
        this.t = t;
        mType = type;
        if (mCount == 1) {
            mTitles = new String[]{"基本信息"};
        } else if (mCount == 2) {
            mTitles = new String[]{"基本信息", "动态"};
        }
        this.mServiceType = serviceType;
        this.mOtherService = otherService;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                if (mServiceType.equals("0")) {
                    mBusinessOrderFragment = BusinessOrderFragment.newInstance(mType, mOtherService);
                    if (t != null)
                        mBusinessOrderFragment.setViewOrder((ViewForwardOrder) t);
                    return mBusinessOrderFragment;
                } else {
                    // 拖车
                    mBusinessOrderFragmentTruck = com.yaoguang.company.businessorder2.truck.businessmain.business.BusinessOrderFragment.newInstance(mType, mOtherService);
                    if (t != null)
                        mBusinessOrderFragmentTruck.setViewOrder((TruckBills) t);
                    return mBusinessOrderFragmentTruck;
                }
            case 1:
                return DynamicFragment.newInstance();
            default:
                return null;
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
