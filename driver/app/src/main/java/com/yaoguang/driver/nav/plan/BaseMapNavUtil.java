package com.yaoguang.driver.nav.plan;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.util.SparseArray;
import android.widget.Toast;

import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.model.LatLng;
import com.amap.api.navi.AMapNavi;
import com.amap.api.navi.AMapNaviListener;
import com.amap.api.navi.model.AMapCarInfo;
import com.amap.api.navi.model.AMapLaneInfo;
import com.amap.api.navi.model.AMapNaviCameraInfo;
import com.amap.api.navi.model.AMapNaviCross;
import com.amap.api.navi.model.AMapNaviInfo;
import com.amap.api.navi.model.AMapNaviLocation;
import com.amap.api.navi.model.AMapNaviPath;
import com.amap.api.navi.model.AMapNaviTrafficFacilityInfo;
import com.amap.api.navi.model.AMapRestrictionInfo;
import com.amap.api.navi.model.AMapServiceAreaInfo;
import com.amap.api.navi.model.AimLessModeCongestionInfo;
import com.amap.api.navi.model.AimLessModeStat;
import com.amap.api.navi.model.NaviInfo;
import com.amap.api.navi.model.NaviLatLng;
import com.amap.api.navi.view.RouteOverLay;
import com.autonavi.tbt.TrafficFacilityInfo;
import com.elvishew.xlog.XLog;
import com.google.gson.Gson;
import com.tencent.bugly.crashreport.CrashReport;
import com.yaoguang.common.base.BaseApplication;
import com.yaoguang.common.common.Constants;
import com.yaoguang.common.common.ULog;
import com.yaoguang.driver.nav.RouteNaviActivity;
import com.yaoguang.common.common.SPUtils;
import com.yaoguang.driver.util.Injection;
import com.yaoguang.greendao.Injections;
import com.yaoguang.map.navi.MapNavi;
import com.yaoguang.map.navi.NavSetting;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 导航算法
 */

public abstract class BaseMapNavUtil implements AMapNaviListener, MapNavi {
    protected final static String NAV_SETTING = getNavName();
    // 设置 规划路线参数
    public NavSetting mNavSetting = new NavSetting();
    /**
     * 导航对象(单例)
     */
    protected AMapNavi mAMapNavi;
    protected Context mContext;
    private boolean congestion, cost, hightspeed, avoidhightspeed;
    private AMap mAmap;
    /**
     * 开始坐标集合
     */
    private List<NaviLatLng> startList = new ArrayList<>();
    /**
     * 途径点坐标集合
     */
    private List<NaviLatLng> wayList = new ArrayList<>();
    /**
     * 终点坐标集合［建议就一个终点］
     */
    private List<NaviLatLng> endList = new ArrayList<>();
    /**
     * 保存当前算好的路线
     */
    private SparseArray<RouteOverLay> routeOverlays = new SparseArray<>();
    /**
     * 当前用户选中的路线，在下个页面进行导航
     */
    private int routeIndex;
    /**
     * 路线的权值，重合路线情况下，权值高的路线会覆盖权值低的路线
     **/
    private int zindex = 1;
    /**
     * 路线计算成功标志位
     */
    private boolean calculateSuccess = false;
    private boolean chooseRouteSuccess = false;
    private OnDrawRoute onDrawRoute;
    private String navName;

    protected void init(Context context, AMap aMap) {
        mContext = context;

        // 初始化导航
        navInit(aMap);
        // 初始化缓存
        initNavCache();
        // 设置车牌
        if (TextUtils.isEmpty(mNavSetting.getChePai())) {   // 如果没有缓存车牌，默认取服务器上司机的车牌
            String carProvince = Injection.provideDriverRepository(BaseApplication.getInstance()).getDriver().getCarProvince();
            String carNumber = Injection.provideDriverRepository(BaseApplication.getInstance()).getDriver().getCarNumber();
            if (!TextUtils.isEmpty(carNumber) && !TextUtils.isEmpty(carProvince)) {
                mNavSetting.setChePai(carProvince+carNumber);
            }
        }
    }

    /**
     * 方法必须重写
     */
    public void onDestroy() {
        startList.clear();
        wayList.clear();
        endList.clear();
        routeOverlays.clear();
        /**
         * 当前页面只是展示地图，activity销毁后不需要再回调导航的状态
         */
        mAMapNavi.removeAMapNaviListener(this);
    }

    @Override
    public void navInit(AMap aMap) {
        mAmap = aMap;
        mAMapNavi = AMapNavi.getInstance(mContext);
        mAMapNavi.addAMapNaviListener(this);
    }

    @Override
    public void startNav() {
        navi(true);
    }

    @Override
    public void emulatorNav() {
        navi(false);
    }

    private void navi(boolean isEmulator) {
        Intent gpsintent = new Intent(mContext, RouteNaviActivity.class);
        gpsintent.putExtra("gps", isEmulator);
        ((Activity) mContext).startActivity(gpsintent);
    }

    /**
     * 描    述：保存缓存
     * 作    者：韦理英
     * 时    间：
     */

    public void saveNavCache() {
        if (mNavSetting == null) return;
        // 实体转json
        String json = new Gson().toJson(mNavSetting);
        if (TextUtils.isEmpty(json) || json.length() <= 5) return;
        // 保存json
        SPUtils.getInstance().put(NAV_SETTING, json);
    }

    /**
     * 描    述：获取缓存
     * 作    者：韦理英
     * 时    间：
     */

    protected void initNavCache() {
        // 获取Sp缓存
        String json = SPUtils.getInstance().getString(NAV_SETTING);
        if (TextUtils.isEmpty(json) || json.length() <= 5) return;
        // SP转实体
        mNavSetting = new Gson().fromJson(json, NavSetting.class);
    }

//    @Override
//    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//        int id = buttonView.getId();
//        switch (id) {
//            case R.id.congestion:
//                congestion = isChecked;
//                break;
//            case R.id.avoidhightspeed:
//                avoidhightspeed = isChecked;
//                break;
//            case R.id.cost:
//                cost = isChecked;
//                break;
//            case R.id.hightspeed:
//                hightspeed = isChecked;
//                break;
//            default:
//                break;
//        }
//    }

    @Override
    public void onInitNaviSuccess() {
        ULog.i("onInitNaviSuccess");
    }

    public OnDrawRoute getOnDrawRoute() {
        return onDrawRoute;
    }

    public void setOnDrawRoute(OnDrawRoute onDrawRoute) {
        this.onDrawRoute = onDrawRoute;
    }

    /**
     * 描    述：线路规划成功
     * 作    者：韦理英
     * 时    间：
     *
     * @param ints map 的key
     */
    @Override
    public void onCalculateRouteSuccess(int[] ints) {
        routeOverlays.clear();
        HashMap<Integer, AMapNaviPath> paths = mAMapNavi.getNaviPaths();
        for (int anInt : ints) {
            AMapNaviPath path = paths.get(anInt);
            if (path != null) {
                drawRoutes(anInt, path);
            }
        }

        if (routeOverlays.size() > 0 && getOnDrawRoute() != null)
            getOnDrawRoute().onSuccess(routeOverlays);
    }

    @Override
    public void onCalculateRouteFailure(int arg0) {
        calculateSuccess = false;
        Toast.makeText(mContext, "计算路线失败，errorcode＝" + arg0, Toast.LENGTH_SHORT).show();
        ULog.i("onCalculateRouteFailure");
    }

    private void drawRoutes(int routeId, AMapNaviPath path) {
        calculateSuccess = true;
        mAmap.moveCamera(CameraUpdateFactory.changeTilt(0));
        RouteOverLay routeOverLay = new RouteOverLay(mAmap, path, mContext);
        routeOverLay.setTrafficLine(false);
        routeOverLay.addToMap();
        routeOverlays.put(routeId, routeOverLay);
    }

    @Override
    public void changeRoute(int select) {
        if (routeOverlays == null) return;
        if (!calculateSuccess) {
            Toast.makeText(mContext, "请先算路", Toast.LENGTH_SHORT).show();
            return;
        }

        /**
         * 计算出来的路径只有一条
         */
        if (routeOverlays.size() == 1) {
            chooseRouteSuccess = true;
            //必须告诉AMapNavi 你最后选择的哪条路
            mAMapNavi.selectRouteId(routeOverlays.keyAt(0));
//            Toast.makeText(mContext, "导航距离:" + (mAMapNavi.getNaviPath()).getAllLength() + "m" + "\n" + "导航时间:" + (mAMapNavi.getNaviPath()).getAllTime() + "s", Toast.LENGTH_SHORT).show();
            return;
        }

        int routeID = routeOverlays.keyAt(select);
        //突出选择的那条路
        for (int i = 0; i < routeOverlays.size(); i++) {
            int key = routeOverlays.keyAt(i);
            routeOverlays.get(key).setTransparency(0.4f);
        }
        if (routeOverlays.get(routeID) != null) {
            routeOverlays.get(routeID).setTransparency(1);
            /**把用户选择的那条路的权值弄高，使路线高亮显示的同时，重合路段不会变的透明**/
            routeOverlays.get(routeID).setZindex(zindex++);
        }

        //必须告诉AMapNavi 你最后选择的哪条路
        mAMapNavi.selectRouteId(routeID);
//        Toast.makeText(mContext, "路线标签:" + mAMapNavi.getNaviPath().getLabels(), Toast.LENGTH_SHORT).init();
        chooseRouteSuccess = true;

        /**选完路径后判断路线是否是限行路线**/
        AMapRestrictionInfo info = mAMapNavi.getNaviPath().getRestrictionInfo();
        if (!TextUtils.isEmpty(info.getRestrictionTitle())) {
            Toast.makeText(mContext, info.getRestrictionTitle(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void changeRoute() {
        if (!calculateSuccess) {
            Toast.makeText(mContext, "请先算路", Toast.LENGTH_SHORT).show();
            return;
        }

        /**
         * 计算出来的路径只有一条
         */
        if (routeOverlays.size() == 1) {
            chooseRouteSuccess = true;
            //必须告诉AMapNavi 你最后选择的哪条路
            mAMapNavi.selectRouteId(routeOverlays.keyAt(0));
//            Toast.makeText(mContext, "导航距离:" + (mAMapNavi.getNaviPath()).getAllLength() + "m" + "\n" + "导航时间:" + (mAMapNavi.getNaviPath()).getAllTime() + "s", Toast.LENGTH_SHORT).show();
            return;
        }

        if (routeIndex >= routeOverlays.size())
            routeIndex = 0;
        int routeID = routeOverlays.keyAt(routeIndex);
        //突出选择的那条路
        for (int i = 0; i < routeOverlays.size(); i++) {
            int key = routeOverlays.keyAt(i);
            routeOverlays.get(key).setTransparency(0.4f);
        }
        routeOverlays.get(routeID).setTransparency(1);
        /**把用户选择的那条路的权值弄高，使路线高亮显示的同时，重合路段不会变的透明**/
        routeOverlays.get(routeID).setZindex(zindex++);

        //必须告诉AMapNavi 你最后选择的哪条路
        mAMapNavi.selectRouteId(routeID);
        Toast.makeText(mContext, "路线标签:" + mAMapNavi.getNaviPath().getLabels(), Toast.LENGTH_SHORT).show();
        routeIndex++;
        chooseRouteSuccess = true;

        /**选完路径后判断路线是否是限行路线**/
        AMapRestrictionInfo info = mAMapNavi.getNaviPath().getRestrictionInfo();
        if (!TextUtils.isEmpty(info.getRestrictionTitle())) {
            Toast.makeText(mContext, info.getRestrictionTitle(), Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 清除当前地图上算好的路线
     */
    @Override
    public void clearRoute() {
        for (int i = 0; i < routeOverlays.size(); i++) {
            RouteOverLay routeOverlay = routeOverlays.valueAt(i);
            routeOverlay.removeFromMap();
        }
        routeOverlays.clear();
    }

    /**
     * 开始规划线路
     *
     * @param start 起点
     * @param way   途经点
     * @param end   终点
     */
    @Override
    public void startCalculate(LatLng start, LatLng way, LatLng end) {
        if (start == null || end == null) {
            ULog.i("start == null || end == null is null!!!!!!");
            return;
        }
        startList.clear();
        wayList.clear();
        endList.clear();
        routeOverlays.clear();

        NaviLatLng startNavi = new NaviLatLng(start.latitude, start.longitude);
        startList.add(startNavi);
        if (way != null) {
            NaviLatLng wayNavi = new NaviLatLng(way.latitude, way.longitude);
            wayList.add(wayNavi);
        }
        NaviLatLng endNavi = new NaviLatLng(end.latitude, end.longitude);
        endList.add(endNavi);
        calculate();
    }

    private void calculate() {

        clearRoute();
        if (avoidhightspeed && hightspeed) {
            Toast.makeText(mContext, "不走高速与高速优先不能同时为true.", Toast.LENGTH_LONG).show();
        }
        if (cost && hightspeed) {
            Toast.makeText(mContext, "高速优先与避免收费不能同时为true.", Toast.LENGTH_LONG).show();
        }
            /*
             * strategyFlag转换出来的值都对应PathPlanningStrategy常量，用户也可以直接传入PathPlanningStrategy常量进行算路。
			 * 如:mAMapNavi.calculateDriveRoute(mStartList, mEndList, mWayPointList,PathPlanningStrategy.DRIVING_DEFAULT);
			 */
        int strategyFlag = 0;
        try {
            /**
             * 方法: int strategy=mAMapNavi.strategyConvert(congestion, avoidhightspeed, cost, hightspeed, multipleroute); 参数:
             *
             * @congestion 躲避拥堵
             * @avoidhightspeed 不走高速
             * @cost 避免收费
             * @hightspeed 高速优先
             * @multipleroute 多路径
             *
             *  说明: 以上参数都是boolean类型，其中multipleroute参数表示是否多条路线，如果为true则此策略会算出多条路线。
             *  注意: 不走高速与高速优先不能同时为true 高速优先与避免收费不能同时为true
             */
            strategyFlag = mAMapNavi.strategyConvert(mNavSetting.isCongestion(), mNavSetting.isAvoidHighSpeed(), mNavSetting.isCost(), mNavSetting.isHighSpeed(), true);
            ULog.i("线路规划：" + mNavSetting.toString());
        } catch (Exception e) {
            CrashReport.postCatchedException(e);
            e.printStackTrace();
        }
        if (strategyFlag >= 0) {
            AMapCarInfo carInfo = new AMapCarInfo();
//            if (carNumber != null) {
            // 设置车辆的车牌号码.
            if (!TextUtils.isEmpty(mNavSetting.getChePai())) {
//                carInfo.setCarNumber("粤AV3268");
                carInfo.setCarNumber(mNavSetting.getChePai());
            }
            //设置车牌是否参与限行算路
            carInfo.setRestriction(mNavSetting.isXianXing());
            //  设置车辆类型，0小车，1货车
            carInfo.setCarType(mNavSetting.isHuoChe() == true ? "1" : "0");

            // 如果启动货车导航，就需要设置
            if (mNavSetting.isHuoChe()) {
                // 设置货车的高度，单位：米。
                if (!TextUtils.isEmpty(mNavSetting.getHuoCheHeight())) {
                    carInfo.setVehicleHeight(mNavSetting.getHuoCheHeight());
                }
                // 设置货车的最大载重，单位：吨。
                if (!TextUtils.isEmpty(mNavSetting.getWeight())) {
                    carInfo.setVehicleLoad(mNavSetting.getWeight());
                }
                // 设置车辆的载重是否参与算路，目前暂时无效。
                carInfo.setVehicleLoadSwitch(mNavSetting.isMaxChaiZhong());
            }
//            }
            mAMapNavi.setCarInfo(carInfo);
            mAMapNavi.calculateDriveRoute(startList, endList, wayList, strategyFlag);
//            Toast.makeText(mContext, "策略:" + strategyFlag, Toast.LENGTH_LONG).init();
        }
    }

    @Override
    public void OnUpdateTrafficFacility(AMapNaviTrafficFacilityInfo arg0) {


    }

    @Override
    public void OnUpdateTrafficFacility(TrafficFacilityInfo arg0) {


    }

    @Override
    public void OnUpdateTrafficFacility(AMapNaviTrafficFacilityInfo[] arg0) {


    }

    @Override
    public void hideCross() {


    }

    @Override
    public void hideLaneInfo() {


    }

    @Override
    public void notifyParallelRoad(int arg0) {


    }

    @Override
    public void onArriveDestination() {


    }

    @Override
    public void onArrivedWayPoint(int arg0) {


    }

    @Override
    public void onEndEmulatorNavi() {


    }

    @Override
    public void onGetNavigationText(int arg0, String arg1) {


    }

    @Override
    public void onGpsOpenStatus(boolean arg0) {


    }

    @Override
    public void onInitNaviFailure() {


    }

    @Override
    public void onLocationChange(AMapNaviLocation arg0) {


    }

    @Override
    public void onNaviInfoUpdate(NaviInfo arg0) {


    }

    @Override
    public void onNaviInfoUpdated(AMapNaviInfo arg0) {


    }

    @Override
    public void updateCameraInfo(AMapNaviCameraInfo[] aMapCameraInfos) {

    }

    @Override
    public void onServiceAreaUpdate(AMapServiceAreaInfo[] amapServiceAreaInfos) {

    }

    @Override
    public void onReCalculateRouteForTrafficJam() {


    }

    @Override
    public void onReCalculateRouteForYaw() {


    }

    @Override
    public void onStartNavi(int arg0) {


    }

    @Override
    public void onTrafficStatusUpdate() {


    }

    @Override
    public void showCross(AMapNaviCross arg0) {


    }

    @Override
    public void showLaneInfo(AMapLaneInfo[] arg0, byte[] arg1, byte[] arg2) {


    }

    @Override
    public void updateAimlessModeCongestionInfo(AimLessModeCongestionInfo arg0) {


    }

    @Override
    public void onPlayRing(int i) {

    }

    @Override
    public void onGetNavigationText(String s) {

    }

    @Override
    public void updateAimlessModeStatistics(AimLessModeStat arg0) {


    }

    /**
     * 获取获取key
     * @return
     */
    public static String getNavName() {
        String name = SPUtils.getInstance().getString(Constants.USERNAME);
        if (TextUtils.isEmpty(name)) {
            return "nav_setting";
        }
        return name;
    }
}
