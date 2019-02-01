package com.yaoguang.driver.nav;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.amap.api.navi.AMapNavi;
import com.amap.api.navi.model.AMapNaviGuide;
import com.amap.api.navi.model.AMapNaviPath;
import com.amap.api.navi.model.AMapNaviStep;
import com.yaoguang.common.appcommon.dialog.CommonDialog;
import com.yaoguang.common.appcommon.dialog.DialogHelper;
import com.yaoguang.common.base.BaseFragment;
import com.yaoguang.common.base.interfaces.BasePresenter;
import com.yaoguang.common.common.AppClickUtil;
import com.yaoguang.common.common.GpsUtils;
import com.yaoguang.common.common.ULog;
import com.yaoguang.common.treerecyclerview.adpater.TreeRecyclerAdapter;
import com.yaoguang.common.treerecyclerview.factory.ItemHelperFactory;
import com.yaoguang.common.treerecyclerview.item.TreeItem;
import com.yaoguang.driver.nav.bean.RoadInfo;
import com.yaoguang.driver.nav.plan.bean.NavPlan;
import com.yaoguang.map.R;

import java.util.ArrayList;
import java.util.List;


/**
 * 作者：韦理英
 * 时间： 2017/5/16 0016.
 */

public class PlanFragment extends BaseFragment implements DPlanFragmentView {
    InitialView mInitialView;
    private NavPlan mNavPlan;
    private List<RoadInfo> data = new ArrayList<>();


    public static Fragment getInstance(NavPlan s) {
        PlanFragment fragment = new PlanFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("content", s);
        fragment.setArguments(bundle);
        return fragment;
    }

    /**
     * 描    述：处理路线数据
     * 作    者：韦理英
     * 时    间：
     */

    protected void handlerPlanData() {
        try {
            data.clear();
            AMapNavi aMapNavi = AMapNavi.getInstance(getContext());
            //概览
            List<AMapNaviGuide> guides = aMapNavi.getNaviGuideList();
            //详情
            AMapNaviPath mAMapNaviPath = aMapNavi.getNaviPath();
            List<AMapNaviStep> steps = mAMapNaviPath.getSteps();

//            // 起点
            RoadInfo start = new RoadInfo();
            // 路线名
            start.setParentName("我的位置");
            // 路线IconType
            start.setParentIconType(99);
            // 路线长
            start.setParentLeng(0);
            // 红绿灯个数
            start.setTrafficLightNumber(0);
            // 添加起点
            data.add(start);
            for (int i = 0; i < guides.size(); i++) {
//                ULog.i(mNavPlan.getName() + "=============添加" + i);
                RoadInfo naviInfo = new RoadInfo();
                //guide step相生相惜，指的是大导航段
                AMapNaviGuide guide = guides.get(i);
//                ULog.i("AMapNaviGuide 路线经纬度:" + guide.getCoord() + "");
//                ULog.i("AMapNaviGuide 路线名:" + guide.getName() + "");
//                ULog.i("AMapNaviGuide 路线长:" + guide.getLength() + "m");
//                ULog.i("AMapNaviGuide 路线耗时:" + guide.getTime() + "s");
//                ULog.i("AMapNaviGuide 路线IconType" + guide.getIconType() + "");
                AMapNaviStep step = steps.get(i);
//                ULog.i("AMapNaviStep 距离:" + step.getLength() + "m" + " " + "耗时:" + step.getTime() + "s");
//                ULog.i("AMapNaviStep 红绿灯个数:" + step.getTrafficLightNumber());

                // 路线名
                naviInfo.setParentName(guide.getName());
                // 路线IconType
                int iconType = guide.getIconType();
                try {
                    if (guide.getIconType() == 0) { // 高德直线为0,图片名action9是直线
                        iconType = 9;
                    } else if (guide.getIconType() > 16) {
                        iconType = Integer.parseInt(String.valueOf(guide.getIconType()).substring(0, 1));
                    }
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }
                naviInfo.setParentIconType(iconType);
                // 路线长
                naviInfo.setParentLeng(guide.getLength());
                // 红绿灯个数
                naviInfo.setTrafficLightNumber(step.getTrafficLightNumber());


                //link指的是大导航段中的小导航段
//                List<AMapNaviLink> links = step.getLinks();
//                for (int i1 = 0; i1 < links.size(); i1++) {
//                    AMapNaviLink link = links.get(i1);
//                    //          请看com.amap.api.navi.enums.RoadClass，以及帮助文档
//                    ULog.i("AMapNaviLink 道路名:" + link.getRoadName() + " " + "道路等级:" + link.getRoadClass());
//                    //          请看com.amap.api.navi.enums.RoadType，以及帮助文档
//                    ULog.i("AMapNaviLink 道路类型:" + link.getRoadType());
//                    ULog.i("AMapNaviLink 路线IconType" + guide.getIconType());
//                    ULog.i("AMapNaviLink 路线长:" + link.getLength());
//
//                    Line line = new Line();
//
//                    // 道路名
//                    line.setLineName(link.getRoadName());
//                    // 下一条路名
//                    if (links.size() -1 > i1) {
//                        line.setLineNextName(links.get(i1 + 1).getRoadName());
//                    }
//                    // 路线长
//                    line.setLineLeng(guide.getLength());
//                    // 道路类型
//                    line.setRoadType(link.getRoadType());
//                    // 路线IconType
//                    line.setLineIconType(guide.getIconType());
//
//                    naviInfo.getLines().add(line);
//                }
                data.add(naviInfo);
            }

            // 起点
            RoadInfo end = new RoadInfo();
            // 路线名
            end.setParentName("到达终点");
            // 路线IconType
            end.setParentIconType(100);
            // 路线长
            end.setParentLeng(0);
            // 红绿灯个数
            end.setTrafficLightNumber(0);
            // 添加终点
            data.add(end);
        } catch (Exception e) {
            e.printStackTrace();
            ULog.i(e.getMessage());
        }
    }

    /**
     * 描    述：更新
     * 作    者：韦理英
     * 时    间：
     *
     * @param navPlan [参数说明] 红绿灯等等信息
     */

    public void update(@NonNull NavPlan navPlan) {
        mNavPlan = navPlan;
        // 更新信息
        if (mInitialView == null) {
            ULog.i("update mInitialView is null");
            return;
        }

        // 处理线路详情数据
        handlerPlanData();
        List<TreeItem> treeItemList = ItemHelperFactory.createTreeItemList(data, RoadInfoItemParent.class, null);
        mInitialView.treeRecyclerAdapter.setDatas(treeItemList);
        // 显示线路详情
        mInitialView.treeRecyclerAdapter.notifyDataSetChanged();
        // 显示红绿灯等信息
        mInitialView.dsc.setText(navPlan.getDsc());
    }

    /**
     * 获取nav信息
     *
     * @return NavPlan
     */
    public NavPlan getmNavPlan() {
        return mNavPlan;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_plan, container, false);
        mInitialView = new InitialView(view);
        // 不显示导航UI
        isShowNavUi(false);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    /**
     * 启用导航
     * @param activity activity
     * @param isRealNav true 真实导航 false模拟导航
     */
    public static void startNavi(final Activity activity, boolean isRealNav) {
        if (!GpsUtils.isOPen(activity)) {
            final DialogHelper mDialogHelper = new DialogHelper();
            mDialogHelper.showConfirmDialog(activity, "GPS", "你没有打开GPS，请先打开GPS", "去打开GPS", new CommonDialog.Listener() {
                @Override
                public void ok() {
                    GpsUtils.openGPS(activity);
                    mDialogHelper.hideDialog();
                }

                @Override
                public void cancel() {
                    mDialogHelper.hideDialog();
                }
            });
            return;
        }
        Intent gpsintent = new Intent(activity, RouteNaviActivity.class);
        gpsintent.putExtra("gps", isRealNav);
        activity.startActivity(gpsintent);
    }

    /**
     * 描    述：是否显示底部导航栏
     * 作    者：韦理英
     * 时    间：
     *
     * @param isShow true显示 false不显示
     */

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public void isShowBottomNav(final boolean isShow) {
        // 判断是否有数据，有数据才能操作
        if (mInitialView == null || mInitialView.treeRecyclerAdapter.getDatas().isEmpty() || getView() == null) return;
        mInitialView.llBottomNav.setVisibility(isShow ? View.VISIBLE : View.GONE);
        mInitialView.startNav.setVisibility(!isShow ? View.VISIBLE : View.GONE);
        // 获取所有点击焦点
        getView().setOnTouchListener((v, event) -> isShow);
    }

    /**
     * 描    述： 底部导航栏
     * 作    者：韦理英
     * 时    间：
     *
     * @param isShow true显示 false不显示
     */

    @Override
    public void isAllShowBottomNav(boolean isShow) {
        if (mInitialView == null) return;
        mInitialView.llBottomNav.setVisibility(isShow ? View.VISIBLE : View.GONE);
        mInitialView.startNav.setVisibility(isShow ? View.VISIBLE : View.GONE);
    }

    /**
     * 描    述：是否显示导航UI
     * 作    者：韦理英
     * 时    间：
     * @param isShow true显示 false不显示
     */

    @Override
    public void isShowNavUi(boolean isShow) {
        if (mInitialView == null) return;
        mInitialView.llBottomNav.setVisibility(isShow ? View.VISIBLE : View.GONE);
        mInitialView.startNav.setVisibility(isShow ? View.VISIBLE : View.GONE);
    }

    @Override
    public BasePresenter getPresenter() {
        return null;
    }

    public final class InitialView {
        private TextView dsc;
        private Button startNav;
        private Button startNav2;
        private TextView startMoNiNav;
        private RecyclerView recyclerView;
        private LinearLayout llBottomNav;
        private TreeRecyclerAdapter treeRecyclerAdapter;

        public InitialView(View view) {


            dsc = view.findViewById(R.id.dsc);
            startNav = view.findViewById(R.id.startNav);
            startNav2 = view.findViewById(R.id.startNav2);
            startMoNiNav = view.findViewById(R.id.startMoNiNav);
            recyclerView = view.findViewById(R.id.recyclerView);
            llBottomNav = view.findViewById(R.id.llBottomNav);


            startNav.setOnClickListener(v -> {
                if (AppClickUtil.isDuplicateClick()) return;

                startNavi(getActivity(), true);
            });
            startNav2.setOnClickListener(v -> startNav.performClick());
            startMoNiNav.setOnClickListener(v -> {
                if (AppClickUtil.isDuplicateClick()) return;

                startNavi(getActivity(), false);
            });

            recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 6));
            recyclerView.setItemAnimator(new DefaultItemAnimator());
            recyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
                @Override
                public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                    super.getItemOffsets(outRect, view, parent, state);
                    outRect.top = 10;
                    if (view.getLayoutParams() instanceof GridLayoutManager.LayoutParams) {
                        GridLayoutManager.LayoutParams layoutParams = (GridLayoutManager.LayoutParams) view.getLayoutParams();
                        int spanIndex = layoutParams.getSpanIndex();//在一行中所在的角标，第几列
                        if (spanIndex != ((GridLayoutManager) parent.getLayoutManager()).getSpanCount() - 1) {
                            outRect.right = 10;
                        }
                    }
                }
            });
            treeRecyclerAdapter = new TreeRecyclerAdapter();
            recyclerView.setAdapter(treeRecyclerAdapter);
        }
    }
}
