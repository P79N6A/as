package com.yaoguang.greendao.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;

/**
 * Created by 韦理英
 * on 2017/5/5 0005.
 */

@Entity
public class Location {

    @Id(autoincrement = true)
    private Long id;
    private double lon; //经度
    private double lat; //纬度
    private String positionTime; //时间
    private String error; // 失败
    private String province; // 省
    private String city;     // 市
    private String district; // 区
    private String street;   // 街道
    private String address;  // 详细地址
    private String userDriverId; // 司机id
    private String azimuth;// 角度
    private String speed;// 速度
    @Generated(hash = 1423688396)
    public Location(Long id, double lon, double lat, String positionTime,
            String error, String province, String city, String district,
            String street, String address, String userDriverId, String azimuth,
            String speed) {
        this.id = id;
        this.lon = lon;
        this.lat = lat;
        this.positionTime = positionTime;
        this.error = error;
        this.province = province;
        this.city = city;
        this.district = district;
        this.street = street;
        this.address = address;
        this.userDriverId = userDriverId;
        this.azimuth = azimuth;
        this.speed = speed;
    }
    @Generated(hash = 375979639)
    public Location() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public double getLon() {
        return this.lon;
    }
    public void setLon(double lon) {
        this.lon = lon;
    }
    public double getLat() {
        return this.lat;
    }
    public void setLat(double lat) {
        this.lat = lat;
    }
    public String getPositionTime() {
        return this.positionTime;
    }
    public void setPositionTime(String positionTime) {
        this.positionTime = positionTime;
    }
    public String getError() {
        return this.error;
    }
    public void setError(String error) {
        this.error = error;
    }
    public String getProvince() {
        return this.province;
    }
    public void setProvince(String province) {
        this.province = province;
    }
    public String getCity() {
        return this.city;
    }
    public void setCity(String city) {
        this.city = city;
    }
    public String getDistrict() {
        return this.district;
    }
    public void setDistrict(String district) {
        this.district = district;
    }
    public String getStreet() {
        return this.street;
    }
    public void setStreet(String street) {
        this.street = street;
    }
    public String getAddress() {
        return this.address;
    }
    public void setAddress(String address) {
        this.address = address;
    }
    public String getUserDriverId() {
        return this.userDriverId;
    }
    public void setUserDriverId(String userDriverId) {
        this.userDriverId = userDriverId;
    }
    public String getAzimuth() {
        return this.azimuth;
    }
    public void setAzimuth(String azimuth) {
        this.azimuth = azimuth;
    }
    public String getSpeed() {
        return this.speed;
    }
    public void setSpeed(String speed) {
        this.speed = speed;
    }






}
