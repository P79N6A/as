package com.yaoguang.driver.nav.plan;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.util.SparseArray;
import android.view.View;
import android.widget.TextView;

import com.amap.api.maps.AMap;
import com.amap.api.navi.model.AMapNaviPath;
import com.amap.api.navi.model.AMapNaviStep;
import com.amap.api.navi.model.AMapTrafficStatus;
import com.amap.api.navi.view.RouteOverLay;
import com.elvishew.xlog.XLog;
import com.yaoguang.common.common.ObjectUtils;
import com.yaoguang.common.common.ULog;
import com.yaoguang.common.common.UiUtils;
import com.yaoguang.driver.nav.NullFragment;
import com.yaoguang.driver.nav.PlanFragment;
import com.yaoguang.map.R;
import com.yaoguang.driver.nav.plan.adapter.NaviPlanAdapter;
import com.yaoguang.driver.nav.plan.bean.NavPlan;

import java.util.ArrayList;
import java.util.List;


/**
 * 导航UI
 * 作者：韦理英
 * 时间： 2017/5/16 0016.
 */

public class NaviPlanUtil extends BaseMapNavUtil {

    public PlanFragment mPlanFragment;
    private List<Fragment> fragments = new ArrayList<>();
    private List<NavPlan> list = new ArrayList<>();
    private int mPosition;
    TabLayout.OnTabSelectedListener onTabSelectedListener = new TabLayout.OnTabSelectedListener() {
        /**
         * tab选择颜色
         * @param tab tab
         */
        @Override
        public void onTabSelected(TabLayout.Tab tab) {
            View view = tab.getCustomView();

            view.findViewById(R.id.llTab).setBackgroundColor(UiUtils.getColor(R.color.orange500));
            // 设置标题
            TextView title = (TextView) view.findViewById(R.id.tvFanAnTitle);
            title.setTextColor(UiUtils.getColor(R.color.white));
            // 设置 时间
            TextView tvFanTime = (TextView) view.findViewById(R.id.tvFanTime);
            tvFanTime.setTextColor(UiUtils.getColor(R.color.orange500));
            // 设置公里
            TextView tvFanKm = (TextView) view.findViewById(R.id.tvFanKm);
            tvFanKm.setTextColor(UiUtils.getColor(R.color.orange500));
            // 改变线路
            changeRoadLine(tab);
            // 切换到页面
//            mViewPager.setCurrentItem(tab.getPosition());
            if (list.size() > tab.getPosition()) {
                mPlanFragment.update(list.get(tab.getPosition()));
            }
        }

        /**
         * tab未选择颜色
         * @param tab tab
         */
        @Override
        public void onTabUnselected(TabLayout.Tab tab) {
            View view = tab.getCustomView();
            view.findViewById(R.id.llTab).setBackgroundColor(UiUtils.getColor(R.color.window_background));
            // 设置标题
            TextView title = (TextView) view.findViewById(R.id.tvFanAnTitle);
            title.setTextColor(UiUtils.getColor(R.color.grey600));
            // 设置 时间
            TextView tvFanTime = (TextView) view.findViewById(R.id.tvFanTime);
            tvFanTime.setTextColor(UiUtils.getColor(R.color.black));
            // 设置公里
            TextView tvFanKm = (TextView) view.findViewById(R.id.tvFanKm);
            tvFanKm.setTextColor(UiUtils.getColor(R.color.grey600));
        }

        @Override
        public void onTabReselected(TabLayout.Tab tab) {
        }
    };
    private TabLayout mTabLayout;
    private ViewPager mViewPager;
    private NaviPlanAdapter mNaviPlanAdapter;
    private AMapNaviPath mAMapNaviPath;

    public AMapNaviPath getmAMapNaviPath() {
        return mAMapNaviPath;
    }

    public void setmAMapNaviPath(AMapNaviPath mAMapNaviPath) {
        this.mAMapNaviPath = mAMapNaviPath;
    }

    /**
     * 路线的红绿灯数的计算
     *
     * @param path
     * @return
     */

    public int getTrafficNumber(AMapNaviPath path) {
        int trafficLightNumber = 0;
        if (path == null) {
            return trafficLightNumber;
        }
        List<AMapNaviStep> steps = path.getSteps();
        for (AMapNaviStep step : steps) {
            trafficLightNumber += step.getTrafficLightNumber();
        }
        return trafficLightNumber;
    }

    public void init(Context context, View view, FragmentManager childFragmentManager, AMap aMap) {
        mContext = context;
        mTabLayout = (TabLayout) view.findViewById(R.id.tabLayout);
        mViewPager = (ViewPager) view.findViewById(R.id.viewPager);
        mTabLayout.setSmoothScrollingEnabled(true);
        mTabLayout.setSelectedTabIndicatorHeight(10);
        mNaviPlanAdapter = new NaviPlanAdapter(childFragmentManager, fragments, list);
        mViewPager.setAdapter(mNaviPlanAdapter);
        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(mTabLayout));
        mTabLayout.addOnTabSelectedListener(onTabSelectedListener);
        mTabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        mPlanFragment = new PlanFragment();
        childFragmentManager.beginTransaction().replace(R.id.flPlanFragment, mPlanFragment, "planFragment").show(mPlanFragment).commit();
        init(context, aMap);
    }

    public void setSchemeUI(SparseArray<RouteOverLay> routeOverlays) {
        list.clear();
        fragments.clear();
        mTabLayout.removeAllTabs();
        ULog.i(""+routeOverlays.size());
        for (int i = 0; i < routeOverlays.size(); i++) {
            int key = routeOverlays.keyAt(i);
            RouteOverLay routeOverLay = routeOverlays.get(key);
            AMapNaviPath aMapNaviPath = routeOverLay.getAMapNaviPath();

            setPlanUI(aMapNaviPath, i);
        }
        mPlanFragment.update(list.get(0));
        mNaviPlanAdapter.notifyDataSetChanged();
//        // TODO: 2017/10/16 0016 这里有错误
        changeRoute(0); // 默认为0
    }

    /**
     * 描    述：设置 tab ui和数据
     * 作    者：韦理英
     * 时    间：
     * @param aMapNaviPath 数据
     * @param i i
     */
    private void setPlanUI(AMapNaviPath aMapNaviPath, int i) {
        String lables = aMapNaviPath.getLabels().split(",")[0];  // 方案
        String allLength = ObjectUtils.FORMAT_NUMBER2.format(aMapNaviPath.getAllLength() / 1000f);  // 公里
        int tollCost = aMapNaviPath.getTollCost(); //花费
        int trafficNumber = getTrafficNumber(aMapNaviPath);// 红绿色数量
        String blockKm = getBlockKm(aMapNaviPath);// 阻塞公里

        String planName = ObjectUtils.formatMinute2(aMapNaviPath.getAllTime()) + "\n" + allLength + "公里";

        String planDsc = "";
        // 过路费
        if (tollCost != 0)
            planDsc = +tollCost + "元过路费  ";
        // 红绿灯
        if (trafficNumber != 0)
            planDsc = planDsc + trafficNumber + "个红绿灯\n";
        // 阻塞公里
        if (blockKm != null) {
            planDsc += blockKm;
        }

        NavPlan navPlan = new NavPlan();
        navPlan.setId(i);
        navPlan.setName(planName);
        navPlan.setDsc(planDsc);

        mAMapNaviPath = aMapNaviPath;

        // 新建一个tab
        TabLayout.Tab tab = mTabLayout.newTab();
        // 导入view
        View view = View.inflate(mContext, R.layout.view_fanan, null);
        // 设置tab view
        tab.setCustomView(view);
        // 设置title
        TextView tvFanAnTitle = (TextView) view.findViewById(R.id.tvFanAnTitle);
        tvFanAnTitle.setText(getTitle(i));
        // 设置 时间
        TextView tvFanTime = (TextView) view.findViewById(R.id.tvFanTime);
        tvFanTime.setText(ObjectUtils.formatMinute2(aMapNaviPath.getAllTime()));
        // 设置公里
        TextView tvFanKm = (TextView) view.findViewById(R.id.tvFanKm);
        tvFanKm.setText(allLength + "公里");

        mTabLayout.addTab(tab);
        list.add(navPlan);
        fragments.add(NullFragment.newInstance());
    }

    /**
     * 描    述：获取交通阻塞公里
     * 作    者：韦理英
     * 时    间：
     *
     * @param aMapNaviPath [参数说明]
     *                     return [return说明]
     */
    @Nullable
    private String getBlockKm(AMapNaviPath aMapNaviPath) {
        // 获取交通实体
        List<AMapTrafficStatus> trafficStatuses = aMapNaviPath.getTrafficStatuses();
        // 获取长度
        int traLen = 0;
        for (AMapTrafficStatus trafficStatuse : trafficStatuses) {
            if (trafficStatuse.getStatus() == 3 || trafficStatuse.getStatus() == 4) {
                traLen += trafficStatuse.getLength();
            }
        }
        traLen = (traLen / 1000);
        if (traLen > 0) {
            return traLen + "公里拥堵";
        }
        return null;
    }

    @NonNull
    private String getTitle(int i) {
        String title = "";
        switch (i) {
            case 0:
                title = "方案一";
                break;
            case 1:
                title = "方案二";
                break;
            case 2:
                title = "方案三";
                break;
            case 3:
                title = "方案四";
                break;
            default:
                title = "更多方案";
                break;
        }
        return title;
    }

    public void changeRoadLine(TabLayout.Tab tab) {
        if (mPosition != tab.getPosition()) {
            ULog.i("onTabSelected" + tab.getPosition());
            mPosition = tab.getPosition();
            changeRoute(mPosition);
        }
    }

}
