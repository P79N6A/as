package com.yaoguang.greendao.entity.driver;


import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by liyangbin on 2018/4/25.
 * 地理位置及区域
 */
public class LocationArea implements Parcelable {


    private Site site;

    private List<Site> area;

    public Site getSite() {
        return site;
    }

    public void setSite(Site site) {
        this.site = site;
    }

    public List<Site> getArea() {
        return area;
    }

    public void setArea(List<Site> area) {
        this.area = area;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(this.site, flags);
        dest.writeList(this.area);
    }

    public LocationArea() {
    }

    protected LocationArea(Parcel in) {
        this.site = in.readParcelable(Site.class.getClassLoader());
        this.area = new ArrayList<Site>();
        in.readList(this.area, Site.class.getClassLoader());
    }

    public static final Parcelable.Creator<LocationArea> CREATOR = new Parcelable.Creator<LocationArea>() {
        @Override
        public LocationArea createFromParcel(Parcel source) {
            return new LocationArea(source);
        }

        @Override
        public LocationArea[] newArray(int size) {
            return new LocationArea[size];
        }
    };
}
