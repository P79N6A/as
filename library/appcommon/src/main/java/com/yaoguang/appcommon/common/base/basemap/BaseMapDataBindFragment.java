package com.yaoguang.appcommon.common.base.basemap;

import android.Manifest;
import android.content.pm.PackageManager;
import android.databinding.ViewDataBinding;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Point;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.location.DPoint;
import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdate;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.LocationSource;
import com.amap.api.maps.Projection;
import com.amap.api.maps.TextureMapView;
import com.amap.api.maps.UiSettings;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.CameraPosition;
import com.amap.api.maps.model.Circle;
import com.amap.api.maps.model.CircleOptions;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.LatLngBounds;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.maps.model.MyLocationStyle;
import com.amap.api.maps.model.animation.Animation;
import com.amap.api.maps.model.animation.ScaleAnimation;
import com.amap.api.maps.model.animation.TranslateAnimation;
import com.amap.api.services.core.AMapException;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.core.PoiItem;
import com.amap.api.services.core.SuggestionCity;
import com.amap.api.services.geocoder.GeocodeResult;
import com.amap.api.services.geocoder.GeocodeSearch;
import com.amap.api.services.geocoder.RegeocodeQuery;
import com.amap.api.services.geocoder.RegeocodeResult;
import com.amap.api.services.poisearch.PoiResult;
import com.amap.api.services.poisearch.PoiSearch;
import com.yaoguang.appcommon.R;
import com.yaoguang.lib.base.BaseApplication;
import com.yaoguang.lib.base.BaseFragmentDataBind;
import com.yaoguang.lib.common.ULog;
import com.yaoguang.lib.common.displaymetrics.DisplayMetricsUtils;
import com.yaoguang.map.common.AMapUtil;
import com.yaoguang.map.common.ToastUtil;

import java.util.List;

import static com.amap.api.maps.AMapOptions.ZOOM_POSITION_RIGHT_CENTER;


/**
 * 作者：韦理英
 * 时间： 2017/5/15 0015.
 * update zhongjh
 * data 2018-06-11
 */
public abstract class BaseMapDataBindFragment<B extends ViewDataBinding> extends BaseFragmentDataBind<B> implements LocationSource, AMapLocationListener,CallBack {
    public final long LOCATION_INTERVAL = 5 * 1000;

    // 是否监听当前位置
    protected boolean isMonitor = true;

    // 地图控制器对象
    public AMap mAMap;
    // 地图
    protected TextureMapView mMapView;
    // 地址改变事件
    private OnLocationChangedListener mOnLocationChangedListener;

    // 获取地图当前位置
    protected LatLng mLatLng;

    // ui定位设置
    private UiSettings mUiSettings;
    // 定位功能
    private AMapLocationClient mlocationClient;
    // 定位模式
    private AMapLocationClientOption mLocationOption;

    boolean useMoveToLocationWithMapMode = true;

    // 自定义定位小蓝点的Marker,显示自己当前位置的
    private Marker locationMarker;
    MyCancelCallback myCancelCallback = new MyCancelCallback(locationMarker);

    // region 可移动的坐标点

    // 设置Marker在屏幕上,不跟随地图移动,用来修改位置的
    public Marker screenMarker = null;

    // 是否人为移动
    private boolean isArtificialMove = true;
    // 是否跟随当前位置移动
    protected boolean isFollowMove = true;

    // endregion

    //坐标和经纬度转换工具
    Projection projection;

    // 地图移动回调事件
    public CallBack mCallBack;

    // 圆形
    Circle mCircle;

    protected Bundle mSavedInstanceState;

    // 赋值
    public void setCallBack(CallBack mCallBack) {
        this.mCallBack = mCallBack;
    }

    @Override
    public void savedInstanceState(@Nullable Bundle savedInstanceState) {
        super.savedInstanceState(savedInstanceState);
        mSavedInstanceState = savedInstanceState;
    }

    @Override
    public void initDataBind(View view, LayoutInflater inflater) {
        super.initDataBind(view, inflater);
        if (ContextCompat.checkSelfPermission(getContext(),
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            showToast("App没有定位权限");
        }

        View map = view.findViewById(R.id.map);
        if (map != null) {
            aMapinit(view.findViewById(R.id.map), mSavedInstanceState);
            map.setOnTouchListener((v, event) -> false);
        }
    }

    protected void aMapinit(TextureMapView mapView, Bundle savedInstanceState) {
        //获取地图控件引用
        mMapView = mapView;
        //在activity执行onCreate时执行mMapView.onCreate(savedInstanceState)，创建地图
        mMapView.onCreate(savedInstanceState);
        setCallBack(this);
        initMap();
    }

    /**
     * 是否显示定位按钮
     *
     * @param isShow true 显示，false 不显示
     */
    protected void setLocationButton(boolean isShow) {
        if (mUiSettings != null) {
            mUiSettings.setMyLocationButtonEnabled(isShow);
        }
    }

    protected void setCompass(boolean isShow) {
        if (mUiSettings != null) {
            mUiSettings.setCompassEnabled(isShow);
        }
    }

    /**
     * 设置是否显示缩放按钮
     */
    protected void setZoomControlsEnabled(boolean isEnabled) {
        mAMap.getUiSettings().setZoomControlsEnabled(isEnabled);// 这个方法设置了地图是否允许显示缩放按钮。
        mAMap.getUiSettings().setZoomPosition(ZOOM_POSITION_RIGHT_CENTER);// 设置缩放按钮的位置
    }

    /**
     * 缩小一级
     */
    public void zoomOut() {
        if (mAMap != null) {
            mAMap.moveCamera(CameraUpdateFactory.zoomOut());
        }
    }

    /**
     * 设置距离
     *
     * @param zoom 距离等级
     */
    public void zoom(float zoom) {
        if (mAMap != null) {
            mAMap.moveCamera(CameraUpdateFactory.zoomTo(zoom));
        }
    }

    /**
     * 缩放移动地图，保证所有自定义marker在可视范围中。
     *
     * @param latLngs
     */
    public void zoomToSpan(List<LatLng> latLngs) {
        if (latLngs != null && latLngs.size() > 0) {
            if (mAMap == null)
                return;
            LatLngBounds bounds = getLatLngBounds(latLngs);
            mAMap.moveCamera(CameraUpdateFactory.newLatLngBounds(bounds, 50));
        }
    }

    /**
     * 移动到位置
     *
     * @param latlng 经纬度
     */
    public void movePoint(@NonNull LatLng latlng, boolean isArtificialMove) {
        this.isArtificialMove = isArtificialMove;
        changeCamera(CameraUpdateFactory.newCameraPosition(new CameraPosition(latlng, 17, 0, 0)));
    }

    /**
     * 移动到我的位置
     */
    public void moveMyLocation() {
        if (mLatLng == null) {
            ULog.e("我的位置 mLocation 是空的");
            return;
        }
        movePoint(mLatLng, true);
    }

    /**
     * 在地图上添加marker
     *
     * @param latlng
     * @param title
     * @param dsc
     */
    public void addMarkersToMap(LatLng latlng, String title, String dsc) {
        MarkerOptions markerOption = new MarkerOptions().icon(BitmapDescriptorFactory
                .defaultMarker(BitmapDescriptorFactory.HUE_AZURE))
                .position(latlng)
                .title(title)
                .snippet(dsc)
                .draggable(true);
        Marker marker = mAMap.addMarker(markerOption);
        marker.showInfoWindow();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        //在activity执行onDestroy时执行mMapView.onDestroy()，销毁地图
        if (mMapView != null)
            mMapView.onDestroy();
        if (mlocationClient != null) {
            mlocationClient.onDestroy();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        //在activity执行onResume时执行mMapView.onResume ()，重新绘制加载地图
        if (mMapView != null)
            mMapView.onResume();
        useMoveToLocationWithMapMode = true;
    }

    @Override
    public void onPause() {
        super.onPause();
        //在activity执行onPause时执行mMapView.onPause ()，暂停地图的绘制
        if (mMapView != null)
            mMapView.onPause();
        deactivate();
        useMoveToLocationWithMapMode = false;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        //在activity执行onSaveInstanceState时执行mMapView.onSaveInstanceState (outState)，保存地图当前的状态
        if (mMapView != null)
            mMapView.onSaveInstanceState(outState);
    }

    @Override
    public void activate(OnLocationChangedListener onLocationChangedListener) {
        mOnLocationChangedListener = onLocationChangedListener;
        if (mlocationClient == null) {
            mlocationClient = new AMapLocationClient(getContext());
            mLocationOption = new AMapLocationClientOption();
            //设置定位监听
            Log.e("地图监听", "地图监听" + "mlocationClient.setLocationListener(this);");
            mlocationClient.setLocationListener(this);
            //设置为高精度定位模式
            mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
            //是指定位间隔
            mLocationOption.setInterval(2000);
            //设置定位参数
            mlocationClient.setLocationOption(mLocationOption);
            // 此方法为每隔固定时间会发起一次定位请求，为了减少电量消耗或网络流量消耗，
            // 注意设置合适的定位时间的间隔（最小间隔支持为2000ms），并且在合适时间调用stopLocation()方法来取消定位请求
            // 在定位结束后，在合适的生命周期调用onDestroy()方法
            // 在单次定位情况下，定位无论成功与否，都无需调用stopLocation()方法移除请求，定位sdk内部会移除
            mlocationClient.startLocation();
        }
    }

    @Override
    public void deactivate() {
        mOnLocationChangedListener = null;
        if (mlocationClient != null) {
            mlocationClient.stopLocation();
            mlocationClient.onDestroy();
        }
        mlocationClient = null;
    }

    /**
     * 定位成功后回调函数
     */
    @Override
    public void onLocationChanged(AMapLocation amapLocation) {
        Log.e("地图监听", "地图监听" + "onLocationChanged()");
        if (mOnLocationChangedListener != null && amapLocation != null) {
            if (amapLocation != null
                    && amapLocation.getErrorCode() == 0) {
                LatLng latLng = new LatLng(amapLocation.getLatitude(), amapLocation.getLongitude());
                //展示自定义定位小蓝点
                if (locationMarker == null) {
                    //首次定位
                    locationMarker = mAMap.addMarker(new MarkerOptions().position(latLng)
                            .icon(BitmapDescriptorFactory.fromResource(R.drawable.location_marker))
                            .anchor(0.5f, 0.5f));
                    if (isFollowMove)
                        //首次定位,选择移动到地图中心点并修改级别到15级
                        mAMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 17));
                    useMoveToLocationWithMapMode = false;
                    startGrowAnimation();
                } else {
                    if (useMoveToLocationWithMapMode) {
                        //二次以后定位，使用sdk中没有的模式，让地图和小蓝点一起移动到中心点（类似导航锁车时的效果）
                        startMoveLocationAndMap(latLng);
                    } else {
                        startChangeLocation(latLng);
                    }
                }


            } else {
                String errText = "定位失败," + amapLocation.getErrorCode() + ": " + amapLocation.getErrorInfo();
                Log.e("AmapErr", errText);
            }
        }
    }

    /**
     * 屏幕中心marker 跳动
     */
    public void startJumpAnimation() {
        Log.e("地图监听", "地图监听" + "startJumpAnimation");
        if (screenMarker != null) {

            // 获取到当前地址后，这个只能放在外层，不然会报错
            if (mCallBack != null && isArtificialMove) {
                Log.e("地图监听", "地图监听" + "onCameraChangeFinish");
                mCallBack.onCameraChangeFinish(screenMarker.getPosition());
            }
            isArtificialMove = true;

            //根据屏幕距离计算需要移动的目标点
            final LatLng latLng = screenMarker.getPosition();
            Point point = mAMap.getProjection().toScreenLocation(latLng);
            point.y -= DisplayMetricsUtils.dip2px(125);
            LatLng target = mAMap.getProjection()
                    .fromScreenLocation(point);
            //使用TranslateAnimation,填写一个需要移动的目标点
            Animation animation = new TranslateAnimation(target);
            animation.setInterpolator(input -> {
                // 模拟重加速度的interpolator
                if (input <= 0.5) {
                    return (float) (0.5f - 2 * (0.5 - input) * (0.5 - input));
                } else {
                    return (float) (0.5f - Math.sqrt((input - 0.5f) * (1.5f - input)));
                }
            });
            //整个移动所需要的时间
//            animation.setDuration(600);
            animation.setDuration(0);
            //设置动画
            screenMarker.setAnimation(animation);
            //开始动画
            screenMarker.startAnimation();
        } else {
            Log.e("amap", "screenMarker is null");
        }
    }


    /**
     * 开始进行poi搜索
     */
    public void doSearchQuery(String keyWord, LatLng latLng) {

        LatLonPoint lp = new LatLonPoint(latLng.latitude, latLng.longitude);

        // 将现在这个lp转换成中文地址
        getAddress(lp);
    }

    /**
     * 初始化
     */
    private void initMap() {
        if (mAMap == null) {
            mAMap = mMapView.getMap();
            mUiSettings = mAMap.getUiSettings();
            // 地图的缩放级别一共分为 17 级，从 3 到 19。数字越大，展示的图面信息越精细
//            zoom(17f);
            setUpMap();
            setCompass(false);
            setLocationButton(true);

            if (mCallBack != null)
                mCallBack.initMapFinish();
        }
    }


    /**
     * 设置一些amap的属性
     */
    private void setUpMap() {
        if (isMonitor) {
            mAMap.setLocationSource(this);// 设置定位监听
        }
        mAMap.setMyLocationEnabled(isMonitor);// 设置为true表示显示定位层并可触发定位，false表示隐藏定位层并不可触发定位，默认是false
        mAMap.getUiSettings().setMyLocationButtonEnabled(true);// 设置默认定位按钮是否显示
        mAMap.getUiSettings().setZoomGesturesEnabled(true); // 这个方法设置了地图是否允许通过手势来缩放。
        mAMap.getUiSettings().setGestureScaleByMapCenter(true);// 开启以中心点进行手势操作的方法：
        setZoomControlsEnabled(false);


        MyLocationStyle myLocationStyle = new MyLocationStyle();
        // 请求间隔
        myLocationStyle.interval(LOCATION_INTERVAL);
        myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_SHOW);
        // 请求类型
        mAMap.setMyLocationStyle(myLocationStyle);
    }

    /**
     * 根据动画按钮状态，调用函数animateCamera或moveCamera来改变可视区域
     */
    private void changeCamera(CameraUpdate update) {
        if (mAMap != null) {
            mAMap.moveCamera(update);
        }
    }

    /**
     * 根据自定义内容获取缩放bounds
     */
    private LatLngBounds getLatLngBounds(List<LatLng> pointList) {
        LatLngBounds.Builder b = LatLngBounds.builder();
        for (int i = 0; i < pointList.size(); i++) {
            LatLng p = pointList.get(i);
            b.include(p);
        }
        return b.build();
    }

    /**
     * 修改自定义定位小蓝点的位置
     */
    private void startChangeLocation(LatLng latLng) {

        if (locationMarker != null) {
            LatLng curLatlng = locationMarker.getPosition();
            if (curLatlng == null || !curLatlng.equals(latLng)) {
                locationMarker.setPosition(latLng);
            }
        }
    }

    /**
     * 同时修改自定义定位小蓝点和地图的位置
     */
    private void startMoveLocationAndMap(LatLng latLng) {
        //将小蓝点提取到屏幕上
        if (projection == null) {
            projection = mAMap.getProjection();
        }
        if (locationMarker != null && projection != null) {
            LatLng markerLocation = locationMarker.getPosition();
            Point screenPosition = mAMap.getProjection().toScreenLocation(markerLocation);
            locationMarker.setPositionByPixels(screenPosition.x, screenPosition.y);

        }

        //移动地图，移动结束后，将小蓝点放到放到地图上
        myCancelCallback.setTargetLatlng(latLng);
        //动画移动的时间，最好不要比定位间隔长，如果定位间隔2000ms 动画移动时间最好小于2000ms，可以使用1000ms
        //如果超过了，需要在myCancelCallback中进行处理被打断的情况
        mAMap.animateCamera(CameraUpdateFactory.changeLatLng(latLng), 1000, myCancelCallback);
    }

    /**
     * 在屏幕中心添加一个Marker
     *
     * @param latLng      经纬度
     * @param isDraggable 是否拖动
     */
    public void addMarkerInScreenCenter(LatLng latLng, boolean isDraggable) {
        if (screenMarker != null)
            screenMarker.remove();
        if (latLng == null)
            latLng = mAMap.getCameraPosition().target;
        Point screenPosition = mAMap.getProjection().toScreenLocation(latLng);
        MarkerOptions markerOption = new MarkerOptions();
        markerOption.position(latLng);
        markerOption.icon(BitmapDescriptorFactory.fromResource(R.drawable.purple_pin));
        markerOption.anchor(0.5f, 0.5f);
        markerOption.draggable(isDraggable);//设置Marker可拖动
        screenMarker = mAMap.addMarker(markerOption);
        if (isDraggable) {
            //设置Marker在屏幕上,不跟随地图移动
            screenMarker.setPositionByPixels(screenPosition.x, screenPosition.y);
            mAMap.setPointToCenter(screenPosition.x, screenPosition.y);// 在对地图进行手势操作时（滑动手势除外），可以指定屏幕中心点后执行相应手势。
        }
    }

    /**
     * 在地图上添加marker ： InfoWindow
     */
    private void addMarkersToMap(LatLng latLng, String content) {
        MarkerOptions markerOption = new MarkerOptions().icon(BitmapDescriptorFactory
                .defaultMarker(BitmapDescriptorFactory.HUE_AZURE))
                .position(latLng)
//                .title("标题")
                .snippet(content)
                .draggable(true);
        Marker markerInfoWindow = mAMap.addMarker(markerOption);
        markerInfoWindow.showInfoWindow();
    }

    /**
     * 重新绘制圆形
     *
     * @param radius 半径
     * @param latLng 经纬度
     */
    public void drawCircle(double radius, LatLng latLng) {
        if (mCircle != null)
            mCircle.remove();
        CircleOptions option = new CircleOptions();
        option.fillColor(getContext().getResources().getColor(R.color.fill));
        option.strokeColor(getContext().getResources().getColor(R.color.stroke));
        option.strokeWidth(4);
        option.radius(radius);
        option.center(latLng);
        mCircle = mAMap.addCircle(option);
    }

    /**
     * 地上生长的Marker
     */
    private void startGrowAnimation() {
        if (locationMarker != null) {
            Animation animation = new ScaleAnimation(0, 1, 0, 1);
            animation.setInterpolator(new LinearInterpolator());
            //整个移动所需要的时间
            animation.setDuration(1000);
            //设置动画
            locationMarker.setAnimation(animation);
            //开始动画
            locationMarker.startAnimation();
        }
    }

    /**
     * 响应逆地理编码
     */
    private void getAddress(final LatLonPoint latLonPoint) {
        RegeocodeQuery regeocodeQuery = new RegeocodeQuery(latLonPoint, 200,
                GeocodeSearch.AMAP);// 第一个参数表示一个Latlng，第二参数表示范围多少米，第三个参数表示是火系坐标系还是GPS原生坐标系
        GeocodeSearch geocoderSearch = new GeocodeSearch(_mActivity);
        geocoderSearch.setOnGeocodeSearchListener(new GeocodeSearch.OnGeocodeSearchListener() {

            /**
             * 逆地理编码回调
             */
            @Override
            public void onRegeocodeSearched(RegeocodeResult result, int rCode) {
                if (rCode == AMapException.CODE_AMAP_SUCCESS) {
                    if (result != null && result.getRegeocodeAddress() != null
                            && result.getRegeocodeAddress().getFormatAddress() != null) {
                        if (mCallBack != null)
                            mCallBack.onRegeocodeSearched(result);
                    } else {
                        ToastUtil.show(BaseApplication.getInstance().getApplicationContext(), R.string.no_result);
                    }
                } else {
                    ToastUtil.show(BaseApplication.getInstance().getApplicationContext(), rCode);
                }
            }

            /**
             * 地理编码查询回调
             */
            @Override
            public void onGeocodeSearched(GeocodeResult result, int rCode) {

            }
        });
        geocoderSearch.getFromLocationAsyn(regeocodeQuery);// 设置异步逆地理编码请求
    }

}
