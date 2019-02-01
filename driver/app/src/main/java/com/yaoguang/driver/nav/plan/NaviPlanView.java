package com.yaoguang.driver.nav.plan;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.util.SparseArray;

import com.amap.api.navi.view.RouteOverLay;

/**
 * 作者：韦理英
 * 时间： 2017/5/16 0016.
 */

public interface NaviPlanView {

    void naviPalInit(TabLayout tabLayout, ViewPager viewPager);

    void setPlan(SparseArray<RouteOverLay> routeOverlays);

    void changeRoadLine(TabLayout.Tab tab);
}
