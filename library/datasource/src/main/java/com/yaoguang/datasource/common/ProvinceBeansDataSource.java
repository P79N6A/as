package com.yaoguang.datasource.common;

import android.content.Context;

import com.google.gson.Gson;
import com.yaoguang.lib.common.GetJsonDataUtil;
import com.yaoguang.lib.entity.ProvinceBean;
import com.yaoguang.lib.entity.ProvinceBeans;

import org.json.JSONArray;

import java.util.ArrayList;

import io.reactivex.Observable;

/**
 * 城市数据源
 * Created by zhongjh on 2018/9/5.
 */
public class ProvinceBeansDataSource {

    /**
     * 解析ProvinceBeansjson
     */
    public Observable<ProvinceBeans> analysisProvinceBeansJson(Context context) {
        return Observable.just(initJsonData(context));
    }

    /**
     * 解析数据
     */
    private ProvinceBeans initJsonData(Context context) {

        ProvinceBeans provinceBeans = new ProvinceBeans();

        /**
         * 注意：assets 目录下的Json文件仅供参考，实际使用可自行替换文件
         * 关键逻辑在于循环体
         *
         * */
        String jsonData = new GetJsonDataUtil().getJson(context, "province.json");//获取assets目录下的json文件数据

        ArrayList<ProvinceBean> provinceBean = parseData(jsonData);//用Gson 转成实体

        /**
         * 添加省份数据
         *
         * 注意：如果是添加的JavaBean实体，则实体类需要实现 IPickerViewData 接口，
         * PickerView会通过getPickerViewText方法获取字符串显示出来。
         */
        provinceBeans.setProvinces(provinceBean);

        for (int i = 0; i < provinceBean.size(); i++) {//遍历省份
            ArrayList<String> CityList = new ArrayList<>();//该省的城市列表（第二级）
            ArrayList<ArrayList<String>> Province_AreaList = new ArrayList<>();//该省的所有地区列表（第三极）

            for (int c = 0; c < provinceBean.get(i).getCityList().size(); c++) {//遍历该省份的所有城市
                String CityName = provinceBean.get(i).getCityList().get(c).getName();
                CityList.add(CityName);//添加城市

                ArrayList<String> City_AreaList = new ArrayList<>();//该城市的所有地区列表

                //如果无地区数据，建议添加空字符串，防止数据为null 导致三个选项长度不匹配造成崩溃
                if (provinceBean.get(i).getCityList().get(c).getArea() == null
                        || provinceBean.get(i).getCityList().get(c).getArea().size() == 0) {
                    City_AreaList.add("");
                } else {

                    for (int d = 0; d < provinceBean.get(i).getCityList().get(c).getArea().size(); d++) {//该城市对应地区所有数据
                        String AreaName = provinceBean.get(i).getCityList().get(c).getArea().get(d);

                        City_AreaList.add(AreaName);//添加该城市所有地区数据
                    }
                }
                Province_AreaList.add(City_AreaList);//添加该省所有地区数据
            }

            /**
             * 添加城市数据
             */
            provinceBeans.getCitys().add(CityList);

            /**
             * 添加地区数据
             */
            provinceBeans.getAreas().add(Province_AreaList);


        }

        return provinceBeans;
    }

    /**
     * Gson 解析
     *
     * @param result 字符串数据
     * @return 返回实体类集合
     */
    private ArrayList<ProvinceBean> parseData(String result) {
        ArrayList<ProvinceBean> detail = new ArrayList<>();
        try {
            JSONArray data = new JSONArray(result);
            Gson gson = new Gson();
            for (int i = 0; i < data.length(); i++) {
                ProvinceBean entity = gson.fromJson(data.optJSONObject(i).toString(), ProvinceBean.class);
                detail.add(entity);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return detail;
    }


}
