package com.yaoguang.greendao;


import com.yaoguang.greendao.biz.driver.LocationBiz;
import com.yaoguang.greendao.biz.company.PublicSearchDataBiz;
import com.yaoguang.greendao.biz.driver.WeatherBiz;

/**
 * Created by zhongjh on 2017/3/31.
 */
public class Injections {

    static PublicSearchDataBiz mPublicSearchDataBiz;
    private static LocationBiz mLocationDao;
    private static WeatherBiz mWeatherDao;

    private static LocationDao getLocationDao() {
        return DbCore.getDaoSession().getLocationDao();
    }

    // 暂时作废
    public static LocationBiz getLocationBiz() {
        if (mLocationDao == null) {
            mLocationDao = new LocationBiz(getLocationDao());
        }
        return mLocationDao;
    }


    private static WeatherDao getWeatherDao() {
        return DbCore.getDaoSession().getWeatherDao();
    }

    public static WeatherBiz getWeatherBiz() {
        if (mWeatherDao == null) {
            mWeatherDao = new WeatherBiz(getWeatherDao());
        }
        return mWeatherDao;
    }

    private static PublicSearchDataDao getPublicSearchDataDao() {
        return DbCore.getDaoSession().getPublicSearchDataDao();
    }

    public static PublicSearchDataBiz getPublicSearchDataBiz() {
        if (mPublicSearchDataBiz == null) {
            mPublicSearchDataBiz = new PublicSearchDataBiz(getPublicSearchDataDao());
        }
        return mPublicSearchDataBiz;
    }

}
