package com.yaoguang.driver.nav.search.model;

import com.amap.api.services.core.LatLonPoint;
import com.yaoguang.common.common.GpsUtils;
import com.yaoguang.greendao.entity.Location;

import java.text.DecimalFormat;

/**
 * 文件名：
 * 描    述： 搜索 关键词
 * 作    者：韦理英
 * 时    间：2017/8/8 0008.
 * 版    权：
 */
public class SearchKeyword {
    protected String id;
    protected String name;
    protected String address;
    protected String km = "-";
    /** 1是点击，2是搜索 */
    protected int type;
    protected boolean mIsHistory = false;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getKm() {
        return km;
    }

    public void setKm(Location start, LatLonPoint end) {
        double value = GpsUtils.GetDistance(start.getLon(),start.getLat(),end.getLongitude(),end.getLatitude());
        DecimalFormat decimalFormat = new DecimalFormat("#.0");
        km = value != 0 ? decimalFormat.format(value / 1000) + " 公里" : "";
    }

    public boolean ismIsHistory() {
        return mIsHistory;
    }

    public void setmIsHistory(boolean mIsHistory) {
        this.mIsHistory = mIsHistory;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getType() {
        return type;
    }

    /** 1是点击，2是搜索 */
    public void setType(int type) {
        this.type = type;
    }
}
