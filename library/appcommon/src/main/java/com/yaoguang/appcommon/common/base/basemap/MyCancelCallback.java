package com.yaoguang.appcommon.common.base.basemap;

import com.amap.api.maps.AMap;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;

/**
 * 监控地图动画移动情况，如果结束或者被打断，都需要执行响应的操作
 * Created by zhongjh on 2018/6/11.
 */
public class MyCancelCallback implements AMap.CancelableCallback {

    // 自定义定位小蓝点的Marker,显示自己当前位置的
    private Marker mLocationMarker;

    public MyCancelCallback(Marker locationMarker){
        mLocationMarker = locationMarker;
    }

    LatLng targetLatlng;

    public void setTargetLatlng(LatLng latlng) {
        this.targetLatlng = latlng;
    }

    @Override
    public void onFinish() {
        if (mLocationMarker != null && targetLatlng != null) {
            mLocationMarker.setPosition(targetLatlng);
        }
    }

    @Override
    public void onCancel() {
        if (mLocationMarker != null && targetLatlng != null) {
            mLocationMarker.setPosition(targetLatlng);
        }
    }
}
