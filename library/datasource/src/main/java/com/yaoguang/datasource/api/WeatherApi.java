package com.yaoguang.datasource.api;

import com.yaoguang.greendao.entity.AliWeather;
import com.yaoguang.greendao.entity.AliWeatherComplex;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Query;

/**
 * Created by zhongjh on 2017/5/24.
 */

public interface WeatherApi {


//    @GET("https://ali-weather.showapi.com/gps-to-weather?")
//    Call<AliWeather> getWeather(@Header("Authorization") String Authorization, @Query("from") String from, @Query("lat") String lat, @Query("lng") String lng,
//                                      @Query("need3HourForcast") String need3HourForcast, @Query("needAlarm") String needAlarm, @Query("needHourData") String needHourData, @Query("needIndex") String needIndex, @Query("needMoreDay") String needMoreDay);

    /**
     * 获取天气
     *
     * @param Authorization    APPCODE ebbefba3b9224a5389e9f18298e954a0
     * @param from             输入的坐标类型： 1：GPS设备获取的角度坐标;
     *                         2：GPS获取的米制坐标、sogou地图所用坐标;
     *                         3：google地图、soso地图、aliyun地图、mapabc地图和amap地图所用坐标
     *                         4：3中列表地图坐标对应的米制坐标
     *                         5：百度地图采用的经纬度坐标
     *                         6：百度地图采用的米制坐标
     *                         7：mapbar地图坐标;
     *                         8：51地图坐标
     * @param lat              纬度值
     * @param lng              经度值
     * @param need3HourForcast 是否需要当天每3/6小时一次的天气预报列表。1为需要，0为不需要。
     * @param needAlarm        是否需要天气预警。1为需要，0为不需要。
     * @param needHourData     是否需要每小时数据的累积数组。由于本系统是半小时刷一次实时状态，因此实时数组最大长度为48。每天0点长度初始化为0. 1为需要 0为不
     * @param needIndex        是否需要返回指数数据，比如穿衣指数、紫外线指数等。1为返回，0为不返回。
     * @param needMoreDay      是否需要返回7天数据中的后4天。1为返回，0为不返回。
     * @return json
     */
    @GET("https://ali-weather.showapi.com/gps-to-weather?")
    Observable<AliWeatherComplex> getWeather2(@Header("Authorization") String Authorization, @Query("from") String from, @Query("lat") String lat, @Query("lng") String lng,
                                              @Query("need3HourForcast") String need3HourForcast, @Query("needAlarm") String needAlarm, @Query("needHourData") String needHourData, @Query("needIndex") String needIndex, @Query("needMoreDay") String needMoreDay);
}
