package com.yaoguang.appcommon.phone.home.weathergaode;

import android.content.Context;

import com.amap.api.services.weather.LocalWeatherForecastResult;
import com.amap.api.services.weather.LocalWeatherLiveResult;
import com.amap.api.services.weather.WeatherSearch;
import com.amap.api.services.weather.WeatherSearchQuery;
import com.yaoguang.greendao.entity.Location;
import com.yaoguang.lib.base.BaseApplication;
import com.yaoguang.map.location.impl.LocationManager;

/**
 * 高德天气
 * Created by zhongjh on 2018/4/20.
 */
public class WeatherGaoDeUtils {

    /**
     * 回调事件1
     */
    public interface ComeBackWeatherLiveSearched {
        void onWeatherLiveSearched(LocalWeatherLiveResult liveResult, int i, Location location);
    }

    /**
     * 回调事件2
     */
    public interface ComeBackWeatherForecastSearched {
        void onWeatherForecastSearched(LocalWeatherForecastResult forecastResult, int i);
    }


    /**
     * 获取实况天气
     *
     * @param context  上下文
     * @param comeBack 回调事件
     */
    public void getWeatherLiveSearched(Context context, ComeBackWeatherLiveSearched comeBack) {
        LocationManager locationManager = new LocationManager();
        locationManager.init(BaseApplication.getInstance().getBaseContext());
        locationManager.setComeback(location -> {
            locationManager.destroyLocation();
            WeatherSearchQuery query = new WeatherSearchQuery(location.getCity(), WeatherSearchQuery.WEATHER_TYPE_LIVE);
            WeatherSearch weathersearch = new WeatherSearch(context);
            weathersearch.setOnWeatherSearchListener(new WeatherSearch.OnWeatherSearchListener() {
                @Override
                public void onWeatherLiveSearched(LocalWeatherLiveResult liveResult, int i) {
                    comeBack.onWeatherLiveSearched(liveResult, i, location);
                }

                @Override
                public void onWeatherForecastSearched(LocalWeatherForecastResult forecastResult, int i) {
                }
            });
            weathersearch.setQuery(query);
            weathersearch.searchWeatherAsyn();


        });
    }

    /**
     * 获取预报天气
     *
     * @param context  上下文
     * @param comeBack 事件
     */
    public void getWeatherSearchListener(Context context, ComeBackWeatherForecastSearched comeBack) {
        LocationManager locationManager = new LocationManager();
        locationManager.init(BaseApplication.getInstance().getBaseContext());
        locationManager.setComeback(location -> {
            locationManager.destroyLocation();
            WeatherSearchQuery query2 = new WeatherSearchQuery(location.getCity(), WeatherSearchQuery.WEATHER_TYPE_FORECAST);
            WeatherSearch weathersearch2 = new WeatherSearch(context);
            weathersearch2.setOnWeatherSearchListener(new WeatherSearch.OnWeatherSearchListener() {
                @Override
                public void onWeatherLiveSearched(LocalWeatherLiveResult liveResult, int i) {
                }

                @Override
                public void onWeatherForecastSearched(LocalWeatherForecastResult forecastResult, int i) {
                    comeBack.onWeatherForecastSearched(forecastResult, i);
                }
            });
            weathersearch2.setQuery(query2);
            weathersearch2.searchWeatherAsyn();
        });


    }


}
