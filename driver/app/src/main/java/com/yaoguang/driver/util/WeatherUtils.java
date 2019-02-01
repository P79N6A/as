package com.yaoguang.driver.util;

import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.GlideDrawableImageViewTarget;
import com.elvishew.xlog.XLog;
import com.google.gson.Gson;
import com.yaoguang.common.base.BaseApplication;
import com.yaoguang.common.common.ObjectUtils;
import com.yaoguang.common.common.SPUtils;
import com.yaoguang.appcommon.common.eventbus.HomeEvent;
import com.yaoguang.common.common.ULog;
import com.yaoguang.common.net.ApiDefault;
import com.yaoguang.greendao.entity.AliWeatherComplex;
import com.yaoguang.greendao.entity.Location;
import com.yaoguang.map.location.AMapLocation;
import com.yaoguang.map.location.impl.LocationManager;
import com.yaoguang.netmodule.api.WeatherApi;

import org.greenrobot.eventbus.EventBus;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * 文件名：
 * 描    述： 天气
 * 作    者：韦理英
 * 时    间：2017/8/9 0009.
 * 版    权：
 */
public class WeatherUtils {

    private static final String LAST_TIME = "last_time";
    private static final String WEATHER_CACHE = "weather_cache";
    /**
     * 天气
     */
    public static final String FLAG_WEATHER = "flag_weather";

    /**
     * 描    述：初始化天气
     * 作    者：韦理英
     * 时    间：
     */

    public static void init() {
        // 是否过了3小时
        boolean isOk = comparisonTime();
        if (isOk) { // 过了3小时，开始获取天气
            getWeather();
        } else { // 3小时以内获取缓存
            notice();
        }
    }

    /**
     * 描    述：获取天气
     * 作    者：韦理英
     * 时    间：
     */

    public static void getWeather() {
        final LocationManager locationManager = new LocationManager();
        locationManager.init(BaseApplication.getInstance().getBaseContext());
        locationManager.setComeback(new AMapLocation.Comeback() {
            @Override
            public void success(Location location) {
                locationManager.destroyLocation();
                WeatherApi weatherApi = ApiDefault.getInstance().retrofit.create(WeatherApi.class);
                weatherApi.getWeather2("APPCODE ebbefba3b9224a5389e9f18298e954a0", "3", ObjectUtils.parseString(location.getLat()), ObjectUtils.parseString(location.getLon()), "0", "0", "0", "0", "0")
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Observer<AliWeatherComplex>() {
                            @Override
                            public void onSubscribe(Disposable d) {
                            }

                            @Override
                            public void onNext(AliWeatherComplex aliWeather) {
                                if (aliWeather == null || aliWeather.getShowapi_res_body().getNow() == null
                                        || aliWeather.getShowapi_res_body() == null ||
                                        aliWeather.getShowapi_res_body().getNow().getWeather_pic() == null)
                                    return;

                                saveCache(aliWeather);
                                saveRequestTime();
                            }

                            @Override
                            public void onError(Throwable e) {
                            }

                            @Override
                            public void onComplete() {
                                notice();
                            }
                        });
            }
        });

    }


    /**
     * 文件名：
     * 描    述：通知
     * 作    者：韦理英
     * 时    间：
     * 版    权：
     */
    private static void notice() {
        EventBus.getDefault().post(new HomeEvent(FLAG_WEATHER));
    }

    /**
     * 描    述：获取上次缓存时间
     * 作    者：韦理英
     * 时    间：
     */

    public static long getLastTime() {
        return SPUtils.getInstance().getLong(LAST_TIME);
    }

    /**
     * 描    述：保存缓存时间
     * 作    者：韦理英
     * 时    间：
     */

    public static void saveRequestTime() {
        // 保存时间
        SPUtils.getInstance().put(LAST_TIME, System.currentTimeMillis());
    }

    /**
     * 描    述：比较时间是否大于3小时
     * 作    者：韦理英
     * 时    间：
     * return boolean true 大于 false 小限
     */

    public static boolean comparisonTime() {
        // 获取缓存时间
        long lastTime = getLastTime();
        // 请求天气
        if (lastTime == -1) {
            saveRequestTime();
            return true;
        }
        // 比较是否大于3小时
        long nowTime = System.currentTimeMillis();
        if (((nowTime - lastTime) / 1000 / 60 / 60) > 3) {
            ULog.i("获取缓存时间 true");
            return true;
        }
        ULog.i("获取缓存时间 false");
        return false;
    }

    /**
     * 描    述：显示天气ui
     * 作    者：韦理英
     * 时    间：
     */

    public static void showLodingUi(TextView... tv) {
        // 天气度数
        tv[0].setText("加载中...");
        // 天气城市
        tv[1].setText("加载中...");
    }

    public static void showUi(AliWeatherComplex cacheWeather, final ImageView imgWeather, TextView... tv) {
        if (cacheWeather == null || cacheWeather.getShowapi_res_body().getNow() == null
                || cacheWeather.getShowapi_res_body() == null ||
                cacheWeather.getShowapi_res_body().getNow().getWeather_pic() == null)
            return;
        imgWeather.setVisibility(View.VISIBLE);
        com.bumptech.glide.Glide.with(BaseApplication.getInstance().getBaseContext())
                .load(cacheWeather.getShowapi_res_body().getNow().getWeather_pic())
                .centerCrop()
                .into(new GlideDrawableImageViewTarget(imgWeather) {
                    @Override
                    public void onResourceReady(GlideDrawable drawable, GlideAnimation anim) {
                        super.onResourceReady(drawable, anim);
                        //在这里添加一些图片加载完成的操作
                        imgWeather.setVisibility(View.VISIBLE);
                    }
                });
        // 天气度数
        tv[0].setText(cacheWeather.getShowapi_res_body().getNow().getTemperature() + "℃");
        // 天气城市
        tv[1].setText(cacheWeather.getShowapi_res_body().getCityInfo().getC5());
    }

    /**
     * 描    述：获取缓存天气
     * 作    者：韦理英
     * 时    间：
     * return
     * [天气实体]
     */

    public static AliWeatherComplex getCacheWeather() {
        // 获取缓存天气json
        String json = SPUtils.getInstance().getString(WEATHER_CACHE);
        if (TextUtils.isEmpty(json)) return null;
        // 转成实体
        AliWeatherComplex aliWeather = new Gson().fromJson(json, AliWeatherComplex.class);
        return aliWeather;
    }

    /**
     * 描    述：保存天气缓存
     * 作    者：韦理英
     * 时    间：
     *
     * @param aliWeather
     */

    public static void saveCache(AliWeatherComplex aliWeather) {
        if (aliWeather == null) return;
        // 实体转json
        String json = new Gson().toJson(aliWeather);
        // 保存json
        SPUtils.getInstance().put(WEATHER_CACHE, json);
    }

}
