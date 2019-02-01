package com.yaoguang.driver.nav.search;

import android.content.Context;
import android.os.Handler;

import com.amap.api.maps.AMap;
import com.amap.api.maps.model.LatLng;
import com.yaoguang.driver.nav.search.model.SearchKeyword;

import java.util.List;

/**
 * 作者：韦理英
 * 时间： 2017/5/15 0015.
 */

public interface MapSearch {

    void init(Context context, AMap aMap, Comeback comeback, String city);

    void searchKeyword(String newText);

    LatLng getSelected();

    void setCurrentLocation(LatLng latLng);

    void searchPoi(String keyword, boolean quiet, String province, Handler handler);

    String getKeyword();

    void setCity(String city);

    interface Comeback {
        void searchResult(List<SearchKeyword> result);
    }
}
