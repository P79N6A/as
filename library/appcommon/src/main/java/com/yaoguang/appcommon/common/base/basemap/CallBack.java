package com.yaoguang.appcommon.common.base.basemap;

import com.amap.api.maps.model.LatLng;
import com.amap.api.services.geocoder.RegeocodeResult;

/**
 * BseMapDataBindFragment的回调
 * Created by zhongjh on 2018/6/11.
 */
public interface CallBack {

    /**
     * 初始化map后
     */
    void initMapFinish();

    /**
     * 每次移动坐标的回调事件
     */
    void onCameraChangeFinish(LatLng latLng);

    /**
     * 移动坐标后 逆地理编码回调
     *
     * @param result 回调成功后的数据源
     */
    void onRegeocodeSearched(RegeocodeResult result);
}
