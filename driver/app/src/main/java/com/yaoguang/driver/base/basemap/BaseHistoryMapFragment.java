package com.yaoguang.driver.base.basemap;

import android.graphics.Color;
import android.util.Pair;

import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.LatLngBounds;
import com.amap.api.maps.model.PolylineOptions;
import com.amap.api.maps.utils.SpatialRelationUtil;
import com.amap.api.maps.utils.overlay.SmoothMoveMarker;
import com.elvishew.xlog.XLog;
import com.yaoguang.greendao.entity.LatLons;
import com.yaoguang.map.R;

import java.util.ArrayList;
import java.util.List;


/**
 * 作者：韦理英
 * 时间： 2017/5/15 0015.
 */

public abstract class BaseHistoryMapFragment extends BaseMapFragment {
    // 轨迹缓存
    private List<LatLng> readHistoryLatLngsCache = new ArrayList<>();

    /**
     * 描    述：开始播放轨迹
     * 作    者：韦理英
     * 时    间：
     * @param readHistoryLatLngs 经纬度列表
     */
    public void startCarHistory(List<LatLng> readHistoryLatLngs) {
        XLog.i("startCarHistory");
        // 轨迹是空的或轨迹没数据就不放
        if (isNull(readHistoryLatLngs)) return;

        // 获取轨迹坐标点
        List<LatLng> points = readHistoryLatLngs;

        // 如果轨迹大于3就bounds范围
        if (readHistoryLatLngs.size() > 3) {
            try {
                LatLngBounds bounds;
                if (points.get(0).latitude < points.get(points.size() - 2).latitude) {
                    bounds = new LatLngBounds(points.get(0), points.get(points.size() - 2));
                } else {
                    bounds = new LatLngBounds(points.get(0), points.get(points.size() - 3));
                }
                aMap.animateCamera(CameraUpdateFactory.newLatLngBounds(bounds, 50));
            } catch (Exception e) {
                e.printStackTrace();
                int local = points.size() / 2;
                movePoint(points.get(local));
                zoom(10f);
            }
        } else { // 如果轨迹小于3就不要bounds，直接移动到起点
            movePoint(points.get(0));
        }

        SmoothMoveMarker smoothMarker = new SmoothMoveMarker(aMap);
        // 设置滑动的图标
        smoothMarker.setDescriptor(BitmapDescriptorFactory.fromResource(R.drawable.ic_car01));

        LatLng drivePoint = points.get(0);
        Pair<Integer, LatLng> pair = SpatialRelationUtil.calShortestDistancePoint(points, drivePoint);
        points.set(pair.first, drivePoint);
        List<LatLng> subList = points.subList(pair.first, points.size());

        aMap.addPolyline(new PolylineOptions().
                addAll(readHistoryLatLngs).width(10).color(Color.BLUE));

        // 设置滑动的轨迹左边点
        smoothMarker.setPoints(subList);
        // 设置滑动的总时间
        smoothMarker.setTotalDuration(40);
        // 开始滑动
        smoothMarker.startSmoothMove();
    }

    /**
     * 描    述：处理轨迹经纬度
     * 作    者：韦理英
     * 时    间：
     *
     * @param positionLogs 传进来的经纬度
     */
    public void setHistoryLatLngs(List<LatLons> positionLogs) {
        if (isNull(positionLogs)) return;
        // 取出经纬度

        try {
            for (LatLons positionLog : positionLogs) {
                // lat 和lon不等于0
                if (positionLog.getLat() != 0 && positionLog.getLon() != 0) {
                    LatLng latLng = new LatLng(positionLog.getLat(), positionLog.getLon());
                    readHistoryLatLngsCache.add(latLng);
                }
            }
//            for (int i = positionLogs.size() - 1; i >=0; i--) {
//                DriverOrderPositionLog driverOrderPositionLog = positionLogs.get(i);
//                LatLng latlng = new LatLng(driverOrderPositionLog.getLat(), driverOrderPositionLog.getLon());
//                if(latlng.latitude != 0) {
//                    readHistoryLatLngsCache.add(latlng);
//                }
//            }

            startCarHistory(readHistoryLatLngsCache);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private boolean isNull(List<?> positionLogs) {
        return positionLogs == null || positionLogs.isEmpty();
    }
}






























