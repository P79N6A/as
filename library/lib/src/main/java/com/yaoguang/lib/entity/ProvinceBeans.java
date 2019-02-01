package com.yaoguang.lib.entity;

import java.util.ArrayList;

/**
 * 省 - 市 - 区数据源合集
 * <p>
 * Created by zhongjh on 2017/5/27.
 */
public class ProvinceBeans {

    ArrayList<ProvinceBean> provinces = new ArrayList<>();
    ArrayList<ArrayList<String>> citys = new ArrayList<>();
    ArrayList<ArrayList<ArrayList<String>>> areas = new ArrayList<>();

    public ArrayList<ProvinceBean> getProvinces() {
        return provinces;
    }

    public void setProvinces(ArrayList<ProvinceBean> provinces) {
        this.provinces = provinces;
    }

    public ArrayList<ArrayList<String>> getCitys() {
        return citys;
    }

    public void setCitys(ArrayList<ArrayList<String>> citys) {
        this.citys = citys;
    }

    public ArrayList<ArrayList<ArrayList<String>>> getAreas() {
        return areas;
    }

    public void setAreas(ArrayList<ArrayList<ArrayList<String>>> areas) {
        this.areas = areas;
    }
}
