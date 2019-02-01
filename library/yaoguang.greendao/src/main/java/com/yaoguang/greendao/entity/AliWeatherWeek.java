package com.yaoguang.greendao.entity;

/**
 * 7天天气实体类
 * Created by zhongjh on 2017/9/7.
 */
public class AliWeatherWeek {

    public String getStrWeekDay() {
        return strWeekDay;
    }

    public void setStrWeekDay(String strWeekDay) {
        this.strWeekDay = strWeekDay;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day.substring(4, 6) + "/" + day.substring(6, 8);
    }

    public String getDayWeather() {
        return dayWeather;
    }

    public void setDayWeather(String dayWeather) {
        this.dayWeather = dayWeather;
    }

    public int getDayWeatherImage() {
        return dayWeatherImage;
    }

    public void setDayWeatherImage(int dayWeatherImage) {
        this.dayWeatherImage = dayWeatherImage;
    }

    //今天/明天/周几
    private String strWeekDay;
    //8/29
    private String day;
    //小到中雨
    private String dayWeather;
    //图标
    private int dayWeatherImage;

}
