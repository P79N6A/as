package com.yaoguang.greendao.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * 作者：韦理英
 * 时间： 2017/5/8 0008.
 */

@Entity
public class Weather {
    @Id(autoincrement = true)
    private Long id;
    private String reportTime;  // 发布时间
    private String weather;  // 文字提醒
    private String temperature; // 温度
    private String windDirection;  // 风向
    private String windPower;  // 风力等级
    private String humidity;  // 湿度
    private String error;

    @Generated(hash = 1580606043)
    public Weather(Long id, String reportTime, String weather, String temperature,
            String windDirection, String windPower, String humidity, String error) {
        this.id = id;
        this.reportTime = reportTime;
        this.weather = weather;
        this.temperature = temperature;
        this.windDirection = windDirection;
        this.windPower = windPower;
        this.humidity = humidity;
        this.error = error;
    }

    @Generated(hash = 556711069)
    public Weather() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getReportTime() {
        return reportTime;
    }

    public void setReportTime(String reportTime) {
        this.reportTime = reportTime;
    }

    public String getWeather() {
        return weather;
    }

    public void setWeather(String weather) {
        this.weather = weather;
    }

    public String getTemperature() {
        return temperature;
    }

    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }

    public String getWindDirection() {
        return windDirection;
    }

    public void setWindDirection(String windDirection) {
        this.windDirection = windDirection;
    }

    public String getWindPower() {
        return windPower;
    }

    public void setWindPower(String windPower) {
        this.windPower = windPower;
    }

    public String getHumidity() {
        return humidity;
    }

    public void setHumidity(String humidity) {
        this.humidity = humidity;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}
