package com.yaoguang.map.location.impl;

import android.annotation.SuppressLint;
import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.yaoguang.lib.base.BaseApplication;
import com.yaoguang.lib.common.AppClickUtil;
import com.yaoguang.lib.common.ObjectUtils;
import com.yaoguang.greendao.entity.Location;

import java.util.Date;

/**
 * Project Name:driver
 * Created by weiliying
 * on 2017 2017/4/17.11:37
 */

public class LocationManager {


    private Location location;
    private AMapLocationClient locationClient = null;
    private Comeback mComeback;
    private Context mContext;

    public interface Comeback {

        /**
         * 获取成功信息
         *
         * @param location 地址
         */
        void success(Location location);


    }

    public void init(Context context) {
        mContext = context;

        //初始化client
        locationClient = new AMapLocationClient(context);
        //设置定位参数
        locationClient.setLocationOption(getDefaultOption());
        startLocation();
    }

    private void startLocation() {
        // 启动定位
        locationClient.startLocation();
        // 设置定位监听
        locationClient.setLocationListener(new AMapLocationListener() {
            @Override
            public void onLocationChanged(com.amap.api.location.AMapLocation aMapLocation) {
                if (aMapLocation.getErrorCode() == 0) {
                    location = new Location();
                    location.setProvince(aMapLocation.getProvince());
                    location.setStreet(aMapLocation.getStreet() + aMapLocation.getStreetNum());
                    location.setDistrict(aMapLocation.getDistrict());
                    location.setCity(aMapLocation.getCity());
                    location.setAddress(aMapLocation.getAddress());
                    // 解析定位结果
                    location.setLon(aMapLocation.getLongitude());
                    location.setLat(aMapLocation.getLatitude());
                    location.setPositionTime(ObjectUtils.parseString(new Date()));

                    if (TextUtils.isEmpty(aMapLocation.getCity())) {
                        Toast.makeText(BaseApplication.getInstance(), "获取不到当前地址，正在重新获取中", Toast.LENGTH_LONG).show();
                    } else {
                        if (getComeback() != null) {
                            getComeback().success(location);
                        }
                    }
                } else {
                    Toast.makeText(BaseApplication.getInstance(), "获取不到当前地址，正在重新获取中，错误码：" + aMapLocation.getErrorCode(), Toast.LENGTH_LONG).show();
                    // 显示错误信息ErrCode是错误码，errInfo是错误信息，详见错误码表。
                    Log.e("AmapError", "location Error, ErrCode:"
                            + aMapLocation.getErrorCode() + ", errInfo:"
                            + aMapLocation.getErrorInfo());
                }
            }
        });
    }

    public Comeback getComeback() {
        return mComeback;
    }

    public void setComeback(Comeback mComeback) {
        this.mComeback = mComeback;
    }

    public void destroyLocation() {
        if (null != locationClient) {
            // 如果AMapLocationClient是在当前Activity实例化的，
            // 在Activity的onDestroy中一定要执行AMapLocationClient的onDestroy
            stopLocation();
            locationClient.onDestroy();
            locationClient = null;
        }
    }

    private void stopLocation() {
        // 停止定位
        if (locationClient != null) {
            locationClient.stopLocation();
        }
    }

    /**
     * 保存数据
     */
    public void save(Location location) {
        if (AppClickUtil.isDuplicateClick()) {
            return;
        }
        // 作废
//        Injections.getLocationBiz().saveOrUpdate(location);
    }

    private AMapLocationClientOption getDefaultOption() {
        AMapLocationClientOption mOption = new AMapLocationClientOption();
        mOption.setOnceLocationLatest(true);//可选，设置是否等待wifi刷新，默认为false.如果设置为true,会自动变为单次定位，持续定位时不要使用

        mOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);//可选，设置定位模式，可选的模式有高精度、仅设备、仅网络。默认为高精度模式
        mOption.setGpsFirst(false);//可选，设置是否gps优先，只在高精度模式下有效。默认关闭
//        mOption.setHttpTimeOut(30000);//可选，设置网络请求超时时间。默认为30秒。在仅设备模式下无效
        mOption.setInterval(2000);//可选，设置定位间隔。默认为2秒
        mOption.setNeedAddress(true);//可选，设置是否返回逆地理地址信息。默认是true
        mOption.setOnceLocation(false);//可选，设置是否单次定位。默认是false
        AMapLocationClientOption.setLocationProtocol(AMapLocationClientOption.AMapLocationProtocol.HTTPS);//可选， 设置网络请求的协议。可选HTTP或者HTTPS。默认为HTTP
        mOption.setSensorEnable(false);//可选，设置是否使用传感器。默认是false
        mOption.setWifiScan(true); //可选，设置是否开启wifi扫描。默认为true，如果设置为false会同时停止主动刷新，停止以后完全依赖于系统刷新，定位位置可能存在误差
        mOption.setLocationCacheEnable(false); //可选，设置是否使用缓存定位，默认为true
        return mOption;
    }

}
