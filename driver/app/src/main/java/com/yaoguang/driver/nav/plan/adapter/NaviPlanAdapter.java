package com.yaoguang.driver.nav.plan.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.yaoguang.driver.nav.PlanFragment;
import com.yaoguang.driver.nav.plan.bean.NavPlan;

import java.util.List;

/**
 * 作者：韦理英
 * 时间： 2017/5/16 0016.
 */

public class NaviPlanAdapter extends FragmentStatePagerAdapter {
    private final List<NavPlan> list;
    private List<Fragment> fragments;

    public NaviPlanAdapter(FragmentManager fm, List<Fragment> fragments, List<NavPlan> list) {
        super(fm);

        this.fragments = fragments;
        this.list = list;
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    /**
     * 解决 FragmentPagerAdapter 无法实时刷新问题
     * 继承FragmentStatePagerAdapter
     * 并设置 getItemPosition问题解决
     * @param object
     * @return
     */
    @Override
    public int getItemPosition(Object object) {
        PlanFragment planFragment = (PlanFragment) object;
        int id = planFragment.getmNavPlan().getId();
        planFragment.update(list.get(id));
        return super.getItemPosition(object);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }
}
















