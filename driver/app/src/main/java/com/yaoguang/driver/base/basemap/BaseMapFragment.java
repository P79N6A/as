package com.yaoguang.driver.base.basemap;

import android.Manifest;
import android.content.pm.PackageManager;
import android.databinding.ViewDataBinding;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;

import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdate;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.TextureMapView;
import com.amap.api.maps.UiSettings;
import com.amap.api.maps.model.CameraPosition;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.LatLngBounds;
import com.amap.api.maps.model.MyLocationStyle;
import com.yaoguang.driver.base.baseview.BasePresenter;
import com.yaoguang.driver.base.DataBindingFragment;
import com.yaoguang.map.R;

import java.util.List;


/**
 * 作者：韦理英
 * 时间： 2017/5/15 0015.
 */

public abstract class BaseMapFragment<P extends BasePresenter, B extends ViewDataBinding> extends DataBindingFragment<P, B> {
    public final long LOCATION_INTERVAL = 5 * 1000;
    protected AMap aMap;
    protected LatLng mLocation;
    TextureMapView mMapView = null;
    private UiSettings mUiSettings;
    // 本地位置是否已经改变
    protected boolean isLocationChange;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        if (ContextCompat.checkSelfPermission(getContext(),
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            showToast("App没有定位权限");
        }

        aMapinit((TextureMapView) getView().findViewById(R.id.map), savedInstanceState);
        super.onActivityCreated(savedInstanceState);
    }

    private void aMapinit(TextureMapView mapView, Bundle savedInstanceState) {
        //获取地图控件引用
        mMapView = mapView;
        //在activity执行onCreate时执行mMapView.onCreate(savedInstanceState)，创建地图
        mMapView.onCreate(savedInstanceState);

        //初始化地图控制器对象
        if (aMap == null) {
            aMap = mMapView.getMap();
            mUiSettings = aMap.getUiSettings();
        }

        zoom(15f);
        setLocationEnabled(true);
        setLocationButton(true);
        setCompass(false);
        mUiSettings.setZoomControlsEnabled(false);
    }

    /**
     * 是否显示定位按钮
     *
     * @param isShow true 显示，false 不显示
     */
    protected void setLocationButton(boolean isShow) {
        mUiSettings.setMyLocationButtonEnabled(isShow);
    }

    protected void setCompass(boolean isShow) {
        mUiSettings.setCompassEnabled(isShow);
    }

    public void setLocationEnabled(boolean b) {
        aMap.setMyLocationEnabled(b);
        MyLocationStyle myLocationStyle = new MyLocationStyle();
        // 请求间隔
        myLocationStyle.interval(LOCATION_INTERVAL);
        // 请求类型
        aMap.setMyLocationStyle(myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATION_ROTATE_NO_CENTER));
        aMap.setMyLocationStyle(myLocationStyle);
        aMap.setOnMyLocationChangeListener(new AMap.OnMyLocationChangeListener() {
            @Override
            public void onMyLocationChange(Location location) {

                try {
                    // 如果缓存位置是空的，就取一个
                    if (mLocation == null) {
                        mLocation = new LatLng(location.getLatitude(), location.getLongitude());
                        changeCamera(CameraUpdateFactory.newCameraPosition(new CameraPosition(mLocation, 15, 0, 0)));
                        updateLocation();
                    } else { // 判断是否有新的位置
                        if (location.getLatitude() != mLocation.latitude) {
                            mLocation = new LatLng(location.getLatitude(), location.getLongitude());
                            isLocationChange = true;
                        } else {
                            isLocationChange = false;
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    protected abstract void updateLocation();

    /**
     * 根据动画按钮状态，调用函数animateCamera或moveCamera来改变可视区域
     */
    private void changeCamera(CameraUpdate update) {
        aMap.moveCamera(update);
    }

    /**
     * 缩小一级
     */
    public void zoomOut() {
        aMap.moveCamera(CameraUpdateFactory.zoomOut());
    }

    public void zoom(float zoom) {
        aMap.moveCamera(CameraUpdateFactory.zoomTo(zoom));
    }

    /**
     * 缩放移动地图，保证所有自定义marker在可视范围中。
     *
     * @param latLngs
     */
    public void zoomToSpan(List<LatLng> latLngs) {
        if (latLngs != null && latLngs.size() > 0) {
            if (aMap == null)
                return;
            LatLngBounds bounds = getLatLngBounds(latLngs);
            aMap.moveCamera(CameraUpdateFactory.newLatLngBounds(bounds, 50));
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

    protected void movePoint(@NonNull LatLng latlng) {
        changeCamera(CameraUpdateFactory.newCameraPosition(new CameraPosition(latlng, 15, 0, 0)));
    }

    @Override
    public void onDestroyView() {
        //在activity执行onDestroy时执行mMapView.onDestroy()，销毁地图
        mMapView.onDestroy();
        super.onDestroyView();
    }

    @Override
    public void onResume() {
        //在activity执行onResume时执行mMapView.onResume ()，重新绘制加载地图
        mMapView.onResume();
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        //在activity执行onPause时执行mMapView.onPause ()，暂停地图的绘制
        mMapView.onPause();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        //在activity执行onSaveInstanceState时执行mMapView.onSaveInstanceState (outState)，保存地图当前的状态
        mMapView.onSaveInstanceState(outState);
    }
}
